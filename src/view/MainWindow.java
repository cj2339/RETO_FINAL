package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import java.awt.Color;
import javax.swing.SwingConstants;

/**
 * Main application window that provides access to all management modules,
 * including cruises, workers, clients, administrators, bookings and XML export.
 * Acts as the central navigation hub and delegates actions to the corresponding
 * management dialogs through the LoginController.
 *
 * @author Santiago
 * @author Iker
 * @author Etna
 * @author Aritz
 */
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	//private String adminName; creo que se puede quitar 
	private JButton btnCruise;
	private JButton btnWorker;
	private JButton btnClient;
	private JButton btnAdmins;
	private JPanel contentPane;
	private JButton btnBook;
	private JButton btnExport;
	private JLabel lblNewLabel;
	private Image imagenFondo = new ImageIcon("images/FondoManagment.png").getImage();


	private ImageIcon insertIcon(String ruta, int ancho, int alto) {
		Image img = new ImageIcon(ruta).getImage();
		Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(imgEscalada);
	}

	public MainWindow(LoginWindow loginWindow, LoginController cont) {
		super();
		this.cont = cont;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};
		setContentPane(contentPane);

		setTitle("Management");
		setBounds(100, 100, 725, 592);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));

		lblNewLabel = new JLabel("MANAGEMENT");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(197, 37, 305, 39);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		getContentPane().add(lblNewLabel);


		btnCruise = new JButton("CRUISE", insertIcon("images/IconoCruise.png", 43, 43));
		btnCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnCruise.setBounds(61, 118, 213, 69);
		getContentPane().add(btnCruise);
		btnCruise.addActionListener(this);

		btnWorker = new JButton("WORKER", insertIcon("images/IconoWorker.png",  43,  43));
		btnWorker.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnWorker.setBounds(399, 118, 200, 69);
		getContentPane().add(btnWorker);
		btnWorker.addActionListener(this);

		btnClient = new JButton("CLIENT", insertIcon("images/IconoClient.png", 43, 43));
		btnClient.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnClient.setBounds(61, 241, 213, 69);
		getContentPane().add(btnClient);
		btnClient.addActionListener(this);

		btnAdmins = new JButton("ADMIN", insertIcon("images/IconoAdmin.png",  43,  43));
		btnAdmins.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnAdmins.setBounds(61, 365, 213, 69);
		getContentPane().add(btnAdmins);
		btnAdmins.addActionListener(this);

		btnBook = new JButton("BOOK", insertIcon("images/IconoReserva.png",  43,  43));
		btnBook.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnBook.setBounds(399, 241, 200, 69);
		contentPane.add(btnBook);
		btnBook.addActionListener(this);

		btnExport = new JButton("EXPORT XML", insertIcon("images/IconoXml.png",  43,  43));
		btnExport.setFont(new Font("SansSerif", Font.PLAIN, 17));
		btnExport.setBounds(399, 366, 200, 68);
		contentPane.add(btnExport);
		btnExport.addActionListener(this);


		JButton[] botones = {btnCruise, btnWorker, btnClient, btnAdmins, btnBook, btnExport};
		for (JButton button : botones) {
			button.setHorizontalTextPosition(SwingConstants.RIGHT);
			button.setIconTextGap(20);
			button.setFocusPainted(false);
			button.setBackground(new Color(200, 230, 255));
		}
	}

	public void exportXML() {
		try {
			export.XMLExporter exporter = new export.XMLExporter(cont);
			exporter.exportToXML();
			JOptionPane.showMessageDialog(this, "XML exported successfully");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error exporting the XML: " + ex.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnAdmins)) {
			ListAdminWindow listAdmin = new ListAdminWindow(this, cont);
			listAdmin.setVisible(true);

		} else {
			if (e.getSource().equals(btnCruise)) {
				ListCruiseWindow cruiseWindow = new ListCruiseWindow(this, cont);
				cruiseWindow.setVisible(true);
			} else if (e.getSource().equals(btnWorker)) {
				ListWorkerWindow workerWindow = new ListWorkerWindow(this, cont);
				workerWindow.setVisible(true);
			} else if (e.getSource().equals(btnClient)) {
				ListClientWindow clientManagement = new ListClientWindow(this, cont);
				clientManagement.setVisible(true);
			} else if (e.getSource().equals(btnBook)) {
				ListBookWindow bookingWindow = new ListBookWindow(this, cont);
				bookingWindow.setVisible(true);
			} else if (e.getSource().equals(btnExport)) {
				exportXML();
			}
		}
	}
}
