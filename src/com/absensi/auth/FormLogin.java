package com.absensi.auth;

import com.absensi.dao.UserDAO;
import com.absensi.main.Form;
import com.absensi.main.FormManager;
import com.absensi.model.User;
import com.absensi.service.ServiceUser;
import static com.absensi.util.AlertUtils.getOptionalAlert;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;

public class FormLogin extends Form{
    private final ServiceUser servis = new UserDAO();
    private JLabel imageLogo;
    private JPanel mainPanel;
    private JPanel panelForm;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    public FormLogin(){
        init();
        setActionButton();
    }
    
    private void init(){
        setLayout(new MigLayout("fill,insets 20","[center]","[center]"));
        
        mainPanel = new JPanel(new MigLayout("insets 50","[][]","[fill][grow]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:rgb(255,255,255);"
                + "[dark]background:darken($Panel.background, 5%)");
         
        JPanel panelLogo = new JPanel(new MigLayout("wrap", "300", ""));
        panelLogo.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:rgb(255,255,255);"
                + "[dark]background:darken($Panel.background, 5%)");
        
        imageLogo = new JLabel();
        imageLogo.setIcon(new FlatSVGIcon("com/absensi/icon/logo_qrcode.svg", 100, 100));
        
        JLabel lbTitleLogo = new JLabel("MyAbsensi");
        lbTitleLogo.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:@accentColor;"
                + "font:bold italic +14");
        
        JLabel lbDetail = new JLabel("Attendance Management System");
        lbDetail.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(51,51,51);"
                + "font:bold 16");
        
        JLabel lbCreated = new JLabel("Created by Kelompok Cepuluh");
        lbCreated.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(14,140,140);"
                + "font:12");
        
        panelLogo.add(imageLogo, "align center, gapy 5, gap 25px 0px");
        panelLogo.add(lbTitleLogo, "align center, gapy 5, gap 25px 0px");
        panelLogo.add(lbDetail, "align center, gapy 5, gap 25px 0px");
        panelLogo.add(lbCreated, "align center, gapy 5, gap 25px 0px");
        
        panelForm = new JPanel(new MigLayout("wrap, insets 20", "fill, 200:250"));
        panelForm.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:@accentColor;"
                + "[dark]background:darken($Panel.background, 5%)");
        
        JLabel lbTitleForm = new JLabel("Login", JLabel.CENTER);
        lbTitleForm.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(255,255,255);"
                + "font:bold +10");
        
        JLabel lbDesciption = new JLabel("Please sign in to access your dashboard", JLabel.CENTER);
        lbDesciption.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(255,255,255);");
        
        JLabel lbUsername = new JLabel("Username");
        lbUsername.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(255,255,255);");
        
        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        txtUsername.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtUsername.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("com/absensi/icon/username.svg", 20, 20));
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "arc:10");
        
        JLabel lbPassword = new JLabel("Password");
        lbPassword.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:rgb(255,255,255);");
        
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("com/absensi/icon/Password.svg", 20, 20));
        txtPassword.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "showRevealButton:true;");
        
        btnLogin = new JButton("Login");
        btnLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:rgb(255,255,255);"
                + "[dark]foreground:@accentColor;"
                + "arc:10;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "font:bold 16");
        
        panelForm.add(lbTitleForm);
        panelForm.add(lbDesciption);
        panelForm.add(lbUsername, "gapy:8");
        panelForm.add(txtUsername, "hmin 30");
        panelForm.add(lbPassword, "gapy:8");
        panelForm.add(txtPassword, "hmin 30");
        panelForm.add(btnLogin, "hmin 30, gapy 15 15");
        
        mainPanel.add(panelForm);
        mainPanel.add(panelLogo); 
        
        add(mainPanel);
        
        btnLogin.addActionListener((e) -> {
           processLogin();
         });
    }
    
    private void setActionButton() {
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                     processLogin();
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                   processLogin();
            }
        });
    }
    
    private boolean validateInput() {
        boolean valid = false;
        if (txtUsername.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Username cannot be empty", getOptionalAlert());
            txtUsername.putClientProperty("JComponent.outline", "error");
        } else if (txtPassword.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Password cannot be empty", getOptionalAlert());
            txtPassword.putClientProperty("JComponent.outline", "error");
        } else {
            valid = true;
            txtUsername.putClientProperty("JComponent.outline", null);
            txtPassword.putClientProperty("JComponent.outline", null);
        }
        return valid;
}

    private void processLogin() {
        if (validateInput()== true) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            User modelUser = new User();
            modelUser.setUsername(username);
            modelUser.setPassword(password);
            
            User user = servis.processLogin(modelUser);
            if (user != null) {
                FormManager.login(user);
                resetForm();
            } else{
                Toast.show(this, Toast.Type.ERROR, "Incorrect username or password. Please try again", getOptionalAlert());
            }
        }
    }
    
    private void resetForm(){
        txtUsername.setText("");
        txtPassword.setText("");
    }
}
