package export;

import controller.LoginController;
import model.Cruise;
import model.Worker;
import model.Book;
import model.Client;

import java.io.PrintWriter;

/**
 * Utility class responsible for exporting all cruise management data
 * (cruises, workers, clients and bookings) into a structured XML file.
 * Retrieves the information through the LoginController and generates
 * the final XML document following the project's schema.
 *
 * @author Iker
 */
public class XMLExporter {

	private LoginController cont;

	public XMLExporter(LoginController cont) {
		this.cont = cont;
	}

	public void exportToXML() {
		try {
			PrintWriter pw = new PrintWriter("data/dataExported.xml");

			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			pw.println("<cruiseManagement");
			pw.println("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			pw.println("    xsi:noNamespaceSchemaLocation=\"cruceros.xsd\">");
			// CRUISES
			pw.println("  <cruises>");
			for (Cruise c : cont.getAllCruise()) {
				pw.println("    <cruise code=\"" + c.getCodCruise() + "\" type=\"" + c.getTypeCruise() + "\" status=\"active\">");
				pw.println("      <name>" + c.getNameCruise() + "</name>");
				pw.println("      <rooms>" + c.getNumRooms() + "</rooms>");
				pw.println("      <capacity>" + c.getCapacityMax() + "</capacity>");
				pw.println("      <photo>images/" + c.getCodCruise() + ".png</photo>");
				pw.println("    </cruise>");
			}
			pw.println("  </cruises>");


			// WORKERS
			pw.println("<workers>");
			for (Worker w : cont.getAllWorker()) {
				pw.println("    <worker id=\"" + w.getIdWorker() + "\" contract=\"fulltime\">");
				pw.println("        <name>" + w.getName() + "</name>");
				pw.println("        <surname>" + w.getSurname() + "</surname>");
				pw.println("        <service>" + w.getService() + "</service>");
				pw.println("        <age>" + w.getAge() + "</age>");
				// Languages
				pw.println("        <languages>");
				pw.println("            <spanish>" + w.isSpanish() + "</spanish>");
				pw.println("            <english>" + w.isEnglish() + "</english>");
				pw.println("        </languages>");
				// Contact
				pw.println("        <contact>");
				pw.println("            <email>" + w.getEmail() + "</email>");
				pw.println("            <phone>" + w.getPhoneNumber() + "</phone>");
				pw.println("        </contact>");
				// Assigned cruise (if exists)
				if (w.getCruise() != null) {
					pw.println("        <cruiseAssigned>" + w.getCruise().getCodCruise() + "</cruiseAssigned>");
				} else {
					pw.println("        <cruiseAssigned>none</cruiseAssigned>");
				}
				pw.println("    </worker>");
			}
			pw.println("</workers>");


			// CLIENTS
			pw.println("  <clients>");
			for (Client c : cont.getAllClient()) {
				pw.println("    <client id=\"" + c.getIdClient() + "\">");
				pw.println("        <name>" + c.getNameClient() + "</name>");
				pw.println("        <surname>" + c.getSurnameClient() + "</surname>");
				pw.println("        <age>" + c.getAgeClient() + "</age>");
				pw.println("        <email>" + c.getEmailClient() + "</email>");
				pw.println("        <phone>" + c.getPhoneClient() + "</phone>");
				pw.println("    </client>");
			}
			pw.println("  </clients>");


			// BOOKINGS
			pw.println("  <bookings>");
			for (Book b : cont.getAllBookings()) {
				pw.println("    <booking>");
				pw.println("      <cruise>" + b.getCodCruise() + "</cruise>");
				pw.println("      <client>" + b.getIdClient() + "</client>");
				pw.println("      <originCity>" + b.getOriginCity() + "</originCity>");
				pw.println("      <destinationCity>" + b.getDestinationCity() + "</destinationCity>");
				pw.println("      <startDate>" + b.getStartDate() + "</startDate>");
				pw.println("      <endDate>" + b.getEndDate() + "</endDate>");
				pw.println("      <room>" + b.getRoomNumber() + "</room>");
				pw.println("      <finalPrice>" + b.getFinalPrice() + "</finalPrice>");
				pw.println("    </booking>");
			}
			pw.println("  </bookings>");

			pw.println("</cruiseManagement>");
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}