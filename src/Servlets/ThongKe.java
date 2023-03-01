package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Control.BillsControl;
import Control.ThongKeControl;
import Objects.ThongKes;

/**
 * Servlet implementation class ThongKe
 */
@WebServlet("/admin/pages/thongke/khoangthoigian")
public class ThongKe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThongKe() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
//		ArrayList<ThongKes> list  = new ThongKeControl().getList("2018/12/01", "2018/12/30");
//		System.out.println(list.toString());
//		request.setAttribute("list", list);
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("uslogin") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("khoangthoigian.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("../../../pages/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String tungay = request.getParameter("tungay");
		String denngay = request.getParameter("denngay");
		String aa="yiyeriy";
		
		String[] dataChart = new BillsControl().getSelectDB30Days(tungay,denngay);
		
		request.setAttribute("valueNgay", dataChart[0]);
		request.setAttribute("valueTongTien", dataChart[1]);
		ArrayList<ThongKes> list  = new ThongKeControl().getList(tungay, denngay);
		request.setAttribute("list",list);
		String str = "";
		str += "<table id=\"bootstrap-data-table\"\r\n" + 
				"									class=\"table table-striped table-bordered\">\r\n" + 
				"									<thead>\r\n" + 
				"										<tr>\r\n" + 
				"											<th>Id</th>\r\n" + 
				"											<th>Họ tên</th>\r\n" + 
				"											<th>Số nhà</th>\r\n" + 
				"											<th>Địa chỉ</th>\r\n" + 
				"											<th>Điện thoại</th>\r\n" + 
				"											<th>Email</th>\r\n" + 
				"											<th>Ngày đặt</th>\r\n" + 
				"											<th>Tổng tiền</th>\r\n" + 
				"										</tr>\r\n" + 
				"									</thead>\r\n" + 
				"									<tbody>";
		long tongtien = 0;
		for (ThongKes thongKes : list) {
			str += "<tr>\r\n" + 
					"											<td>"+ thongKes.getId() + "</td>\r\n" + 
					"											<td>"+ thongKes.getHoTen() + "</td>\r\n" + 
					"											<td>"+ thongKes.getSoNhan() + "</td>\r\n" + 
					"											<td>"+ thongKes.getDiaChi() + "</td>\r\n" + 
					"											<td>" + thongKes.getDienThoai() + "</td>\r\n" + 
					"											<td>" + thongKes.getEmail() + "</td>\r\n" + 
					"											<td>" + thongKes.getNgayDat() + "</td>\r\n" + 
					"											<td>" + thongKes.getTongTien() + "</td>\r\n" + 
					"										</tr>";
			tongtien += thongKes.getTongTien() ;
		}
		
		str += "</tbody></table>";
		str += "<input\r\n" + 
				"type=\"hidden\"  id=\"tong\" value=\"" + String.valueOf(tongtien) + "\">";
		response.getWriter().append(str);
		
		
		String chart="				<div class=\"row\">\r\n"
				+ "					<div class=\"col-md-12\">\r\n"
				+ "						<div class=\"card\">\r\n"
				+ "							<div class=\"card-body\">\r\n"
				+ "								<h4 class=\"box-title\">Thu nhập </h4>\r\n"
				+ "								<div class=\"row\">\r\n"
				+ "									<div class=\"col-md-1\"></div>\r\n"
				+ "									<div class=\"col-md-10\">\r\n"
				+ "										<canvas id=\"myChart\"></canvas>\r\n"
				+ "									</div>\r\n"
				+ "									<div class=\"col-md-1\"></div>\r\n"
				+ "								</div>\r\n"
				+ "							</div>\r\n"
				+ "						</div>\r\n"
				+ "					</div>\r\n"
				+ "					<!-- /# column -->\r\n"
				+ "				</div>\r\n"
				+ "				<!-- /Thanh toan -->\r\n"
				+ "			</div>\r\n"
				+ "			<!-- .animated -->\r\n"
				+ "		</div>\r\n"
				+ "		<!-- .content -->\r\n"
				+ "		<form>\r\n"
				+ "			<input type=\"hidden\" value=\""+request.getAttribute("valueNgay")+"\"\r\n"
				+ "				id=\"valueNgay\"> <input type=\"hidden\"\r\n"
				+ "				value=\""+request.getAttribute("valueTongTien")+"\r\n"
				+ "				id=\"valueTongTien\">\r\n"
				+ "		</form>\r\n"
				+ "		<div class=\"clearfix\"></div>\r\n"
				+ "	</div>";
		
		response.getWriter().append(chart);
		
		String script= "<script type=\"text/javascript\">\r\n"
				+ "		$(document).ready(function($) {\r\n"
				+ "			var valueNgay = $('#valueNgay').val();\r\n"
				+ "			valueNgay = valueNgay.replace(/'/g, '\"');\r\n"
				+ "			valueNgay = JSON.parse(valueNgay);\r\n"
				+ "			var valueTongTien = $('#valueTongTien').val();\r\n"
				+ "			valueTongTien = JSON.parse(valueTongTien);\r\n"
				+ "			\r\n"
				+ "			var canvas = document.getElementById('myChart');\r\n"
				+ "			canvas.height = 110;\r\n"
				+ "			var data = {\r\n"
				+ "				labels : valueNgay,\r\n"
				+ "				datasets : [ {\r\n"
				+ "					label : \"Tổng tiền\",\r\n"
				+ "					backgroundColor : \"rgba(255,99,132,0.2)\",\r\n"
				+ "					borderColor : \"rgba(255,99,132,1)\",\r\n"
				+ "					borderWidth : 2,\r\n"
				+ "					hoverBackgroundColor : \"rgba(255,99,132,0.4)\",\r\n"
				+ "					hoverBorderColor : \"rgba(255,99,132,1)\",\r\n"
				+ "					data : valueTongTien\r\n"
				+ "				} ]\r\n"
				+ "			};\r\n"
				+ "			var option = {\r\n"
				+ "				scales : {\r\n"
				+ "					yAxes : [ {\r\n"
				+ "						stacked : true,\r\n"
				+ "						gridLines : {\r\n"
				+ "							display : true,\r\n"
				+ "							color : \"rgba(255,99,132,0.2)\"\r\n"
				+ "						}\r\n"
				+ "					} ],\r\n"
				+ "					xAxes : [ {\r\n"
				+ "						gridLines : {\r\n"
				+ "							display : false\r\n"
				+ "						}\r\n"
				+ "					} ]\r\n"
				+ "				}\r\n"
				+ "			};\r\n"
				+ "\r\n"
				+ "			var myBarChart = new Chart(canvas, {\r\n"
				+ "				type : 'bar',\r\n"
				+ "				data : data,\r\n"
				+ "				options : option\r\n"
				+ "			});\r\n"
				+ "		});\r\n"
				+ "	</script>";
		
		response.getWriter().append(script);
		
	}

}
