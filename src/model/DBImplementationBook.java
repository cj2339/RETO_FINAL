package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationBook implements BookDAO {

    private Connection connection;
    private PreparedStatement statement;
    private ResourceBundle configFile;
    private String driverDB;//TENGO QUE PREGUNTARLE A LEIRE!!!!!
    private String urlDB;
    private String userDB;
    private String passwordDB;

    // SQL queries
    final String SQLSELECTALL ="SELECT id_client, cod_cruise, originCity, destinationCity, startDate, endDate, basePrice, finalPrice, room_number FROM book";
    final String SQLDELETE ="DELETE FROM book WHERE id_client = ? AND cod_cruise = ? AND startDate = ?";

    public DBImplementationBook() {
        this.configFile = ResourceBundle.getBundle("configClass");
        this.driverDB = this.configFile.getString("Driver");
        this.urlDB = this.configFile.getString("Conn");
        this.userDB = this.configFile.getString("DBUser");
        this.passwordDB = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
        } catch (SQLException e) {
            System.out.println("Error opening DB: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBookings() {
        List<Book> list = new ArrayList<>();
        openConnection();
        try {
            statement = connection.prepareStatement(SQLSELECTALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                    rs.getString("id_client"),
                    rs.getInt("cod_cruise"),
                    rs.getString("originCity"),
                    rs.getString("destinationCity"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getDouble("basePrice"),
                    rs.getDouble("finalPrice"),
                    rs.getInt("room_number")
                ));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean createBooking(Book b) {
        boolean ok = false;
        openConnection();

        try {
            CallableStatement cs = connection.prepareCall("{CALL p_create_booking(?,?,?,?,?,?,?,?)}");
            cs.setString(1, b.getIdClient());
            cs.setInt(2, b.getCodCruise());
            cs.setString(3, b.getOriginCity());
            cs.setString(4, b.getDestinationCity());
            cs.setDate(5, new Date(b.getStartDate().getTime()));
            cs.setDate(6, new Date(b.getEndDate().getTime()));
            cs.setInt(7, b.getRoomNumber());
            cs.registerOutParameter(8, Types.VARCHAR);
            cs.execute();
            String result = cs.getString(8);
            System.out.println("Procedure result: " + result);
            ok = (result != null && result.startsWith("Booking successfully"));
            cs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
        return ok;
    }

    @Override
    public boolean deleteBooking(String idClient, int codCruise, java.util.Date startDate) {
        boolean ok = false;
        openConnection();
        try {
            statement = connection.prepareStatement(SQLDELETE);
            statement.setString(1, idClient);
            statement.setInt(2, codCruise);
            statement.setDate(3, new Date(startDate.getTime()));
            ok = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
        return ok;
    }


    @Override
    public boolean updateBooking(Book oldBooking, Book newBooking) {
        boolean deleted = deleteBooking(
            oldBooking.getIdClient(),
            oldBooking.getCodCruise(),
            oldBooking.getStartDate()
        );
        if (!deleted) return false;
        return createBooking(newBooking);
    }
}