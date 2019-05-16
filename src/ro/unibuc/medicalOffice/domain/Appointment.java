package ro.unibuc.medicalOffice.domain;

import java.util.Comparator;
import java.util.Date;

public class Appointment implements Comparable<Appointment>{
    private Date date;
    private String motive;
    private Patient patient;
    private Doctor doctor;


    public Appointment(Date date, String motive, Patient patient, Doctor doctor){
        setDate(date);
        setMotive(motive);
        setDoctor(doctor);
        setPatient(patient);
    }


    public void setMotive(String motive) {
        this.motive = motive;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public String getMotive() {
        return motive;
    }
    public Date getDate() {
        return date;
    }
    public Patient getPatient() {
        return patient;
    }
    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public int compareTo(Appointment o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Appointment> DATE = new Comparator<Appointment>() {
            @Override
            public int compare(Appointment a1, Appointment a2) {
                return a1.date.compareTo(a2.date);
            }
        };
    }
}
