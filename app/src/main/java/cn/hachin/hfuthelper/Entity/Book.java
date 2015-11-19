package cn.hachin.hfuthelper.Entity;

/**
 * Created by yanghanqing on 15/11/18.
 * 图书类
 */
public class Book {
    /*
    <TD  bgcolor="#FFFFFF"><a class="blue" target="_blank" href="item.php?marc_no=0000326207">工程经济学.3版</a></TD>
					<TD  bgcolor="#FFFFFF">黄渝祥,邢爱芳编著</TD>
					<TD  bgcolor="#FFFFFF">同济大学出版社 1984</TD>
					<TD  bgcolor="#FFFFFF">29.2/4433=3</TD>
					<TD  bgcolor="#FFFFFF">中文图书</TD>
     */
    String name;
    String author;
    String publishing;
    String bookNumber;
    String bookType;
    String url;

    public Book() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", bookNumber='" + bookNumber + '\'' +
                ", bookType='" + bookType + '\'' +
                '}'+"\n";
    }
}
