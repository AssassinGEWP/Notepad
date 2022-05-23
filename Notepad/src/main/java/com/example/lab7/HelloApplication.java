package com.example.lab7;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class HelloApplication extends Application {
    String temp= null;

    @Override
    public void start(Stage primaryStage) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setContentText("Made with pure love by Omar.");
        Menu File = new Menu("File");
        MenuItem New = new MenuItem("New");
        MenuItem Open = new MenuItem("Open");
        MenuItem Save = new MenuItem("Save");
        MenuItem Exit = new MenuItem("Exit");
        File.getItems().addAll(New, Open, Save, Exit);

        Menu Edit = new Menu("Edit");

        MenuItem Undo = new MenuItem("Undo");
        MenuItem Cut = new MenuItem("Cut");
        MenuItem Copy = new MenuItem("Copy");
        MenuItem Paste = new MenuItem("Paste");
        MenuItem Delete = new MenuItem("Delete");
        MenuItem Select = new MenuItem("Select All");



        Edit.getItems().addAll( Undo , Cut , Copy , Paste , Delete,Select );


        Menu Help = new Menu("Help");
        MenuItem About = new MenuItem("About");

        Help.getItems().addAll(About);

        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        About.setOnAction(e -> {
            dialog.showAndWait();
        });

        Exit.setOnAction(e -> {
            Platform.exit();
        });



        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.getMenus().add(File);
        menuBar.getMenus().add(Edit);
        menuBar.getMenus().add(Help);


        VBox top = new VBox();
        top.setSpacing(0);
        top.getChildren().addAll(menuBar);


        TextArea textArea = new TextArea();
        Rectangle rectangle = new Rectangle(0, 0, 600, 650);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, textArea);
        textArea.setStyle("border:0; background-color:transparent; overflow: hidden; border-style:none;");
        rectangle.setFill(Color.color(0.5, 0.5, 0.5));


        Save.setOnAction(e -> {
            save(primaryStage, textArea);
        });


        Open.setOnAction(e -> {
            open(primaryStage, textArea);
        });

        New.setOnAction(e -> {
            textArea.setText("");
        });

        Undo.setOnAction(e -> textArea.undo());

        Delete.setOnAction(e -> textArea.deleteText(textArea.getSelection()));

        Select.setOnAction(e -> textArea.selectAll());

        Copy.setOnAction(e ->  textArea.copy());

        Cut.setOnAction(e -> textArea.cut());

        Paste.setOnAction(e -> textArea.paste());


        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(stackPane);
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setTitle("EditPlus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void open(Stage stage, TextArea textArea) {
        FileChooser fp = new FileChooser();
        fp.setTitle("Choose a file");
        java.io.File file = fp.showOpenDialog(stage);
        if (file != null && file.exists()) {
            try {
                FileInputStream in = new FileInputStream(file);
                byte[] bs = new byte[(int)file.length()];
                in.read(bs);
                textArea.setText(new String(bs));
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static void save(Stage stage, TextArea textArea) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose where to save");
        java.io.File file = fc.showSaveDialog(stage);
        if (file!=null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(textArea.getText().getBytes());
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}