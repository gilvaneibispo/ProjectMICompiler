package analyzer.lexicon;

import util.Helper;
import model.Token;
import exception.CommentFormException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import util.Debug;

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
    private int count_error;
    //ArrayList<Token> tokens = new ArrayList();

    public LexiconAnalyzer() throws IOException {
        lexemas = new ArrayList();
        this.line_count = 1;
        this.count_error = 0;
    }

    public void setFile(File file) throws FileNotFoundException, IOException {

        this.buffer = new BufferedReader(new FileReader(file));
        this.file = file;
    }

    public void init() throws IOException, CommentFormException {

        System.out.println(""
                + "\n-------------\n"
                + "Iniciando análise do arquivo \'" + this.file.getName() + "\'!"
                + "\n-------------\n");

        this.line = this.buffer.readLine();

        while (line != null) {
            this.line_characters = this.line.toCharArray();

            this.startLexicalAnalysis();

            this.line_count++;
            this.line = this.buffer.readLine();
        }

        this.errorPrinter();
    }

    private void analystMaker(StringBuilder string_buffer, int local_line_count) {

        String lexema = string_buffer.toString();

        if (!lexema.matches("") && !lexema.matches("\\x09|\\x0A|\\x0B|\\x20")) {

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
            } else if (lexema.matches(Helper.CHARACTER_CHAIN)) {

                lexemas.add(new Token(lexema, "Cadeia de Caracteres", local_line_count));
            } else {

                /*
                Varificar se é numero mal formado!
                 */
                if (lexema.matches(Helper.NUMBER_FAILED_1) || lexema.matches(Helper.NUMBER_FAILED_2)) {

                    this.count_error++;
                    lexemas.add(new Token(lexema, "Erro: Número mal formado", local_line_count));
                } else if (lexema.matches(Helper.CHARACTER_CHAIN_FAILED)) {

                    this.count_error++;
                    lexemas.add(new Token(lexema, "Erro: String mal formado", local_line_count));
                } else if (lexema.matches(Helper.CHARACTER_CHAIN)) {

                } else if (lexema.matches(Helper.CHARACTER_CHAIN)) {

                } else {

                    lexemas.add(new Token(lexema, "Expressão não identificada", local_line_count));
                }
            }
        }
    }

    private void errorPrinter() throws IOException {

        String output_file_path = this.file.getParentFile().getParentFile() + "\\output";
        String output_file_name = "output_lexicon_for_" + this.file.getName();

        //Colocar um if se a pasta não existir criar
        FileWriter output = new FileWriter(new File(output_file_path, output_file_name));

        try (BufferedWriter bw = new BufferedWriter(output)) {

            bw.write("\rIniciando Análise Léxica para " + file.getName() + "\r\n \r\n");

            boolean has_errors = false;

            for (Token lexema : this.lexemas) {

                Debug.println("\n" + lexema.toString());
                Debug.printError("\n--------------------");

                bw.write("\r" + lexema.toString());
                bw.write("\r\n--------------------");
                bw.newLine();
            }

            if (!has_errors) {

                bw.write("\rAnalise Léxica concluida, não houve erros apresentados!");
                System.out.println("Análise Léxica do arquivo " + this.file.getName() + " concluida com sucesso!");
            } else {

                bw.write("\rAnalise Léxica concluida, " + count_error + " erro(s) apresentados!");
                System.out.println("Análise Léxica do arquivo " + this.file.getName() + " concluida com erro(s)!");
            }

            bw.close();
            this.lexemas.clear();
            this.line_count = 1;
            System.err.println("Arquivo de saída " + output_file_name + " criado!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //Imprimir erro
        }
    }

    public void startLexicalAnalysis() throws IOException {

        try {

            StringBuilder string_buffer = new StringBuilder();

            for (int i = 0; i < this.line_characters.length; i++) {

                int id_next_item = i + 1;

                /* Verifocando uma cadeia de caracteres [a string] */
                if (this.line_characters[i] == '\"') {

                    //this.analystMaker(string_buffer, this.line_count);
                    string_buffer.append("\"");

                    for (int k = id_next_item; k < this.line_characters.length; k++) {
                        string_buffer.append(this.line_characters[k]);
                        if (this.line_characters[k - 1] != '\\' && this.line_characters[k] == '\"') {
                            i = k;
                            break;
                        }
                        i = k;
                    }

                    this.analystMaker(string_buffer, this.line_count);
                    //string_buffer = new StringBuilder();
                    //string_buffer.delete(0, string_buffer.length());
                } else if (this.line_characters[i] == '/') {

                    /* Verificando a existencia de comentários */
                    //this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder().append("/");
                    int temp_lc = this.line_count;

                    boolean found_finisher = false;

                    /* Para comentários de blocos */
                    if (id_next_item < this.line_characters.length
                            && this.line_characters[id_next_item] == '*') {

                        do {

                            for (int k = id_next_item; k < this.line_characters.length; k++) {

                                string_buffer.append(this.line_characters[k]);

                                /* busca até achar o '*' seguido da '/' */
                                if (this.line_characters[k] == '*'
                                        && k + 1 < this.line_characters.length
                                        && this.line_characters[k + 1] == '/') {

                                    string_buffer.append('/');
                                    found_finisher = true;
                                    i = k + 1;
                                    break;
                                }
                            }

                            /* caso o cométário continue na proxima linha. */
                            if (!found_finisher) {

                                this.line = buffer.readLine();

                                if (this.line != null) {

                                    i = -1;
                                    this.line_characters = this.line.toCharArray();
                                    this.line_count++;
                                } else {

                                    /* Linha nula é fim de arquivo */
                                    found_finisher = true;
                                }
                            }
                        } while (!found_finisher);
                    } else if (id_next_item < this.line_characters.length
                            && this.line_characters[id_next_item] == '/') {

                        /* Para comentários de linha. */
                        for (int k = id_next_item; k < this.line_characters.length; k++) {

                            string_buffer.append(this.line_characters[k]);
                        }

                        i = this.line_characters.length;
                    }

                    this.analystMaker(string_buffer, temp_lc);
                    string_buffer = new StringBuilder();
                } else if (this.line_characters[i] == '+') {

                    //this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder().append(this.line_characters[i]);

                    if (id_next_item < this.line_characters.length
                            && this.line_characters[id_next_item] == '+') {

                        //string_buffer.append(this.line_characters[id_next_item]);
                        //i++;
                        if(this.buildOperator('+', id_next_item)) i++;
                    }
                } else if (this.line_characters[i] == '-') {

                    //this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder().append('-');

                    /* rever os proximos três blocos de códigos, projetar melhor */
                    for (int k = id_next_item; k < this.line_characters.length; k++) {

                        /* ignora espaço, tab e quebras de linhas */
                        if (this.line_characters[k] != 9
                                && this.line_characters[k] != 10
                                && this.line_characters[k] != 13
                                && this.line_characters[k] != 32) {
                            i = k;
                            break;
                        }
                    }

                    while (id_next_item == this.line_characters.length) {

                        this.line = buffer.readLine();

                        if (this.line != null) {
                            i = -1;
                            this.line_characters = this.line.toCharArray();
                            this.line_count++;
                        } else {
                            break;
                        }

                        for (int k = id_next_item; k < this.line_characters.length; k++) {
                            if (this.line_characters[k] != 9
                                    && this.line_characters[k] != 10
                                    && this.line_characters[k] != 13
                                    && this.line_characters[k] != 32) {
                                i = k;
                                break;
                            }
                        }
                    }

                    StringBuilder buffer_temp = new StringBuilder();

                    for (int k = i; k < this.line_characters.length; k++) {

                        /* Se for dígitos ou ponto */
                        if (this.line_characters[k] > 47
                                && this.line_characters[k] < 58
                                || this.line_characters[k] == '.') {

                            buffer_temp.append(this.line_characters[k]);
                        } else if (Character.toString(this.line_characters[k]).matches(Helper.DELIMITER)) {

                            break;
                        } else if (this.line_characters[id_next_item] == '-') {
                            string_buffer.append('-');
                            i++;
                            //this.analystMaker(string_buffer, this.line_count);
                        } else {
                            buffer_temp = new StringBuilder();
                            break;
                        }
                    }

                    if (i != 0) {
                        i += buffer_temp.length() - 1;
                    }

                    string_buffer.append(buffer_temp);
                    this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder();

                } else if (Helper.isAlgo(this.line_characters[i])) {

                    this.analystMaker(string_buffer, this.line_count);
                    this.analystMaker(new StringBuilder().append(this.line_characters[i]), this.line_count);
                    string_buffer = new StringBuilder();

                } else if (this.line_characters[i] == '<'
                        || this.line_characters[i] == '>'
                        || this.line_characters[i] == '='
                        || this.line_characters[i] == '!') {

                    this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder().append(this.line_characters[i]);

                    if (id_next_item < this.line_characters.length && this.line_characters[id_next_item] == '=') {

                        string_buffer.append(this.line_characters[id_next_item]);
                    }

                    this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder();
                } else if (this.line_characters[i] == '&') {
                    
                    if(this.buildOperator('&', id_next_item)) i++;

                    /* Operador lógico && */
                    //this.analystMaker(string_buffer, this.line_count);
                    /*string_buffer = new StringBuilder().append(this.line_characters[i]);

                    if (id_next_item < this.line_characters.length && this.line_characters[id_next_item] == '&') {

                        string_buffer.append(this.line_characters[id_next_item]);
                        i++;
                    }

                    this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder();*/
                } else if (this.line_characters[i] == '|') {
                    if(this.buildOperator('|', id_next_item)) i++;
                    /* Operador lógico || */
                    //this.analystMaker(string_buffer, this.line_count);
                    /*string_buffer = new StringBuilder().append(this.line_characters[i]);

                    if (id_next_item < this.line_characters.length
                            && this.line_characters[id_next_item] == '|') {

                        string_buffer.append(this.line_characters[id_next_item]);
                        i++;
                    }

                    this.analystMaker(string_buffer, this.line_count);
                    string_buffer = new StringBuilder();*/
                } else {

                    string_buffer.append(this.line_characters[i]);
                }
            }

            this.analystMaker(string_buffer, this.line_count);
            //this.line = buffer.readLine();

        } catch (IOException ex) {
            //Logger.getLogger(ALexico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean buildOperator(char character, int id_next_item) {
        StringBuilder string_buffer = new StringBuilder().append(character);
        boolean rt = false;

        if (id_next_item < this.line_characters.length && this.line_characters[id_next_item] == character) {

            string_buffer.append(this.line_characters[id_next_item]);
            rt = true;
        }

        this.analystMaker(string_buffer, this.line_count);
        return rt;
    }
}
