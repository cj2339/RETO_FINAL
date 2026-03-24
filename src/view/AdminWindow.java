package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AdminWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblChangePassword;
	private JLabel lblPassword;
	private JTextField textFieldPassword;
	private JTextField textFieldNewPassword;
	private JButton btnChange;
	private JLabel lblNewPassword;
	private JLabel lblWarning;
	private Image BackgroundImage = new ImageIcon("images/ventanaAdmin.png").getImage();
	private int atempts=3;

	public static void main(String[] args) {
	    try {
	        AdminWindow dialog = new AdminWindow();
	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        dialog.setVisible(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public AdminWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

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
		lblChangePassword.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		lblChangePassword.setBounds(24, 10, 342, 58);
		getContentPane().add(lblChangePassword);

		lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Bahnschrift", Font.BOLD, 20));
		lblPassword.setBounds(34, 78, 139, 25);
		getContentPane().add(lblPassword);

		lblNewPassword = new JLabel("NEW PASSWORD");
		lblNewPassword.setFont(new Font("Bahnschrift", Font.BOLD, 20));
		lblNewPassword.setBounds(34, 143, 192, 25);
		getContentPane().add(lblNewPassword);

		btnChange = new JButton("CHANGE");
		btnChange.addActionListener(this);
		btnChange.setVerticalAlignment(SwingConstants.TOP);
		btnChange.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnChange.setBounds(229, 178, 183, 34);
		getContentPane().add(btnChange);

		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(229, 78, 183, 34);
		getContentPane().add(textFieldPassword);

		textFieldNewPassword = new JTextField();
		textFieldNewPassword.setColumns(10);
		textFieldNewPassword.setBounds(229, 134, 183, 34);
		getContentPane().add(textFieldNewPassword);
		
		lblWarning = new JLabel("");
		lblWarning.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		lblWarning.setBounds(10, 222, 400, 30);
		lblWarning.setForeground(Color.RED);	
		contentPane.add(lblWarning);
	}


	@Override
	public void actionPerformed(ActionEvent e) {if (e.getSource() == btnChange) {
		boolean valido = true;
		String oldPass = textFieldPassword.getText();
		String newPass = textFieldNewPassword.getText();

		// 1. We check that they are not empty
		if (oldPass.isEmpty() || newPass.isEmpty()) {
			lblWarning.setText("You must fill in both fields");
			valido = false;
		}
		// 2. We verify that the password is indeed the one entered
		else if (valido && !oldPass.equals("")) {//debe comparar con la contraseña asociada al admin registrado en la bd, pero ahora no sé hacerlo
			lblWarning.setText("The current password is incorrect");
			valido = false;
		}
		// 3. We check that it is different from the old one
		else if (valido && oldPass.equals(newPass)) {
			lblWarning.setText("The new password cannot be the same as the previous one");
			valido = false;
		}
		// 4. Everything is fine; let's change the password
		else{
			lblWarning.setForeground(Color.BLACK);
			lblWarning.setText("Password changed!");
		}
	}}
}
