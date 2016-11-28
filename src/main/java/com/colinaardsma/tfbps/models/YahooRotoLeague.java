package com.colinaardsma.tfbps.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "yahoorotoleague")
public class YahooRotoLeague extends AbstractEntity {
	
	// league variables
	private String leagueKey;
	private String leagueName;
	private String yqlURL;
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
	

}
