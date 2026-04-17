package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.LoginController;
import model.Cruise;
import model.TypeWorker;
import model.Worker;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

/**
 * Modal dialog that displays the list of workers in a table and provides
 * operations to add, modify and delete workers. It uses the provided
 * LoginController to perform CRUD operations and refresh the table model.
 * 
 * @author Etna
 */
public class ListWorkerWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JPanel contentPane;
	private JButton btnADD, btnDELETE, btnMODIFY;
	private Image backgroundImage = new ImageIcon("images/CruiseBackground.png").getImage();
	JTable table;

	/**
	 * Creates the dialog for listing and managing workers.
	 *
	 * @param mainWindow The parent main window.
	 * @param cont       The application controller containing logic.
	 */
	public ListWorkerWindow(MainWindow mainWindow, LoginController cont) {
		super(mainWindow, true);
		this.cont = cont;

		setTitle("Workers");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(734, 450);
		setLocationRelativeTo(null);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		// TITTLE
		JLabel title = new JLabel("Worker Management");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(245, 10, 249, 49);
		title.setFont(new Font("SansSerif", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		title.setBorder(new EmptyBorder(5, 5, 15, 5));
		contentPane.add(title);

		// TABLE
		loadTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 59, 698, 271);

		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		table.setOpaque(false);
		table.setBackground(new Color(255, 255, 255, 180));

		contentPane.add(scrollPane);

		// BUTTONS
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(202, 340, 319, 45);
		buttonPanel.setOpaque(false);

		btnADD = new JButton("ADD");
		btnADD.setBounds(20, 10, 88, 25);
		btnDELETE = new JButton("DELETE");
		btnDELETE.setBounds(216, 10, 88, 25);
		btnMODIFY = new JButton("MODIFY");
		btnMODIFY.setBounds(118, 10, 88, 25);

		btnADD.addActionListener(this);
		btnDELETE.addActionListener(this);
		btnMODIFY.addActionListener(this);

		btnADD.setBackground(new Color(63, 117, 243));
		btnADD.setForeground(Color.WHITE);

		btnDELETE.setBackground(new Color(63, 117, 243));
		btnDELETE.setForeground(Color.WHITE);

		btnMODIFY.setBackground(new Color(63, 117, 243));
		btnMODIFY.setForeground(Color.WHITE);
		buttonPanel.setLayout(null);

		buttonPanel.add(btnADD);
		buttonPanel.add(btnMODIFY);
		buttonPanel.add(btnDELETE);

		contentPane.add(buttonPanel);
	}

	/**
	 * Detects and responds to the interaction with the specific interface option selected.
	 *
	 * @param e The object encapsulating the user event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();
		int confirm;
		boolean selected=true;
		String idWorker;

		if (e.getSource() == btnDELETE&&selected) {
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select a worker to delete.");
			}else {
				idWorker = table.getValueAt(row, 0).toString();
				confirm=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this worker?", "Warning" , JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(confirm==JOptionPane.YES_OPTION) {
					if(cont.deleteWorker(idWorker)) {
						refreshModel();
						JOptionPane.showMessageDialog(this, "Worker has been deleted.");
					}else {
						JOptionPane.showMessageDialog(this, "Worker not deleted.");
					}

				}
			} 


		}else if(e.getSource()==btnMODIFY) {

			int viewRow=table.getSelectedRow();
			int cruiseCode, modelRow;
			String dateStr;

			if(row == -1) {
				JOptionPane.showMessageDialog(this, "Select a worker to modify");
			}else {
				Worker worker=new Worker();
				modelRow = table.convertRowIndexToModel(row);
				TableModel model = table.getModel();
				worker.setIdWorker((String) model.getValueAt(modelRow, 0));
				worker.setService(TypeWorker.valueOf((String)model.getValueAt(modelRow,1)));
				worker.setName((String) model.getValueAt(modelRow, 2));
				worker.setSurname((String) model.getValueAt(modelRow, 3));
				try {
					dateStr = model.getValueAt(modelRow, 4).toString();
					worker.setHiringDate(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr));
				}catch(Exception d) {
					System.out.println("Error date format: "+d.getMessage());
				}
				worker.setPhoneNumber((String) model.getValueAt(modelRow, 5));
				worker.setEmail((String) model.getValueAt(modelRow, 6));
				worker.setAge(Integer.parseInt(model.getValueAt(modelRow, 7).toString()));
				worker.setSpanish((Boolean)model.getValueAt(modelRow, 8));
				worker.setEnglish((Boolean)model.getValueAt(modelRow, 9));
				cruiseCode = Integer.parseInt(model.getValueAt(modelRow, 10).toString());
				Cruise tempCruise = new Cruise();
				tempCruise.setCodCruise(cruiseCode); //se crea un objeto crucero solo con el código
				worker.setCruise(tempCruise);

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

	/**
	 * Configures the data table model definition.
	 */
	private void loadTable() {
		List<Worker> workers=cont.getAllWorker();

		// Crear modelo de tabla no editable
		DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Service", "Name", "Surname", "Hiring date", "Phone number", "Email", "Age", "Spanish", "English", "Cruise Code" }, 0) {
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

	/**
	 * Connects to the database and populates the table rows with the currently existing workers.
	 */
	public void refreshModel() {
		// Rellenar tabla con los usuarios
		List<Worker> workers = cont.getAllWorker();
		SimpleDateFormat simpleDate=new SimpleDateFormat("dd/MM/yyyy");
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Worker worker: workers) {
			model.addRow(new Object[] {
					worker.getIdWorker(), 
					worker.getService().toString(), 
					worker.getName(), 
					worker.getSurname(), 
					simpleDate.format(worker.getHiringDate()),
					worker.getPhoneNumber(),
					worker.getEmail(),
					worker.getAge(),
					worker.isSpanish(),
					worker.isEnglish(),
					worker.getCruise().getCodCruise()
			}); 
		}
	}
}