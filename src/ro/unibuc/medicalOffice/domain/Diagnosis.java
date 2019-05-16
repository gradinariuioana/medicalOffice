package ro.unibuc.medicalOffice.domain;

public class Diagnosis {
    private  String description;

    public Diagnosis(String description){
        setDescription(description);
    }

    //Setter
    public void setDescription(String description) {
        this.description = description;
    }

    //Getter
    public String getDescription() {
        return description;
    }
}
