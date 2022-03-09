package com.example.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("Book")
public class BookRest {

    private static ArrayList<Book> books = new ArrayList<>();

    @GET
    @Produces("application/json")
    public ArrayList<Book> getBook() {
        return books;
    }


    @GET
    @Produces("application/json")
    @Path("{title}")
    public Book getBookList(@PathParam("title") String title){
        return books.stream().filter(customer1 -> customer1.getTitle() == title)
                .findFirst()
                .orElse(null);

    }

    @POST
    @Consumes("application/json")
    public void createBook(Book book){
        books.add(new Book(book));
    }

    @POST
    @Path("/form")
    public void createNewBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("isbn") String isbn){
        books.add(new Book(title,author,isbn));
    }

    @PUT
    @Path("{title}")
    @Consumes("application/json")
    public void modifyBook(@PathParam("isbn") String isbn, Book book){
        deleteBook(isbn);
        books.add(new Book(book));
    }

    @DELETE
    @Path("{isbn}")
    public void deleteBook(@PathParam("isbn") String isbn){
        books = books.stream().filter(customer -> customer.getIsbn() != isbn)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
