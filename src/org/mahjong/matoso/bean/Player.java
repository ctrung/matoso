/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.bean;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.mahjong.matoso.util.NumberUtils;

/**
 * Player bean
 * 
 * @author ctrung
 * @date 21 oct. 2009
 */
public class Player {

	private Integer id;
	private Set<Tournament> tournaments;

	private String firstname;
	private String lastname;
	private String email;
	private String country;
	private String ema;
	private String pseudo;
	private Date dateArrival;
	private Date dateDeparture;
	private Date dateFormular;
	private Date datePayment;
	private String paymentMode;
	private boolean hasPhoto;
	private String cj;
	private String cp;
	private String details;
	private String club;

	transient Integer rank;
	transient Integer nbGames;
	transient Integer nbVictory;
	transient Integer nbDefeat;
	transient Integer nbGiven;
	transient Integer nbSelfpick;
	transient Integer nbSustainSelfpick;
	transient Integer nbDraw;
	transient Integer score;
	transient Double points;
	
	// TODO add a proper persistent mapping to Team
	transient Team team;

	transient List<Table> tables;

	public Player() {
		nbGames = 0;
		nbVictory = 0;
		nbDefeat = 0;
		nbGiven = 0;
		nbSelfpick = 0;
		nbSustainSelfpick = 0;
		nbDraw = 0;
		score = 0;
		points = 0D;
	}

	public Player(String firstname) {
		this.firstname = firstname;
	}

	public String toString() {
		return this.firstname + " " + this.lastname;
	}

