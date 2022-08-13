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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/print")
public class PrintServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
//		out.println("This is last page");
		resp.setContentType("text/html");
		
		int fid=Integer.parseInt(req.getParameter("fid"));
		String pname=req.getParameter("pname");
		int page=Integer.parseInt(req.getParameter("page"));
		String address=req.getParameter("paddress");
		Long mob = Long.parseLong(req.getParameter("pmobile"));
		int count = Integer.parseInt(req.getParameter("pcount"));
		
			
//		out.println("This is getparameter"+ fid+"<br>");
//		out.println("This is get paramaeter " + pname+" "+page+ " "+address+ " " + mob + " " + count);
		
		
		InputStream inp=getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		Properties property= new Properties();
		property.load(inp);
		
		Connection conn=DBConfig.getConnection(property);
		
		if(conn!=null) {
			out.println("<center>Payment is done by dummy pament mode.<br><h4><b>Ticket Confirmed</b></h4></center>");
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs=stmt.executeQuery("select * from FlightDetails where f_id="+
						fid);
				
//				out.print("<h3>Cost for a single passenger: "+rs.getInt(6)+"/-"+
//						"<br>Price(for "+count+" seats):" +count* rs.getInt(6)+"/-<br>"
//						+"<br></h3>");
				
				out.println("<center><h3>Ticket Details<br></h3>");
				
				while(rs.next()) {
					out.print("Flight ID: "+rs.getInt(1)+
							"<br>Company ID: "+rs.getInt(2)+
							"<br>Flight Provider: "+rs.getString(3)+
							"<br>Source: "+rs.getString(4)+
							"<br>Destination: "+rs.getString(5)+
							"<br>Amount Paid(for "+count+" seats): " +count* rs.getInt(6)+"/-"+
							"<br>Onboarding Date: "+rs.getString(7)+"<br>"+
							"<h3>Passenger Details</h3>"+
							"Passenger Name: "+ pname+
							"<br>Passenger Age: "+ page+
							"<br>Contact Number: "+ mob+
							"<br>Address: "+ address +"<br><br>"
							);
					
						out.println("<form action=\"index.html\" method=\"post\">"
								+ "<input type=\"submit\" value=\"Go to Home\">"
								+ "</form>");
						
				}
					
			}catch (SQLException e) {
				e.printStackTrace();
				}
			finally {
				out.println("</center>");
			}
			}
		else {
			out.println("This is else block.");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
