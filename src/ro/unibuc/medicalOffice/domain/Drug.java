package ro.unibuc.medicalOffice.domain;

public class Drug {
    private String concentration;
    private String name;
    private double quantity;

    public Drug(String name, String concentration, double quantity){
        setConcentration(concentration);
        setName(name);
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
