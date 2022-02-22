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
      // Print everything before the right column
      PrintBefore(out);

      try (
         //Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         //Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Print the start of the right column
         out.println("<div id='rightcolumn'>");
         out.println("<div class='content'> ");
            out.println("<h2>WELCOME TO OUR SHOP</h2>");
            out.println("<p id='explaintxt'>The first Friday night each month at JavaJam is a special night.");
            out.println("Join us from 8pm to 11pm for some music you won't want to miss.</p>");
         out.println("</div>");
         out.println("<div id='righttxt'>");
            out.println("<table>");

         //Execute a SQL SELECT query
         String sqlStr = "SELECT * FROM products";
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Print the cards (middle of the right column)
         int count = 0;
         while(rset.next()) {
            String image_src = rset.getString("image_src");
            String brand = rset.getString("brand");
            String description = rset.getString("description");
            float price = rset.getFloat("price");
            if (count % 4 == 0) // print <tr>
            {
               out.println("<tr>");
            }
            // Print a card <p>...</p> for each record
            PrintCards(out, image_src, brand, description, price);
            if (count % 4 == 3) // print <tr/>
            {
               out.println("</tr>");
            }
            count++;
         }
         // Print the end of the right column
         out.println("</table>");
         out.println("</div>");
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      } 
 
      // Print everything after the right column
      PrintAfter(out);
      out.close();
   }

   // The new doPost() runs the doGet() too
   @Override
   public void doPost (HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);  // Re-direct POST request to doGet()
   }

   private void PrintBefore(PrintWriter out)
   {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Yet Another E-shop</title>");
      out.println("<meta charset='utf-8' />");
      out.println("<link rel='stylesheet' href='color.css'>");
      out.println("<link rel='stylesheet' href='ecommerce.css'>");
      out.println("</head>");
      out.println("<body>");
      out.println("<div id='wrapper'>");
      out.println("<header>");
         out.println("<h1><img src='image/wrapper.jpg' width='100%' height='150' alt='Our New E-shop'></h1>");
      out.println("</header>");
      out.println("<div id='leftcolumn'>");
         out.println("<nav>");
         out.println("<form method='post' action='ecommercequery'>");
            out.println("<ul>");
            out.println("<b>ACRYLIC</b>");
            out.println("<li><input type='radio' name='acrylic' value='AcrylicInk' />ink</li>  ");
            out.println("<b>OIL</b>  ");
            out.println("<li><input type='radio' name='oil' value='OilPaint' />paint</li>   ");
            out.println("<b>WATERCOLOR</b>    ");
            out.println("<li><input type='radio' name='watercolor' value='WatercolorInk' />ink</li>");
            out.println("<b>Brand</b>    ");
            out.println("<li><input type='radio' name='brand' value='HOLBEIN' />HOLBEIN</li>");
            out.println("<li><input type='radio' name='brand' value='SCHMINCKE' />SCHMINCKE</li>");
            out.println("<li><input type='submit' value='Search' /></li>");
            out.println("<li><input type='reset' /></li>");
            out.println("</ul>");
         out.println("</form>");
         out.println("</nav>	");
      out.println("</div>");
   }

   private void PrintCards(PrintWriter out, String image_src, String brand, String description, float price)
   {
      out.println("<td>");
      out.println("<div class='card'>");
      out.println("<img src='" + image_src + "'>");
      out.println("<div class='container'>");
      out.println("<h4><b>" + brand + "</b></h4> ");
      out.println("<p id='explaintxt'>" + description + "</p> ");
      out.println("<p id='explaintxt'><strong>SGD " + price + "<strong></p> ");
      out.println("</div>");
      out.println("</div>");
      out.println("</td>");
   }

   private void PrintAfter(PrintWriter out)
   {
      out.println("<div></body></html>");
   }
}