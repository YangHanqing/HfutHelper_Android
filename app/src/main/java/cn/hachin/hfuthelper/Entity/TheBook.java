package cn.hachin.hfuthelper.Entity;

/**
 * Created by yanghanqing on 15/11/18.
 */
public class TheBook {
    /*
                                <td >索书号</td>
					            <td >条码号</td>
					            <td >年卷期</td>
					            <td >校区—馆藏地</td>
					            <td >书刊状态</td>

     */

    String bookId;  //索书号
    String barCode; //条形码
    String location;   //馆藏地
    String state;   //状态
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TheBook() {
    }


    @Override
    public String toString() {
        return "TheBook{" +
                "bookId='" + bookId + '\'' +
                ", barCode='" + barCode + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                '}'+"\n";
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
