package vincegeralddelaccerna.ezwheels;

public class Favorites {
     public String image1, brand, model, price, color, year, currentUid,  uid,  listingid, name;

    public Favorites() {
    }

    public Favorites(String image1, String brand, String model, String price, String color, String year, String currentUid, String uid, String listingid, String name) {
        this.image1 = image1;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.color = color;
        this.year = year;
        this.currentUid = currentUid;
        this.uid = uid;
        this.listingid = listingid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
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

