package ro.unibuc.medicalOffice.domain;

public class Doctor {
    private String first_name;
    private String last_name;
    private String phone_number;
    protected String speciality;

    public Doctor(String first_name, String last_name, String phone_number) {
        setPhoneNumber(phone_number);
        setFirstName(first_name);
        setLastName(last_name);
        setSpeciality("Family");
    }

    public Doctor(String first_name, String last_name, String phone_number, String speciality) {
        setPhoneNumber(phone_number);
        setFirstName(first_name);
        setLastName(last_name);
        setSpeciality(speciality);
    }


    //Setters
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public void setPhoneNumber(String value){
        this.phone_number = value;
    }
    public void setFirstName(String value){
        this.first_name = value;
    }
    public void setLastName(String value){
        this.last_name = value;
    }

    //Getters
    public String getFirstName(){
        return first_name;
    }
    public String getLastName() {
        return last_name;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public String getSpeciality(){
        return speciality;
    }
}
