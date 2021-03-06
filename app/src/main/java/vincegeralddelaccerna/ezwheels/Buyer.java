package vincegeralddelaccerna.ezwheels;

public class Buyer {

    public String firstname, lastname, contactnumber, purl, longitude, latitude, email;

    public Buyer(){

    }

    public Buyer(String firstname, String lastname, String contactnumber, String purl, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.contactnumber = contactnumber;
        this.purl = purl;
        this.longitude = longitude;
        this.latitude = latitude;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
