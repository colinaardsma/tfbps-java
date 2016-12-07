package com.colinaardsma.tfbps.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "yahoorotoleague")
public class YahooRotoLeague extends AbstractEntity {
	
	// league variables
	private String leagueKey;
	private String leagueName;
	private String leagueURL;
	private int teamCount;
	private int season;
	
	// links to other leagues within this database
	private String previousYearKey;
	private int previousYearUID;
	
	// team variables
	private int team1UID; // join to YahooRotoTeam model
	private int team1Rank;
	private int team2UID; // join to YahooRotoTeam model
	private int team2Rank;
	private int team3UID; // join to YahooRotoTeam model
	private int team3Rank;
	private int team4UID; // join to YahooRotoTeam model
	private int team4Rank;
	private int team5UID; // join to YahooRotoTeam model
	private int team5Rank;
	private int team6UID; // join to YahooRotoTeam model
	private int team6Rank;
	private int team7UID; // join to YahooRotoTeam model
	private int team7Rank;
	private int team8UID; // join to YahooRotoTeam model
	private int team8Rank;
	private int team9UID; // join to YahooRotoTeam model
	private int team9Rank;
	private int team10UID; // join to YahooRotoTeam model
	private int team10Rank;
	private int team11UID; // join to YahooRotoTeam model
	private int team11Rank;
	private int team12UID; // join to YahooRotoTeam model
	private int team12Rank;
	private int team13UID; // join to YahooRotoTeam model
	private int team13Rank;
	private int team14UID; // join to YahooRotoTeam model
	private int team14Rank;
	
//	private List<String> yahooGUIDs;
	
//	private YahooRotoLeagueMembership yahoorotoleaguememberships;
	
	private List<User> users;
	
	public YahooRotoLeague(String leagueKey, String leagueName, String leagueURL, int teamCount, int season) {
		this.leagueKey = leagueKey;
		this.leagueName = leagueName;
		this.leagueURL = leagueURL;
		this.teamCount = teamCount;
		this.season = season;
	}
	
	public YahooRotoLeague() {}

    @NotNull
    @Column(name = "leaguekey", unique = true)
	public String getLeagueKey() {
		return leagueKey;
	}

	public void setLeagueKey(String leagueKey) {
		this.leagueKey = leagueKey;
	}

    @NotNull
    @Column(name = "leaguename")
	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

    @NotNull
    @Column(name = "leagueURL", unique = true)
	public String getLeagueURL() {
		return leagueURL;
	}

	public void setLeagueURL(String leagueURL) {
		this.leagueURL = leagueURL;
	}

