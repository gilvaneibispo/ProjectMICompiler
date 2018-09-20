package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gilvanei Bispo
 */
public class HandleFileIO {

    private static String path;
    private static final String SETTING_FILE_PATH = "C:\\Users\\Gilvanei\\Documents\\NetBeansProjects\\Compiler\\src\\files\\setting.txt";
    private static final int NUMBER_ROWS_FILE = 2;
    
    public static void setFilePath(String path) {

        HandleFileIO.path = path;
    }

    public static BufferedReader getFileRead() throws FileNotFoundException, IOException {
        if (HandleFileIO.path != null) {
            BufferedReader bf = new BufferedReader(new FileReader(HandleFileIO.path));
            return bf;
        } else {
            System.err.println("Você deve definir um arquivo primeiro!");
            return null;
        }
    }

    public static boolean fileSave(String path, String content) {

        FileWriter file;
        
        try {
            
            file = new FileWriter(path);
            PrintWriter toSaveFile = new PrintWriter(file);

            toSaveFile.printf(content);

            file.close();

            return true;
        } catch (IOException ex) {
            
            Logger.getLogger(HandleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public static void saveConfigFile(String path, boolean value_check) throws IOException {
        
        String dir = "C:\\Users\\Gilvanei\\Desktop\\text.txt";
        String vc = "true";
        
        if(path == null || path.isEmpty())
            dir = path;
        
        vc = (value_check == true ? "true" : "false");
        
        String content = ""
                + dir + "%n"
                + vc + "%n";
        
        HandleFileIO.fileSave(HandleFileIO.SETTING_FILE_PATH, content);
        System.err.println("Arquivo criado com sucesso [HandleFileIO:74]");
    }
    
    public static String[] readConfigFile() throws IOException{
        HandleFileIO.setFilePath(HandleFileIO.SETTING_FILE_PATH);
        BufferedReader buffer = HandleFileIO.getFileRead();
        String[] casa = new String[HandleFileIO.NUMBER_ROWS_FILE];
        
        String line = buffer.readLine();
        for (int i = 0; i < HandleFileIO.NUMBER_ROWS_FILE; i++) {
            System.out.println(line);
            casa[i] = line;
            line = buffer.readLine();
        }
        
        
        return casa;
    }

    private static void test(BufferedReader bf) throws IOException {

        String line = bf.readLine();
        int line_count = 1;
        while (line != null) {
            System.out.println(line);
            line = bf.readLine();
            line_count++;
        }
    }
}
