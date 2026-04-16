package model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TestDBImplementationCruise {

	private DBImplementationCruise dao;

	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockStatement;
	@Mock
	private ResultSet mockResultSet;

	private AutoCloseable closeable;
	private MockedStatic<DriverManager> mockedDriverManager;

	@BeforeEach
	void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);

		// Setup DBImplementationCruise
		dao = new DBImplementationCruise();

		mockedDriverManager = mockStatic(DriverManager.class);
		mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
				.thenReturn(mockConnection);
	}

	@AfterEach
	void tearDown() throws Exception {
		if (mockedDriverManager != null) {
			mockedDriverManager.close();
		}
		if (closeable != null) {
			closeable.close();
		}
	}

	@Test
	void testGetAllCruise() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true, true, false); // 2 elements
		when(mockResultSet.getString("type_cruise")).thenReturn("FAMILY", "LUXURY");
		when(mockResultSet.getString("cod_cruise")).thenReturn("1", "2");
		when(mockResultSet.getString("name_cruise")).thenReturn("Cruise1", "Cruise2");
		when(mockResultSet.getInt("num_rooms")).thenReturn(100, 200);
		when(mockResultSet.getInt("capacity_max")).thenReturn(500, 1000);

		List<Cruise> cruises = dao.getAllCruise();

		assertEquals(2, cruises.size());
		assertEquals("Cruise1", cruises.get(0).getNameCruise());
		assertEquals(TypeCruise.FAMILY, cruises.get(0).getTypeCruise());
		assertEquals("Cruise2", cruises.get(1).getNameCruise());

		verify(mockStatement).executeQuery();
		verify(mockResultSet).close();
		verify(mockStatement).close();
		verify(mockConnection).close();
	}

	@Test
	void testDeleteCruise() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(1); // 1 row affected

		boolean result = dao.deleteCruise("1");

		assertTrue(result);
		verify(mockStatement).setString(1, "1");
		verify(mockStatement).executeUpdate();
		verify(mockStatement).close();
		verify(mockConnection).close();
	}

	@Test
	void testDeleteCruiseFails() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(0); // 0 rows affected

		boolean result = dao.deleteCruise("1");

		assertFalse(result);
		verify(mockStatement).executeUpdate();
	}

	@Test
	void testGetCruiseByCode() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true, false);
		when(mockResultSet.getString("type_cruise")).thenReturn("PREMIUM");
		when(mockResultSet.getString("cod_cruise")).thenReturn("5");
		when(mockResultSet.getString("name_cruise")).thenReturn("Test Premium");
		when(mockResultSet.getInt("num_rooms")).thenReturn(150);
		when(mockResultSet.getInt("capacity_max")).thenReturn(600);

		Cruise cruise = dao.getCruiseByCode("5");

		assertNotNull(cruise);
		assertEquals(5, cruise.getCodCruise());
		assertEquals("Test Premium", cruise.getNameCruise());
		assertEquals(TypeCruise.PREMIUM, cruise.getTypeCruise());

		verify(mockStatement).setString(1, "5");
		verify(mockStatement).executeQuery();
	}

	@Test
	void testUpdateCruiseByCode() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(1);

		Cruise cruise = new Cruise(1, TypeCruise.EXPEDITION, "Update Test", 50, 200);

		boolean result = dao.updateCruiseByCode(cruise);

		assertTrue(result);
		verify(mockStatement).setString(1, "EXPEDITION");
		verify(mockStatement).setString(2, "Update Test");
		verify(mockStatement).setInt(3, 50);
		verify(mockStatement).setInt(4, 200);
		verify(mockStatement).setInt(5, 1);
		verify(mockStatement).executeUpdate();
	}

	@Test
	void testInsertCruise() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(1);

		Cruise cruise = new Cruise(0, TypeCruise.LUXURY, "Insert Test", 60, 250);

		boolean result = dao.insertCruise(cruise);

		assertTrue(result);
		verify(mockStatement).setString(1, "Insert Test");
		verify(mockStatement).setString(2, "LUXURY");
		verify(mockStatement).setInt(3, 60);
		verify(mockStatement).setInt(4, 250);
		verify(mockStatement).executeUpdate();
	}

	@Test
	void testCheckCruiseInWorker() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);

		boolean result = dao.checkCruiseInWorker("2");

		assertTrue(result);
		verify(mockStatement).setString(1, "2");
		verify(mockStatement).executeQuery();
	}

	@Test
	void testCheckCruiseInBook() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);

		boolean result = dao.checkCruiseInBook("3");

		assertFalse(result);
		verify(mockStatement).setString(1, "3");
		verify(mockStatement).executeQuery();
	}

	@Test
	void testGetNextCruiseCode() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(10);

		int code = dao.getNextCruiseCode();

		assertEquals(10, code);
		verify(mockStatement).executeQuery();
	}

	@Test
	void testSQLExceptionHandling() throws SQLException {
		SQLException ex = new SQLException("Mock DB Error");
		when(mockConnection.prepareStatement(anyString())).thenThrow(ex);

		boolean result = dao.insertCruise(new Cruise(0, TypeCruise.FAMILY, "Error", 10, 10));
		assertFalse(result, "Should return false on SQL error");

		if (!result) {
			assertThrows(ArithmeticException.class, () -> {
				int numero1 = 10;
				int numero2 = 0;
				int resultado = numero1 / numero2;
			});
		}
	}
}