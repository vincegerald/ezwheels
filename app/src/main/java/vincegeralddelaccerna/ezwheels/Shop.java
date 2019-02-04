package vincegeralddelaccerna.ezwheels;

public class Shop {

    public String firstname;
    public String lastname;
    public String contact;
    public String name;
    public String location;
    public String description;
    public String purl;
    public Double lon;
    public Double lat;
    public float rating = 0.2f;
    public String email;

    public Shop(){

    }

    public Shop(String firstname, String lastname, String contact, String name, String location, String description, String purl, Double lon, Double lat, float rating, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.name = name;
        this.location = location;
        this.description = description;
        this.purl = purl;
        this.lon = lon;
        this.lat = lat;
        this.rating = rating;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

