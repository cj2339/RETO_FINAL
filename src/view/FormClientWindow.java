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

public class FormClientWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel panel = new JPanel();
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtAge;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isInsert;
    private LoginController controller;
    private Client client;
    private ClientManagement parent;

    public FormClientWindow(JDialog clientManagement, LoginController controller, Client client, boolean isInsert) {
        super(clientManagement, true);
        setBounds(100, 100, 476, 350);
        getContentPane().setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        this.isInsert = isInsert;
        this.controller = controller;
        this.client = client;
        this.parent = (ClientManagement) clientManagement;

        txtId = new JTextField();
        txtId.setColumns(10);
        txtId.setBounds(266, 60, 116, 22);
        panel.add(txtId);

        txtName = new JTextField();
        txtName.setColumns(10);
        txtName.setBounds(266, 112, 116, 22);
        panel.add(txtName);

        txtSurname = new JTextField();
        txtSurname.setColumns(10);
        txtSurname.setBounds(266, 171, 116, 22);
        panel.add(txtSurname);

        txtAge = new JTextField();
        txtAge.setColumns(10);
        txtAge.setBounds(266, 230, 116, 22);
        panel.add(txtAge);
        // solo numeros en age
        txtAge.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        JLabel lblId = new JLabel("Client ID:");
        lblId.setBounds(52, 63, 88, 16);
        panel.add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(52, 115, 56, 16);
        panel.add(lblName);

        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setBounds(52, 174, 88, 16);
        panel.add(lblSurname);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(52, 233, 56, 16);
        panel.add(lblAge);

        // si es modify rellena los campos
        if (this.client != null) {
            txtId.setText(client.getIdClient());
            txtId.setEnabled(false); // no se puede cambiar el id
            txtName.setText(client.getNameClient());
            txtSurname.setText(client.getSurnameClient());
            txtAge.setText(String.valueOf(client.getAgeClient()));
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
            if (isInsert) {
                Client client = new Client(
                    txtId.getText(),
                    txtName.getText(),
                    txtSurname.getText(),
                    Integer.parseInt(txtAge.getText())
                );
                if (controller.insertClient(client)) {
                    JOptionPane.showMessageDialog(this, "Client has been inserted.");
                    parent.refreshModel();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Client not inserted.");
                }
            } else {
                Client client = new Client(
                    txtId.getText(),
                    txtName.getText(),
                    txtSurname.getText(),
                    Integer.parseInt(txtAge.getText())
                );
                if (controller.updateClientByCode(client)) {
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