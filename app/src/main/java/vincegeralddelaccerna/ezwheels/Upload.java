package vincegeralddelaccerna.ezwheels;

public class Upload{
    public String category, doors, listid, edition, series, info, uid, date, fuel, image,  imagePath1,  imagePath2,  imagePath3,  videoPath,  finalBrand,  finalModel,  finalYear,  finalColor,  finalTransmission,  finalPcondition,  finalMileage,  finalPrice,  shop,  status;

    public Upload() {
    }




    public Upload(String listid, String edtion, String series, String info, String uid, String date, String fuel, String image, String imagePath1, String imagePath2, String imagePath3, String videoPath, String finalBrand, String finalModel, String finalYear, String finalColor, String finalTransmission, String finalPcondition, String finalMileage, String finalPrice, String shop, String status, String category, String doors) {
        this.listid = listid;
        this.image = image;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
        this.videoPath = videoPath;
        this.finalBrand = finalBrand;
        this.finalModel = finalModel;
        this.finalYear = finalYear;
        this.finalColor = finalColor;
        this.finalTransmission = finalTransmission;
        this.finalPcondition = finalPcondition;
        this.finalMileage = finalMileage;
        this.finalPrice = finalPrice;
        this.shop = shop;
        this.status = status;
        this.uid = uid;
        this.fuel = fuel;
        this.date = date;
        this.info = info;
        this.edition = edtion;
        this.series = series;
        this.category = category;
        this.doors = doors;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getListid() {
        return listid;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getFuel() { return fuel; }

    public void setFuel(String fuel) { this.fuel = fuel; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(String imagePath3) { this.imagePath3 = imagePath3; }


    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getFinalBrand() {
        return finalBrand;
    }

    public void setFinalBrand(String finalBrand) {
        this.finalBrand = finalBrand;
    }

    public String getFinalModel() {
        return finalModel;
    }

    public void setFinalModel(String finalModel) {
        this.finalModel = finalModel;
    }

    public String getFinalYear() {
        return finalYear;
    }

    public void setFinalYear(String finalYear) {
        this.finalYear = finalYear;
    }

    public String getFinalColor() {
        return finalColor;
    }

    public void setFinalColor(String finalColor) {
        this.finalColor = finalColor;
    }

    public String getFinalTransmission() {
        return finalTransmission;
    }

    public void setFinalTransmission(String finalTransmission) {
        this.finalTransmission = finalTransmission;
    }

    public String getFinalPcondition() {
        return finalPcondition;
    }

    public void setFinalPcondition(String finalPcondition) {
        this.finalPcondition = finalPcondition;
    }

    public String getFinalMileage() {
        return finalMileage;
    }

    public void setFinalMileage(String finalMileage) {
        this.finalMileage = finalMileage;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

}