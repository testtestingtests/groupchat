package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController extends Thread {
    @FXML
    public ListView ChatListView;
    @FXML
    public Button SendButton;
    @FXML
    public TextField ChatTextField;
    @FXML
    public Label LabelText;
    static private String username;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    public static void setUsername(String username) {
        ChatController.username = username;
    }


    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(ChatController.username + " has joined");
            ChatListView.getItems().add("You have joined the group chat");
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                StringBuilder fullmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fullmsg.append(tokens[i]);
                }
                if(cmd.equalsIgnoreCase(ChatController.username))
                    continue;
                ChatListView.getItems().add(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send() {
        String msg = ChatTextField.getText();
        if(msg.isEmpty()) return;
        writer.println(ChatController.username + " : " + msg);
        ChatListView.getItems().add("Me : " + msg);
        ChatTextField.setText("");
    }

    @FXML
    public void initialize(){
        LabelText.setText("You are connected as "+ChatController.username);
        connectSocket();
    }
    public void disconnect() throws IOException {
        writer.println(ChatController.username + " disconnected");
        this.stop();
        reader.close();
        writer.close();
        socket.close();
    }

}

