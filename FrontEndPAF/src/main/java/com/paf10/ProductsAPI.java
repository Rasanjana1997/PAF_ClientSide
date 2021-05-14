package com.paf10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductsAPI
 */
@WebServlet("/ProductsAPI")
public class ProductsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Product proObj;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductsAPI() {
		super();
		proObj = new Product();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = proObj.insertProduct(
				request.getParameter("productCode"),
				request.getParameter("productName"),
				request.getParameter("productPrice"),
				request.getParameter("productDesc"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		String output = proObj.updateProducts(
				Integer.parseInt(paras.get("hidProductIDSave").toString()),
				paras.get("productCode"),
				paras.get("productName"),
				paras.get("productPrice"),
				paras.get("productDesc"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		String output = proObj.removeProducts(Integer.parseInt(paras.get("productID").toString()));
		response.getWriter().write(output);
	}

	// Convert The Request parameters To the MAP
	private static Map<String,String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}
}