package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.Worker;

public class WorkerFormWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private Worker worker;

	public WorkerFormWindow(WorkerListWindow workerListWindow, LoginController cont, Worker worker, boolean b) {
		super(workerListWindow, true);
		this.cont=cont;
		
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

}
