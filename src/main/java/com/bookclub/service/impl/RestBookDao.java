package com.bookclub.service.impl;

import com.bookclub.model.Book;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RestBookDao {

    private static final String OPEN_LIBRARY_URL = "https://openlibrary.org/api/books";

    // Fetches data from OpenLibrary API
    public Object getBooksDoc(String isbnString) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(OPEN_LIBRARY_URL)
                .queryParam("bibkeys", isbnString)
                .queryParam("format", "json")
                .queryParam("jscmd", "data");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = rest.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        String jsonBookList = response.getBody();

        return Configuration.defaultConfiguration()
                .jsonProvider()
                .parse(jsonBookList);
    }

    // Returns a list of books (hardcoded ISBNs for now)
    public List<Book> list() {
        List<Book> books = new ArrayList<>();

        String isbnString = "ISBN:9780140328721,ISBN:9780439023481,ISBN:9780307474278";

        Object document = getBooksDoc(isbnString);
        Map<String, Object> jsonMap = JsonPath.read(document, "$");

        for (String key : jsonMap.keySet()) {
            String isbn = key.replace("ISBN:", "");

            String title;
            String description;
            String infoUrl;
            Integer numOfPages;

            try {
                title = JsonPath.read(document, "$." + key + ".title");
            } catch (Exception e) {
                title = "Unknown Title";
            }

            try {
                description = JsonPath.read(document, "$." + key + ".subtitle");
            } catch (Exception e) {
                description = "No description available.";
            }

            try {
                infoUrl = JsonPath.read(document, "$." + key + ".info_url");
            } catch (Exception e) {
                infoUrl = "https://openlibrary.org";
            }

            try {
                numOfPages = JsonPath.read(document, "$." + key + ".number_of_pages");
            } catch (Exception e) {
                numOfPages = 0;
            }

            books.add(new Book(isbn, title, description, infoUrl, numOfPages));
        }

        return books;
    }

    // Finds a single book by ISBN
    public Book find(String isbn) {
        Object document = getBooksDoc("ISBN:" + isbn);

        String title = "Unknown";
        String description = "No description available.";
        String infoUrl = "https://openlibrary.org";
        int numOfPages = 0;

        try {
            title = JsonPath.read(document, "$.*.title");
        } catch (Exception ignored) {}

        try {
            description = JsonPath.read(document, "$.*.subtitle");
        } catch (Exception ignored) {}

        try {
            infoUrl = JsonPath.read(document, "$.*.info_url");
        } catch (Exception ignored) {}

        try {
            numOfPages = JsonPath.read(document, "$.*.number_of_pages");
        } catch (Exception ignored) {}

        return new Book(isbn, title, description, infoUrl, numOfPages);
    }
}
