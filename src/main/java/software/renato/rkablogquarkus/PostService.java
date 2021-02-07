package software.renato.rkablogquarkus;

import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import io.quarkus.qute.RawString;
import io.quarkus.qute.Template;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ApplicationScoped
public class PostService {

    private static final String MY_NAME = "Renato K. Araujo";

    private static final Map<String, String> POST_CACHE = new HashMap<>();

    private static final List<String> POSTS = Arrays.asList(
            "gatsby-blog",
            "setting-up-typescript",
            "nextjs-blog",
            "python-transform-lists");

    @Inject
    Template postTemplate;

    @Inject
    Template indexTemplate;

    @PostConstruct
    public void setUp() {
        try {
            TreeSet<PostPage> postPages = new TreeSet<>(Comparator.comparing(PostPage::getDate).reversed());

            MutableDataSet options = new MutableDataSet();
            options.set(Parser.EXTENSIONS, Collections.singletonList(YamlFrontMatterExtension.create()));

            Parser parser = Parser.builder(options).build();
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();

            for (String post : POSTS) {
                PostPage postPage = buildPostPage(post, parser, renderer);

                String page = postTemplate
                        .data("myName", MY_NAME)
                        .data("post", new RawString(postPage.getContent()))
                        .data("title", postPage.getTitle())
                        .data("description", postPage.getDescription())
                        .data("date", postPage.getDateFormatted())
                        .render();
                POST_CACHE.put(post, page);

                postPages.add(postPage);
            }

            POST_CACHE.put("/", indexTemplate
                    .data("myName", MY_NAME)
                    .data("posts", postPages)
                    .render());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private PostPage buildPostPage(String post, Parser parser, HtmlRenderer renderer) throws IOException {
        InputStream is = PostResource.class.getResourceAsStream("/posts/" + post + ".md");
        try (InputStreamReader isr = new InputStreamReader(is)) {
            AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
            Document document = parser.parseReader(isr);
            visitor.visit(document);
            String content = renderer.render(document);

            Map<String, List<String>> data = visitor.getData();

            String title = data.get("title").get(0);
            String description = data.get("description").get(0);
            LocalDateTime date = LocalDateTime.parse(data.get("date").get(0), DateTimeFormatter.ISO_DATE_TIME);
            String dateFormatted = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

            return new PostPage(post, title, date, dateFormatted, description, content);
        }
    }

    public String getPage(String post) {
        return POST_CACHE.get(post);
    }

}
