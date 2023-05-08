package sample.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Const;
import sample.DataBaseHandler;
import sample.OpenScene;

public class TestController implements OpenScene{
    
    private static List<String> queslist;
    private static List<String> anslist;
    private static final List<String> userAnsList = new ArrayList<>();
    private static final String quesFile = "src\\sample\\assets\\questions.txt";
    private static final String ansFile = "src\\sample\\assets\\answer.txt";
    
    private static long timeOnTest = 20 * 1000 * 60;
    private static int CountQuestionNow = 0;
    private static long startTime;
    private static int testResult = 0;


    @FXML   private TextArea textQuestion;

    @FXML   private RadioButton answerA;

    @FXML   private RadioButton answerB;

    @FXML   private RadioButton answerC;

    @FXML   private RadioButton answerD;

    @FXML   private ToggleGroup answerGroup;

    @FXML   private Button nextButton;

    @FXML   private Button goBackButton;

    @FXML   private Label labelOfResult;

    @FXML   private Label labelJavaKnowledgeAssessment;


    {
        queslist = ReadFile(quesFile);
        anslist = ReadFile(ansFile);

        Date date = new Date();
        startTime = date.getTime();
    }


    @FXML
    void initialize() {

        setTextLableANDAnswerABCD();

            nextButton.setOnAction(event -> {
                if (queslist.size() == CountQuestionNow)
                    showResult();

                else if (time(startTime) && queslist.size() > CountQuestionNow) {

                    if (answerA.isSelected()) userAnsList.add("a");
                    else if (answerB.isSelected()) userAnsList.add("b");
                    else if (answerC.isSelected()) userAnsList.add("c");
                    else if (answerD.isSelected()) userAnsList.add("d");

                    setTextLableANDAnswerABCD();

                } else if(!time(startTime) && testResult !=0)
                    showResult();

            });

            goBackButton.setOnAction(event -> openNewScene("/sample/view/menu.fxml"));
    }

    String splitText(String fullline) {
        String result = "";
        String[] line = fullline.split(" Ч");

        for (String l: line) {
            result += l + "\n";
        }
        return result;
    }

    void setTextLableANDAnswerABCD() {
        textQuestion.setText(splitText(queslist.get(CountQuestionNow)));
        answerA.setText(splitText(queslist.get(CountQuestionNow+1)));
        answerB.setText(splitText(queslist.get(CountQuestionNow+2)));
        answerC.setText(splitText(queslist.get(CountQuestionNow+3)));
        answerD.setText(splitText(queslist.get(CountQuestionNow+4)));

        CountQuestionNow += 5;
    }

    void showResult() {
        answerA.setVisible(false);
        answerB.setVisible(false);
        answerC.setVisible(false);
        answerD.setVisible(false);
        nextButton.setVisible(false);
        textQuestion.setVisible(false);

        labelOfResult.setVisible(true);
        labelJavaKnowledgeAssessment.setVisible(true);
        goBackButton.setVisible(true);


        int count = 0;
        for (int i = 0; i < userAnsList.size(); i++) {
            if (userAnsList.get(i).equals(anslist.get(i)))
                count++;
        }

        testResult = (count*100)/(anslist.size()-1) ;
        labelOfResult.setText("–езультат тестировани€:\n \t" + testResult + " %");

        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        Const.USER_NOW.setDateTime(date);
        Const.USER_NOW.setUserResult(testResult);


        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.setNewResultAndDateForUser(date, testResult);

    }

    static List<String> ReadFile(String path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), Charset.defaultCharset())) {
            list = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(list);
    }

    static Boolean time(long startTime) {
        Date q = new Date();
        long time = q.getTime();
        if ((time - startTime) < timeOnTest)
            return true;
        else {
            System.out.println("¬рем€ вышло.");
            return false;
        }
    }

    @Override
    public void openNewScene(String window) {
        nextButton.getScene().getWindow().hide();

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