package com.bookclub.dao.impl;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.GenericCrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoWishlistDao implements GenericCrudDao<WishlistItem, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "wishlistItems";

    // ✅ Create
    @Override
    public void add(WishlistItem wishlistItem) {
        mongoTemplate.insert(wishlistItem, COLLECTION_NAME);
    }

    // ✅ Update
    @Override
    public void update(WishlistItem wishlistItem) {
        Query query = new Query(Criteria.where("id").is(wishlistItem.getId())
                .and("username").is(wishlistItem.getUsername()));
        Update update = new Update()
                .set("isbn", wishlistItem.getIsbn())
                .set("title", wishlistItem.getTitle())
                .set("username", wishlistItem.getUsername());

        mongoTemplate.updateFirst(query, update, WishlistItem.class, COLLECTION_NAME);
    }

    // ✅ Delete — matches `boolean remove(String username, K key)`
    @Override
    public boolean remove(String username, String id) {
        Query query = new Query(Criteria.where("id").is(id).and("username").is(username));
        return mongoTemplate.remove(query, WishlistItem.class, COLLECTION_NAME).wasAcknowledged();
    }

    // ✅ List — matches `List<E> list(String username)`
    @Override
    public List<WishlistItem> list(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.find(query, WishlistItem.class, COLLECTION_NAME);
    }

    // ✅ Find — same as before
    @Override
    public WishlistItem find(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, WishlistItem.class, COLLECTION_NAME);
    }
}
