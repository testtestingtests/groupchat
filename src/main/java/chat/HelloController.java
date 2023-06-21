package chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private TextField loginUsernameTextField;
    FXMLLoader fxmlLoader;

    @FXML
    protected void onLoginButtonClick() {
        ChatController.setUsername(loginUsernameTextField.getText());
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("chat-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("GroupChat Window");
            stage.setScene(scene);
            stage.setOnHidden(e -> {
                try {
                    ((ChatController)fxmlLoader.getController()).disconnect();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close(){
        if(fxmlLoader==null) return;
        try {
            ((ChatController)fxmlLoader.getController()).disconnect();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}