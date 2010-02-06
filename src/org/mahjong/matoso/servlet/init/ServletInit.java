/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.mahjong.matoso.util.HibernateUtil;

/**
 * Servlet for initializing Hibernate.<br>
 * Instanciate the factory by reading the configuration and mapping files.
 *
 * @author ctrung
 * @date 3 juil. 2009
 */
public class ServletInit extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(ServletInit.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		long startTime = System.currentTimeMillis();
		
		logger.info("**************************** Initialising Hibernate...");
		HibernateUtil.init();
		logger.info("**************************** Hibernate successfully initialized in " + (System.currentTimeMillis() - startTime) + " ms");
	}
	
}
