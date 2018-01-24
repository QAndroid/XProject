package workshop1024.com.xproject.model;

public class News {
    private String banner;
    private String title;
    private String author;
    private String time;

    public News(String banner, String title, String author, String time) {
        this.banner = banner;
        this.title = title;
        this.author = author;
        this.time = time;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
