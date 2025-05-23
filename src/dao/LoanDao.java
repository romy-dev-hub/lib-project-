package dao;

import database.DBConnection;
import model.Loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanDao {


    private static final Connection conn = DBConnection.connect();
    private static final DateTimeFormatter DB_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static boolean decrease_book_qty(int loanId, int book_id) {
        String sql = "UPDATE BOOK SET available_quantity = ? WHERE BOOK_ID = ?";
        String qu = "SELECT available_quantity FROM book WHERE book_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(qu)) {
            ps.setInt(1, book_id);
            ResultSet rs = ps.executeQuery();

            // Check if result set has data
            if (!rs.next()) {
                System.err.println("No book found with ID: " + book_id);
                return false;
            }

            int available_qty = rs.getInt("available_quantity") - 1;

            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                stm.setInt(1, available_qty);
                stm.setInt(2, book_id);
                return stm.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error decreasing book quantity: " + e.getMessage());
            return false;
        }
    }

    private static boolean increase_book_qty(int loanId) {
        String sql = "UPDATE BOOK SET available_quantity = ? WHERE BOOK_ID = ?";
        String sql2 = "SELECT b.book_id FROM book b, loan l WHERE l.book_id = b.book_id and l.loan_id = ?";
        String sql3 = "SELECT available_quantity FROM book WHERE book_id = ?";

        int book_id;

        // First get the book ID from the loan
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setInt(1, loanId);
            ResultSet rs = ps2.executeQuery();

            if (!rs.next()) {
                System.err.println("No loan found with ID: " + loanId);
                return false;
            }

            book_id = rs.getInt("book_id");
        } catch (SQLException e) {
            System.err.println("Error getting book ID from loan: " + e.getMessage());
            return false;
        }

        // Then update the quantity
        try (PreparedStatement pstmt = conn.prepareStatement(sql3)) {
            pstmt.setInt(1, book_id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.err.println("No book found with ID: " + book_id);
                return false;
            }

            int available_qty = rs.getInt("available_quantity") + 1; // Note: Changed to +1 for return

            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                stm.setInt(1, available_qty);
                stm.setInt(2, book_id);
                return stm.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error increasing book quantity: " + e.getMessage());
            return false;
        }
    }

    private static boolean check_if_late (int loanId){
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Step 1: Establish database connection (assuming 'conn' is your connection)
            // conn = ... (your connection code here)

            // Step 2: Query the expected return date for the given loan ID
            String sql = "SELECT expected_return_date FROM loan WHERE loan_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, loanId);

            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No loan found with ID: " + loanId);
                return false;
            }

            // Step 3: Get the expected return date from the result set
            java.sql.Date expectedReturnDate = rs.getDate("expected_return_date");
            LocalDate expectedDate = expectedReturnDate.toLocalDate();

            // Step 4: Get the current date
            LocalDate currentDate = LocalDate.now();

            // Step 5: Compare the current date with the expected return date
            return currentDate.isBefore(expectedDate);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean InsertLoan(Loan loan) {
        String sql = "INSERT INTO LOAN (loan_id, student_id, book_id, loan_date, expected_return_date, actual_return_date, statuts) " +
                "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Validate and parse dates
            LocalDate loanDate = LocalDate.parse(loan.getLoanDate(), DB_DATE_FORMAT);
            LocalDate expectedDate = LocalDate.parse(loan.getStringExpectedReturnDate(), DB_DATE_FORMAT);

            pstmt.setInt(1, loan.getLoanId());
            pstmt.setInt(2, loan.getStudentId());
            pstmt.setInt(3, loan.getIdBook());
            pstmt.setString(4, loan.getLoanDate());  // Will be formatted as YYYY-MM-DD
            pstmt.setString(5, loan.getStringExpectedReturnDate());

            // Handle actual return date (might be null)
            if (loan.getActualReturnDate() != null && !loan.getActualReturnDate().isEmpty()) {
                LocalDate actualDate = LocalDate.parse(loan.getActualReturnDate(), DB_DATE_FORMAT);
                pstmt.setString(6, loan.getActualReturnDate());
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            pstmt.setString(7, "In progress");

            // Decrease book quantity
            decrease_book_qty(loan.getLoanId(), loan.getIdBook());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error inserting loan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean return_book (int loanId){
        String sql = "UPDATE LOAN SET status = ?, actual_return_date = ? WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);){

            ps.setString(1,"Returned");
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setInt(3,loanId);
            increase_book_qty(loanId);
            return ps.executeUpdate() >0 ;
        }catch(SQLException e){
            e.printStackTrace();
            return  false ;
        }
    }

    public static void checkAndPrintAllLoans() {

        try {
            // Step 1: Establish database connection (assuming 'conn' is your connection)
            // conn = ... (your connection code here)

            // Step 2: Query all loans
            String sql = "SELECT * FROM loan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Step 3: Process each loan
            while (rs.next()) {
                int loanId = rs.getInt("loan_id");
                int studentId = rs.getInt("student_id");
                int bookId = rs.getInt("book_id");
                java.sql.Date loanDate = rs.getDate("loan_date");
                java.sql.Date expectedReturnDate = rs.getDate("expected_return_date");
                java.sql.Date actualReturnDate = rs.getDate("actual_return_date");
                String status = rs.getString("statuts");

                // Convert dates to LocalDate for easier comparison
                LocalDate currentDate = LocalDate.now();
                LocalDate expectedDate = expectedReturnDate.toLocalDate();

                // Check if the loan is overdue and status is not already "late" or "returned"
                if (status.equals("In progress") && currentDate.isAfter(expectedDate)) {
                    // Update status to "late"
                    updateLoanStatus(loanId, "late");
                    status = "late";
                } else if (actualReturnDate != null) {
                    // If actual return date is set, status should be "returned"
                    status = "returned";
                }

                // Print loan details
                System.out.println("Loan ID: " + loanId);
                System.out.println("Student ID: " + studentId);
                System.out.println("Book ID: " + bookId);
                System.out.println("Loan Date: " + loanDate);
                System.out.println("Expected Return Date: " + expectedReturnDate);
                System.out.println("Actual Return Date: " + actualReturnDate);
                System.out.println("Status: " + status);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateLoanStatus(int loanId, String newStatus) {
        PreparedStatement updateStmt = null;
        try {
            String updateSql = "UPDATE loan SET statuts = ? WHERE loan_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newStatus);
            updateStmt.setInt(2, loanId);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (updateStmt != null) updateStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Loan> getAllLoans(){
        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT * FROM loan";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Loan loan = new Loan(
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getString("loan_date"),
                        rs.getString("expected_return_date"),
                        rs.getString("actual_return_date"),
                        rs.getString("statuts")
                );
                loan.setLoanId(rs.getInt("loan_id"));
                loans.add(loan);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loans;
    }

    public static boolean deleteLoan(int loanId) {
        String sql = "DELETE FROM loan WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
