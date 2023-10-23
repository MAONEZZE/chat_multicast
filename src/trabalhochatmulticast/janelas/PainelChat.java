package trabalhochatmulticast.janelas;
//Corrigido 10/05/2023 - Formatação para Apresentar no TextArea
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PainelChat extends javax.swing.JPanel {
    private InetAddress srvIP;
    private MulticastSocket mtcSock;
    private int srvPort;
    
    public PainelChat(String nome, InetAddress srvIP, MulticastSocket mtcSock, int srvPort) {
        initComponents();
        this.srvIP = srvIP;
        this.mtcSock = mtcSock;
        lb_nome.setText(nome);
        this.srvPort = srvPort;
        
        ta_mensagens.setEditable(false);
        
        lerMSG();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_mensagens = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        lb_nome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        tf_escreverMsg = new javax.swing.JTextField();
        bt_enviar = new javax.swing.JButton();
        bt_sair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        ta_mensagens.setColumns(20);
        ta_mensagens.setRows(5);
        jScrollPane1.setViewportView(ta_mensagens);

        lb_nome.setText("nome");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Chat");

        tf_escreverMsg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        bt_enviar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_enviar.setText("Enviar");
        bt_enviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_enviarMouseClicked(evt);
            }
        });

        bt_sair.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bt_sair.setText("Sair");
        bt_sair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_sairMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Para sair digite <exit>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lb_nome))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tf_escreverMsg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_enviar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bt_sair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(99, 99, 99))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lb_nome)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_escreverMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_enviar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_sair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bt_sairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_sairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_bt_sairMouseClicked

    private void lerMSG(){
        new Thread(){
            @Override
            public void run() {
                String msg;
                while(true){
                    try {
                        byte[] rxData = new byte[65507];
                        JSONParser jsonP = new JSONParser();
                        DatagramPacket rxPkt = new DatagramPacket(rxData, rxData.length);
                                
                        mtcSock.receive(rxPkt);

                        rxData = rxPkt.getData();
                        msg = new String(rxData);
                        msg = msg.substring(0, rxPkt.getLength());
                        JSONObject json = (JSONObject) jsonP.parse(msg);
                        
                        ta_mensagens.setText(ta_mensagens.getText() + "Date: " + json.get("date") + 
                                "\n" + "Time: " + json.get("time") + 
                                "\n" + "Username: " + json.get("username") + 
                                "\n" + "Message: " + json.get("message") + "\n");
                        
                        ta_mensagens.setText(ta_mensagens.getText() + "\n");
                        

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException ex) {
                        Logger.getLogger(PainelChat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
    private void enviarMSG(String msg){
        new Thread(){
            @Override
            public void run() {
                    try {
                    LocalDateTime agora = LocalDateTime.now();
                    JSONObject json = new JSONObject();
                    byte[] txData = new byte[65507];
                    String txMsg;
                    
                    DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = formatterData.format(agora);

                    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String horaFormatada = formatterHora.format(agora);
                    //=======================
                    
                    if(msg.equalsIgnoreCase("<exit>")){
                        mtcSock.leaveGroup(srvIP);
                        mtcSock.close();
                        System.exit(0);
                    }
                    
                    json.put("date", dataFormatada);
                    json.put("time", horaFormatada);
                    json.put("username", lb_nome.getText());
                    json.put("message", msg);

                    txMsg = json.toString();
                    txData = txMsg.getBytes(StandardCharsets.UTF_8);
                    
                    DatagramPacket txPkt = new DatagramPacket(txData, txData.length, srvIP, srvPort);

                    mtcSock.send(txPkt);
                    
                    
                } catch (SocketException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }
    
    private void bt_enviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_enviarMouseClicked
        String msg = tf_escreverMsg.getText();
        tf_escreverMsg.setText(null);
        enviarMSG(msg);
    }//GEN-LAST:event_bt_enviarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_enviar;
    private javax.swing.JButton bt_sair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_nome;
    private javax.swing.JTextArea ta_mensagens;
    private javax.swing.JTextField tf_escreverMsg;
    // End of variables declaration//GEN-END:variables
}
