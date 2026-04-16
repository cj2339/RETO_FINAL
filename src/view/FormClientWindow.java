package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Client;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Dialog that displays a form to add or modify a Client. Validates numeric
 * fields and delegates insert/update actions to the LoginController.
 * 
 * @author Santiago
 */
public class FormClientWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Image backgroundImage = new ImageIcon("images/FondoForms.png").getImage();
	private final JPanel panel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	};
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtPhone; 
	private JTextField txtEmail; 
	private JButton okButton;
	private boolean isInsert;
	private LoginController controller;
	private Client client;
	private ListClientWindow parent;
	private JLabel lblFillTheClient;
	private JButton btnClear;
	private JSpinner spinnerAge;

	/**
	 * Constructs a new dialog for client management (creation or modification).
	 *
	 * @param clientManagement The parent dialog window.
	 * @param controller       The application's logic controller.
	 * @param client           The client object to edit, or null if inserting.
	 * @param isInsert         Boolean flag indicating whether it's an insertion or update.
	 */
	public FormClientWindow(JDialog clientManagement, LoginController controller, Client client, boolean isInsert) {
		super(clientManagement, true);
		if(isInsert) {
			setTitle("Add client");
		}else {
			setTitle("Modify client");
		}

		// He aumentado el alto de la ventana a 450 para que quepan los campos nuevos
		setBounds(100, 100, 531, 484);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		this.isInsert = isInsert;
		this.controller = controller;
		this.client = client;
		this.parent = (ListClientWindow) clientManagement;

		txtId = new JTextField();
		txtId.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtId.setColumns(10);
		txtId.setBounds(287, 58, 186, 30);
		panel.add(txtId);
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (txtId.getText().length() > 8) {
					e.consume();
				}
			}
		});

		txtName = new JTextField();
		txtName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtName.setColumns(10);
		txtName.setBounds(287, 108, 186, 30);
		panel.add(txtName);

		txtSurname = new JTextField();
		txtSurname.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtSurname.setColumns(10);
		txtSurname.setBounds(287, 158, 186, 30);
		panel.add(txtSurname);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtPhone.setColumns(10);
		txtPhone.setBounds(287, 256, 186, 30);
		panel.add(txtPhone);
		txtPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}else if (txtPhone.getText().length() > 8) {
					e.consume();
				}
			}
		});


		txtEmail = new JTextField();
		txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 20));
		txtEmail.setColumns(10);
		txtEmail.setBounds(287, 302, 186, 30);
		panel.add(txtEmail);

		JLabel lblId = new JLabel("Client ID:");
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblId.setBounds(52, 58, 116, 30);
		panel.add(lblId);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblName.setBounds(52, 111, 98, 30);
		panel.add(lblName);

		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSurname.setBounds(52, 161, 88, 30);
		panel.add(lblSurname);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblAge.setBounds(52, 208, 56, 30);
		panel.add(lblAge);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblPhone.setBounds(52, 259, 98, 30);
		panel.add(lblPhone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblEmail.setBounds(52, 305, 98, 30);
		panel.add(lblEmail);

		lblFillTheClient = new JLabel("Fill the client data:");
		lblFillTheClient.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblFillTheClient.setBounds(48, 10, 287, 36);
		panel.add(lblFillTheClient);

		okButton = new JButton("Confirm");
		okButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		okButton.setBounds(287, 369, 116, 36);
		panel.add(okButton);
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnClear.setActionCommand("OK");
		btnClear.setBounds(131, 369, 116, 36);
		panel.add(btnClear);

		spinnerAge = new JSpinner();
		spinnerAge.setModel(new SpinnerNumberModel(18, 18, 75, 1));
		spinnerAge.setFont(new Font("SansSerif", Font.PLAIN, 20));
		spinnerAge.setBounds(287, 208, 186, 31);
		panel.add(spinnerAge);
		okButton.addActionListener(this);


		//		if (this.client != null) {
		//			txtId.setText(client.getIdClient());
		//			txtName.setText(client.getNameClient());
		//			txtSurname.setText(client.getSurnameClient());
		//			(int)spinnerAge.getValue();
		//			txtPhone.setText(String.valueOf(client.getPhoneClient()));
		//			txtEmail.setText(client.getEmailClient());
		//		}
	}


	/**
	 * Responds to action events from the interface components (buttons).
	 *
	 * @param e The fired action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String dniRegexp;
		String idInput;
		if (e.getSource() == okButton) {
			dniRegexp = "^[0-9]{8}[A-Z]$";
			idInput = txtId.getText().toUpperCase();
			if (txtId.getText().isEmpty()||txtName.getText().isEmpty()||txtSurname.getText().isEmpty()||txtPhone.getText().isEmpty()
					||txtEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill the data in all fields.");
			}else if(txtPhone.getText().length() < 9) {
				JOptionPane.showMessageDialog(this, "The phone number must have 9 digits");
			} else if (!idInput.matches(dniRegexp)) {
				JOptionPane.showMessageDialog(this, "ID format invalid");
			}else {
				Client newClient = new Client(
						txtId.getText(),
						txtName.getText(),
						txtSurname.getText(),
						(int)spinnerAge.getValue(),
						Integer.parseInt(txtPhone.getText()), 
						txtEmail.getText()                    
						);
				if (isInsert) {
					if (controller.insertClient(newClient)) {
						JOptionPane.showMessageDialog(this, "Client added successfully.");
						parent.refreshModel();
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Error adding client.");
					}
				} else {
					if (controller.updateClientByCode(newClient)) {
						JOptionPane.showMessageDialog(this, "Client modified successfully.");
						parent.refreshModel();
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Error modifying client.");
					}
				}
			}


		}else if(e.getSource()==btnClear) {
			txtId.setText(null);
			txtName.setText(null);
			txtSurname.setText(null);
			spinnerAge.setValue(18);
			txtPhone.setText(null);
			txtEmail.setText(null);
		}
	}

}
