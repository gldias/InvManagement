package inventory_app.domain_layer;

import inventory_app.data_mappers.InventoryDataMapper;
import inventory_app.data_mappers.PartDataMapper;
import stubs.accounting;
import stubs.manufacturing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "partservlet",
        urlPatterns = "partserv"
)
public class PartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
        if(req.getParameter("partButtonBuy") != null) {
            accounting a = accounting.getAccounting();
            InventoryManager i = InventoryManager.getStaticManager();
            double price = (double) i.getPart(req.getParameter("pID")).getPrice() * Integer.parseInt(req.getParameter("pQuant"));
            a.withdraw(price);
            i.addParts(req.getParameter("pID"),Integer.parseInt(req.getParameter("pQuant")));
            rep.sendRedirect("/parts.jsp");
        }
        if(req.getParameter("partButtonCreate") != null) {
            InventoryManager i = InventoryManager.getStaticManager();
            i.createPart(req.getParameter("pname"),PartCategory.valueOf(req.getParameter("pcat")),req.getParameter("pID2"), Double.parseDouble(req.getParameter("pweight")));
            rep.sendRedirect("/parts.jsp");
        }
        if(req.getParameter("partButtonSend") != null){
            manufacturing m = manufacturing.getManufacturing();
            m.sendParts(req.getParameter("pID3"),Integer.parseInt(req.getParameter("pQuant2")));
        }
    }
}
