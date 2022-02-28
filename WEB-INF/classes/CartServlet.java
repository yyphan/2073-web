import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/seecart")
public class CartServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                      throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();
          
        HttpSession session=request.getSession(false);  
        if(session==null){  
            out.print("Please login first");  
            request.getRequestDispatcher("login.html").include(request, response); 
        }  
        else{   
            String name=(String)session.getAttribute("name"); 
            out.println("<p><a href='ecommercequery'>Home</a></p><hr>"); 
            out.print("Hello, " + name + ". Welcome to Your Cart");  

            try (
                Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "xxxx"
                ); 
                
                Statement stmt = conn.createStatement();
            ) {
                String sqlStr = "SELECT * FROM products;";
                ResultSet rset = stmt.executeQuery(sqlStr);
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Product Name</th>");
                out.println("<th>Quantity</th>");
                out.println("<th>Total Price</th>");
                out.println("</tr>");
                // loop through all products to see if any of them was added in cart
                while (rset.next()) {
                    int id = rset.getInt("id");
                    String idStr = Integer.toString(id);

                    // if the product with that id was added in cart
                    if (session.getAttribute(idStr) != null)
                    {
                        String description = rset.getString("description");
                        float singlePrice = rset.getFloat("price");
                        int count = ((Integer)session.getAttribute(idStr)).intValue();
                        float totalPrice = singlePrice * count;
                        // display the item
                        out.println("<tr>");
                        out.println("<td>" + description + "</td>");
                        out.println("<td>" + count + "</td>");
                        out.println("<td>" + totalPrice + "</td>");
                        out.println("</tr>");
                    }
                }

                out.println("</table>");
            } catch (Exception ex) {
                out.println("<p>Error: " + ex.getMessage() + "</p>");
                out.println("<p>Check Tomcat console for details.</p>");
                ex.printStackTrace();
            }
        }  
        out.close();  
    }  
} 