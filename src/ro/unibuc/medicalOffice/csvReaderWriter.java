package ro.unibuc.medicalOffice;

import ro.unibuc.medicalOffice.dao.*;
import ro.unibuc.medicalOffice.domain.Drug;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class csvReaderWriter {

    private static csvReaderWriter instance = null;

    private static DoctorDao doctors = new DoctorDao();
    private static DrugDao drugs = new DrugDao();
    private static AppointmentDao appointments = new AppointmentDao();
    private static ReferralDao referrals = new ReferralDao();
    private static SickLeaveCertificateDao sickLeaveCertificates = new SickLeaveCertificateDao();
    private static ExaminationDao examinations = new ExaminationDao();
    private static PrescriptionDao prescriptions = new PrescriptionDao();
    private static PatientDao patients = new PatientDao();
    private static DiagnosisDao diagnoses = new DiagnosisDao();

    private csvReaderWriter(){};

    public static csvReaderWriter getInstance() {
        if (instance == null)
            instance = new csvReaderWriter();
        return instance;
    }

    public void writeCsv(String action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String toWritePath = "C:\\Users\\Ioana\\Desktop\\University\\Anul2 Semestrul2\\Programare Avansata pe Obiecte\\Cabinet\\csv\\audit.csv";
        try (FileWriter fileWriter = new FileWriter(toWritePath, true)) {

            fileWriter.append(action);
            fileWriter.append(",");
            fileWriter.append(String.valueOf(timestamp));
            fileWriter.append("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeCsv(String file, String details) {
        String toWritePath = "C:\\Users\\Ioana\\Desktop\\University\\Anul2 Semestrul2\\Programare Avansata pe Obiecte\\Cabinet\\csv\\"+file+".csv";
        try (FileWriter fileWriter = new FileWriter(toWritePath, true)){
            fileWriter.append(details);
            fileWriter.append("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void readCsv(String objectType) {
        BufferedReader reader = null;
        try {
            String toReadPath = "C:\\Users\\Ioana\\Desktop\\University\\Anul2 Semestrul2\\Programare Avansata pe Obiecte\\Cabinet\\csv\\" + objectType + ".csv";
            String line = "";
            reader = new BufferedReader(new FileReader(toReadPath));
            reader.readLine();
            if (objectType == "Doctors") {
                System.out.println("Reading doctors");
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    if (fields.length > 0) {
                        if (fields.length < 4) {
                            doctors.addDoctorFromCsv(fields[0], fields[1], fields[2]);
                        } else {
                            doctors.addDoctorFromCsv(fields[0], fields[1], fields[2], fields[3]);
                        }
                    }
                }
            }
            else {
                if (objectType == "Patients") {
                    System.out.println("Reading patients");
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                            patients.addPatientFromCsv(fields[0], fields[1], fields[2], fields[3], fields[4]);
                    }
                } else {
                    if (objectType == "Drugs") {
                        System.out.println("Reading drugs");
                        while ((line = reader.readLine()) != null) {
                            String[] fields = line.split(",");
                                drugs.addDrugFromCsv(fields[0], fields[1], Double.parseDouble(fields[2]));
                        }
                    } else {
                        if (objectType == "Diagnoses") {
                            System.out.println("Reading diagnoses");
                            while ((line = reader.readLine()) != null) {
                                String[] fields = line.split(",");
                                    diagnoses.addDiagnosisFromCsv(fields[0]);
                            }
                        } else {
                            Date date;
                            if (objectType == "Appointments") {
                                System.out.println("Reading appointments");
                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split(",");
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    date = format.parse(fields[0]);
                                    appointments.addAppointmentFromCsv(date, fields[1], patients.getPatient(fields[2]), doctors.getDoctor(fields[3], fields[4]));
                                }
                            } else if (objectType == "Referrals") {
                                System.out.println("Reading referrals");
                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split(",");
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    date = format.parse(fields[5]);
                                        referrals.addReferralFromCsv(doctors.getDoctor(fields[0], fields[1]), doctors.getDoctor(fields[2], fields[3]), diagnoses.getDiagnosis(fields[4]), date, patients.getPatient(fields[6]));
                                }
                            }
                            else if (objectType == "SickLeaveCertificates"){
                                System.out.println("Reading SickLeaveCertificates");
                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split(",");
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    date = format.parse(fields[0]);
                                    sickLeaveCertificates.addSickLeaveCertificateFromCsv(date, fields[1], parseInt(fields[2]), diagnoses.getDiagnosis(fields[3]), doctors.getDoctor(fields[4], fields[5]), patients.getPatient(fields[6]));
                                }
                            }
                            else if (objectType == "Prescriptions"){
                                System.out.println("Reading prescriptions");
                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split(",");
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    date = format.parse(fields[0]);
                                    Set<Drug> d = new HashSet<>();
                                    String[] medicine = fields[4].split(" ");
                                    for (String m:medicine){
                                        d.add(drugs.getDrug(m));
                                    }
                                    prescriptions.addPrescriptionFromCsv(date, doctors.getDoctor(fields[1], fields[2]), patients.getPatient(fields[3]), d);
                                }
                            } else if (objectType == "Examinations"){
                                System.out.println("Reading Examinations");
                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split(",");
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    date = format.parse(fields[0]);
                                    examinations.addExaminationFromCsv(date, diagnoses.getDiagnosis(fields[1]), doctors.getDoctor(fields[2], fields[3]), patients.getPatient(fields[4]));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DiagnosisDao getDiagnoses() {
        return diagnoses;
    }

    public static DoctorDao getDoctors() {
        return doctors;
    }

    public static DrugDao getDrugs() {
        return drugs;
    }

    public static PatientDao getPatients() {
        return patients;
    }

    public static ExaminationDao getExaminations() {
        return examinations;
    }

    public static AppointmentDao getAppointments() {
        return appointments;
    }

    public static ReferralDao getReferrals() {
        return referrals;
    }

    public static PrescriptionDao getPrescriptions() {
        return prescriptions;
    }

    public static SickLeaveCertificateDao getSickLeaveCertificates() {
        return sickLeaveCertificates;
    }

    public static void setPrescriptions(PrescriptionDao prescriptions) {
        csvReaderWriter.prescriptions = prescriptions;
    }

    public static void setReferrals(ReferralDao referrals) {
        csvReaderWriter.referrals = referrals;
    }

    public static void setAppointments(AppointmentDao appointments) {
        csvReaderWriter.appointments = appointments;
    }

    public static void setExaminations(ExaminationDao examinations) {
        csvReaderWriter.examinations = examinations;
    }

    public static void setSickLeaveCertificates(SickLeaveCertificateDao sickLeaveCertificates) {
        csvReaderWriter.sickLeaveCertificates = sickLeaveCertificates;
    }

    public static void setDiagnoses(DiagnosisDao diagnoses) {
        csvReaderWriter.diagnoses = diagnoses;
    }

    public static void setDoctors(DoctorDao doctors) {
        csvReaderWriter.doctors = doctors;
    }

    public static void setDrugs(DrugDao drugs) {
        csvReaderWriter.drugs = drugs;
    }

    public static void setPatients(PatientDao patients) {
        csvReaderWriter.patients = patients;
    }
}