    @NotNull
    @Column(name = "teamcount")
	public int getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}

    @NotNull
    @Column(name = "season")
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

    @Column(name = "prevyearkey")
	public String getPreviousYearKey() {
		return previousYearKey;
	}

	public void setPreviousYearKey(String previousYearKey) {
		this.previousYearKey = previousYearKey;
	}

    @Column(name = "prevyearUID")
	public int getPreviousYearUID() {
		return previousYearUID;
	}

	public void setPreviousYearUID(int previousYearUID) {
		this.previousYearUID = previousYearUID;
	}

    @Column(name = "team1UID")
	public int getTeam1UID() {
		return team1UID;
	}

	public void setTeam1UID(int team1uid) {
		team1UID = team1uid;
	}

    @Column(name = "team1RANK")
	public int getTeam1Rank() {
		return team1Rank;
	}

	public void setTeam1Rank(int team1Rank) {
		this.team1Rank = team1Rank;
	}

    @Column(name = "team2UID")
	public int getTeam2UID() {
		return team2UID;
	}

	public void setTeam2UID(int team2uid) {
		team2UID = team2uid;
	}

    @Column(name = "team2RANK")
	public int getTeam2Rank() {
		return team2Rank;
	}

	public void setTeam2Rank(int team2Rank) {
		this.team2Rank = team2Rank;
	}

    @Column(name = "team3UID")
	public int getTeam3UID() {
		return team3UID;
	}

	public void setTeam3UID(int team3uid) {
		team3UID = team3uid;
	}

    @Column(name = "team3RANK")
	public int getTeam3Rank() {
		return team3Rank;
	}

	public void setTeam3Rank(int team3Rank) {
		this.team3Rank = team3Rank;
	}

    @Column(name = "team4UID")
	public int getTeam4UID() {
		return team4UID;
	}

	public void setTeam4UID(int team4uid) {
		team4UID = team4uid;
	}

    @Column(name = "team4RANK")
	public int getTeam4Rank() {
		return team4Rank;
	}

	public void setTeam4Rank(int team4Rank) {
		this.team4Rank = team4Rank;
	}

    @Column(name = "team5UID")
	public int getTeam5UID() {
		return team5UID;
	}

	public void setTeam5UID(int team5uid) {
		team5UID = team5uid;
	}

    @Column(name = "team5RANK")
	public int getTeam5Rank() {
		return team5Rank;
	}

	public void setTeam5Rank(int team5Rank) {
		this.team5Rank = team5Rank;
	}

    @Column(name = "team6UID")
	public int getTeam6UID() {
		return team6UID;
	}

	public void setTeam6UID(int team6uid) {
		team6UID = team6uid;
	}

    @Column(name = "team6RANK")
	public int getTeam6Rank() {
		return team6Rank;
	}

	public void setTeam6Rank(int team6Rank) {
		this.team6Rank = team6Rank;
	}

    @Column(name = "team7UID")
	public int getTeam7UID() {
		return team7UID;
	}

	public void setTeam7UID(int team7uid) {
		team7UID = team7uid;
	}

    @Column(name = "team7RANK")
	public int getTeam7Rank() {
		return team7Rank;
	}

	public void setTeam7Rank(int team7Rank) {
		this.team7Rank = team7Rank;
	}

    @Column(name = "team8UID")
	public int getTeam8UID() {
		return team8UID;
	}

	public void setTeam8UID(int team8uid) {
		team8UID = team8uid;
	}

    @Column(name = "team8RANK")
	public int getTeam8Rank() {
		return team8Rank;
	}

	public void setTeam8Rank(int team8Rank) {
		this.team8Rank = team8Rank;
	}

    @Column(name = "team9UID")
	public int getTeam9UID() {
		return team9UID;
	}

	public void setTeam9UID(int team9uid) {
		team9UID = team9uid;
	}

    @Column(name = "team9RANK")
	public int getTeam9Rank() {
		return team9Rank;
	}

	public void setTeam9Rank(int team9Rank) {
		this.team9Rank = team9Rank;
	}

    @Column(name = "team10UID")
	public int getTeam10UID() {
		return team10UID;
	}

	public void setTeam10UID(int team10uid) {
		team10UID = team10uid;
	}

    @Column(name = "team10RANK")
	public int getTeam10Rank() {
		return team10Rank;
	}

	public void setTeam10Rank(int team10Rank) {
		this.team10Rank = team10Rank;
	}

    @Column(name = "team11UID")
	public int getTeam11UID() {
		return team11UID;
	}

	public void setTeam11UID(int team11uid) {
		team11UID = team11uid;
	}

    @Column(name = "team11RANK")
	public int getTeam11Rank() {
		return team11Rank;
	}

	public void setTeam11Rank(int team11Rank) {
		this.team11Rank = team11Rank;
	}

    @Column(name = "team12UID")
	public int getTeam12UID() {
		return team12UID;
	}

	public void setTeam12UID(int team12uid) {
		team12UID = team12uid;
	}

    @Column(name = "team12RANK")
	public int getTeam12Rank() {
		return team12Rank;
	}

	public void setTeam12Rank(int team12Rank) {
		this.team12Rank = team12Rank;
	}

    @Column(name = "team13UID")
	public int getTeam13UID() {
		return team13UID;
	}

	public void setTeam13UID(int team13uid) {
		team13UID = team13uid;
	}

    @Column(name = "team13RANK")
	public int getTeam13Rank() {
		return team13Rank;
	}

	public void setTeam13Rank(int team13Rank) {
		this.team13Rank = team13Rank;
	}

    @Column(name = "team14UID")
	public int getTeam14UID() {
		return team14UID;
	}

	public void setTeam14UID(int team14uid) {
		team14UID = team14uid;
	}

    @Column(name = "team14RANK")
	public int getTeam14Rank() {
		return team14Rank;
	}

	public void setTeam14Rank(int team14Rank) {
		this.team14Rank = team14Rank;
	}
	
//    @Column(name = "yahooGUIDs")
//	public List<String> getYahooGUIDs() {
//		return yahooGUIDs;
//	}
//	
//	public void setYahooGUIDs(List<String> yahooGUIDs) {
//		this.yahooGUIDs = yahooGUIDs;
//	}
//	
//	@ManyToOne
//	public YahooRotoLeagueMembership getYahoorotoleaguememberships() {
//		return yahoorotoleaguememberships;
//	}
//	
//	public void setYahoorotoleaguememberships(YahooRotoLeagueMembership yahoorotoleaguememberships) {
//		this.yahoorotoleaguememberships = yahoorotoleaguememberships;
//	}
	
    @ManyToMany
    @JoinTable(name="USER_YAHOOROTOLEAGUES")
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
