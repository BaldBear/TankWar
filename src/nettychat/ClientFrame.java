package nettychat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame extends Frame {

    public static final ClientFrame INSTANCE = new ClientFrame();

    private TextArea textArea= new TextArea();
    private TextField textField = new TextField();
    private Client c = null;

    private ClientFrame(){
        this.setSize(500,700);
        this.setLocation(100,100);
        this.add(textArea, BorderLayout.CENTER);
        this.add(textField, BorderLayout.SOUTH);
        this.setTitle("Client");
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c.send(textField.getText());
//                textArea.setText(textArea.getText() + textField.getText() + "\n");
                textField.setText("");
            }
        });
    }

    private void connectServer(){
        c= new Client();
        c.connect();
    }

    public void updateText(String str){
        textArea.setText(textArea.getText() + str + "\n");
    }

    public static void main(String[] args) {
        ClientFrame f = ClientFrame.INSTANCE;
        f.setVisible(true);
        f.connectServer();
    }


}
