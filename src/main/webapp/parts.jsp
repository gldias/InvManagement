<%@ page import="inventory_app.domain_layer.InventoryManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <title>Parts</title>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">Inventory</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="products.jsp">Products</a></li>
                <li><a href="orders.jsp">Orders</a></li>
                <li><a href="parts.jsp">Parts</a></li>
            </ul>
        </div>
    </div>
</div>

<h2>Parts/h2>

<div class="container body-content">
    <table class="table table-bordered" >
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>ID</th>
            <th>Weight</th>
            <th>Quantity</th>
        </tr>
        <%
            InventoryManager manager = InventoryManager.getStaticManager();
            // Part is already a type in Java so we have to be explicit about our casts here
            for(inventory_app.domain_layer.Part p : manager.getParts()){
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
</div>
</body>
</html>
