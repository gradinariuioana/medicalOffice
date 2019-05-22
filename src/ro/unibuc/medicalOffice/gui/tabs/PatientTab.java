package ro.unibuc.medicalOffice.gui.tabs;

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
import ro.unibuc.medicalOffice.service.PatientService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static javafx.scene.paint.Color.*;

public class PatientTab extends AbstractAppTab {

    private BorderPane borderPane = new BorderPane();
    private static int clicked = 0;
    private static TextField textField2 = new TextField();
    private static TextField textField3 = new TextField();
    private static TextField textField4 = new TextField();
    private static TextField textField5 = new TextField();
    private static Label first_name = new Label("*First Name:");
    private static Label last_name = new Label("*Last Name:");
    private static Label ph = new Label("Phone Number:");
    private static Label address = new Label("*Address:");
    private static VBox form = new VBox();

    public PatientTab() {
        super("Patient Service");
        this.setClosable(false);

        borderPane.setTop(buildHeader("Patient Service"));

        displayContent();

        this.setContent(borderPane);
    }

    private void displayContent(){
        VBox contentBox = new VBox();
        contentBox.setPadding(new Insets(20, 40, 20, 40));
        Label welcomingText = new Label("Log in or sign up to check your files!");
        welcomingText.setFont(new Font(20));
        contentBox.getChildren().add(welcomingText);

        HBox login = new HBox();
        login.setPadding(new Insets(40, 0, 20, 0));
        Label cnp = new Label("C.N.P.");
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
                    if (textField.getText().length() != 13){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning!");
                        alert.setHeaderText("You cannot sign in");
                        alert.setContentText("The cnp you provided has a wrong format!");
                        alert.show();
                    }
                    else {  //cnp format ok
                        String id = textField.getText();
                        PatientService patientService = new PatientService(id);
                        if (patientService.getPatients().getPatient(id) != null)
                        {   //the patient is already in the db
                            Label displayMessage = new Label("Hi " + patientService.getPatients().getPatient(id).getFirstName() +
                                    " " + patientService.getPatients().getPatient(id).getLastName() + "!\nHow can we help you today?");
                            displayMessage.setFont(new Font(20));
                            contentBox.getChildren().add(displayMessage);
                            signin.setVisible(false);

                            HBox buttons = new HBox();
                            Button b1 = new Button("Make an appointment.");
                            Button b2 = new Button("Check your appointments");
                            Button b3 = new Button("Check your referrals");
                            buttons.setPadding(new Insets(40, 0, 0, 0));
                            buttons.getChildren().addAll(b1, b2, b3);
                            buttons.setSpacing(20);
                            contentBox.getChildren().addAll(buttons);
                            b1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Stage stage = new Stage();
                                    stage.show();
                                    stage.setTitle("Make an appointment!");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Make an Appointment!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(20);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);
                                    Label form1 = new Label("Please provide a preferred date: (yyyy-MM-dd)");
                                    form1.setMinWidth(300);
                                    TextField txt1 = new TextField();
                                    HBox h1 = new HBox();
                                    h1.getChildren().addAll(form1, txt1);
                                    Label form2 = new Label("Now give a motive:");
                                    form2.setMinWidth(300);
                                    TextField txt2 = new TextField();
                                    HBox h2 = new HBox();
                                    h2.getChildren().addAll(form2, txt2);
                                    Label form3 = new Label("Please provide the doctor's first name:");
                                    form3.setMinWidth(300);
                                    Label form4 = new Label("Please provide the doctor's last name:");
                                    form4.setMinWidth(300);
                                    TextField txt3 = new TextField();
                                    TextField txt4 = new TextField();

                                    HBox h3 = new HBox();
                                    h3.getChildren().addAll(form3, txt3);
                                    HBox h4 = new HBox();
                                    h4.getChildren().addAll(form4, txt4);

                                    Button submit = new Button("Submit your appointment!");
                                    content.getChildren().addAll(h1, h2, h3, h4, submit);

                                    submit.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            String provided_motive = txt2.getText();
                                            String provided_date = txt1.getText();
                                            String provided_doctor_first =  txt3.getText();
                                            String provided_doctor_last = txt4.getText();
                                            if (provided_date.equals("") || provided_motive.equals("") || provided_doctor_first.equals("") || provided_doctor_last.equals(""))
                                            {
                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                alert.setTitle("Warning!");
                                                alert.setHeaderText("You cannot make the appointment in");
                                                alert.setContentText("You have to complete all the blank spaces!");
                                                alert.show();
                                            }
                                            else {
                                                if (patientService.getDoctors().getDoctor(provided_doctor_first, provided_doctor_last) == null) {
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setTitle("Warning!");
                                                    alert.setHeaderText("You cannot make the appointment in");
                                                    alert.setContentText("The doctor is not in our database!");
                                                    alert.show();
                                                } else {
                                                    System.out.println(provided_date);
                                                    try {
                                                        patientService.makeAppointment(provided_date, provided_motive, provided_doctor_first, provided_doctor_last);
                                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("You've just made an appointment!");
                                                        alert.setContentText("Have a nice day!");
                                                        alert.show();
                                                    }
                                                    catch (Exception e){
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("Congratulations!");
                                                        alert.setHeaderText("The appointment already exists in our database!");
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
                                    stage.setTitle(patientService.getPatients().getPatient(id).getFirstName() + "'s Appointments");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Here's a list of your appointments!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(10);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);

                                    Appointment[] appointments = patientService.getAppointmentsForPatient();

                                    Label form1 = new Label("Doctor");
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
                                        Label name = new Label(a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName());
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
                                    stage.setTitle(patientService.getPatients().getPatient(id).getFirstName() + "'s Referrals");
                                    BorderPane border = new BorderPane();
                                    border.setTop(buildHeader("Here's a list of your referrals!"));
                                    stage.setScene(new Scene(border, 600, 450));

                                    VBox content = new VBox();
                                    content.setSpacing(10);
                                    content.setPadding(new Insets(50));
                                    border.setCenter(content);

                                    Referral[] referrals = patientService.getReferralsForPatient();

                                    Label form1 = new Label("From Doctor");
                                    form1.setMinWidth(200);
                                    Label form2 = new Label("To Doctor");
                                    form2.setMinWidth(200);
                                    Label form3 = new Label("Date");
                                    HBox h1 = new HBox();
                                    h1.getChildren().addAll(form1, form2, form3);
                                    form1.setFont(new Font(20));
                                    form2.setFont(new Font(20));
                                    form3.setFont(new Font(20));
                                    h1.setMinHeight(25);
                                    h1.setBackground(new Background(new BackgroundFill(DARKVIOLET, null, null)));
                                    content.getChildren().addAll(h1);
                                    int k = 0;
                                    for (Referral a : referrals) {
                                        k++;
                                        HBox hbox1 = new HBox();
                                        if (k%2 == 0){
                                            hbox1.setBackground(new Background(new BackgroundFill(CORNSILK, null, null)));
                                        }
                                        Label name = new Label(a.getFromDoctor().getFirstName() + " " + a.getFromDoctor().getLastName());
                                        name.setMinWidth(200);
                                        Label name1 = new Label(a.getToDoctor().getFirstName() + " " + a.getToDoctor().getLastName());
                                        name1.setMinWidth(200);
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Text date = new Text(dateFormat.format(a.getDate()));
                                        hbox1.setMinHeight(20);
                                        name.setFont(new Font(15));
                                        name1.setFont(new Font(15));
                                        date.setFont(new Font(15));
                                        hbox1.getChildren().addAll(name, name1, date);
                                        content.getChildren().addAll(hbox1);
                                    }
                                }
                            });
                        }
                        else        //signup
                            {
                                signin.setText("Sign up!");
                                clicked = clicked + 1;
                                if (clicked == 1) {
                                    form.setSpacing(20);
                                    HBox firstn = new HBox();
                                    first_name.setMinWidth(100);
                                    first_name.setMaxWidth(200);
                                    firstn.getChildren().addAll(first_name, textField2);

                                    HBox lastn = new HBox();
                                    last_name.setMinWidth(100);
                                    last_name.setMaxWidth(200);
                                    lastn.getChildren().addAll(last_name, textField3);

                                    HBox phone = new HBox();
                                    ph.setMinWidth(100);
                                    ph.setMaxWidth(200);
                                    phone.getChildren().addAll(ph, textField4);

                                    HBox add = new HBox();
                                    address.setMinWidth(100);
                                    address.setMaxWidth(200);
                                    add.getChildren().addAll(address, textField5);
                                    form.getChildren().addAll(firstn, lastn, phone, add);
                                    contentBox.getChildren().addAll(form);
                                }
                                else
                                {
                                String fname = textField2.getText();
                                String lname = textField3.getText();
                                String pnumber = textField4.getText();
                                String addre = textField5.getText();
                                if (fname.equals("") || lname.equals("") || addre.equals(""))
                                    {
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Warning!");
                                        alert.setHeaderText("You cannot sign up");
                                        alert.setContentText("You have to complete all the required columns!");
                                        alert.show();
                                    }
                                else
                                    {
                                    patientService.getPatients().addPatient(fname, lname, id, addre, pnumber);
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Congratulation!");
                                    alert.setHeaderText("You are our patient now!");
                                    alert.setContentText("You can now sign in!");
                                    alert.show();
                                    contentBox.getChildren().remove(form);
                                    signin.setText("Sign in!");
                                    }
                                }
                            }
                        break;
                    }
                }
            }
        });
        borderPane.setCenter(contentBox);
    }
}