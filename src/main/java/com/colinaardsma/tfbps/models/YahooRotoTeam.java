package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "yahoorototeam")
public class YahooRotoTeam extends AbstractEntity {

	// team identifiers
	private String teamKey;
	private String teamName;
	private String teamURL;
	private int teamFAABBalance;
	private int teamMoves;
	private int teamTrades;
	private String teamGUID; // add to user model and join
//	private String userUID; // join to user model
	private String teamManagerName;
//	private String leagueName;
	private String leagueKey;
//	private String leagueUID; // join to YahooRotoLeague model
	private int rank;
	private double totalPoints;
	
	// stats
	private double habStats; // stat_id = 60
	private int rStats; // stat_id = 7
	private int hrStats; // stat_id = 12
	private int rbiStats; // stat_id = 13
	private int sbStats; // stat_id = 16
	private double avgStats; // stat_id = 3
	private double opsStats; // stat_id = 55
	private int ipStats; // stat_id = 50
	private int wStats; // stat_id = 28
	private int svStats; // stat_id = 32
	private int kStats; // stat_id = 42
	private double eraStats; // stat_id = 26
	private double whipStats; // stat_id = 27

	// points
	private double habPoints; // stat_id = 60
	private double rPoints; // stat_id = 7
	private double hrPoints; // stat_id = 12
	private double rbiPoints; // stat_id = 13
	private double sbPoints; // stat_id = 16
	private double avgPoints; // stat_id = 3
	private double opsPoints; // stat_id = 55
	private double ipPoints; // stat_id = 50
	private double wPoints; // stat_id = 28
	private double svPoints; // stat_id = 32
	private double kPoints; // stat_id = 42
	private double eraPoints; // stat_id = 26
	private double whipPoints; // stat_id = 27
	
	// join variables
	private YahooRotoLeague yahooRotoLeague;
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
	
	public YahooRotoTeam(String teamKey, String teamName, String teamURL, int teamFAABBalance, int teamMoves, int teamTrades, 
			String teamGUID, String teamManagerName,	String leagueKey, double habStats, int rStats, int hrStats, int rbiStats, 
			int sbStats, double avgStats, double opsStats, int ipStats, int wStats, int svStats, int kStats, double eraStats, 
			double whipStats, double habPoints, double rPoints, double hrPoints, double rbiPoints, double sbPoints,	double avgPoints, 
			double opsPoints, double ipPoints, double wPoints, double svPoints, double kPoints, double eraPoints, double whipPoints, 
			int rank, double totalPoints) {

		this.teamKey = teamKey;
		this.teamName = teamName;
		this.teamURL = teamURL;
		this.teamFAABBalance = teamFAABBalance;
		this.teamMoves = teamMoves;
		this.teamTrades = teamTrades;
		this.teamGUID = teamGUID;
//		this.userUID = userUID;
		this.teamManagerName = teamManagerName;
//		this.leagueName = leagueName;
		this.leagueKey = leagueKey;
//		this.leagueUID = leagueUID;
		this.habStats = habStats;
		this.rStats = rStats;
		this.hrStats = hrStats;
		this.rbiStats = rbiStats;
		this.sbStats = sbStats;
		this.avgStats = avgStats;
		this.opsStats = opsStats;
		this.ipStats = ipStats;
		this.wStats = wStats;
		this.svStats = svStats;
		this.kStats = kStats;
		this.eraStats = eraStats;
		this.whipStats = whipStats;
		this.habPoints = habPoints;
		this.rPoints = rPoints;
		this.hrPoints = hrPoints;
		this.rbiPoints = rbiPoints;
		this.sbPoints = sbPoints;
		this.avgPoints = avgPoints;
		this.opsPoints = opsPoints;
		this.ipPoints = ipPoints;
		this.wPoints = wPoints;
		this.svPoints = svPoints;
		this.kPoints = kPoints;
		this.eraPoints = eraPoints;
		this.whipPoints = whipPoints;
		this.rank = rank;
		this.totalPoints = totalPoints;
	}
	
