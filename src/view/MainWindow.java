package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnCruise;
	private JButton btnWorker;
	private JButton btnClient;
	private JButton btnAdmins;
	private JPanel contentPane;
	private Image imagenFondo = new ImageIcon("images/ManagementAnchor.png").getImage();

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
		
		 contentPane = new JPanel() {
		        @Override
		        protected void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
		        }
		  };
		  setContentPane(contentPane);
		  contentPane.setLayout(null);
		    
		setTitle("Main Management");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MANAGEMENT");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(148, 22, 160, 21);
		getContentPane().add(lblNewLabel);
		
		btnCruise = new JButton("CRUISE");
		btnCruise.setBounds(24, 77, 112, 38);
		getContentPane().add(btnCruise);
		btnCruise.addActionListener(this);
		
		btnWorker = new JButton("WORKER");
		btnWorker.setBounds(299, 77, 112, 38);
		getContentPane().add(btnWorker);
		btnWorker.addActionListener(this);
		
		btnClient = new JButton("CLIENT");
		btnClient.setBounds(24, 164, 112, 38);
		getContentPane().add(btnClient);
		btnClient.addActionListener(this);
		
		btnAdmins = new JButton("ADMIN");
		btnAdmins.setBounds(299, 164, 112, 38);
		getContentPane().add(btnAdmins);
		btnAdmins.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String[] options = {"Add", "Eliminate", "Modify", "See"};
	    int option;
	    String type = "";

	    if (e.getSource().equals(btnCruise)) type = "cruise";
	    if (e.getSource().equals(btnWorker)) type = "worker";
	    if (e.getSource().equals(btnClient)) type = "client";
	    if (e.getSource().equals(btnAdmins)) type = "admin";

	    option = JOptionPane.showOptionDialog(this, "¿what do you want to do?", type.toUpperCase(),
	            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

//	    switch (option) {
//	        case 0:
//	        	new AddWindow(type).setVisible(true);
//	            break;
//	        case 1:
//	            new DeleteWindow(type).setVisible(true);
//	            break;
//	        case 2:
//	            new ModifyWindow(type).setVisible(true);
//	            break;
//	        case 3:
//	            new ViewWindow(type).setVisible(true);
//	            break;
//	    }

		
	}
}
