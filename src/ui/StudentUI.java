package ui;

import dao.StudentDao;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Student;

public class StudentUI {

    public static Scene getScene() {
        Label idLabel = new Label("Student ID:");
        TextField idField = new TextField();

        Label nameLabel = new Label("First Name:");
        TextField nameField = new TextField();

        Label surnameLabel = new Label("Last Name:");
        TextField surnameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField();

        Label searchLabel = new Label("Search (Name/ID):");
        TextField searchField = new TextField();

        Label deleteLabel = new Label("Delete Student ID:");
        TextField deleteField = new TextField();

        Button addButton = new Button("Add Student");
        Button displayButton = new Button("Display Students");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("← Back to Dashboard");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                Student student = new Student(id, name, surname, email, phone);
                boolean added = StudentDao.addStudent(student);
                resultArea.setText(added ? "Student added successfully!" : "Failed to add student.");

                // Clear input fields
                idField.clear();
                nameField.clear();
                surnameField.clear();
                emailField.clear();
                phoneField.clear();
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid ID format!");
            }
        });

        displayButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (Student s : StudentDao.getAllStudents()) {
                sb.append("ID: ").append(s.getStudent_id())
                        .append(" | Name: ").append(s.getFirst_name())
                        .append(" ").append(s.getLast_name())
                        .append(" | Email: ").append(s.getEmail())
                        .append("\n");
            }
            resultArea.setText(sb.toString());
        });

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            StringBuilder sb = new StringBuilder();
            for (Student s : StudentDao.getAllStudents()) {
                if (String.valueOf(s.getStudent_id()).contains(query) ||
                        s.getFirst_name().toLowerCase().contains(query) ||
                        s.getLast_name().toLowerCase().contains(query)) {
                    sb.append("ID: ").append(s.getStudent_id())
                            .append(" | Name: ").append(s.getFirst_name())
                            .append(" ").append(s.getLast_name())
                            .append(" | Phone: ").append(s.getPhone_number())
                            .append("\n");
                }
            }
            resultArea.setText(sb.toString());
            searchField.clear();
        });

        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(deleteField.getText());
                boolean deleted = StudentDao.deleteStudent(id);
                resultArea.setText(deleted ? "Student deleted successfully!" : "Failed to delete student.");
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

        form.add(idLabel, 0, 0);       form.add(idField, 1, 0);
        form.add(nameLabel, 0, 1);     form.add(nameField, 1, 1);
        form.add(surnameLabel, 0, 2);  form.add(surnameField, 1, 2);
        form.add(emailLabel, 0, 3);    form.add(emailField, 1, 3);
        form.add(phoneLabel, 0, 4);    form.add(phoneField, 1, 4);
        form.add(addButton, 0, 5);     form.add(displayButton, 1, 5);
        form.add(searchLabel, 0, 6);  form.add(searchField, 1, 6);
        form.add(searchButton, 0, 7); form.add(deleteLabel, 0, 8);
        form.add(deleteField, 1, 8);  form.add(deleteButton, 0, 9);

        VBox layout = new VBox(15, form, backButton, resultArea);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-ui");
        layout.getStyleClass().add("student-ui-bg");

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(StudentUI.class.getResource("/style.css").toExternalForm());

        return scene;
    }
}