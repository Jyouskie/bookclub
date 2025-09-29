package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.impl.MemWishlistDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    // Show wishlist
    @GetMapping
    public String showWishlist(Model model) {
        MemWishlistDao dao = new MemWishlistDao();
        List<WishlistItem> wishlist = dao.list();
        model.addAttribute("wishlist", wishlist);
        return "wishlist/list";
    }

    // Show form for new wishlist item
    @GetMapping("/new")
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    // Handle form submission
    @PostMapping
    public String addWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        }
        // Not persisting to DB, just redirecting
        return "redirect:/wishlist";
    }
}
