package com.example.client;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Scanner;

public class BookClient{
    public static void main(String[] args) throws InterruptedException{
        putBook("MotoGP_Champion","Fabio_Quartararo","123456");
        putBook("Sherlock_Holmes","Arthur_Conan_Doyle","654321");
        System.out.println(getBooks("MotoGP_Champion"));

        Thread.sleep(5000);

        putBook("Harry_Potter","J_K_Rowling","123654");
        putBook("Star_Wars","Georges_Lucas","321456");
        System.out.println(getBooks("Star_Wars"));
        System.out.println(getBooks("Sherlock_Holmes"));

        Thread.sleep(5000);

        putBook("How_to_get_graded","Concordia_University","321654");
        System.out.println(getBooks("Harry_Potter"));
        System.out.println(getBooks("How_to_get_graded"));
    }

    public static String getBooks(String title){
        // Create closeable http client to execute requests with
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Creating the request to execute
            HttpGet httpget = new HttpGet("http://localhost:8080/Library/Book/"+title);

            // Executing the request using the http client and obtaining the response
            CloseableHttpResponse response = client.execute(httpget);
            return readResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to get books";
        }
    }

    public static void putBook(String title, String author, String isbn){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut httpput = new HttpPut("http://localhost:8080/Library/Book/"+title+"/"+author+"/"+isbn);
            client.execute(httpput);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String readResponse(CloseableHttpResponse response) throws IOException {
        // Handling the IO Stream from the response using scanner
        Scanner sc = new Scanner(response.getEntity().getContent());
        StringBuilder stringResponse = new StringBuilder();
        while (sc.hasNext()) {
            stringResponse.append(sc.nextLine());
            stringResponse.append("\n");
        }
        response.close();
        return stringResponse.toString();
    }

}