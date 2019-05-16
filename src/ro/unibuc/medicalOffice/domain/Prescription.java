package ro.unibuc.medicalOffice.domain;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Prescription implements  Comparable<Prescription>{
    private Date date;
    private Doctor doctor;
    private Patient patient;
    private Set <Drug> drugs = new HashSet<>();

    public Prescription(Date date, Doctor doctor, Patient patient, Set<Drug> drugs) {
        setDate(date);
        setDoctor(doctor);
        setPatient(patient);
        setDrugs(drugs);
    }

    //Setter
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDate(Date date){
        this.date=date;
    }
    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }

    //Getter
    public Date getDate(){
        return date;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public Patient getPatient() {
        return patient;
    }
    public Set<Drug> getDrugs() {
        return drugs;
    }

    @Override
    public int compareTo(Prescription o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Prescription> DATE = new Comparator<>() {
            @Override
            public int compare(Prescription a1, Prescription a2) {
                return a1.date.compareTo(a2.date);
            }
        };
    }
}
