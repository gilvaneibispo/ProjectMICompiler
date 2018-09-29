package compiler;

public class TesteDia {
    //private String dia_atual;
    //private String dia_guardado;
    //private String mes_atual;
    //private String mes_guardado;
    
    public static void main(String[] args) {
        
        String velha = HelperDia.getDiaGuardado().split("#")[0];
        String atual = HelperDia.getData();
        
        int dia_guardado = Integer.parseInt(velha.split("/")[0]);
        int mes_guardado = Integer.parseInt(velha.split("/")[1]);
        
        int dia_atual = Integer.parseInt(atual.split("/")[0]);
        int mes_atual = Integer.parseInt(atual.split("/")[1]);
        int dia_data = Integer.parseInt(HelperDia.getDiaGuardado().split("#")[1]);
                
        int conta_dia = 0;
        
        while(dia_guardado > dia_atual)
            dia_atual += 10;
        
        int temp_dia = dia_data;
        while (dia_guardado <= dia_atual){
            dia_guardado++;
            temp_dia++;
            if(temp_dia > 7) temp_dia = 1;
        }
        
       temp_dia -= 1;
        
        System.out.println("Guardado: " + HelperDia.getDia(calculaDia(dia_data)) + " - Atual: " + HelperDia.getDia(calculaDia(temp_dia)) + " >> " + temp_dia +calculaDia(temp_dia));
    }
    
    public static int calculaDia(int dia){
        
        //if (dia <= 7) return dia; else return dia/7;
        return dia <= 7 ? dia : dia%7;
    }
}
