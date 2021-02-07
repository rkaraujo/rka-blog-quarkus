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
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("")
public class PostResource {

    private static final String MY_NAME = "Renato K. Araujo";

    private final Map<String, String> postCache = new HashMap<>();

    @Inject
    private Template postTemplate;

    @Inject
    private Template indexTemplate;

    @PostConstruct
    public void init() {
        try {
            TreeSet<PostPage> postPages = new TreeSet<>(Comparator.comparing(PostPage::getDate).reversed());

            List<String> posts = Arrays.asList(
                    "gatsby-blog",
                    "setting-up-typescript",
                    "nextjs-blog",
                    "python-transform-lists");

            MutableDataSet options = new MutableDataSet();
            options.set(Parser.EXTENSIONS, Collections.singletonList(YamlFrontMatterExtension.create()));

            Parser parser = Parser.builder(options).build();
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();

            for (String post : posts) {
                InputStream is = PostResource.class.getResourceAsStream("/posts/" + post + ".md");
                try (InputStreamReader isr = new InputStreamReader(is)) {
                    AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
                    Document document = parser.parseReader(isr);
                    visitor.visit(document);
                    String htmlPost = renderer.render(document);

                    Map<String, List<String>> data = visitor.getData();

                    String title = data.get("title").get(0);
                    String description = data.get("description").get(0);
                    LocalDateTime date = LocalDateTime.parse(data.get("date").get(0), DateTimeFormatter.ISO_DATE_TIME);
                    String dateFormatted = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

                    String page = postTemplate
                            .data("myName", MY_NAME)
                            .data("post", new RawString(htmlPost))
                            .data("title", title)
                            .data("description", description)
                            .data("date", dateFormatted)
                            .render();

                    postCache.put(post, page);

                    postPages.add(new PostPage(post, title, date, dateFormatted));
                }
            }

            postCache.put("/", indexTemplate
                    .data("myName", MY_NAME)
                    .data("posts", postPages)
                    .render());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Path("/")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return Response.ok()
                .entity(postCache.get("/"))
                .build();
    }

    @Path("/{page}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getPage(@PathParam("page") String page) {
        String content = postCache.get(page);
        if (content == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok()
                .entity(content)
                .build();
    }

}
