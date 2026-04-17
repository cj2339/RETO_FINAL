package view;

import javax.swing.*;

import controller.LoginController;

import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Initial application window that displays a welcome screen.
 * When the user clicks the window it closes and opens the login dialog.
 * This class extends JFrame and implements MouseListener to detect clicks.
 * 
 * @author Santiago
 * @author Iker
 * @author Etna
 * @author Aritz
 */
public class StartWindow extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JLabel lblName;
	private Image BackgroundImage = new ImageIcon("images/background1.png").getImage();
	private JTextField lblText;

	/**
	 * Launches the startup window setting up graphical dependencies.
	 *
	 * @param loginController The application overarching communication logic.
	 */
	public StartWindow(LoginController loginController) {
		this.cont=loginController; 

		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 550);

		//Prevent the window from being resized or maximised
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
		
		JLabel lblNewLabel = new JLabel("CLICK TO START!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
		lblNewLabel.setBounds(212, 415, 274, 34);
		contentPane.add(lblNewLabel);
	}

	/**
	 * Responds to mouse click events over the window canvas.
	 *
	 * @param e Details related to the mouse input event.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		dispose();
		LoginWindow loginWindow=new LoginWindow(null,cont);
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