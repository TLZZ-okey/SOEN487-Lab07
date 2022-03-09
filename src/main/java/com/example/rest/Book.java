package com.example.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    @XmlElement
    private String isbn;
    @XmlElement
    private String author;
    @XmlElement
    private String title;

    public Book(String isbn, String author, String title){
        this.isbn = isbn;
        this.author = author;
        this.title = title;
    }

    public Book(Book book){
        this.title=book.getTitle();
        this.author=book.getAuthor();
        this.isbn=book.getIsbn();
    }

    public Book(){

    }

    public String getIsbn(){
        return this.isbn;
    }

    public void setIsbn(){ this.isbn = isbn; }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getTitle(){ return this.title; }

    public void setTitle(String title) { this.title = title; }

    public String toString() {
        return String.format("[Title : %s] - [Author : %s] - [ISBN : %s]", this.getTitle(), this.getAuthor(), this.getIsbn());
    }
}
