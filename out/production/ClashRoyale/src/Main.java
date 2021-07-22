import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("Accounts/View/login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Menu/View/MainMenu.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Menu/View/NewGame.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Menu/View/Deck.fxml"));

        primaryStage.getIcons().add(new Image("Pix/Accounts/icon.png"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));//, 1136, 639));
        primaryStage.show();

        Media menuSound = new Media(getClass().getResource("Audio/Menu.mp3").toExternalForm());
        MediaPlayer musicPlayer = new MediaPlayer(menuSound);
        //musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
        //musicPlayer.setOnReady(musicPlayer::play);
        musicPlayer.play();




    }

    public static void main(String[] args) {
        launch(args);
    }
}