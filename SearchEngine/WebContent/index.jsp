<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Engine</title>

<link href="index.css" type="text/css" rel="stylesheet">
</head>
<body>

	<div class="container">
		<div class="heading">
		</div>

		<div class="body" align="center">
			<h1>UCI Search</h1>
			<form action=" <%=request.getContextPath()%>/searchServlet"
				method="post">
				<Input type="text" name="query" style=" font-size:20px; width:500px;height:30px"/> <input type="submit"
					value="Query" style="width:80px;height:30px;"/>
			</form>
			<h2>E.g 'information retrieval','machine learning'or'security'</h2>
		</div>
	</div>
</body>
</html>