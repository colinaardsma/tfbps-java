package com.colinaardsma.tfbps.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "KeeperCosts")
public class KeeperCosts extends AbstractEntity {
	
	private int cost;
	private String playerKey;
	private Date created;
	
	// join variables
	private User user;
//	private List<FPProjBatter> batters;
//	private List<FPProjPitcher> pitchers;
	private FPProjBatter batter;
	private FPProjPitcher pitcher;
	private YahooRotoLeague yahooRotoLeague;
	private YahooRotoTeam yahooRotoTeam;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private OttoneuOldSchoolTeam ottoneuOldSchoolTeam;

	// TODO: change cost increment to league custom $
	// yahoo roto batter
	public KeeperCosts(String playerKey, FPProjBatter batter, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam) {
//		this.batters.add(batter);
		this.playerKey = playerKey;
		this.batter = batter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.cost = 5;
		this.created = new Date();
	}
	
	// yahoo roto pitcher
	public KeeperCosts(String playerKey, FPProjPitcher pitcher, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam) {
//		this.pitchers.add(pitcher);
		this.playerKey = playerKey;
		this.pitcher = pitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.cost = 5;
		this.created = new Date();
	}
	
	// yahoo roto player not in projections
	public KeeperCosts(String playerKey, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam) {
		this.playerKey = playerKey;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.cost = 5;
		this.created = new Date();
	}
	
// http://www.fangraphs.com/fantasy/auction-values-for-ottoneu-leagues/
	public KeeperCosts(String playerKey, FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam, int cost) {
//		this.batters.add(batter);
		this.playerKey = playerKey;
		this.batter = batter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.cost = cost;
		this.created = new Date();
	}
	
	public KeeperCosts(String playerKey, FPProjPitcher pitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam, int cost) {
//		this.pitchers.add(pitcher);
		this.playerKey = playerKey;
		this.pitcher = pitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.cost = cost;
		this.created = new Date();
	}
	
	public KeeperCosts() {}
	
    @Column(name = "cost")
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@NotNull
    @Column(name = "playerKey")
	public String getPlayerKey() {
		return playerKey;
	}

	public void setPlayerKey(String playerKey) {
		this.playerKey = playerKey;
	}

	@NotNull
    @Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public FPProjBatter getBatter() {
		return batter;
	}

	public void setBatter(FPProjBatter batter) {
		this.batter = batter;
	}

//    @ManyToMany
//    @JoinTable(name="BATTER_KEEPERCOSTS")
//	public List<FPProjBatter> getBatters() {
//		return batters;
//	}
//
//	public void setBatters(List<FPProjBatter> batters) {
//		this.batters = batters;
//	}

	@ManyToOne
	public FPProjPitcher getPitcher() {
		return pitcher;
	}

	public void setPitcher(FPProjPitcher pitcher) {
		this.pitcher = pitcher;
	}

//    @ManyToMany
//    @JoinTable(name="PITCHER_KEEPERCOSTS")
//	public List<FPProjPitcher> getPitchers() {
//		return pitchers;
//	}
//
//	public void setPitchers(List<FPProjPitcher> pitchers) {
//		this.pitchers = pitchers;
//	}

	@ManyToOne
	public YahooRotoLeague getYahooRotoLeague() {
		return yahooRotoLeague;
	}

	public void setYahooRotoLeague(YahooRotoLeague yahooRotoLeague) {
		this.yahooRotoLeague = yahooRotoLeague;
	}

	@ManyToOne
	public YahooRotoTeam getYahooRotoTeam() {
		return yahooRotoTeam;
	}

	public void setYahooRotoTeam(YahooRotoTeam yahooRotoTeam) {
		this.yahooRotoTeam = yahooRotoTeam;
	}

	@ManyToOne
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}

	@ManyToOne
    public OttoneuOldSchoolTeam getOttoneuOldSchoolTeam() {
		return ottoneuOldSchoolTeam;
	}

	public void setOttoneuOldSchoolTeam(OttoneuOldSchoolTeam ottoneuOldSchoolTeam) {
		this.ottoneuOldSchoolTeam = ottoneuOldSchoolTeam;
	}

}
