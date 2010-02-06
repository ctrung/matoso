/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.display;

import org.mahjong.matoso.util.NumberUtils;

/**
 * A simple object to display a tournament stats.
 * 
 * @author ctrung
 * @date 3 janv. 2010
 */
public class TournamentStats {
	
	private Integer nbGames;
	private Integer nbVictory;
	private Integer nbDefeat;
	private Integer nbGiven;
	private Integer nbSelfpick;
	private Integer nbSustainSelfpick;
	private Integer nbDraw;
	
	public TournamentStats() {
		super();
		this.nbGames = 0;
		this.nbVictory = 0;
		this.nbDefeat = 0;
		this.nbGiven = 0;
		this.nbSelfpick = 0;
		this.nbSustainSelfpick = 0;
		this.nbDraw = 0;
	}
	
	/**
	 * @return the nbGames
	 */
	public Integer getNbGames() {
		return nbGames;
	}
	/**
	 * @param nbGames the nbGames to set
	 */
	public void setNbGames(Integer nbGames) {
		this.nbGames = nbGames;
	}
	/**
	 * @return the nbVictory
	 */
	public Integer getNbVictory() {
		return nbVictory;
	}
	/**
	 * @param nbVictory the nbVictory to set
	 */
	public void setNbVictory(Integer nbVictory) {
		this.nbVictory = nbVictory;
	}
	/**
	 * @return the nbDefeat
	 */
	public Integer getNbDefeat() {
		return nbDefeat;
	}
	/**
	 * @param nbDefeat the nbDefeat to set
	 */
	public void setNbDefeat(Integer nbDefeat) {
		this.nbDefeat = nbDefeat;
	}
	/**
	 * @return the nbGiven
	 */
	public Integer getNbGiven() {
		return nbGiven;
	}
	/**
	 * @param nbGiven the nbGiven to set
	 */
	public void setNbGiven(Integer nbGiven) {
		this.nbGiven = nbGiven;
	}
	/**
	 * @return the nbSelfpick
	 */
	public Integer getNbSelfpick() {
		return nbSelfpick;
	}
	/**
	 * @param nbSelfpick the nbSelfpick to set
	 */
	public void setNbSelfpick(Integer nbSelfpick) {
		this.nbSelfpick = nbSelfpick;
	}
	/**
	 * @return the nbSustainSelfpick
	 */
	public Integer getNbSustainSelfpick() {
		return nbSustainSelfpick;
	}
	/**
	 * @param nbSustainSelfpick the nbSustainSelfpick to set
	 */
	public void setNbSustainSelfpick(Integer nbSustainSelfpick) {
		this.nbSustainSelfpick = nbSustainSelfpick;
	}
	/**
	 * @return the nbDraw
	 */
	public Integer getNbDraw() {
		return nbDraw;
	}
	/**
	 * @param nbDraw the nbDraw to set
	 */
	public void setNbDraw(Integer nbDraw) {
		this.nbDraw = nbDraw;
	}
	
	/*
	 * Other methods
	 */
	
	/**
	 * Return a pretty print form of the percentage of selfpick games.
	 * <br> Unlike others getter methods, it directly returns a String type because 
	 * it is calculated by dividing the number of selfpick games by the number 
	 * of total games.
	 * 
	 * @return the pretty print form of percentage of selfpick games or "N/A" if the total 
	 * of nb games is null or 0.
	 */
	public String getPercSelfpick() {
		if(this.getNbGames()==null || this.getNbGames().intValue()==0) return "N/A";
		double perc = ((double)this.getNbSelfpick().intValue()) / this.getNbGames().intValue();
		return NumberUtils.getPrettyPrintForm(perc * 100) + "%";
	}
	
	/**
	 * Return a pretty print form of the percentage of won on discard games.
	 * <br> Unlike others getter methods, it directly returns a String type because 
	 * it is calculated by dividing the number of won on discard games by the number 
	 * of total games.
	 * 
	 * @return the pretty print form of percentage of won on discard games or "N/A" if the total 
	 * of nb games is null or 0.
	 */
	public String getPercVictory() {
		if(this.getNbGames()==null || this.getNbGames().intValue()==0) return "N/A";
		double perc = ((double)this.getNbVictory().intValue()) / this.getNbGames().intValue();
		return NumberUtils.getPrettyPrintForm(perc * 100) + "%";
	}	
	
	/**
	 * Return a pretty print form of the percentage of given games.
	 * <br> Unlike others getter methods, it directly returns a String type because 
	 * it is calculated by dividing the number of given games by the number 
	 * of total games.
	 * 
	 * @return the pretty print form of percentage of given games or "N/A" if the total 
	 * of nb games is null or 0.
	 */
	public String getPercGiven() {
		if(this.getNbGames()==null || this.getNbGames().intValue()==0) return "N/A";
		double perc = ((double)this.getNbGiven().intValue()) / this.getNbGames().intValue();
		return NumberUtils.getPrettyPrintForm(perc * 100) + "%";
	}	
	
	/**
	 * Return a pretty print form of the percentage of draw games.
	 * <br> Unlike others getter methods, it directly returns a String type because 
	 * it is calculated by dividing the number of given games by the number 
	 * of total games.
	 * 
	 * @return the pretty print form of percentage of draw games or "N/A" if the total 
	 * of nb games is null or 0.
	 */
	public String getPercDraw() {
		if(this.getNbGames()==null || this.getNbGames().intValue()==0) return "N/A";
		double perc = ((double)this.getNbDraw().intValue()) / this.getNbGames().intValue();
		return NumberUtils.getPrettyPrintForm(perc * 100) + "%";
	}	
}
