package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AdminWindow extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblChangePassword;
	private JLabel lblPassword;
	private JTextField textFieldPassword;
	private JTextField textFieldNewPassword;
	private JButton btnChange;
	private JLabel lblNewPassword;
	
	
	//esto hay que quitarlo luego
		public static void main(String[] args) {
			try {
				LoginWindow dialog = new LoginWindow();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	public AdminWindow() {
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
		btnChange.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnChange.setBounds(229, 195, 183, 58);
		getContentPane().add(btnChange);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(229, 78, 183, 34);
		getContentPane().add(textFieldPassword);
		
		textFieldNewPassword = new JTextField();
		textFieldNewPassword.setColumns(10);
		textFieldNewPassword.setBounds(229, 134, 183, 34);
		getContentPane().add(textFieldNewPassword);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
}
