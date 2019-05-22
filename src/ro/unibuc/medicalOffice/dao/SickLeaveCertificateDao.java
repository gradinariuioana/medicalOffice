package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Diagnosis;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Patient;
import ro.unibuc.medicalOffice.domain.SickLeaveCertificate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SickLeaveCertificateDao {
    SickLeaveCertificate[] sickLeaveCertificates;

    public SickLeaveCertificateDao(){
        sickLeaveCertificates = new SickLeaveCertificate[0];
    }

    public void addSickLeaveCertificate (Date startingDate, Diagnosis diagnosis, Doctor doctor, Patient patient){
        SickLeaveCertificate[] aux_list = new SickLeaveCertificate[sickLeaveCertificates.length + 1];

        for(int i = 0; i < sickLeaveCertificates.length; i++)
            aux_list[i] = sickLeaveCertificates[i];

        SickLeaveCertificate ob = new SickLeaveCertificate(startingDate, diagnosis, doctor, patient);
        aux_list[aux_list.length - 1] = ob;
        sickLeaveCertificates = aux_list;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(startingDate);
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = strDate+","+ob.getType()+","+Integer.toString(ob.getNumberOfDays())+","+ob.getDiagnosis().getDescription()+","+","+doctor.getFirstName()+","+doctor.getLastName()+","+patient.getCnp();
        readerWriter.writeCsv("SickLeaveCertificates", details);

        try {
            String[] args = new String[6];
            args[0] = strDate;
            args[1] = ob.getType();
            args[2] = Integer.toString(ob.getNumberOfDays());
            args[3] = diagnosis.getDescription();
            args[4] = doctor.getPhone_number();
            args[5] = patient.getCnp();
            DBConnection.write("sickleavecertificates", args);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addSickLeaveCertificate (Date startingDate, String type, int numberOfDays, Diagnosis diagnosis, Doctor doctor, Patient patient){
        SickLeaveCertificate[] aux_list = new SickLeaveCertificate[sickLeaveCertificates.length + 1];

        for(int i = 0; i < sickLeaveCertificates.length; i++)
            aux_list[i] = sickLeaveCertificates[i];

        SickLeaveCertificate ob = new SickLeaveCertificate(startingDate, type, numberOfDays, diagnosis, doctor, patient);
        aux_list[aux_list.length - 1] = ob;
        sickLeaveCertificates = aux_list;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(startingDate);
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = strDate+","+type+","+Integer.toString(numberOfDays)+","+ob.getDiagnosis().getDescription()+","+doctor.getFirstName()+","+doctor.getLastName()+","+patient.getCnp();
        readerWriter.writeCsv("SickLeaveCertificates", details);

        try {
            String[] args = new String[6];
            args[0] = strDate;
            args[1] = ob.getType();
            args[2] = Integer.toString(ob.getNumberOfDays());
            args[3] = diagnosis.getDescription();
            args[4] = doctor.getPhone_number();
            args[5] = patient.getCnp();
            DBConnection.write("sickleavecertificates", args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readSickLeaveCertificate(Date startingDate, Diagnosis diagnosis, Doctor doctor, Patient patient){
        SickLeaveCertificate[] aux_list = new SickLeaveCertificate[sickLeaveCertificates.length + 1];

        for(int i = 0; i < sickLeaveCertificates.length; i++)
            aux_list[i] = sickLeaveCertificates[i];

        SickLeaveCertificate ob = new SickLeaveCertificate(startingDate, diagnosis, doctor, patient);
        aux_list[aux_list.length - 1] = ob;
        sickLeaveCertificates = aux_list;

    }

    public void readSickLeaveCertificate(Date startingDate, String type, int numberOfDays, Diagnosis diagnosis, Doctor doctor, Patient patient){
        SickLeaveCertificate[] aux_list = new SickLeaveCertificate[sickLeaveCertificates.length + 1];

        for(int i = 0; i < sickLeaveCertificates.length; i++)
            aux_list[i] = sickLeaveCertificates[i];

        SickLeaveCertificate ob = new SickLeaveCertificate(startingDate, type, numberOfDays, diagnosis, doctor, patient);
        aux_list[aux_list.length - 1] = ob;
        sickLeaveCertificates = aux_list;
    }

    public SickLeaveCertificate getSickLeaveCertificate (int index){
        if(index >= 0 && index < sickLeaveCertificates.length)
            return sickLeaveCertificates[index];
        return null;
    }

    public SickLeaveCertificate getSickLeaveCertificate (Date startingDate, String type, int numberOfDays,  Doctor doctor, Patient patient){
        for(int i = 0; i < sickLeaveCertificates.length; i++)
            if (sickLeaveCertificates[i].getStartingDate().equals(startingDate) && sickLeaveCertificates[i].getNumberOfDays() == numberOfDays && sickLeaveCertificates[i].getType().equals(type) && sickLeaveCertificates[i].getPatient().equals(patient) && sickLeaveCertificates[i].getDoctor().equals(doctor))
                return sickLeaveCertificates[i];
        return null;
    }

    public void deleteSickLeaveCertificate(Date startingDate, String type, int numberOfDays, Doctor doctor, Patient patient){
        for(int i = 0; i < sickLeaveCertificates.length; i++)
            if (sickLeaveCertificates[i].getStartingDate().equals(startingDate) && sickLeaveCertificates[i].getNumberOfDays() == numberOfDays && sickLeaveCertificates[i].getType().equals(type) && sickLeaveCertificates[i].getPatient().equals(patient) && sickLeaveCertificates[i].getDoctor().equals(doctor))
                sickLeaveCertificates[i] = null;
    }

    public int getSize() {
        return sickLeaveCertificates.length;
    }
}
