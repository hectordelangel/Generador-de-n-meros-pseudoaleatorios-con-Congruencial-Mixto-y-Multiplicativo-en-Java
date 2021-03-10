import java.util.Scanner;
import java.util.Stack;
import java.util.Map;
import javax.print.event.PrintEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static int muestra=20;
    public static Stack <Float> pila = new Stack<Float>();
    public static Stack <Float> pilaAleatorios = new Stack<Float>();
    public static Float[] numerosMuestra = new Float[muestra];
        public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);    
        int semilla, m, a ,c;
        
        semilla=8;
        m=32;
        a=9;
        c=13;
        mixto(semilla, a, c, m);
        // for( int a= 1; a <=100; a++ ){
        //     for( int c = 1; c <=100; c++){
        //         mixto(semilla, a, c, m);
        //     }
        // }

    }
        
    public static void mixto(int semilla, int a, int c, int m){
        pila.clear();
        pilaAleatorios.clear();
        float numAleatorio; 
        float xi=semilla;
        boolean repetido=false;
        while(repetido!=true){
            xi = ((a*xi)+c) % m;
            numAleatorio=xi/m;
            repetido=repetidos(xi, numAleatorio);
        }
        if (pila.size()==m){
            
            txt(semilla, a, c , m);
            System.out.println("Periodo de " + m);
            pruebaPromedio();

        }
        else{
            System.out.println("Periodo incompleto");
        }
              
    }
     
    public static void pruebaPromedio(){
        float suma=0;
        float ZA = (float)1.96;
        float alpha=(float)0.05;
        float varianza = (float)0.2886751346;
        int n= pilaAleatorios.size();
        float sqrtN = (float) Math.sqrt(n);
        float prom_esperado=(float)0.5;
        int i = 0;
        while(!pilaAleatorios.empty()){
            if(i < muestra){
                
                numerosMuestra[i] = pilaAleatorios.peek();
                i++;
                
            }
            suma=suma+pilaAleatorios.pop();
        }
        float prom_observado=suma/n;
        float Z0 = ((prom_observado - prom_esperado ) * sqrtN)/varianza;
        if(Z0<ZA){
            //Guardar la combinación de números
           pruebaFrecuencias();
        }
    }

    public static void pruebaFrecuencias(){
        float num_intervalos=4;
        float rango=1/num_intervalos;
        float FE=muestra/num_intervalos;
        int valores_intervalos[]= new int[(int)num_intervalos];
        for(int i=0;i<num_intervalos;i++){
           for(int j=0;j<numerosMuestra.length;j++){
               float limite_inferior=rango*i;
               float limite_superior=rango*(i+1);
               float num_evaluado=numerosMuestra[j];
               if(num_evaluado>limite_inferior && num_evaluado<limite_superior){
                   valores_intervalos[i]+= 1;
               }
           }
        }
        for (int i=0;i<valores_intervalos.length;i++){
            System.out.println(valores_intervalos[i]);
        }
        pruebaFrecuenciasEvaluacion(valores_intervalos,FE);
    }

    public static void pruebaFrecuenciasEvaluacion(int[] sumaIntervalos, float FE){
        float suma=0;
        for(int i=0; i<sumaIntervalos.length;i++){
            float diferencia= FE-sumaIntervalos[i];
            suma+=Math.pow(diferencia, 2);
        }
        System.out.println(suma);
        float x02= suma/muestra;
        System.out.println(x02);
        if(x02<7.81){
            System.out.println("Se aprueba");
        }else{
            System.out.println("Se rechaza");
        }

    }


    public static void multiplicativo(int semilla, int a, int m){
        float numAleatorio;
        float xi=semilla;
        boolean repetido=false;
        while(repetido!=true){
            xi = (a * xi) % m;   
            numAleatorio = xi/m;
            repetido = repetidos(xi, numAleatorio);
            System.out.println("Número Aleatorio = " + numAleatorio);    
        }
        if (pila.size()==(m/4)){
            System.out.println("Periodo de: " + m/4);
        }
        else{
            System.out.println("Periodo incompleto");
        }
    }

    public static boolean repetidos(float xi, float numAleatorio){
        if (pila.empty()){
            pila.push(xi);
            pilaAleatorios.push(numAleatorio);
            return false;
        }else{
            int en_la_pila = pila.search(xi);
            if (en_la_pila < 0){
                pilaAleatorios.push(numAleatorio);
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
