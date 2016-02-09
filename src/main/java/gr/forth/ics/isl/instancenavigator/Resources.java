/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.forth.ics.isl.instancenavigator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anyfantis Nikos (nanifant 'at' ics 'dot' forth 'dot' gr)
 */
public class Resources {
    
    private static final String FOLDER_NAME = "/DATA/";
    
    public static String getFirstFileFromFolder(){
        try {
            String path = findRelativePathFromRoot(FOLDER_NAME)+ "/";
            File folder = new File(path);
            String fileName = null;
            for (final File fileEntry : folder.listFiles()) {
                fileName = fileEntry.getName();
            }
            
            return path + "/" + fileName;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    /**
    * This method is used to find relative path from root.
    * @param filepath String file path.
    * @return String This returns full path including input path.
    */
    public static String findRelativePathFromRoot(String filepath) throws UnsupportedEncodingException{
        String path = Resources.class.getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0] + filepath;
        String reponsePath = new File(fullPath).getPath();
        return reponsePath;
    }
}
