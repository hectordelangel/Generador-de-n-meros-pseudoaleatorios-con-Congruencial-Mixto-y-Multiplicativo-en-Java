import java.util.Stack;

import org.w3c.dom.TypeInfo;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

import java.util.List;
import java.util.Stack;

public class VariosProcesos {
    Simulacion sim = new Simulacion(19200);
    sim.run();
    int timeinsystem_engrane_avg = 0;
    int timeinsystem_placa_avg = 0;
    Stack<Integer> engranes = new Stack<Integer>();
    Stack<Integer> placa = new Stack<Integer>();

    for(String piece: sims.exits){
        if(piece.equals("Placa")){
            if(timeinsystem_placa_avg == 1)
                timeinsystem_placa_avg = (timeinsystem_placa_avg+(piece.exit_empacado_time - piece.arrival_time)-timeinsystem_placa_avg)/((placa.size())+1);
            else
                timeinsystem_placa_avg = piece.exit_empacado_time - piece.arrival_time;
            placa.push(piece.exit_empacado_time);
        
        }
    }
        for(String piece: sims.exits){
            if(piece.equals("Engrane")){
                if(timeinsystem_engrane_avg == 1)
                    timeinsystem_engrane_avg = (timeinsystem_engrane_avg+(piece.exit_empacado_time - piece.arrival_time)-timeinsystem_engrane_avg)/((engranes.size())+1);
                else
                    timeinsystem_engrane_avg = piece.exit_empacado_time - piece.arrival_time;
                engranes.push(piece.exit_empacado_time);
            }
            
        }
        System.out.println("Salieron {} engranes".format((engranes.size())));
        System.out.println("Salieron {} placas".format((placas.size())));
        System.out.println("Rechazaron {} engranes".format((sim.rejected_engrane.lenght)));
        System.out.println("Rechazaron {} placas".format((sim.rejected_placa.lenght)));
}


    


class Engrane{
    int id;
    int arrival_time;
    int enter_queque_time;
    int exit_queque_time;
    int enter_supervisor_time;
    int exit_supervisor_time;
    public Engrane(int id){
        this.id = id;
        this.arrival_time = 0;
        this.enter_queque_time =0;
        this.exit_queque_time = 0;
        this.enter_supervisor_time = 0;
        this.exit_supervisor_time = 0;
    }
    //public String Cadena(){
    //    return "Engrane {} entro {6.2f}, rectificado {6.2f}, lavado {6.2f}, empaque {6.2f}".format(
    //        this.id, this.arrival_time, this.exit_rectificado_time, this.exit_lavado_time, this.exit_empacado_time);
    //}
}

class Placa{
    int id;
    int arrival_time;
    int enter_queque_time;
    int exit_queque_time;
    int enter_supervisor_time;
    int exit_supervisor_time;
    public Placa(int id){
        this.id = id;
        this.arrival_time = 0;
        this.enter_queque_time =0;
        this.exit_queque_time = 0;
        this.enter_supervisor_time = 0;
        this.exit_supervisor_time = 0;
    }
    //public String cadena(){
    //    return "Placa {} entro {6.2f}, prensa {6.2f}, lavado {6.2f}, empaque {6.2f}".format(
    //        this.id, this.arrival_time, this.exit_prensa_time, this.exit_lavado_time, this.exit_empacado_time)
    //}

}

class Event{
    int NEW_ENGRANE_ARRIVAL = 1;
    int NEW_PLACA_ARRIVAL = 2;
    int RECTIFICADO_EXIT = 3;
    int PRENSA_EXIT = 4;
    int LAVADO_EXIT_1 = 5;
    int LAVADO_EXIT_2 = 6;
    int EMPACADO_EXIT_1 = 7;
    int EMPACADO_EXIT_2 = 8;
    int time;
    int event_type;
    int piece;

    public Event(int time, int event_type, int piece){
        this.time = time;
        this.event_type = event_type;
        this.piece = piece;
    }


}

class Simulacion{
    int EMPTY = 0;
    int BUSY = 1;
    int FULL = 2;
    int simulation_time = 10000;
    int clock; 
    Stack<Event> events = new Stack<Event>();
    Stack<Object> queue_engrane = new Stack<Object>();
    Stack<Object> queue_placa = new Stack<Object>();
    Stack<Object> rejected_engrane = new Stack<Object>();
    Stack<Object> rejected_placa = new Stack<Object>();
    Stack<Object> exits = new Stack<Object>();
    int empacado_state_1,  empacado_state_2,  lavado_state_1, lavado_state_2, rectificado_state, prensa_state ;

