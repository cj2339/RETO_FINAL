package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;

import controller.LoginController;
import model.Book;
import model.Client;
import model.Cruise;

/**
 * Dialog that shows a form to create or modify a booking. Validates required
 * fields and date order, then delegates creation/update to the LoginController.
 */
public class FormBookWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel panel = new JPanel();

    private JComboBox<Client> comboClient;
    private JComboBox<Cruise> comboCruise;
    private JTextField txtOrigin;
    private JTextField txtDestination;
    private JTextField txtRoom;

    private JSpinner spStartDate;
    private JSpinner spEndDate;

    private JButton okButton;
    private JButton cancelButton;

    private boolean isInsert;
    private LoginController controller;
    private Book oldBooking;
    private ListBookWindow parent;

    public FormBookWindow(JDialog parentWindow, LoginController controller, Book booking, boolean isInsert) {
        super(parentWindow, true);
        setBounds(100, 100, 500, 380);
        getContentPane().setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        this.isInsert = isInsert;
        this.controller = controller;
        this.oldBooking = booking;
        this.parent = (ListBookWindow) parentWindow;

        JLabel lblClient = new JLabel("Client ID:");
        lblClient.setBounds(40, 40, 120, 20);
        panel.add(lblClient);

        comboClient = new JComboBox<>();
        comboClient.setBounds(200, 40, 200, 22);
        panel.add(comboClient);

        JLabel lblCruise = new JLabel("Cruise Code:");
        lblCruise.setBounds(40, 80, 120, 20);
        panel.add(lblCruise);

        comboCruise = new JComboBox<>();
        comboCruise.setBounds(200, 80, 200, 22);
        panel.add(comboCruise);

        JLabel lblOrigin = new JLabel("Origin City:");
        lblOrigin.setBounds(40, 120, 120, 20);
        panel.add(lblOrigin);

        txtOrigin = new JTextField();
        txtOrigin.setBounds(200, 120, 200, 22);
        panel.add(txtOrigin);

        JLabel lblDestination = new JLabel("Destination City:");
        lblDestination.setBounds(40, 160, 120, 20);
        panel.add(lblDestination);

        txtDestination = new JTextField();
        txtDestination.setBounds(200, 160, 200, 22);
        panel.add(txtDestination);

        JLabel lblStart = new JLabel("Start Date:");
        lblStart.setBounds(40, 200, 120, 20);
        panel.add(lblStart);

        spStartDate = new JSpinner(new SpinnerDateModel());
        spStartDate.setBounds(200, 200, 200, 22);
        panel.add(spStartDate);

        JLabel lblEnd = new JLabel("End Date:");
        lblEnd.setBounds(40, 240, 120, 20);
        panel.add(lblEnd);

        spEndDate = new JSpinner(new SpinnerDateModel());
        spEndDate.setBounds(200, 240, 200, 22);
        panel.add(spEndDate);

        JLabel lblRoom = new JLabel("Room Number:");
        lblRoom.setBounds(40, 280, 120, 20);
        panel.add(lblRoom);

        txtRoom = new JTextField();
        txtRoom.setBounds(200, 280, 200, 22);
        panel.add(txtRoom);

        loadClients();
        loadCruises();

        if (!isInsert && oldBooking != null) {
            selectClient(oldBooking.getIdClient());
            selectCruise(oldBooking.getCodCruise());
            txtOrigin.setText(oldBooking.getOriginCity());
            txtDestination.setText(oldBooking.getDestinationCity());
            spStartDate.setValue(oldBooking.getStartDate());
            spEndDate.setValue(oldBooking.getEndDate());
            txtRoom.setText(String.valueOf(oldBooking.getRoomNumber()));

            comboClient.setEnabled(false);
            comboCruise.setEnabled(false);
            spStartDate.setEnabled(false);
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(this);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);
        buttonPane.add(cancelButton);
    }

    private void loadClients() {
        List<Client> clients = controller.getAllClient();
        for (Client c : clients) comboClient.addItem(c);
    }

    private void loadCruises() {
        List<Cruise> cruises = controller.getAllCruise();
        for (Cruise c : cruises) comboCruise.addItem(c);
    }

    private void selectClient(String id) {
        for (int i = 0; i < comboClient.getItemCount(); i++) {
            if (comboClient.getItemAt(i).getIdClient().equals(id)) {
                comboClient.setSelectedIndex(i);
                break;
            }
        }
    }

    private void selectCruise(int code) {
        for (int i = 0; i < comboCruise.getItemCount(); i++) {
            if (comboCruise.getItemAt(i).getCodCruise() == code) {
                comboCruise.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == okButton) {

            if (txtOrigin.getText().trim().isEmpty() ||
                txtDestination.getText().trim().isEmpty() ||
                txtRoom.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int roomNumber;
            try {
                roomNumber = Integer.parseInt(txtRoom.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Room number must be numeric.");
                return;
            }

            Date start = (Date) spStartDate.getValue();
            Date end = (Date) spEndDate.getValue();

            if (!start.before(end)) {
                JOptionPane.showMessageDialog(this, "Start date must be before end date.");
                return;
            }

            LocalDate startLD = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();

            if (startLD.isBefore(today.plusDays(15))) {
                JOptionPane.showMessageDialog(this, "Bookings must be made at least 15 days in advance.");
                return;
            }

            Client client = (Client) comboClient.getSelectedItem();
            Cruise cruise = (Cruise) comboCruise.getSelectedItem();

            Book newB = new Book(
                    client.getIdClient(),
                    cruise.getCodCruise(),
                    txtOrigin.getText().trim(),
                    txtDestination.getText().trim(),
                    start,
                    end,
                    0.0,
                    0.0,
                    roomNumber
            );

            if (isInsert) {

                String msg = controller.createBooking(newB);

                JOptionPane.showMessageDialog(
                        this,
                        msg,
                        msg.startsWith("Booking successfully") ? "Success" : "Error",
                        msg.startsWith("Booking successfully") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                );

                if (msg.startsWith("Booking successfully")) {
                    parent.refreshModel();
                    this.dispose();
                }

            } else {

                boolean updated = controller.updateBooking(oldBooking, newB);

                if (updated) {
                    JOptionPane.showMessageDialog(this, "Booking updated successfully.");
                    parent.refreshModel();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating booking.");
                }
            }
        }

        if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }
}
