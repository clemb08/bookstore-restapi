package com.kenavo.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenavo.bookstore.models.Book;
import com.kenavo.bookstore.models.Language;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.JsonPathResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookController bookController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void CRUDShould() throws Exception {

        List<Book> books = new ArrayList<Book>();

        given(this.bookController.findAll())
                .willReturn(books);
        this.mockMvc.perform(get("/books/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        Book book = new Book("15645", "title", 22.00f, 101, Language.ENGLISH, new Date(), "image", "description");
        book.setId(1000L);

        given(this.bookController.create(book))
                .willReturn(book);
        this.mockMvc.perform(post("/books/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book))
        )
                .andExpect(status().isOk());

        books.add(book);

        given(this.bookController.findAll())
                .willReturn(books);
        this.mockMvc.perform(get("/books/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        Long bookId = book.getId();
        System.out.println(bookId);

        given(this.bookController.find(bookId))
                .willReturn(book);
        this.mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isOk()
                );


    }
}