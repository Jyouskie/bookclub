package com.bookclub.web;

import com.bookclub.model.Book;
import com.bookclub.service.impl.MemBookDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showHome(Model model) {
        // Load books from DAO
        MemBookDao booksDao = new MemBookDao();
        List<Book> books = booksDao.list();
        model.addAttribute("books", books);
        return "index";
    }

    @RequestMapping("/about")
    public String showAboutUs() {
        return "about";
    }

    @RequestMapping("/contact")
    public String showContactUs() {
        return "contact";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getMonthlyBook(@PathVariable("id") String id, Model model) {
        MemBookDao booksDao = new MemBookDao();
        Book book = booksDao.find(id);
        model.addAttribute("book", book);
        return "monthly-books/view";
    }
}


