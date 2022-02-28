import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                            throws ServletException, IOException {  
        HttpSession session=request.getSession();  
        session.invalidate();  

        request.getRequestDispatcher("ecommercequery").forward(request, response);
    }  
}  
