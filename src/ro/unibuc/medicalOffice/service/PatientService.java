package ro.unibuc.medicalOffice.service;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.*;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PatientService {

    private PatientDao patients = new PatientDao();
    private DoctorDao doctors = new DoctorDao();
    private AppointmentDao appointments = new AppointmentDao();
    private ReferralDao referrals;
    private SickLeaveCertificateDao sickLeaveCertificates;
    private PrescriptionDao prescriptions;
    private ExaminationDao examinations;
    private DrugDao drugs;
    private DiagnosisDao diagnoses;
    private Connection connection;
    private String cnp;

    public PatientService(String cnp){
        /*
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        readerWriter.readCsv("Doctors");
        DoctorDao doctors = readerWriter.getDoctors();
        setDoctors(doctors);
        readerWriter.readCsv("Patients");
        PatientDao patients = readerWriter.getPatients();
        setPatients(patients);
        readerWriter.readCsv("Drugs");
        DrugDao drugs = readerWriter.getDrugs();
        setDrugs(drugs);
        readerWriter.readCsv("Diagnoses");
        DiagnosisDao diagnoses = readerWriter.getDiagnoses();
        setDiagnoses(diagnoses);
        readerWriter.readCsv("Appointments");
        AppointmentDao appointments = readerWriter.getAppointments();
        setAppointments(appointments);
        readerWriter.readCsv("Referrals");
        ReferralDao referrals = readerWriter.getReferrals();
        setReferrals(referrals);
        readerWriter.readCsv("SickLeaveCertificates");
        SickLeaveCertificateDao sickLeaveCertificates = readerWriter.getSickLeaveCertificates();
        setSickLeaveCertificates(sickLeaveCertificates);
        readerWriter.readCsv("Prescriptions");
        PrescriptionDao prescriptions = readerWriter.getPrescriptions();
        setPrescriptions(prescriptions);
        readerWriter.readCsv("Examinations");
        ExaminationDao examinations = readerWriter.getExaminations();
        setExaminations(examinations);
        */
        try {
            connection = DBConnection.getInstance();
            DBConnection.read("Doctors");
            DoctorDao doctors = DBConnection.getDoctors();
            setDoctors(doctors);
            DBConnection.read("Patients");
            PatientDao patients = DBConnection.getPatients();
            setPatients(patients);
            DBConnection.read("Drugs");
            DrugDao drugs = DBConnection.getDrugs();
            setDrugs(drugs);
            DBConnection.read("Diagnoses");
            DiagnosisDao diagnoses = DBConnection.getDiagnoses();
            setDiagnoses(diagnoses);
            DBConnection.read("Appointments");
            AppointmentDao appointments = DBConnection.getAppointments();
            setAppointments(appointments);
            DBConnection.read("Referrals");
            ReferralDao referrals = DBConnection.getReferrals();
            setReferrals(referrals);
            DBConnection.read("SickLeaveCertificates");
            SickLeaveCertificateDao sickLeaveCertificates = DBConnection.getSickLeaveCertificates();
            setSickLeaveCertificates(sickLeaveCertificates);
            DBConnection.read("Prescriptions");
            PrescriptionDao prescriptions = DBConnection.getPrescriptions();
            setPrescriptions(prescriptions);
            DBConnection.read("Examinations");
            ExaminationDao examinations = DBConnection.getExaminations();
            setExaminations(examinations);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        setCnp(cnp);
    }

    public Patient enter () {
        String line;
        Patient you = patients.getPatient(cnp);
        /*if (you == null) {
            System.out.println("You do not seem to be in our database!\nWould you like to become our patient? (y/n) ");
            line = new Scanner(System.in).nextLine();
            if (line.equals("y") == true) {
                System.out.println("Please, provide your first name: ");
                line = new Scanner(System.in).nextLine();
                String patient_first_name = line;
                System.out.println("Please, provide your last name:");
                line = new Scanner(System.in).nextLine();
                String patient_last_name = line;
                System.out.println("Please, provide your address:");
                line = new Scanner(System.in).nextLine();
                String address = line;
                System.out.println("Please, provide your phone number:");
                line = new Scanner(System.in).nextLine();
                String patient_phone_number = line;
                you = new Patient(patient_first_name, patient_last_name, cnp, address, patient_phone_number);
                patients.addPatient(patient_first_name, patient_last_name, cnp, address, patient_phone_number);
            }
        }
        else{
            System.out.println("Hi "+you.getFirstName()+" "+you.getLastName()+"! Welcome back!");
        }*/
        return you;
    }



    public Appointment[] getAppointmentsForPatient (){
        List<Appointment> result = new ArrayList<>();
        for (int i = 0; i< appointments.getSize(); i++)
            if (appointments.getAppointment(i).getPatient().getCnp().equals(cnp)) {
                result.add(appointments.getAppointment(i));
            }
        Collections.sort(result, Appointment.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got Appointments for Patient cnp: "+cnp);
        return result.toArray(new Appointment[0]);
    }

    public Referral[] getReferralsForPatient (){
        List<Referral> result = new ArrayList<>();
        for (int i = 0; i< referrals.getSize(); i++){
            if (referrals.getReferral(i).getPatient().getCnp().equals(cnp)){
                result.add(referrals.getReferral(i));
            }
        }
        Collections.sort(result, Referral.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got Referrals for Patient cnp: "+cnp);
        return result.toArray(new Referral[0]);
    }

    public SickLeaveCertificate[] getSickLeaveCertificatesForPatient (){
        List<SickLeaveCertificate> result = new ArrayList<>();
        for (int i = 0; i< sickLeaveCertificates.getSize(); i++){
            if (sickLeaveCertificates.getSickLeaveCertificate(i).getPatient().getCnp().equals(cnp)){
                result.add(sickLeaveCertificates.getSickLeaveCertificate(i));
            }
        }
        Collections.sort(result, SickLeaveCertificate.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got SickLeaveCertificates for Patient cnp: "+cnp);
        return result.toArray(new SickLeaveCertificate[0]);
    }

    public Prescription[] getPrescriptionsForPatient (){
        List<Prescription> result = new ArrayList<>();
        for (int i = 0; i< prescriptions.getSize(); i++){
            if (prescriptions.getPrescription(i).getPatient().getCnp().equals(cnp)){
                result.add(prescriptions.getPrescription(i));
            }
        }
        Collections.sort(result, Prescription.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got Prescriptions for Patient cnp: "+cnp);
        return result.toArray(new Prescription[0]);
    }

    public Examination[] getExaminationsForPatient (){
        List<Examination> result = new ArrayList<>();
        for (int i = 0; i< examinations.getSize(); i++){
            if (examinations.getExamination(i).getPatient().getCnp().equals(cnp)){
                result.add(examinations.getExamination(i));
            }
        }
        Collections.sort(result, Examination.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got Examinations for Patient cnp: "+cnp);
        return result.toArray(new Examination[0]);
    }

    public void makeAppointment(String adate, String motive, String doctor_first_name, String doctor_last_name) throws Exception {
        Date date = null;
        Doctor doctor = doctors.getDoctor(doctor_first_name, doctor_last_name);
        try{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date = format.parse(adate);
            System.out.println(date);}
        catch (Exception e){e.printStackTrace();}

        Appointment appointment = appointments.getAppointment(date, patients.getPatient(cnp), doctor);
        try{
            if (appointment == null) {
                appointments.addAppointment(date, motive, patients.getPatient(cnp), doctor);
                System.out.println("\n\n\nYour Appointment has been scheduled!\n\n\n");
                csvReaderWriter.getInstance().writeCsv("Made Appointment for Patient cnp: "+patients.getPatient(cnp).getCnp());
            } else {
                throw new Exception("The appointment already exists!");
            }
        } finally {

        }
    }


    //Setters
    public void setSickLeaveCertificates(SickLeaveCertificateDao sickLeaveCertificates) {
        this.sickLeaveCertificates = sickLeaveCertificates;
    }

    public void setReferrals(ReferralDao referrals) {
        this.referrals = referrals;
    }

    public void setExaminations(ExaminationDao examinations) {
        this.examinations = examinations;
    }

    public void setPrescriptions(PrescriptionDao prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setDrugs(DrugDao drugs) {
        this.drugs = drugs;
    }

    public void setAppointments(AppointmentDao appointments) {
        this.appointments = appointments;
    }

    public void setDoctors(DoctorDao doctors) {
        this.doctors = doctors;
    }

    public void setPatients(PatientDao patients) {
        this.patients = patients;
    }

    public void setDiagnoses(DiagnosisDao diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    //Getters
    public SickLeaveCertificateDao getSickLeaveCertificates() {
        return sickLeaveCertificates;
    }

    public ReferralDao getReferrals() {
        return referrals;
    }

    public PrescriptionDao getPrescriptions() {
        return prescriptions;
    }

    public AppointmentDao getAppointments() {
        return appointments;
    }

    public DoctorDao getDoctors() {
        return doctors;
    }

    public PatientDao getPatients() {
        return patients;
    }

    public ExaminationDao getExaminations() {
        return examinations;
    }

    public DiagnosisDao getDiagnoses() {
        return diagnoses;
    }

    public DrugDao getDrugs() {
        return drugs;
    }
}
