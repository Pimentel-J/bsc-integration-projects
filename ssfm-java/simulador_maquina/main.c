#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <pthread.h>
#include "header.h"

pthread_mutex_t mux_tcp;
pthread_mutex_t mux_tcp_retorno;

/* Impressão de mensagens de consola */

// Imprime "aguardando resposta" do servidor
void aguardarResposta()
{
	printf("[TCP] Aguardando resposta ACK ou NACK de %s. Próxima tentativa dentro de %d segundos\n", SERVER_IP, NEXT_CONNECTION_ATTEMPT);
	sleep(NEXT_CONNECTION_ATTEMPT);
}

// Imprime status da ligação atual
void imprimirEstadoLigacao(int status)
{
	if (status == CONNECTION_ON)
	{
		printf("Connection status: ON\n");
	}
	else if (status == CONNECTION_OFF)
	{
		printf("Connection status: OFF\n");
	}
}

/* Escrita e leitura de mensagens (cliente<->servidor) */

// Envia um unsigned short para o server, byte a byte
void escreverShortNum(int socket, unsigned short number)
{
	unsigned char b1;
	int i;
	for (i = 0; i < 2; i++)
	{
		b1 = number % 256;
		write(socket, &b1, sizeof(b1));
		number = number / 256;
	}
}

// Lê um unsigned short do server, byte a byte
unsigned short lerShortNum(int socket)
{
	unsigned char b1;
	unsigned short number = 0, f = 1;
	int i;
	for (i = 0; i < 2; i++)
	{
		read(socket, &b1, sizeof(b1));
		number = number + b1 * f;
		f = f * 256;
	}
	return number;
}

// Envia mensagem para o server (de acordo com o formato/protocolo estabelecido)
void enviarMensagem(int socket, unsigned char version, unsigned char code, unsigned short machine_id, unsigned short data_length, char *raw_data)
{

	printf("[OUTGOING MESSAGE]\n");
	write(socket, &version, sizeof(version));
	printf("-version: %u\n", version);
	write(socket, &code, sizeof(code));
	printf("-code: %u\n", code);
	escreverShortNum(socket, machine_id);
	printf("-id: %u\n", machine_id);
	escreverShortNum(socket, data_length);
	printf("-data length: %u\n", data_length);
	if (data_length > 0)
	{
		write(socket, raw_data, strlen(raw_data));
		printf("-raw data: %s\n", raw_data);
	}
	printf("\n");
}

// Receives message from the server (according to message defined format/protocol)
int receberMensagem(int socket, char* response) {

	unsigned char version, code;
	unsigned short machine_id, data_length;

	printf("[INCOMING MESSAGE]\n");
	read(socket, &version, sizeof(version));
	printf("-version: %u\n", version);
	read(socket, &code, sizeof(code));
	printf("-code: %u\n", code);
	machine_id = lerShortNum(socket);
	printf("-id: %u\n", machine_id);
	data_length = lerShortNum(socket);
	printf("-data length: %u\n", data_length);
	char reader[STRING_MAX];
	if (data_length > 0) {
		read(socket, reader, sizeof(reader)-1);
    reader[data_length] = 0x00;
		strcpy(response, reader);
		printf("-raw data: %s\n", reader);
	}
	printf("\n");

	return code;
}

/* Funcionamento da Máquina */

// Escrever mensagem no ficheiro de erros próprio da máquina em questão
void escreverErro(unsigned short machine_id, char *linha)
{
	char filePath2[STRING_MAX];
	snprintf(filePath2, sizeof(filePath2), "%s%d", ERROR_PATH, machine_id);
	FILE *fp2 = fopen(filePath2, "a");
	if (fp2 == NULL)
	{
		printf("Failed to open file %s\n", filePath2);
	}
	else
	{
		fprintf(fp2, "%s\n", linha);
		fclose(fp2); //close file
	}
}

