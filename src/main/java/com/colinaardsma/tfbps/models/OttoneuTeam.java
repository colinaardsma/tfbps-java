package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ottoneuteam")
public class OttoneuTeam extends AbstractEntity {

	// team identifiers
	private int teamNumber;
	private String teamKey;
	private String teamName;
	private String teamURL;
	private int teamBudget;
	private int teamMoves; // https://ottoneu.fangraphs.com/642/transactions
	private int teamTrades; // https://ottoneu.fangraphs.com/642/transactions
	private String teamManagerName;
	private int leagueNumber;
	private int season;
	private String leagueKey;
	private int rank;
	private double totalPoints;
	
	// stats
	private int rStats;
	private int hrStats;
	private int rbiStats;
	private int sbStats;
	private double avgStats;
	private double ipStats;
	private int wStats;
	private int svStats;
	private int kStats;
	private double eraStats;
	private double whipStats;

	// points
	private double rPoints;
	private double hrPoints;
	private double rbiPoints;
	private double sbPoints;
	private double avgPoints;
	private double wPoints;
	private double svPoints;
	private double kPoints;
	private double eraPoints;
	private double whipPoints;
	
	// join variables
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private User user;

	/*
	 * 60 = h/ab
	 * 7 = r
	 * 12 = hr
	 * 13 = rbi
	 * 16 = sb
	 * 55 = ops
	 * 3 = avg
	 * 50 = ip
	 * 28 = w
	 * 32 = sv
	 * 42 = k
	 * 26 = era
	 * 27 = whip
	 */
	
	public OttoneuTeam(int teamNumber, String teamName, String teamURL, int leagueNumber, int season, String leagueKey, 
			int rStats, int hrStats, int rbiStats, int sbStats, double avgStats, int wStats, int svStats, int kStats, 
			double eraStats, double whipStats, double rPoints, double hrPoints, double rbiPoints, double sbPoints, 
			double avgPoints, double ipStats, double wPoints, double svPoints, double kPoints, double eraPoints,
			double whipPoints, int rank, double totalPoints) {

		this.teamKey = leagueKey + "." + teamNumber;
		this.teamName = teamName;
		this.teamURL = teamURL;
		this.leagueNumber = leagueNumber;
		this.season = season;
		this.leagueKey = leagueKey;
		this.rStats = rStats;
		this.hrStats = hrStats;
		this.rbiStats = rbiStats;
		this.sbStats = sbStats;
		this.avgStats = avgStats;
		this.ipStats = ipStats;
		this.wStats = wStats;
		this.svStats = svStats;
		this.kStats = kStats;
		this.eraStats = eraStats;
		this.whipStats = whipStats;
		this.rPoints = rPoints;
		this.hrPoints = hrPoints;
		this.rbiPoints = rbiPoints;
		this.sbPoints = sbPoints;
		this.avgPoints = avgPoints;
		this.wPoints = wPoints;
		this.svPoints = svPoints;
		this.kPoints = kPoints;
		this.eraPoints = eraPoints;
		this.whipPoints = whipPoints;
		this.rank = rank;
		this.totalPoints = totalPoints;
	}
	
	public OttoneuTeam(int teamNumber, String teamName, String teamURL, int leagueNumber, int season, String leagueKey) {
		this.teamKey = leagueKey + "." + teamNumber;
		this.teamNumber = teamNumber;
		this.teamName = teamName;
		this.teamURL = teamURL;
		this.leagueNumber = leagueNumber;
		this.season = season;
		this.leagueKey = leagueKey;

	}

	public OttoneuTeam(){}

    @NotNull
    @Column(name = "teamNumber", unique = true)
	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

