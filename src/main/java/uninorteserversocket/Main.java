/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uninorteserversocket;

/**
 *
 * @author asaad
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int portNumber;
        if(args.length == 0) {
            portNumber = 8989;
        } else {
            portNumber = Integer.parseInt(args[0]);
        }
        new uninorte.
                tcp.
                clientservermodel.
                UninorteServerSocketManager(portNumber);
    }

}
