package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.DataBaseHandler;
import sample.User;


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
    private TextArea boxResultTest;

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button resultButton;


    @FXML
    void initialize() {
        testButton.setOnAction(event -> {
            boxResultTest.setVisible(false);
            resultButton.setVisible(false); // �������� ������������� ������
            testButton.setVisible(false);   // �������� ������ ����
            startTestButton.setVisible(true);   // ��������� ������ START TEST!

        });

        startTestButton.setOnAction(event -> {
            openNewScene("/sample/view/testJava.fxml");
        });

        resultButton.setOnAction(event -> {
            resultButton.setVisible(false);
            testButton.setVisible(false);
            boxResultTest.setVisible(true); // ��������� ��� text Area
            boxResultTest.setText(printResultAllUsers());

        });

        imageButtonHome.setOnMouseClicked(mouseEvent -> { // ��������� �� �������� - ������������ � �������� ��������� �������
            boxResultTest.setVisible(false);
            boxResultTest.setVisible(false);
            resultButton.setVisible(true);
            testButton.setVisible(true);
            startTestButton.setVisible(false);
        });
    }

    public void openNewScene(String window){
        startTestButton.getScene().getWindow().hide(); // ������ ������� �����

        FXMLLoader loader = new FXMLLoader(); // ����� ���������� ��������� ����
        loader.setLocation(getClass().getResource(window)); // ����������� ���� � ����� ������� ����� �������

        try {
            loader.load(); // ������� ���������
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot(); // ������ ���� � ����� ������� ���������� ���������
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show(); // �������� � ���������
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