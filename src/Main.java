import View.UIMainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Angel on 3/28/17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIMainView.display(primaryStage);
    }
}