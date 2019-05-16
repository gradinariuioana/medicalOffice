package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Doctor;

import java.util.List;

public class DoctorDao {
    private Doctor[] doctors;

    public DoctorDao(){
        doctors = new Doctor[0];
    }

    public void addDoctor(String first_name, String last_name, String phone_number, String speciality){
        Doctor[] aux_list = new Doctor[doctors.length + 1];

        for(int i = 0; i < doctors.length; i++)
            aux_list[i] = doctors[i];

        Doctor ob = new Doctor(first_name, last_name, phone_number, speciality);
        aux_list[aux_list.length - 1] = ob;
        doctors = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = first_name+","+last_name+","+phone_number+","+speciality+"\n";
        readerWriter.writeCsv("Doctors", details);
    }

    public void addDoctor(String first_name, String last_name, String phone_number){
        Doctor[] aux_list = new Doctor[doctors.length + 1];

        for(int i = 0; i < doctors.length; i++)
            aux_list[i] = doctors[i];

        Doctor ob = new Doctor(first_name, last_name, phone_number);
        aux_list[aux_list.length - 1] = ob;
        doctors = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = first_name+","+last_name+","+phone_number+","+"Family";
        readerWriter.writeCsv("Doctors", details);
    }

    public void addDoctorFromCsv(String first_name, String last_name, String phone_number, String speciality){
        Doctor[] aux_list = new Doctor[doctors.length + 1];

        for(int i = 0; i < doctors.length; i++)
            aux_list[i] = doctors[i];

        Doctor ob = new Doctor(first_name, last_name, phone_number, speciality);
        aux_list[aux_list.length - 1] = ob;
        doctors = aux_list;

    }

    public void addDoctorFromCsv(String first_name, String last_name, String phone_number){
        Doctor[] aux_list = new Doctor[doctors.length + 1];

        for(int i = 0; i < doctors.length; i++)
            aux_list[i] = doctors[i];

        Doctor ob = new Doctor(first_name, last_name, phone_number);
        aux_list[aux_list.length - 1] = ob;
        doctors = aux_list;
    }

    public Doctor getDoctor(String first_name, String last_name){
        for(int i = 0; i < doctors.length; i++) {
            if (doctors[i].getFirstName().equals(first_name) && doctors[i].getLastName().equals(last_name))
                return doctors[i];
        }
        return null;
    }

    public Doctor getDoctor (int index){
        if(index >= 0 && index < doctors.length)
            return doctors[index];
        return null;
    }

    public void deleteDoctor(String phone_number){
        for(int i = 0; i < doctors.length; i++)
            if(doctors[i].getPhone_number().equals(phone_number))
                doctors[i] = null;
    }
}
