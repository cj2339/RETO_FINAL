package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Client;

/**
 * Dialog that displays a form to add or modify a Client. Validates numeric
 * fields and delegates insert/update actions to the LoginController.
 */
public class FormClientWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel panel = new JPanel();
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtAge;
    private JTextField txtPhone; 
    private JTextField txtEmail; 
    private JButton okButton;
    private JButton cancelButton;
    private boolean isInsert;
    private LoginController controller;
    private Client client;
    private ListClientWindow parent;

    public FormClientWindow(JDialog clientManagement, LoginController controller, Client client, boolean isInsert) {
        super(clientManagement, true);
        setTitle(isInsert ? "Add Client" : "Modify Client");
        // He aumentado el alto de la ventana a 450 para que quepan los campos nuevos
        setBounds(100, 100, 476, 450);
        getContentPane().setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        this.isInsert = isInsert;
        this.controller = controller;
        this.client = client;
        this.parent = (ListClientWindow) clientManagement;

        txtId = new JTextField();
        txtId.setColumns(10);
        txtId.setBounds(266, 40, 116, 22);
        panel.add(txtId);

        txtName = new JTextField();
        txtName.setColumns(10);
        txtName.setBounds(266, 90, 116, 22);
        panel.add(txtName);

        txtSurname = new JTextField();
        txtSurname.setColumns(10);
        txtSurname.setBounds(266, 140, 116, 22);
        panel.add(txtSurname);

        txtAge = new JTextField();
        txtAge.setColumns(10);
        txtAge.setBounds(266, 190, 116, 22);
        panel.add(txtAge);
        
       
        txtAge.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        txtPhone = new JTextField();
        txtPhone.setColumns(10);
        txtPhone.setBounds(266, 240, 116, 22);
        panel.add(txtPhone);
        txtPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        
        txtEmail = new JTextField();
        txtEmail.setColumns(10);
        txtEmail.setBounds(266, 290, 116, 22);
        panel.add(txtEmail);

        JLabel lblId = new JLabel("Client ID:");
        lblId.setBounds(52, 43, 88, 16);
        panel.add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(52, 93, 56, 16);
        panel.add(lblName);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setBounds(52, 143, 88, 16);
        panel.add(lblSurname);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(52, 193, 56, 16);
        panel.add(lblAge);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(52, 243, 56, 16);
        panel.add(lblPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(52, 293, 56, 16);
        panel.add(lblEmail);

        
        if (this.client != null) {
            txtId.setText(client.getIdClient());
            txtId.setEnabled(false); 
            txtName.setText(client.getNameClient());
            txtSurname.setText(client.getSurnameClient());
            txtAge.setText(String.valueOf(client.getAgeClient()));
            txtPhone.setText(String.valueOf(client.getPhoneClient()));
            txtEmail.setText(client.getEmailClient());
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            if (txtAge.getText().isEmpty() || txtPhone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in Age and Phone with numbers.");
                return;
            }

            if (isInsert) {
                Client newClient = new Client(
                    txtId.getText(),
                    txtName.getText(),
                    txtSurname.getText(),
                    Integer.parseInt(txtAge.getText()),
                    Integer.parseInt(txtPhone.getText()), 
                    txtEmail.getText()                    
                );
                if (controller.insertClient(newClient)) {
                    JOptionPane.showMessageDialog(this, "Client has been inserted.");
                    parent.refreshModel();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Client not inserted.");
                }
            } else {
                Client updatedClient = new Client(
                    txtId.getText(),
                    txtName.getText(),
                    txtSurname.getText(),
                    Integer.parseInt(txtAge.getText()),
                    Integer.parseInt(txtPhone.getText()), 
                    txtEmail.getText()                    
                );
                if (controller.updateClientByCode(updatedClient)) {
                    JOptionPane.showMessageDialog(this, "Client has been updated.");
                    parent.refreshModel();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Client not updated.");
                }
            }
        }
        if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }
}