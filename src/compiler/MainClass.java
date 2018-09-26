package compiler;

import analyzer.lexicon.Helper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import util.Debug;

/**
 * @author Gilvanei Bispo
 */
public class MainClass {

    private static final String DIR = Helper.isGilvanei();
    private static Facade FACADE = Facade.getInstance();

    public static void main(String args[]) throws IOException {

        Debug.setDebugOn();

        File base_dir = new File(DIR);
        System.out.println("Diretório: " + base_dir.getName());

        /* Verificar tbm se é um caminho válido e se tá vazio */
        if (base_dir.isDirectory()) {

            String output_file_path = base_dir.getParentFile() + "\\output";

            File output_directory = new File(output_file_path);

            /* Remove arquivos de análises antigas */
            if (output_directory.isDirectory()) {

                File[] sun = output_directory.listFiles();

                for (File toDelete : sun) {
                    toDelete.delete();
                }
            }

            File[] files = base_dir.listFiles();

            if (files.length != 0) {
                
                for (File file : files) {
                    
                    if (!file.isDirectory() && file.isFile()) {
                        FACADE.analyzerLexicon(file);
                    }
                }
            } else {
                
                System.err.println("Não há arquivos para análise no diretório de entrada!");
            }

        }else if(!base_dir.exists()){
            
            System.err.println("Verifique o usuário atual e o caminho da pasta de entrada!");
        }
    }
}
