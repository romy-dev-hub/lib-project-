package ui;

import dao.LoanDao;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Loan;

import java.util.List;

public class LoanUI {

    public static Scene getScene() {
        Label studentIdLabel = new Label("Student ID:");
        TextField studentIdField = new TextField();

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Label loanDateLabel = new Label("Loan Date (YYYY-MM-DD):");
        TextField loanDateField = new TextField();

        Label expectedReturnLabel = new Label("Expected Return Date:");
        TextField expectedReturnField = new TextField();

        Label actualReturnLabel = new Label("Actual Return Date:");
        TextField actualReturnField = new TextField();

        Label loanIdReturnLabel = new Label("Loan ID to return:");
        TextField loanIdReturnField = new TextField();

        Label deleteLabel = new Label("Delete Loan ID:");
        TextField deleteField = new TextField();

        Button loanBtn = new Button("Register Loan");
        Button returnBtn = new Button("Return Book");
        Button displayBtn = new Button("Display All Loans");
        Button deleteButton = new Button("Delete Loan");
        Button backButton = new Button("← Back to Dashboard");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        loanBtn.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                String loanDate = loanDateField.getText();
                String expectedReturn = expectedReturnField.getText();
                String actualReturn = actualReturnField.getText();

                Loan loan = new Loan(studentId, bookId, loanDate, expectedReturn, actualReturn, "In progress");
                boolean added = LoanDao.InsertLoan(loan);
                resultArea.setText(added ? "Loan registered!" : "Failed to register loan.");

                // Clear fields
                studentIdField.clear();
                bookIdField.clear();
                loanDateField.clear();
                expectedReturnField.clear();
                actualReturnField.clear();
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid number format!");
            }
        });

        returnBtn.setOnAction(e -> {
            try {
                int loanId = Integer.parseInt(loanIdReturnField.getText());
                boolean returned = LoanDao.return_book(loanId);
                resultArea.setText(returned ? "Book returned!" : "Failed to return book.");
                loanIdReturnField.clear();
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid loan ID format!");
            }
        });

        displayBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            List<Loan> allLoans = LoanDao.getAllLoans();
            for (Loan l : allLoans) {
                sb.append("Loan ID: ").append(l.getLoanId())
                        .append(" | Book ID: ").append(l.getIdBook())
                        .append(" | Student ID: ").append(l.getStudentId())
                        .append(" | Status: ").append(l.getStatus())
                        .append("\n");
            }
            resultArea.setText(sb.toString());
        });

        deleteButton.setOnAction(e -> {
            try {
                int loanId = Integer.parseInt(deleteField.getText());
                boolean deleted = LoanDao.deleteLoan(loanId);
                resultArea.setText(deleted ? "Loan deleted successfully!" : "Failed to delete loan.");
                deleteField.clear();
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid loan ID format!");
            }
        });

        backButton.setOnAction(e -> SceneController.switchScene(DashboardUI.getDashboardScene()));

        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        form.add(studentIdLabel, 0, 0);     form.add(studentIdField, 1, 0);
        form.add(bookIdLabel, 0, 1);        form.add(bookIdField, 1, 1);
        form.add(loanDateLabel, 0, 2);      form.add(loanDateField, 1, 2);
        form.add(expectedReturnLabel, 0, 3);form.add(expectedReturnField, 1, 3);
        form.add(actualReturnLabel, 0, 4);  form.add(actualReturnField, 1, 4);
        form.add(loanBtn, 0, 5);            form.add(displayBtn, 1, 5);
        form.add(loanIdReturnLabel, 0, 6);  form.add(loanIdReturnField, 1, 6);
        form.add(returnBtn, 0, 7);          form.add(deleteLabel, 0, 8);
        form.add(deleteField, 1, 8);        form.add(deleteButton, 0, 9);

        VBox layout = new VBox(15, form, backButton, resultArea);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-ui");
        layout.getStyleClass().add("loan-ui-bg");

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(LoanUI.class.getResource("/style.css").toExternalForm());

        return scene;
    }
}