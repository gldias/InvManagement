<%@ page import="inventory_app.domain_layer.OrderManager" %>
<%@ page import="inventory_app.domain_layer.Order" %>
<%@ page import="inventory_app.domain_layer.Product" %>
<%@ page import="inventory_app.domain_layer.Item" %><%--
  Created by IntelliJ IDEA.
  User: Hunter
  Date: 10/15/2017
  Time: 7:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>

    <%
        OrderManager manager = OrderManager.getStaticManager();

        for(Order o : manager.getOrders()){ %>
        Order ID: <%=o.getId()%>
        <ul>
            <li>Destination: <%=o.getDestination()%></li>
            <% for(Item i : o.getItems().keySet()){ %>

                <li><%=i.getName()%></li>
                <ul>
                    <li>Quantity: <%=o.getItems().get(i)%></li>
                </ul>

            <% }%>
        </ul>
    <%}
    %>

</body>
</html>
