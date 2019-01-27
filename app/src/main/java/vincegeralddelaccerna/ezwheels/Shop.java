package vincegeralddelaccerna.ezwheels;

public class Shop {

    public String firstname;
    public String lastname;
    public String contact;
    public String name;
    public String location;
    public String description;

    public Shop(){

    }

    public Shop(String firstname, String lastname, String contact, String name, String location, String description) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.name = name;
        this.location = location;
        this.description = description;
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

