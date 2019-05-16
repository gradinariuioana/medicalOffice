package ro.unibuc.medicalOffice.service;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.*;

import java.text.DateFormat;
import java.text.ParseException;
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

    public PatientService(){
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
    }

    public Patient enter () {
        String cnp;
        String line;
        while (true) {
            try {
                System.out.println("Please, provide your cnp: ");
                line = new Scanner(System.in).nextLine();
                if (line.matches("[0-9]+") == true && line.length() == 13) {
                    cnp = line;
                    break;
                } else {
                    Exception e = new Exception("Wrong format");
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Patient you = patients.getPatient(cnp);
        if (you == null) {
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
        }
        return you;
    }



    public Appointment[] getAppointmentsForPatient (){
        String cnp = enter().getCnp();
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
        String cnp = enter().getCnp();
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
        String cnp = enter().getCnp();
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
        String cnp = enter().getCnp();
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
        String cnp = enter().getCnp();
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

    public void makeAppointment() {
        Patient you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a date (dd/MM/yyyy): ");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(line);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Please, provide a motive:");
        line = new Scanner(System.in).nextLine();
        String motive = line;
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
                    break;
                }
            }
        }
        Appointment appointment = appointments.getAppointment(date, you, doctor);
        if (appointment == null) {
            appointments.addAppointment(date, motive, you, doctor);
        } else {
            System.out.println("The appointment already exists!");
        }
        System.out.println("\n\n\nYour Appointment has been scheduled!\n\n\n");
        csvReaderWriter.getInstance().writeCsv("Made Appointment for Patient cnp: "+you.getCnp());

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
