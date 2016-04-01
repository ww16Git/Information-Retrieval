package edu.ww.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.ww.entity.Document;
import edu.ww.impl.QueryDaoImpl;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");
		String forward = "result.jsp";
		
		QueryDaoImpl queryDao = new QueryDaoImpl();
		//保存Stokens
		ArrayList<String> arrList = new ArrayList<String>();
		//分词存进arrList
		StringTokenizer strTokens = new StringTokenizer(query);
		while (strTokens.hasMoreTokens()) {
			arrList.add(strTokens.nextToken());	
		}
		
		
		//查词，返回map(term,postingsList)
		
		HashMap<Integer, ArrayList<Double>> accumulator = queryDao.query(arrList);
		
		//排序取前5
		ArrayList<Integer> result = queryDao.ranking(accumulator);
		
		//提取url,title
		ArrayList<Document> docList  = queryDao.docinfoRead(result);

		
		HttpSession session = request.getSession();
		session.setAttribute("query", query);
		session.setAttribute("docList", docList);
			// forward
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
			// redirect
			// response.sendRedirect(request.getContextPath() + "/result.jsp");

	}

}
