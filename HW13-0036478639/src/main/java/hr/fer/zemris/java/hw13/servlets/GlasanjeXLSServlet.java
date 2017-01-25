package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.Results;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
* Servlet used for xls file representation of
* voting.
* @author Teo Toplak
*
*/
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		@SuppressWarnings("unchecked")
		List<Results> list = (List<Results>)req.getServletContext().getAttribute("results");
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=filename.xls");
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("new sheet");

		int i=1;
		for (Results res : list) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(res.getName());
			row.createCell(1).setCellValue(res.getNum());
			i++;
		}
		hwb.write(resp.getOutputStream()); 
		hwb.close();
	}
}