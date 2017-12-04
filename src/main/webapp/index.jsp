<%@ page import="inventory_app.Application" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
     <link rel="stylesheet" href="css/indexstyle.css" type="text/css">
      <title>Inventory Management</title>
  </head>
  <body>
  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
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

  <%
    // Example for using forms to create a new item
  %>
  <div class="container">
    <h1>Inventory Management</h1>
    <div class="container t">
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
            <button type="submit" class="btn btn-Primary" value="newProd" name="prodButton">Create New Product</button><br>
        </form>
      </div>
  </div>
  </div>
  </body>
</html>
