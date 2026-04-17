package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Cruise;
import model.TypeCruise;

/**
 * Modal dialog that displays the list of cruises and provides operations to add,
 * modify and delete cruises. Communicates with LoginController for CRUD
 * operations and prevents deletion when a cruise is referenced by workers or
 * bookings.
 * 
 * @author Aritz
 */
public class ListCruiseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JTable table;
	private JButton btnADD, btnDELETE, btnMODIFY;
	private Image backgroundImage = new ImageIcon("images/CruiseBackground.png").getImage();

	/**
	 * Constructs the list cruises window dialog.
	 *
	 * @param mainWindow The main interface frame.
	 * @param cont       The overarching application controller.
	 */
	public ListCruiseWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		this.cont = cont;

		setTitle("Cruises");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(663, 450);
		setLocationRelativeTo(null);
		setResizable(false);

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
		JLabel title = new JLabel("Cruise Management");
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
	 * Process events originated from interface interactions.
	 *
	 * @param e The action event details.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();

		if (e.getSource() == btnDELETE) {
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select a cruise to delete.");
				return;
			}

			String codCruise = table.getValueAt(row, 0).toString();

			if (!cont.checkCruiseInWorker(codCruise)) {
				if (!cont.checkCruiseInBook(codCruise)) {
					if (cont.deleteCruise(codCruise)) {
						refreshModel();
						JOptionPane.showMessageDialog(this, "Cruise has been deleted.");
					} else {
						JOptionPane.showMessageDialog(this, "Cruise not deleted.");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Cruise not deleted because exists in Book");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Cruise not deleted because exists in Worker");
			}
		}

		if (e.getSource() == btnMODIFY) {
			int viewRow = table.getSelectedRow();
			if (viewRow != -1) {
				int modelRow = table.convertRowIndexToModel(viewRow);
				TableModel model = table.getModel();

				Cruise cruise = new Cruise();
				cruise.setCodCruise((Integer) model.getValueAt(modelRow, 0));
				cruise.setTypeCruise(TypeCruise.valueOf((String) model.getValueAt(modelRow, 1)));
				cruise.setNameCruise((String) model.getValueAt(modelRow, 2));
				cruise.setNumRooms((Integer) model.getValueAt(modelRow, 3));
				cruise.setCapacityMax((Integer) model.getValueAt(modelRow, 4));

				new FormCruiseWindow(this, cont, cruise, false).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Select a cruise to modify");
			}
		}

		if (e.getSource() == btnADD) {
			new FormCruiseWindow(this, cont, null, true).setVisible(true);
			refreshModel();
		}
	}

	/**
	 * Sets the required definitions into the user table components.
	 */
	private void loadTable() {
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Code", "Type", "Name", "Rooms", "Capacity" },
				0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(modelo);
		table.setRowHeight(28);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

		refreshModel();
	}

	/**
	 * Reloads updated database data into the table layout.
	 */
	public void refreshModel() {
		List<Cruise> cruises = cont.getAllCruise();
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		modelo.setRowCount(0);

		for (Cruise cruise : cruises) {
			modelo.addRow(new Object[] { cruise.getCodCruise(), cruise.getTypeCruise().toString(),
					cruise.getNameCruise(), cruise.getNumRooms(), cruise.getCapacityMax() });
		}
	}
}