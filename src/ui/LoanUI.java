package ui;

import dao.LoanDao;
import model.Loan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoanUI {

    public static JPanel getPanel(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 248, 225), 0, getHeight(), new Color(245, 235, 200));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form components
        JLabel studentIdLabel = createStyledLabel("Student ID:");
        JTextField studentIdField = createStyledTextField();
        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();
        JLabel loanDateLabel = createStyledLabel("Loan Date (YYYY-MM-DD):");
        JTextField loanDateField = createStyledTextField();
        JLabel expectedReturnLabel = createStyledLabel("Expected Return Date:");
        JTextField expectedReturnField = createStyledTextField();
        JLabel actualReturnLabel = createStyledLabel("Actual Return Date:");
        JTextField actualReturnField = createStyledTextField();
        JLabel loanIdReturnLabel = createStyledLabel("Loan ID to return:");
        JTextField loanIdReturnField = createStyledTextField();
        JLabel deleteLabel = createStyledLabel("Delete Loan ID:");
        JTextField deleteField = createStyledTextField();

        JButton loanBtn = createStyledButton("Register Loan");
        JButton returnBtn = createStyledButton("Return Book");
        JButton displayBtn = createStyledButton("Display All Loans");
        JButton deleteButton = createStyledButton("Delete Loan");
        JButton backButton = createStyledButton("← Back to Dashboard");

        JTextArea resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        resultArea.setBackground(new Color(255, 245, 200));
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(212, 160, 23), 2, true));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // GridBagLayout setup
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(studentIdLabel, gbc);
        gbc.gridx = 1; formPanel.add(studentIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; formPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(loanDateLabel, gbc);
        gbc.gridx = 1; formPanel.add(loanDateField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(expectedReturnLabel, gbc);
        gbc.gridx = 1; formPanel.add(expectedReturnField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(actualReturnLabel, gbc);
        gbc.gridx = 1; formPanel.add(actualReturnField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(loanBtn, gbc);
        gbc.gridx = 1; formPanel.add(displayBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(loanIdReturnLabel, gbc);
        gbc.gridx = 1; formPanel.add(loanIdReturnField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(returnBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 8; formPanel.add(deleteLabel, gbc);
        gbc.gridx = 1; formPanel.add(deleteField, gbc);
        gbc.gridx = 0; gbc.gridy = 9; formPanel.add(deleteButton, gbc);

        // Scrollable form panel
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setOpaque(false);
        formScrollPane.getViewport().setOpaque(false);
        formScrollPane.setBorder(BorderFactory.createEmptyBorder());
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setPreferredSize(new Dimension(400, 400));

        // Action Listeners
        loanBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                String loanDate = loanDateField.getText();
                String expectedReturn = expectedReturnField.getText();
                String actualReturn = actualReturnField.getText();

                Loan loan = new Loan(studentId, bookId, loanDate, expectedReturn, actualReturn, "In progress");
                boolean added = LoanDao.InsertLoan(loan);
                resultArea.setText(added ? "Loan registered!" : "Failed to register loan.");

                studentIdField.setText("");
                bookIdField.setText("");
                loanDateField.setText("");
                expectedReturnField.setText("");
                actualReturnField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid number format!");
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int loanId = Integer.parseInt(loanIdReturnField.getText());
                boolean returned = LoanDao.return_book(loanId);
                resultArea.setText(returned ? "Book returned!" : "Failed to return book.");
                loanIdReturnField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid loan ID format!");
            }
        });

        displayBtn.addActionListener(e -> {
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

        deleteButton.addActionListener(e -> {
            try {
                int loanId = Integer.parseInt(deleteField.getText());
                boolean deleted = LoanDao.deleteLoan(loanId);
                resultArea.setText(deleted ? "Loan deleted successfully!" : "Failed to delete loan.");
                deleteField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid loan ID format!");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));

        // Layout
        panel.add(formScrollPane, BorderLayout.NORTH);
        panel.add(backButton, BorderLayout.WEST);
        panel.add(resultScrollPane, BorderLayout.SOUTH);
        return panel;
    }

    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Georgia", Font.BOLD, 16));
        label.setForeground(new Color(62, 39, 35));
        return label;
    }

    private static JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Open Sans", Font.PLAIN, 14));
        textField.setBackground(new Color(255, 245, 200));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 160, 23), 2, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Open Sans", Font.BOLD, 16));
        button.setBackground(new Color(46, 125, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 160, 23), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(76, 175, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(46, 125, 50));
            }
        });

        return button;
    }
}