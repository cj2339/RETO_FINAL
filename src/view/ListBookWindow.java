package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;
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
import model.Book;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Modal dialog that lists bookings and provides operations to create, modify
 * and delete bookings. Uses the LoginController to perform persistence
 * operations and refresh the displayed booking list.
 */
public class ListBookWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	private JTable table;
	private Image backgroundImage = new ImageIcon("images/CruiseBackground.png").getImage();

	/**
	 * Constructs the list booking dialog.
	 *
	 * @param mainWindow The internal frame acting as parent.
	 * @param cont       The controller for operational queries and logic.
	 */
	public ListBookWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		setTitle("Bookings");
		this.cont = cont;

		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(663, 450);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel() {
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
		JLabel title = new JLabel("Book Management");
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
	 * Handles actions originating from the interface buttons.
	 *
	 * @param e The triggered action event object.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int row = table.getSelectedRow();

		if (e.getSource() == btnDELETE) {
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select a booking to delete.");
				return;
			}

			TableModel model = table.getModel();

			String idClient = (String) model.getValueAt(row, 0);
			int codCruise = (int) model.getValueAt(row, 1);
			Date startDate = (Date) model.getValueAt(row, 2);

			int confirm = JOptionPane.showConfirmDialog(
					this,
					"Are you sure you want to delete this booking?",
					"Confirm deletion",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
					);

			if (confirm == JOptionPane.YES_OPTION) {
				boolean deleted = cont.deleteBooking(idClient, codCruise, startDate);

				if (deleted) {
					JOptionPane.showMessageDialog(this, "Booking deleted.");
				} else {
					JOptionPane.showMessageDialog(this, "Booking not deleted.");
				}

				refreshModel();
			}
		}

		if (e.getSource() == btnMODIFY) {
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select a booking to modify.");
				return;
			}

			TableModel model = table.getModel();

			Book oldB = new Book(
					(String) model.getValueAt(row, 0),   // client
					(int) model.getValueAt(row, 1),      // cruise
					(String) model.getValueAt(row, 3),   // origin
					(String) model.getValueAt(row, 4),   // destination
					(Date) model.getValueAt(row, 2),     // start
					(Date) model.getValueAt(row, 5),     // end
					(double) model.getValueAt(row, 6),   // base
					(double) model.getValueAt(row, 7),   // final
					(int) model.getValueAt(row, 8)       // room
					);

			FormBookWindow fb = new FormBookWindow(this, cont, oldB, false);
			fb.setVisible(true);
			refreshModel();
		}

		if (e.getSource() == btnADD) {
			FormBookWindow fb = new FormBookWindow(this, cont, null, true);
			fb.setVisible(true);
			refreshModel();
		}
	}

	/**
	 * Prepares and initializes the default table model to hold bookings.
	 */
	private void loadTable() {
		DefaultTableModel modelo = new DefaultTableModel(
				new String[] {
						"Client", "Cruise", "Start Date", "Origin", "Destination", "End Date", "Base Price", "Final Price", "Room"
				},
					0
					) {
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

	/**
	 * Fetches updated information from the database and updates the dialog table.
	 */
	public void refreshModel() {
		List<Book> bookings = cont.getAllBookings();
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		for (Book b : bookings) {
			modelo.addRow(new Object[]{
					b.getIdClient(),
					b.getCodCruise(),
					b.getStartDate(),
					b.getOriginCity(),
					b.getDestinationCity(),
					b.getEndDate(),
					b.getBasePrice(),
					b.getFinalPrice(),
					b.getRoomNumber()
			});
		}
	}
}