package trabalhochatmulticast;

import trabalhochatmulticast.janelas.JanelaP;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class TrabalhoChatMulticast {

    public static void main(String[] args) {
        InetAddress srvIP = null;
        int srvPort = 0;
        MulticastSocket mtcSock = null;
        
        if(args.length != 2){
            JOptionPane.showMessageDialog(null, "Argumentos inválidos", "ERRO", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        else{
            try {
                srvIP = InetAddress.getByName(args[0]);
                srvPort = Integer.parseInt(args[1]); 
                mtcSock = new MulticastSocket(srvPort);
                
                mtcSock.joinGroup(srvIP); 

            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } 
        }

        JanelaP j = new JanelaP(srvIP, mtcSock, srvPort);
        j.setVisible(true);
        j.setLocationRelativeTo(null);
    }
    
}
