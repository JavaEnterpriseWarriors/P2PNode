/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.ByteArrayOutputStream;
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

    public RequestAnalyst(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;

        localDataManager = new LocalDataManager();
    }

    public void process(byte[] message) {
        String mask = new String(new byte[]{message[0]});

        byte[] data = Arrays.copyOfRange(message, 1, message.length);

        switch (mask) {
            case "R":
                serveRequest(data);
                break;
            case "F":
                serveIncoimingFile(data);
                break;
            default:
                logger.log(Level.SEVERE, new String(message));
                break;
        }
    }

    private void serveRequest(byte[] data) {
        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        try {
            String tmp = "F";
            String fileName = new String(data).trim();
            String tmpFileName = fileName + ";";
            byte[] file = localDataManager.getFile(fileName);
            
            tmpOutputStream.write(tmp.getBytes());
            tmpOutputStream.write(tmpFileName.getBytes());
            tmpOutputStream.write(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        data = tmpOutputStream.toByteArray();
        DataSender.sendData(inetAddress.getHostAddress(), port, data);
    }
    
    private void serveIncoimingFile(byte[] data){
        
        int marker = 0;
        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < data.length; i++){
            if ((new String(new byte[]{data[i]})).equals(";")){
                marker = i + 1;
                break;
            }
            tmpOutputStream.write(data[i]);
        }
        String fileName = new String(tmpOutputStream.toByteArray());
        
        byte[] fileData = null;
        if (marker < data.length)
            fileData = Arrays.copyOfRange(data, marker, data.length);
        
        localDataManager.saveFile(fileName, fileData);
    }

}
