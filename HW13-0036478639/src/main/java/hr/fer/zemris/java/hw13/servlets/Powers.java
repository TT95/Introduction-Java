package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook; 

/**
 * Servlet used for xls representation of 
 * array of numbers and their powers based on
 * given arguments.
 * @author Teo Toplak
 *
 */
@WebServlet("/powers")
public class Powers extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		Integer startFrom = null;
		Integer endAt = null;
		Integer pages = null;
		
		try {
			startFrom = Integer.valueOf(req.getParameter("a"));			
		} catch (Exception e) {
			startFrom=1;
		}
		
		try {
			endAt = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ex){
			endAt=5;
		}
		
		if(startFrom > endAt) {
			Integer tmp = startFrom;
			startFrom = endAt;
			endAt = tmp;
		}
		
		try {
			pages = Integer.valueOf(req.getParameter("n"));
		} catch (Exception ex){
			pages=3;
		}
		
		if(startFrom > 100 || startFrom < -100 ||
				endAt > 100 || endAt < -100 ||
				pages > 5 || pages < 1) {
			req.getRequestDispatcher("/WEB-INF/pages/powers_error.jsp")
			.forward(req, resp);
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=filename.xls");
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int page = 0; page < pages; page++) {
			HSSFSheet sheet = hwb.createSheet(page+"_sheet");
			for(int num = startFrom; num<=endAt;num++) {
				HSSFRow numColumn = sheet.createRow((short) num-startFrom);
				numColumn.createCell((int) 1).setCellValue(num);
				numColumn.createCell((int) 2).setCellValue(Math.pow(num, page));
			}
		}
		hwb.write(resp.getOutputStream()); 
		hwb.close();	
	}
}