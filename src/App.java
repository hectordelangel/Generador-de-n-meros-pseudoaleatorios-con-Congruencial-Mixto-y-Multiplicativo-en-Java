import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static Stack <Object> pila = new Stack<>();
        public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);    
        int semilla, m;
        semilla=94;
        m=100;

        for( int a= 1; a <=100; a++ ){
            for( int c = 1; c <=100; c++){
                mixto(semilla, a, c, m);
            }
        }

    }
        
    public static void mixto(int semilla, int a, int c, int m){
        pila.clear();
        float numAleatorio; 
        float xi=semilla;
        boolean repetido=false;
        while(repetido!=true){
            xi = ((a*xi)+c) % m;
            repetido=repetidos(xi);
            numAleatorio=xi/m;
        }
        if (pila.size()==m){
            
            txt(semilla, a, c , m);
            System.out.println("Periodo de " + m);
        }
        else{
            System.out.println("Periodo incompleto");
        }
              
    }
    
    public static void multiplicativo(int semilla, int a, int m){
        float numAleatorio;
        float xi=semilla;
        boolean repetido=false;
        while(repetido!=true){
            xi = (a * xi) % m;   
            repetido = repetidos(xi);
            numAleatorio = xi/m;

            System.out.println("Número Aleatorio = " + numAleatorio);    
        }
        if (pila.size()==(m/4)){
            System.out.println("Periodo de: " + m/4);
        }
        else{
            System.out.println("Periodo incompleto");
        }
    }

    public static boolean repetidos(float xi){
        if (pila.empty()){
            pila.push(xi);
            return false;
        }else{
            int en_la_pila = pila.search(xi);
            if (en_la_pila < 0){
                pila.push(xi);
                return false;
            }
        }
        
        return true;
    }

    //Genera archivos txt
public static int contador=0;

    public static void txt (int semilla, int a, int c, int m){
        BufferedWriter bw = null;
        FileWriter fw = null;
    
        try {
            contador++;
            String data = contador+".- "+semilla+", " +a+", "+c+", "+m+ "\n";
            File file = new File("C:/Users/2RJ27LA_RS5/Documents/Parámetros/100secuencias.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(data);
            System.out.println("información agregada!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                           
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
            
        
    }

}
