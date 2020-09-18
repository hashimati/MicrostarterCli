package io.hashimait.repositories;

import io.hashimait.domains.Book;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;

import java.util.ArrayList;

@Factory
public class BookRepository {

    @Prototype
    public ArrayList<Book> books = new ArrayList<Book>();
}