	public Player(String firstname, String lastname, String email, String country, String ema, String team, String pseudo, Date dateArrival,
			Date dateDeparture, Date dateFormular, Date datePayment, String paymentMode, boolean hasPhoto, String cj, String cp, String details,
			String club) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.country = country;
		this.ema = ema;
		this.pseudo = pseudo;
		this.dateArrival = dateArrival;
		this.dateDeparture = dateDeparture;
		this.dateFormular = dateFormular;
		this.datePayment = datePayment;
		this.paymentMode = paymentMode;
		this.hasPhoto = hasPhoto;
		this.cj = cj;
		this.cp = cp;
		this.details = details;
		this.club = club;
	}

	public Player(String firstname, String lastname, String country, String ema, String pseudo) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.country = country;
		this.ema = ema;
		this.pseudo = pseudo;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the ema
	 */
	public String getEma() {
		return ema;
	}

	/**
	 * @param ema
	 *            the ema to set
	 */
	public void setEma(String ema) {
		this.ema = ema;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo
	 *            the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return the dateArrival
	 */
	public Date getDateArrival() {
		return dateArrival;
	}

	/**
	 * @param dateArrival
	 *            the dateArrival to set
	 */
	public void setDateArrival(Date dateArrival) {
		this.dateArrival = dateArrival;
	}

	/**
	 * @return the dateDeparture
	 */
	public Date getDateDeparture() {
		return dateDeparture;
	}

	/**
	 * @param dateDeparture
	 *            the dateDeparture to set
	 */
	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}

	/**
	 * @return the dateFormular
	 */
	public Date getDateFormular() {
		return dateFormular;
	}

	/**
	 * @param dateFormular
	 *            the dateFormular to set
	 */
	public void setDateFormular(Date dateFormular) {
		this.dateFormular = dateFormular;
	}

	/**
	 * @return the datePayment
	 */
	public Date getDatePayment() {
		return datePayment;
	}

	/**
	 * @param datePayment
	 *            the datePayment to set
	 */
	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the hasPhoto
	 */
	public boolean isHasPhoto() {
		return hasPhoto;
	}

	/**
	 * @param hasPhoto
	 *            the hasPhoto to set
	 */
	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	/**
	 * @return the cj
	 */
	public String getCj() {
		return cj;
	}

	/**
	 * @param cj
	 *            the cj to set
	 */
	public void setCj(String cj) {
		this.cj = cj;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the club
	 */
	public String getClub() {
		return club;
	}

	/**
	 * @param club
	 *            the club to set
	 */
	public void setClub(String club) {
		this.club = club;
	}

	public Set<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	/*
	 * Other methods
	 */

	/**
	 * Get a pretty print form of the player's name.
	 * 
	 * @return the firstname and the name separated by a space.
	 */
	public String getPrettyPrintName() {

		String res = this.firstname == null ? "" : this.firstname + " ";

		res += this.lastname == null ? "" : this.lastname;

		return res;
	}

	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * @return the nbGames
	 */
	public Integer getNbGames() {
		return nbGames;
	}

	/**
	 * @param nbGames
	 *            the nbGames to set
	 */
	public void setNbGames(Integer nbGames) {
		this.nbGames = nbGames;
	}

	/**
	 * @return the number of won on discard games.
	 */
	public Integer getNbVictory() {
		return nbVictory;
	}

	/**
	 * @param nbVictory
	 *            the nbVictory to set
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
	 * @param nbDefeat
	 *            the nbDefeat to set
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
	 * @param nbGiven
	 *            the nbGiven to set
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
	 * @param nbSelfpick
	 *            the nbSelfpick to set
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
	 * @param nbSustainSelfpick
	 *            the nbSustainSelfpick to set
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
	 * @param nbDraw
	 *            the nbDraw to set
	 */
	public void setNbDraw(Integer nbDraw) {
		this.nbDraw = nbDraw;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the points
	 */
	public Double getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(Double points) {
		this.points = points;
	}

	/**
	 * Return the the percentage of selfpick games. <br>
	 * Unlike others getter methods, it directly returns a String type because
	 * it is calculated by dividing the number of selfpick games by the number
	 * of total games.
	 * 
	 * @return the percentage of selfpick games.
	 */
	public Double getPercSelfpick() {
		if (this.getNbGames() == null || this.getNbGames().intValue() == 0)
			return null;
		return ((double) this.getNbSelfpick().intValue()) / this.getNbGames().intValue() * 100;
	}

	/**
	 * Return the percentage of won on discard games. <br>
	 * Unlike others getter methods, it directly returns a String type because
	 * it is calculated by dividing the number of won on discard games by the
	 * number of total games.
	 * 
	 * @return the percentage of won on discard games.
	 */
	public Double getPercVictory() {
		if (this.getNbGames() == null || this.getNbGames().intValue() == 0)
			return null;
		return ((double) this.getNbVictory().intValue()) / this.getNbGames().intValue() * 100;
	}

	/**
	 * Return a pretty print form of the percentage of given games. <br>
	 * Unlike others getter methods, it directly returns a String type because
	 * it is calculated by dividing the number of given games by the number of
	 * total games.
	 * 
	 * @return the percentage of given games.
	 */
	public Double getPercGiven() {
		if (this.getNbGames() == null || this.getNbGames().intValue() == 0)
			return null;
		return ((double) this.getNbGiven().intValue()) / this.getNbGames().intValue() * 100;
	}

	/**
	 * Increment the selfpick number
	 */
	public void incrementNbSelfpick() {
		nbSelfpick++;
	}

	/**
	 * Increment the games number
	 */
	public void incrementNbGames() {
		nbGames++;
	}

	/**
	 * Increment the victory number
	 */
	public void incrementNbVictory() {
		nbVictory++;
	}

	/**
	 * Increment the defeat number
	 */
	public void incrementNbDefeat() {
		nbDefeat++;
	}

	/**
	 * Increment the given number
	 */
	public void incrementNbGiven() {
		nbGiven++;
	}

	/**
	 * Increment the sustain selfpick number
	 */
	public void incrementNbSustainSelfpick() {
		nbSustainSelfpick++;
	}

	/**
	 * Increment the draw number
	 */
	public void incrementNbDraw() {
		nbDraw++;
	}

	/**
	 * Add to the score
	 * 
	 * @param score
	 */
	public void addToScore(Integer score) {
		if (score == null)
			score = 0;
		this.score += score;
	}

	/**
	 * Add to the points
	 * 
	 * @param points
	 */
	public void addToPoints(Double points) {
		if (points == null)
			points = 0D;
		this.points += points;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public String getSelfDraw() {
		return getNbSelfpick() + " (" + NumberUtils.getPrettyPrintForm(getPercSelfpick()) + " %)";
	}

	public String getWin() {
		return getNbVictory() + " (" + NumberUtils.getPrettyPrintForm(getPercVictory()) + " %)";
	}

	public String getLose() {
		return getNbGiven() + " (" + NumberUtils.getPrettyPrintForm(getPercGiven()) + " %)";
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
}
