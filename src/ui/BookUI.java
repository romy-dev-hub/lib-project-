package ui;

import dao.BookDao;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Book;

public class BookUI {

    public static Scene getScene() {
        Label idLabel = new Label("Book ID:");
        TextField idField = new TextField();

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();

        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField();

        Label yearLabel = new Label("Year:");
        TextField yearField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label searchLabel = new Label("Search (Title/Author):");
        TextField searchField = new TextField();

        Label deleteLabel = new Label("Delete Book ID:");
        TextField deleteField = new TextField();

        Button addButton = new Button("Add Book");
        Button listButton = new Button("Display Books");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("← Back to Dashboard");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String title = titleField.getText();
                String author = authorField.getText();
                String category = categoryField.getText();
                int year = Integer.parseInt(yearField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Book book = new Book(id, title, author, category, year, quantity);
                boolean added = BookDao.addBook(book);

                resultArea.setText(added ? "Book added successfully!" : "Failed to add book.");

                // Clear input fields
                idField.clear();
                titleField.clear();
                authorField.clear();
                categoryField.clear();
                yearField.clear();
                quantityField.clear();
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        listButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (Book b : BookDao.getAllBooks()) {
                sb.append("ID: ").append(b.getBook_id())
                        .append(" | Title: ").append(b.getTitle())
                        .append(" | Author: ").append(b.getAuthor())  // Fixed this line
                        .append(" | Category: ").append(b.getCategory())
                        .append(" | Year: ").append(b.getPublication_year())
                        .append(" | Available: ").append(b.getAvailable_quantity())
                        .append("\n");
            }
            resultArea.setText(sb.toString());
        });

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            StringBuilder sb = new StringBuilder();
            for (Book b : BookDao.getAllBooks()) {
                if (b.getTitle().toLowerCase().contains(query) ||
                        b.getAuthor().toLowerCase().contains(query)) {
                    sb.append("ID: ").append(b.getBook_id())
                            .append(" | Title: ").append(b.getTitle())
                            .append(" | Author: ").append(b.getAuthor())
                            .append("\n");
                }
            }
            resultArea.setText(sb.toString());
            searchField.clear();
        });

        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(deleteField.getText());
                boolean deleted = BookDao.deleteBook(id);
                resultArea.setText(deleted ? "Book deleted successfully!" : "Failed to delete book.");
                deleteField.clear();
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid ID format!");
            }
        });

        backButton.setOnAction(e -> SceneController.switchScene(DashboardUI.getDashboardScene()));

        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // GridPane setup
        form.add(idLabel, 0, 0);       form.add(idField, 1, 0);
        form.add(titleLabel, 0, 1);    form.add(titleField, 1, 1);
        form.add(authorLabel, 0, 2);   form.add(authorField, 1, 2);
        form.add(categoryLabel, 0, 3); form.add(categoryField, 1, 3);
        form.add(yearLabel, 0, 4);     form.add(yearField, 1, 4);
        form.add(quantityLabel, 0, 5); form.add(quantityField, 1, 5);
        form.add(addButton, 0, 6);     form.add(listButton, 1, 6);
        form.add(searchLabel, 0, 7);   form.add(searchField, 1, 7);
        form.add(searchButton, 0, 8);
        form.add(deleteLabel, 0, 9);   form.add(deleteField, 1, 9);
        form.add(deleteButton, 0, 10);

        VBox layout = new VBox(15, form, backButton, resultArea);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-ui");
        layout.getStyleClass().add("book-ui-bg");

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(BookUI.class.getResource("/style.css").toExternalForm());

        return scene;
    }
}