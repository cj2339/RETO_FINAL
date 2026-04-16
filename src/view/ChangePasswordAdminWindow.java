package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import controller.LoginController;
import model.Administrator;

/**
 * Dialog that allows changing an administrator password. Validates the old
 * password, enforces attempt limits and updates the password via the
 * LoginController.
 * 
 * @author Iker
 */
public class ChangePasswordAdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private String adminName;
	private JPanel contentPane;
	private JLabel lblPassword;
	private JPasswordField  textFieldPassword;
	private JPasswordField  textFieldNewPassword;
	private JButton btnChange;
	private JLabel lblNewPassword;
	private JTextField txtName;
	private JLabel lblName;
	private JLabel lblWarning;
	private Image BackgroundImage = new ImageIcon("images/adminWindow.png").getImage();
	private int atempts=3;

	/**
	 * Creates a new dialog to change the administrator's password.
	 *
	 * @param listAdminWindow The parent list administrator window.
	 * @param cont            The login controller used for database operations.
	 * @param adminName       The name of the administrator whose password is being changed.
	 */
	public ChangePasswordAdminWindow(ListAdminWindow listAdminWindow, LoginController cont, String adminName) {
		super(listAdminWindow, true);
		this.cont = cont;
		this.adminName = adminName;
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(600, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(BackgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setType(Type.POPUP);
		setTitle("CHANGE PASSWORD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		getContentPane().setLayout(null);

		lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		lblPassword.setBounds(103, 84, 155, 25);
		getContentPane().add(lblPassword);

		lblNewPassword = new JLabel("NEW PASSWORD");
		lblNewPassword.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		lblNewPassword.setBounds(102, 140, 232, 25);
		getContentPane().add(lblNewPassword);

		btnChange = new JButton("CHANGE");
		btnChange.addActionListener(this);
		btnChange.setVerticalAlignment(SwingConstants.TOP);
		btnChange.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnChange.setBounds(356, 193, 183, 34);
		getContentPane().add(btnChange);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(355, 78, 183, 34);
		getContentPane().add(textFieldPassword);

		textFieldNewPassword = new JPasswordField();
		textFieldNewPassword.setColumns(10);
		textFieldNewPassword.setBounds(355, 128, 183, 34);
		getContentPane().add(textFieldNewPassword);

		lblWarning = new JLabel("");
		lblWarning.setBackground(new Color(128, 255, 255));
		lblWarning.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		lblWarning.setBounds(139, 284, 400, 30);
		lblWarning.setForeground(Color.RED);	
		contentPane.add(lblWarning);

		txtName = new JTextField();
		txtName.setBounds(355, 30, 183, 34);
		txtName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtName.setText(adminName); // Rellenar con el nombre actual
		getContentPane().add(txtName);

		lblName = new JLabel("NAME");
		lblName.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		lblName.setBounds(103, 35, 155, 25);
		getContentPane().add(lblName);

	}


	/**
	 * Handles the action events triggered by the dialog's buttons.
	 * Validates the input and attempts to change the password.
	 *
	 * @param e The action event object.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {if (e.getSource() == btnChange) {

		String oldPass = new String(textFieldPassword.getPassword());
		String newPass = new String(textFieldNewPassword.getPassword());
		String newName = txtName.getText().trim(); // NUEVO

		boolean valido = true;
		lblWarning.setForeground(Color.RED);

		// 0. No attempts available
		if (atempts == 0) {
			lblWarning.setText("No attempts left. Access blocked.");
			btnChange.setEnabled(false);
			valido = false;
		}

		// 1. We check that they are not empty
		if (valido) {
			if (oldPass.isEmpty() || newPass.isEmpty() || newName.isEmpty()) { // AÑADIDO newName
				lblWarning.setText("You must fill in all fields!");
				valido = false;
			}
		}

		// 2. Data validation against the database
		if (valido) {
			Administrator admin = new Administrator(adminName, oldPass);
			if (!cont.checkUser(admin)) {
				atempts--;
				lblWarning.setText("Incorrect password! Attempts left: " + atempts);
				valido = false;

				if (atempts == 0) {
					btnChange.setEnabled(false);
					lblWarning.setText("No attempts left. Access blocked.");
				}
			}
		}

		// 3. We check that the new password is not the same as the previous one
		if (valido) {
			if (oldPass.equals(newPass)) {
				lblWarning.setText("The new password cannot be the same as the previous one!");
				valido = false;
			}
		}

		// 4. All correct → update name + password
		if (valido) {
			Administrator updated = new Administrator(newName, newPass);

			if (cont.updateAdministrator(updated)) {
				lblWarning.setForeground(Color.BLACK);
				lblWarning.setText("Administrator updated successfully!");
				atempts = 3;
				adminName = newName;
			} else {
				lblWarning.setText("Error updating administrator. Please try again.");
			}
		}
	}
	}
}