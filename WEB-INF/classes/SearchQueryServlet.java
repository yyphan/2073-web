import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ecommercequery")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SearchQueryServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      request.getRequestDispatcher("ecommerce-header.html").include(request, response);
      // print login links in between
      HttpSession session=request.getSession(false);  
      if(session==null){  
         out.println("<div id='wrapper'>");
         out.println("<a href='login.html'>Login</a> |  ");
         out.println("<a href='signup.html'>Sign Up</a> |  ");
         out.println("<a href='seecart'>Cart</a>  ");
         out.println("<hr> ");
         out.println("</div>");
      }  
      else
      {
         out.println("<div id='wrapper'>");
         out.println("<p>Welcome, " + session.getAttribute("name") + "</p>");
         out.println("<a href='logout'>Log Out</a> |  ");
         out.println("<a href='seecart'>Cart</a>  ");
         out.println("<hr> ");
         out.println("</div>");
      }
      request.getRequestDispatcher("ecommerce-after-header-before-cards.html").include(request, response);

      try (
         //Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         //Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         //Execute a SQL SELECT query
         String sqlStr = "SELECT * FROM products";

         String categoryParam = request.getParameter("category");
         if (categoryParam != null)
         { 
            String category = categoryParam.split("-", 2)[0];
            String type = categoryParam.split("-", 2)[1];
            sqlStr += " WHERE category = '" + category +  "'" +
               "AND type = '" + type + "'";
         }

         String brandParam = request.getParameter("brand");
         if (brandParam != null)
         {
            if (categoryParam == null) 
            {
               sqlStr += " WHERE";
            }
            else 
            {
               sqlStr += " AND";
            }

            sqlStr += " brand = '" + brandParam + "'";
         }
         
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Print the cards (middle of the right column)
         int count = 0;
         while(rset.next()) {
            int product_id = rset.getInt("id");
            String image_src = rset.getString("image_src");
            String brand = rset.getString("brand");
            String description = rset.getString("description");
            float price = rset.getFloat("price");
            if (count % 4 == 0) // print <tr>
            {
               out.println("<tr>");
            }
            // Print a card <p>...</p> for each record
            PrintCard(out, product_id, image_src, brand, description, price);
            if (count % 4 == 3) // print <tr/>
            {
               out.println("</tr>");
            }
            count++;
         }
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      } 
 
      request.getRequestDispatcher("ecommerce-after-cards.html").include(request, response);
      out.close();
   }

   // The new doPost() runs the doGet() too
   @Override
   public void doPost (HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);  // Re-direct POST request to doGet()
   }
   private void PrintCard(PrintWriter out, int product_id, String image_src, String brand, String description, float price)
   {
      out.println("<td>");
      out.println("<div class='card'>");
      out.println("<img src='" + image_src + "'>");
      out.println("<div class='container'>");
      out.println("<h4><b>" + brand + "</b></h4> ");
      out.println("<p id='explaintxt'>" + description + "</p> ");
      out.println("<p id='explaintxt'><strong>SGD " + price + "<strong></p> ");
      out.println("<input type='button' class='add-to-cart' id='" + product_id + "' value='Add to Cart'>");
      out.println("</div>");
      out.println("</div>");
      out.println("</td>");
   }
}