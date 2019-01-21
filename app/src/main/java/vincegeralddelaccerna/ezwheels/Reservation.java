package vincegeralddelaccerna.ezwheels;

import android.widget.RadioGroup;

public class Reservation {

    public String addressText, reminderText, shopuid,  currentDate,  currentTime, uid, listid, type;



    public Reservation(String addressText, String reminderText, String shopuid, String currentDate, String currentTime, String uid, String listid, String type) {
        this.addressText = addressText;
        this.reminderText = reminderText;
        this.shopuid = shopuid;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.uid = uid;
        this.listid = listid;
        this.type = type;
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
