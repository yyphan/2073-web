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
        if(session==null){  // if not logged in
            request.getRequestDispatcher("login.html").include(request, response); 
            out.println("<p style=' text-align: center; color: red;'> Please login first</p>");
        }  
        else{  
            out.println("<!DOCTYPE html>"); 
            out.println("<html>");          
            out.println("<head><title>Welcome to Your Cart</title>");
            out.println("<link rel='stylesheet'  href='cart.css'>");
            out.println("<div id='headnevigation'>  ");
            out.println("<a href='ecommercequery'>Home</a>"); 
            out.println("</div>");

            //out.println("<p><a href='ecommercequery'>Home</a></p><hr>"); 
            //String name=(String)session.getAttribute("name"); 
            //out.print("Hello, " + name + ". Welcome to Your Cart");  

            out.println("</head>");
            out.println("<body>");
            //out.println("<p><a href='ecommercequery'>Home</a></p><hr>"); 
            //String name=(String)session.getAttribute("name"); 
            //out.print("Hello, " + name + ". Welcome to Your Cart");  

            // display all added products in a table
            try (
                Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "xxxx"
                ); 
                
                Statement stmt = conn.createStatement();
            ) {
                String sqlStr = "SELECT * FROM products;";
                ResultSet rset = stmt.executeQuery(sqlStr);              

                out.println("<div class='Cart-Container'>");
                out.println("<div class='Header'>");
                out.println("<h3 class='Heading'>Shopping Cart</h3>");
                out.println("</div>");

                // loop through all products to see if any of them was added in cart
                while (rset.next()) {
                    int id = rset.getInt("id");
                    String idStr = Integer.toString(id);

                    // if the product with that id was added in cart
                    if (session.getAttribute(idStr) != null)
                    {
                        String product_img = rset.getString("image_src");

                        String brand = rset.getString("brand");
                        String description = rset.getString("description");
                        float singlePrice = rset.getFloat("price");
                        int count = ((Integer)session.getAttribute(idStr)).intValue();
                        float totalPrice = singlePrice * count;

                        out.println("<div class='Cart-Items'>");

                        out.println("<div class='image-box'>");
                        out.println("<img src=' " + product_img + " ' style='height: 100px; border-radius: 5px; margin-bottom: 10px;' />");
                        out.println("</div>");

                        out.println("<div class='about'>");
                        out.println("<h1 class='title'>" + brand + "</h1>");
                        out.println("<h3 class='subtitle'>" + description + "</h3>");
                        out.println("<div class='count'> Count: " + count + "</div>");
                        out.println("</div>");

                        out.println("<div  class='price'>");
                        out.println("<div  class='amount'>" + singlePrice + "</div>");
                        out.println("<div  class='save'>Subtotal Price</div>");
                        out.println("<div  class='remove'>" + totalPrice +"</div>");
                        out.println("</div>");
                      

                        out.println("</div>");

                    }
                }

                out.println("</div>");
                out.println("</body>");
                //out.println("</html>");
            } catch (Exception ex) {
                out.println("<p>Error: " + ex.getMessage() + "</p>");
                out.println("<p>Check Tomcat console for details.</p>");
                ex.printStackTrace();
            }
            out.println("</html>");
        }  
        out.close();  
    }  
} 