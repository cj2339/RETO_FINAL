package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class FormCruiseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
	private JTextField txtCode;
	private JTextField txtNameCruise;
	private JTextField txtNumRooms;
	private JTextField txtMaxCapacity;
	private JComboBox cmbType;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isInsert;
	private LoginController controller;

	public FormCruiseWindow(JDialog cruiseListWindow, LoginController controller, Cruise cruise, boolean isInsert) {
		super(cruiseListWindow, true);
		setBounds(100, 100, 476, 437);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		this.isInsert = isInsert;
		this.controller = controller;

		txtCode = new JTextField();
		txtCode.setEnabled(false);
		txtCode.setBounds(266, 60, 116, 22);
		panel.add(txtCode);
		txtCode.setColumns(10);

		txtNameCruise = new JTextField();
		txtNameCruise.setColumns(10);
		txtNameCruise.setBounds(266, 112, 116, 22);
		panel.add(txtNameCruise);

		txtNumRooms = new JTextField();
		txtNumRooms.setColumns(10);
		txtNumRooms.setBounds(266, 171, 116, 22);
		panel.add(txtNumRooms);

		txtMaxCapacity = new JTextField();
		txtMaxCapacity.setColumns(10);
		txtMaxCapacity.setBounds(266, 290, 116, 22);
		panel.add(txtMaxCapacity);

		cmbType = new JComboBox();
		cmbType.setModel(new DefaultComboBoxModel(TypeCruise.values()));
		cmbType.setBounds(266, 230, 116, 24);
		panel.add(cmbType);

		JLabel lblCode = new JLabel("Cruise Code:");
		lblCode.setBounds(52, 63, 88, 16);
		panel.add(lblCode);

		JLabel lblNameCruise = new JLabel("Name:");
		lblNameCruise.setBounds(52, 115, 56, 16);
		panel.add(lblNameCruise);

		JLabel lblNumRooms = new JLabel("Number of Rooms:");
		lblNumRooms.setBounds(52, 174, 108, 16);
		panel.add(lblNumRooms);

		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(52, 234, 56, 16);
		panel.add(lblType);

		JLabel lblCapacity = new JLabel("Capacity:");
		lblCapacity.setBounds(52, 293, 56, 16);
		panel.add(lblCapacity);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == okButton) {

			if (isInsert) {
				Cruise cruise = new Cruise(0, (TypeCruise)cmbType.getSelectedItem(), txtNameCruise.getText(), Integer.parseInt(txtNumRooms.getText()),
						Integer.parseInt(txtMaxCapacity.getText()));
				if (controller.insertCruise(cruise)) {
					JOptionPane.showMessageDialog(this, "Cruise has been inserted.");
					this.dispose();
				}
			}

		}
	}
}
