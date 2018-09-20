package compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Gilvanei Bispo
 */
public class MainClass {

    private static final String DIR = "C:\\Users\\Gilvanei.DESKTOP-JB5LH75\\Documents\\NetBeansProjects\\Compiler\\src\\files";
    private static Facade FACADE = Facade.getInstance();;

    public static void main(String args[]) throws IOException {

        File base_dir = new File(DIR);

        if (base_dir.isDirectory()) {
            System.out.println("Diretório: " + base_dir.getName());
            File[] files = base_dir.listFiles();
            //ArrayList tokens = new ArrayList();

            for (File file : files) {
                if (!file.isDirectory() && file.isFile()) {                    
                    FACADE.analyzerLexicon(file);  
                }
            }
        }
    }
}
