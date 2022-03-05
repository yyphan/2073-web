import java.io.*;
import java.sql.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();   
            
        try (
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "xxxx"
            ); 
            
            Statement stmt = conn.createStatement();
        ) {
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String password = request.getParameter("password");

            // check if email's already been registered
            ResultSet rset = stmt.executeQuery("SELECT * FROM users WHERE email='" + email + "';");
            if (rset.next()) {
                throw new Exception("Email exists");
            }

            // check all fields are filled
            if (email == "" || name == "" || password == "") throw new Exception("All fields must be filled");

            // write the new user into database
            String sqlStr = "INSERT INTO users (email, name, password) VALUES ('" 
                + email + "', '" 
                + name + "', '" 
                + password 
                + "');";

            stmt.executeUpdate(sqlStr);
            

            out.println("<!DOCTYPE html>"); 
            out.println("<html>");
            out.println("<link rel='stylesheet'  href='signsuccess.css'>");
            out.println("<div id='headnevigation'> ");
            out.println("<a href='login.html'>Login</a>"); 
            out.println("</div>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='Cart-Container'>");

            out.println("<div class='Header'>");
            out.println("<h3 class='Heading'>You are successfully registered! Now please log in.</h3>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
           //out.print("You are successfully registered! Now please log in.");  
            //out.print("<a href='login.html'>Login</a>"); 
            //out.print("<hr>");
        } catch (Exception ex) {
            // this function is used many times. It is used to insert a piece of html code.
            request.getRequestDispatcher("signup.html").include(request, response); 
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            ex.printStackTrace();
        }
            
        out.close();     
    }
}
