package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.dao.impl.MongoWishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistRestController {

    @Autowired
    private MongoWishlistDao wishlistDao;

    // ✅ Get all wishlist items for the logged-in user
    @GetMapping
    public List<WishlistItem> list(Authentication authentication) {
        String username = authentication.getName();
        return wishlistDao.list(username);
    }

    // ✅ Add a new wishlist item
    @PostMapping
    public WishlistItem add(@RequestBody WishlistItem wishlistItem, Authentication authentication) {
        wishlistItem.setUsername(authentication.getName());
        wishlistDao.add(wishlistItem);
        return wishlistItem;
    }

    // ✅ Update an existing wishlist item
    @PutMapping("/{id}")
    public WishlistItem update(@PathVariable String id,
                               @RequestBody WishlistItem updatedItem,
                               Authentication authentication) {
        updatedItem.setId(id);
        updatedItem.setUsername(authentication.getName());
        wishlistDao.update(updatedItem);
        return updatedItem;
    }

    // ✅ Delete a wishlist item
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        wishlistDao.remove(username, id);
    }
}

