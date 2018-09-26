package util;

/**
 * @author Gilvanei Bispo
 */
public class Debug {

    public static boolean DEBUG = false;

    public static void println(Object str) {
        if (DEBUG) {
            System.out.println(str);
        }
    }
    
    public static void printError(Object str) {
        if (DEBUG) {
            System.err.println(str);
        }
    }

    public static void setDebugOn() {
        DEBUG = true;
    }

    public static void setDebugOff() {
        DEBUG = false;
    }
}
