package com.example.literalura;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

public class Gutendex {

    public static Libro buscarTitulo(String title) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String url = "https://gutendex.com/books/?search=" + title;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode results = root.path("results");
        if (results.isArray() && results.size() > 0) {
            JsonNode first = results.get(0);

            String bookTitle = first.path("title").asText();

            String language = "";
            JsonNode languages = first.path("languages");
            if (languages.isArray() && languages.size() > 0) {
                language = languages.get(0).asText();
            }

            int downloadCount = first.path("download_count").asInt();

            Libro libro = new Libro(bookTitle, language, downloadCount);

            JsonNode authors = first.path("authors");
            if (authors.isArray() && authors.size() > 0) {
                JsonNode a = authors.get(0);
                String name = a.path("name").asText();
                if (!name.isEmpty()) {
                    Integer anioNacimiento = a.has("birth_year") && !a.get("birth_year").isNull()
                            ? a.get("birth_year").asInt()
                            : null;

                    Integer anioMuerte = a.has("death_year") && !a.get("death_year").isNull()
                            ? a.get("death_year").asInt()
                            : null;

                    Autor autor = new Autor(name, anioNacimiento, anioMuerte);
                    libro.setAutor(autor);
                }
            }

            return libro;
        }

        return null;
    }
}