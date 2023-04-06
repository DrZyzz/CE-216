package com.example.plswork;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    static ArrayList<Translator> translatorsList = new ArrayList<>();


    @FXML
    private TextArea translationText;
    @FXML
    private Label searchWord;

    @FXML
    private Label translation;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addItemButton;

    @FXML
    private Button addTypeButton;

    @FXML
    private Button valueButton;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField itemTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField keyTextField;
    @FXML
    private ListView<String> myListView;
    @FXML
    private Label myLabel;

    String[] languages = {"Turkish","English","Swedish","French","German","Italian","Modern Greek"};
    String currentLanguage;
    /*
    @FXML
    private Label myLabel;

     @FXML
    private ChoiceBox<String> myChoiceBox;
    private String[] tranlator = {"Turkish","English","Swedish","French","German","Italian","Modern Greek"};

     */

    Item item = null;
    List<Item> items = Arrays.<Item>asList();
    TreeItem<String> rootNode = new TreeItem<>("Search History");

    ObservableList<Item> list = FXCollections.observableArrayList();



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        translationText.setEditable(false);

        myListView.getItems().addAll(languages);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentLanguage = myListView.getSelectionModel().getSelectedItem();

                myLabel.setText(currentLanguage);

            }
        });

        /*
        myChoiceBox.getItems().addAll(tranlator);
        myChoiceBox.setOnAction(this::gettranlate);

         */

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

        //tableView.setEditable(true);

//        TableColumn type = new TableColumn("Type");
//        TableColumn name = new TableColumn("Item");

//        tableView.getColumns().addAll(type, name);
//
//        name.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
//        type.setCellValueFactory(new PropertyValueFactory<Item, String>("type"));

        //tableView.setItems(list);



    }

    /*
    public void gettranlate(ActionEvent event){
        String mytranlator = myChoiceBox.getValue();
        myLabel.setText(mytranlator);
    }

     */


    @FXML
    void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
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
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Something went wrong:/");
                    alert.setContentText("You cannot delete the root!");
                    alert.showAndWait();
                }
            }
        });
    }
    // delete ilk tıklandığında çalışmıyor

    @FXML
    void createType() throws IOException {
        if(typeTextField.getText().length() != 0) {
            TreeItem<String> newType = new TreeItem<>(typeTextField.getText());
            rootNode.getChildren().add(newType);
            translate(typeTextField.getText());


            //typeTextField.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION!");
            alert.setHeaderText("Something went wrong:/");
            alert.setContentText("Please enter something.");
            alert.showAndWait();
        }
    }

    @FXML
    void createItem() {
        if (itemTextField.getText().length() != 0) {
            TreeItem<String> newItem = new TreeItem<>(itemTextField.getText());
            TreeItem<String> itemNode = treeView.getSelectionModel().getSelectedItem();
            if (itemNode != null) {
                itemNode.getChildren().add(newItem);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Creation");
                alert.setHeaderText("You have not selected which type you want to add the item to!");
                alert.setContentText("Please try again by selecting the type you want to add the item to.");
                alert.showAndWait();
            }
            itemTextField.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION!");
            alert.setHeaderText("Something went wrong:/");
            alert.setContentText("Please enter something.");
            alert.showAndWait();
        }
    }

    @FXML
    void createKey() {
        TableColumn key = new TableColumn(keyTextField.getText());
        tableView.getColumns().add(key);
        keyTextField.setText("");
    }

    void createValue() {
    }

    @FXML
    void helpText(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Help Menu");
        alert.setHeaderText("Questions?");
        alert.setContentText("You can use the functions of the dictionary via clicking the related buttons" +
                "\n\nYou can type in the field next to Search label then by clicking the Search button you" +
                " will access to the translation of the word.\n\nIf you want to find a synonym of a word all " +
                "you need to do is typing in to the field next to Synonym label.  ");
        alert.showAndWait();

    }

    void synonymFinder(){

    }

    @FXML
    void translate(String word) throws IOException {

        word = word.substring(0, 1).toUpperCase() + word.substring(1);

        searchWord.setText(word + ":");
        word = word.toLowerCase();

        for (Languages source : Languages.values()) {
            for (Languages target : Languages.values()) {
                if (source == target) continue;
                Pair p = new Pair(source, target);
                Translator t = new Translator(p);
                translatorsList.add(t);
            }

        }

        for (Translator t : translatorsList) {
            List<String> definitions = t.getDefinition(word);
            Pair p = t.pair;
            System.out.println(p);
            System.out.println(definitions);

            for (int i = 0; i < definitions.size(); i++) {
                translationText.setText(p.toString().toUpperCase() + "\n" + definitions.toString());
            }

            // this function should be written in gui
            // doWhatYouWantWithDefinitionsInGui(definitions)

        }

        //translationText.setText(def);
        typeTextField.setText("");


    }



}