package view;

import java.awt.EventQueue;

import javax.swing.JDialog;

import controller.LoginController;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import model.TypeCruise;
import javax.swing.JButton;

public class CruiseFormWindow extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JTextField textFieldCode;
	private JTextField textFieldName;
	private JTextField textFieldRooms;
	private JTextField textFieldCapacity;
	private JComboBox comboBoxType;
	private JButton btnClear;
	private JButton btnConfirm;

	//hay que cambiar el constructor, en vez de recibir type mejor que reciba todo el objeto de crucero (si se quiere modificar)
	//si se quiere añadir crucero será nulo
	public CruiseFormWindow(CruiseListWindow cruiseListWindow, boolean b, LoginController cont, String type) {
		super(cruiseListWindow,b);
		this.cont=cont;
		
		setTitle(type+" cruise");
		setBounds(100, 100, 600, 497);
		getContentPane().setLayout(null);
		
		JLabel lblInsert = new JLabel("Insert the cruise data");
		lblInsert.setFont(new Font("Bahnschrift", Font.BOLD, 30));
		lblInsert.setBounds(57, 23, 359, 37);
		getContentPane().add(lblInsert);
		
		JLabel lblCode = new JLabel("Code:");
		lblCode.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCode.setBounds(57, 82, 61, 22);
		getContentPane().add(lblCode);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblName.setBounds(57, 131, 61, 22);
		getContentPane().add(lblName);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblType.setBounds(57, 179, 61, 22);
		getContentPane().add(lblType);
		
		JLabel lblCapacity = new JLabel("Maximun capacity:");
		lblCapacity.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCapacity.setBounds(57, 282, 185, 22);
		getContentPane().add(lblCapacity);
		
		JLabel lblRooms = new JLabel("Number of rooms:");
		lblRooms.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblRooms.setBounds(57, 231, 185, 22);
		getContentPane().add(lblRooms);
		
		textFieldCode = new JTextField();
		textFieldCode.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textFieldCode.setBounds(357, 78, 172, 31);
		getContentPane().add(textFieldCode);
		textFieldCode.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textFieldName.setColumns(10);
		textFieldName.setBounds(357, 127, 172, 31);
		getContentPane().add(textFieldName);
		
		comboBoxType = new JComboBox();
		comboBoxType.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		comboBoxType.setModel(new DefaultComboBoxModel(TypeCruise.values()));
		comboBoxType.setBounds(357, 175, 172, 31);
		getContentPane().add(comboBoxType);
		
		textFieldRooms = new JTextField();
		textFieldRooms.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textFieldRooms.setColumns(10);
		textFieldRooms.setBounds(357, 222, 172, 31);
		getContentPane().add(textFieldRooms);
		
		textFieldCapacity = new JTextField();
		textFieldCapacity.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textFieldCapacity.setColumns(10);
		textFieldCapacity.setBounds(357, 273, 172, 31);
		getContentPane().add(textFieldCapacity);
		
		btnClear = new JButton("CLEAR");
		btnClear.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnClear.setBounds(57, 358, 205, 62);
		btnClear.addActionListener(this);
		getContentPane().add(btnClear);
		
		btnConfirm = new JButton("CONFIRM");
		btnConfirm.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		btnConfirm.setBounds(324, 358, 205, 62);
		btnConfirm.addActionListener(this);
		getContentPane().add(btnConfirm);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnClear) {
			
		}
	}
}
