package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class DashboardUI extends JFrame {

    private static JPanel dashboardPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DashboardUI() {
        setTitle("Library Management System - Dashboard");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Nimbus Look and Feel with customizations
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(38, 96, 41)); // Darker forest green
            UIManager.put("control", new Color(255, 245, 200)); // Parchment cream
            UIManager.put("text", new Color(56, 34, 28)); // Rich brown
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to default look-and-feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 245, 200), 0, getHeight(), new Color(240, 220, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Parchment texture effect
                g2d.setColor(new Color(255, 255, 255, 50));
                for (int i = 0; i < getWidth(); i += 10) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
            }
        };
        dashboardPanel = createDashboardPanel();
        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(BookUI.getPanel(cardLayout, cardPanel), "BookUI");
        cardPanel.add(StudentUI.getPanel(cardLayout, cardPanel), "StudentUI");
        cardPanel.add(LoanUI.getPanel(cardLayout, cardPanel), "LoanUI");

        add(cardPanel);
        cardLayout.show(cardPanel, "Dashboard");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title with embossed effect
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                Font font = getFont();
                g2d.setFont(font);
                FontMetrics fm = g2d.getFontMetrics();
                String text = getText();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = fm.getAscent() + (getHeight() - fm.getHeight()) / 2;

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(text, x + 2, y + 2);
                // Embossed effect
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.drawString(text, x - 1, y - 1);
                // Main text
                g2d.setColor(getForeground());
                g2d.drawString(text, x, y);
            }
        };
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 32));
        titleLabel.setForeground(new Color(56, 34, 28));
        titleLabel.setOpaque(false);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton bookBtn = createStyledButton("Manage Books");
        JButton studentBtn = createStyledButton("Manage Students");
        JButton loanBtn = createStyledButton("Manage Loans");

        bookBtn.addActionListener(e -> cardLayout.show(cardPanel, "BookUI"));
        studentBtn.addActionListener(e -> cardLayout.show(cardPanel, "StudentUI"));
        loanBtn.addActionListener(e -> cardLayout.show(cardPanel, "LoanUI"));

        // Decorative dividers
        JSeparator topDivider = new JSeparator(SwingConstants.HORIZONTAL);
        topDivider.setForeground(new Color(212, 160, 23));
        topDivider.setPreferredSize(new Dimension(200, 2));
        JSeparator bottomDivider = new JSeparator(SwingConstants.HORIZONTAL);
        bottomDivider.setForeground(new Color(212, 160, 23));
        bottomDivider.setPreferredSize(new Dimension(200, 2));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(topDivider);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(bookBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(studentBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(loanBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(bottomDivider);
        buttonPanel.add(Box.createVerticalGlue());

        // Decorative frame around buttons
        JPanel framePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(56, 34, 28, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                g2d.setColor(new Color(212, 160, 23));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
            }
        };
        framePanel.setOpaque(false);
        framePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        framePanel.add(buttonPanel, gbc);

        panel.add(framePanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text) {
        class StyledButton extends JButton {
            private boolean isHovered = false;

            public StyledButton(String text) {
                super(text);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient fill
                GradientPaint gp = new GradientPaint(0, 0, new Color(48, 106, 51), 0, getHeight(), new Color(38, 96, 41));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                // Glow effect on hover
                if (isHovered) {
                    g2d.setColor(new Color(212, 160, 23, 100));
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 13, 13);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
                super.paintComponent(g);
            }
        }

        StyledButton button = new StyledButton(text);
        button.setFont(new Font("Open Sans", Font.BOLD, 16));
        button.setBackground(new Color(38, 96, 41)); // Darker forest green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 160, 23), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.isHovered = true;
                button.setBackground(new Color(68, 136, 71)); // Lighter green
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.isHovered = false;
                button.setBackground(new Color(38, 96, 41));
                button.repaint();
            }
        });

        return button;
    }

    public static JPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardUI().setVisible(true));
    }
}