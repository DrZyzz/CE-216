package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.example.demo3.HelloController.translatorsList;

public class EditController implements Initializable {
    @FXML
    public TextField editWord;
    @FXML
    public TextField editDefinition;

    @FXML
    public TextArea text;

    @FXML
    public ChoiceBox cb1;
    @FXML
    public ChoiceBox cb2;
    @FXML
    public TextField lineOfDef;

    private String[] wordChoices = {"ENG", "TUR", "DEU", "FRA", "SWE", "ITA", "ELL"};
    private String[] definitionChoices = {"ENG", "TUR", "DEU", "FRA", "SWE", "ITA", "ELL"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cb1.getItems().addAll(wordChoices);
        cb2.getItems().addAll(definitionChoices);
        text.setEditable(false);
    }


    @FXML
    public void listDefinition() throws IOException {

        String word = editWord.getText();

        Pair currentChosenPair = new Pair(Pair.getFromString(cb1.getValue().toString()), Pair.getFromString(cb2.getValue().toString()));
        System.out.println(currentChosenPair);
        Translator t = Pair.getTranslatorFromPair(currentChosenPair, translatorsList);
        List<String> definitions = t.getDefinitions(word);

        String s = String.join("\n\n", definitions);
        text.setText(s);

    }

    public void EditDefinition(ActionEvent actionEvent) throws IOException {


        Pair currentChosenPair = new Pair(Pair.getFromString(cb1.getValue().toString()), Pair.getFromString(cb2.getValue().toString()));

        Translator t = Pair.getTranslatorFromPair(currentChosenPair, translatorsList);

        String word = editWord.getText();
        String definition = editDefinition.getText();
        int num = Integer.parseInt(lineOfDef.getText()) -1 ;


        t.editDefinition(word,definition,num);
        editWord.setText("");
        editDefinition.setText("");
        lineOfDef.setText("");
        text.setText("");
    }
}