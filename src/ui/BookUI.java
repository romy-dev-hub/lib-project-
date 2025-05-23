package ui;

import dao.BookDao;
import model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookUI {

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
        JLabel idLabel = createStyledLabel("Book ID:");
        JTextField idField = createStyledTextField();
        JLabel titleLabel = createStyledLabel("Title:");
        JTextField titleField = createStyledTextField();
        JLabel authorLabel = createStyledLabel("Author:");
        JTextField authorField = createStyledTextField();
        JLabel categoryLabel = createStyledLabel("Category:");
        JTextField categoryField = createStyledTextField();
        JLabel yearLabel = createStyledLabel("Year:");
        JTextField yearField = createStyledTextField();
        JLabel quantityLabel = createStyledLabel("Quantity:");
        JTextField quantityField = createStyledTextField();
        JLabel searchLabel = createStyledLabel("Search (Title/Author):");
        JTextField searchField = createStyledTextField();
        JLabel deleteLabel = createStyledLabel("Delete Book ID:");
        JTextField deleteField = createStyledTextField();

        JButton addButton = createStyledButton("Add Book");
        JButton listButton = createStyledButton("Display Books");
        JButton searchButton = createStyledButton("Search");
        JButton deleteButton = createStyledButton("Delete");
        JButton backButton = createStyledButton("← Back to Dashboard");

        JTextArea resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        resultArea.setBackground(new Color(255, 245, 200));
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(212, 160, 23), 2, true));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // GridBagLayout setup
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(titleLabel, gbc);
        gbc.gridx = 1; formPanel.add(titleField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(authorLabel, gbc);
        gbc.gridx = 1; formPanel.add(authorField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(categoryLabel, gbc);
        gbc.gridx = 1; formPanel.add(categoryField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(yearLabel, gbc);
        gbc.gridx = 1; formPanel.add(yearField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(quantityLabel, gbc);
        gbc.gridx = 1; formPanel.add(quantityField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(addButton, gbc);
        gbc.gridx = 1; formPanel.add(listButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(searchLabel, gbc);
        gbc.gridx = 1; formPanel.add(searchField, gbc);
        gbc.gridx = 0; gbc.gridy = 8; formPanel.add(searchButton, gbc);
        gbc.gridx = 0; gbc.gridy = 9; formPanel.add(deleteLabel, gbc);
        gbc.gridx = 1; formPanel.add(deleteField, gbc);
        gbc.gridx = 0; gbc.gridy = 10; formPanel.add(deleteButton, gbc);

        // Scrollable form panel
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setOpaque(false);
        formScrollPane.getViewport().setOpaque(false);
        formScrollPane.setBorder(BorderFactory.createEmptyBorder());
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setPreferredSize(new Dimension(400, 400)); // Adjusted for form content

        // Action Listeners
        addButton.addActionListener(e -> {
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

                idField.setText("");
                titleField.setText("");
                authorField.setText("");
                categoryField.setText("");
                yearField.setText("");
                quantityField.setText("");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        listButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Book b : BookDao.getAllBooks()) {
                sb.append("ID: ").append(b.getBook_id())
                        .append(" | Title: ").append(b.getTitle())
                        .append(" | Author: ").append(b.getAuthor())
                        .append(" | Category: ").append(b.getCategory())
                        .append(" | Year: ").append(b.getPublication_year())
                        .append(" | Available: ").append(b.getAvailable_quantity())
                        .append("\n");
            }
            resultArea.setText(sb.toString());
        });

        searchButton.addActionListener(e -> {
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
            searchField.setText("");
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(deleteField.getText());
                boolean deleted = BookDao.deleteBook(id);
                resultArea.setText(deleted ? "Book deleted successfully!" : "Failed to delete book.");
                deleteField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid ID format!");
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