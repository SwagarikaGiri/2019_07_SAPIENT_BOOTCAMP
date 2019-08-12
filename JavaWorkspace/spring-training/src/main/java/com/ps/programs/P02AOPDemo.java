package com.ps.programs;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ps.cfg.AppConfig4;
import com.ps.dao.DaoException;
import com.ps.dao.ProductDao;
import com.ps.entity.Product;

public class P02AOPDemo {
	 static Logger logger 
     = Logger.getLogger(P02AOPDemo.class.getName()); 
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig4.class);
		ProductDao dao = ctx.getBean("hibernateTemplateProductDao", ProductDao.class);

		logger.info("dao is an instanceof " + dao.getClass().getName());

		int pc = dao.count();
		logger.info("There are " + pc + " products.");

		try {
			Product p = dao.getProduct(2);
			logger.info("p.name = " + p.getProductName());
			logger.info("p.price = $" + p.getUnitPrice());
			
			p.setUnitPrice(p.getUnitPrice() + 1);
			
			dao.updateProduct(p);
			
			p = dao.getProduct(2);
			logger.info("After updating the price...");
			logger.info("p.name = " + p.getProductName());
			logger.info("p.price = $" + p.getUnitPrice());
			
			
		} catch (DaoException e) {
			logger.info(e.toString());
		}

		Collection<Product> list = dao.getProductsByPriceRange(50.0, 250.0);
		logger.info("There are " + list.size() + " products between $50 and $250.");

		list = dao.getProductsByPriceRange(250.0, 50.0);
		logger.info("There are " + list.size() + " products between $250 and $50.");

		list = dao.getProductsNotInStock();
		logger.info(list.size() + " products are not in stock");

		ctx.close();

	}
}
