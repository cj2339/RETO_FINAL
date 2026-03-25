package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ClientManagement extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField textField;
	private LoginController cont;

	public ClientManagement(MainWindow mainWindow, boolean b, LoginController cont) {
		super(mainWindow, b);
		setTitle("Client Management");
		this.cont=cont;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		table = new JTable();
		table.setBounds(28, 44, 207, 183);
		contentPanel.add(table);
		
		JLabel lblNewLabel = new JLabel("Client");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(28, 10, 87, 24);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("SEARCH:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_1.setBounds(269, 44, 62, 14);
		contentPanel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(268, 68, 143, 18);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.setBounds(269, 96, 142, 36);
		contentPanel.add(btnNewButton);
		
		JButton btnModify = new JButton("MODIFY");
		btnModify.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnModify.setBounds(269, 142, 142, 36);
		contentPanel.add(btnModify);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDelete.setBounds(269, 191, 142, 36);
		contentPanel.add(btnDelete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
