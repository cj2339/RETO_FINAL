package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.LoginController;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class DeleteWindow extends JDialog implements ActionListener {
   private static final long serialVersionUID = 1L;
   private JComboBox<String> comboBoxClient;
   private JLabel lblClient;
   private JComboBox<String> comboBox;
   private JButton btnDelete;
   private JButton btnCancelar;
   private LoginController cont;
   private String tipo;
   Container contentPanel;
   public DeleteWindow(JDialog parent, LoginController cont, String tipo) {
       super(parent, "Delete " + tipo, true);
       this.cont = cont;
       this.tipo = tipo;
       setTitle("Eliminate " + tipo);
       setBounds(100, 100, 450, 300);
       getContentPane().setLayout(null);
       JLabel lblTitulo = new JLabel("Delete Window");
       lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
       lblTitulo.setBounds(150, 10, 149, 24);
       getContentPane().add(lblTitulo);
       JLabel lblCodigo = new JLabel("Select code:");
       lblCodigo.setFont(new Font("Arial", Font.BOLD, 14));
       lblCodigo.setBounds(67, 65, 120, 20);
       getContentPane().add(lblCodigo);
       comboBox = new JComboBox<String>();
       comboBox.setBounds(197, 66, 169, 21);
       getContentPane().add(comboBox);
      
       lblClient = new JLabel("Select client:");
       lblClient.setFont(new Font("Arial", Font.BOLD, 14));
       lblClient.setBounds(67, 113, 120, 21);
       getContentPane().add(lblClient);
       comboBoxClient = new JComboBox<String>();
       comboBoxClient.setBounds(197, 114, 169, 21);
       getContentPane().add(comboBoxClient);
      
       btnDelete = new JButton("Delete");
       btnDelete.setBounds(100, 190, 114, 34);
       btnDelete.addActionListener(this);
       getContentPane().add(btnDelete);
       btnCancelar = new JButton("Cancel");
       btnCancelar.setBounds(230, 190, 114, 34);
       btnCancelar.addActionListener(this);
       getContentPane().add(btnCancelar);
       cargarCombo();
   }
   private void cargarCombo() {
       ArrayList<String> codes = new ArrayList<>();
       switch (tipo) {
           case "cruise":
           	codes = cont.getCruiseCodes();
           	break;
           case "worker":
           	codes = cont.getWorkerCodes();
           	break;
           case "client":
           	codes = cont.getClientCodes();
           	break;
           case "book":
               codes = cont.getCruiseCodes();
               for (String code : cont.getClientCodes()) {
                   comboBoxClient.addItem(code);
               }
               break;
       }
       for (String code : codes) {
           comboBox.addItem(code);
       }
   }
   public void actionPerformed(ActionEvent e) {
       if (e.getSource().equals(btnCancelar)) {
           dispose();
       }
       if (e.getSource().equals(btnDelete)) {
           String id = (String) comboBox.getSelectedItem();
           int confirmar = JOptionPane.showConfirmDialog(this,
                   "Are you sure you want to delete " + id + "?", "Confirm",
                   JOptionPane.YES_NO_OPTION);
           if (confirmar == JOptionPane.YES_OPTION) {
           	boolean ok = false;
               switch (tipo) {
                   case "cruise":
                	   cont.deleteCruise(id);
                	   break;
                   case "worker":
                	   cont.deleteWorker(id); 
                	   break;
                   case "client":
                	   cont.deleteClient(id);
                	   break;
                   case "book":
                	   cont.deleteBook(id, (String) comboBoxClient.getSelectedItem());
                	   comboBoxClient.removeItem(comboBoxClient.getSelectedItem());
                	   break;
               }
               JOptionPane.showMessageDialog(this, id + " deleted successfully");
               comboBox.removeItem(id);
           }
       }
   }
}
