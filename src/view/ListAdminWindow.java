package view;

import java.awt.Toolkit;
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
import model.Administrator;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Modal dialog that lists administrators and allows adding, deleting and
 * initiating password changes. Uses LoginController for persistence and to
 * refresh the administrator list.
 */
public class ListAdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	JTable table;

	/**
	 * Constructs the administrator list window.
	 *
	 * @param mainWindow The parent main window.
	 * @param cont       The login controller.
	 */
	public ListAdminWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		setTitle("Administrators");
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

	/**
	 * Handles action events from the Add, Delete, and Modify buttons.
	 *
	 * @param e The action event object.
	 */
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();

		if (e.getSource() == btnDELETE) {
		    if (row == -1) {
		        JOptionPane.showMessageDialog(this, "Select an administrator to delete.");
		        return;
		    }

		    String adminName = table.getValueAt(row, 0).toString();

		    int confirm = JOptionPane.showConfirmDialog(
		            this,
		            "Are you sure you want to delete administrator: " + adminName + "?",
		            "Confirm deletion",
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.WARNING_MESSAGE
		    );

		    if (confirm == JOptionPane.YES_OPTION) {
		        if (cont.deleteAdministrator(adminName)) {
		            refreshModel();
		            JOptionPane.showMessageDialog(this, "Administrator has been deleted.");
		        } else {
		            JOptionPane.showMessageDialog(this, "Administrator not deleted.");
		        }
		    }
		}

		if (e.getSource() == btnMODIFY) {

			Administrator admin = new Administrator();
			int viewRow = table.getSelectedRow();
			if (viewRow != -1) {
				int modelRow = table.convertRowIndexToModel(viewRow);
				TableModel model = table.getModel();
				String adminName = (String) model.getValueAt(modelRow, 0);
				// Open the password change window
				ChangePasswordAdminWindow adminWindow = new ChangePasswordAdminWindow(this, cont, adminName);
				adminWindow.setVisible(true);

				refreshModel();
			} else {
				JOptionPane.showMessageDialog(this, "Select an administrator to modify");
			}
		}
		if (e.getSource() == btnADD) {
			FormAdminWindow formAdminWindow = new FormAdminWindow(this, cont, null, true);
			formAdminWindow.setVisible(true);
			refreshModel();

		}
	}

	/**
	 * Initializes the table model and properties.
	 */
	private void loadTable() {
		// Create a non-editable table template
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Name", "Password" },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// This is to ensure the table cannot be edited
		};
		table = new JTable(modelo);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// Allow only one row to be selected
		refreshModel();
	}

	/**
	 * Refreshes the table data by requesting all administrators from the database.
	 */
	public void refreshModel() {
		List<Administrator> administrators = cont.getAllAdministrators(); //Get the updated list of administrators
		DefaultTableModel modelo = (DefaultTableModel) table.getModel(); //Retrieve the table model
		//Limpiar tabla
		modelo.setRowCount(0);
		//Fill in the table
		for (Administrator admin : administrators) {
			modelo.addRow(new Object[] {admin.getName(),admin.getPassword()});
		}
	}
}