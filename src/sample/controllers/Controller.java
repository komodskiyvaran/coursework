package sample.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DataBaseHandler;
import sample.OpenScene;
import sample.User;
import sample.animations.Shake;

public class Controller implements OpenScene {

    @FXML   private TextField login_filed;

    @FXML   private PasswordField password_field;

    @FXML   private Button authfSignButton;

    @FXML   private Button loginSingUpButton;


    @FXML
    void initialize() {
        authfSignButton.setOnAction(event -> {
            String loginText = login_filed.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginPassword.equals("") && !loginText.equals(""))
                loginUser(loginText, loginPassword);
            else System.out.println("login or password is empty");
        });

        loginSingUpButton.setOnAction(event -> openNewScene("/sample/view/signUp.fxml"));
    }

    private void loginUser(String loginText, String loginPassword) {

        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try {
            while(result.next()) {
                counter++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (counter!=0) {
            dbHandler.setUserNow(user);
            openNewScene("/sample/view/menu.fxml");
        } else {
            Shake userloginAnim = new Shake(login_filed);
            Shake userPasswordAnim = new Shake(password_field);
            userloginAnim.playAnim();
            userPasswordAnim.playAnim();
        }
    }

    @Override
    public void openNewScene(String window) {
        loginSingUpButton.getScene().getWindow().hide();

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
}