    @NotNull
    @Column(name = "teamKey", unique = true)
	public String getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}

    @Column(name = "teamName")
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    @Column(name = "teamURL")
	public String getTeamURL() {
		return teamURL;
	}

	public void setTeamURL(String teamURL) {
		this.teamURL = teamURL;
	}

    @Column(name = "teamBudget")
	public int getTeamBudget() {
		return teamBudget;
	}

	public void setTeamBudget(int teamBudget) {
		this.teamBudget = teamBudget;
	}

    @Column(name = "teamMoves")
	public int getTeamMoves() {
		return teamMoves;
	}

	public void setTeamMoves(int teamMoves) {
		this.teamMoves = teamMoves;
	}

    @Column(name = "teamTrades")
	public int getTeamTrades() {
		return teamTrades;
	}

	public void setTeamTrades(int teamTrades) {
		this.teamTrades = teamTrades;
	}

    @Column(name = "teamManagerName")
	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

    @Column(name = "leagueNumber")
	public int getLeagueNumber() {
		return leagueNumber;
	}

	public void setLeagueNumber(int leagueNumber) {
		this.leagueNumber = leagueNumber;
	}

    @Column(name = "season")
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

    @Column(name = "leagueKey")
	public String getLeagueKey() {
		return leagueKey;
	}

	public void setLeagueKey(String leagueKey) {
		this.leagueKey = leagueKey;
	}

    @Column(name = "rStats")
	public int getRStats() {
		return rStats;
	}

	public void setRStats(int rStats) {
		this.rStats = rStats;
	}

    @Column(name = "hrStats")
	public int getHrStats() {
		return hrStats;
	}

	public void setHrStats(int hrStats) {
		this.hrStats = hrStats;
	}

    @Column(name = "rbiStats")
	public int getRbiStats() {
		return rbiStats;
	}

	public void setRbiStats(int rbiStats) {
		this.rbiStats = rbiStats;
	}

    @Column(name = "sbStats")
	public int getSbStats() {
		return sbStats;
	}

	public void setSbStats(int sbStats) {
		this.sbStats = sbStats;
	}

    @Column(name = "avgStats")
	public double getAvgStats() {
		return avgStats;
	}

	public void setAvgStats(double avgStats) {
		this.avgStats = avgStats;
	}

    @Column(name = "ipStats")
	public double getIpStats() {
		return ipStats;
	}

	public void setIpStats(double ipStats) {
		this.ipStats = ipStats;
	}

    @Column(name = "wStats")
	public int getWStats() {
		return wStats;
	}

	public void setWStats(int wStats) {
		this.wStats = wStats;
	}

    @Column(name = "svStats")
	public int getSvStats() {
		return svStats;
	}

	public void setSvStats(int svStats) {
		this.svStats = svStats;
	}

    @Column(name = "kStats")
	public int getKStats() {
		return kStats;
	}

	public void setKStats(int kStats) {
		this.kStats = kStats;
	}

    @Column(name = "eraStats")
	public double getEraStats() {
		return eraStats;
	}

	public void setEraStats(double eraStats) {
		this.eraStats = eraStats;
	}

    @Column(name = "whipStats")
	public double getWhipStats() {
		return whipStats;
	}

	public void setWhipStats(double whipStats) {
		this.whipStats = whipStats;
	}

    @Column(name = "rPoints")
	public double getRPoints() {
		return rPoints;
	}

	public void setRPoints(double rPoints) {
		this.rPoints = rPoints;
	}

    @Column(name = "hrPoints")
	public double getHrPoints() {
		return hrPoints;
	}

	public void setHrPoints(double hrPoints) {
		this.hrPoints = hrPoints;
	}

    @Column(name = "rbiPoints")
	public double getRbiPoints() {
		return rbiPoints;
	}

	public void setRbiPoints(double rbiPoints) {
		this.rbiPoints = rbiPoints;
	}

    @Column(name = "sbPoints")
	public double getSbPoints() {
		return sbPoints;
	}

	public void setSbPoints(double sbPoints) {
		this.sbPoints = sbPoints;
	}

    @Column(name = "avgPoints")
	public double getAvgPoints() {
		return avgPoints;
	}

	public void setAvgPoints(double avgPoints) {
		this.avgPoints = avgPoints;
	}

    @Column(name = "wPoints")
	public double getWPoints() {
		return wPoints;
	}

	public void setWPoints(double wPoints) {
		this.wPoints = wPoints;
	}

    @Column(name = "svPoints")
	public double getSvPoints() {
		return svPoints;
	}

	public void setSvPoints(double svPoints) {
		this.svPoints = svPoints;
	}

    @Column(name = "kPoints")
	public double getKPoints() {
		return kPoints;
	}

	public void setKPoints(double kPoints) {
		this.kPoints = kPoints;
	}

    @Column(name = "eraPoints")
	public double getEraPoints() {
		return eraPoints;
	}

	public void setEraPoints(double eraPoints) {
		this.eraPoints = eraPoints;
	}

    @Column(name = "whipPoints")
	public double getWhipPoints() {
		return whipPoints;
	}

	public void setWhipPoints(double whipPoints) {
		this.whipPoints = whipPoints;
	}

    @Column(name = "rank")
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

    @Column(name = "totalPoints")
	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	@ManyToOne
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
