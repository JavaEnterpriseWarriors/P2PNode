/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marino
 */
public class RequestAnalyst {
    
    private InetAddress inetAddress;
    private int port;
    
    private LocalDataManager localDataManager;
    
    Logger logger = Logger.getLogger(this.getClass().getName());
    
    public RequestAnalyst(InetAddress inetAddress, int port){
        this.inetAddress = inetAddress;
        this.port = port;
        
        localDataManager = new LocalDataManager();
    }
    
    public void process(byte[] message){
        String mask = new String(new byte[] { message[0] });
        
        byte[] rest = Arrays.copyOfRange(message, 1, message.length);
        
        if (mask.equals("R")){
                DataSender.sendData(inetAddress.getHostAddress(), port, rest);
        } else if (mask.equals("F")){
            localDataManager.saveFile(message);
        } else {
            logger.log(Level.SEVERE, new String(rest));
        }
    }
    
}
