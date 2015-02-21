/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marino
 */
public class RequestReceiver implements Runnable {

    private int port;
    private RequestAnalyst requestAnalyst;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    Logger logger = Logger.getLogger(this.getClass().getName());
    
    public RequestReceiver(int port){
        this.port = port;
        
        try {
            serverSocket = new ServerSocket(port);
            logger.log(Level.SEVERE, "ServerSocket is created");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                logger.log(Level.SEVERE, "Connected");
                
                byte[] messageByte = getData(new DataInputStream(clientSocket.getInputStream()));
                
                requestAnalyst = new RequestAnalyst(clientSocket.getInetAddress(), port);
                requestAnalyst.process(messageByte);

                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    private byte[] getData(DataInputStream in) throws IOException{
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
                
                String log = "Bytes readed: " + messageByte.length;
                logger.log(Level.SEVERE, log);
                
                return messageByte;
    }
    
}
