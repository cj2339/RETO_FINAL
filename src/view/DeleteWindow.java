package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;
    private JButton btnDelete;
    private JButton btnCancelar;
    private LoginController cont;
    private String tipo;

    public DeleteWindow(JDialog parent, LoginController cont, String tipo) {
        super(parent, true);
        this.cont = cont;
        this.tipo = tipo;

        setTitle("Eliminate " + tipo);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Delete Window");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(150, 10, 149, 24);
        getContentPane().add(lblTitulo);

        JLabel lblCodigo = new JLabel("Select code:");
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 14));
        lblCodigo.setBounds(67, 65, 120, 20);
        getContentPane().add(lblCodigo);

        comboBox = new JComboBox<String>();
        comboBox.setBounds(200, 61, 169, 21);
        getContentPane().add(comboBox);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 190, 114, 34);
        btnDelete.addActionListener(this);
        getContentPane().add(btnDelete);

        btnCancelar = new JButton("Cancel");
        btnCancelar.setBounds(230, 190, 114, 34);
        btnCancelar.addActionListener(this);
        getContentPane().add(btnCancelar);

        cargarCombo();
    }

    private void cargarCombo() {
        ArrayList<String> codes = new ArrayList<>();

        switch (tipo) {
            case "cruise": 
            	codes = cont.getCruiseCodes(); 
            break;
            case "worker": 
            	codes = cont.getWorkerCodes(); 
            break;
            case "client": 
            	codes = cont.getClientCodes(); 
            break;
        }

        for (String code : codes) {
            comboBox.addItem(code);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(btnCancelar)) {
            dispose();
        }

        if (e.getSource().equals(btnDelete)) {
            String id = (String) comboBox.getSelectedItem();

            int confirmar = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + id + "?", "Confirm",
                    JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                switch (tipo) {
 //                   case "cruise": controller.deleteCruise(id); break;
 //                   case "worker": controller.deleteWorker(id); break;
 //                   case "client": controller.deleteClient(id); break;
                }
                JOptionPane.showMessageDialog(this, id + " deleted successfully");
                comboBox.removeItem(id);
            }
        }
    }
}


