package view;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Administrator;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminListWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	JTable table;

	public AdminListWindow(JFrame mainWindow, LoginController cont) {
		super(mainWindow, true);
		setTitle("Administrators");
		this.cont = cont;

		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		loadTable();

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

		// Aplicar cursor de ancla a los botones
		// CustomCursor.applyToButtons(this); ESTO LO HE HECHO CON IA
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();

		if (e.getSource() == btnDELETE) {
			//creo que habría que poner una confirmación
			if (row == -1) {
				// No hay fila seleccionada
				JOptionPane.showMessageDialog(this, "Select an administrator to delete.");
				return;
			}

			// Obtener el nombre de la columna 0
			String adminName = table.getValueAt(row, 0).toString();

			// Llamar al controlador con el nombre
			if (cont.deleteAdministrator(adminName)) {
				refreshModel();
				JOptionPane.showMessageDialog(this, "Administrator has been deleted.");
			} else {
				JOptionPane.showMessageDialog(this, "Administrator not deleted.");
			}

		}

		if (e.getSource() == btnMODIFY) {

			Administrator admin = new Administrator();
			int viewRow = table.getSelectedRow();
			if (viewRow != -1) {
				int modelRow = table.convertRowIndexToModel(viewRow);
				TableModel model = table.getModel();
				String adminName = (String) model.getValueAt(modelRow, 0);
			    // Abrir la ventana de cambiar contraseña
				ChangePasswordAdminWindow adminWindow = new ChangePasswordAdminWindow(this, cont, adminName);
		        adminWindow.setVisible(true);

				refreshModel();
			} else {
				JOptionPane.showMessageDialog(this, "Select an administrator to modify");
			}
		}
		if (e.getSource() == btnADD) {
			FormAdminWindow formAdminWindow = new FormAdminWindow(this, cont, null, true);
			formAdminWindow.setVisible(true);
			refreshModel();

		}
	}

	private void loadTable() {
		// Crear modelo de tabla no editable
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Name", "Password" },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// Esto es para que la tabla no sea editable
		};
		table = new JTable(modelo);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// Permitir seleccionar solo una fila
		refreshModel();
	}

	public void refreshModel() {
	    List<Administrator> administrators = cont.getAllAdministrators();// Obtener la lista actualizada de administradores
	    DefaultTableModel modelo = (DefaultTableModel) table.getModel();// Obtener el modelo de la tabla
	    //Limpiar tabla
	    modelo.setRowCount(0);
	    //Rellenar tabla
	    for (Administrator admin : administrators) {
	        modelo.addRow(new Object[] {admin.getName(),admin.getPassword()});
	    }
	}
}