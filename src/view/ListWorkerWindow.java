package view;

import java.awt.Toolkit;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Language;
import model.TypeWorker;
import model.Worker;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListWorkerWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	JTable table;

	public ListWorkerWindow(MainWindow mainWindow, LoginController cont) {
		super(mainWindow, true);
		this.cont = cont;

		setTitle("Workers");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		fillTable();
		
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 44, 964, 283);
		getContentPane().add(scrollPane);

		btnADD = new JButton("ADD");
		btnADD.setBounds(772, 348, 97, 25);
		btnADD.addActionListener(this);
		contentPane.add(btnADD);

		btnDELETE = new JButton("DELETE");
		btnDELETE.setBounds(665, 348, 97, 25);
		btnDELETE.addActionListener(this);
		contentPane.add(btnDELETE);

		btnMODIFY = new JButton("MODIFY");
		btnMODIFY.addActionListener(this);

		btnMODIFY.setBounds(879, 348, 97, 25);
		contentPane.add(btnMODIFY);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();
		int confirm;
		boolean selected=true;

		if (e.getSource() == btnDELETE&&selected) {
			if (row == -1) {
				selected=false;
			}
			
			if(selected) {
				confirm=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this worker?", "Warning" , JOptionPane.YES_NO_OPTION);
				if(confirm==JOptionPane.YES_OPTION) {
					// Llamar al controlador con el código
					DefaultTableModel modelo = (DefaultTableModel) table.getModel();
					modelo.removeRow(row);
					refreshModel();
					JOptionPane.showMessageDialog(this, "Worker has been deleted.");
				}
				
			}else {
				JOptionPane.showMessageDialog(this, "Select a worker to delete.");
			}

		}else if(e.getSource()==btnMODIFY) {
			Worker worker=new Worker();
			int viewRow=table.getSelectedRow();
			if(viewRow == -1) {
				JOptionPane.showMessageDialog(this, "Select a worker to modify");
			}else {
				int modelRow = table.convertRowIndexToModel(viewRow);
				TableModel model = table.getModel();
				worker.setIdWorker((String) model.getValueAt(modelRow, 0));
				worker.setService(TypeWorker.valueOf((String)model.getValueAt(modelRow,1)));
				worker.setName((String) model.getValueAt(modelRow, 2));
				worker.setSurname((String) model.getValueAt(modelRow, 3));
				worker.setHiringDate((Date) model.getValueAt(modelRow, 4));
				worker.setPhoneNumber((String) model.getValueAt(modelRow, 5));
				worker.setEmail((String) model.getValueAt(modelRow, 6));
				worker.setAge((int)model.getValueAt(modelRow, 7));
				worker.setLanguage(Language.valueOf((String)model.getValueAt(modelRow,8)));
				worker.setCodCruise((Integer)model.getValueAt(modelRow,9));
				
				FormWorkerWindow workerFormWindow=new FormWorkerWindow(this, cont, worker, true);
				workerFormWindow.setVisible(true);
				refreshModel();
			}
		}else if(e.getSource()==btnADD) {
			FormWorkerWindow workerFormWindow=new FormWorkerWindow(this, cont, null, true);
			workerFormWindow.setVisible(true);
			refreshModel();
		}

	}
	
	private void fillTable() {
		List<Worker> workers=cont.getAllWorker();

		// Crear modelo de tabla no editable
		DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Service", "Name", "Surname", "Hiring date", "Phone number", "Email", "Age", "Language", "Cruise Code" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//Esto es para que la tabla no sea editable
		};
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		refreshModel();
	}
	
	public void refreshModel() {
		// Rellenar tabla con los usuarios
		List<Worker> workers = cont.getAllWorker();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Worker worker: workers) {
			model.addRow(new Object[] {
					worker.getIdWorker(), 
					worker.getService().toString(), 
					worker.getName(), 
					worker.getSurname(), 
					worker.getHiringDate(),
					worker.getPhoneNumber(),
					worker.getEmail(),
					worker.getAge(),
					worker.getLanguage().toString(),
					worker.getCodCruise()
			}); 
		}
	}
}
