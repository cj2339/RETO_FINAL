package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.LoginController;
import model.Cruise;
import model.TypeCruise;

/**
 * Dialog to add or modify a cruise. Handles form input, validation,
 * and delegates insert/update operations to the LoginController.
 * 
 * @author Aritz
 */
public class FormCruiseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField txtCode;
	private JTextField txtNameCruise;
	private JTextField txtNumRooms;
	private JTextField txtMaxCapacity;
	private JComboBox<TypeCruise> cmbType;
	private JButton okButton;
	private boolean isInsert;
	private LoginController controller;
	private Cruise cruise;
	private ListCruiseWindow parent;
	private JButton btnClear;
	private Image backgroundImage = new ImageIcon("images/FondoForms2.png").getImage();

	public FormCruiseWindow(JDialog cruiseListWindow, LoginController controller, Cruise cruise, boolean isInsert) {
		super(cruiseListWindow, true);

		if (isInsert) {
			setTitle("Add cruise");
		} else {
			setTitle("Modify cruise");
		}

		setBounds(100, 100, 529, 437);
		setResizable(false);

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);

		this.isInsert = isInsert;
		this.controller = controller;
		this.cruise = cruise;
		this.parent = (ListCruiseWindow) cruiseListWindow;

		txtCode = new JTextField();
		txtCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtCode.setEnabled(false);
		txtCode.setBounds(266, 80, 191, 22);
		panel.add(txtCode);

		txtNameCruise = new JTextField();
		txtNameCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtNameCruise.setBounds(266, 126, 191, 22);
		panel.add(txtNameCruise);

		txtNumRooms = new JTextField();
		txtNumRooms.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtNumRooms.setBounds(266, 171, 191, 22);
		panel.add(txtNumRooms);

		txtNumRooms.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		txtMaxCapacity = new JTextField();
		txtMaxCapacity.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtMaxCapacity.setBounds(266, 252, 191, 22);
		panel.add(txtMaxCapacity);

		txtMaxCapacity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		cmbType = new JComboBox<TypeCruise>();
		cmbType.setFont(new Font("SansSerif", Font.PLAIN, 20));
		cmbType.setModel(new DefaultComboBoxModel<TypeCruise>(TypeCruise.values()));
		cmbType.setBounds(266, 211, 191, 24);
		panel.add(cmbType);

		JLabel lblCode = new JLabel("Cruise Code:");
		lblCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblCode.setBounds(52, 83, 130, 16);
		panel.add(lblCode);

		JLabel lblNameCruise = new JLabel("Name:");
		lblNameCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNameCruise.setBounds(52, 129, 108, 16);
		panel.add(lblNameCruise);

		JLabel lblNumRooms = new JLabel("Number of Rooms:");
		lblNumRooms.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNumRooms.setBounds(52, 174, 170, 16);
		panel.add(lblNumRooms);

		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblType.setBounds(52, 215, 70, 20);
		panel.add(lblType);

		JLabel lblCapacity = new JLabel("Capacity:");
		lblCapacity.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblCapacity.setBounds(52, 255, 108, 19);
		panel.add(lblCapacity);

		okButton = new JButton("Confirm");
		okButton.setBounds(266, 349, 116, 41);
		panel.add(okButton);
		okButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnClear.setBounds(118, 349, 116, 41);
		panel.add(btnClear);
		btnClear.addActionListener(this);

		JLabel lblFill = new JLabel("Fill the cruise data:");
		lblFill.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblFill.setBounds(52, 26, 310, 24);
		panel.add(lblFill);
		okButton.addActionListener(this);


		cmbType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TypeCruise selectedType = (TypeCruise) cmbType.getSelectedItem();
				if (selectedType != null) {
					int maxCapacity = 0;
					switch (selectedType) {
					case FAMILY:
						maxCapacity = 700;
						break;
					case LUXURY:
						maxCapacity = 1200;
						break;
					case EXPEDITION:
						maxCapacity = 1500;
						break;
					case PREMIUM:
						maxCapacity = 900;
						break;
					}
					txtMaxCapacity.setText(String.valueOf(maxCapacity));
					txtMaxCapacity.setEditable(false);


					int numRooms = maxCapacity / 5;
					txtNumRooms.setText(String.valueOf(numRooms));
					txtNumRooms.setEditable(false);
				}
			}
		});


		if (this.cruise != null) {
			txtCode.setText(String.valueOf(cruise.getCodCruise()));
			cmbType.setSelectedItem(cruise.getTypeCruise());
			txtNameCruise.setText(cruise.getNameCruise());
			txtNumRooms.setText(String.valueOf(cruise.getNumRooms()));
			txtMaxCapacity.setText(String.valueOf(cruise.getCapacityMax()));
			txtMaxCapacity.setEditable(false);
		} else {
			txtCode.setText(String.valueOf(controller.getNextCruiseCode()));
			cmbType.setSelectedIndex(0);
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean valid = true;
		if (e.getSource() == okButton) {
			if (txtNameCruise.getText().isEmpty() || txtNumRooms.getText().isEmpty()
					|| txtMaxCapacity.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields.");
				valid = false;
			}
			if (valid) {
				if (isInsert) {
					Cruise cruise = new Cruise(0, (TypeCruise) cmbType.getSelectedItem(), txtNameCruise.getText(),
							Integer.parseInt(txtNumRooms.getText()), Integer.parseInt(txtMaxCapacity.getText()));
					if (controller.insertCruise(cruise)) {
						JOptionPane.showMessageDialog(this, "Cruise has been inserted.");
						this.dispose();
					}
				} else {
					Cruise cruise = new Cruise(Integer.parseInt(txtCode.getText()),
							(TypeCruise) cmbType.getSelectedItem(), txtNameCruise.getText(),
							Integer.parseInt(txtNumRooms.getText()), Integer.parseInt(txtMaxCapacity.getText()));
					if (controller.updateCruiseByCode(cruise)) {
						JOptionPane.showMessageDialog(this, "Cruise has been updated.");
						parent.refreshModel();
						this.dispose();
					}
				}
			}
		} else if (e.getSource() == btnClear) {
			txtNameCruise.setText(null);
			txtNumRooms.setText(null);
			cmbType.setSelectedIndex(0);
		}
	}
}
