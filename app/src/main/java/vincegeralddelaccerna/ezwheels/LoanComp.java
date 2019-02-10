package vincegeralddelaccerna.ezwheels;

public class LoanComp {

    public String shopUid, loanCompId, loanReq1, loanReq2, loanReq3, loanReq4, loanReq5;

    public LoanComp() {
    }

        public LoanComp(String shopUid, String loanCompId, String loanReq1, String loanReq2, String loanReq3, String loanReq4, String loanReq5) {
        this.shopUid = shopUid;
        this.loanCompId = loanCompId;
        this.loanReq1 = loanReq1;
        this.loanReq2 = loanReq2;
        this.loanReq3 = loanReq3;
        this.loanReq4 = loanReq4;
        this.loanReq5 = loanReq5;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }

    public String getLoanCompId() {
        return loanCompId;
    }

    public void setLoanCompId(String loanCompId) {
        this.loanCompId = loanCompId;
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
}
