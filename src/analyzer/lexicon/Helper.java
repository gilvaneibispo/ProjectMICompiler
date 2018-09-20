package analyzer.lexicon;

/**
 * @author Gilvanei Bispo
 */
public class Helper {

    //"\\x09|\\x0A|\\x0B|\\x20"
    public static final String RESERVED_WORD = "class|final|if|else|for|scan|print|int|float|bool|true|false|string";
    public static final String IDENTIFIER = "^[a-zA-Z](\\w)*";
    public static final String NUMBER = "[\\-]?(\\d)+(\\.\\d+)?";
    public static final String COMMENT_IN_LINE = "^//.*";
    public static final String BLOCK_COMMENT = "^(/\\*).*(\\*/)$";
    public static final String ARITHMETIC_OPERATOR = "\\+|\\-|\\*|/|%";
    public static final String RELATIONAL_OPERATOR = "\\!\\=|\\=|\\<|\\<\\=|\\>|\\>\\=";
    public static final String LOGICAL_OPERATOR = "\\!|\\&\\&|\\|\\|";
    public static final String DELIMITER = ";|:|,|\\(|\\)|\\[|\\]|\\{|\\}";
    public static final String CHARACTER_CHAIN = "\"[\\x20-\\x21\\x23-\\x7E\\x5C\\x22]*\"";
    public static final String CHARACTER_CHAIN_FAILED = "^\".*";
    public static final String NUMBER_FAILED_1 = "[\\-]?(\\d)+(\\..*)?";
    public static final String NUMBER_FAILED_2 = "[\\-]?(\\.\\d+)?";
    public static final String BLOCK_COMMENT_2 = "^(/\\*).*";
}
