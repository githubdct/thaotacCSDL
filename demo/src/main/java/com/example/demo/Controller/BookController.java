package com.example.demo.Controller;


import com.example.demo.Service.BookService;
import com.example.demo.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String showAllBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books",books);
        return "book/list";
    }
    @GetMapping("/add")
    public String addBookForm(Model model)
    {
        model.addAttribute("book",new Book());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "book/add";
    }


    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book")Book book , BindingResult result){
        if(result.hasErrors()){
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id")Long id){
        Book book = bookService.getBookById(id);
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String editBooks(@PathVariable("id") Long id, Model model){
        Book a = bookService.getBookById(id);
        if(a != null){
            model.addAttribute("book", a);
            //model.addAttribute("categories", categoryService.getCategory(a.getCategory().getId()));
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }
        else{
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book update, BindingResult result){
        Book a = bookService.getBookById(update.getId());
        if(result.hasErrors()){
            return "book/edit";
        }
        bookService.updateBook(update);
        return "redirect:/books";
    }


}
