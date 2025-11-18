package com.bookclub.dao.impl;

import com.bookclub.dao.BookOfTheMonthDao;
import com.bookclub.model.BookOfTheMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookOfTheMonthDao")
public class MongoBookOfTheMonthDao implements BookOfTheMonthDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "booksOfTheMonth";

    @Override
    public void add(BookOfTheMonth book) {
        mongoTemplate.insert(book, COLLECTION_NAME);
    }

    @Override
    public void update(BookOfTheMonth book) {
        // Not required for this assignment, but you could implement similar to wishlist
    }

    @Override
    public boolean remove(String key, String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, BookOfTheMonth.class, COLLECTION_NAME).wasAcknowledged();
    }

    @Override
    public List<BookOfTheMonth> list(String key) {
        // “key” will just be a dummy value (“999”) since this isn’t user-specific
        return mongoTemplate.findAll(BookOfTheMonth.class, COLLECTION_NAME);
    }

    @Override
    public BookOfTheMonth find(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, BookOfTheMonth.class, COLLECTION_NAME);
    }
}

