package controller;

import analyzer.lexicon.LexiconAnalyzer;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Gilvanei
 */
public class LexiconController {

    private LexiconAnalyzer la;

    public LexiconController() {
        la = new LexiconAnalyzer();
    }

    public void init(File file) throws IOException {

        if (file != null && file.canRead()) {            
            la.setFile(file);
            la.init();
        } else {
            //Criar exceção com aviso de erro de leitura.
        }
    }
}
