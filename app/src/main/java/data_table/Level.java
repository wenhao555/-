package data_table;

/**
 * Created by Administrator on 2017/5/27.
 */

public class Level {

    private String placeid;
    private String placename;
    private String placetoid;
    private String placetoname;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getPlacetoid() {
        return placetoid;
    }

    public void setPlacetoid(String placetoid) {
        this.placetoid = placetoid;
    }

    public String getPlacetoname() {
        return placetoname;
    }

    public void setPlacetoname(String placetoname) {
        this.placetoname = placetoname;
    }


    @Override
    public String toString() {
        return "Level [placeid=" + placeid + ", placename=" + placename
                + ", placetoid=" + placetoid + "]";
    }

}
