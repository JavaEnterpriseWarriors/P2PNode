/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marino
 */
public class DataSender {
    
    static Logger logger = Logger.getLogger(DataSender.class.getName());
    
    public static void sendData(String address, int port, byte[] data){
        try {
            Socket socket = new Socket(address, port);
            OutputStream output = socket.getOutputStream();
            output.write(data);
            output.flush();
            output.close();
            socket.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
