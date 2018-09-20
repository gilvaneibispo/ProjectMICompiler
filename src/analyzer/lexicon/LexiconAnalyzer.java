package analyzer.lexicon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Gilvanei
 */
public class LexiconAnalyzer {

    private String line;
    private BufferedReader buffer;
    private int line_count;
    private char line_characters[];
    private String name_file;

    public LexiconAnalyzer() {
        this.line_count = 0;
    }

    public void setFile(File file) throws FileNotFoundException, IOException {

        this.buffer = new BufferedReader(new FileReader(file));
        this.name_file = file.getName();
    }

    public void init() throws IOException {

        System.out.println(""
                + "\n-------------\n"
                + "Iniciando análise do arquivo \'" + this.name_file + "\'!"
                + "\n-------------\n");

        this.line = this.buffer.readLine();

        while (line != null) {
            this.line_characters = this.line.toCharArray();

            //Verifica se existe um comentário iniciado na linha.
            isComent();
            
            //Verifica se existe uma string iniciada na alinha.
            isString();

            this.line_count++;
            this.line = this.buffer.readLine();
        }
    }

    private boolean isComent() throws IOException {

        StringBuilder string_buffer = new StringBuilder();

        for (int i = 0; i < this.line_characters.length; i++) {

            if (this.line_characters[i] == '/') {
                //analise(buffer, contadorLinha, tokens);
                string_buffer = new StringBuilder().append("/");
                //int cL = contadorLinha;
                int id_next_item = i + 1;

                if (id_next_item < this.line_characters.length && this.line_characters[id_next_item] == '*') { //caso seja comentario de bloco
                    boolean encontrou = false;

                    do {

                        for (int k = id_next_item; k < this.line_characters.length; k++) {

                            string_buffer.append(this.line_characters[k]);

                            //Se encontrar um '*/', ou seja, fim do bloco de comentários, breca o wilhe.
                            if (this.line_characters[k] == '*' && k + 1 < this.line_characters.length && this.line_characters[k + 1] == '/') {

                                string_buffer.append('/');
                                encontrou = true;
                                i = k + 1;
                                break;
                            }
                        }

                        //Pulando linha dentro do comentário.
                        if (!encontrou) {
                            this.line = this.buffer.readLine();
                            if (this.line != null) {
                                i = -1;
                                this.line_characters = line.toCharArray();
                                string_buffer.append(" ");
                                //contadorLinha++;
                            } else {
                                //Criar uma exceção dizendo que o bloco não foi fechado
                            }
                        }
                    } while (!encontrou);
                } else if (id_next_item < this.line_characters.length && this.line_characters[id_next_item] == '/') { //caso seja comentario de linha
                    for (int k = i + 1; k < this.line_characters.length; k++) { //termina a linha
                        string_buffer.append(this.line_characters[k]);
                    }
                    i = this.line_characters.length;
                }
                System.out.println("Comentário na linha " + this.line_count + ": " + string_buffer);
            }
        }

        return true;
    }

    private void isString() {

        StringBuilder string_buffer = new StringBuilder();

        for (int i = 0; i < this.line_characters.length; i++) {
            if (this.line_characters[i] == '\"') { //verifica se é uma cadeia de caracteres
                //analise(buffer, contadorLinha, tokens);
                string_buffer.append("\"");

                for (int k = i + 1; k < this.line_characters.length; k++) {
                    string_buffer.append(this.line_characters[k]);
                    if (this.line_characters[k - 1] != '\\' && this.line_characters[k] == '\"') { //verifica se é o fim da cadeia de caracteres
                        i = k;
                        break;
                    }
                    i = k;
                }
                //analise(buffer, contadorLinha, tokens);
                //buffer = new StringBuilder();
                System.out.println("String na linha " + this.line_count + ": " + string_buffer);
            }
        }
    }
}
