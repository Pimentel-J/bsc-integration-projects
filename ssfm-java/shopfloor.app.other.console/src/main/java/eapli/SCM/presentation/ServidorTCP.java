/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.SCM.presentation;

import eapli.shopfloor.SCM.application.RecolherMensagensController;
import java.io.*;
import java.net.*;

/**
 *
 *.se.1181483
 */
public class ServidorTCP {

    static ServerSocket sock;

    public static void main(String args[]) throws Exception {
        Socket cliSock;

        try {
            sock = new ServerSocket(9999);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }
        System.out.println("SERVICO DE COMUNICACAO COM MAQUINAS - TCP SERVER");
        while (true) {
            cliSock = sock.accept();
            new Thread(new ServidorTcpThread(cliSock)).start();
        }
    }
}

class ServidorTcpThread implements Runnable {

    private RecolherMensagensController controller = new RecolherMensagensController();

    private Socket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;

    private final String RESPOSTA_ACK = "Maq. valida/Msg. Aceite";
    private final String RESPOSTA_NACK = "Maq. invalida/Msg. Rejeitada";

    public ServidorTcpThread(Socket cli_s) {
        s = cli_s;
    }

    public void run() {
        int version = 0, code, id, dataLength;
        String rawData = null, outResposta = null;

        InetAddress clientIP;

        clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress()
                + ", port number " + s.getPort());

        int outVersion = 0, outCode, outId = 0, outLength = 0;
        try {
            sOut = new DataOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());

            do {
                version = sIn.read();
                if (version == -1) {
                    break;
                }
                System.out.println("Version:" + version);

                code = sIn.read();
                System.out.println("Code:" + code);

                id = readId(sIn);

                dataLength = readDatalength(sIn);

                rawData = readRawData(dataLength, sIn);

                boolean resposta = controller.recolherMensagem(clientIP, version, code, id, dataLength, rawData);
                if (resposta) {
                    outCode = 150;
                    outLength = RESPOSTA_ACK.length();
                    outResposta = RESPOSTA_ACK;
                } else {
                    outCode = 151;
                    outLength = RESPOSTA_NACK.length();
                    outResposta = RESPOSTA_NACK;
                }

                sOut.writeByte(outVersion);
                sOut.writeByte(outCode);
                sOut.write(outId);
                sOut.write(outId * 256);
                sOut.write(outLength);
                sOut.write(outLength * 256);
                if (outLength>0) {
                    sOut.writeBytes(outResposta);
                }
            } while (true);
            System.out.println("Client " + clientIP.getHostAddress() + ", port number: " + s.getPort()
                    + " disconnected");
            s.close();
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }

    private int readId(DataInputStream sIn) throws IOException {
        int f, i, id;
        f = 1;
        id = 0;
        for (i = 0; i < 2; i++) {
            id = id + f * sIn.read();
            f *= 256;
        }
        System.out.println("id:" + id);
        return id;
    }

    private int readDatalength(DataInputStream sIn) throws IOException {
        int f, i, dataLength = 0;
        f = 1;
        for (i = 0; i < 2; i++) {
            dataLength = dataLength + f * sIn.read();
            f *= 256;
        }
        System.out.println("data length:" + dataLength);
        return dataLength;
    }

    private String readRawData(int dataLength, DataInputStream sIn) throws IOException {
        String rawData = null;
        if (dataLength > 0) {

            byte buffer[] = new byte[dataLength];

            sIn.read(buffer, 0, dataLength);

            rawData = new String(buffer, 0, dataLength);
            System.out.println("raw data: " + rawData);
            return rawData;
        }
        return null;
    }
}
