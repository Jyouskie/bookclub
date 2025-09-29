package com.bookclub.service.impl;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.dao.WishlistDao;

import java.util.ArrayList;
import java.util.List;

public class MemWishlistDao implements WishlistDao {

    private List<WishlistItem> wishlist;

    public MemWishlistDao() {
        wishlist = new ArrayList<>();
        // Add some starter wishlist items
        wishlist.add(new WishlistItem("123", "Spring Recipes"));
        wishlist.add(new WishlistItem("456", "Clean Architecture"));
        wishlist.add(new WishlistItem("789", "Effective Java"));
    }

    @Override
    public List<WishlistItem> list() {
        return wishlist;
    }

    @Override
    public WishlistItem find(String isbn) {
        for (WishlistItem item : wishlist) {
            if (item.getIsbn().equals(isbn)) {
                return item;
            }
        }
        return null;
    }
}
