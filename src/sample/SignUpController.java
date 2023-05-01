package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignButton;

    @FXML
    private TextField login_filed;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField signUpGroup;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    void initialize() {
        SignButton.setOnAction(event -> {   // нажимаем на кнопку "Зарегистрироваться"
            signUpNewUser();
            openNewScene("/sample/sample.fxml");

        });
    }

    private void signUpNewUser() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        String firstName = signUpName.getText();
        String lastName = signUpSurname.getText();
        String userName = login_filed.getText();
        String password = password_field.getText();
        String group = signUpGroup.getText();

        User user = new User(firstName, lastName, userName, password, group);

        dbHandler.singUpUser(user);

    }

    public void openNewScene(String window){
        SignButton.getScene().getWindow().hide(); // прячем текущую сцену

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