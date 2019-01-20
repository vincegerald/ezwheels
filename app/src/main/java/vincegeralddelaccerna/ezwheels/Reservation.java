package vincegeralddelaccerna.ezwheels;

public class Reservation {
    public String addressText, reminderText, shopuid,  currentDate,  currentTime, uid;

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

    public Reservation(String addressText, String reminderText, String shopuid, String currentDate, String currentTime, String uid) {
        this.addressText = addressText;
        this.reminderText = reminderText;
        this.shopuid = shopuid;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.uid = uid;
    }
}
