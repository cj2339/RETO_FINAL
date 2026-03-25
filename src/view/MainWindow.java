package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import controller.LoginController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private String adminName;
	private JButton btnCruise;
	private JButton btnWorker;
	private JButton btnClient;
	private JButton btnAdmins;
	private JPanel contentPane;
	private JButton btnBook;
	private JLabel lblNewLabel;
	private Image imagenFondo = new ImageIcon("images/ManagementAnchor.png").getImage();	

	//preguntar sobre esta herencia
	public MainWindow(JDialog father, LoginController cont, String adminName) {
		super(father,true);
		this.cont=cont;
		this.adminName = adminName;

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};
		setContentPane(contentPane);

		setTitle("Main Management");
		setBounds(100, 100, 450, 333);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));


		lblNewLabel = new JLabel("MANAGEMENT");
		lblNewLabel.setBounds(148, 22, 160, 21);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(lblNewLabel);

		btnCruise = new JButton("CRUISE");
		btnCruise.setBounds(24, 82, 112, 38);
		getContentPane().add(btnCruise);
		btnCruise.addActionListener(this);

		btnWorker = new JButton("WORKER"); 
		btnWorker.setBounds(299, 82, 112, 38);
		getContentPane().add(btnWorker);
		btnWorker.addActionListener(this);

		btnClient = new JButton("CLIENT");
		btnClient.setBounds(24, 168, 112, 38);
		getContentPane().add(btnClient);
		btnClient.addActionListener(this);

		btnAdmins = new JButton("ADMIN");
		btnAdmins.setBounds(180, 254, 81, 21);
		getContentPane().add(btnAdmins);
		btnAdmins.addActionListener(this);

		btnBook = new JButton("BOOK");
		btnBook.setBounds(299, 168, 112, 38);
		contentPane.add(btnBook);
		btnBook.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String[] options = {"Add", "Eliminate", "Modify", "See"};
		int option;
		String type = "";

		if (e.getSource().equals(btnAdmins)) {
			AdminWindow adminWindow = new AdminWindow(this, cont, adminName);
			adminWindow.setVisible(true);

		}else{
			if (e.getSource().equals(btnCruise)) {
				type = "cruise";
			}else if (e.getSource().equals(btnWorker)) {
				type = "worker";
			}else if (e.getSource().equals(btnClient)) {
				type = "client";
			} else if (e.getSource().equals(btnBook)) {
				type = "book";
			}

			option = JOptionPane.showOptionDialog(this, "Select an option: ", type, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

			switch (option) {
			case 0:
				AddWindow add = new AddWindow(this,true,cont,type);
				add.setVisible(true);
				break;
			case 1:
				DeleteWindow delete = new DeleteWindow(this, cont, type);
				delete.setVisible(true);
				break;
				//	        case 2:
				//	            new ModifyWindow(type).setVisible(true);
				//	            break;
				//	        case 3:
				//	            new ViewWindow(type).setVisible(true);
				//	            break;
			}
		}
	}
}