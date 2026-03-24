package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AddWindow extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private Image backgroundImage = new ImageIcon("images/waterBackground.png").getImage();
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	private JTextField textField7;
	private String type;
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;
	private JLabel lbl4;
	private JLabel lbl5;
	private JLabel lbl6;
	private JLabel lbl7;
	private JButton btn;
	private JComboBox<String> comboBox1;
	private JComboBox<String> comboBox2;
	private ArrayList<String> codes = new ArrayList<>();
	private ArrayList<String> ids = new ArrayList<>();


	public AddWindow(MainWindow mainWindow, boolean b, LoginController cont, String type) {
		super(mainWindow, b);
		this.cont=cont;
		this.type=type;

		setTitle("Add");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setBounds(100, 100, 520, 474);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		setResizable(false);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblInsert = new JLabel("Insert the "+type+" data");
		lblInsert.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		lblInsert.setBounds(33, 34, 306, 25);
		contentPane.add(lblInsert);

		lbl1 = new JLabel("");
		lbl1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl1.setBounds(55, 70, 175, 25);
		contentPane.add(lbl1);

		lbl2 = new JLabel("");
		lbl2.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl2.setBounds(55, 108, 175, 25);
		contentPane.add(lbl2);

		lbl3 = new JLabel("");
		lbl3.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl3.setBounds(55, 143, 175, 25);
		contentPane.add(lbl3);

		lbl4 = new JLabel("");
		lbl4.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl4.setBounds(55, 178, 175, 25);
		contentPane.add(lbl4);

		lbl5 = new JLabel("");
		lbl5.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl5.setBounds(55, 213, 175, 25);
		contentPane.add(lbl5);

		lbl6 = new JLabel("");
		lbl6.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl6.setBounds(55, 248, 175, 25);
		contentPane.add(lbl6);

		lbl7 = new JLabel("");
		lbl7.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbl7.setBounds(55, 282, 175, 25);
		contentPane.add(lbl7);

		textField1 = new JTextField();
		textField1.setEditable(false);
		textField1.setBounds(292, 70, 156, 25);
		contentPane.add(textField1);
		textField1.setColumns(10);

		textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setBounds(292, 108, 156, 25);
		contentPane.add(textField2);

		textField3 = new JTextField();
		textField3.setColumns(10);
		textField3.setBounds(292, 143, 156, 25);
		contentPane.add(textField3);

		textField4 = new JTextField();
		textField4.setColumns(10);
		textField4.setBounds(292, 178, 156, 25);
		contentPane.add(textField4);

		textField5 = new JTextField();
		textField5.setColumns(10);
		textField5.setBounds(292, 213, 156, 25);
		contentPane.add(textField5);

		textField6 = new JTextField();
		textField6.setColumns(10);
		textField6.setBounds(292, 248, 156, 25);
		contentPane.add(textField6);

		textField7 = new JTextField();
		textField7.setColumns(10);
		textField7.setBounds(292, 282, 156, 25);
		contentPane.add(textField7);

		btn = new JButton("Confirm");
		btn.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btn.setBounds(292, 378, 156, 36);
		contentPane.add(btn);

		comboBox1 = new JComboBox<String>();
		comboBox1.setVisible(false);
		contentPane.add(comboBox1);

		comboBox2 = new JComboBox();
		comboBox1.setVisible(false);
		contentPane.add(comboBox2);

		//llama al método para rellenar los combobox con los códigos
		codes=cont.getCruiseCodes();
		ids=cont.getWorkerCodes();


		if(type.equals("cruise")) {
			lbl1.setText("Cruise code:");
			lbl2.setText("Origin city:");
			lbl3.setText("Type");
			lbl4.setText("Number of rooms:");
			lbl5.setText("Max capacity:");
			textField3.setVisible(false);
			textField6.setVisible(false);
			textField7.setVisible(false);
			comboBox1.setVisible(true);
			comboBox1.setBounds(292, 143, 156, 25);
			comboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"luxury", "premium", "family", "expedition"}));
		}else if(type.equals("worker")) {
			lbl1.setText("Worker ID:");
			lbl2.setText("Service:");
			lbl3.setText("Name:");
			lbl4.setText("Surname:");
			lbl5.setText("Cruise code:");
			textField2.setVisible(false);
			textField5.setVisible(false);
			textField6.setVisible(false);
			textField7.setVisible(false);
			comboBox1.setVisible(true);
			comboBox1.setBounds(292, 108, 156, 25);
			comboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"captain", "cook", "guide", "receptionist"}));
			comboBox2.setVisible(true);
			for(String code: codes) {
				comboBox2.addItem(code);
			}
			comboBox2.setBounds(292, 213, 156, 25);
		}else if(type.equals("client")) {
			lbl1.setText("Client ID:");
			lbl2.setText("Name:");
			lbl3.setText("Surname:");
			lbl4.setText("Age:");
			textField5.setVisible(false);
			textField6.setVisible(false);
			textField7.setVisible(false);
		}else if(type.equals("book")) {
			lbl1.setText("Client ID:");
			lbl2.setText("Cruise ID:");
			lbl3.setText("Origin city:");
			lbl4.setText("Destination city:");
			lbl1.setText("Start date:");
			lbl2.setText("End date:");
			lbl3.setText("Base price:");
			textField1.setVisible(false);
			textField2.setVisible(false);
			comboBox1.setVisible(true);
			comboBox2.setVisible(true);
			for(String code: codes) {
				comboBox1.addItem(code);
			}
			for(String id: ids) {
				comboBox2.addItem(id);
			}
			comboBox1.setBounds(55, 70, 175, 25);
			comboBox2.setBounds(55, 108, 175, 25);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn) {
			
			
			textField1.setText("");
			textField2.setText("");
			textField3.setText("");
			textField4.setText("");
			textField5.setText("");
			textField6.setText("");
			textField7.setText("");
		}

	}
}
