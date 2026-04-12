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
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import model.TypeWorker;

public class WorkerFormWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private Worker worker;
	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JComboBox comboBoxService;
	private JComboBox comboBoxCruiseCode;
	private JButton btnClear;
	private JButton btnConfirm;

	public WorkerFormWindow(WorkerListWindow workerListWindow, LoginController cont, Worker worker, boolean b) {
		super(workerListWindow, true);
		setTitle("worker");
		this.cont=cont;
		
		setBounds(100, 100, 500, 600);
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
		textFieldId.setBounds(279, 93, 175, 24);
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
		comboBoxService.setBounds(279, 149, 175, 25);
		contentPanel.add(comboBoxService);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldName.setColumns(10);
		textFieldName.setBounds(279, 213, 175, 24);
		contentPanel.add(textFieldName);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(279, 269, 175, 24);
		contentPanel.add(textFieldSurname);
		
		JLabel lblCruiseCode = new JLabel("Cruise code:");
		lblCruiseCode.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCruiseCode.setBounds(57, 331, 126, 25);
		contentPanel.add(lblCruiseCode);
		
		comboBoxCruiseCode = new JComboBox();
		comboBoxCruiseCode.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		comboBoxCruiseCode.setBounds(279, 333, 175, 25);
		contentPanel.add(comboBoxCruiseCode);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnConfirm.setBounds(302, 452, 133, 37);
		contentPanel.add(btnConfirm);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnClear.setBounds(75, 452, 133, 37);
		contentPanel.add(btnClear);
	}
}
