
/**
 * @file InsertRamirez.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertRamirez")
public class InsertRamirez extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertRamirez() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String business = request.getParameter("business");
      String address = request.getParameter("address");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");
      String description = request.getParameter("description");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableRamirezTEF2023 (id, business, address, phone, email, description) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionRamirez.getDBConnectionRamirez();
         connection = DBConnectionRamirez.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, business);
         preparedStmt.setString(2, address);
         preparedStmt.setString(3, phone);
         preparedStmt.setString(4, email);
         preparedStmt.setString(5, description);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Business to Database Table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Business Name</b>: " + business + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "  <li><b>Phone</b>: " + phone + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Description (100c)</b>: " + description + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-te-fall2023-Ramirez/SearchRamirez.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
