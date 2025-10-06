package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Author;
import model.Paper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ScholarService {

    private static final String SERPAPI_BASE = "https://serpapi.com/search.json";
    private final String apiKey;
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public ScholarService(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("SERPAPI_API_KEY");
        }
        this.apiKey = apiKey;
    }

    /** Busca autores por nombre usando engine=google_scholar (perfiles vienen en 'profiles'). */
    public List<Author> searchAuthors(String query) throws IOException, InterruptedException {
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = SERPAPI_BASE
                + "?engine=google_scholar"
                + "&q=" + q
                + "&hl=en"
                + "&api_key=" + apiKey;

        JsonNode root = get(url);
        List<Author> out = new ArrayList<>();

        // Caso principal: bloque 'profiles'
        JsonNode profiles = root.path("profiles");
        if (profiles.isArray()) {
            for (JsonNode p : profiles) {
                Author a = new Author();
                a.setName(p.path("name").asText(""));
                // SerpApi suele incluir author_id directo; si no, lo extraemos del link ?user=XXXX
                String authorId = p.path("author_id").asText("");
                if (authorId.isBlank()) {
                    String link = p.path("link").asText("");
                    authorId = extractAuthorIdFromLink(link);
                }
                a.setAuthorId(authorId);
                a.setAffiliation(p.path("affiliations").asText(""));
                // h-index y citaciones no siempre están en 'profiles'; deja 0 si faltan.
                a.setHIndex(p.path("h_index").asInt(0));
                a.setI10Index(p.path("i10_index").asInt(0));
                a.setCitedBy(p.path("cited_by").asInt(0));
                out.add(a);
            }
        } else {
            // Fallback: a veces viene dentro de organic_results con tipo de perfil
            JsonNode organic = root.path("organic_results");
            if (organic.isArray()) {
                for (JsonNode r : organic) {
                    if (r.path("type").asText("").toLowerCase().contains("profile")) {
                        Author a = new Author();
                        a.setName(r.path("title").asText(""));
                        String link = r.path("link").asText("");
                        a.setAuthorId(extractAuthorIdFromLink(link));
                        a.setAffiliation(r.path("snippet").asText(""));
                        out.add(a);
                    }
                }
            }
        }
        return out;
    }

    /** Lista publicaciones de un autor usando engine=google_scholar_author. */
    public List<Paper> listPapers(String authorId) throws IOException, InterruptedException {
        String url = SERPAPI_BASE
                + "?engine=google_scholar_author"
                + "&author_id=" + URLEncoder.encode(authorId, StandardCharsets.UTF_8)
                + "&view_op=list_works"
                + "&hl=en"
                + "&api_key=" + apiKey;

        JsonNode root = get(url);
        List<Paper> out = new ArrayList<>();

        JsonNode articles = root.path("articles");
        if (articles.isArray()) {
            for (JsonNode n : articles) {
                Paper p = new Paper();
                p.setTitle(n.path("title").asText(""));
                p.setYear(n.path("year").asInt(0));
                p.setLink(n.path("link").asText(""));
                int cites = n.path("cited_by").path("value").asInt(0);
                p.setCitedBy(cites);
                out.add(p);
            }
        }
        return out;
    }

    // ----------------------- helpers -----------------------

    private JsonNode get(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() >= 400) {
            throw new IOException("Search error: HTTP " + res.statusCode() + ": " + res.body());
        }
        return mapper.readTree(res.body());
    }

    private static String extractAuthorIdFromLink(String link) {
        // ej: https://scholar.google.com/citations?user=XXXX&hl=en
        int i = link.indexOf("user=");
        if (i >= 0) {
            int j = link.indexOf('&', i);
            return j > i ? link.substring(i + 5, j) : link.substring(i + 5);
        }
        return "";
    }
}


