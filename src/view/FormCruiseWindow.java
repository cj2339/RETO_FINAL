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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import model.Cruise;
import model.TypeCruise;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;

public class FormCruiseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
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

	public FormCruiseWindow(JDialog cruiseListWindow, LoginController controller, Cruise cruise, boolean isInsert) {
		super(cruiseListWindow, true);
		setBounds(100, 100, 476, 437);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		this.isInsert = isInsert;
		this.controller = controller;
		this.cruise = cruise;
		this.parent = (ListCruiseWindow) cruiseListWindow;

		txtCode = new JTextField();
		txtCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtCode.setEnabled(false);
		txtCode.setBounds(266, 60, 116, 22);
		panel.add(txtCode);
		txtCode.setColumns(10);

		txtNameCruise = new JTextField();
		txtNameCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtNameCruise.setColumns(10);
		txtNameCruise.setBounds(266, 112, 116, 22);
		panel.add(txtNameCruise);

		txtNumRooms = new JTextField();
		txtNumRooms.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtNumRooms.setColumns(10);
		txtNumRooms.setBounds(266, 171, 116, 22);
		panel.add(txtNumRooms);
		// Agregar un KeyListener para validar que solo se ingresen números
		// TODO: validar que el numero de habitaciones no quede vacio
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
		txtMaxCapacity.setColumns(10);
		txtMaxCapacity.setBounds(266, 290, 116, 22);
		panel.add(txtMaxCapacity);
		// Agregar un KeyListener para validar que solo se ingresen números
		// TODO: validar que el numero no quede vacio
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
		cmbType.setBounds(266, 230, 116, 24);
		panel.add(cmbType);

		JLabel lblCode = new JLabel("Cruise Code:");
		lblCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblCode.setBounds(52, 63, 130, 16);
		panel.add(lblCode);

		JLabel lblNameCruise = new JLabel("Name:");
		lblNameCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNameCruise.setBounds(52, 115, 108, 16);
		panel.add(lblNameCruise);

		JLabel lblNumRooms = new JLabel("Number of Rooms:");
		lblNumRooms.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNumRooms.setBounds(52, 174, 170, 16);
		panel.add(lblNumRooms);

		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblType.setBounds(52, 234, 70, 16);
		panel.add(lblType);

		JLabel lblCapacity = new JLabel("Capacity:");
		lblCapacity.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblCapacity.setBounds(52, 293, 108, 16);
		panel.add(lblCapacity);

		if (this.cruise != null) {
			txtCode.setText(String.valueOf(cruise.getCodCruise()));
			cmbType.setSelectedItem(cruise.getTypeCruise());
			txtNameCruise.setText(cruise.getNameCruise());
			txtNumRooms.setText(String.valueOf(cruise.getNumRooms()));
			txtMaxCapacity.setText(String.valueOf(cruise.getCapacityMax()));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Confirm");
				okButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean valid = true;
		// TODO: validar valores numericos enteros
		if (e.getSource() == okButton) {
			if (txtNameCruise.getText().isEmpty() || txtNumRooms.getText().isEmpty()
					|| txtMaxCapacity.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields.");
				valid = false;

			}
			if (valid == true) {
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
			
		}
	}
}