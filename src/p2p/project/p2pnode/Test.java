/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Marino
 */
public class Test implements Runnable {

    Socket clientSocket;
    static ServerSocket serverSocket;
    InputStream input;
    OutputStream output;

    Logger logger = Logger.getLogger(Test.class.getName());

//    public static void main(String[] args) throws IOException {
//        serverSocket = new ServerSocket(5005);
//        (new Thread(new Test())).start();
//    }

    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                logger.log(Level.SEVERE, "Connected");
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                byte[] messageByte = new byte[0];
                int bytesRead = 1;
                while (bytesRead > 0) {
                    byte[] messagePartByte = new byte[1024];
                    bytesRead = in.read(messagePartByte);
                    if (bytesRead > 0) {
                        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
                        tmpOutputStream.write(messageByte);
                        tmpOutputStream.write(messagePartByte);

                        messageByte = tmpOutputStream.toByteArray();
                    }
                }
                in.close();
                clientSocket.close();
                logger.log(Level.SEVERE, "Bytes readed: " + messageByte.length);

            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
