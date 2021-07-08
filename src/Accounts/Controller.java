package Accounts;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public TextField email;
    public TextField password;

    @FXML
    public void openSignUp(Event event){
        switchToScene(event, "View/signUp.fxml");
    }

    @FXML
    public void openLogIn(Event event){
        switchToScene(event, "View/logIn.fxml");
    }

    @FXML
    public void openResetPassword(Event event){
        switchToScene(event, "View/resetPassword.fxml");
    }

    @FXML
    public void logIn(Event event){
        System.out.println(event.getEventType() + " on " + ((Button)event.getTarget()).getText());

        User user = Model.tryLogIn(email.getText(), password.getText());
        if (user == null){
            System.out.println(email.getText() + ": " + password.getText() + ": wasn't found.");
            return;
        }
        System.out.println(email.getText() + ": " + password.getText() + ": signed in.");

        // Here we send user to "MainMenu package"
    }

    @FXML
    public void signUp(Event event){
        System.out.println(event.getEventType() + " on " + event.getTarget());

        if(Model.signUp(email.getText(), password.getText())){

        }


    }



    @FXML
    public void confirmEmailToReset(Event event){
        System.out.println(event.getEventType() + " on " + event.getTarget());
        switchToScene(event, "View/resetPassword2.fxml");
    }


    public void switchToScene(Event event, String sceneName){

        System.out.println(event.getEventType() + " on " + event.getTarget());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            /*
            Parent root = new FXMLLoader(getClass().getResource("LogIn.fxml")).load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
             */
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}