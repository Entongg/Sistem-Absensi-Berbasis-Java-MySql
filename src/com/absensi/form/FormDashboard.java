package com.absensi.form;

import com.absensi.main.Form;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormDashboard extends Form {
    
    private JLabel lblGreeting;
    private JLabel lblDateTime;
    private JPanel statsPanel;
    private JPanel mainContentPanel;
    
    public FormDashboard() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 10, fill, wrap", "fill", "[]0[fill, grow]"));
        
        // Header Section
        createHeader();
        
        // Main Content Section
        createMainContent();
        
        // Start timer for updating date and time
        startDateTimeTimer();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new MigLayout("insets 10", "[grow][right]", "[]10[]"));
        headerPanel.setBackground(new Color(245, 247, 250));
        
        // Greeting and User Info
        JPanel userPanel = new JPanel(new MigLayout("insets 0", "[][]", ""));
        lblGreeting = new JLabel("Hello, Admin!");
        lblGreeting.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JLabel lblUserEmail = new JLabel("admin@gmail.com");
        lblUserEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserEmail.setForeground(Color.GRAY);
        
        userPanel.add(lblGreeting, "wrap");
        userPanel.add(lblUserEmail);
        userPanel.setOpaque(false);
        
        // Date and Time
        JPanel datetimePanel = new JPanel(new MigLayout("insets 0", "[right]", "[]0[]"));
        lblDateTime = new JLabel();
        lblDateTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel lblSystemInfo = new JLabel("Java v23.0.2");
        lblSystemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblSystemInfo.setForeground(Color.GRAY);
        
        datetimePanel.add(lblDateTime, "wrap");
        datetimePanel.add(lblSystemInfo);
        datetimePanel.setOpaque(false);
        
        headerPanel.add(userPanel, "grow");
        headerPanel.add(datetimePanel);
        
        add(headerPanel, "grow, span");
    }
    
    private void createMainContent() {
        mainContentPanel = new JPanel(new MigLayout("insets 20, gap 20", "[grow][grow]", "[][grow]"));
        mainContentPanel.setBackground(Color.WHITE);
        
        // Stats Cards
        createStatsCards();
        
        // Recent Activity/Summary
        createRecentActivity();
        
        add(mainContentPanel, "grow");
    }
    
    private void createStatsCards() {
        statsPanel = new JPanel(new MigLayout("insets 0, gap 15", "[grow][grow][grow]", ""));
        statsPanel.setOpaque(false);
        
        // Card 1: Total Students
        addStatCard("Total Students", "35", new Color(41, 128, 185), "👥");
        
        // Card 2: Total Teachers
        addStatCard("Total Teachers", "1", new Color(39, 174, 96), "👨‍🏫");
        
        // Card 3: Today's Attendance
        addStatCard("Today's Attendance", "85%", new Color(142, 68, 173), "✓");
        
        statsPanel.add(new JSeparator(), "span 3, grow, wrap 20");
        
        mainContentPanel.add(statsPanel, "span 2, grow, wrap");
    }
    
    private void addStatCard(String title, String value, Color color, String icon) {
        JPanel card = new JPanel(new MigLayout("insets 15", "[][grow]", "[][][]"));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        
        // Title and Value
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        // Progress bar for attendance
        if (title.equals("Today's Attendance")) {
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(85);
            progressBar.setForeground(color);
            progressBar.setBackground(new Color(230, 230, 230));
            progressBar.setStringPainted(true);
            progressBar.setString("85%");
            card.add(progressBar, "span 2, grow, wrap");
        }
        
        card.add(iconLabel, "span 1 2");
        card.add(titleLabel, "wrap");
        card.add(valueLabel, "wrap");
        
        statsPanel.add(card, "grow");
    }
    
    private void createRecentActivity() {
        // Left Panel: Quick Actions
        JPanel quickActionsPanel = new JPanel(new MigLayout("insets 15", "[grow]", "[]10[]10[]"));
        quickActionsPanel.setBackground(Color.WHITE);
        quickActionsPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        
        addQuickActionButton("Take Attendance", "📝", new Color(41, 128, 185), quickActionsPanel);
        addQuickActionButton("Add Student", "👨‍🎓", new Color(39, 174, 96), quickActionsPanel);
        addQuickActionButton("Generate Report", "📊", new Color(142, 68, 173), quickActionsPanel);
        
        // Right Panel: System Info
        JPanel systemInfoPanel = new JPanel(new MigLayout("insets 15", "[grow]", "[]10[]"));
        systemInfoPanel.setBackground(Color.WHITE);
        systemInfoPanel.setBorder(BorderFactory.createTitledBorder("System Information"));
        
        // Version Info
        JLabel versionLabel = new JLabel("MyAbsents v1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Build Info
        JLabel buildInfoLabel = new JLabel("<html>"
            + "<b>Running on:</b> Java v23.0.2<br>"
            + "<b>Last Updated:</b> 2025-12-10 08:21:51<br>"
            + "<b>Status:</b> <font color='green'>● Online</font>"
            + "</html>");
        buildInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        systemInfoPanel.add(versionLabel, "wrap");
        systemInfoPanel.add(buildInfoLabel, "wrap");
        
        mainContentPanel.add(quickActionsPanel, "grow");
        mainContentPanel.add(systemInfoPanel, "grow");
    }
    
    private void addQuickActionButton(String text, String icon, Color color, JPanel panel) {
        JButton button = new JButton("<html><center>" + icon + "<br>" + text + "</center></html>");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 60));
        
        panel.add(button, "grow");
    }
    
    private void startDateTimeTimer() {
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();
        updateDateTime(); // Initial call
    }
    
    private void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy   HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        lblDateTime.setText(formattedDateTime);
    }
    
    // Method to update greeting based on time of day
    public void updateGreeting(String username) {
        int hour = LocalDateTime.now().getHour();
        String greeting;
        
        if (hour < 12) {
            greeting = "Good Morning";
        } else if (hour < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        
        lblGreeting.setText(greeting + ", " + username + "!");
    }
    
    // Method to update stats (can be called from outside)
    public void updateStats(int totalStudents, int totalTeachers, int attendancePercentage) {
        // Implementation to update the stats cards
        // You would need to store references to the stat labels
    }
}