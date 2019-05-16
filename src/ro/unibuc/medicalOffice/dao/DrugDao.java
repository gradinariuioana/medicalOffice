package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Drug;


public class DrugDao {
    private Drug[] drugs;

    public DrugDao(){
        drugs = new Drug[0];
    }

    public void addDrug(String name, String concentration, double quantity){
        Drug ob = new Drug(name, concentration, quantity);
        Drug[] aux_list = new Drug[drugs.length + 1];

        for(int i = 0; i < drugs.length; i++)
            aux_list[i] = drugs[i];

        aux_list[aux_list.length - 1] = ob;
        drugs = aux_list;
        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        String details = name+","+concentration+","+Double.toString(quantity);
        readerWriter.writeCsv("Drugs", details);
    }

    public void addDrugFromCsv(String name, String concentration, double quantity){
        Drug ob = new Drug(name, concentration, quantity);
        Drug[] aux_list = new Drug[drugs.length + 1];

        for(int i = 0; i < drugs.length; i++)
            aux_list[i] = drugs[i];

        aux_list[aux_list.length - 1] = ob;
        drugs = aux_list;
    }

    public Drug getDrug(String name){
        for(int i = 0; i < drugs.length; i++)
            if(drugs[i].getName().equals(name))
                return drugs[i];
        return null;
    }

    public Drug getDrug (int index){
        if(index >= 0 && index < drugs.length)
            return drugs[index];
        return null;
    }

    public void deleteDrug(String name){
        for(int i = 0; i < drugs.length; i++)
            if(drugs[i].getName() == name)
                drugs[i] = null;
    }
}
