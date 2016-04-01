<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.ArrayList,java.sql.ResultSet,edu.ww.entity.Document"%>
<%@ page import="edu.ww.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Result</title>

<link href="result.css" type="text/css" rel="stylesheet">
</head>
<body>
	<%
		String queryinfo = (String) session.getAttribute("query");
	%>
	<div class="container">
		<div class="wrapper">
			<div class="heading">
				<div class="heading_form">
					
					<form action=" <%=request.getContextPath()%>/searchServlet"
						method="post">
						
								<Input type="text" name="query" style="font-size:20px;width:500px;height:30px" value="<%=queryinfo%>" /> 
								<input type="submit" style="width:80px;height:30px;" value="Query" />
						
					</form>
				</div>
			</div>
			<div class="body">
				<%
					ArrayList<Document> docList = (ArrayList<Document>) session.getAttribute("docList");
					ArrayList<Integer> top = (ArrayList<Integer>) session.getAttribute("result");
					for (int i = 0; i < docList.size(); i++) {
						if (docList.get(i).title.equals("")) {
							docList.get(i).title = "Untitled";
						}
				%>
			
				<dl>
				
				
					<dt>
						<a href=<%=docList.get(i).url%>><%=docList.get(i).title%></a>
					</dt>
					<dd>
						<a href=<%=docList.get(i).url%> style="color: blue;"><%=docList.get(i).url%></a>
					</dd>
				
				</dl>

				<%
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>