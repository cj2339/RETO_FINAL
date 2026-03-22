package view;

import java.awt.EventQueue;
import javax.swing.*;

import controller.LoginController;

import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class StartWindow extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JLabel lblTitle;
	private JLabel lblName;
	private Image BackgroundImage = new ImageIcon("images/background1.png").getImage();
	private JTextField lblText;
	
	public StartWindow(LoginController loginController) {
		this.cont=loginController; 
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 779, 550);

	    //Bloquear que la ventana pueda redimensionarse o maximizarse
	    setResizable(false);

	    contentPane = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(BackgroundImage, 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    
	    contentPane.addMouseListener(this);
	    
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
 
	    lblName = new JLabel();
	    lblName.setBounds(148, 40, 250, 20);
	    lblName.setFont(new Font("Tahoma", Font.BOLD, 10));
	    lblName.setBackground(new Color(255, 255, 255));
	    contentPane.add(lblName);

	    lblTitle = new JLabel();
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitle.setText("Click to start");
	    lblTitle.setBounds(227, 440, 250, 34);
	    lblTitle.setFont(new Font("Mongolian Baiti", Font.PLAIN, 30));
	    lblTitle.setBackground(new Color(255, 255, 255));
	    contentPane.add(lblTitle);
	    
	    lblText = new JTextField();
	    lblText.setEditable(false);
	    lblText.setFocusable(false);
	    lblText.setBackground(new Color(251, 251, 251));
	    lblText.setBounds(244, 182, 207, 34);
	    lblText.setFont(new Font("Mistral", Font.PLAIN, 25));
	    lblText.setForeground(new Color(0, 0, 0));
	    lblText.setHorizontalAlignment(SwingConstants.CENTER);
	    lblText.setText("WELCOME ABOARD!");
	    contentPane.add(lblText);
	    lblText.setColumns(10);
	    lblText.setBorder(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dispose();
		LoginWindow loginWindow=new LoginWindow(this,cont);
		loginWindow.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}