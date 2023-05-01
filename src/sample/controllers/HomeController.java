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
            resultButton.setVisible(false); // �������� ������������� ������
            testButton.setVisible(false);   // �������� ������ ����
            startTestButton.setVisible(true);   // ��������� ������ START TEST!

        });

        startTestButton.setOnAction(event -> {
            startTestButton.getScene().getWindow().hide(); // ������ ������� �����

            FXMLLoader loader = new FXMLLoader(); // ����� ���������� ��������� ����
            loader.setLocation(getClass().getResource("/sample/view/testJava.fxml")); // ����������� ���� � ����� ������� ����� �������

            try {
                loader.load(); // ������� ���������
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); // ������ ���� � ����� ������� ���������� ���������
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show(); // �������� � ���������
        });

        resultButton.setOnAction(event -> {
            //!!!!!!!!!!!!!!!!!!!!!
        });
    }



}/*            chooseButton.getScene().getWindow().hide(); // ������ ������� �����

            FXMLLoader loader = new FXMLLoader(); // ����� ���������� ��������� ����
            loader.setLocation(getClass().getResource("/sample/view/testJava.fxml")); // ����������� ���� � ����� ������� ����� �������

            try {
                loader.load(); // ������� ���������
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); // ������ ���� � ����� ������� ���������� ���������
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show(); // �������� � ���������*/