// Gera mensagens a partir de ficheiro de texto para enviar para o server e obter resposta
void ligarSensores(int socket, unsigned char version, unsigned char code, unsigned short machine_id, int cadenciaEnvio, recursosPartilhados* recursos) {

	unsigned short data_length;

	int response;
	char responseDescription[STRING_MAX];

	char linha[BUFFER_SIZE] = {0};
	// Opens file with read permission
	char filePath[STRING_MAX];
	snprintf(filePath, sizeof(filePath), "%s%d", FILE_PATH, machine_id);
	FILE *fp = fopen(filePath, "r");
	if (fp == NULL)
	{
		printf("Failed to open file %s\n", filePath);
	}
	else
	{
		while (fgets(linha, sizeof(linha), fp) != NULL)
		{
			strtok(linha, "\n");
			sleep(cadenciaEnvio); // Simulate machine working

			// Send MSG message to server
			data_length = strlen(linha);
			enviarMensagem(socket, version, MSG, machine_id, data_length, linha);

			// Receive reply from server
			response = receberMensagem(socket, responseDescription);
			while (response != ACK && response != NACK) {
				aguardarResposta();
				response = receberMensagem(socket, responseDescription);
			}
			if (response == NACK) {
				printf("%s\n", responseDescription);
				// write in the error file
				escreverErro(machine_id, linha);
			}

			// Guardar última resposta e texto descritivo na estrutura de recursos partilhados
			recursos->ultimaRespostaTCPServer = response;
			strcpy(recursos->ultimaDescricaoResposta, responseDescription);

		}
		fclose(fp); //close file
	}
}

// Simula o funcionamento da máquina ligada ao servidor
int simularFuncionamento(recursosPartilhados* recursos) {
	unsigned char version = 0;
	unsigned short machine_id = (unsigned short)atoi(recursos->parametrosEntrada[1]);
	unsigned short data_length;
	int cadenciaEnvio = atoi(recursos->parametrosEntrada[2]);
	int socket = recursos->socketTCP;

	// Send Hello message
	data_length = 0;
	enviarMensagem(socket, version, HELLO, machine_id, data_length, NULL);

	// Receive answer from server
	char responseDescription[STRING_MAX];
	int response = receberMensagem(socket, responseDescription);

	while (response != ACK && response != NACK) {
		aguardarResposta();
		response = receberMensagem(socket, responseDescription);
	}
	// Guardar última resposta e texto descritivo na estrutura de recursos partilhados
	recursos->ultimaRespostaTCPServer = response;
	strcpy(recursos->ultimaDescricaoResposta, responseDescription);

	if (response == NACK) {
		printf("%s\n", responseDescription);
		return -1;
	}

	// Read file to simulate raw_data being generated and sent to the server
	ligarSensores(socket, version, MSG, machine_id, cadenciaEnvio, recursos);

	return 0;
}

/* Ligação TCP */

// Fecha a ligação ao Servidor TCP definido
void fecharLigacaoServidorTCP(int *status, int socket)
{

	close(socket);
	printf("[TCP] Ligação ao servidor %s fechada\n", SERVER_IP);

	*status = CONNECTION_OFF;
}

// Efetua a ligação ao Servidor TCP definido
int efetuarLigacaoServidorTCP(int *status)
{

	int err, sock;
	struct addrinfo req, *list;

	printf("[TCP] A efetuar ligação ao servidor %s ...\n", SERVER_IP);

	bzero((char *)&req, sizeof(req));
	// let getaddrinfo set the family depending on the supplied server address
	req.ai_family = AF_UNSPEC;
	req.ai_socktype = SOCK_STREAM;
	err = getaddrinfo(SERVER_IP, SERVER_PORT, &req, &list);
	if (err)
	{
		printf("[TCP] Falha ao obter o endereço do servidor, erro: %s\n", gai_strerror(err));
		return -1;
	}

	sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
	if (sock == -1)
	{
		printf("[TCP] Falha ao abrir o socket\n");
		freeaddrinfo(list);
		return -1;
	}

	// Menu tentativa de ligação falhada

	int selectedOption;
	if (connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
	{
		do
		{
			printf("[TCP] Falha de ligação a %s.\n", SERVER_IP);
			printf("Opções:\n");
			printf("1. Tentar ligar novamente\n");
			printf("0. Cancelar ligação TCP\n");
			scanf("%d", &selectedOption);
			if (selectedOption == 1)
			{
				printf("[TCP] A efetuar ligação ao servidor %s ...\n", SERVER_IP);
				connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen);
			}
		} while (selectedOption == 1);
		if (selectedOption == 0)
		{
			close(sock);
			return -1;
		}
	}

	*status = CONNECTION_ON;
	printf("[TCP] Ligação ao servidor %s efetuada\n", SERVER_IP);
	printf("\n");
	return sock;
}

