package view;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.TypeWorker;
import model.Worker;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WorkerListWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD;
	private JButton btnDELETE;
	private JButton btnMODIFY;
	JTable table;

	public WorkerListWindow(MainWindow mainWindow, LoginController cont) {
		super(mainWindow, true);
		this.cont = cont;

		setTitle("Workers");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		fillTable();
		
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
				worker.setIdWorker((String) model.getValueAt(modelRow,  0));
				worker.setService(TypeWorker.valueOf((String)model.getValueAt(modelRow,1)));
				worker.setName((String) model.getValueAt(modelRow, 2));
				worker.setSurname((String) model.getValueAt(modelRow, 3));
				worker.setCodCruise((Integer)model.getValueAt(modelRow,4));
				
				WorkerFormWindow workerFormWindow=new WorkerFormWindow(this, cont, worker, true);
				//WorkerFormWindow.setVisible(true);
			}
		}else if(e.getSource()==btnADD) {
			WorkerFormWindow workerFormWindow=new WorkerFormWindow(this, cont, null, true);
			//WorkerFormWindow.setVisible(true);
		}

	}
	
	private void fillTable() {
		List<Worker> workers=cont.getAllWorker();

		// Crear modelo de tabla no editable
		DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Service", "Name", "Surname", "Cruise Code" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//Esto es para que la tabla no sea editable
		};

		// Rellenar tabla con los usuarios

		for (Worker worker: workers) {
			model.addRow(new Object[] {
					worker.getIdWorker(), 
					worker.getService().toString(), 
					worker.getName(), 
					worker.getSurname(), 
					worker.getCodCruise()
			});
		}
		table = new JTable(model);
	}
}
