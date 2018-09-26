package compiler;

import controller.LexiconController;
import java.io.File;

/**
 * @author Gilvanei Bispo
 */
public class Facade {

    private static Facade INSTANCE = null;
    
    private LexiconController lc;
    
    public Facade(){
        this.lc = new LexiconController();
    }
    
    static Facade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Facade();
        }
        return INSTANCE;
    }
    
    public void analyzerLexicon(File file) {
        lc.init(file);
    }
}
