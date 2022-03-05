import java.io.*;
import java.sql.*;
import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ecommercequery")
public class SearchQueryServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      // this function is used many times. It is used to insert a piece of html code.
      request.getRequestDispatcher("ecommerce-header.html").include(request, response);
      // print login links in between, depending on where user is logged in
      HttpSession session=request.getSession(false);  
      if(session==null){  // user has not logged in
         out.println("<div id='headnevigation'>");
         out.println("<a href='login.html'>Login</a>");
         out.println("<a href='signup.html'>Sign Up</a>");
         out.println("<a href='seecart'>Cart</a>");
         //out.println("<hr> ");
         out.println("</div>");
      }  
      else // user has logged in
      {
         out.println("<div id='headnevigationafterlogin'>");
         out.println("<p>Welcome, " + session.getAttribute("name"));
         out.println("<a href='logout'>Log Out</a>");
         out.println("<a href='seecart'>Cart</a>");
         out.println("</p>");
         //out.println("<hr> ");
         out.println("</div>");
      }
      // this function is used many times. It is used to insert a piece of html code.
      request.getRequestDispatcher("ecommerce-after-header-before-cards.html").include(request, response);

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");

         Statement stmt = conn.createStatement();
      ) {
         String sqlStr = "SELECT * FROM products";

         // add conditions for category
         String categoryParam = request.getParameter("category");
         if (categoryParam != null)
         { 
            String category = categoryParam.split("-", 2)[0];
            String type = categoryParam.split("-", 2)[1];
            sqlStr += " WHERE category = '" + category +  "'" +
               "AND type = '" + type + "'";
         }

         // add conditions for brand
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

         // Print the results (in card form)
         int count = 0;
         while(rset.next()) {
            int product_id = rset.getInt("id");
            String image_src = rset.getString("image_src");
            String brand = rset.getString("brand");
            String description = rset.getString("description");
            float price = rset.getFloat("price");
            if (count % 4 == 0) // 4 products in a row
            {
               out.println("<tr>");
            }
            // Print a card <p>...</p> for each record
            PrintCard(out, product_id, image_src, brand, description, price);
            if (count % 4 == 3) // 4 products in a row
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
 
      // this function is used many times. It is used to insert a piece of html code.
      request.getRequestDispatcher("ecommerce-after-cards.html").include(request, response);
      out.close();
   }

   @Override
   public void doPost (HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);
   }

   // the function used to print one product
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