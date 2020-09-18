package io.hashimait.controller;

import io.hashimait.domains.Book;
import io.hashimait.services.BookService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.ArrayList;

@Controller
public class BookController {


    @Inject
    private BookService bookService;


    @Post("/add/{isbn}")
    public Boolean add(String isbn, Book book){

        return bookService.add(isbn, book);

    }

    @Delete("/del/{isbn}")
    public Book remove(String isbn)
    {

        return bookService.remove(isbn);

    }

    @Get("/books")
    public ArrayList<Book> bookArrayList()
    {

        return bookService.bookArrayList();

    }
}
