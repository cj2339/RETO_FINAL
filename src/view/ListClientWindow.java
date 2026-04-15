package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Client;

/**
 * Modal dialog that displays the list of clients and allows adding, modifying
 * and deleting clients. Interacts with the LoginController to perform
 * persistence operations and prevents deletion when the client has bookings.
 */
public class ListClientWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private LoginController cont;
    private JPanel contentPane;
    private JButton btnADD, btnDELETE, btnMODIFY;
    private JTable table;

    public ListClientWindow(JFrame mainWindow, LoginController cont) {
        super(mainWindow, true);
        this.cont = cont;

        setTitle("Clients Management");
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Llamamos a fillTable igual que en Worker
        fillTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 44, 710, 231);
        contentPane.add(scrollPane);

        btnADD = new JButton("ADD");
        btnADD.setBounds(150, 303, 97, 25);
        btnADD.addActionListener(this);
        contentPane.add(btnADD);

        btnDELETE = new JButton("DELETE");
        btnDELETE.setBounds(320, 343, 97, 25);
        btnDELETE.addActionListener(this);
        contentPane.add(btnDELETE);

        btnMODIFY = new JButton("MODIFY");
        btnMODIFY.setBounds(500, 303, 97, 25);
        btnMODIFY.addActionListener(this);
        contentPane.add(btnMODIFY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();

        if (e.getSource() == btnDELETE) {
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a client to delete.");
            } else {
                String idClient = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?", "Warning", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Verificamos si tiene reservas antes de borrar
                    if (!cont.checkClientInBook(idClient)) {
                        // Creamos un objeto cliente auxiliar para el borrado
                        Client aux = new Client(idClient, null, null, 0, 0, null);
                        if (cont.deleteClient(aux)) {
                            refreshModel();
                            JOptionPane.showMessageDialog(this, "Client has been deleted.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error: Client not deleted.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Cannot delete: Client has active bookings.");
                    }
                }
            }
        }

        if (e.getSource() == btnMODIFY) {
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a client to modify.");
            } else {
                int modelRow = table.convertRowIndexToModel(row);
                TableModel model = table.getModel();
                
                // Extraemos los 6 campos asegurando los tipos
                Client client = new Client(
                    model.getValueAt(modelRow, 0).toString(),
                    model.getValueAt(modelRow, 1).toString(),
                    model.getValueAt(modelRow, 2).toString(),
                    Integer.parseInt(model.getValueAt(modelRow, 3).toString()),
                    Integer.parseInt(model.getValueAt(modelRow, 4).toString()),
                    model.getValueAt(modelRow, 5).toString()
                );
                
                FormClientWindow form = new FormClientWindow(this, cont, client, false);
                form.setVisible(true);
                refreshModel();
            }
        }

        if (e.getSource() == btnADD) {
            FormClientWindow form = new FormClientWindow(this, cont, null, true);
            form.setVisible(true);
            refreshModel();
        }
    }

    private void fillTable() {
        // Configuramos el modelo igual que en Worker
        DefaultTableModel model = new DefaultTableModel(
            new String[] { "ID", "Name", "Surname", "Age", "Phone", "Email" }, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshModel();
    }

    public void refreshModel() {
        List<Client> clients = cont.getAllClient();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Client c : clients) {
            model.addRow(new Object[] {
                c.getIdClient(), 
                c.getNameClient(), 
                c.getSurnameClient(), 
                c.getAgeClient(), 
                c.getPhoneClient(), 
                c.getEmailClient()
            });
        }
    }
}