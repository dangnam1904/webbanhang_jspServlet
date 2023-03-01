package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Control.ProductsControl;
import Objects.Products;

/**
 * Servlet implementation class FiterPriceProduct
 */
@WebServlet(name="/FiterPriceProduct", urlPatterns = {"/pages/shop-page" })
public class FiterPriceProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductsControl productController= new ProductsControl();
	private  ArrayList<Products> listProduct ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiterPriceProduct() {
        super();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_type=0;
		if(request.getParameter("id_product")=="" ||request.getParameter("id_product")==null){
		 ArrayList<Products> listProduct=	productController.getListProducts();
			request.setAttribute("listProduct", listProduct);
			
		}else{
			id_type = Integer.parseInt(request.getParameter("id_product"));
			ArrayList<Products> listProduct=	productController.getListProductsType(id_type);
			request.setAttribute("listProduct", listProduct);
		}
		
		System.out.println(request.getAttribute("listProduct"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("./shop-page.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("price")!=null && request.getParameter("price")!="" ) {
			int price_start=0;
			int price_end=0;
			String[] word = null;
			try {
				String  price=request.getParameter("price");
				 word =price.split("-");
				price_start = Integer.parseInt(word[0].replace("k", "000").trim());
				price_end = Integer.parseInt(word[1].replace("k", "000").trim());
			} catch (Exception e) {
				try {
					price_end = Integer.parseInt(word[1].replace("K", "000").trim());
				} catch (NullPointerException e1) {
					
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			listProduct=	productController.getListProductsByPrice(price_start, price_end);
			request.setAttribute("listProduct", listProduct);
		}
		
		if(request.getParameter("text_seach")!="" && request.getParameter("text_seach")!=null ) {
			listProduct=	productController.getListProductsByName(request.getParameter("text_seach"));
			request.setAttribute("listProduct", listProduct);
		}
		
		
		 System.out.println(request.getAttribute("listProduct"));
		 RequestDispatcher dispatcher = request.getRequestDispatcher("./shop-page.jsp");
			dispatcher.forward(request, response);
	}

}
