package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Diagnosis;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Patient;
import ro.unibuc.medicalOffice.domain.Referral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReferralDao {
    Referral[] referrals;

    public ReferralDao(){
        referrals = new Referral[0];
    }

    public void addReferral(Doctor fromDoctor, Doctor toDoctor, Diagnosis diagnosis, Date date, Patient patient){
        Referral ob = new Referral(fromDoctor, toDoctor, diagnosis, date, patient);
        Referral[] aux_list = new Referral[referrals.length + 1];

        for(int i = 0; i < referrals.length; i++)
            aux_list[i] = referrals[i];
        aux_list[aux_list.length - 1] = ob;
        referrals = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        String details = fromDoctor.getFirstName()+","+fromDoctor.getLastName()+","+toDoctor.getFirstName()+","+toDoctor.getLastName()+","+diagnosis.getDescription()+","+strDate+","+patient.getCnp();
        readerWriter.writeCsv("Referrals", details);
    }

    public void addReferralFromCsv(Doctor fromDoctor, Doctor toDoctor, Diagnosis diagnosis, Date date, Patient patient){
        Referral ob = new Referral(fromDoctor, toDoctor, diagnosis, date, patient);
        Referral[] aux_list = new Referral[referrals.length + 1];

        for(int i = 0; i < referrals.length; i++)
            aux_list[i] = referrals[i];
        aux_list[aux_list.length - 1] = ob;
        referrals = aux_list;
    }

    public Referral getReferral(Doctor fromDoctor, Doctor toDoctor, Date date, Patient patient){
        for(int i = 0; i < referrals.length; i++)
            if(referrals[i].getDate().equals(date) && referrals[i].getFromDoctor().equals(fromDoctor) && referrals[i].getToDoctor().equals(toDoctor) && referrals[i].getPatient().equals(patient))
                return referrals[i];
        return null;
    }

    public Referral getReferral(int index){
        if(index >= 0 && index < referrals.length)
            return referrals[index];
        return null;
    }

    public void deleteReferral(Doctor fromDoctor, Doctor toDoctor, Date date, Patient patient){
        for(int i = 0; i < referrals.length; i++)
            if(referrals[i].getDate().equals(date) && referrals[i].getFromDoctor().equals(fromDoctor) && referrals[i].getToDoctor().equals(toDoctor) && referrals[i].getPatient().equals(patient))
                referrals[i] = null;
    }

    public int getSize() {
        return referrals.length;
    }
}
