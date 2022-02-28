import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                            throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        request.getRequestDispatcher("link.html").include(request, response);  
          
        HttpSession session=request.getSession();  
        session.invalidate();  
          
        out.print("You are successfully logged out!");  
          
        out.close();  
    }  
}  
