package com.lakroft;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static javafx.scene.input.KeyCode.*;

public class Main extends Application {

    private static final List<KeyCode> HOT_KEYS = Arrays.asList(F1, F2, F3, F6, F7, F8);
    private final KamilaPredictor predictor = new KamilaPredictorImpl(HOT_KEYS.size()).loadDictionary();
    private static final int DELIMITER_INDEX = 2;
    private static final char[] nonAlphabetics = "_(){}[].,\\/~`!@#$%^&-=+<>?".toCharArray();
    private List<Character> nextChars = predictor.predict("");

    @Override
    public void start(Stage primaryStage) {
        Label helpLabel = new Label(getHelpText());
        helpLabel.setAlignment(Pos.TOP_CENTER);
        TextArea textArea = new TextArea();

        VBox vBox = new VBox(helpLabel, textArea);
        Scene scene = new Scene(vBox, 300, 275);

        primaryStage.setScene(scene);
        textArea.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            KeyCode code = e.getCode();
            if (HOT_KEYS.contains(code)) {
                int index = HOT_KEYS.indexOf(code);
                if (index > nextChars.size()) return;
                textArea.appendText(String.valueOf(nextChars.get(index)));
            }
            String prefix = getLastWord(textArea.getText());
            nextChars = predictor.predict(prefix.toLowerCase());
            helpLabel.setText(getHelpText());
        });

        primaryStage.setTitle("Kamila Typing");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String getHelpText() {
        StringBuilder headBuilder = new StringBuilder();
        for (int i = 0; i < HOT_KEYS.size(); i++) {
            headBuilder.append(HOT_KEYS.get(i).getName()).append('\t');
            if (i == DELIMITER_INDEX) {
                headBuilder.append('\t');
            }
        }

        StringBuilder tailBuilder = new StringBuilder();
        for (int i = 0; i < nextChars.size(); i++) {
            tailBuilder.append(nextChars.get(i)).append('\t');
            if (i == DELIMITER_INDEX) {
                tailBuilder.append('\t');
            }
        }
        headBuilder.append('\n').append(tailBuilder.toString());
        return headBuilder.toString();
    }

    private String getLastWord(String text) {
        String shText = text.substring(Math.max(text.lastIndexOf(' '), 0));
        int lastIndex = 0;
        for (Character target : nonAlphabetics) {
            int index = shText.lastIndexOf(target);
            if (index > lastIndex) lastIndex = index;
        }
        return shText.substring(lastIndex+1);
    }
}
