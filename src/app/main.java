package app;

import dao.BookDao;
import model.Book;

import dao.StudentDao;
import model.Student;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args){
        /*
        Book book1 = new Book(001, "all about sql", "boudour rachid", "education", 2023 , 158);
      //  Book book2 = new Book(002, "java", "martin jonz", "education", 2012, 206);

        if (BookDao.addBook(book1)){
            System.out.println("book added with success");
        }else {
            System.out.println("book addition failed");
        }

        System.out.println("list of books: ");
        for (Book l : BookDao.getAllBooks()){
            System.out.println(l.getTitle() + " by "+ l.getAuthor());
        }


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter keyword to search for books: ");
        String keyword = scanner.nextLine();

        List<Book> results = BookDao.searchBook(keyword);
        if (results.isEmpty()) {
            System.out.println("No books found matching your search, babe.");
        } else {
            System.out.println("Books found:");
            for (Book book : results) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }



        Scanner scanner = new Scanner(System.in);


        // 1. Add a student
        Student newStudent = new Student(1, "Romy", "Hadibi", "romy@email.com", "0555123456");

        if (StudentDao.addStudent(newStudent)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }

         */

        // update student
        //Student newStudent = new Student(1, "xiao roro", null, null, null);
        Student student = new Student(1, "Romina", null, null, null);
        boolean updated = StudentDao.updateStudent(student);

        if (updated) {
            System.out.println("First name updated successfully, babe!");
        } else {
            System.out.println("Update failed.");
        }
    }
}
