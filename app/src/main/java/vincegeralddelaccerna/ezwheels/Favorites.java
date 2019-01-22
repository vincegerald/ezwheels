package vincegeralddelaccerna.ezwheels;

public class Favorites {
     public String brand, model, price, color, year, currentUid,  uid,  listingid;

    public Favorites() {
    }

    public Favorites(String brand, String model, String price, String color, String year, String currentUid, String uid, String listingid) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.color = color;
        this.year = year;
        this.currentUid = currentUid;
        this.uid = uid;
        this.listingid = listingid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getListingid() {
        return listingid;
    }

    public void setListingid(String listingid) {
        this.listingid = listingid;
    }
}

