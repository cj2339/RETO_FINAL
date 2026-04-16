package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Administrator;

/**
 * Dialog to add or modify an Administrator. Delegates insert/update actions to
 * the provided LoginController and refreshes the administrators list on the
 * parent dialog when needed.
 */
public class FormAdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Image backgroundImage = new ImageIcon("images/FondoForms.png").getImage();
	private final JPanel panel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	};
	private JTextField txtName;
	private JPasswordField txtPassword;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isInsert;
	private LoginController controller;
	private Administrator administrator;
	private ListAdminWindow parent;

	/**
	 * Constructs the form dialog for adding or editing an administrator.
	 *
	 * @param adminListWindow The parent dialog.
	 * @param controller      The login controller for managing data.
	 * @param administrator   The administrator object to edit, or null if inserting.
	 * @param isInsert        True if adding a new administrator, false if updating.
	 */
	public FormAdminWindow(JDialog adminListWindow, LoginController controller, Administrator administrator, boolean isInsert) {
		super(adminListWindow, true);
		setBounds(100, 100, 476, 300);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		this.isInsert = isInsert;
		this.controller = controller;
		this.administrator = administrator;
		this.parent = (ListAdminWindow) adminListWindow;

		txtName = new JTextField();
		if (!isInsert) {
			txtName.setEnabled(false);
		}
		txtName.setBounds(266, 60, 116, 22);
		panel.add(txtName);
		txtName.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(266, 112, 116, 22);
		panel.add(txtPassword);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(52, 63, 88, 16);
		panel.add(lblName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(52, 115, 88, 16);
		panel.add(lblPassword);

		if (this.administrator != null) {
			txtName.setText(administrator.getName());
			txtPassword.setText(administrator.getPassword());
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(this);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(this);

	}

	/**
	 * Handles the action events for the OK and Cancel buttons.
	 *
	 * @param e The action event object.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        boolean valido = true;
		if (e.getSource() == okButton) {
			// Check that the fields are not empty
			if (txtName.getText().trim().isEmpty() ||  new String(txtPassword.getPassword()).trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields.");
				 valido = false;;
			}
			if(valido) {
				if (isInsert) {
					Administrator admin = new Administrator(txtName.getText(), new String(txtPassword.getPassword()));
					if (controller.insertAdministrator(admin)) {
						JOptionPane.showMessageDialog(this, "Administrator has been inserted.");
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Administrator already exists.");
					}
				} else {
					Administrator admin = new Administrator(txtName.getText(), new String(txtPassword.getPassword()));
					if (controller.updateAdministrator(admin)) {
						JOptionPane.showMessageDialog(this, "Administrator has been updated.");
						parent.refreshModel();
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Error updating administrator.");
					}
				}
			}
		}
		if (e.getSource() == cancelButton) {
			this.dispose();
		}
	}
}