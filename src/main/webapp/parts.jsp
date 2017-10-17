<%@ page import="inventory_app.domain_layer.InventoryManager" %>
<%@ page import="inventory_app.domain_layer.Part" %><%--
  Created by IntelliJ IDEA.
  User: Hunter
  Date: 10/15/2017
  Time: 7:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parts</title>
</head>
<body>
    <table style="width:100%" border="1">
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>ID</th>
            <th>Weight</th>
            <th>Quantity</th>
        </tr>
        <%
            InventoryManager manager = InventoryManager.getStaticManager();
            for(Part p : manager.getParts()){
        %>
        <tr>
            <td><%=p.getName()%></td>
            <td><%=p.getCategory()%></td>
            <td><%=p.getId()%></td>
            <td><%=p.getWeight()%></td>
            <td><%=p.getQuantity()%></td>
        </tr>
        <%
            }
        %>
    </table>

</body>
</html>
