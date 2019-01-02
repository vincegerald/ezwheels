package vincegeralddelaccerna.ezwheels;

public class Model {

    public Model(String model, String year) {
        this.model = model;
        this.year = year;
    }

    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String year;

    @Override
    public String toString() {
        return model + " " +  year;
    }
}
