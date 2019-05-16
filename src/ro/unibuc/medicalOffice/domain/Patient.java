package ro.unibuc.medicalOffice.domain;


public class Patient {
    private String first_name;
    private String last_name;
    private String cnp;
    private String address;
    private String phone_number;


    public Patient(String first_name, String last_name, String cnp, String address, String phone_number) {
        setCnp(cnp);
        setAddress(address);
        setFirstName(first_name);
        setLastName(last_name);
        setPhone_number(phone_number);
    }


    //Setters
    public void setCnp(String value){
        this.cnp = value;
    }
    public void setFirstName(String value){
        this.first_name = value;
    }
    public void setLastName(String value){
        this.last_name = value;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    //Getters
    public String getFirstName(){
        return first_name;
    }
    public String getCnp() {
        return cnp;
    }
    public String getLastName() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone_number() {
        return phone_number;
    }

}
