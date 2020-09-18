package io.hashimait.services;

import io.hashimait.domains.Book;
import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.CacheInvalidate;
import io.micronaut.cache.annotation.CachePut;
import io.micronaut.cache.annotation.Cacheable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
@CacheConfig("books")
public class BookService {


    @Inject
   private  ArrayList<Book> books;

    @CachePut(parameters = {"book.isbn"})
    public Boolean add(String isbn, Book book){

        if(!book.equalsIgnoreCase(isbn)) return false;

        return books.add(book);
    }


    @CacheInvalidate(parameters = {"isbn"})
    public Book remove(String isbn)
    {
        int x= 0;
        for(Book b : books)
        {
          if(b.equalsIgnoreCase(isbn)){
              break;
          }
          x++;
        }
        return books.remove(x);
    }

    @Cacheable
    public ArrayList<Book> bookArrayList()
    {
        return books;

    }
}
