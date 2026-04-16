package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Modal dialog that lists administrators and allows adding, deleting and
 * initiating password changes. Uses LoginController for persistence and to
 * refresh the administrator list.
 * 
 * @author Iker
 */
public class ListAdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	private JTable table;
	private Image backgroundImage = new ImageIcon("images/CruiseBackground.png").getImage();

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
		setSize(663, 450);
		setLocationRelativeTo(null);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel title = new JLabel("Administrator Management");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(202, 13, 249, 49);
		title.setFont(new Font("SansSerif", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		title.setBorder(new EmptyBorder(5, 5, 15, 5));
		contentPane.add(title);

		loadTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 59, 625, 271);

		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		table.setOpaque(false);
		table.setBackground(new Color(255, 255, 255, 180));

		contentPane.add(scrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(159, 340, 319, 45);
		buttonPanel.setOpaque(false);

		btnADD = new JButton("ADD");
		btnADD.setBounds(20, 10, 88, 25);
		btnDELETE = new JButton("DELETE");
		btnDELETE.setBounds(216, 10, 88, 25);
		btnMODIFY = new JButton("MODIFY");
		btnMODIFY.setBounds(118, 10, 88, 25);

		btnADD.addActionListener(this);
		btnDELETE.addActionListener(this);
		btnMODIFY.addActionListener(this);

		btnADD.setBackground(new Color(63, 117, 243));
		btnADD.setForeground(Color.WHITE);

		btnDELETE.setBackground(new Color(63, 117, 243));
		btnDELETE.setForeground(Color.WHITE);

		btnMODIFY.setBackground(new Color(63, 117, 243));
		btnMODIFY.setForeground(Color.WHITE);
		buttonPanel.setLayout(null);
		buttonPanel.add(btnADD);
		buttonPanel.add(btnMODIFY);
		buttonPanel.add(btnDELETE);

		contentPane.add(buttonPanel);
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
		List<Administrator> administrators = cont.getAllAdministrators();
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		String logged = cont.getLoggedInAdminName();
		int i = 0;
		while (i < administrators.size()) {
			Administrator admin = administrators.get(i);
			String passToShow = "";
			if (admin.getName().trim().equalsIgnoreCase(logged.trim())) {
				passToShow = admin.getPassword();//show the one logged
			} else {
				passToShow = "********";//hide the rest
			}
			modelo.addRow(new Object[]{admin.getName(), passToShow});
			i++;
		}
	}

}