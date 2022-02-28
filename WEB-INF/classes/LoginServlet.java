import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*; 

@WebServlet("/login")
public class LoginServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                    throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
        request.getRequestDispatcher("link.html").include(request, response);  

        try (
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/newecommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "xxxx"
            ); 
            
            Statement stmt = conn.createStatement();
        ) {
            String email=request.getParameter("email");  
            String password=request.getParameter("password");  

            String sqlStr = "SELECT * FROM users WHERE email='" + email + "';";
            ResultSet rset = stmt.executeQuery(sqlStr);

            if (!rset.next())
            {
                throw new Exception("Sorry, email does not exist. Please sign up or check again.");
            }
            else
            {
                String passwordInDB = rset.getString("password");
                String name = rset.getString("name");
                int user_id = rset.getInt("id");
                if (password.equals(passwordInDB))
                {
                    out.print("Welcome, "+name);  
                    HttpSession session=request.getSession();  
                    session.setAttribute("name",name); 
                    session.setAttribute("user_id",user_id); 
                }
                else
                {
                    throw new Exception("Sorry, password incorrect. Please try again.");
                }
            }
        } catch (Exception ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.close();  
    }  
} 