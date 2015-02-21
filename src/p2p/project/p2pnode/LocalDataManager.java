/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marino
 */
public class LocalDataManager {
    
    Logger logger = Logger.getLogger(this.getClass().getName());
    
    public void saveFile(String fileName, byte[] fileBytes){
        logger.log(Level.SEVERE, fileName);
        logger.log(Level.SEVERE, new String(fileBytes));
    }
    
    public byte[] getFile(String fileName){
        logger.log(Level.SEVERE, fileName);
        byte[] fileData = "testFileContent".getBytes();
        return fileData;
    }
    
}
