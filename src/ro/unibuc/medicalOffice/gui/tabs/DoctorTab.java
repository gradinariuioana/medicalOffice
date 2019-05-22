package ro.unibuc.medicalOffice.gui.tabs;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ro.unibuc.medicalOffice.domain.Appointment;
import ro.unibuc.medicalOffice.domain.Referral;
import ro.unibuc.medicalOffice.service.DoctorService;
import ro.unibuc.medicalOffice.service.PatientService;

import javax.print.Doc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.*;

public class DoctorTab extends AbstractAppTab {

    private BorderPane borderPane = new BorderPane();
    private static List<TextField> moretxtfields = new ArrayList<>();

    public DoctorTab() {
        super("Doctor Service");
        this.setClosable(false);

        borderPane.setTop(buildHeader("Doctor Service"));

        displayContent();

        this.setContent(borderPane);
    }

    private void displayContent(){
        VBox contentBox = new VBox();
        contentBox.setPadding(new Insets(20, 40, 20, 40));
        Label welcomingText = new Label("Log in to check your files or a patient's one!");
        welcomingText.setFont(new Font(20));
        contentBox.getChildren().add(welcomingText);

        HBox login = new HBox();
        login.setPadding(new Insets(40, 0, 20, 0));
        Label cnp = new Label("Phone number:");
        cnp.setMinWidth(80);
        cnp.setMaxWidth(200);
        TextField textField = new TextField();
        login.getChildren().addAll(cnp, textField);

        contentBox.getChildren().add(login);

        Button signin = new Button("Sign in!");

        login.setSpacing(20);
        login.getChildren().addAll(signin);

        signin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                while(true){
                    if (textField.getText().length() != 10){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning!");
                        alert.setHeaderText("You cannot sign in");
                        alert.setContentText("The phone number you provided has a wrong format!");
                        alert.show();
                    }
                    else {  //phonenb format ok
                        String id = textField.getText();
                        DoctorService doctorService = new DoctorService(id);
                        if (doctorService.getDoctors().getDoctor(id) != null)
                        {   //the doctor is in the db
                            Label displayMessage = new Label("Hi " + doctorService.getDoctors().getDoctor(id).getFirstName() +
                                    " " + doctorService.getDoctors().getDoctor(id).getLastName() + "!\nHow can we help you today?");
                            displayMessage.setFont(new Font(20));
                            contentBox.getChildren().add(displayMessage);
                            signin.setVisible(false);

                            HBox buttons = new HBox();
                            Button b1 = new Button("Note an examination.");
                            Button b2 = new Button("Check your appointments");
                            Button b3 = new Button("Write a prescription");
                            buttons.setPadding(new Insets(40, 0, 0, 0));
                            buttons.getChildren().addAll(b1, b2, b3);
                            buttons.setSpacing(20);
                            contentBox.getChildren().addAll(buttons);
                            b1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Stage stage = new Stage();
                                    stage.show();
                                    stage.setTitle("Note an examination!");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Note an examination!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(20);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);
                                    Label form1 = new Label("Please provide a date: (yyyy-MM-dd)");
                                    form1.setMinWidth(300);
                                    TextField txt1 = new TextField();
                                    HBox h1 = new HBox();
                                    h1.getChildren().addAll(form1, txt1);
                                    Label form2 = new Label("Now give a diagnosis:");
                                    form2.setMinWidth(300);
                                    TextField txt2 = new TextField();
                                    HBox h2 = new HBox();
                                    h2.getChildren().addAll(form2, txt2);
                                    Label form3 = new Label("Please provide the patient's cnp:");
                                    form3.setMinWidth(300);
                                    TextField txt3 = new TextField();

                                    HBox h3 = new HBox();
                                    h3.getChildren().addAll(form3, txt3);

                                    Button submit1 = new Button("Submit your note!");
                                    content.getChildren().addAll(h1, h2, h3, submit1);

                                    submit1.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            String provided_diagnosis = txt2.getText();
                                            String provided_date = txt1.getText();
                                            String provided_patient_cnp =  txt3.getText();
                                            if (provided_date.equals("") || provided_diagnosis.equals("") || provided_patient_cnp.equals(""))
                                            {
                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                alert.setTitle("Warning!");
                                                alert.setHeaderText("You cannot make the note");
                                                alert.setContentText("You have to complete all the blank spaces!");
                                                alert.show();
                                            }
                                            else {
                                                if (doctorService.getPatients().getPatient(provided_patient_cnp) == null) {
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setTitle("Warning!");
                                                    alert.setHeaderText("You cannot make the note");
                                                    alert.setContentText("The patient is not in our database!");
                                                    alert.show();
                                                }
                                                else
                                                    {
                                                    if (doctorService.getDiagnoses().getDiagnosis(provided_diagnosis) == null) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setTitle("Warning!");
                                                        alert.setHeaderText("The diagnosis is not in the database but it will be added");
                                                        alert.setContentText("The diagnosis is not in our database!");
                                                        alert.show();
                                                    }
                                                    System.out.println(provided_date);
                                                    try {
                                                        doctorService.noteExamination(provided_date, provided_diagnosis, provided_patient_cnp);
                                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("You've just noted an examination!");
                                                        alert.setContentText("Have a nice day!");
                                                        alert.show();
                                                    }
                                                    catch (Exception e){
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("The examination is already noted in our database!");
                                                        alert.setContentText("Have a nice day!");
                                                        alert.show();
                                                    }
                                                    }
                                            }
                                        }
                                });
                                }
                            });
                            b2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {

                                    Stage stage = new Stage();
                                    stage.show();
                                    stage.setTitle(doctorService.getDoctors().getDoctor(id).getFirstName() + " " + doctorService.getDoctors().getDoctor(id).getLastName() + "'s Appointments");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Here's a list of your appointments!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(10);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);

                                    Appointment[] appointments = doctorService.getAppointmentsForDoctor();

                                    Label form1 = new Label("Patient");
                                    form1.setMinWidth(200);
                                    Label form2 = new Label("Date");
                                    HBox h1 = new HBox();
                                    h1.getChildren().addAll(form1, form2);
                                    form1.setFont(new Font(20));
                                    form2.setFont(new Font(20));
                                    h1.setMinHeight(25);
                                    h1.setBackground(new Background(new BackgroundFill(DARKVIOLET, null, null)));
                                    content.getChildren().addAll(h1);
                                    int k = 0;
                                    for (Appointment a : appointments) {
                                        k++;
                                        HBox hbox1 = new HBox();
                                        if (k%2 == 0){
                                            hbox1.setBackground(new Background(new BackgroundFill(CORNSILK, null, null)));
                                        }
                                        Label name = new Label(a.getPatient().getFirstName() + " " + a.getPatient().getLastName());
                                        name.setMinWidth(200);
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Text date = new Text(dateFormat.format(a.getDate()));
                                        hbox1.setMinHeight(20);
                                        name.setFont(new Font(15));
                                        date.setFont(new Font(15));
                                        hbox1.getChildren().addAll(name, date);
                                        content.getChildren().addAll(hbox1);
                                    }
                                }
                            });
                            b3.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {

                                    Stage stage = new Stage();
                                    stage.show();
                                    stage.setTitle("Write a prescription");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Write a prescription!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(20);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);
                                    Label form1 = new Label("Please provide a date: (yyyy-MM-dd)");
                                    form1.setMinWidth(250);
                                    TextField txt1 = new TextField();
                                    HBox h1 = new HBox();
                                    h1.getChildren().addAll(form1, txt1);
                                    Label form2 = new Label("Please provide the patient's cnp:");
                                    form2.setMinWidth(250);
                                    TextField txt2 = new TextField();
                                    HBox h2 = new HBox();
                                    h2.getChildren().addAll(form2, txt2);
                                    Label form3 = new Label("Drug (name, concentration, quantity):");
                                    form3.setMinWidth(250);
                                    TextField txt3 = new TextField();
                                    moretxtfields.add(txt3);
                                    HBox h3 = new HBox();
                                    h3.getChildren().addAll(form3, txt3);
                                    //txt3.setMinWidth(200);

                                    Button plus = new Button("+");
                                    h3.getChildren().addAll(plus);
                                    Button submit = new Button("Submit your note!");
                                    content.getChildren().addAll(h1, h2, h3, submit);
                                    plus.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            HBox hbox = new HBox();
                                            Label auxlabel = new Label("Drug (name, concentration, quantity):");
                                            auxlabel.setMinWidth(250);
                                            TextField auxtxt = new TextField();
                                            moretxtfields.add(auxtxt);
                                            hbox.getChildren().addAll(auxlabel, auxtxt);
                                            h3.getChildren().remove(plus);
                                            hbox.getChildren().addAll(plus);
                                            content.getChildren().remove(submit);
                                            content.getChildren().addAll(hbox, submit);
                                        }
                                    });
                                    submit.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            List<String> provided_drugs = new ArrayList<>();
                                            Boolean not_ok = false;
                                            for (TextField t:moretxtfields){
                                                provided_drugs.add(t.getText());
                                                if (t.getText().equals(""))
                                                    not_ok = true;
                                            }
                                            String provided_date = txt1.getText();
                                            String provided_patient_cnp =  txt2.getText();
                                            if (provided_date.equals("") || not_ok || provided_patient_cnp.equals(""))
                                            {
                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                alert.setTitle("Warning!");
                                                alert.setHeaderText("You cannot make the appointment");
                                                alert.setContentText("You have to complete all the blank spaces!");
                                                alert.show();
                                            }
                                            else {
                                                if (doctorService.getPatients().getPatient(provided_patient_cnp) == null) {
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setTitle("Warning!");
                                                    alert.setHeaderText("You cannot write the prescription");
                                                    alert.setContentText("The patient is not in our database!");
                                                    alert.show();
                                                }
                                                else
                                                {
                                                    try {
                                                        doctorService.writePrescription(provided_date, provided_patient_cnp, provided_drugs);
                                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("You've just written a prescription!");
                                                        alert.setContentText("Have a nice day!");
                                                        alert.show();
                                                    }
                                                    catch (Exception e){
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("The prescription is already written in our database!");
                                                        alert.setContentText("Have a nice day!");
                                                        alert.show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning!");
                            alert.setHeaderText("You cannot use our application");
                            alert.setContentText("You are not a doctor from our database!");
                            alert.show();

                        }
                        break;
                    }
                }
            }
        });
        borderPane.setCenter(contentBox);
    }
}