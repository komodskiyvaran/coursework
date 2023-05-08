package sample.controllers;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Const;
import sample.DataBaseHandler;
import sample.OpenScene;
import sample.User;


public class HomeController implements OpenScene {

    @FXML   private Button testButton;

    @FXML   private Button resultButton;

    @FXML   private TextArea boxResultTest;

    @FXML   private Label textview;

    @FXML   private ImageView imageButtonHome;


    @FXML
    void initialize() {

        firstAttempt();

        testButton.setOnAction(event -> openNewScene("/sample/view/testJava.fxml"));

        resultButton.setOnAction(event -> {
            resultButton.setVisible(false);
            testButton.setVisible(false);

            textview.setVisible(true);
            boxResultTest.setVisible(true);
            boxResultTest.setText(getResultAllUsers());
        });

        imageButtonHome.setOnMouseClicked(mouseEvent -> {
            textview.setVisible(false);
            boxResultTest.setVisible(false);
            boxResultTest.setVisible(false);

            resultButton.setVisible(true);
            firstAttempt();
        });
    }

    public void firstAttempt() { // Is this the first attempt to pass the test?
        testButton.setVisible(Const.USER_NOW.getUserResult() == 0);
    }

    @Override
    public void openNewScene(String window) {
        testButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public String getResultAllUsers() {
        String resulText = "";
        DataBaseHandler dbHandler = new DataBaseHandler();
        List<User> users = dbHandler.getAllUsers();
        for (User u: users) {
            resulText += u.showUsersResult();
        }
        return resulText;
    }
}