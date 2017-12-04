package inventory_app.domain_layer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "productservlet",
        urlPatterns = "pserv"
)
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
        InventoryManager i = InventoryManager.getStaticManager();

        if(req.getParameter("prodButton") != null) {
            i.createProduct(req.getParameter("pname"), ProductCategory.valueOf(req.getParameter("pcat")), req.getParameter("psku"), Double.parseDouble(req.getParameter("pweight")));
            rep.sendRedirect("/index.jsp");
        }
        //in case you want to forward a response
        //req.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);
    }
}
