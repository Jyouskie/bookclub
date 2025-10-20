package com.bookclub.web;


import com.bookclub.dao.WishlistDao;
import com.bookclub.dao.impl.MongoWishlistDao;
import com.bookclub.model.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/wishlist", produces = "application/json")
@CrossOrigin(origins = "*")
public class WishlistRestController {

    private WishlistDao wishlistDao = new MongoWishlistDao();

    @Autowired
    public void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    // Return all wishlist items
    @GetMapping
    public List<WishlistItem> showWishlist() {
        return wishlistDao.list();
    }

    // Find a specific item by ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WishlistItem findById(@PathVariable String id) {
        return wishlistDao.find(id);
    }
}
