/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.util.Iterator;
import java.util.List;

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
	 * Get a table penalties.
	 * 
	 * @param table 
	 * 
	 * @return the table's penalties, never null.
	 */
	@SuppressWarnings("unchecked")
	public static List<Penalty> getByTable(Table table) throws FatalException {
		
		Session s = HibernateUtil.getSession();
		Query q = s.createQuery("select p from Table as t inner join t.penalty as p where t = :table");
		q.setParameter("table", table);
		
		return (List<Penalty>) q.list();
	}

	/**
	 * Try to collect penalties data.
	 * If collection succeeds, the Penalty object is updated.
	 * 
	 * @param paramName
	 * @param paramValues 
	 * @param table
	 * 
	 * @return true if collection succeeds, false otherwise.
	 * @throws FatalException 
	 */
	public static boolean collectPenaltyData(String paramName, String[] paramValues, Table table) throws FatalException {
		
		if(paramName == null) return false;
		
		assert table != null;
		
		List<Penalty> penalties = table.getPenalties();
		
		if(!paramName.equals("penalty1") && !paramName.equals("penalty2") 
				&& !paramName.equals("penalty3") && !paramName.equals("penalty4")) {
			return false;
		}
		
		// adding new penalties ?
		if(penalties.size() < paramValues.length) {
			for(int i=penalties.size(); i<paramValues.length; i++) {
				Penalty penalty = new Penalty();
				penalties.add(penalty);
			}
		} else if (penalties.size() > paramValues.length){
			// deleting penalties ?
			for(int i=paramValues.length; i<penalties.size(); i++) {
				penalties.remove(i);
			}
		}
		
		// collect
		if(paramName.equals("penalty1")) {
			for(int i=0; i<paramValues.length; i++) {
				penalties.get(i).setPenaltyPlayer1(NumberUtils.getInteger(paramValues[i]));
			}
		} else if (paramName.equals("penalty2")) {
			for(int i=0; i<paramValues.length; i++) {
				penalties.get(i).setPenaltyPlayer2(NumberUtils.getInteger(paramValues[i]));
			}
		} else if(paramName.equals("penalty3")) {
			for(int i=0; i<paramValues.length; i++) {
				penalties.get(i).setPenaltyPlayer3(NumberUtils.getInteger(paramValues[i]));
			}
		} else if(paramName.equals("penalty4")) {
			for(int i=0; i<paramValues.length; i++) {
				penalties.get(i).setPenaltyPlayer4(NumberUtils.getInteger(paramValues[i]));
			}
		}
		
		return true;
	}

	/**
	 * Add/update a table's penalty.
	 * 
	 * @param table
	 * @param penalties
	 */
	public static void addOrUpdateAll(Table table) throws FatalException {
		
		// delete penalties for which values are all null
		List<Penalty> tablePenalties = table.getPenalties();
		for (Iterator<Penalty> iterator = tablePenalties.iterator(); iterator.hasNext();) {
			Penalty penalty = (Penalty) iterator.next();
			if(penalty != null && penalty.getPenaltyPlayer1()==null && penalty.getPenaltyPlayer2()==null 
					&& penalty.getPenaltyPlayer3()==null && penalty.getPenaltyPlayer4()==null) {
				iterator.remove();
			}
		}
		
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
	public static void deleteAll(Table table) throws FatalException {
		List<Penalty> tablePenalties = table.getPenalties();
		tablePenalties.clear();
		
		HibernateUtil.save(table);
	}

	/**
	 * Test if penalties are all valid, i.e sum is equal to 0.
	 * 
	 * @param penalties
	 * @return
	 */
	public static boolean isPenaltiesValid(List<Penalty> penalties) {
		
		if(penalties==null || penalties.size()==0) return true;
		
		for (Iterator<Penalty> iterator = penalties.iterator(); iterator.hasNext();) {
			Penalty penalty = (Penalty) iterator.next();
			
			// skip empty lines
			if(penalty.getPenaltyPlayer1()==null && penalty.getPenaltyPlayer2()==null 
					&& penalty.getPenaltyPlayer3()==null && penalty.getPenaltyPlayer4()==null) {
				continue;
			}
			
			// one of the input is null
			if(penalty.getPenaltyPlayer1()==null || penalty.getPenaltyPlayer2()==null 
					|| penalty.getPenaltyPlayer3()==null || penalty.getPenaltyPlayer4()==null) {
				return false;
			}
			
			// sum is not equal to zero
			if(penalty.getPenaltyPlayer1() + penalty.getPenaltyPlayer2()
					+ penalty.getPenaltyPlayer3() + penalty.getPenaltyPlayer4() != 0) {
				return false;
			}
		}
		
		return true;
	}
	
}
