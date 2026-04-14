package export;

import controller.LoginController;
import model.Cruise;
import model.Worker;
import model.Client;
//import model.Booking;

import java.io.PrintWriter;
//import java.util.List;

public class XMLExporter {

    private LoginController cont;

    public XMLExporter(LoginController cont) {
        this.cont = cont;
    }

    public void exportToXML() {
        try {
            PrintWriter pw = new PrintWriter("dataExported.xml");

            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<cruiseManagement>");

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
                pw.println("        <language>" + w.getLanguage() + "</language>");
                pw.println("        <contact>");
                pw.println("            <email>" + w.getEmail() + "</email>");
                pw.println("            <phone>" + w.getPhoneNumber() + "</phone>");
                pw.println("        </contact>");
                pw.println("        <cruiseAssigned>" + w.getCodCruise() + "</cruiseAssigned>");
                pw.println("    </worker>");
            }
            pw.println("</workers>");

         // CLIENTS
            pw.println("<clients>");
            for (Client c : cont.getAllClient()) {
                pw.println("    <client id=\"" + c.getIdClient() + "\" vip=\"no\">");
                pw.println("        <name>" + c.getNameClient() + "</name>");
                pw.println("        <surname>" + c.getSurnameClient() + "</surname>");
                pw.println("        <age>" + c.getAgeClient() + "</age>");
                pw.println("        <photo>images/" + c.getIdClient() + ".png</photo>");
                pw.println("    </client>");
            }
            pw.println("</clients>");

            // BOOKINGS
//            pw.println("  <bookings>");
//            for (Booking b : cont.getAllBookings()) {
//                pw.println("    <booking>");
//                pw.println("      <cruise>" + b.getCruiseCode() + "</cruise>");
//                pw.println("      <client>" + b.getClientId() + "</client>");
//                pw.println("      <date>" + b.getDate() + "</date>");
//                pw.println("    </booking>");
//            }
//            pw.println("  </bookings>");

            pw.println("</cruiseManagement>");
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}