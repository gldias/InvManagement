<%@ page import="inventory_app.domain_layer.InventoryManager" %>
<%@ page import="inventory_app.domain_layer.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="css/indexstyle.css" type="text/css">

    <title>Products</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
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
</nav>

<div class="container body-content">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>
                    Products
                </h4>
            </div>
            <table class="table table-fixed">
                <thead>
                <tr>
                    <th class="col-xs-4">Name</th><th class="col-xs-2">Category</th><th class="col-xs-2">SKU</th><th class="col-xs-2">Weight</th><th class="col-xs-2">Quantity</th>
                </tr>
                </thead>
                <tbody><%InventoryManager manager = InventoryManager.getStaticManager();for(Product p : manager.getProducts()){%>
                <tr>
                    <td class="col-xs-4"><%=p.getName()%></td>
                    <td class="col-xs-2"><%=p.getCategory()%></td>
                    <td class="col-xs-2"><%=p.getSKU()%></td>
                    <td class="col-xs-2"><%=p.getWeight()%></td>
                    <td class="col-xs-2"><%=p.getQuantity()%></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

    <%
        // Example for using forms to create a new item
    %>
    <div class="container">
        <div class="container">
            <div class="form-group center-block">
                <form action="pserv" method="post">
                    <label>
                        Name <input type="text" class="text-input" name="pname">
                    </label><br>
                    <label>
                        Category <input type="text" class="text-input" name="pcat">
                    </label><br>
                    <label>
                        SKU <input type="text" class="text-input" name="psku">
                    </label><br>
                    <label>
                        Weight <input type="number" class="number-input" name="pweight">
                    </label><br>
                    <button type="submit" class="btn btn-Primary" value="newProd" name="prodButton">Create Product</button><br>
                </form>
            </div>
        </div>
    </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</body>
</html>
