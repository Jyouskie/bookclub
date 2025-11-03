package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.dao.impl.MongoWishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private MongoWishlistDao wishlistDao;

    // ✅ Show wishlist items for logged-in user
    @GetMapping
    public String showWishlist(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<WishlistItem> wishlist = wishlistDao.list(username);
        model.addAttribute("wishlist", wishlist);
        return "wishlist/list";
    }

    // ✅ Show form for new wishlist item
    @GetMapping("/new")
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    // ✅ Handle new wishlist item submission
    @PostMapping
    public String addWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem,
                                  BindingResult bindingResult,
                                  Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        }

        wishlistItem.setUsername(authentication.getName());
        wishlistDao.add(wishlistItem);
        return "redirect:/wishlist";
    }

    // ✅ Show form to edit an existing item
    @GetMapping("/{id}")
    public String showWishlistItem(@PathVariable String id, Model model) {
        WishlistItem item = wishlistDao.find(id);
        model.addAttribute("wishlistItem", item);
        return "wishlist/view";
    }

    // ✅ Handle form submission for updating wishlist item
    @PostMapping("/update")
    public String updateWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem,
                                     BindingResult bindingResult,
                                     Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "wishlist/view";
        }

        wishlistItem.setUsername(authentication.getName());
        wishlistDao.update(wishlistItem);
        return "redirect:/wishlist";
    }

    // ✅ Delete an item from wishlist
    @GetMapping("/remove/{id}")
    public String removeWishlistItem(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        wishlistDao.remove(username, id);
        return "redirect:/wishlist";
    }
}
