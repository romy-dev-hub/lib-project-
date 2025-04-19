package dao;

import database.DBConnection;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    //add new book
    public static boolean addBook(Book book) {
        String sql = "INSERT INTO book (book_id, title, author, category,  publication_year, available_quantity) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, book.getBook_id());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getCategory());
            ps.setInt(5, book.getPublication_year());
            ps.setInt(6, book.getAvailable_quantity());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get all books
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_quantity")
                );
                books.add(book);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    //update a book
    public static boolean updateBook(Book book){
        String sql = "UPDATE book SET title=?, author=?, category=?, publication_year=?, available_quantity=? WHERE book_id=?";
        try(Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getPublication_year());
            ps.setInt(5, book.getAvailable_quantity());
            ps.setInt(6, book.getBook_id());
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //delete a book
    public static boolean deleteBook(int id_book){
        String sql = "DELETE FROM book WHERE book_id=?";
        try(Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id_book);
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //search for a book
    public static List<Book> searchBook (String keyword){
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(category) LIKE ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){

            String search = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_quantity")
                );
                result.add(book);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
