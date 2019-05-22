package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Diagnosis;

public class DiagnosisDao {
    private Diagnosis[] diagnoses;

    public DiagnosisDao(){
        diagnoses = new Diagnosis[0];
    }

    public void addDiagnosis(String description){
        Diagnosis ob = new Diagnosis(description);
        Diagnosis[] aux_list = new Diagnosis[diagnoses.length + 1];

        for(int i = 0; i < diagnoses.length; i++)
            aux_list[i] = diagnoses[i];

        aux_list[aux_list.length - 1] = ob;
        diagnoses = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = description;
        readerWriter.writeCsv("Diagnoses", details);

        try {
            String[] args = new String[1];
            args[0] = description;
            DBConnection.write("diagnoses", args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readDiagnosis(String description){
        Diagnosis ob = new Diagnosis(description);
        Diagnosis[] aux_list = new Diagnosis[diagnoses.length + 1];

        for(int i = 0; i < diagnoses.length; i++)
            aux_list[i] = diagnoses[i];

        aux_list[aux_list.length - 1] = ob;
        diagnoses = aux_list;
    }

    public Diagnosis getDiagnosis (int index){
        if(index >= 0 && index < diagnoses.length)
            return diagnoses[index];
        return null;
    }

    public Diagnosis getDiagnosis (String description){
        for(int i = 0; i < diagnoses.length; i++)
            if (diagnoses[i].getDescription().equals(description))
                return diagnoses[i];
            return null;
    }

    public void deleteDiagnosis (int index){
        if(index >= 0 && index < diagnoses.length)
            diagnoses[index] = null;
    }
}
