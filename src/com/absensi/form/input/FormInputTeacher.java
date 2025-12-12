package com.absensi.form.input;

import com.absensi.dao.TeacherDAO;
import com.absensi.form.FormTeacher;
import com.absensi.main.FormManager;
import com.absensi.model.Teacher;
import com.absensi.model.User;
import static com.absensi.util.AlertUtils.getOptionalAlert;
import com.absensi.service.ServiceTeacher;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.ModalDialog;
import raven.modal.Toast;

public class FormInputTeacher extends JPanel {

    private JTextField txtName;
    private JTextField txtNIP;

    private ButtonGroup groupGender;
    private JRadioButton rbMale;
    private JRadioButton rbFemale;

    private DatePicker datePicker;
    private JFormattedTextField dateEditor;

    private JTextArea txtAddress;
    private JTextField txtPhone;

    private JButton btnSave;
    private JButton btnCancel;

    private final ServiceTeacher servis = new TeacherDAO();
    private Teacher model;
    private FormTeacher formTeacher;
    private User loggedInUser;
    private int idTeacher;

    public FormInputTeacher(Teacher model, FormTeacher formTeacher) {
        init();

        this.loggedInUser = FormManager.getloggedInUser();
        this.model = model;
        this.formTeacher = formTeacher;

        if (model != null) {
            loadData();
        }
    }
    
    private void init() {
        setLayout(new MigLayout(
                "fillx, insets 5 30 5 30, wrap 2, gap 20, width 400",
                "[50][fill, grow]"
        ));

        txtName = new JTextField();
        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter teacher name");
        txtName.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        txtNIP = new JTextField();
        txtNIP.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter NIP");
        txtNIP.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");

        groupGender = new ButtonGroup();
        groupGender.add(rbMale);
        groupGender.add(rbFemale);

        rbMale.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.add(rbMale);
        radioPanel.add(rbFemale);

        datePicker = new DatePicker();
        datePicker.setCloseAfterSelected(true);
        dateEditor = new JFormattedTextField();
        datePicker.setEditor(dateEditor);

        txtAddress = new JTextArea();
        txtAddress.setWrapStyleWord(true);
        txtAddress.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(txtAddress);

        txtPhone = new JTextField();
        txtPhone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter phone number");
        txtPhone.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        btnSave = new JButton("Save");
        btnSave.setIcon(new FlatSVGIcon("com/absensi/icon/save_white.svg", 0.8f));
        btnSave.setIconTextGap(5);
        btnSave.putClientProperty(FlatClientProperties.STYLE, "background:@accentColor;foreground:rgb(255,255,255)");

        btnCancel = new JButton("Cancel");
        btnCancel.setIcon(new FlatSVGIcon("com/absensi/icon/cancel.svg", 0.8f));
        btnCancel.setIconTextGap(5);
        
        add(createSeparator(), "span, grow, height 20");
        add(new JLabel("Teacher Name"), "align right");
        add(txtName);
        add(new JLabel("NIP"), "align right");
        add(txtNIP);
        add(new JLabel("Gender"), "align right");
        add(radioPanel);
        add(new JLabel("Birth Date"), "align right");
        add(dateEditor);
        add(new JLabel("Address"), "align right");
        add(scroll, "w 100");
        add(new JLabel("Phone Number"), "align right");
        add(txtPhone);

        add(createSeparator(), "span, grow, height 20");
        add(btnSave, "span, split 2, align center, sg btn, h 30");
        add(btnCancel, "sg btn, h 30");

        btnSave.addActionListener((e) -> {
            if(model == null){
                insertData();
            } else {
                updateData();
            }
        });

        btnCancel.addActionListener((e) -> {
            if(model == null){
                ModalDialog.closeModal("form input");
            } else {
                ModalDialog.closeModal("form update");
            }
        });
    }
    
