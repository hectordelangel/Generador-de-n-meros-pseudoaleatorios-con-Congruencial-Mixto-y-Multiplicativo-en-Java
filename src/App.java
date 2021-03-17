import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;
import java.util.Map;
import javax.print.event.PrintEvent;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static int muestra=20;
    public static Stack <Float> pila = new Stack<Float>();
    public static Stack <Float> pilaAleatorios = new Stack<Float>();
    public static Float[] numerosMuestra = new Float[muestra];
    public static Float[] aleatorios;
    public static int incrementar = 0;
    public static int limiteA,limiteB;
    public static boolean pasa = false;
        public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);    
        int semilla, m, a ,c;
        
        // System.out.println("");
        // limiteA=sc.nextInt();
        // System.out.println("");
        // limiteB =sc.nextInt();
        limiteA=1;
        limiteB=-1;
        semilla=94;
        m=32;
        float n=1000;
        int contadorMonteCarlo=0;
        int dentro=0;
        // a=9;
        // c=13;
        // mixto(semilla, a, c, m);
        outerloop:   
        for(a= 1; a <=100; a++ ){
            for(c = 1; c <=100; c++){
                    //Hacemos que se ejecute lo que tenemos en main (lo pasamos a una función)
                    mixto(semilla, a, c, m);
                if (pasa){   
                    // txt(semilla, a, c , m);
                    while(incrementar<m){
                        System.out.println("Montecarlo: "+contadorMonteCarlo);
                        dentro+=monteCarlo();
                        contadorMonteCarlo++;
                        if(contadorMonteCarlo==n){
                            break outerloop;
                        }
                    }
                    pasa=false;
                }
                incrementar = 0;
            }
        }
        // leerTxt();
        System.out.println("dentro: "+dentro);
        float pi=(dentro/n)*4;
        System.out.println("Pi: "+pi);


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
            pruebaPromedio();
        }
        else{
            pasa=false;
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
        aleatorios=new Float[pilaAleatorios.size()];
        while(!pilaAleatorios.empty()){
            if(i < muestra){
                numerosMuestra[i] = pilaAleatorios.peek();
            }
            aleatorios[i]=pilaAleatorios.peek();
            suma=suma+pilaAleatorios.pop();
            i++;
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
            pasa = false;
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
            pasa = false;
            System.out.println("no pasa poker");
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
            pasa = true;
        }
        else{
            pasa = false;
        }   
    }

    public static float Uniforme(int a, int b){
        float x=a+(b-a)*aleatorios[incrementar];
        incrementar++;
        
        // while(){
        //     while(){
                
        //     }
        // }
        // for (;incrementar<x.length;){
        //     //x = a+(b-a)*aleatorios[incrementar];
        //     x[incrementar]=a+(b-a)*aleatorios[incrementar];
        return x;
        
    }

    public static int monteCarlo(){
        int dentro = 0;
        float x=0;
        float y=0;
        float dardo=0;
        x=Uniforme(limiteA, limiteB);
        System.out.println("x: "+x);
        y=Uniforme(limiteA, limiteB);
        System.out.println("y: "+y);
        dardo=(float)Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
        System.out.println("dardo "+dardo);
        if( dardo < 1){
            dentro++;
        }
        return dentro;
        //(detro/n)*4
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
            String data =semilla+","+a+","+c+","+m;
            File file = new File("100secuencias.txt");
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

    public static void leerTxt(){
        String nombreFichero = "100secuencias.txt";
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
           //Crear un objeto BufferedReader al que se le pasa 
           //   un objeto FileReader con el nombre del fichero
           br = new BufferedReader(new FileReader(nombreFichero));
           //Leer la primera línea, guardando en un String
           String texto = br.readLine();
           //Repetir mientras no se llegue al final del fichero
           
           System.out.println(texto);
           while(texto != null)
           {
               String [] parts= texto.split(",");   
               //Hacer lo que sea con la línea leída
               System.out.println(parts[0]);
               //Leer la siguiente línea
               texto = br.readLine();
           }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }

}
