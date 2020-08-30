package com.kenavo.bookstore.controllers;

import com.kenavo.bookstore.models.Book;
import com.kenavo.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @RequestMapping("/{id}")
    public Book find(@PathVariable @NotNull Long id) {
        try {
            Optional<Book> book = bookRepo.findById(id);
            return book.get();
        } catch (NoSuchElementException exception) {
            System.out.println("Book not found");
            System.out.println(exception);
        }
        return null;
    }

    @RequestMapping("/findAll")
    public List<Book> findAll() {
        List<Book> books  = bookRepo.findAll();
        if(books.size() == 0)
            System.out.println("Nothing found");
        return books;
    }

    @RequestMapping("/count")
    public Long countAll() {
        Long sumOfBooks = bookRepo.count();
        if (sumOfBooks == null)
            return Long.valueOf(0);
        return sumOfBooks;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Book create(@RequestBody @NotNull Book book) {
        bookRepo.save(book);
        return book;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam("id") @NotNull Long id) {
        bookRepo.deleteById(id);
    }
}
