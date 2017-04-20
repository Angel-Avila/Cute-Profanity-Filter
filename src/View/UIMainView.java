package View;

import Controller.ProfanityChecker;
import Utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Angel on 3/28/17.
 */
public class UIMainView {

    public static void display(Stage primaryStage) {
        Stage window;

        window = primaryStage;
        window.setTitle("Cute Profanity Filter");

        // MARK: - Text area

        TextArea filteredTextTA = new TextArea();
        filteredTextTA.setPromptText("Filtered text will appear here...");
        filteredTextTA.setPrefWidth(450);
        filteredTextTA.setPrefHeight(300);
        filteredTextTA.setMaxWidth(450);
        filteredTextTA.setMaxHeight(300);
        filteredTextTA.setPrefRowCount(10);
        filteredTextTA.setPrefColumnCount(100);
        filteredTextTA.setFont(Font.font("Courier"));

        // MARK: - Labels

        Label selectedFileLbl = new Label("Selected file: ");
        Label fileDirLbl = new Label("/selectedFile/dir/example.txt");

        selectedFileLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        selectedFileLbl.setTextFill(Color.web("#555555"));
        fileDirLbl.setTextFill(Color.web("#bababa"));

        // MARK: - Buttons

        Button selectFileBtn = new Button("Select file");

        selectFileBtn.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(primaryStage);

            if(file != null) {

                String dir = Utils.getShortenedDir(file.getAbsolutePath());

                fileDirLbl.setText(dir);

                String text = Utils.readFile(file)/*.replaceAll("[^A-Za-z\\s]+", "")*/;

                ProfanityChecker.getInstance().unfilteredText = text;

                filteredTextTA.setText(Utils.getTAFormattedString(text));
            }

        });

        Button filterTextBtn = new Button("Filter text");

        filterTextBtn.setOnAction(e -> {

            ProfanityChecker instance = ProfanityChecker.getInstance();
            StringBuilder text = new StringBuilder(instance.unfilteredText);

            while(instance.index < text.length()) {
                String letter = Character.toString(text.charAt(instance.index));

                if (letter.matches("[A-Za-z0-9]"))
                    instance.check(text, letter.toLowerCase());
                else
                    instance.index++;
            }

            instance.cleanPossibleProfanities(text);

            filteredTextTA.setText(Utils.getTAFormattedString(text.toString()));
        });

        // MARK: - Layout

        HBox labelsHBox = new HBox(10);
        labelsHBox.getChildren().addAll(selectedFileLbl, fileDirLbl);
        labelsHBox.setAlignment(Pos.CENTER);

        HBox buttonsHBox = new HBox(100);
        buttonsHBox.getChildren().addAll(selectFileBtn, filterTextBtn);
        buttonsHBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(labelsHBox, buttonsHBox, filteredTextTA);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 415);

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

}
