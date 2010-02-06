/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.bean;



/**
 * Players' penalties at a table.
 *
 * @author ctrung
 * @date 23 août 2009
 */
public class Penalty {

	private Integer id;
	
	private Integer penaltyPlayer1; 
	private Integer penaltyPlayer2; 
	private Integer penaltyPlayer3; 
	private Integer penaltyPlayer4;
	
	/**
	 * Empty constructor for Hibernate
	 */
	public Penalty() {
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the penaltyPlayer1
	 */
	public Integer getPenaltyPlayer1() {
		return penaltyPlayer1;
	}

	/**
	 * @param penaltyPlayer1 the penaltyPlayer1 to set
	 */
	public void setPenaltyPlayer1(Integer penaltyPlayer1) {
		this.penaltyPlayer1 = penaltyPlayer1;
	}

	/**
	 * @return the penaltyPlayer2
	 */
	public Integer getPenaltyPlayer2() {
		return penaltyPlayer2;
	}

	/**
	 * @param penaltyPlayer2 the penaltyPlayer2 to set
	 */
	public void setPenaltyPlayer2(Integer penaltyPlayer2) {
		this.penaltyPlayer2 = penaltyPlayer2;
	}

	/**
	 * @return the penaltyPlayer3
	 */
	public Integer getPenaltyPlayer3() {
		return penaltyPlayer3;
	}

	/**
	 * @param penaltyPlayer3 the penaltyPlayer3 to set
	 */
	public void setPenaltyPlayer3(Integer penaltyPlayer3) {
		this.penaltyPlayer3 = penaltyPlayer3;
	}

	/**
	 * @return the penaltyPlayer4
	 */
	public Integer getPenaltyPlayer4() {
		return penaltyPlayer4;
	}

	/**
	 * @param penaltyPlayer4 the penaltyPlayer4 to set
	 */
	public void setPenaltyPlayer4(Integer penaltyPlayer4) {
		this.penaltyPlayer4 = penaltyPlayer4;
	}
	
	/*
	 * Others
	 */
	
	/**
	 * Return a pretty print string of penaltyPlayer1, 
	 * meaning null is replaced by empty string
	 */
	public String getPenaltyPlayer1PrettyPrint(){
		if (this.penaltyPlayer1==null) return "";
		return this.penaltyPlayer1.toString();
	}
	
	/**
	 * Return a pretty print string of penaltyPlayer2, 
	 * meaning null is replaced by empty string
	 */
	public String getPenaltyPlayer2PrettyPrint(){
		if (this.penaltyPlayer2==null) return "";
		return this.penaltyPlayer2.toString();
	}
	
	/**
	 * Return a pretty print string of penaltyPlayer3, 
	 * meaning null is replaced by empty string
	 */
	public String getPenaltyPlayer3PrettyPrint(){
		if (this.penaltyPlayer3==null) return "";
		return this.penaltyPlayer3.toString();
	}
	
	/**
	 * Return a pretty print string of penaltyPlayer4, 
	 * meaning null is replaced by empty string
	 */
	public String getPenaltyPlayer4PrettyPrint(){
		if (this.penaltyPlayer4==null) return "";
		return this.penaltyPlayer4.toString();
	}
	
}
