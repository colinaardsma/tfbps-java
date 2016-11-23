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
	
	// team variables
	private String team1Key;
	private int team1ID;
	private String team1Name;
	private String team1URL;
	
	// add to user model and join
	private String team1GUID;
	private String team1ManagerName;
	
	
	

}
