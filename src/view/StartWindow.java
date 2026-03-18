package view;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class StartWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JLabel lblTitulo;
	private JLabel lblNombre;
	private Image imagenFondo = new ImageIcon("FotooFondo.png").getImage();
	private JTextField txtbienvenidosABordo;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow frame = new StartWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartWindow() {
	    setIconImage(Toolkit.getDefaultToolkit().getImage("53283.png"));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 779, 550);

	    // 🔒 Bloquear que la ventana pueda redimensionarse o maximizarse
	    setResizable(false);

	    contentPane = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
 
	    lblNombre = new JLabel();
	    lblNombre.setBounds(148, 40, 250, 20);
	    lblNombre.setFont(new Font("Tahoma", Font.BOLD, 10));
	    lblNombre.setBackground(new Color(255, 255, 255));
	    contentPane.add(lblNombre);

	    lblTitulo = new JLabel();
	    lblTitulo.setBounds(148, 80, 250, 20);
	    lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 10));
	    lblTitulo.setBackground(new Color(255, 255, 255));
	    contentPane.add(lblTitulo);
	    
	    txtbienvenidosABordo = new JTextField();
	    txtbienvenidosABordo.setEditable(false);
	    txtbienvenidosABordo.setBackground(new Color(251, 251, 251));
	    txtbienvenidosABordo.setBounds(244, 182, 207, 34);
	    txtbienvenidosABordo.setFont(new Font("Mistral", Font.PLAIN, 25));
	    txtbienvenidosABordo.setForeground(new Color(0, 0, 0));
	    txtbienvenidosABordo.setHorizontalAlignment(SwingConstants.CENTER);
	    txtbienvenidosABordo.setText("WELCOME ABOARD!");
	    contentPane.add(txtbienvenidosABordo);
	    txtbienvenidosABordo.setColumns(10);
	    txtbienvenidosABordo.setBorder(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}