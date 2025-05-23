package ui;

import dao.StudentDao;
import model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentUI {

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
        JLabel idLabel = createStyledLabel("Student ID:");
        JTextField idField = createStyledTextField();
        JLabel nameLabel = createStyledLabel("First Name:");
        JTextField nameField = createStyledTextField();
        JLabel surnameLabel = createStyledLabel("Last Name:");
        JTextField surnameField = createStyledTextField();
        JLabel emailLabel = createStyledLabel("Email:");
        JTextField emailField = createStyledTextField();
        JLabel phoneLabel = createStyledLabel("Phone:");
        JTextField phoneField = createStyledTextField();
        JLabel searchLabel = createStyledLabel("Search (Name/ID):");
        JTextField searchField = createStyledTextField();
        JLabel deleteLabel = createStyledLabel("Delete Student ID:");
        JTextField deleteField = createStyledTextField();

        JButton addButton = createStyledButton("Add Student");
        JButton displayButton = createStyledButton("Display Students");
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
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(surnameLabel, gbc);
        gbc.gridx = 1; formPanel.add(surnameField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; formPanel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(addButton, gbc);
        gbc.gridx = 1; formPanel.add(displayButton, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(searchLabel, gbc);
        gbc.gridx = 1; formPanel.add(searchField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(searchButton, gbc);
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
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                Student student = new Student(id, name, surname, email, phone);
                boolean added = StudentDao.addStudent(student);
                resultArea.setText(added ? "Student added successfully!" : "Failed to add student.");

                idField.setText("");
                nameField.setText("");
                surnameField.setText("");
                emailField.setText("");
                phoneField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid ID format!");
            }
        });

        displayButton.addActionListener(e -> {
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

        searchButton.addActionListener(e -> {
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
            searchField.setText("");
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(deleteField.getText());
                boolean deleted = StudentDao.deleteStudent(id);
                resultArea.setText(deleted ? "Student deleted successfully!" : "Failed to delete student.");
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