    public Simulacion(int simulation_time){
        this.simulation_time = simulation_time;
        this.clock = 0;
        this.empacado_state_1 = this.EMPTY;
        this.empacado_state_2 = this.EMPTY;
        this.lavado_state_1 = this.EMPTY;
        this.lavado_state_2 = this.EMPTY;
        this.rectificado_state = this.EMPTY;
        this.prensa_state = this.EMPTY;
        this.prepare_entries();
    }

    public double Uniforme(double a, double b){
        double x= a+(b-a)*Math.random();
        return x;  
    }

    public double distNormal(double media, double desvEstandar){
        double distrNormal;
        double x=0;
        double y=0;
        x=Uniforme(0, 1);
        y=Uniforme(0, 1);
        distrNormal = Math.sqrt(-2*Math.log(x))*Math.cos(2*Math.PI*y);
        distrNormal=distrNormal*desvEstandar+media;
        return  distrNormal;
    }
    public double exponencial(double lamda){
        return  -lamda * Math.log(1 - (double) Math.random());
    }

    public void prepare_entries(){
        int time = 0;
        int id = 1;
        while (true){
            time += Uniforme(13, 2);
            Engrane engrane = new Engrane(id);
            id += 1;
            engrane.arrival_time = time;
            Event event;
            event = new Event(time, 1, engrane.id);
            this.events.push(event);
            if (time > this.simulation_time){
                this.events.pop();
                break;
            }
        }
        time = 0;
        id = 1;
        while(true){
            time += exponencial(12);
            Placa placa = new Placa(id);
            id +=1;
            placa.arrival_time = time;
            Event event;
            event = new Event(time, 2, placa.id);
            this.events.push(event);
            if(time > this.simulation_time){
                events.pop();
                break;
            }
        }

    }

