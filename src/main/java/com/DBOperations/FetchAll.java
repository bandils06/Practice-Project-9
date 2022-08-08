package com.DBOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/fetch")
public class FetchAll extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out= res.getWriter();
		res.setContentType("text/html");
		
		Properties  props= new Properties();
		
		InputStream in=getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		props.load(in);
		
		Connection conn= DBConfig.getConnection(props);
		
		if(conn!=null) {	
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs=stmt.executeQuery("select * from eproduct");
				
				while(rs.next()) {
					out.print("<center>"+rs.getInt(1)+",  "+rs.getString(2)+",  "+rs.getBigDecimal(3)+",  "+rs.getTimestamp(4)+"</center><br>");
				}
								
			} catch (SQLException e) {
				out.println("Error: "+e);
			}
		}
		else {
			out.print("Error While Connecting Connections");
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req, res);

	}
}
