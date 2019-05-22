package ro.unibuc.medicalOffice.domain;

import java.util.Comparator;
import java.util.Date;

public class SickLeaveCertificate implements Comparable<SickLeaveCertificate> {

    private Date startingDate;
    private int numberOfDays;
    private String type;
    private Diagnosis diagnosis;
    private Doctor doctor;
    private Patient patient;

    public SickLeaveCertificate(Date startingDate, Diagnosis diagnosis, Doctor doctor, Patient patient){
        setStartingDate(startingDate);
        setDiagnosis(diagnosis);
        setType("Urgent");
        setNumberOfDays(6);
        setDoctor(doctor);
        setPatient(patient);
    }

    public SickLeaveCertificate(Date startingDate, String type, int numberOfDays, Diagnosis diagnosis, Doctor doctor, Patient patient){
        setStartingDate(startingDate);
        setDiagnosis(diagnosis);
        setType(type);
        setNumberOfDays(numberOfDays);
        setDoctor(doctor);
        setPatient(patient);
    }


    //Setter
    public void setType(String type) {
        this.type = type;
    }
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    //Getter
    public Date getStartingDate() {
        return startingDate;
    }
    public int getNumberOfDays() {
        return numberOfDays;
    }
    public String getType() {
        return type;
    }
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public Patient getPatient() {
        return patient;
    }

    @Override
    public int compareTo(SickLeaveCertificate o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<SickLeaveCertificate> DATE = new Comparator<SickLeaveCertificate>() {
            @Override
            public int compare(SickLeaveCertificate a1, SickLeaveCertificate a2) {
                return a1.startingDate.compareTo(a2.startingDate);
            }
        };
    }
}
