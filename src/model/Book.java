package model;

public class Book {

    private int book_id;
    private String title;
    private String author;
    private String category;
    private int publication_year;
    private int available_quantity;

    public Book(int book_id, String title, String author, String category, int publication_year, int available_quantity){
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publication_year = publication_year;
        this.available_quantity = available_quantity;
    }

    //getters
    public int getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    //setters
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setTitle(String titre) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }
}