	public YahooRotoTeam(String teamKey, String teamName, String teamURL, int teamFAABBalance, int teamMoves, int teamTrades, String teamGUID, String teamManagerName,	String leagueKey, int rank, double totalPoints) {
		this.teamKey = teamKey;
		this.teamName = teamName;
		this.teamURL = teamURL;
		this.teamFAABBalance = teamFAABBalance;
		this.teamMoves = teamMoves;
		this.teamTrades = teamTrades;
		this.teamGUID = teamGUID;
//		this.userUID = userUID;
		this.teamManagerName = teamManagerName;
//		this.leagueName = leagueName;
		this.leagueKey = leagueKey;
//		this.leagueUID = leagueUID;
		this.rank = rank;
		this.totalPoints = totalPoints;
	}

	public YahooRotoTeam(){}

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

    @Column(name = "teamFAABBalance")
	public int getTeamFAABBalance() {
		return teamFAABBalance;
	}

	public void setTeamFAABBalance(int teamFAABBalance) {
		this.teamFAABBalance = teamFAABBalance;
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

    @Column(name = "teamGUID")
	public String getTeamGUID() {
		return teamGUID;
	}

	public void setTeamGUID(String teamGUID) {
		this.teamGUID = teamGUID;
	}

//    @Column(name = "userUID")
//	public String getUserUID() {
//		return userUID;
//	}
//
//	public void setUserUID(String userUID) {
//		this.userUID = userUID;
//	}
//
    @Column(name = "teamManagerName")
	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

//    @Column(name = "leageName")
//	public String getLeagueName() {
//		return leagueName;
//	}
//
//	public void setLeagueName(String leagueName) {
//		this.leagueName = leagueName;
//	}

    @Column(name = "leagueKey")
	public String getLeagueKey() {
		return leagueKey;
	}

	public void setLeagueKey(String leagueKey) {
		this.leagueKey = leagueKey;
	}

//    @Column(name = "leaugeUID")
//	public String getLeagueUID() {
//		return leagueUID;
//	}
//
//	public void setLeagueUID(String leagueUID) {
//		this.leagueUID = leagueUID;
//	}

    @Column(name = "habStats")
	public double getHabStats() {
		return habStats;
	}

	public void setHabStats(double habStats) {
		this.habStats = habStats;
	}

    @Column(name = "rStats")
	public int getrStats() {
		return rStats;
	}

	public void setrStats(int rStats) {
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

    @Column(name = "opsStats")
	public double getOpsStats() {
		return opsStats;
	}

	public void setOpsStats(double opsStats) {
		this.opsStats = opsStats;
	}

    @Column(name = "ipStats")
	public int getIpStats() {
		return ipStats;
	}

	public void setIpStats(int ipStats) {
		this.ipStats = ipStats;
	}

    @Column(name = "wStats")
	public int getwStats() {
		return wStats;
	}

	public void setwStats(int wStats) {
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
	public int getkStats() {
		return kStats;
	}

	public void setkStats(int kStats) {
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

    @Column(name = "habPoints")
	public double getHabPoints() {
		return habPoints;
	}

	public void setHabPoints(double habPoints) {
		this.habPoints = habPoints;
	}

    @Column(name = "rPoints")
	public double getrPoints() {
		return rPoints;
	}

	public void setrPoints(double rPoints) {
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

    @Column(name = "opsPoints")
	public double getOpsPoints() {
		return opsPoints;
	}

	public void setOpsPoints(double opsPoints) {
		this.opsPoints = opsPoints;
	}

    @Column(name = "ipPoints")
	public double getIpPoints() {
		return ipPoints;
	}

	public void setIpPoints(double ipPoints) {
		this.ipPoints = ipPoints;
	}

    @Column(name = "wPoints")
	public double getwPoints() {
		return wPoints;
	}

	public void setwPoints(double wPoints) {
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
	public double getkPoints() {
		return kPoints;
	}

	public void setkPoints(double kPoints) {
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
	public YahooRotoLeague getYahooRotoLeague() {
		return yahooRotoLeague;
	}

	public void setYahooRotoLeague(YahooRotoLeague yahooRotoLeague) {
		this.yahooRotoLeague = yahooRotoLeague;
	}
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
