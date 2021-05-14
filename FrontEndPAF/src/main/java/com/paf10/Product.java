package com.paf10;
import java.sql.*;

public class Product {

	// Database Connection 
	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafclient",	"root", "");
			
			//Test Connection
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}

	//Insert Product Function
	public String insertProduct(String Code, String Name, String Price, String Desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// Create The Prepared Statement
			String query = " insert into product(`productID`,`productCode`,`productName`,`productPrice`,`productDesc`) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			//Binding Values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Code);
			preparedStmt.setString(3, Name);
			preparedStmt.setDouble(4, Double.parseDouble(Price));
			preparedStmt.setString(5, Desc); 

			//Prepared Statement Execute
			preparedStmt.execute();
			con.close();

			String newProducts = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Read Product Function
	public String readProducts()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Create HTML Table 
			output = "<table  class='table table-dark table-striped'><tr><th>Product Code</th>"
					+"<th>Product Name</th><th>Product Price</th>"
					+ "<th>Product Description</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from product";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

		
			while (rs.next())
			{
				String productID = Integer.toString(rs.getInt("productID"));
				String productCode = rs.getString("productCode");
				String productName = rs.getString("productName");
				String productPrice = Double.toString(rs.getDouble("productPrice"));
				String productDesc = rs.getString("productDesc");

				// Add Row To HTML Table
				output += "<tr><td>" + productCode + "</td>";
				output += "<td>" + productName + "</td>";
				output += "<td>" + productPrice + "</td>"; 
				output += "<td>" + productDesc + "</td>";
				

				// Update and Remove Button
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-productid='" + productID + "'></td>"
						+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-productid='" + productID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the Products.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update Product Function
	public String updateProducts(int ID, String Code, String Name, String Price, String Desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// Create The Prepared Statement
			String query = "update product set `productCode`=?,`productName`=?,`productPrice`=?,`productDesc`=? where `productID`=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Values Binding
			preparedStmt.setString(1, Code);
			preparedStmt.setString(2, Name);
			preparedStmt.setDouble(3, Double.parseDouble(Price));
			preparedStmt.setString(4, Desc);
			preparedStmt.setInt(5, ID);

			//Execute the Prepared Statement
			preparedStmt.executeUpdate();
			con.close();

			String newProducts = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
			
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Delete Product Function
	public String removeProducts(int ID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// Create The Prepared Statement
			String query = "delete from product where `productID`=?;";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Binding Values
			preparedStmt.setInt(1, ID);

			//Execute The Prepared Statement
			preparedStmt.executeUpdate();
			con.close();

			String newProducts = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
}
