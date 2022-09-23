#ifndef HEADER_H
#define HEADER_H

#define BUFFER_SIZE 100
#define SERVER_PORT "9999"
#define BUF_SIZE_UDP 300
#define SERVER_PORT_UDP "31304"
// #define SERVER_IP "10.9.21.70" // Tests TCP server (C)
 #define SERVER_IP "10.9.21.69"
// #define SERVER_IP "10.8.209.245"

#define FILE_PATH "mensagens_maquinas/Mensagens."
#define ERROR_PATH "mensagens_erro/ERRO."

#define CONNECTION_ON 1
#define CONNECTION_OFF 0
#define NEXT_CONNECTION_ATTEMPT 5	// segundos

#define HELLO 0
#define MSG 1
#define CONFIG 2
#define RESET 3
#define ACK 150
#define NACK 151

#define STRING_MAX 50

typedef struct {
  char** parametrosEntrada;
  int socketTCP;
	int statusLigacaoTCP;
  int statusLigacaoUDP;
  int ultimaRespostaTCPServer;
  char ultimaDescricaoResposta[STRING_MAX];
} recursosPartilhados;


