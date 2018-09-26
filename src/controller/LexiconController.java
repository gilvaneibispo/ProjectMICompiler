package controller;

import analyzer.lexicon.LexiconAnalyzer;
import exception.CommentFormException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Gilvanei
 */
public class LexiconController {

    private LexiconAnalyzer la;

    public LexiconController() {
        try {
            la = new LexiconAnalyzer();
        } catch (Exception e) {

        }
    }

    public void init(File file) {

        try {

            if (file != null && file.canRead()) {
                la.setFile(file);
                la.init();
            } else {
                //Criar exceção com aviso de erro de leitura.
            }
        } catch (Exception e) {

        }
    }
}
