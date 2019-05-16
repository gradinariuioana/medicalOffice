package ro.unibuc.medicalOffice.domain;

import javax.print.Doc;
import java.util.Comparator;
import java.util.Date;

public class Referral implements Comparable<Referral>{
    private Doctor fromDoctor;
    private Doctor toDoctor;
    private Diagnosis diagnosis;
    private Date date;
    private Patient patient;

    public Referral(Doctor fromDoctor, Doctor toDoctor, Diagnosis diagnosis, Date date, Patient patient){
        setDate(date);
        setDiagnosis(diagnosis);
        setFromDoctor(fromDoctor);
        setPatient(patient);
        setToDoctor(toDoctor);
    }

    public void setFromDoctor(Doctor fromDoctor) {
        this.fromDoctor = fromDoctor;
    }
    public void setToDoctor(Doctor toDoctor) {
        this.toDoctor = toDoctor;
    }
    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    public Doctor getFromDoctor() {
        return fromDoctor;
    }
    public Doctor getToDoctor() {
        return toDoctor;
    }
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }
    public Date getDate() {
        return date;
    }
    public Patient getPatient() {
        return patient;
    }

    @Override
    public int compareTo(Referral o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Referral> DATE = new Comparator<Referral>() {
            @Override
            public int compare(Referral r1, Referral r2) {
                return r1.date.compareTo(r2.date);
            }
        };
    }
}
