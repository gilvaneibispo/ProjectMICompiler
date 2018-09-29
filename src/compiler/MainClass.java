package compiler;

import util.Helper;
import java.io.File;
import java.io.IOException;
import util.Debug;

/**
 * @author Gilvanei Bispo
 */
public class MainClass {

    private static final String DIR = Helper.getURICurrentUser();
    private static Facade FACADE = Facade.getInstance();

    public static void main(String args[]) throws IOException {

        Debug.setDebugOn();

        File base_dir = new File(DIR);
        System.out.println("Diretório: " + base_dir.getName());
        
        
        
System.out.println(System.getProperty("user.dir"));
        /* Verificar tbm se é um caminho válido e se tá vazio */
        if (base_dir.isDirectory() && base_dir.exists()) {

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

                System.out.println("-------------");
                System.out.println("Iniciando análise lexica:");
                System.out.println("-------------");

                for (File file : files) {

                    if (!file.isDirectory() && file.isFile()) {
                        System.out.println("Analisando arquivo " + file.getName() + "...");
                        FACADE.analyzerLexicon(file);
                    }
                }
            } else {

                System.err.println("Não há arquivos para análise no diretório de entrada!");
            }

        } else {

            System.err.println("Verifique o usuário atual e o caminho da pasta de entrada!");
        }
    }
}
