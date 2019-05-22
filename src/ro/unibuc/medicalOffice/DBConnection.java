package ro.unibuc.medicalOffice;

import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.Drug;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public final class DBConnection {
    private static Connection connection;
    private static DBConnection instance;
    private static final String URL = "jdbc:mysql://localhost";
    private static final String USER = "root";

    private static DoctorDao doctors = new DoctorDao();
    private static DrugDao drugs = new DrugDao();
    private static AppointmentDao appointments = new AppointmentDao();
    private static ReferralDao referrals = new ReferralDao();
    private static SickLeaveCertificateDao sickLeaveCertificates = new SickLeaveCertificateDao();
    private static ExaminationDao examinations = new ExaminationDao();
    private static PrescriptionDao prescriptions = new PrescriptionDao();
    private static PatientDao patients = new PatientDao();
    private static DiagnosisDao diagnoses = new DiagnosisDao();


    private DBConnection() throws SQLException, ClassNotFoundException {
        this.connection = DriverManager.getConnection(URL, USER, null);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException{
        return connection;
    }

    public static Connection getInstance() throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connection = new DBConnection().getConnection();
        }
        String sql = "use medicaloffice";
        Statement st = connection.createStatement();
        st.executeQuery(sql);
        return connection;
    }

    public static void read(String objectType){
        try {
            Connection connection = DBConnection.getInstance();
            objectType = objectType.toLowerCase();
            String sql = "select * from " + objectType;
            //PreparedStatement pst = connection.prepareStatement(sql);
            //pst.setString(1, objectType.toLowerCase());
            //ResultSet rs = pst.executeQuery();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                switch (objectType){
                    case "patients":
                        patients.readPatient(rs.getString("first_name"), rs.getString("last_name"),
                                rs.getString("cnp"), rs.getString("address"), rs.getString("phone_number"));
                        break;
                    case "doctors":
                        doctors.readDoctor(rs.getString("first_name"), rs.getString("last_name"),
                                rs.getString("phone_number"), rs.getString("speciality") == null? "family" : rs.getString("speciality"));
                    break;
                    case "diagnoses":
                        diagnoses.readDiagnosis(rs.getString("description"));
                        break;
                    case "appointments":
                        appointments.readAppointment(rs.getDate("date"), rs.getString("motive"),
                                patients.getPatient(rs.getString("patient_cnp")), doctors.getDoctor(rs.getString("doctor_phone_number")));
                        break;
                    case "drugs":
                        drugs.readDrug(rs.getString("name"), rs.getString("concentration"), rs.getDouble("quantity"));
                        break;
                    case "examinations":
                        examinations.readExamination(rs.getDate("date"), diagnoses.getDiagnosis(rs.getString("diagnosis_description")),
                                doctors.getDoctor(rs.getString("doctor_phone_number")), patients.getPatient(rs.getString("patient_cnp")));
                        break;
                    case "prescriptions":
                        Set<Drug> d = new HashSet<>();
                        String[] medicine = rs.getString("drugs").split(", ");
                        for (String m : medicine) {
                            d.add(drugs.getDrug(m));
                        }
                        prescriptions.readPrescription(rs.getDate("date"), doctors.getDoctor(rs.getString("doctor_phone_number")),
                                patients.getPatient(rs.getString("patient_cnp")), d);
                        break;
                    case "referrals":
                        referrals.readReferral(doctors.getDoctor(rs.getString("from_doctor_phone_number")), doctors.getDoctor(rs.getString("to_doctor_phone_number")),
                                diagnoses.getDiagnosis("diagnosis_description"), rs.getDate("date"), patients.getPatient(rs.getString("patient_cnp")));
                        break;
                    case "sickLeaveCertificates":
                        sickLeaveCertificates.readSickLeaveCertificate(rs.getDate("starting_date"), rs.getString("type"),
                                rs.getInt("number_of_days"), diagnoses.getDiagnosis(rs.getString("diagnosis_description")),
                                doctors.getDoctor(rs.getString("doctor_phone_number")), patients.getPatient(rs.getString("patient_cnp")));
                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write(String objectType, String[] args) {
        try {
            Connection connection = DBConnection.getInstance();
            objectType = objectType.toLowerCase();
            String sql = "insert into " + objectType + "(";
            int idx_date = -1;
            switch (objectType){
                case "appointments":
                    sql = sql + "date, motive, patient_cnp, doctor_phone_number) values(";
                    idx_date = 1;
                    break;
                case "doctors":
                    sql = sql + "phone_number, first_name, last_name, speciality) values(";
                    break;
                case "patients":
                    sql = sql + "cnp, first_name, last_name, address, phone_number) values(";
                    break;
                case "referrals":
                    sql = sql + "from_doctor_phone_number, to_doctor_phone_number, diagnosis_description, date, patient_cnp) values(";
                    idx_date = 4;
                    break;
                case "diagnoses":
                    sql = sql + "description) values(";
                    break;
                case "drugs":
                    sql = sql + "concentration, name, quantity) values(";
                    break;
                case "examinations":
                    sql = sql + "date, diagnosis_description, doctor_phone_number, patient_cnp) values(";
                    idx_date = 1;
                    break;
                case "prescriptions":
                    sql = sql + "drugs, date, doctor_phone_number, patient_cnp) values(";
                    idx_date = 2;
                    break;
                case "sickleavecertificates":
                    sql = sql + "starting_date, type, number_of_days, diagnosis_description, doctor_phone_number, patient_cnp) values(";
                    idx_date = 1;
                    break;
            }
            for (String s:args){
                sql = sql + "?,";
            }
            sql = sql.substring(0, sql.length()-1);
            sql = sql + ")";
            System.out.println(sql);
            PreparedStatement st = connection.prepareStatement(sql);

            for (int i=1; i<= args.length; i++){
                if (idx_date == i){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
                    java.util.Date dateStr = formatter.parse(args[i-1]);
                    java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
                    st.setDate(i, dateDB);
                }
                else {
                    st.setString(i, args[i - 1]);
                }
            }
            st.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DoctorDao getDoctors() {
        return doctors;
    }

    public static String getURL() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static SickLeaveCertificateDao getSickLeaveCertificates() {
        return sickLeaveCertificates;
    }

    public static PrescriptionDao getPrescriptions() {
        return prescriptions;
    }

    public static ReferralDao getReferrals() {
        return referrals;
    }

    public static AppointmentDao getAppointments() {
        return appointments;
    }

    public static ExaminationDao getExaminations() {
        return examinations;
    }

    public static DiagnosisDao getDiagnoses() {
        return diagnoses;
    }

    public static PatientDao getPatients() {
        return patients;
    }

    public static DrugDao getDrugs() {
        return drugs;
    }
}
