package as1124.com.ch4test.news;

public class NewsModel {

    private String title;

    private String content;

    public NewsModel() {
        // default constructor
    }

    public NewsModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
