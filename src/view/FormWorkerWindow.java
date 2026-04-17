package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.Worker;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import model.Cruise;
import model.TypeWorker;
import javax.swing.JSpinner;
import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

/**
 * Modal dialog that shows a form to add or modify a Worker. The form collects
 * personal data, hiring date, language flags and associated cruise. Uses
 * LoginController to persist or update the worker record.
 * 
 * @author Etna
 */
public class FormWorkerWindow extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Image backgroundImage = new ImageIcon("images/FondoForms.png").getImage();
	private final JPanel contentPanel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	};
	private LoginController cont;
	private Worker worker;
	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JComboBox comboBoxService;
	private JButton btnClear;
	private JButton btnConfirm;
	private JTextField textFieldPhone;
	private JDateChooser calendarHiringDate;
	private JTextField textFieldEmail;
	private JCheckBox chckbxSpanish;
	private JSpinner spinnerAge;
	private JCheckBox chckbxEnglish;
	private JComboBox comboBoxCruiseCode;

	private List<Cruise> cruiseCodes;

	/**
	 * Constructs the worker form dialog window.
	 *
	 * @param workerListWindow The modal parent window.
	 * @param cont             The controller for executing logic.
	 * @param worker           The worker object to edit, or null if inserting a new worker.
	 * @param b                Flag to specify insertion or update.
	 */
	public FormWorkerWindow(ListWorkerWindow workerListWindow, LoginController cont, Worker worker, boolean b) {
		super(workerListWindow, true);
		this.cont=cont;
		this.worker=worker;

		setBounds(100, 100, 600, 814);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Fill the worker data:");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setBounds(57, 37, 320, 25);
		contentPanel.add(lblNewLabel);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblId.setBounds(57, 93, 126, 25);
		contentPanel.add(lblId);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblName.setBounds(57, 210, 126, 25);
		contentPanel.add(lblName);

		JLabel lblName_1 = new JLabel("Surname:");
		lblName_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblName_1.setBounds(57, 268, 126, 25);
		contentPanel.add(lblName_1);

		JLabel lblService = new JLabel("Service:");
		lblService.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblService.setBounds(57, 149, 126, 25);
		contentPanel.add(lblService);

		textFieldId = new JTextField();
		textFieldId.setEditable(false);
		textFieldId.setFont(new Font("SansSerif", Font.PLAIN, 19));
		textFieldId.setBounds(292, 93, 201, 24);
		contentPanel.add(textFieldId);
		textFieldId.setColumns(10);
		textFieldId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (textFieldId.getText().length() > 8) {
					e.consume();
				}
			}
		});

		comboBoxService = new JComboBox();
		comboBoxService.setFont(new Font("SansSerif", Font.PLAIN, 19));
		comboBoxService.setModel(new DefaultComboBoxModel(TypeWorker.values()));
		comboBoxService.setBounds(292, 149, 201, 25);
		contentPanel.add(comboBoxService);

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("SansSerif", Font.PLAIN, 19));
		textFieldName.setColumns(10);
		textFieldName.setBounds(292, 210, 201, 24);
		contentPanel.add(textFieldName);

		textFieldSurname = new JTextField();
		textFieldSurname.setFont(new Font("SansSerif", Font.PLAIN, 19));
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(292, 268, 201, 24);
		contentPanel.add(textFieldSurname);

		JLabel lblCruiseCode = new JLabel("Cruise code:");
		lblCruiseCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblCruiseCode.setBounds(57, 541, 126, 25);
		contentPanel.add(lblCruiseCode);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnConfirm.setBounds(318, 714, 133, 37);
		contentPanel.add(btnConfirm);
		btnConfirm.addActionListener(this);

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnClear.setBounds(115, 714, 133, 37);
		contentPanel.add(btnClear);

		JLabel lblHiringDate = new JLabel("Hiring date:");
		lblHiringDate.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblHiringDate.setBounds(57, 324, 126, 25);
		contentPanel.add(lblHiringDate);

		calendarHiringDate = new JDateChooser();
		calendarHiringDate.setDateFormatString("dd/MM/yyyy");
		calendarHiringDate.setBounds(292, 324, 201, 25);
		contentPanel.add(calendarHiringDate);

		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblPhoneNumber.setBounds(57, 379, 151, 25);
		contentPanel.add(lblPhoneNumber);

		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("SansSerif", Font.PLAIN, 19));
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(292, 379, 201, 24);
		contentPanel.add(textFieldPhone);
		textFieldPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}else if (textFieldPhone.getText().length() > 8) {
					e.consume();
				}
			}
		});

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblEmail.setBounds(57, 437, 151, 25);
		contentPanel.add(lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("SansSerif", Font.PLAIN, 19));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(292, 437, 201, 24);
		contentPanel.add(textFieldEmail);

		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblLanguage.setBounds(57, 589, 126, 25);
		contentPanel.add(lblLanguage);

		chckbxSpanish = new JCheckBox("Spanish");
		chckbxSpanish.setFont(new Font("SansSerif", Font.PLAIN, 20));
		chckbxSpanish.setBounds(82, 627, 144, 20);
		contentPanel.add(chckbxSpanish);

		chckbxEnglish = new JCheckBox("English");
		chckbxEnglish.setFont(new Font("SansSerif", Font.PLAIN, 20));
		chckbxEnglish.setBounds(82, 661, 144, 20);
		contentPanel.add(chckbxEnglish);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblAge.setBounds(57, 493, 151, 25);
		contentPanel.add(lblAge);

		spinnerAge = new JSpinner();
		spinnerAge.setModel(new SpinnerNumberModel(18, 18, 75, 1));
		spinnerAge.setFont(new Font("SansSerif", Font.PLAIN, 20));
		spinnerAge.setBounds(292, 492, 201, 25);
		contentPanel.add(spinnerAge);

		comboBoxCruiseCode = new JComboBox<Cruise>();
		List<Cruise> cruises = cont.getAllCruise();
		for(Cruise c:cruises) {
			comboBoxCruiseCode.addItem(c);
		}
		comboBoxCruiseCode.setBounds(292, 541, 201, 25);
		contentPanel.add(comboBoxCruiseCode);
		btnClear.addActionListener(this);

		if(worker!=null) {
			setTitle("Modify worker");
			textFieldId.setText(worker.getIdWorker());
			comboBoxService.setSelectedItem(worker.getService());
			textFieldName.setText(worker.getName());
			textFieldSurname.setText(worker.getSurname());
			calendarHiringDate.setDate(worker.getHiringDate());
			textFieldPhone.setText(worker.getPhoneNumber());
			textFieldEmail.setText(worker.getEmail());
			spinnerAge.setValue(worker.getAge());
			if(worker.isSpanish()) {
				chckbxSpanish.setSelected(true);
			}else {
				chckbxSpanish.setSelected(false);
			}
			if(worker.isEnglish()) {
				chckbxEnglish.setSelected(true);
			}else {
				chckbxEnglish.setSelected(false);
			}
			selectCruiseCode();
		}else {
			setTitle("Add worker");
			calendarHiringDate.setDate(new java.util.Date());
		}


	}

	/**
	 * Selects the corresponding cruise code in the combo box based on the worker's cruise.
	 */
	public void selectCruiseCode() {
		int id = worker.getCruise().getCodCruise();
		int i = 0;
		boolean found = false;
		//getItemCount cuenta la cantidad de elementos de un combo
		while (i < comboBoxCruiseCode.getItemCount() && !found) {
			Cruise c = (Cruise) comboBoxCruiseCode.getItemAt(i);
			if (c.getCodCruise()==id) {
				comboBoxCruiseCode.setSelectedIndex(i);
				found = true;
			}
			i++;
		}
	}

	/**
	 * Handles interactions with the interface buttons to either clear the form
	 * or confirm the worker insertion/modification.
	 *
	 * @param e The triggered action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String dateStr;
		String dniRegexp = "^[0-9]{8}[A-Z]$";
		String emailRegexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
		String idInput = textFieldId.getText().toUpperCase();
		boolean isValid;

		if(e.getSource()==btnClear) {
			textFieldId.setText(null);
			comboBoxService.setSelectedIndex(0);
			textFieldName.setText(null);
			textFieldSurname.setText(null);
			calendarHiringDate.setDate(new java.util.Date());
			textFieldPhone.setText(null);
			textFieldEmail.setText(null);
			spinnerAge.setValue(18);
			chckbxSpanish.setSelected(false);
			chckbxEnglish.setSelected(false);
			comboBoxCruiseCode.setSelectedIndex(0);
		}
		if(e.getSource()==btnConfirm) {
			isValid=true;
			if(textFieldId.getText().isEmpty()||textFieldName.getText().isEmpty()||textFieldSurname.getText().isEmpty()||
					textFieldPhone.getText().isEmpty()||textFieldEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill the data in all fields");
				isValid=false;
			}
			if(isValid) {
				if(textFieldPhone.getText().length() < 9) {
					JOptionPane.showMessageDialog(this, "The phone number must have 9 digits");
					isValid=false;
				}else if (!idInput.matches(dniRegexp)) {
					JOptionPane.showMessageDialog(this, "ID format invalid");
					isValid=false;
				}else if (!textFieldEmail.getText().matches(emailRegexp)) {
					JOptionPane.showMessageDialog(this, "Email format invalid");
					isValid=false;
				}
			}
			if(isValid) {
				Worker workerTemp=new Worker(
						textFieldId.getText(),
						(TypeWorker) comboBoxService.getSelectedItem(),
						textFieldName.getText(),
						textFieldSurname.getText(),
						calendarHiringDate.getDate(),
						textFieldPhone.getText(),
						textFieldEmail.getText(),
						(int)spinnerAge.getValue(),
						chckbxSpanish.isSelected(),
						chckbxEnglish.isSelected(),
						(Cruise)comboBoxCruiseCode.getSelectedItem()
						);
				if(worker!=null) {
					if(!worker.getPhoneNumber().equals(textFieldPhone.getText()) && cont.phoneWorkerExists(textFieldPhone.getText())) {
	                    JOptionPane.showMessageDialog(this, "Phone number already exists");
	                    isValid = false;
	                }
	                
	                if(isValid && !worker.getEmail().equals(textFieldEmail.getText()) && cont.emailWorkerExists(textFieldEmail.getText())) {
	                    JOptionPane.showMessageDialog(this, "Email already exists");
	                    isValid = false;
	                }

	                if(isValid) {
	                    cont.updateWorker(workerTemp);
	                    JOptionPane.showMessageDialog(this, "Worker updated successfully.");
	                    this.dispose();
	                }

				}else {
					if(cont.phoneWorkerExists(textFieldPhone.getText())) {
	                    JOptionPane.showMessageDialog(this, "Phone number already exists");
	                    isValid = false;
	                } else if(cont.emailWorkerExists(textFieldEmail.getText())) {
	                    JOptionPane.showMessageDialog(this, "Email already exists");
	                    isValid = false;
	                }

	                if(isValid) {
	                    cont.insertWorker(workerTemp);
	                    JOptionPane.showMessageDialog(this, "Worker added successfully.");
	                    this.dispose();
	                }
				}
			}



		}
	}
}