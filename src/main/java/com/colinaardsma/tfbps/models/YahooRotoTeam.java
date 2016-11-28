package com.colinaardsma.tfbps.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "yahoorototeam")
public class YahooRotoTeam extends AbstractEntity {

	// team identifiers
	private String teamKey;
	private int teamID;
	private String teamName;
	private String teamURL;
	private int teamFAABBalance;
	private int teamMoves;
	private int teamTrades;
	private String teamGUID; // add to user model and join
	private String userUID; // join to user model
	private String teamManagerName;
	private String leagueName;
	private String leagueKey;
	private String leagueUID; // join to YahooRotoLeague model
	
	// stats
	private String habStats; // stat_id = 60
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
	private String habPoints; // stat_id = 60
	private int rPoints; // stat_id = 7
	private int hrPoints; // stat_id = 12
	private int rbiPoints; // stat_id = 13
	private int sbPoints; // stat_id = 16
	private double avgPoints; // stat_id = 3
	private double opsPoints; // stat_id = 55
	private int ipPoints; // stat_id = 50
	private int wPoints; // stat_id = 28
	private int svPoints; // stat_id = 32
	private int kPoints; // stat_id = 42
	private double eraPoints; // stat_id = 26
	private double whipPoints; // stat_id = 27

	
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


}
