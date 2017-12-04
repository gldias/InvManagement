package inventory_app.domain_layer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/productservlet")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
        InventoryManager i = InventoryManager.getStaticManager();

        if("newProd" = req.getParameter("prodButton")) {
            i.createProduct(req.getParameter("pname"), req.getParameter("pcat"), req.getParameter("psku"), req.getParameter("pweight"));
        }
        //in case you want to forward a response
        //req.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);
    }
}
