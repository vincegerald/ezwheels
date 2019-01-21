package vincegeralddelaccerna.ezwheels;

public class Favorites {
     public String currentUid,  uid,  listingid;

    public Favorites() {
    }

    public Favorites(String currentUid, String uid, String listingid) {
        this.currentUid = currentUid;
        this.uid = uid;
        this.listingid = listingid;
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

