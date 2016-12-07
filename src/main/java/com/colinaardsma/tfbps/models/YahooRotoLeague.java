package com.colinaardsma.tfbps.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	
	private List<User> users;
	private List<YahooRotoTeam> yahooRotoTeams;
	
	
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

    @ManyToMany
    @JoinTable(name="USER_YAHOOROTOLEAGUES")
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
    @OneToMany
	public List<YahooRotoTeam> getYahooRotoTeams() {
		return yahooRotoTeams;
	}
	
	public void setYahooRotoTeams(List<YahooRotoTeam> yahooRotoTeams) {
		this.yahooRotoTeams = yahooRotoTeams;
	}
	
}