int ligarServidorUDP(recursosPartilhados *recursos)
{
	struct sockaddr_storage client;
	int err, sock_udp, res;
	unsigned int adl;
	char line[BUF_SIZE_UDP];
	char cliIPtext[BUF_SIZE_UDP], cliPortText[BUF_SIZE_UDP];
	struct addrinfo req, *list;

	bzero((char *)&req, sizeof(req));
	// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
	req.ai_family = AF_INET6;
	req.ai_socktype = SOCK_DGRAM;
	req.ai_flags = AI_PASSIVE; // local address

	err = getaddrinfo(NULL, SERVER_PORT_UDP, &req, &list);

	if (err)
	{
		printf("[UDP] Falha a obter endereco local, error: %s\n", gai_strerror(err));
		exit(1);
	}

	sock_udp = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
	if (sock_udp == -1)
	{
		perror("[UDP] Falha a abrir socket");
		freeaddrinfo(list);
		exit(1);
	}

	if (bind(sock_udp, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
	{
		perror("[UDP] Emparelhamento falhado");
		close(sock_udp);
		freeaddrinfo(list);
		exit(1);
	}

	freeaddrinfo(list);
	puts("[UDP] A escuta por pedidos UDP (IPv6 ou IPv4).\n");
	recursos->statusLigacaoUDP = CONNECTION_ON;
	adl = sizeof(client);

	while (1)
	{
		res = recvfrom(sock_udp, line, BUF_SIZE_UDP, 0, (struct sockaddr *)&client, &adl);
		if (!getnameinfo((struct sockaddr *)&client, adl,
						 cliIPtext, BUF_SIZE_UDP, cliPortText, BUF_SIZE_UDP, NI_NUMERICHOST | NI_NUMERICSERV))
		{
			printf("[UDP] Pedido do no %s, porto numero %s\n", cliIPtext, cliPortText);
		}
		else
		{
			puts("[UDP] Pedido recebido, mas falhou a rececao do endereco de cliente");
		}

		char str_concat[STRING_MAX];
		sprintf(str_concat, "%d;%d;%d;%d;%s;", 0, recursos->ultimaRespostaTCPServer, atoi(recursos->parametrosEntrada[1]), STRING_MAX, recursos->ultimaDescricaoResposta);

		printf("str_concat: %s\n", str_concat);
		sendto(sock_udp, &str_concat, sizeof(str_concat), 0, (struct sockaddr *)&client, adl);
	}
	close(sock_udp);
	exit(0);

	return sock_udp;
}

// Execução da Thread TCP
void *ligacaoTCP(void *arg)
{

	if (arg == NULL)
	{
		perror("v pointer is not valid!!! ");
		pthread_mutex_unlock(&mux_tcp_retorno);
		pthread_exit(NULL);
	}
	recursosPartilhados* recursos = (recursosPartilhados*) arg;

	// Efetuar ligação ao servidor
	int socket;
	socket = efetuarLigacaoServidorTCP(&recursos->statusLigacaoTCP);
	if (socket == -1) {
		pthread_mutex_unlock(&mux_tcp_retorno);
		pthread_exit((void*) NULL);
	}
	recursos->socketTCP = socket;
	pthread_mutex_unlock(&mux_tcp_retorno);
	pthread_mutex_lock(&mux_tcp);
	fecharLigacaoServidorTCP(&recursos->statusLigacaoTCP, recursos->socketTCP);
	pthread_exit((void *)NULL);
}

// Execução da Thread UDP
void *ligacaoUDP(void *arg)
{

	if (arg == NULL)
	{
		perror("v pointer is not valid!!! ");
		pthread_exit(NULL);
	}

	recursosPartilhados *recursos = (recursosPartilhados *)arg;

	// Efetuar ligação do servidor
	int socket;
	socket = ligarServidorUDP(recursos);
	if (socket == -1)
	{
		pthread_exit((void *)NULL);
	}
	pthread_exit((void *)NULL);
}

// Cria a Thread TCP
pthread_t correrThreadTCP(void* recursos) {

	pthread_t tcp_thread;
	int retorno;

	// Create TCP thread
	retorno = pthread_create(&tcp_thread, NULL, ligacaoTCP, recursos);
	if(retorno != 0) {
		fprintf(stderr, "pthread_create failed!!! Error=%d\n", retorno);
		exit(EXIT_FAILURE);
	}

	return tcp_thread;
}

// Espera pela morte da Thread TCP
void terminarThreadTCP(pthread_t tcp_thread)
{
	int retorno;

	// Wait for the TCP thread to finish
	retorno = pthread_join(tcp_thread, NULL);
	if (retorno != 0)
	{
		fprintf(stderr, "pthread_join failed!!! Error=%d\n", retorno);
		exit(EXIT_FAILURE);
	}
}

// Cria a Thread UDP
pthread_t correrThreadUDP(void *recursos)
{
	pthread_t udp_thread;
	int retorno;

	// Create UDP thread
	retorno = pthread_create(&udp_thread, NULL, ligacaoUDP, recursos);
	if (retorno != 0)
	{
		fprintf(stderr, "pthread_create failed!!! Error=%d\n", retorno);
		exit(EXIT_FAILURE);
	}

	return udp_thread;
}

// Espera pela morte da Thread UDP
void terminarThreadUDP(pthread_t udp_thread)
{
	int retorno;

	// Wait for the UDP thread to finish
	retorno = pthread_join(udp_thread, NULL);
	if (retorno != 0)
	{
		fprintf(stderr, "pthread_join failed!!! Error=%d\n", retorno);
		exit(EXIT_FAILURE);
	}
}

int main(int argc, char **argv)
{

	if (argc != 3)
	{
		puts("Deve introduzir o ID da máquina e a cadência de envio como parâmetros.");
		exit(1);
	}

	printf("\n-- Máquina %s ON --\n", argv[1]);

	recursosPartilhados recursos;
	recursos.parametrosEntrada = argv;
	recursos.statusLigacaoTCP = CONNECTION_OFF;
	recursos.statusLigacaoUDP = CONNECTION_OFF;

	pthread_mutex_lock(&mux_tcp);
	pthread_mutex_lock(&mux_tcp_retorno);

	pthread_t udp_thread, tcp_thread;
	udp_thread = correrThreadUDP((void *)&recursos);

	int selectedOption;

	do {
		imprimirEstadoLigacao(recursos.statusLigacaoTCP);
		printf("Opções:\n");
		printf("Ctrl+C. Desligar Máquina\n");
		if (recursos.statusLigacaoTCP == CONNECTION_OFF) {
			printf("1. Ligar ao Servidor TCP\n");
		} else {
			printf("1. Desligar do Servidor TCP\n");
		}
		printf("2. Simular funcionamento\n");
		scanf("%d", &selectedOption);

		// Menu Máquina

		switch (selectedOption)
		{
			case 1:
				if (recursos.statusLigacaoTCP == CONNECTION_OFF) {
					tcp_thread = correrThreadTCP((void*) &recursos);
					pthread_mutex_lock(&mux_tcp_retorno);
					if (recursos.statusLigacaoTCP == CONNECTION_OFF) {
						terminarThreadTCP(tcp_thread);
					}
				} else {
					pthread_mutex_unlock(&mux_tcp);
					terminarThreadTCP(tcp_thread);
				}
				break;
			case 2:
				if (recursos.statusLigacaoTCP == CONNECTION_OFF) {
					printf("Deve estabelecer primeiro uma ligação TCP com o servidor.\n");
				} else {
					// Simular funcionamento da máquina (envio e receção de mensagens)
					if (simularFuncionamento(&recursos) == -1) {
						break;	// Em caso de resposta NACK à msg HELLO, deve cancelar o envio de mensagens
					}
				}
		}
	} while (1);

	if (recursos.statusLigacaoTCP == CONNECTION_ON) {
		pthread_mutex_unlock(&mux_tcp);
		terminarThreadTCP(tcp_thread);
	}

	printf("\n-- Máquina %s OFF --\n", argv[1]);

	exit(0);
}
