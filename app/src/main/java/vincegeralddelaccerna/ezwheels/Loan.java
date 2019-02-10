package vincegeralddelaccerna.ezwheels;

public class Loan {

    public String shopUid, uid, loanReqid, status, loanReq1, loanReq2, loanReq3, loanReq4, loanReq5, shopSeen, userSeen;

    public Loan() {
    }

    public Loan(String shopUid, String uid, String loanReqid, String status, String loanReq1, String loanReq2, String loanReq3, String loanReq4, String loanReq5, String shopSeen, String userSeen) {
        this.shopUid = shopUid;
        this.uid = uid;
        this.loanReqid = loanReqid;
        this.status = status;
        this.loanReq1 = loanReq1;
        this.loanReq2 = loanReq2;
        this.loanReq3 = loanReq3;
        this.loanReq4 = loanReq4;
        this.loanReq5 = loanReq5;
        this.shopSeen = shopSeen;
        this.userSeen = userSeen;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLoanReqid() {
        return loanReqid;
    }

    public void setLoanReqid(String loanReqid) {
        this.loanReqid = loanReqid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanReq1() {
        return loanReq1;
    }

    public void setLoanReq1(String loanReq1) {
        this.loanReq1 = loanReq1;
    }

    public String getLoanReq2() {
        return loanReq2;
    }

    public void setLoanReq2(String loanReq2) {
        this.loanReq2 = loanReq2;
    }

    public String getLoanReq3() {
        return loanReq3;
    }

    public void setLoanReq3(String loanReq3) {
        this.loanReq3 = loanReq3;
    }

    public String getLoanReq4() {
        return loanReq4;
    }

    public void setLoanReq4(String loanReq4) {
        this.loanReq4 = loanReq4;
    }

    public String getLoanReq5() {
        return loanReq5;
    }

    public void setLoanReq5(String loanReq5) {
        this.loanReq5 = loanReq5;
    }

    public String getShopSeen() {
        return shopSeen;
    }

    public void setShopSeen(String shopSeen) {
        this.shopSeen = shopSeen;
    }

    public String getUserSeen() {
        return userSeen;
    }

    public void setUserSeen(String userSeen) {
        this.userSeen = userSeen;
    }
}
