package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Client;

/**
 * Modal dialog that displays the list of clients and allows adding, modifying
 * and deleting clients. Interacts with the LoginController to perform
 * persistence operations and prevents deletion when the client has bookings.
 * 
 * @author Santiago
 */
public class ListClientWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD, btnDELETE, btnMODIFY;
	private JTable table;
	private Image backgroundImage = new ImageIcon("images/CruiseBackground.png").getImage();

	/**
	 * Constructs the window showing all clients.
	 *
	 * @param mainWindow The main window that launches this dialog.
	 * @param cont       The program's login controller.
	 */
	public ListClientWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		this.cont = cont;

		setTitle("Clients Management");
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


		// TITTLE
		JLabel title = new JLabel("Client Management");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(202, 13, 226, 49);
		title.setFont(new Font("SansSerif", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		title.setBorder(new EmptyBorder(5, 5, 15, 5));
		contentPane.add(title);

		// TABLE
		loadTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 59, 625, 237);

		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		table.setOpaque(false);
		table.setBackground(new Color(255, 255, 255, 180));

		contentPane.add(scrollPane);

		// BUTTONS
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
	 * Responds to the clicking of buttons within the dialog.
	 *
	 * @param e An ActionEvent encapsulating the click.
	 */
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
					// We check if there are any bookings before deleting
					if (!cont.checkClientInBook(idClient)) {
						// We create an auxiliary client object for deletion
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

	/**
	 * Creates and loads the base model format for the Table.
	 */
	private void loadTable() {
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

	/**
	 * Retrieves all client information and reflects it on the table.
	 */
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