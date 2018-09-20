package analyzer.lexicon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Gilvanei Bispo
 */
public class LexiconAnalyzer {

    private String line;
    private BufferedReader buffer;
    private int line_count;
    private char line_characters[];
    private ArrayList<Token> lexemas;
    private File file;

    public LexiconAnalyzer() throws IOException {
        lexemas = new ArrayList();
        this.line_count = 0;
    }

    public void setFile(File file) throws FileNotFoundException, IOException {

        this.buffer = new BufferedReader(new FileReader(file));
        this.file = file;
    }

    public void init() throws IOException {

        System.out.println(""
                + "\n-------------\n"
                + "Iniciando análise do arquivo \'" + this.file.getName() + "\'!"
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

        this.errorPrinter();
    }

    private boolean isComent() throws IOException {

        StringBuilder string_buffer = new StringBuilder();

        for (int i = 0; i < this.line_characters.length; i++) {

            if (this.line_characters[i] == '/') {
                //analise(buffer, contadorLinha, tokens);
                string_buffer.append("/");
                //int cL = contadorLinha;
                int id_next_item = i + 1;

                if (id_next_item < this.line_characters.length 
                        && this.line_characters[id_next_item] == '*') {
                    boolean encontrou = false;

                    do {

                        for (int k = id_next_item; k < this.line_characters.length; k++) {

                            string_buffer.append(this.line_characters[k]);

                            //Se encontrar um '*/', ou seja, fim do bloco de comentários, breca o wilhe.
                            if (this.line_characters[k] == '*' 
                                    && k + 1 < this.line_characters.length 
                                    && this.line_characters[k + 1] == '/') {

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
                } else if (id_next_item < this.line_characters.length 
                        && this.line_characters[id_next_item] == '/') {
                    
                    for (int k = i + 1; k < this.line_characters.length; k++) {
                        string_buffer.append(this.line_characters[k]);
                    }
                    
                    i = this.line_characters.length;
                }
                
                this.analystMaker(string_buffer, line_count);
            }
        }

        return true;
    }

    private void isString() {

        StringBuilder string_buffer = new StringBuilder();

        for (int i = 0; i < this.line_characters.length; i++) {
            if (this.line_characters[i] == '\"') {

                //verifica se é uma cadeia de caracteres                
                this.analystMaker(string_buffer, line_count);
                string_buffer.append("\"");

                for (int k = i + 1; k < this.line_characters.length; k++) {
                    
                    string_buffer.append(this.line_characters[k]);
                    
                    if (this.line_characters[k - 1] != '\\' && this.line_characters[k] == '\"') { 
                        i = k;
                        break;
                    }
                    i = k;
                }

                this.analystMaker(string_buffer, line_count);
            }
        }
    }

    private void analystMaker(StringBuilder string_buffer, int local_line_count) {

        String lexema = string_buffer.toString();

        if (!lexema.matches("")) {

            if (lexema.matches(Helper.RESERVED_WORD)) {

                lexemas.add(new Token(lexema, "Palavra Reservada", local_line_count));
            } else if (lexema.matches(Helper.IDENTIFIER)) {

                lexemas.add(new Token(lexema, "Identificador", local_line_count));
            } else if (lexema.matches(Helper.NUMBER)) {

                lexemas.add(new Token(lexema, "Número", local_line_count));
            } else if (lexema.matches(Helper.COMMENT_IN_LINE)) {

                lexemas.add(new Token(lexema, "Comentário de Linha", local_line_count));
            } else if (lexema.matches(Helper.BLOCK_COMMENT)) {

                lexemas.add(new Token(lexema, "Comentário de Bloco", local_line_count));
            } else if (lexema.matches(Helper.ARITHMETIC_OPERATOR)) {

                lexemas.add(new Token(lexema, "Operador Aritmético", local_line_count));
            } else if (lexema.matches(Helper.RELATIONAL_OPERATOR)) {

                lexemas.add(new Token(lexema, "Operador Relacional", local_line_count));
            } else if (lexema.matches(Helper.LOGICAL_OPERATOR)) {

                lexemas.add(new Token(lexema, "Operador Lógico", local_line_count));
            } else if (lexema.matches(Helper.DELIMITER)) {

                lexemas.add(new Token(lexema, "Delimitador", local_line_count));
            } else {

                lexemas.add(new Token(lexema, "Expressão não identificada", local_line_count));
            }
        }
    }

    private void errorPrinter() throws IOException {

        int count_error = 0;

        String output_file_path = this.file.getParentFile().getParentFile() + "\\output";
        String output_file_name = "output_lexicon_for_" + this.file.getName();

        FileWriter output = new FileWriter(new File(output_file_path, output_file_name));
        
        try (BufferedWriter bw = new BufferedWriter(output)) {
            
            bw.write("\nIniciando Análise Léxica para " + file.getName() + "\n\n");

            boolean continuar = true;

            for (Token lexema : this.lexemas) {

                bw.write(lexema.toString());
                bw.write("\n--------------------\n");
                bw.newLine();
            }

            if (continuar) {

                bw.write("\nAnalise Léxica concluida, não houve erros apresentados!");
                System.out.println("Análise Léxica do arquivo " + this.file.getName() + " concluida com sucesso!");
            } else {

                bw.write("\nAnalise Léxica concluida, " + count_error + " erro(s) apresentados!");
                System.out.println("Análise Léxica do arquivo " + this.file.getName() + " concluida com erro(s)!");
            }
            
            bw.close();
            System.err.println("Arquivo de saída " + output_file_name + " criado!");
        } catch (Exception e) {
            //Imprimir erro
        }
    }
}
