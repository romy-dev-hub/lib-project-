package model;

public class Loan {
    private static int counter = 0 ;
    private int loanId;
    private int studentId;
    private int bookId;
    private String loanDate;
    private String expectedReturnDate;
    private String actualReturnDate;
    private String status;

    // Default constructor
    //public Loan() {}

    // Constructor with all fields
    public Loan(int studentId, int bookId, String loanDate,
                String expectedReturnDate, String actualReturnDate, String status) {
        counter ++;
        this.loanId = counter;
        this.studentId = studentId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
    }

    // Getters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getIdBook() {
        return bookId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getStringExpectedReturnDate() {
        return expectedReturnDate;
    }

    public String getActualReturnDate() {
        return actualReturnDate;
    }

    public String getStatus() {
        return status;
    }

    //setters
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setExpectedReturnDate(String expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "Loan{" +
                "\nloanId=" + loanId +
                "\nstudentId=" + studentId +
                "\nbookId=" + bookId +
                "\nDate loan=" + loanDate +
                "\nexpectedReturnDate=" + expectedReturnDate +
                "\nactualReturnDate=" + actualReturnDate +
                "\nstatus='" + status + '\'' +
                '}';
    }
}