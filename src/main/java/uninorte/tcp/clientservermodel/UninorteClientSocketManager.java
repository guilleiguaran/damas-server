/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uninorte.tcp.clientservermodel;

import java.net.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asaad
 */
public class UninorteClientSocketManager extends Thread{

    Socket client_socket_;
    InputStream input_;
    OutputStream output_;
    UninorteClientSocketCaller caller_;
    boolean conectado=false;
    //MySQLdb db_;

    public UninorteClientSocketManager(
            UninorteClientSocketCaller caller,
            Socket incoming_socket){
        this.caller_=caller;
        this.client_socket_=incoming_socket;
        //this.db_ = new MySQLdb();
        this.start();
        
        
    }

    public void run(){
        try{
            input_=this.client_socket_.getInputStream();
            output_=this.client_socket_.getOutputStream();
            conectado=true;
            int incoming_char=0;
            StringBuffer incoming_message=new StringBuffer();
            while((incoming_char=input_.read())!=-1){
                if(incoming_char=='\n'){
                    
                    ProcessMessage(incoming_message.toString());
                    incoming_message=new StringBuffer();
                    
                }else{
                    incoming_message.append((char)incoming_char);
                }
            }
        }catch(Exception err){
            //err.printStackTrace();
            try {
                this.input_.close();
                this.output_.close();
                this.client_socket_.close();
                
                //err.printStackTrace();
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
    }

    public boolean SendMessage(String message){
        boolean success=true;
        try{
            this.output_.write((message+"\n").getBytes());
        }catch(Exception err){
            success=false;
        }
        return success;
    }

    public void ProcessMessage(String message){
        System.out.println(message);
        /*
        try{
            if(message.split(";").length > 2){
                System.out.println(message);
                this.db_.guardar_coordenadas(message);
            }
        }catch(Exception e){
            
        }
        * 
        */
    
        caller_.MessageHasBeenRecieved(message);
    }


public void cerrarYDestruir(){
        try {
            client_socket_.close();
        } catch (IOException ex) {
            Logger.getLogger(UninorteClientSocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
this.interrupt();

}

public boolean isConected(){

return conectado;
}



}







