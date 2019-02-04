package vincegeralddelaccerna.ezwheels;

public class Report {

    public String shopuid, report, uid;

    public Report() {
    }

    public Report(String shopuid, String report, String uid) {
        this.shopuid = shopuid;
        this.report = report;
        this.uid = uid;
    }

    public String getShopuid() {
        return shopuid;
    }

    public void setShopuid(String shopuid) {
        this.shopuid = shopuid;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
