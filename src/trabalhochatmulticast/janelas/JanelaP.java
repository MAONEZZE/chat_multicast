package trabalhochatmulticast.janelas;

import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class JanelaP extends javax.swing.JFrame {
    static PainelChat pChat;
    static PainelLogin pLog;
    
    public JanelaP(InetAddress srvIP, MulticastSocket mtcSock, int srvPort) {
        initComponents();
        BorderLayout bd = new BorderLayout();
        this.setLayout(bd);
        
        pLog = new PainelLogin(srvIP, mtcSock, srvPort);
        
        this.add(pLog, bd.CENTER);
        this.pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
