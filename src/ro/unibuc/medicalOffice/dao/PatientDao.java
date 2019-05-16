package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Patient;

public class PatientDao {

    private Patient[] patients;

    public PatientDao(){
        patients = new Patient[0];
    }

    public void addPatient(String first_name, String last_name, String cnp, String address, String phone_number){
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        Patient[] aux_list = new Patient[patients.length + 1];
        for(int i = 0; i < patients.length; i++)
            aux_list[i] = patients[i];
        Patient ob = new Patient(first_name, last_name, cnp, address, phone_number);
        aux_list[aux_list.length - 1] = ob;
        patients = aux_list;
        String details = first_name+","+last_name+","+cnp+","+address+","+phone_number;
        readerWriter.writeCsv("Patients", details);
    }

    public void addPatientFromCsv(String first_name, String last_name, String cnp, String address, String phone_number){
        Patient[] aux_list = new Patient[patients.length + 1];
        for(int i = 0; i < patients.length; i++)
            aux_list[i] = patients[i];
        Patient ob = new Patient(first_name, last_name, cnp, address, phone_number);
        aux_list[aux_list.length - 1] = ob;
        patients = aux_list;
    }

    public Patient getPatient(String cnp){
        for(int i = 0; i < patients.length; i++)
            if(patients[i].getCnp().equals(cnp)) {
                return patients[i];

            }
        return null;
    }

    public Patient getPatient (int index){
        if(index >= 0 && index < patients.length)
            return patients[index];
        return null;
    }

    public void deletePatient(String cnp){
        for(int i = 0; i < patients.length; i++)
            if(patients[i].getCnp().equals(cnp))
                patients[i] = null;
    }

    public int getSize() {
        return patients.length;
    }
}
