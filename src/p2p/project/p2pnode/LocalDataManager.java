/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.project.p2pnode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Marino
 */
public class LocalDataManager {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void saveFile(String fileName, byte[] fileBytes) {
        logger.log(Level.SEVERE, fileName);
        logger.log(Level.SEVERE, new String(fileBytes));
        if (fileBytes != null) {
            try {
                saveContentOnDisk(fileName, fileBytes);
            } catch (FileNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            logger.log(Level.SEVERE, "File data is empty.");
        }
    }

    public byte[] getFile(String fileName) {
        logger.log(Level.SEVERE, fileName);
        byte[] fileData = null;
        
        File file = new File(fileName);
        if (file.exists()){
            try {
                fileData = Files.readAllBytes(file.toPath());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return fileData;
    }

    private void saveContentOnDisk(String fileName, byte[] fileBytes) throws FileNotFoundException, IOException {
        String tmpFileName = fileName;
        File file = new File(fileName);
        int i = 1;
        while (!file.exists()) {;
            tmpFileName = fileName + i++;
            file = new File(tmpFileName);
        }

        FileOutputStream output = new FileOutputStream(file);
        output.write(fileBytes);
        output.close();
        logger.log(Level.SEVERE, "File was saved");
    }

}
