package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.LoginController;
import model.Cruise;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CruiseListWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private String adminName;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	JTable table;

	public CruiseListWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		setTitle("Cruises");
		this.cont = cont;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		cargarTabla();
		
		contentPane.setLayout(null);

		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 44, 567, 231);
		getContentPane().add(scrollPane);

		btnADD = new JButton("ADD");
		btnADD.setBounds(96, 303, 97, 25);
		btnADD.addActionListener(this);
		contentPane.add(btnADD);

		btnDELETE = new JButton("DELETE");
		btnDELETE.setBounds(247, 343, 97, 25);
		btnDELETE.addActionListener(this);
		contentPane.add(btnDELETE);

		btnMODIFY = new JButton("MODIFY");
		btnMODIFY.addActionListener(this);

		btnMODIFY.setBounds(400, 303, 97, 25);
		contentPane.add(btnMODIFY);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String type=null;
		int row = table.getSelectedRow();
		
		if (e.getSource() == btnDELETE) {

			if (row == -1) {
				// No hay fila seleccionada
				JOptionPane.showMessageDialog(this, "Select a cruise to delete.");
				return;
			}

			// Obtener el código de la columna 0
			String codCruise = table.getValueAt(row, 0).toString();

			// Llamar al controlador con el código
			if(!cont.checkCruiseInWorker(codCruise))
			{
				if(!cont.checkCruiseInBook(codCruise))
				{
					if (cont.deleteCruise(codCruise)) {
						cargarTabla();

						JOptionPane.showMessageDialog(this, "Cruise has been deleted.");
					} else {
						JOptionPane.showMessageDialog(this, "Cruise not deleted.");
					}
				}else {
					JOptionPane.showMessageDialog(this, "Cruise not deleted because exists in Book");
				}
			}else {
				JOptionPane.showMessageDialog(this, "Cruise not deleted because exists in Worker");
			}
			
		}else if(e.getSource()==btnMODIFY) {
			if(row == -1) {
				JOptionPane.showMessageDialog(this, "Select a cruise to modify");
			}else {
				type="Modify";
				CruiseFormWindow addModCruise=new CruiseFormWindow(this,true,cont,type);
				addModCruise.setVisible(true);
			}
		}else if(e.getSource()==btnADD) {
			type="Add";
			FormCruiseWindow formCruiseWindow=new FormCruiseWindow(this, cont, null, true);
			formCruiseWindow.setVisible(true);
			cargarTabla();
			
		}
	}
	
	private void cargarTabla() {
		List<Cruise> cruises = cont.getAllCruise();
		// Crear modelo de tabla no editable
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Code", "Type", "Name", "Rooms", "Capacity" },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//Esto es para que la tabla no sea editable
		};

		// Rellenar tabla con los cruceros
		for (Cruise cruise : cruises) {
			modelo.addRow(new Object[] { cruise.getCodCruise(), cruise.getTypeCruise().toString(),
					cruise.getNameCruise(), cruise.getNumRooms(), cruise.getCapacityMax() });
		}
		table = new JTable(modelo);
	}
}
