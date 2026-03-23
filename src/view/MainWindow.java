package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JButton btnCruise;
	private JButton btnWorker;
	private JButton btnClient;
	private JButton btnAdmins;
	private JPanel contentPane;
	private Image imagenFondo = new ImageIcon("images/ManagementAnchor.png").getImage();	
	
	//preguntar sobre esta herencia
	public MainWindow(JDialog father, LoginController cont) {
		super(father,true);
		this.cont=cont;
		
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
		
	    String[] opciones = {"Añadir", "Eliminar", "Modificar", "Ver"};
	    int opcion;

	    if (e.getSource().equals(btnCruise)) {
	        opcion = JOptionPane.showOptionDialog(this, "¿Qué quieres hacer?", "CRUISE",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
	   
	    }

	    if (e.getSource().equals(btnWorker)) {
	        opcion = JOptionPane.showOptionDialog(this, "¿Qué quieres hacer?", "WORKER",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
	    }

	    if (e.getSource().equals(btnClient)) {
	        opcion = JOptionPane.showOptionDialog(this, "¿Qué quieres hacer?", "CLIENT",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
	    }

	    if (e.getSource().equals(btnAdmins)) {
	        opcion = JOptionPane.showOptionDialog(this, "¿Qué quieres hacer?", "ADMIN",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
	    }

		
	}
}
