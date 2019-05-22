package ro.unibuc.medicalOffice.service;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DoctorService {

    private PatientDao patients;
    private DoctorDao doctors;
    private AppointmentDao appointments;
    private ReferralDao referrals;
    private SickLeaveCertificateDao sickLeaveCertificates;
    private PrescriptionDao prescriptions;
    private ExaminationDao examinations;
    private DrugDao drugs;
    private DiagnosisDao diagnoses;
    private Connection connection;
    private String phonenb;

    public DoctorService(String phone){
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
        setPhonenb(phone);

    }

    public Doctor enter () {
        String line;
        Doctor you = doctors.getDoctor(phonenb);
        /*
        String firstName;
        String lastName;
        String line;
        Doctor you = null;
        while (true) {
            try {
                System.out.println("Please, provide your first name: ");
                line = new Scanner(System.in).nextLine();
                firstName = line;
                System.out.println("Please, provide your last name: ");
                line = new Scanner(System.in).nextLine();
                lastName = line;
                you = doctors.getDoctor(firstName, lastName);
                if (you!=null)
                    break;
                else {
                    Exception e = new Exception("You are not in our database!");
                    throw (e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        return you;
    }

    public Appointment[] getAppointmentsForDoctor (){
        Doctor you = enter();
        List<Appointment> result = new ArrayList<>();
        for (int i = 0; i< appointments.getSize(); i++){
            if (appointments.getAppointment(i).getDoctor().getPhone_number().equals(you.getPhone_number())){
                result.add(appointments.getAppointment(i));
            }
        }
        Collections.sort(result, Appointment.Comparators.DATE);
        csvReaderWriter.getInstance().writeCsv("Got Appointments for Doctor: "+you.getFirstName()+" "+you.getLastName());
        return result.toArray(new Appointment[0]);
    }

    public void writePrescription (String adate, String patient_cnp, List<String> provideddrugs) throws Exception{
        Date date = null;
        Patient patient = patients.getPatient(patient_cnp);
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(adate);
            System.out.println(date);}
        catch (Exception e){
            System.out.println("aixi");}

        Prescription prescription = prescriptions.getPrescription(date, patient, doctors.getDoctor(phonenb));
        try{
            if (prescription == null) {
                Set<Drug> auxdrugs = new HashSet<>();
                for (String s: provideddrugs){
                    String [] rez = s.split(", +");
                    String drugName = rez[0];
                    String drugConcentration = rez[1];
                    Double drugQuantity = Double.parseDouble(rez[2]);
                    Drug aux = new Drug(drugName, drugConcentration, drugQuantity);
                    auxdrugs.add(aux);
                }
                prescriptions.addPrescription(date, doctors.getDoctor(phonenb), patient, auxdrugs);
                System.out.println("\n\n\nYour Prescription has been written!\n\n\n");
                csvReaderWriter.getInstance().writeCsv("Doctor: "+doctors.getDoctor(phonenb).getFirstName()+" "+doctors.getDoctor(phonenb).getLastName()+" wrote a prescription for "+patient.getCnp());
            } else {
                throw new Exception("The prescription already exists!");
            }
        } finally {

        }
    }

    public void writeReferral () {
        Doctor you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a date:");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                date = format.parse(line);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Patient patient;
        while (true) {
            try {
                System.out.println("Please, provide patient's cnp:");
                line = new Scanner(System.in).nextLine();
                String cnp = line;
                patient = patients.getPatient(cnp);
                if (patient != null)
                    break;
                else {
                    Exception e = new Exception("Pacientul nu se afla in baza de date!");
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String doctor_first_name;
        String doctor_last_name;
        Doctor doctor = null;
        while (true) {
            try {
                System.out.println("Please, provide the doctor's first name:");
                line = new Scanner(System.in).nextLine();
                doctor_first_name = line;
                System.out.println("Please, provide the doctor's last name:");
                line = new Scanner(System.in).nextLine();
                doctor_last_name = line;
                doctor = doctors.getDoctor(doctor_first_name, doctor_last_name);
                if (doctor != null) {
                    break;
                } else {
                    Exception e = new Exception("The doctor in not in our database! Would you like to try another doctor? (y/n) ");
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
                line = new Scanner(System.in).nextLine();
                if (line.equals("n")) {
                    System.out.println("Thank you for using our service! Have a great day!");
                    System.exit(0);
                }
            }
        }
        System.out.println("Please provide a diagnosis!: ");
        line = new Scanner(System.in).nextLine();
        Diagnosis diagnosis = diagnoses.getDiagnosis(line);
        if (diagnosis == null)
            diagnoses.addDiagnosis(line);
        diagnosis = diagnoses.getDiagnosis(line);
        referrals.addReferral(you, doctor, diagnosis, date, patient);
        System.out.println("The referral has been added!");
        csvReaderWriter.getInstance().writeCsv("Doctor: "+you.getFirstName()+" "+you.getLastName()+" wrote a referral for "+patient.getCnp());
    }

    public void writeSickLeaveCertificate (String adate, String type, int numberOfDays, String diagnosis_description, String patient_cnp) throws Exception
    {
        Date date = null;
        Patient patient = patients.getPatient(patient_cnp);
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(adate);
            System.out.println(date);}
        catch (Exception e){e.printStackTrace();}

        SickLeaveCertificate sickLeaveCertificate = sickLeaveCertificates.getSickLeaveCertificate(date, type, numberOfDays, doctors.getDoctor(phonenb), patient);
        try{
            if (sickLeaveCertificate == null) {
                sickLeaveCertificates.addSickLeaveCertificate(date, type, numberOfDays, diagnoses.getDiagnosis(diagnosis_description), doctors.getDoctor(phonenb), patient);
                System.out.println("\n\n\nYour Sick Leave Certificate has been added to the db!\n\n\n");
                csvReaderWriter.getInstance().writeCsv("Written SLCertificate for Doctor phone_nb: "+doctors.getDoctor(phonenb).getPhone_number());
            } else {
                throw new Exception("The SLCertificate already exists!");
            }
        } finally {

        } }

    public void noteExamination (String adate, String diagnosis_description, String patient_cnp) throws Exception {
        Date date = null;
        Patient patient = patients.getPatient(patient_cnp);
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(adate);
            System.out.println(date);}
        catch (Exception e){e.printStackTrace();}

        Examination examination = examinations.getExamination(date, patient, doctors.getDoctor(phonenb));
        try{
            if (examination == null) {
                if (diagnoses.getDiagnosis(diagnosis_description) == null){
                    diagnoses.addDiagnosis(diagnosis_description);
                }
                examinations.addExamination(date, diagnoses.getDiagnosis(diagnosis_description), doctors.getDoctor(phonenb), patient);
                System.out.println("\n\n\nYour Examination has been noted!\n\n\n");
                csvReaderWriter.getInstance().writeCsv("Noted Examination for Doctor phone_nb: "+doctors.getDoctor(phonenb).getPhone_number());
            } else {
                throw new Exception("The examination already exists!");
            }
        } finally {

        }
    }

    public void setExaminations(ExaminationDao examinations) {
        this.examinations = examinations;
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
    public void setPrescriptions(PrescriptionDao prescriptions) { this.prescriptions = prescriptions;}
    public void setReferrals(ReferralDao referrals) {
        this.referrals = referrals;
    }
    public void setSickLeaveCertificates(SickLeaveCertificateDao sickLeaveCertificates) {
        this.sickLeaveCertificates = sickLeaveCertificates;
    }
    public void setDiagnoses(DiagnosisDao diagnoses) {
        this.diagnoses = diagnoses;
    }
    public PatientDao getPatients() {
        return patients;
    }
    public DoctorDao getDoctors() {
        return doctors;
    }
    public AppointmentDao getAppointments() {
        return appointments;
    }
    public ReferralDao getReferrals() {
        return referrals;
    }
    public PrescriptionDao getPrescriptions() {
        return prescriptions;
    }
    public DrugDao getDrugs() {
        return drugs;
    }
    public SickLeaveCertificateDao getSickLeaveCertificates() {
        return sickLeaveCertificates;
    }
    public DiagnosisDao getDiagnoses() {
        return diagnoses;
    }
    public ExaminationDao getExaminations() {
        return examinations;
    }

    public String getPhonenb() {
        return phonenb;
    }

    public void setPhonenb(String phonenb) {
        this.phonenb = phonenb;
    }
}
