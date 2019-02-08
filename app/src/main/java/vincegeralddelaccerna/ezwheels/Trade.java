package vincegeralddelaccerna.ezwheels;

public class Trade {
    public String year, fyear, listingid, imagePath1, imagePath2,  addPrice,  shopAddPrice,  fbrand,  fmodel,  uid,  shopuid,  type, image1, model, brand, name, status, priceList, tid, seen, fromSeen;

    public Trade() {
    }

    public Trade(String listingid, String imagePath1, String imagePath2, String addPrice, String shopAddPrice, String fbrand, String fmodel, String fyear, String uid, String shopuid, String type, String image1, String model, String brand, String year, String name, String status, String priceList, String tradeId, String seen, String fromSeen) {
        this.listingid = listingid;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.addPrice = addPrice;
        this.shopAddPrice = shopAddPrice;
        this.fbrand = fbrand;
        this.fmodel = fmodel;
        this.uid = uid;
        this.shopuid = shopuid;
        this.type = type;
        this.image1 = image1;
        this.model = model;
        this.brand = brand;
        this.name = name;
        this.status = status;
        this.priceList = priceList;
        this.tid = tradeId;
        this.fyear = fyear;
        this.year = year;
        this.seen = seen;
        this.fromSeen = fromSeen;
    }

    public String getFromSeen() {
        return fromSeen;
    }

    public void setFromSeen(String fromSeen) {
        this.fromSeen = fromSeen;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getFyear() {
        return fyear;
    }

    public void setFyear(String fyear) {
        this.fyear = fyear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFbrand() {
        return fbrand;
    }

    public void setFbrand(String fbrand) {
        this.fbrand = fbrand;
    }

    public String getFmodel() {
        return fmodel;
    }

    public void setFmodel(String fmodel) {
        this.fmodel = fmodel;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListingid() {
        return listingid;
    }

    public void setListingid(String listingid) {
        this.listingid = listingid;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
    }

    public String getShopAddPrice() {
        return shopAddPrice;
    }

    public void setShopAddPrice(String shopAddPrice) {
        this.shopAddPrice = shopAddPrice;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
