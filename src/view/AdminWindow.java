package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.LoginController;
import model.Administrator;

public class AdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private String adminName;
	private JPanel contentPane;
	private JLabel lblChangePassword;
	private JLabel lblPassword;
	private JPasswordField  textFieldPassword;
	private JPasswordField  textFieldNewPassword;
	private JButton btnChange;
	private JLabel lblNewPassword;
	private JLabel lblWarning;
	private Image BackgroundImage = new ImageIcon("images/ventanaAdmin.png").getImage();
	private int atempts=3;

	public static void main(String[] args) {
		LoginController cont = new LoginController();
		String adminName = "Iker";

		AdminWindow dialog = new AdminWindow(null, cont, adminName);
		dialog.setVisible(true);
	}
	
	public AdminWindow(JDialog father, LoginController cont, String adminName) {
		super(father, true);
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
		setTitle("ADMIN");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\Icono.png"));
		getContentPane().setLayout(null);

		lblChangePassword = new JLabel("CHANGE PASSWORD");
		lblChangePassword.setFont(new Font("Bahnschrift", Font.BOLD, 40));
		lblChangePassword.setBounds(116, 9, 397, 58);
		getContentPane().add(lblChangePassword);

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
		lblWarning.setBounds(81, 311, 400, 30);
		lblWarning.setForeground(Color.RED);	
		contentPane.add(lblWarning);
	}


	@Override
	public void actionPerformed(ActionEvent e) {if (e.getSource() == btnChange) {
		if (e.getSource() == btnChange) {

			String oldPass = new String(textFieldPassword.getPassword());
			String newPass = new String(textFieldNewPassword.getPassword());
			boolean valido = true;
			lblWarning.setForeground(Color.RED);

			// 0. No attempts available
			if (atempts == 0) {
				lblWarning.setText("No attempts left. Access blocked.");
				btnChange.setEnabled(false);
				valido=true	;
			}
			// 1. We check that they are not empty
			if (oldPass.isEmpty() || newPass.isEmpty()) {
				lblWarning.setText("You must fill in both fields!");
				valido = false;
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
			if (valido && oldPass.equals(newPass)) {
				lblWarning.setText("The new password cannot be the same as the previous one!");
				valido = false;
			}
			// 4. All correct, change password
			if (valido) {            
				if (cont.updatePassword(adminName, newPass)) {
					lblWarning.setForeground(Color.BLACK);
					lblWarning.setText("Password changed successfully!");
					atempts = 3; // Reset attempts on successful password change
				} else {
					lblWarning.setText("Error changing password. Please try again.");
				}}
		}
	}}
}
