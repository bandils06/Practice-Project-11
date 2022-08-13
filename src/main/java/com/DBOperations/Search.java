package com.DBOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class Search extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out=resp.getWriter();
		resp.setContentType("text/html");
		
		String from=req.getParameter("from");
		String to=req.getParameter("to");
		int count=Integer.parseInt(req.getParameter("count"));
		String date=req.getParameter("date");
		
		InputStream in=getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		Properties props= new Properties();
		props.load(in);
		
		Connection conn=DBConfig.getConnection(props);
		
		if(conn!=null) {
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				out.println("<center><h2>Available Flights</h2>");
				out.println("<br><br>");
				ResultSet rs=stmt.executeQuery("select * from FlightDetails where (f_from='"+
							from+"' and f_to='"+to+"' and f_date='"+date+"')");
//				ResultSet rs=stmt.executeQuery("select * from FlightDetails");
				while(rs.next()) {
					
					out.println("<p style=\"color: red\"><b>NOTE: </b>In case of booking flight, please remember <b>'Flight ID'</b></p>");
					out.print("Flight ID: "+rs.getInt(1)+"    <t>Company ID: "+rs.getInt(2)+"    <t>Company Name: "+rs.getString(3)
						+"<br>Source: "+rs.getString(4)+"<br>Destination: "+rs.getString(5)
						+"<br>Price(for "+count+" seats):" +count* rs.getInt(6)+"/-<br>Date: "
						+rs.getString(7)+"<br><a href=details.html?=fid="+rs.getInt(1)+">Book Now</a><br>");
					
					
//					int finalPrice = count*rs.getInt(6);
//					req.setAttribute("finalPrice", finalPrice);
//					RequestDispatcher view = req.getRequestDispatcher("/details.jsp");
//					view.forward(req, resp);
//					resp.sendRedirect("/details.html");
					
				}
				out.println("</center>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			out.print("Error While Connecting Connections");
		}
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
