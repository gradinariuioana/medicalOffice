package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Diagnosis;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Examination;
import ro.unibuc.medicalOffice.domain.Patient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExaminationDao {
    private Examination[] examinations;

    public ExaminationDao(){
        examinations = new Examination[0];
    }

    public void addExamination(Date date, Diagnosis diagnosis, Doctor doctor, Patient patient){
        Examination ob = new Examination(date, diagnosis, doctor, patient);
        Examination[] aux_list = new Examination[examinations.length + 1];

        for(int i = 0; i < examinations.length; i++)
            aux_list[i] = examinations[i];
        aux_list[aux_list.length - 1] = ob;
        examinations = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        String details = strDate+","+patient.getCnp()+","+doctor.getFirstName()+","+doctor.getLastName();
        readerWriter.writeCsv("Examinations", details);

        try {
            String[] args = new String[4];
            args[0] = strDate;
            args[1] = diagnosis.getDescription();
            args[2] = doctor.getPhone_number();
            args[3] = patient.getCnp();
            DBConnection.write("examinations", args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readExamination(Date date, Diagnosis diagnosis, Doctor doctor, Patient patient){
        Examination ob = new Examination(date, diagnosis, doctor, patient);
        Examination[] aux_list = new Examination[examinations.length + 1];

        for(int i = 0; i < examinations.length; i++)
            aux_list[i] = examinations[i];
        aux_list[aux_list.length - 1] = ob;
        examinations = aux_list;
    }

    public Examination getExamination(Date date, Patient patient, Doctor doctor){
        for(int i = 0; i < examinations.length; i++)
            if(examinations[i].getDate().equals(date) && examinations[i].getDoctor().equals(doctor) && examinations[i].getPatient().equals(patient))
                return examinations[i];
        return null;
    }

    public Examination getExamination(int index){
        if(index >= 0 && index < examinations.length)
            return examinations[index];
        return null;
    }

    public void deleteExamination(Date date,Patient patient, Doctor doctor){
        for(int i = 0; i < examinations.length; i++)
            if(examinations[i].getDate()==date && examinations[i].getDoctor().equals(doctor) && examinations[i].getPatient().equals(patient))
                examinations[i] = null;
    }

    public int getSize() {
        return examinations.length;
    }
}
