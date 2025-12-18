package pl.mechanicsystem.dto.clients.dto;

public class ClientCarDTO {

    private Integer carid;
    private String brand;
    private String model;
    private Integer year;
    private String register;
    private String fuel;

    public ClientCarDTO(Integer carid, String brand, String model, Integer year, String register, String fuel) {
        this.carid = carid;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.register = register;
        this.fuel = fuel;
    }

    public Integer getCarid() {
        return carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
