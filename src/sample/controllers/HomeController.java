package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button testButton;

    @FXML
    private Button startTestButton;


    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button resultButton;


    @FXML
    void initialize() {
        testButton.setOnAction(event -> {
            resultButton.setVisible(false); // скрываем результрующую кнопку
            testButton.setVisible(false);   // скрываем кнопку тест
            startTestButton.setVisible(true);   // открываем кнопку START TEST!

        });

        startTestButton.setOnAction(event -> {
            startTestButton.getScene().getWindow().hide(); // пр€чем текущую сцену

            FXMLLoader loader = new FXMLLoader(); // нужно отобразить следующее окно
            loader.setLocation(getClass().getResource("/sample/view/testJava.fxml")); // прописываем путь к файлу который хотим открыть

            try {
                loader.load(); // пробуем загрузить
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); // полный путь к файлу который необходимо загрузить
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show(); // показать и подождать
        });

        resultButton.setOnAction(event -> {
            //!!!!!!!!!!!!!!!!!!!!!
        });
    }



}/*            chooseButton.getScene().getWindow().hide(); // пр€чем текущую сцену

            FXMLLoader loader = new FXMLLoader(); // нужно отобразить следующее окно
            loader.setLocation(getClass().getResource("/sample/view/testJava.fxml")); // прописываем путь к файлу который хотим открыть

            try {
                loader.load(); // пробуем загрузить
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); // полный путь к файлу который необходимо загрузить
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show(); // показать и подождать*/