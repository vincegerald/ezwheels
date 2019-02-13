package vincegeralddelaccerna.ezwheels;

public class Payments {

    public String image, name, code, uid, id, type, shopuid;
    public String amount, resid, date, seen;

    public Payments() {
    }

    public Payments(String image, String name, String code, String uid, String id, String amount, String type, String shopuid, String resid, String date, String seen) {
        this.image = image;
        this.name = name;
        this.code = code;
        this.uid = uid;
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.shopuid = shopuid;
        this.resid = resid;
        this.date = date;
        this.seen = seen;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getShopuid() {
        return shopuid;
    }

    public void setShopuid(String shopuid) {
        this.shopuid = shopuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
