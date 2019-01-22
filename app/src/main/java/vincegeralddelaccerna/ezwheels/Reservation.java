package vincegeralddelaccerna.ezwheels;

import android.widget.RadioGroup;

public class Reservation {

    public String model, brand, name, image1, addressText, reminderText, shopuid,  currentDate,  currentTime, uid, listid, type;

    public Reservation() {
    }

    public Reservation(String model, String brand, String name, String image1, String addressText, String reminderText, String shopuid, String currentDate, String currentTime, String uid, String listid, String type) {
        this.model = model;
        this.brand = brand;
        this.name = name;
        this.image1 = image1;
        this.addressText = addressText;
        this.reminderText = reminderText;
        this.shopuid = shopuid;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.uid = uid;
        this.listid = listid;
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getReminderText() {
        return reminderText;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    public String getShopuid() {
        return shopuid;
    }

    public void setShopuid(String shopuid) {
        this.shopuid = shopuid;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
