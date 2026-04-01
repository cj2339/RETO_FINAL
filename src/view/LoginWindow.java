package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.Administrator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class LoginWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private Image imagenFondo = new ImageIcon("images/ship.png").getImage();
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblError;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JButton btnLogin;
	private int atempts = 3;

	public LoginWindow(StartWindow startWindow, LoginController cont) {
		super(startWindow, true);
		this.cont = cont;

		setTitle("Login");
		setBounds(100, 100, 553, 403);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		setResizable(false);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		lblUser.setBounds(50, 40, 217, 58);
		contentPane.add(lblUser);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		lblPassword.setBounds(50, 108, 217, 58);
		contentPane.add(lblPassword);

		textField = new JTextField();
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		textField.setOpaque(false);
		textField.setBackground(new Color(255, 255, 255, 100));
		textField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		textField.setBounds(277, 55, 183, 34);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		passwordField.setOpaque(false);
		passwordField.setBackground(new Color(255, 255, 255, 100));
		passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		passwordField.setBounds(277, 117, 183, 34);
		contentPane.add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnLogin.setBounds(277, 280, 183, 58);
		btnLogin.addActionListener(this);
		contentPane.add(btnLogin);

		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Bahnschrift", Font.PLAIN, 17));
		lblError.setBounds(277, 161, 183, 77);
		contentPane.add(lblError);
		
		contentPane.getRootPane().setDefaultButton(btnLogin);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnLogin) {
			String password = new String(passwordField.getPassword());
			if (atempts > 0) {
				Administrator admin = new Administrator(textField.getText(), password);
				if (cont.checkUser(admin)) {
					cont.setLoggedInAdminName(admin.getName());
					dispose();
					MainWindow mainWindow = new MainWindow(this, cont, admin.getName());
					mainWindow.setVisible(true);
				} else {
					atempts--;
					JOptionPane.showMessageDialog(this, "User not found", "Login error",
							JOptionPane.INFORMATION_MESSAGE);
					lblError.setText("Remaining attempts: " + atempts);
					if (atempts == 0) {
						JOptionPane.showMessageDialog(this, "You have exceeded 3 attempts", "Login failure",
								JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "No attempts left. Access blocked.", "Login failure",
						JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		}
	}

}
