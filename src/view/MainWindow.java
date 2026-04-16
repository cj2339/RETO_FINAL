package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
 * Main management window used by administrators to navigate to the different
 * management modules (cruises, workers, clients, bookings, administrators) and
 * to export data to XML.
 */
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private String adminName;
	private JButton btnCruise;
	private JButton btnWorker;
	private JButton btnClient;
	private JButton btnAdmins;
	private JPanel contentPane;
	private JButton btnBook;
	private JButton btnExport;
	private JLabel lblNewLabel;
	private Image imagenFondo = new ImageIcon("images/water.jpg").getImage();	

	/**
	 * Initializes the main dashboard interface window.
	 *
	 * @param loginWindow The login window prior to this application state.
	 * @param cont        The system login controller.
	 */
	public MainWindow(LoginWindow loginWindow, LoginController cont) {
		super();
		this.cont=cont;
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
		setBounds(100, 100, 450, 333);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));


		lblNewLabel = new JLabel("MANAGEMENT");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(95, 29, 261, 30);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		getContentPane().add(lblNewLabel);

		btnCruise = new JButton("CRUISE");
		btnCruise.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnCruise.setBounds(61, 79, 150, 47);
		getContentPane().add(btnCruise);
		btnCruise.addActionListener(this);

		btnWorker = new JButton("WORKER"); 
		btnWorker.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnWorker.setBounds(235, 83, 150, 47);
		getContentPane().add(btnWorker);
		btnWorker.addActionListener(this);

		btnClient = new JButton("CLIENT");
		btnClient.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnClient.setBounds(61, 136, 150, 47);
		getContentPane().add(btnClient);
		btnClient.addActionListener(this);

		btnAdmins = new JButton("ADMIN");
		btnAdmins.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnAdmins.setBounds(61, 193, 150, 47);
		getContentPane().add(btnAdmins);
		btnAdmins.addActionListener(this);

		btnBook = new JButton("BOOK");
		btnBook.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnBook.setBounds(235, 136, 150, 47);
		contentPane.add(btnBook);
		btnBook.addActionListener(this);

		btnExport = new JButton("EXPORT XML");
		btnExport.setFont(new Font("SansSerif", Font.PLAIN, 17));
		btnExport.setBounds(236, 193, 149, 47);
		contentPane.add(btnExport);
		btnExport.addActionListener(this);

	}

	/**
	 * Triggers the routine to dump the application data to an XML export file.
	 */
	public void exportXML() {
		try {
			export.XMLExporter exporter = new export.XMLExporter(cont);
			exporter.exportToXML();
			JOptionPane.showMessageDialog(this, "XML exported successfully");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error exporting the XML: " + ex.getMessage());
		}
	}


	/**
	 * Orchestrates interaction processing against the navigation action buttons.
	 *
	 * @param e The interaction operation context details.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String[] options = {"Add", "Eliminate", "Modify", "See"};


		if (e.getSource().equals(btnAdmins)) {
			ListAdminWindow listAdmin = new ListAdminWindow(this, cont);
			listAdmin.setVisible(true);

		}else{
			if (e.getSource().equals(btnCruise)) {
				ListCruiseWindow cruiseWindow = new ListCruiseWindow(this, cont);
				cruiseWindow.setVisible(true);
			}else if (e.getSource().equals(btnWorker)) {
				ListWorkerWindow workerWindow=new ListWorkerWindow(this, cont);
				workerWindow.setVisible(true);
			}else if (e.getSource().equals(btnClient)) {
				ListClientWindow clientManagement = new ListClientWindow(this, cont);
				clientManagement.setVisible(true);
			} else if (e.getSource().equals(btnBook)) {
				ListBookWindow bookingWindow = new ListBookWindow(this, cont);
				bookingWindow.setVisible(true);
			}else if (e.getSource().equals(btnExport)) {
				exportXML();
			}
		}
	}
}