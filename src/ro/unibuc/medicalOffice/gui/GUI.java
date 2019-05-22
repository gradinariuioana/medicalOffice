package ro.unibuc.medicalOffice.gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.unibuc.medicalOffice.gui.tabs.DoctorTab;
import ro.unibuc.medicalOffice.gui.tabs.PatientTab;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        setStageParams(stage);
        Scene scene = createMainScene(stage);

        stage.show();
    }

    private void setStageParams(Stage stage) {
        stage.setTitle("Medical Office Database");
        stage.centerOnScreen();
    }

    private Scene createMainScene(Stage stage) {
        TabPane tabs = new TabPane();

        tabs.getTabs().add(new PatientTab());

        tabs.getTabs().add(new DoctorTab());

        Scene scene = new Scene(tabs, 600, 450);
        scene.setFill(Color.PALEVIOLETRED);
        stage.setScene(scene);
        return scene;
    }

    public static void main(String []args) {
        launch(args);
    }
}