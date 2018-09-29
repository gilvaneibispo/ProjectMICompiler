package util;

/**
 * @author Gilvanei Bispo
 */
public class Helper {

    //"\\x09|\\x0A|\\x0B|\\x20"
    public static final String RESERVED_WORD = "class|const|variables|method|return|main|if|then|else|while|read|write|void|int|float|bool|string|true|false|extends";
    public static final String IDENTIFIER = "^[a-zA-Z](\\w)*";
    public static final String NUMBER = "[\\-]?(\\d)+(\\.\\d+)?";
    public static final String COMMENT_IN_LINE = "^//.*";
    public static final String BLOCK_COMMENT = "^(/\\*).*(\\*/)$";
    public static final String ARITHMETIC_OPERATOR = "\\+|\\-|\\*|/|\\+\\+|\\-\\-";
    public static final String RELATIONAL_OPERATOR = "\\!\\=|\\=|\\<|\\<\\=|\\>|\\>\\=|\\=\\=";
    public static final String LOGICAL_OPERATOR = "\\!|\\&\\&|\\|\\|";
    public static final String DELIMITER = ";|:|,|\\(|\\)|\\[|\\]|\\{|\\}";
    public static final String CHARACTER_CHAIN = "\"[\\x20-\\x21\\x23-\\x7E\\x5C\\x22]*\"";
    public static final String CHARACTER_CHAIN_FAILED = "^\".*";
    public static final String NUMBER_FAILED_1 = "[\\-]?(\\d)+(\\..*)?";
    public static final String NUMBER_FAILED_2 = "[\\-]?(\\.\\d+)?";
    public static final String BLOCK_COMMENT_2 = "^(/\\*).*";
    public static final String TERMINALS = "+|%|*|;|,|(|)|[|]|{|}|:|=|!|9|10|13|32|58";
    
    /* terminais */
    public static boolean isAlgo(char item_char) {
        return item_char == 9
                || item_char == 10
                || item_char == 13
                || item_char == 32
                || item_char == '+'
                || item_char == '%'
                || item_char == '*'
                || item_char == ';'
                || item_char == ','
                || item_char == '('
                || item_char == ')'
                || item_char == '['
                || item_char == ']'
                || item_char == '{'
                || item_char == '}'
                || item_char == ':'
                || item_char == '='
                || item_char == '!'
                || item_char == 58;
    }
        
    public static String getURICurrentUser(){
        
        return System.getProperty("user.dir") + "\\src\\files\\input";
    }
}
