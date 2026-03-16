package vista;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class VentanaLogin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JLabel lblTitulo;
	private JLabel lblNombre;
	private Image imagenFondo = new ImageIcon("FotoFondo2.png").getImage();
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("53283.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 450);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setLayout(null);
		setContentPane(contentPane);

		lblNombre = new JLabel();
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNombre.setBackground(new Color(255, 255, 255));
		lblNombre.setBounds(148, 40, 250, 20);
		contentPane.add(lblNombre);

		lblTitulo = new JLabel();
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblTitulo.setBackground(new Color(255, 255, 255));
		lblTitulo.setBounds(148, 80, 250, 20);
		contentPane.add(lblTitulo);

	}



	public void actionPerformed(ActionEvent e) {
	}
}