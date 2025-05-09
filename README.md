# Library Management System

A JavaFX-based application for managing library operations including book loans, student records, and inventory management.

![Screenshot](resources/pics/dashboard.png) <!-- Add your own screenshots later -->

## Features

- **Book Management**
    - Add/Delete books with manual ID
    - Search books by title/author
    - Display all books with detailed information
    - Track available quantities

- **Student Management**
    - Add/Delete student records
    - Search students by name/ID
    - Display all student information

- **Loan Management**
    - Register new book loans
    - Track book returns
    - Delete loan records
    - Monitor loan statuses

- **Dashboard Interface**
    - Easy navigation between modules
    - Responsive UI with visual feedback
    - Error handling and input validation

## Prerequisites

- Java JDK 17+
- JavaFX 19+
- Oracle Database 11g+
- Maven/Gradle (recommended)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/lib-project.git
   ```
2. Database Setup:
````
ALTER SESSION SET nls_date_format = 'dd,MM,yyyy'
/

CREATE TABLE student(
    student_id NUMBER (10),
    firstname VARCHAR2 (20) NOT NULL,
    lastname VARCHAR2 (20) NOT NULL,
    email VARCHAR2 (50),
    phone_number VARCHAR2 (10),
    CONSTRAINT student PRIMARY KEY (student_id),
    CONSTRAINT eamil_check CHECK ( REGEXP_LIKE(email, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.com$')) 
)
/

CREATE TABLE book (
    book_id NUMBER(10),
    title VARCHAR2(40) NOT NULL,
    author VARCHAR2(30)NOT NULL,
    category VARCHAR2(20),
    publication_year  NUMBER(4) NOT NULL,
    available_quantity  NUMBER (15) NOT NULL, 
    CONSTRAINT book_pk PRIMARY KEY (book_id)
)
/

CREATE TABLE loan(
    loan_id NUMBER (10),
    student_id NUMBER (10),
    book_id NUMBER(10),
    loan_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    statuts VARCHAR2 (10) NOT NULL,
    CONSTRAINT loan_pk PRIMARY KEY (loan_id),
    CONSTRAINT loan_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE SET NULL,
    CONSTRAINT loan_book FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE SET NULL
    )
/
commit 
/
````
3. Configure database connection in DBConnection.java:
````
public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
}
````
## Running the Application
1. Import as Maven/Gradle project
2. Set VM options for JavaFX:
````
--module-path /path/to/javafx-sdk-19 --add-modules javafx.controls,javafx.fxml
````
3. Run DashboardUI.java

### Command Line:
````
mvn clean javafx:run
````

## Project Structure
├── resources/

│       ├── style.css    # CSS styles

│       └── pics/        # Background images

├── src/

│├──app/

│├── ui/          # JavaFX controllers and views

│├── dao/         # Database operations

│└── model/       # Data classes

## License
MIT License

## Acknowledgments
- Oracle JDBC Driver
- JavaFX Framework
- OpenJDK


   
