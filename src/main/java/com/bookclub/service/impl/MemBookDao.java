package com.bookclub.service.impl;

import com.bookclub.model.Book;
import com.bookclub.dao.BookDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemBookDao implements BookDao {

    private List<Book> books;

    public MemBookDao() {
        books = new ArrayList<>();
        books.add(new Book("111", "Spring in Action", "Intro to Spring", 400, Arrays.asList("Craig Walls")));
        books.add(new Book("222", "Thymeleaf Basics", "Learn Thymeleaf", 250, Arrays.asList("John Doe")));
        books.add(new Book("333", "Java Mastery", "Deep dive into Java", 500, Arrays.asList("Jane Smith")));
        books.add(new Book("444", "Effective Testing", "JUnit and beyond", 300, Arrays.asList("Alan Tester")));
        books.add(new Book("555", "Clean Code", "Best practices in coding", 450, Arrays.asList("Robert C. Martin")));
    }

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public Book find(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}
