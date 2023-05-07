package com.example.demo3;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.*;


public class HelloController implements Initializable {

    static ArrayList<Translator> translatorsList = new ArrayList<>();

    @FXML
    private TextArea translationText;

    @FXML
    private TextField synonymTextF;

    @FXML
    private Label searchWord;

    @FXML
    private TreeView<String> treeView;


    @FXML
    private Button deleteButton;

    @FXML
    private TextField typeTextField;

    @FXML
    private ChoiceBox<String> cb;
    @FXML
    private String[] wordChoices = {"ENG", "TUR", "DEU", "FRA", "SWE", "ITA", "ELL"};
    List<Item> items = Arrays.<Item>asList();
    TreeItem<String> rootNode = new TreeItem<>("Search History");


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cb.getItems().addAll(wordChoices);

        translationText.setEditable(false);

        rootNode.setExpanded(true);

        for (Item item : items) {
            TreeItem<String> itemLeaf = new TreeItem<>(item.getName());
            boolean found = false;
            for (TreeItem<String> typeNode : rootNode.getChildren()) {
                if (typeNode.getValue().contentEquals(item.getType())) {
                    typeNode.getChildren().add(itemLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> typeNode = new TreeItem<>(item.getType());
                rootNode.getChildren().add(typeNode);
                typeNode.getChildren().add(itemLeaf);
            }
        }
        treeView.setRoot(rootNode);
        treeView.setEditable(true);
        for (Languages source : Languages.values()) {
            for (Languages target : Languages.values()) {
                if (source == target) continue;
                Pair p = new Pair(source, target);
                Translator t = new Translator(p);
                if (t.pair != null) {
                    translatorsList.add(t);
                }

            }
        }

    }



    @FXML
    void delete(ActionEvent e) {
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem c = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                if (c == null) {
                    return;
                }
                if (c.getParent() != null) {
                    boolean remove = c.getParent().getChildren().remove(c);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Something went wrong:/");
                    alert.setContentText("You cannot delete the root!");
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    void translate() throws IOException {
        if (typeTextField.getText().length() != 0) {
            TreeItem<String> newType = new TreeItem<>(typeTextField.getText());
            rootNode.getChildren().add(newType);
            translate(typeTextField.getText());

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION!");
            alert.setHeaderText("Something went wrong:/");
            alert.setContentText("Please enter something.");
            alert.showAndWait();
        }
    }

    @FXML
    void helpText() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Help Menu");
        alert.setHeaderText("Manual");
        alert.setContentText("You can use the functions of the dictionary by clicking the related buttons." +
                "\n\nTo search for a word, type it in the field next to the Search label, and then click on the Search button to access its translation." +
                "\n\nIf you want to find a synonym for a word, select the language of the word and type it into the field next to the Synonym label. " +
                " Then click on the Search button next to the text field." +
                "\n\nTo add a new definition and its translation, click on Add Definition button. On the new window, select" +
                " the language of the word you want to add and its translation. Then type in the values you want to add and" +
                " click on the Add Definition button.\n\n" +
                "If you want to edit a translation, click on the Edit Definition button. In the new window, select" +
                " the language of the word you want to edit and its translation. To see the definition you want, click on " +
                "the Show Definition button. Then enter the definition you want to change and the number of the definition. " +
                "Finally, click on the Edit Definition button to make changes.  ");
        alert.showAndWait();

    }



    @FXML
    void openAddDefinitionWindow(ActionEvent event) throws IOException {


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("s.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Definition");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void openEditWindow(ActionEvent event) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("t.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Definition");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    @FXML
    public void synonymFinder() throws IOException {

        String synonym;
        synonym = synonymTextF.getText();
        synonym = synonym.substring(0, 1).toUpperCase() + synonym.substring(1);

        searchWord.setText(synonym + ":");
        synonym = synonym.toLowerCase();
        synonymTextF.setText("");

        Languages l = Pair.getFromString(cb.getValue());

        ArrayList<Translator> translatorsList2 = new ArrayList<>();
        for (Translator t : translatorsList) {
            if (t.pair == null) {
                continue;
            }
            if (t.pair.source == l) {
                translatorsList2.add(t);
            }
        }

        StringBuilder result = new StringBuilder();
        for (Translator t : translatorsList2) {
            if (t.pair == null) {
                continue;
            }
            List<String> definition = t.getDefinitions(synonym);
            String joinedDefinition = String.join("", definition);
            definition = List.of(joinedDefinition.split("\n"));
            if (definition.size() <= 1) {
                continue;
            }
            List<String> words = List.of(definition.get(1).split(", "));
            Pair reverse = new Pair(t.pair.target, t.pair.source);
            Translator t2 = Pair.getTranslatorFromPair(reverse, translatorsList);
            if (t2 == null) {
                continue;
            }
            StringBuilder result2 = new StringBuilder();
            for (String word : words) {
                List<String> defs = t2.getDefinitions(word.trim());
                if (defs.size() != 0) {
                    String definitionToShow = String.join("", defs);
                    result2.append(definitionToShow);
                    result2.append("\n");
                }
            }
            result.append(result2);
        }

        translationText.setText(result.toString());

    }

    @FXML
    void translate(String word) throws IOException {

        word = word.substring(0, 1).toUpperCase() + word.substring(1);

        searchWord.setText(word + ":");
        word = word.toLowerCase();


        String output = "";
        for (Translator t : translatorsList) {

            List<String> definitions = t.getDefinitions(word);
            Pair p = t.pair;

            Set<String> c = new TreeSet<String>(definitions);
            c.add(String.valueOf(p));
            if (definitions.size() != 0) {

                output += p.toString().toUpperCase();
                output += "\n" + definitions + "\n" + "\n ";
            }


        }
        translationText.setText(output);

        typeTextField.setText("");


    }


}
