package software.renato.rkablogquarkus;

import java.time.LocalDateTime;

public class PostPage {

    private final String path;
    private final String title;
    private final LocalDateTime date;
    private final String dateFormatted;

    public PostPage(String path, String title, LocalDateTime date, String dateFormatted) {
        this.path = path;
        this.title = title;
        this.date = date;
        this.dateFormatted = dateFormatted;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }
}
