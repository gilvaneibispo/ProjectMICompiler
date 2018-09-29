package model;

/**
 * @author Gilvanei Bispo
 */
public class Token {
    
    private String name;
    private String description;
    private int line_token;
    
    public Token(String nome, String tipo, int linha) {
        this.name = nome;
        this.description = tipo;
        this.line_token = linha;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLine_token() {
        return line_token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLine_token(int line_token) {
        this.line_token = line_token;
    }
    
    @Override
    public String toString(){
      //Exempo de linha de saída -> String na linha 17: "casa nossa"
        return this.description + " na linha " + this.line_token + ": ≪ " + this.getName() + " ≫";
    }
}
