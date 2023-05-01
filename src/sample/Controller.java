package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shake;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authfSignButton;

    @FXML
    private TextField login_filed;

    @FXML
    private Button lognSingUpButton;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        authfSignButton.setOnAction(event -> { // нажимаем на кнопку "войти"
            String loginText = login_filed.getText().trim(); // trim - удаление пробелов со строки
            String loginPassword = password_field.getText().trim();

            if(!loginPassword.equals("") && !loginText.equals(""))
                loginUser(loginText, loginPassword);
            else System.out.println("login or password is empty");
        });

        lognSingUpButton.setOnAction(event -> { // нажимаем на кнопку "регистраци€"
            openNewScene("/sample/signUp.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        try {
            while(result.next()){
                counter++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (counter >= 1) {
            openNewScene("/sample/app.fxml");
        }
        else {
            Shake userloginAnim = new Shake(login_filed);
            Shake userPasswordAnim = new Shake(password_field);
            userloginAnim.playAnim();
            userPasswordAnim.playAnim();
        }
    }

    public void openNewScene(String window){
        lognSingUpButton.getScene().getWindow().hide(); // пр€чем текущую сцену

        FXMLLoader loader = new FXMLLoader(); // нужно отобразить следующее окно
        loader.setLocation(getClass().getResource(window)); // прописываем путь к файлу который хотим открыть

        try {
            loader.load(); // пробуем загрузить
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot(); // полный путь к файлу который необходимо загрузить
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show(); // показать и подождать
    }
}
