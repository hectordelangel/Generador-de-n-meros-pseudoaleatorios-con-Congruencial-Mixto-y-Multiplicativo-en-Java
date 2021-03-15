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
        }
        pruebaFrecuenciasEvaluacion(valores_intervalos,FE);
    }

    public static void pruebaFrecuenciasEvaluacion(int[] sumaIntervalos, float FE){
        float suma=0;
        for(int i=0; i<sumaIntervalos.length;i++){
            float diferencia= FE-sumaIntervalos[i];
            suma+=Math.pow(diferencia, 2);
        }
        float x02= suma/muestra;
        if(x02<7.81){
            pruebaSeries();
        }else{
            System.out.println("Se rechaza");
        }
    }

    public static void pruebaSeries(){
        float n=2;
        float FE=(float) ((muestra-1)/(Math.pow(n, 2)));
        float num_celdas=4;
        int celdas_pares[]= new int[(int)num_celdas];

        for(int j=0; j<(numerosMuestra.length-1);j++){
            if(numerosMuestra[j]<0.5 && numerosMuestra[(j+1)]<0.5){
                celdas_pares[0]+=1;
            }
            else if(numerosMuestra[j]>0.5 && numerosMuestra[(j+1)]<0.5){
                celdas_pares[1]+=1;
            }
            else if(numerosMuestra[j]<0.5 && numerosMuestra[(j+1)]>0.5){
                celdas_pares[2]+=1;
            }
            else if(numerosMuestra[j]>0.5 && numerosMuestra[(j+1)]>0.5){
                celdas_pares[3]+=1;
            }
        }
        pruebaSeriesEvaluacion(celdas_pares, FE);
    }

    public static void pruebaSeriesEvaluacion(int[] sumaCeldaPares, float FE){
        float suma=0;
        for(int i=0; i<sumaCeldaPares.length;i++){
            float diferencia= sumaCeldaPares[i]-FE;
            suma+=Math.pow(diferencia, 2);
        }
        float x02= suma/FE;
        if(x02<7.81){
            pruebaPoker();
        }else{
            System.out.println("Se rechaza");
        }
    }

    public static void pruebaPoker(){
        String [] digitos= new String[muestra];
        //arreglo con jugadas de poker
        // 0 - quintilla 
        //-1 poker 
        //2- full 
        //3- tercia 
        //4- dos pares 
        //5-par 
        //6-diferentes
        int [] poker = new int [7];

        for (int i=0;i<numerosMuestra.length;i++){
            String numeroCadena = Float.toString(numerosMuestra[i]);
            String [] parts = numeroCadena.split("\\.");    
    
            if(parts[1].length()<5){
                for (int j=parts[1].length();j<5;j++){
                    parts[1]+="0";
                }
            }
            digitos[i]=parts[1].substring(0, 5);
        }
        
        for(int i=0; i<digitos.length;i++){
            String numeroEvaluado=digitos[i];
            int [] conteoIndividual = new int [10];
            for(int j=0;j<numeroEvaluado.length();j++){
                int num=Character.getNumericValue(numeroEvaluado.charAt(j));
                conteoIndividual[num]+=1;
            }
            int contadorTercia = 0, contadorDosPares = 0, diferente = 0;
            for(int j = 0; j < conteoIndividual.length; j++ ){
                //Quintilla
                 if(conteoIndividual[j] == 5){
                    poker[0] += 1;
                }
                //Poker
                else if(conteoIndividual[j] == 4)
                {
                    poker[1] += 1;
                }

                else if(conteoIndividual[j] == 2){
                    contadorDosPares++;
                    //Dos pares
                    if(contadorDosPares == 2){
                        poker[4] += 1;
                        poker[5] -= 1;
                        
                    }
                    //Par
                    else if(contadorDosPares == 1){
                        poker[5] += 1;
                    }
                }
                else if(conteoIndividual[j] == 3){
                    contadorTercia++;
                    //Full
                    if(contadorTercia == 1 && contadorDosPares == 1){
                        poker[2] += 1;
                        poker[3] -= 1;
                        poker[5] -= 1;
                    }
                    //Tercia
                    else if(contadorTercia == 1){
                        poker[3] += 1;
                    }
                }
                if(conteoIndividual[j] <= 1 ){
                    diferente++;
                    if(diferente == 10){
                        poker[6] += 1;
                    }

                }
            }
        }
        //muestra = 20;
        
        double [] p = {0.0001,0.0045,0.009,0.072,0.108,0.504,0.3024};
        double [] valoresChiCuadrada = {3.841,5.991,7.815,9.488,11.070,12.592,14.067, 15.507,16.919,18.307};
        double m = muestra, chiCuadrada = 0;
        int i;
        int numAcumulado=0;
        double FOacumulado = poker[0], FEacumulado =  p[0]* m;
        for(i = 1; i < poker.length; i++){
            if((FOacumulado + poker[i]) < 5){
                FOacumulado += poker[i];
                FEacumulado += p[i] * muestra;
            }
            else{
                numAcumulado = (poker.length - (i-1))+1;
                chiCuadrada += (Math.pow((p[i]*m)-poker[i], 2))/(p[i]*m);
            }

        }
        chiCuadrada += (Math.pow((FEacumulado-FOacumulado), 2))/FEacumulado;
        if( chiCuadrada < valoresChiCuadrada[numAcumulado-2]){
            System.out.println(chiCuadrada);
            System.out.println("No se rechaza");
        }
        else{
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
