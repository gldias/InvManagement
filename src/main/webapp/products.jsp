<%@ page import="inventory_app.Application" %>
<%@ page import="inventory_app.domain_layer.Product" %>
<%@ page import="inventory_app.domain_layer.InventoryManager" %>
<%--
  Created by IntelliJ IDEA.
  User: Hunter
  Date: 10/15/2017
  Time: 2:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<table style="width:100%" border="1">
    <tr>
        <th>Name</th>
        <th>Category</th>
        <th>SKU</th>
        <th>Weight</th>
        <th>Quantity</th>
    </tr>
    <%
        InventoryManager manager = InventoryManager.getStaticManager();
        for(Product p : manager.getProducts()){
    %>
    <tr>
        <td><%=p.getName()%></td>
        <td><%=p.getCategory()%></td>
        <td><%=p.getSKU()%></td>
        <td><%=p.getWeight()%></td>
        <td><%=p.getQuantity()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
