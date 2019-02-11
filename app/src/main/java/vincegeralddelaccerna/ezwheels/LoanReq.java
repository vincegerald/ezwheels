package vincegeralddelaccerna.ezwheels;

public class LoanReq {

    public String shopUid, comp1, comp2, comp3, comp4, comp5, uid, seenShop, seen, aid, dp, month, listId, status;

    public LoanReq() {
    }

    public LoanReq(String shopUid, String comp1, String comp2, String comp3, String comp4, String comp5, String uid, String seenShop, String seen, String aid, String dp, String month, String listId, String status) {
        this.shopUid = shopUid;
        this.comp1 = comp1;
        this.comp2 = comp2;
        this.comp3 = comp3;
        this.comp4 = comp4;
        this.comp5 = comp5;
        this.uid = uid;
        this.seenShop = seenShop;
        this.seen = seen;
        this.aid = aid;
        this.dp = dp;
        this.month = month;
        this.listId = listId;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }

    public String getComp1() {
        return comp1;
    }

    public void setComp1(String comp1) {
        this.comp1 = comp1;
    }

    public String getComp2() {
        return comp2;
    }

    public void setComp2(String comp2) {
        this.comp2 = comp2;
    }

    public String getComp3() {
        return comp3;
    }

    public void setComp3(String comp3) {
        this.comp3 = comp3;
    }

    public String getComp4() {
        return comp4;
    }

    public void setComp4(String comp4) {
        this.comp4 = comp4;
    }

    public String getComp5() {
        return comp5;
    }

    public void setComp5(String comp5) {
        this.comp5 = comp5;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSeenShop() {
        return seenShop;
    }

    public void setSeenShop(String seenShop) {
        this.seenShop = seenShop;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }
}
