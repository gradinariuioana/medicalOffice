package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Drug;
import ro.unibuc.medicalOffice.domain.Patient;
import ro.unibuc.medicalOffice.domain.Prescription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class PrescriptionDao {
    Prescription[] prescriptions;

    public PrescriptionDao(){
        prescriptions = new Prescription[0];
    }

    public void addPrescription (Date date, Doctor doctor, Patient patient, Set<Drug> drugs){
        Prescription[] aux_list = new Prescription[prescriptions.length + 1];

        for(int i = 0; i < prescriptions.length; i++)
            aux_list[i] = prescriptions[i];

        Prescription ob = new Prescription(date, doctor, patient, drugs);
        aux_list[aux_list.length - 1] = ob;
        prescriptions = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String medicine = "";
        System.out.println(drugs.size());
        for (Drug drug :drugs){
            medicine = medicine + drug.getName()+" ";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        String details = strDate+","+patient.getCnp()+","+doctor.getFirstName()+","+doctor.getLastName()+","+medicine;
        readerWriter.writeCsv("Prescriptions", details);
    }

    public void addPrescriptionFromCsv (Date date, Doctor doctor, Patient patient, Set<Drug> drugs){
        Prescription[] aux_list = new Prescription[prescriptions.length + 1];

        for(int i = 0; i < prescriptions.length; i++)
            aux_list[i] = prescriptions[i];

        Prescription ob = new Prescription(date, doctor, patient, drugs);
        aux_list[aux_list.length - 1] = ob;
        prescriptions = aux_list;
    }

    public Prescription getPrescription (int index){
        if(index >= 0 && index < prescriptions.length)
            return prescriptions[index];
        return null;
    }

    public Prescription getPrescription (Date date, Patient patient, Doctor doctor){
        for(int i = 0; i < prescriptions.length; i++)
            if (prescriptions[i].getDate().equals(date) && prescriptions[i].getPatient().equals(patient) && prescriptions[i].getDoctor().equals(doctor))
                return prescriptions[i];
        return null;
    }

    public void deletePrescription(Date date, Patient patient, Doctor doctor){
        for(int i = 0; i < prescriptions.length; i++)
            if (prescriptions[i].getDate().equals(date) && prescriptions[i].getPatient().equals(patient) && prescriptions[i].getDoctor().equals(doctor))
                prescriptions[i]=null;
    }

    public int getSize() {
        return prescriptions.length;
    }
}
