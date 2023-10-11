import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchRamirez")
public class SearchRamirez extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchRamirez() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionRamirez.getDBConnectionRamirez();
         connection = DBConnectionRamirez.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableRamirezTEF2023";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableRamirezTEF2023 WHERE description LIKE ?";
            String desc = "%" + keyword.toLowerCase() + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, desc);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String businessName = rs.getString("business").trim();
            String address = rs.getString("address").trim();
            String phone = rs.getString("phone").trim();
            String email = rs.getString("email").trim();
            String description = rs.getString("description").trim();

            if (keyword.isEmpty() || description.toLowerCase().contains(keyword.toLowerCase())) {
               out.println("ID: " + id + ", ");
               out.println("Business Name: " + businessName + ", ");
               out.println("Business Address: " + address + ", ");
               out.println("Phone: " + phone + ", ");
               out.println("Email: " + email + ", ");
               out.println("Description: " + description + "<br>");
            }
         }
         out.println("<a href=/webproject-te-fall2023-Ramirez/SearchRamirez.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