    public Object next_event(){
        Event event;
        event = (Event) this.events.pop();
        return event;
    }
    public void run(){
        String pieza_rectificado = "";
        String pieza_prensa = "";
        String pieza_lavado_1 = "";
        String pieza_lavado_2 = "";
        String pieza_empacado_1 = "";
        String pieza_empacado_2 = "";
        String next_piece;
        int iteration = 0, prioridad_engrane = 0;
        while(!this.events.isEmpty()){
            iteration += 1;
            Object event = new Object();
            event = this.next_event();
            this.clock = event.time;
            
            if (even.event_type == event.NEW_ENGRANE_ARRIVAL){
                event.piece.enter_queque_time = this.clock;
                if(this.queue_engrane.size() > 30){
                    this.queue_engrane.push(event.piece);
                }
                else{
                    this.rejected_engrane.push(event.piece);
                }
            }
            else if(event.event_type == event.NEW_PLACA_ARRIVAL){
                event.piece.enter_queque_time = this.clock;
                if(this.queue_placa.size() > 30){
                    this.queue_placa.push(event.piece);
                }
                else{
                    this.rejected_placa.push(event.piece);
                }
            }
            else if(event.event_type == event.RECTIFICADO_EXIT){
                this.rectificado_state = this.FULL;
                event.piece.exit_rectificado_time=this.clock;
                pieza_rectificado = event.piece;
            }
            else if(event.event_type == event.PRENSA_EXIT){
                this.prensa_state = this.FULL;
                event.piece.exit_prensa_time=this.clock;
                pieza_prensa = event.piece;
            }
            else if(event.event_type == event.LAVADO_EXIT_1){
                this.lavado_state_1 = this.FULL;
                event.piece.exit_lavado_time=this.clock;
                pieza_lavado_1 = event.piece;
            }
            else if(event.event_type == event.LAVADO_EXIT_2){
                this.lavado_state_2 = this.FULL;
                event.piece.exit_lavado_time=this.clock;
                pieza_lavado_2 = event.piece;
            }
            else if(event.event_type == event.EMPACADO_EXIT_1){
                this.empacado_state_1 = this.EMPTY;
                event.piece.exit_empacado_time=this.clock;
                this.exits.append(event.piece);
            }
            else if(event.event_type == event.EMPACADO_EXIT_2){
                this.empacado_state_2 = this.EMPTY;
                event.piece.exit_empacado_time=this.clock;
                this.exits.append(event.piece);
            }

            if(this.empacado_state_1 == this.EMPTY && this.lavado_state_1 == this.FULL){
                this.empacado_state_1 = this.BUSY;
                this.lavado_state_1 = this.EMPTY;
                next_piece = pieza_lavado_1;
                if (next_piece.equals("Engrane")){
                    double busy_time = Uniforme(4.0, 6.0) + exponencial(3);
                }
                else{
                    double busy_time = Uniforme(5.0, 9.0) + exponencial(3);
                }
                Event ev;
                this.events.push(ev = new Event(this.busytime, 7, next_piece));
                
            }
            if(this.empacado_state_1 == this.EMPTY && this.lavado_state_2 == this.FULL){
                this.empacado_state_1 = this.BUSY;
                this.lavado_state_1 = this.EMPTY;
                next_piece = pieza_lavado_2;
                if(next_piece.equals("Engrane")){
                    double busy_time = Uniforme(4.0, 6.0) + exponencial(3);
                }
                else{
                    double busy_time = Uniforme(5.0, 9.0) + exponencial(3);
                }
                Event ev;
                this.events.push(ev = new Event(this.busytime, 7, next_piece));


            }
            if(this.empacado_state_2 == this.EMPTY && this.lavado_state_1 == this.FULL){
                this.empacado_state_2 = this.BUSY;
                this.lavado_state_1 = this.EMPTY;
                next_piece = pieza_lavado_1;
                if(next_piece.equals("Engrane")){
                    double busy_time = Uniforme(4.0, 6.0) + exponencial(3);
                }
                else{
                    double busy_time = Uniforme(5.0, 9.0) + exponencial(3);
                }
                Event ev;
                this.events.push(ev = new Event(this.busytime, 8, next_piece));
                

            }
            if(this.empacado_state_2 == this.EMPTY && this.lavado_state_2 == this.FULL){
                this.empacado_state_2 = this.BUSY;
                this.lavado_state_2 = this.EMPTY;
                next_piece = pieza_lavado_2;
                if(next_piece.equals("Engrane")){
                    double busy_time = Uniforme(4.0, 6.0) + exponencial(3);
                }
                else{
                    double busy_time = Uniforme(5.0, 9.0) + exponencial(3);
                }
                Event ev;
                this.events.push(ev = new Event(this.busytime, 8, next_piece));
                

            }
            if(this.lavado_state_1 == this.EMPTY && (this.rectificado_state == this.FULL || this.prensa_state == this.FULL)){
                if (this.rectificado_state == this.FULL && this.prensa_state == this.FULL){
                   int prioridad_negrane = pieza_rectificado.exit_rectificado_time > pieza_prensa.exit_prensa_time;
                }
                else if(this.rectificado_state == this.FULL){
                    prioridad_engrane = 1;
                }
                else{
                    prioridad_engrane = 0;
                }
                this.lavado_state_1 = this.BUSY;
                if(prioridad_engrane == 1 && this.rectificado_state == this.FULL){
                    this.rectificado_state = this.EMPTY;
                    next_piece = pieza_rectificado;
                    pieza_rectificado = "";
                }
                else {
                    this.lavado_state_1 = this.BUSY;
                    this.prensa_state = this.EMPTY;
                    next_piece = pieza_prensa;
                    pieza_rectificado = "";
                }
                int busy_time = 10 + exponencial(3);
                next_piece.enter_lavado_time = this.clock;

            }
            if(this.lavado_state_1 == this.EMPTY && (this.rectificado_state == this.FULL || this.prensa_state == this.FULL)){
                if (this.rectificado_state == this.FULL && this.prensa_state == this.FULL){
                   int prioridad_negrane = pieza_rectificado.exit_rectificado_time > pieza_prensa.exit_prensa_time;
                }
                else if(this.rectificado_state == this.FULL){
                    prioridad_engrane = 1;
                }
                else{
                    prioridad_engrane = 0;
                }
                this.lavado_state_1 = this.BUSY;
                if(prioridad_engrane == 1 && this.rectificado_state == this.FULL){
                    this.rectificado_state = this.EMPTY;
                    next_piece = pieza_rectificado;
                    pieza_rectificado = "";
                }
                else {
                    this.lavado_state_1 = this.BUSY;
                    this.prensa_state = this.EMPTY;
                    next_piece = pieza_prensa;
                    pieza_rectificado = "";
                }
                int busy_time = 10 + exponencial(3);
                next_piece.enter_lavado_time = this.clock;

            }
            
        }
            
        }
    }
