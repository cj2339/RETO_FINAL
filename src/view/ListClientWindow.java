package view;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Client;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListClientWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private LoginController cont;
    private JPanel contentPane;
    private JButton btnADD;
    private JButton btnDELETE;
    private JButton btnMODIFY;
    JTable table;

    public ListClientWindow(JFrame mainWindow, LoginController cont) {
        super(mainWindow, true);
        setTitle("Clients");
        this.cont = cont;

        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 607, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        loadTable();

        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 44, 567, 231);
        getContentPane().add(scrollPane);

        btnADD = new JButton("ADD");
        btnADD.setBounds(96, 303, 97, 25);
        btnADD.addActionListener(this);
        contentPane.add(btnADD);

        btnDELETE = new JButton("DELETE");
        btnDELETE.setBounds(247, 343, 97, 25);
        btnDELETE.addActionListener(this);
        contentPane.add(btnDELETE);

        btnMODIFY = new JButton("MODIFY");
        btnMODIFY.addActionListener(this);
        btnMODIFY.setBounds(400, 303, 97, 25);
        contentPane.add(btnMODIFY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();

        if (e.getSource() == btnDELETE) {
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a client to delete.");
                return;
            }

            String idClient = table.getValueAt(row, 0).toString();
            Client client = new Client(idClient, null, null, 0);

            if (!cont.checkClientInBook(idClient)) {
                if (cont.deleteClient(client)) {
                    refreshModel();
                    JOptionPane.showMessageDialog(this, "Client has been deleted.");
                } else {
                    JOptionPane.showMessageDialog(this, "Client not deleted.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Client not deleted because exists in Book");
            }
        }

        if (e.getSource() == btnMODIFY) {
            if (row != -1) {
                int modelRow = table.convertRowIndexToModel(row);
                TableModel model = table.getModel();
                Client client = new Client(
                    (String) model.getValueAt(modelRow, 0),
                    (String) model.getValueAt(modelRow, 1),
                    (String) model.getValueAt(modelRow, 2),
                    (Integer) model.getValueAt(modelRow, 3)
                );
                FormClientWindow formClientWindow = new FormClientWindow(this, cont, client, false);
                formClientWindow.setVisible(true);
                refreshModel();
            } else {
                JOptionPane.showMessageDialog(this, "Select a client to modify");
            }
        }

        if (e.getSource() == btnADD) {
            FormClientWindow formClientWindow = new FormClientWindow(this, cont, null, true);
            formClientWindow.setVisible(true);
            refreshModel();
        }
    }

    private void loadTable() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Name", "Surname", "Age"}, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(modelo);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshModel();
    }

    public void refreshModel() {
        List<Client> clients = cont.getAllClient();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0);
        for (Client client : clients) {
            modelo.addRow(new Object[]{
                client.getIdClient(),
                client.getNameClient(),
                client.getSurnameClient(),
                client.getAgeClient()
            });
        }
    }
}