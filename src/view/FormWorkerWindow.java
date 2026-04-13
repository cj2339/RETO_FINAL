package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.Worker;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import model.TypeWorker;
import javax.swing.JSpinner;
import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;

public class FormWorkerWindow extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private Worker worker;
	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JComboBox comboBoxService;
	private JButton btnClear;
	private JButton btnConfirm;
	private JTextField textFieldPhone;
	private JDateChooser txtHiringDate;
	private JTextField textFieldEmail;
	private JCheckBox chckbxSpanish;

	public FormWorkerWindow(ListWorkerWindow workerListWindow, LoginController cont, Worker worker, boolean b) {
		super(workerListWindow, true);
		setTitle("worker");
		this.cont=cont;
		
		setBounds(100, 100, 600, 846);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fill the worker data:");
		lblNewLabel.setFont(new Font("Bahnschrift", Font.BOLD, 20));
		lblNewLabel.setBounds(33, 36, 228, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblId.setBounds(57, 93, 126, 25);
		contentPanel.add(lblId);
		
		textFieldId = new JTextField();
		textFieldId.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldId.setBounds(318, 93, 175, 24);
		contentPanel.add(textFieldId);
		textFieldId.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblName.setBounds(57, 210, 126, 25);
		contentPanel.add(lblName);
		
		JLabel lblName_1 = new JLabel("Surname:");
		lblName_1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblName_1.setBounds(57, 268, 126, 25);
		contentPanel.add(lblName_1);
		
		JLabel lblService = new JLabel("Service:");
		lblService.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblService.setBounds(57, 149, 126, 25);
		contentPanel.add(lblService);
		
		comboBoxService = new JComboBox();
		comboBoxService.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		comboBoxService.setModel(new DefaultComboBoxModel(TypeWorker.values()));
		comboBoxService.setBounds(318, 149, 175, 25);
		contentPanel.add(comboBoxService);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldName.setColumns(10);
		textFieldName.setBounds(318, 210, 175, 24);
		contentPanel.add(textFieldName);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(318, 268, 175, 24);
		contentPanel.add(textFieldSurname);
		
		JLabel lblCruiseCode = new JLabel("Cruise code:");
		lblCruiseCode.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCruiseCode.setBounds(57, 488, 126, 25);
		contentPanel.add(lblCruiseCode);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnConfirm.setBounds(360, 737, 133, 37);
		contentPanel.add(btnConfirm);
		btnConfirm.addActionListener(this);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnClear.setBounds(57, 737, 133, 37);
		contentPanel.add(btnClear);
		
		JLabel lblHiringDate = new JLabel("Hiring date:");
		lblHiringDate.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHiringDate.setBounds(57, 324, 126, 25);
		contentPanel.add(lblHiringDate);
		
		txtHiringDate = new JDateChooser();
		txtHiringDate.setDateFormatString("dd/mm/yyyy");
		txtHiringDate.setBounds(318, 324, 175, 25);
		contentPanel.add(txtHiringDate);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblPhoneNumber.setBounds(57, 379, 151, 25);
		contentPanel.add(lblPhoneNumber);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(318, 379, 175, 24);
		contentPanel.add(textFieldPhone);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblEmail.setBounds(57, 437, 151, 25);
		contentPanel.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(318, 437, 175, 24);
		contentPanel.add(textFieldEmail);
		
		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLanguage.setBounds(57, 535, 126, 25);
		contentPanel.add(lblLanguage);
		
		chckbxSpanish = new JCheckBox("Spanish");
		chckbxSpanish.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxSpanish.setBounds(82, 581, 144, 20);
		contentPanel.add(chckbxSpanish);
		
		JCheckBox chckbxEnglish = new JCheckBox("English");
		chckbxEnglish.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxEnglish.setBounds(82, 620, 144, 20);
		contentPanel.add(chckbxEnglish);
		
		JCheckBox chckbxFrench = new JCheckBox("French");
		chckbxFrench.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxFrench.setBounds(82, 661, 144, 20);
		contentPanel.add(chckbxFrench);
		
		JCheckBox chckbxGerman = new JCheckBox("German");
		chckbxGerman.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxGerman.setBounds(318, 581, 144, 20);
		contentPanel.add(chckbxGerman);
		
		JCheckBox chckbxChinese = new JCheckBox("Chinese");
		chckbxChinese.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxChinese.setBounds(318, 620, 144, 20);
		contentPanel.add(chckbxChinese);
		
		JCheckBox chckbxArabic = new JCheckBox("Arabic");
		chckbxArabic.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		chckbxArabic.setBounds(318, 661, 144, 20);
		contentPanel.add(chckbxArabic); 
		btnClear.addActionListener(this);
		
		if(worker!=null) {
			textFieldId.setText(worker.getIdWorker());
			//comboBoxService.setSelectedIndex();
			textFieldName.setText(worker.getName());
			textFieldSurname.setText(worker.getSurname());
			//comboBoxCodCruise.setSelectedIndex();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnClear) {
			textFieldId.setText("");
			comboBoxService.setSelectedIndex(0);
			textFieldName.setText("");
			textFieldSurname.setText("");
			//comboBoxCodCruise.setSelectedIndex(0);
		}
		if(e.getSource()==btnConfirm) {
			
		}
	}
}
