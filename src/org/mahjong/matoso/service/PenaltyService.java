/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.mahjong.matoso.bean.Penalty;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Service layer dealing with players penalties at one table.
 *
 * @author ctrung
 * @date 23 août 2009
 */
public abstract class PenaltyService {

	/**
	 * Get a table players' penalties.
	 * 
	 * @param table 
	 * 
	 * @return the table's penalty, never null.
	 */
	public static Penalty getByTable(Table table) throws FatalException {
		
		Session s = HibernateUtil.getSession();
		Query q = s.createQuery("select p from Table as t inner join t.penalty as p where t = :table");
		q.setParameter("table", table);
		
		Penalty p = (Penalty) q.uniqueResult();
		if(p==null) p = new Penalty();
		
		return p;
	}

	/**
	 * Try to collect a penalty data.
	 * If collection succeeds, the Penalty object is updated.
	 * 
	 * @param paramName
	 * @param paramValue
	 * 
	 * @param penalty 
	 * 
	 * @return true if collection succeeds, that is to say the parameter was a penalty data, false otherwise.
	 */
	public static boolean collectPenaltyData(String paramName, String paramValue, Penalty penalty) {
		
		if(paramName == null) return false;
		
		boolean found = true;
		
		if(paramName.equals("penalty1")) penalty.setPenaltyPlayer1(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("penalty2")) penalty.setPenaltyPlayer2(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("penalty3")) penalty.setPenaltyPlayer3(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("penalty4")) penalty.setPenaltyPlayer4(NumberUtils.getInteger(paramValue)); 
		else found = false;
		
		return found;
	}

	/**
	 * Add/update a table's penalty.
	 * 
	 * @param table
	 * @param penalty
	 */
	public static void addOrUpdate(Table table, Penalty penalty) throws FatalException {
		table.setPenalty(penalty);
		HibernateUtil.save(table);
	}

	/**
	 * Remove a table's penalty.
	 * 
	 * @param table
	 * @param penalty
	 * 
	 * @throws FatalException 
	 */
	public static void delete(Table table, Penalty penalty) throws FatalException {
		table.setPenalty(null);
		HibernateUtil.delete(penalty);
	}

	/**
	 * Test if a penalty is empty, ie no values.
	 * 
	 * @param penalty
	 * 
	 * @return true if empty, false otherwise.
	 */
	public static boolean isEmpty(Penalty penalty) {
		return penalty.getPenaltyPlayer1()==null && penalty.getPenaltyPlayer2()==null 
		&& penalty.getPenaltyPlayer3()==null && penalty.getPenaltyPlayer4()==null;
	}

	
}
