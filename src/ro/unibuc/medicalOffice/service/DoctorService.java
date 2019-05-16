package ro.unibuc.medicalOffice.service;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.*;

import javax.print.Doc;
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

    public DoctorService(){
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

    public Doctor enter () {
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
        }
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

    public void writePrescription (){
        Doctor you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a date:");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(line);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Patient patient;
        while(true){
            try{
                System.out.println("Please, provide patient's cnp:");
                line = new Scanner(System.in).nextLine();
                String cnp = line;
                patient = patients.getPatient(cnp);
                if (patient!=null)
                    break;
                else {
                    Exception e = new Exception("Pacientul nu se afla in baza de date!");
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Set<Drug> d= new HashSet<>();
        while(true){
            System.out.println("Please provide a drug name: ");
            line=new Scanner(System.in).nextLine();
            String name = line;
            System.out.println("Please provide a concentration: ");
            line=new Scanner(System.in).nextLine();
            String concentration = line;
            System.out.println("Please provide a quantity: ");
            line=new Scanner(System.in).nextLine();
            double quantity = Double.parseDouble(line);
            Drug drug = drugs.getDrug(name);
            if (drug == null || !drug.getConcentration().equals(concentration) || drug.getQuantity() != quantity) {
                System.out.println("Medicamentul nu exista in baza de date, dar va fi introdus!");
                drugs.addDrug(name, concentration, quantity);
            }
            drug = drugs.getDrug(name);
            d.add(drug);
            System.out.println("Do you wish to add another drug? (y/n) ");
            line=new Scanner(System.in).nextLine();
            if (line.equals("n"))
                break;
        }
        prescriptions.addPrescription(date, you, patient, d);
        System.out.println("Your prescription has been added!");
        csvReaderWriter.getInstance().writeCsv("Doctor: "+you.getFirstName()+" "+you.getLastName()+" wrote a prescription for "+patient.getCnp());
    }

    public void writeReferral () {
        Doctor you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a date:");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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

    public void writeSickLeaveCertificate () {
        Doctor you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a starting date:");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
        System.out.println("Please provide a diagnosis!: ");
        line = new Scanner(System.in).nextLine();
        Diagnosis diagnosis = diagnoses.getDiagnosis(line);
        if (diagnosis == null)
            diagnoses.addDiagnosis(line);
        diagnosis = diagnoses.getDiagnosis(line);
        System.out.println("Please provide a type: ");
        line = new Scanner(System.in).nextLine();
        String type = line;
        if (type.equals("Urgent")){
            sickLeaveCertificates.addSickLeaveCertificate(date, diagnosis, you, patient);
        }
        else {
            System.out.println("Please provide a number of days: ");
            line = new Scanner(System.in).nextLine();
            sickLeaveCertificates.addSickLeaveCertificate(date, type, Integer.parseInt(line), diagnosis, you, patient);
        }
        System.out.println("The certificate has been added!");
        csvReaderWriter.getInstance().writeCsv("Doctor: "+you.getFirstName()+" "+you.getLastName()+" wrote a sick leave certificate for "+patient.getCnp());
    }

    public void noteExamination () {
        Doctor you = enter();
        String line;
        Date date;
        while (true) {
            try {
                System.out.println("Please, provide a date:");
                line = new Scanner(System.in).nextLine();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
        System.out.println("Please provide a diagnosis!: ");
        line = new Scanner(System.in).nextLine();
        Diagnosis diagnosis = diagnoses.getDiagnosis(line);
        if (diagnosis == null)
            diagnoses.addDiagnosis(line);
        diagnosis = diagnoses.getDiagnosis(line);
        examinations.addExamination(date, diagnosis, you, patient);
        System.out.println("The examination has been added!");
        csvReaderWriter.getInstance().writeCsv("Doctor: "+you.getFirstName()+" "+you.getLastName()+" noted an examination for "+patient.getCnp());
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
}
