import java.util.Scanner;
import java.util.Stack;

public class App {
    public static Stack <Object> pila = new Stack<>();
        public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);    
        int semilla, a, c, m, operacion;
        System.out.println("1 para el Congruencial Mixto, 2 para el Congruencial Multiplicativo");
        operacion = sc.nextInt();
        System.out.println("Introduzca la semilla!");
        semilla = sc.nextInt();
        System.out.println("Introduzca a");
        a = sc.nextInt();
        System.out.println("Introduzca m");
        m = sc.nextInt();
        switch(operacion){
            case 1:
            System.out.println("Introduzca c");
            c = sc.nextInt();
            mixto(semilla, a, c, m);
                break;
            case 2:
            multiplicativo(semilla, a, m);
                break;
        }
        //System.out.println("Introduzca la semilla!");
        //semilla = sc.nextInt();
        //System.out.println("Introduzca a");
        //a = sc.nextInt();
        //System.out.println("Introduzca b");
        //c = sc.nextInt();
        //System.out.println("Introduzca m");
        //m = sc.nextInt();
//
        //mixto(semilla, a, c, m);
        //multiplicativo(semilla, a, m);
    }
        //Mixto:(a*Xi+c)
    public static void mixto(int semilla, int a, int c, int m){
        float numAleatorio; 
        float xi=semilla;
        boolean repetido=false;
        while(repetido!=true){
            xi = ((a*xi)+c) % m;
            System.out.println(xi); 
            repetido=repetidos(xi);
            numAleatorio=xi/m;
            System.out.println("Número Aleatorio = " + numAleatorio);  
        }
        if (pila.size()==m){
            System.out.println("Periodo de" + m);
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
}
