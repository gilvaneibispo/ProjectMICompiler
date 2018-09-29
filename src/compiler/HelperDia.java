package compiler;

public class HelperDia {
    
    public static String getData(){
        return "3/10/2018";
    }
    
    public static String getDiaGuardado(){
        return "11/10/2018#2";
    }
    
    public static String getDia(int index){
        
        String rt = "";
        
        switch(index){
            case 1: rt = "Segunda-Feira";
            break;
            case 2: rt = "Terça-Feira";
            break;
            case 3: rt = "Quarta-Feira";
            break;
            case 4: rt = "Quinta-Feira";
            break;
            case 5: rt = "Sexta-Feira";
            break;
            case 6: rt = "Sabado";
            break;
            case 7: rt = "Domingo";
            break;
            
        }
        return rt;
    }
}