    private JSeparator createSeparator(){
        JSeparator separator = new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, "foreground:rgb(206,206,206)");
        return separator;
    }
    
    private boolean validasiInput(boolean isUpdate) {
        boolean valid = false;
        String nip = txtNIP.getText().trim();
        String currentNIP = isUpdate ? model.getNip() : null;

        if (txtName.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Please enter the teacher's name", getOptionalAlert());
        } else if (txtNIP.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Please enter the teacher's NIP", getOptionalAlert());
        } else if (!rbMale.isSelected() && !rbFemale.isSelected()) {
            Toast.show(this, Toast.Type.INFO, "Please select a gender", getOptionalAlert());
        } else if (datePicker.getSelectedDate() == null) {
            Toast.show(this, Toast.Type.INFO, "Please select a date", getOptionalAlert());
        } else if (txtAddress.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Please enter the teacher's address", getOptionalAlert());
        } else if (txtPhone.getText().trim().isEmpty()) {
            Toast.show(this, Toast.Type.INFO, "Please enter the teacher's phone number", getOptionalAlert());
        } else {
            if (isUpdate && nip.equals(currentNIP)) {
                valid = true;
            } else {
                Teacher modelTeacher = new Teacher();
                modelTeacher.setNip(nip);
                if (servis.validateINIP(modelTeacher)) {
                    valid = true;
                } else {
                    Toast.show(this, Toast.Type.WARNING, "This NIP is already taken\nPlease choose another one", getOptionalAlert());
                }
            }
        }

        return valid;
    }
    
    private void insertData() {
        if (validasiInput(false)) {
            String name = txtName.getText();
            String nip = txtNIP.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";

            LocalDate selectedDate = datePicker.getSelectedDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birthDate = selectedDate.format(formatter);

            String address = txtAddress.getText();
            String phone = txtPhone.getText();

            Teacher modelTeacher = new Teacher();
            modelTeacher.setTeachername(name);
            modelTeacher.setNip(nip);
            modelTeacher.setGender(gender);
            modelTeacher.setBirthDate(birthDate);
            modelTeacher.setAddress(address);
            modelTeacher.setPhone(phone);
            modelTeacher.setInsertBy(loggedInUser.getIdUser());

            servis.insertData(modelTeacher);
            Toast.show(this, Toast.Type.SUCCESS, "Data has been successfully added", getOptionalAlert());

            formTeacher.refreshTable();
            resetForm();
        }
    }

    private void loadData() {
        btnSave.setText("Update");

        idTeacher = model.getIdTeacher();
        txtName.setText(model.getTeachername());
        txtNIP.setText(model.getNip());

        if (model != null) {
            if (model.getGender().equals("Male")) {
                rbMale.setSelected(true);
            } else if (model.getGender().equals("Female")) {
                rbFemale.setSelected(true);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(model.getBirthDate(), formatter);
        datePicker.setSelectedDate(birthDate);

        txtAddress.setText(model.getAddress());
        txtPhone.setText(model.getPhone());
    }


    private void updateData() {
        if (validasiInput(true)) {
            String name = txtName.getText();
            String nip = txtNIP.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";

            LocalDate selectedDate = datePicker.getSelectedDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birthDate = selectedDate.format(formatter);

            String address = txtAddress.getText();
            String phone = txtPhone.getText();

            Teacher modelTeacher = new Teacher();
            modelTeacher.setIdTeacher(idTeacher);
            modelTeacher.setTeachername(name);
            modelTeacher.setNip(nip);
            modelTeacher.setGender(gender);
            modelTeacher.setBirthDate(birthDate);
            modelTeacher.setAddress(address);
            modelTeacher.setPhone(phone);
            modelTeacher.setUpdateBy(loggedInUser.getIdUser());

            servis.updateData(modelTeacher);
            Toast.show(this, Toast.Type.SUCCESS, "Data has been successfully updated", getOptionalAlert());

            formTeacher.refreshTable();
            resetForm();
            ModalDialog.closeModal("from updtae");
        }
    }

    private void resetForm() {
        txtName.setText("");
        txtNIP.setText("");
        groupGender.clearSelection();
        datePicker.clearSelectedDate();
        txtAddress.setText("");
        txtPhone.setText("");
    }

}

