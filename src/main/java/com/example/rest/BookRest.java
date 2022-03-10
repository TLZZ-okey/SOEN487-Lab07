package com.example.rest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Objects;
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
        return books.stream().filter(book1 -> book1.getTitle() == title)
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
    public Response createNewBook(@HeaderParam("x-api-key") String token, @FormParam("title") String title, @FormParam("author") String author, @FormParam("isbn") String isbn) throws GeneralSecurityException, IOException {
        if(validateToken(token)){
            books.add(new Book(title,author,isbn));
            return Response.status(Response.Status.OK).entity("Succesfully added book!").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated!").build();
    }

    @PUT
    @Path("{isbn}")
    //@Consumes("application/json")
    public Response modifyBook(@HeaderParam("x-api-key") String token, @PathParam("isbn") String isbn, @PathParam("title") String title, @PathParam("author") String author){
        if(validateToken(token)){
            Book book = books.stream().filter(book1-> Objects.equals(book1.getIsbn(), isbn))
                    .findFirst()
                    .orElse(null);
            if(book != null){
                book.setTitle(title);
                book.setAuthor(author);
                return Response.status(Response.Status.OK).entity("Succesfully modified book!").build();
            }
            else{
                return Response.status(Response.Status.OK).entity("Book does not exist!").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated!").build();
    }

    @DELETE
    @Path("{isbn}")
    public Response deleteBook(@HeaderParam("x-api-key") String token, @PathParam("isbn") String isbn){
        if(validateToken(token)){
            books = books.stream().filter(customer -> !Objects.equals(customer.getIsbn(), isbn))
                    .collect(Collectors.toCollection(ArrayList::new));
            return Response.status(Response.Status.OK).entity("Succesfully deleted book!").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated!").build();
    }

    /**
     * This method sends a POST request to the User API, to verify that a call is done
     * by an authenticated user
     * @param token generated from login
     * @return boolean if the user is authenticated or not
     */
    private boolean validateToken(String token) {
        // Setting the truststore to our server.jks file let Java trust the certificate.
        System.setProperty("javax.net.debug", "ssl");
        System.setProperty("javax.net.ssl.trustStore","localhost.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "SOEN487-lab07");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("https://localhost:8443/Library/user/auth"));
            httpPost.addHeader("x-api-key", token);
            CloseableHttpResponse httpResponse = client.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String isAuthenticated = EntityUtils.toString(entity);
            httpResponse.close();
            if(isAuthenticated.equals("true")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
