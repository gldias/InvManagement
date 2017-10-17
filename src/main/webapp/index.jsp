<%@ page import="inventory_app.Application" %><%--
  Created by IntelliJ IDEA.
  User: guidi
  Date: 10/14/2017
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Inventory Management</title>
  </head>
  <body>

    <ul>
      <li> <button ONCLICK="redirectProduct()">View Products</button> </li>
      <li> <button ONCLICK="redirectParts()">View Parts</button> </li>
      <li> <button ONCLICK="redirectOrders()">View Orders</button> </li>
      <br/>
      <%
        // Example of java code in a jsp page
        System.out.println("IP Address: " + request.getRemoteAddr());
      %>
      <script language="JavaScript">
        function redirectProduct(){
            window.location.href = 'products.jsp';
        }
        function redirectParts(){
            window.location.href = 'parts.jsp';
        }
        function redirectOrders(){
            window.location.href = 'orders.jsp';
        }
      </script>
    </ul>

  </body>
</html>
