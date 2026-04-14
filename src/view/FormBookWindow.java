package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import controller.LoginController;
import model.Book;

public class FormBookWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();

	private JTextField txtClient;
	private JTextField txtCruise;
	private JTextField txtOrigin;
	private JTextField txtDestination;
	private JTextField txtRoom;

	private JSpinner spStartDate;
	private JSpinner spEndDate;

	private JButton okButton;
	private JButton cancelButton;

	private boolean isInsert;
	private LoginController controller;
	private Book oldBooking;
	private ListBookWindow parent;

	public FormBookWindow(JDialog parentWindow, LoginController controller, Book booking, boolean isInsert) {
		super(parentWindow, true);
		setBounds(100, 100, 500, 380);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		this.isInsert = isInsert;
		this.controller = controller;
		this.oldBooking = booking;
		this.parent = (ListBookWindow) parentWindow;

		JLabel lblClient = new JLabel("Client ID:");
		lblClient.setBounds(40, 40, 120, 20);
		panel.add(lblClient);

		txtClient = new JTextField();
		txtClient.setBounds(200, 40, 200, 22);
		panel.add(txtClient);

		JLabel lblCruise = new JLabel("Cruise Code:");
		lblCruise.setBounds(40, 80, 120, 20);
		panel.add(lblCruise);

		txtCruise = new JTextField();
		txtCruise.setBounds(200, 80, 200, 22);
		panel.add(txtCruise);

		JLabel lblOrigin = new JLabel("Origin City:");
		lblOrigin.setBounds(40, 120, 120, 20);
		panel.add(lblOrigin);

		txtOrigin = new JTextField();
		txtOrigin.setBounds(200, 120, 200, 22);
		panel.add(txtOrigin);

		JLabel lblDestination = new JLabel("Destination City:");
		lblDestination.setBounds(40, 160, 120, 20);
		panel.add(lblDestination);

		txtDestination = new JTextField();
		txtDestination.setBounds(200, 160, 200, 22);
		panel.add(txtDestination);

		JLabel lblStart = new JLabel("Start Date:");
		lblStart.setBounds(40, 200, 120, 20);
		panel.add(lblStart);

		spStartDate = new JSpinner(new SpinnerDateModel());
		spStartDate.setBounds(200, 200, 200, 22);
		panel.add(spStartDate);

		JLabel lblEnd = new JLabel("End Date:");
		lblEnd.setBounds(40, 240, 120, 20);
		panel.add(lblEnd);

		spEndDate = new JSpinner(new SpinnerDateModel());
		spEndDate.setBounds(200, 240, 200, 22);
		panel.add(spEndDate);

		JLabel lblRoom = new JLabel("Room Number:");
		lblRoom.setBounds(40, 280, 120, 20);
		panel.add(lblRoom);

		txtRoom = new JTextField();
		txtRoom.setBounds(200, 280, 200, 22);
		panel.add(txtRoom);

		if (!isInsert && oldBooking != null) {
			txtClient.setText(oldBooking.getIdClient());
			txtCruise.setText(String.valueOf(oldBooking.getCodCruise()));
			txtOrigin.setText(oldBooking.getOriginCity());
			txtDestination.setText(oldBooking.getDestinationCity());
			spStartDate.setValue(oldBooking.getStartDate());
			spEndDate.setValue(oldBooking.getEndDate());
			txtRoom.setText(String.valueOf(oldBooking.getRoomNumber()));

			txtClient.setEnabled(false);
			txtCruise.setEnabled(false);
			spStartDate.setEnabled(false);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == okButton) {

			if (txtClient.getText().trim().isEmpty() ||
					txtCruise.getText().trim().isEmpty() ||
					txtOrigin.getText().trim().isEmpty() ||
					txtDestination.getText().trim().isEmpty() ||
					txtRoom.getText().trim().isEmpty()) {

				JOptionPane.showMessageDialog(this, "Please fill in all fields.");
				return;
			}

			int codCruise;
			int roomNumber;
			try {
				codCruise = Integer.parseInt(txtCruise.getText().trim());
				roomNumber = Integer.parseInt(txtRoom.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Cruise code and room number must be numeric.");
				return;
			}

			Date start = (Date) spStartDate.getValue();
			Date end = (Date) spEndDate.getValue();
			if (!start.before(end)) {
				JOptionPane.showMessageDialog(this, "Start date must be before end date.");
				return;
			}

			Book newB = new Book(
					txtClient.getText().trim(),
					codCruise,
					txtOrigin.getText().trim(),
					txtDestination.getText().trim(),
					start,
					end,
					0.0,
					0.0,
					roomNumber
					);

			boolean ok;
			if (isInsert) {
				ok = controller.createBooking(newB);
			} else {
				ok = controller.updateBooking(oldBooking, newB);
			}

			if (ok) {
				JOptionPane.showMessageDialog(this, "Booking saved.");
				parent.refreshModel();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error saving booking.");
			}
		}

		if (e.getSource() == cancelButton) {
			this.dispose();
		}
	}
}
