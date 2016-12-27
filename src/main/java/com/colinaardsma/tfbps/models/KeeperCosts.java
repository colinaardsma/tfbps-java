package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "KeeperCosts")
public class KeeperCosts extends AbstractEntity {
	
	private BigDecimal cost;
	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter batter;
	private FPProjPitcher pitcher;
	private YahooRotoLeague yahooRotoLeague;
	private YahooRotoTeam yahooRotoTeam;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private OttoneuOldSchoolTeam ottoneuOldSchoolTeam;

	public KeeperCosts(FPProjBatter batter, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam) {
		this.batter = batter;
		this.yahooRotoLeague = yahooRotoLeague;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.created = new Date();
	}
	
	public KeeperCosts(FPProjPitcher pitcher, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam) {
		this.pitcher = pitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.created = new Date();
	}
	
	public KeeperCosts(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam) {
		this.batter = batter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.created = new Date();
	}
	
	public KeeperCosts(FPProjPitcher pitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam) {
		this.pitcher = pitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.created = new Date();
	}
	
	public KeeperCosts() {}
	
    @Column(name = "cost")
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
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

	@ManyToOne
	public FPProjPitcher getPitcher() {
		return pitcher;
	}

	public void setPitcher(FPProjPitcher pitcher) {
		this.pitcher = pitcher;
	}

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
