package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userbattergp")
public class UserBatterSGP extends AbstractEntity {
	
	private double histSGP;
	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter batter;
	private YahooRotoLeague league;
	
	public UserBatterSGP(FPProjBatter batter, YahooRotoLeague league, User user) {
		this.batter = batter;
		this.league = league;
		this.user = user;
		this.created = new Date();
		this.histSGP = caclLeagueHistSGP(batter, league);
	}
	
	public UserBatterSGP() {}
	
    public double caclLeagueHistSGP(FPProjBatter batter, YahooRotoLeague league) {
    	BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(league.getRHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(league.getHrHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(league.getRbiHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(league.getSbHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	
    	// ops
    	BigDecimal ab = new BigDecimal(batter.getAb()).multiply(new BigDecimal(1.15));	
    	BigDecimal obpNum = new BigDecimal(batter.getObp()).multiply(ab).add(new BigDecimal(2178.8));
    	BigDecimal obpDenom = ab.add(new BigDecimal(6682));
    	BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal slgNum = new BigDecimal(batter.getSlg()).multiply(new BigDecimal(batter.getAb())).add(new BigDecimal(2528.5));
    	BigDecimal slgDenom = new BigDecimal(batter.getAb()).add(new BigDecimal(5993));
    	BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal ops = obp.divide(slg, 2, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.748));
    	ops = ops.divide(new BigDecimal(league.getOpsHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    			 	
    	return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
    }

	@NotNull
    @Column(name = "SGP")
	public double getHistSGP() {
		return histSGP;
	}

	public void setHistSGP(double histSGP) {
		this.histSGP = histSGP;
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
	public YahooRotoLeague getLeague() {
		return league;
	}

	public void setLeague(YahooRotoLeague league) {
		this.league = league;
	}

}
