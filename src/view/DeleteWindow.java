package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class DeleteWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JComboBox<String> comboBox;

	public DeleteWindow(String tipo) {
		setTitle("Eliminate");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			comboBox = new JComboBox<String>();
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(227, 61, 169, 21);
			contentPanel.add(comboBox);
		}
		comboBox = new JComboBox<String>();
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(227, 121, 169, 21);
		contentPanel.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Eliminate");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(166, 10, 106, 24);
		contentPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(146, 191, 114, 34);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Code Cruise\r\n");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setBounds(67, 65, 106, 12);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID Client");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(67, 125, 106, 12);
		contentPanel.add(lblNewLabel_1_1);
	}
}
