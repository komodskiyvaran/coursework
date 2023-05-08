package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button testButton;


    @FXML
    private TextArea boxResultTest;

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button resultButton;

    @FXML
    private Label textview;


    @FXML
    void initialize() {
        firstAttempt();

        testButton.setOnAction(event -> {
            openNewScene("/sample/view/testJava.fxml");

        });

        resultButton.setOnAction(event -> {
            textview.setVisible(true);
            resultButton.setVisible(false);
            testButton.setVisible(false);
            boxResultTest.setVisible(true); // открываем наш text Area
            boxResultTest.setText(printResultAllUsers());

        });

        imageButtonHome.setOnMouseClicked(mouseEvent -> { // нажимаешь на картинку - возвращается в исходное положение менюшки
            textview.setVisible(false);
            boxResultTest.setVisible(false);
            boxResultTest.setVisible(false);
            resultButton.setVisible(true);
            firstAttempt();
        });
    }

    public void firstAttempt(){ // первая ли попытка пройти тест?
        if (Const.USER_NOW.getUserResult() != 0)
            testButton.setVisible(false);
        else testButton.setVisible(true);
    }

    @Override
    public void openNewScene(String window) {
        testButton.getScene().getWindow().hide(); // прячем текущую сцену

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

    public String printResultAllUsers(){
        String resulText = "";
        DataBaseHandler dbHandler = new DataBaseHandler();
        List<User> users = dbHandler.getAllUsers();
        for (User u: users) {
            resulText += u.showUsersResult();
        }
        return resulText;
    }
}