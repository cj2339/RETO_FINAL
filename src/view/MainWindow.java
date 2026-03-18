package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnCruise;
	private JButton btnWorkers;
	private JButton btnClient;
	private JButton btnAdmins;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MainWindow dialog = new MainWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MainWindow() {
		setTitle("Main Management");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MANAGEMENT");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(138, 22, 160, 21);
		getContentPane().add(lblNewLabel);
		
		btnCruise = new JButton("CRUISE");
		btnCruise.setBounds(24, 77, 112, 38);
		getContentPane().add(btnCruise);
		
		btnWorkers = new JButton("WORKER");
		btnWorkers.setBounds(283, 77, 112, 38);
		getContentPane().add(btnWorkers);
		
		btnClient = new JButton("CLIENT");
		btnClient.setBounds(24, 164, 112, 38);
		getContentPane().add(btnClient);
		
		btnAdmins = new JButton("ADMIN");
		btnAdmins.setBounds(283, 164, 112, 38);
		getContentPane().add(btnAdmins);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
