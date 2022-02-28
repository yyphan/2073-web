import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
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

            ResultSet rset = stmt.executeQuery("SELECT * FROM users WHERE email='" + email + "';");
            if (rset.next()) {
                throw new Exception("Email exists");
            }

            if (email == "" || name == "" || password == "") throw new Exception("All fields must be filled");

            String sqlStr = "INSERT INTO users (email, name, password) VALUES ('" 
                + email + "', '" 
                + name + "', '" 
                + password 
                + "');";

            stmt.executeUpdate(sqlStr);
            
            out.print("You are successfully registered! Now please log in.");  
            out.print("<a href='login.html'>Login</a>"); 
            out.print("<hr>");
        } catch (Exception ex) {
            request.getRequestDispatcher("signup.html").include(request, response); 
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            ex.printStackTrace();
        }
            
        out.close();     
    }
}
