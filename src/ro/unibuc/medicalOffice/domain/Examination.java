package ro.unibuc.medicalOffice.domain;


import java.util.Comparator;
import java.util.Date;

public class Examination implements Comparable<Examination> {
    private Date date;
    private Diagnosis diagnosis;
    private Doctor doctor;
    private Patient patient;

    public Examination(Date date, Diagnosis diagnosis, Doctor doctor, Patient patient){
        setDate(date);
        setDiagnosis(diagnosis);
        setDoctor(doctor);
        setPatient(patient);
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public Diagnosis getDiagnosis() {
        return diagnosis;
    }
    public Date getDate() {
        return date;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public Patient getPatient() {
        return patient;
    }

    @Override
    public int compareTo(Examination o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Examination> DATE = new Comparator<>() {
            @Override
            public int compare(Examination a1, Examination a2) {
                return a1.date.compareTo(a2.date);
            }
        };
    }
}
