/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uninorte.tcp.clientservermodel;

import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author asaad
 */
public class UninorteServerSocketManager extends Thread
        implements UninorteClientSocketCaller{

    int port_;
    ServerSocket server_socket_;
    public boolean is_enabled_=true;
    Vector client_sockets_vector_=new Vector();
    int jug=-1;

    
     long tactual=0;
    long tanterior=6000;
    long timeout= 60000; // 60000=1minuto
    
     hilotimeout tm;    
    
 public UninorteServerSocketManager(int port){
        this.port_=port;
        this.start();
        System.out.println("Se ha iniciado EL servidor de Juego de damas en el puerto "+port);
    }

    public void run(){
        try{
            this.server_socket_=new
                    ServerSocket(port_);

            while(is_enabled_){
                
                Socket client_socket=
                        this.server_socket_.accept();
                UninorteClientSocketManager sk=new UninorteClientSocketManager(this,client_socket);
                while(!sk.conectado){
               
                }
                this.client_sockets_vector_.add(sk);
             
                
                sk.SendMessage("Color:"+jug);
                
                System.out.println("Ha entrado el jugador: "+jug);
                jug=jug+2;
                
                tanterior=System.currentTimeMillis();
                if(jug==3){
                UninorteClientSocketManager j1 = (UninorteClientSocketManager)client_sockets_vector_.get(0);
                UninorteClientSocketManager j2 = (UninorteClientSocketManager)client_sockets_vector_.get(1);
                j1.SendMessage("Turno:-1");
                j2.SendMessage("Turno:-1");
                tm=new hilotimeout();                              
                tm.start();
                jug=jug+1;
                }
                
                
                
                
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void MessageHasBeenRecieved(String message) {
       System.out.println("Llego el mensaje: "+message);
         tanterior=System.currentTimeMillis();
        for(int i=0;i<this.client_sockets_vector_.size();i++){
            UninorteClientSocketManager current=
                    (UninorteClientSocketManager)this.
                    client_sockets_vector_.get(i);
            if(current!=null){
                current.SendMessage(message);
            }
        }
    }

    
    
    class hilotimeout extends Thread{

        public hilotimeout() {
           
        }

        @Override
        public void run() {
            while(true){
               tactual=System.currentTimeMillis();
               //System.out.println("Tiempo: "+(tactual-tanterior));
                if((tactual-tanterior)>timeout){
                
                //JOptionPane.showMessageDialog(null, "mensaje de timeout");
                    for(int k =0 ; k < client_sockets_vector_.size(); k++){

                        UninorteClientSocketManager a2 = (UninorteClientSocketManager)client_sockets_vector_.get(k);
                        a2.SendMessage("Mensaje:Tiempo de espera agotado, El juego se va a cerrarse");
                        a2.cerrarYDestruir();

                    }
                System.out.println("TIMEOUT");
                jug=-1;
                    client_sockets_vector_.clear();
                    this.interrupt();
                    break;
                }
            
            }

       
        }


    }    
}








