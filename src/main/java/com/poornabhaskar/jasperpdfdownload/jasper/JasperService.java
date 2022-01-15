package com.poornabhaskar.jasperpdfdownload.jasper;

import com.poornabhaskar.jasperpdfdownload.model.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperService {

	public void createPdf(HttpServletResponse response) throws JRException, IOException {
		List<User> users = new ArrayList<User>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		users.add(new User(1, "Om"));
		users.add(new User(2, "Om Shakthi"));
		users.add(new User(3, "Omdram"));
		JRBeanCollectionDataSource studentCollectionDataSource = new JRBeanCollectionDataSource(users);
		parameter.put("userDataSource", studentCollectionDataSource);
		parameter.put("title", new String("Hi, I am Title"));
		Resource resource = new ClassPathResource("usersReport.jrxml");
		JasperReport jasperDesign = JasperCompileManager.compileReport(resource.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter, new JREmptyDataSource());
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=NameOfFile.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		System.out.println("Report Generated!");
	}

}
