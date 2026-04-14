package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationBook implements BookDAO {

    private Connection con;
    private PreparedStatement stmt;

    private ResourceBundle configFile;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    final String SQL_GET_ALL_BOOKINGS = "SELECT * FROM book";
    final String SQL_DELETE_BOOKING = "DELETE FROM book WHERE id_client = ? AND cod_cruise = ? AND startDate = ?";
 // The INSERT is performed via a procedure --> there is no SQL_INSERT_BOOK

    public DBImplementationBook() {
        this.configFile = ResourceBundle.getBundle("configClass");
        this.urlDB = this.configFile.getString("Conn");
        this.userDB = this.configFile.getString("DBUser");
        this.passwordDB = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlDB, userDB, passwordDB);
        } catch (SQLException e) {
            System.out.println("Error opening the database: " + e.getMessage());
        }
    }

    @Override
    public boolean createBooking(Book b) {
        boolean ok = false;
        openConnection();
        try {
            CallableStatement cs = con.prepareCall("{CALL createBooking(?,?,?,?,?,?,?,?)}");
            cs.setString(1, b.getIdClient());
            cs.setInt(2, b.getCodCruise());
            cs.setString(3, b.getOriginCity());
            cs.setString(4, b.getDestinationCity());
            cs.setDate(5, new java.sql.Date(b.getStartDate().getTime()));
            cs.setDate(6, new java.sql.Date(b.getEndDate().getTime()));
            cs.setDouble(7, b.getBasePrice());
            cs.setDouble(8, b.getFinalPrice());
            ok = cs.executeUpdate() >= 0; // executeUpdate returns 0 in procedures
            cs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
        return ok;
    }

    @Override
    public boolean deleteBooking(String idClient, int codCruise, Date startDate) {
        boolean ok = false;
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_DELETE_BOOKING);
            stmt.setString(1, idClient);
            stmt.setInt(2, codCruise);
            stmt.setDate(3, new java.sql.Date(startDate.getTime()));
            ok = stmt.executeUpdate() > 0;
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
        return ok;
    }

    @Override
    public List<Book> getAllBookings() {
        List<Book> list = new ArrayList<>();
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_GET_ALL_BOOKINGS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                    rs.getString("id_client"),
                    rs.getInt("cod_cruise"),
                    rs.getString("originCity"),
                    rs.getString("destinationCity"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getDouble("basePrice"),
                    rs.getDouble("finalPrice")
                ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean updateBooking(Book oldB, Book newB) {
        boolean ok = true;
        // 1. Try to delete the old booking
        boolean deleted = deleteBooking(
            oldB.getIdClient(),
            oldB.getCodCruise(),
            oldB.getStartDate()
        );
        if (!deleted) {
            ok = false;
        }
        // 2. If it has been deleted, create a new one
        if (ok) {
            boolean created = createBooking(newB);
            if (!created) {
                ok = false;
            }
        }
        return ok;
    }
}