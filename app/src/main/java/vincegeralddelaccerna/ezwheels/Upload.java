package vincegeralddelaccerna.ezwheels;

public class Upload{
    public String image,  imagePath1,  imagePath2,  imagePath3,  videoPath,  finalBrand,  finalModel,  finalYear,  finalColor,  finalTransmission,  finalPcondition,  finalMileage,  finalPrice,  shop,  status;

    public Upload() {
    }

    public Upload(String image, String imagePath1, String imagePath2, String imagePath3, String videoPath, String finalBrand, String finalModel, String finalYear, String finalColor, String finalTransmission, String finalPcondition, String finalMileage, String finalPrice, String shop, String status) {
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
    }
}