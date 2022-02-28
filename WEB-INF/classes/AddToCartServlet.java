import java.io.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/addtocart") 
public class AddToCartServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
        String product_id = request.getParameter("product_id");
        
        // Add the product to the cart using session
        HttpSession session=request.getSession(false);  
        if(session!=null){ 
            // data is like [1001, 2]: 2 of product with id 1001 were added in cart
            if (session.getAttribute(product_id) != null)
            {
                // increment count if the product was already added
                int count = ((Integer) session.getAttribute(product_id)).intValue();
                session.setAttribute(product_id, count + 1);
            } else {
                // add the product if not added in cart
                session.setAttribute(product_id, 1);
            }
        }  
    }
}
