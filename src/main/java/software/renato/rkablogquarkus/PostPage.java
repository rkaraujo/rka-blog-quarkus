package software.renato.rkablogquarkus;

import java.time.LocalDateTime;

public class PostPage {

    private final String path;
    private final String title;
    private final LocalDateTime date;
    private final String dateFormatted;
    private final String description;
    private final String content;

    public PostPage(String path, String title, LocalDateTime date, String dateFormatted, String description, String content) {
        this.path = path;
        this.title = title;
        this.date = date;
        this.dateFormatted = dateFormatted;
        this.description = description;
        this.content = content;
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

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }
}
