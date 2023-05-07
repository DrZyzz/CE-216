package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo3.HelloController.translatorsList;

public class AddController implements Initializable {
        public ChoiceBox cb1;
        public ChoiceBox cb2;
        public TextField itemTextField;
        public TextField itemTextField1;

        private String[] wordChoices = {"ENG", "TUR", "DEU", "FRA", "SWE", "ITA", "ELL"};
        private String[] definitionChoices = {"ENG", "TUR", "DEU", "FRA", "SWE", "ITA", "ELL"};



        @Override
        public void initialize(URL location, ResourceBundle resources) {
                cb1.getItems().addAll(wordChoices);
                cb2.getItems().addAll(definitionChoices);

        }

        @FXML
        void addDefinition() throws IOException {

                String word = itemTextField.getText();
                String definition = itemTextField1.getText();
                itemTextField.setText("");
                itemTextField1.setText("");

                Pair currentChosenPair = new Pair(Pair.getFromString(cb1.getValue().toString()), Pair.getFromString(cb2.getValue().toString()));
                Translator wanted = null;
                System.out.println(currentChosenPair);
                for (Translator t : translatorsList) {
                        if (t.pair != null && t.pair.source == currentChosenPair.source && t.pair.target == currentChosenPair.target) {
                                wanted = t;
                                break;

                        }
                }
                if (wanted != null) {

                        wanted.addDefinition(word,definition);

                }

                else {
                        System.out.println("Coudln't find the transtor for this pair");
                }

        }
}
