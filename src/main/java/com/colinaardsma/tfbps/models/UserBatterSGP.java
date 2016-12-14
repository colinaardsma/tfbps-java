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
@Table(name = "userbattersgp")
public class UserBatterSGP extends AbstractEntity {
	
	private double histSGP;
	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter batter;
	private YahooRotoLeague yahooRotoLeague;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	
	public UserBatterSGP(FPProjBatter batter, YahooRotoLeague yahooRotoLeague, User user) {
		this.batter = batter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = caclLeagueHistSGP(batter, yahooRotoLeague);
	}
	
	public UserBatterSGP(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.batter = batter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = caclLeagueHistSGP(batter, ottoneuOldSchoolLeague);
	}
	
	public UserBatterSGP() {}
	
    public double caclLeagueHistSGP(FPProjBatter batter, YahooRotoLeague yahooRotoLeague) {
    	BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(yahooRotoLeague.getRHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(yahooRotoLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(yahooRotoLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(yahooRotoLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	
    	// ops
    	BigDecimal ab = new BigDecimal(batter.getAb()).multiply(new BigDecimal(1.15));	
    	BigDecimal obpNum = new BigDecimal(batter.getObp()).multiply(ab).add(new BigDecimal(2178.8));
    	BigDecimal obpDenom = ab.add(new BigDecimal(6682));
    	BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal slgNum = new BigDecimal(batter.getSlg()).multiply(new BigDecimal(batter.getAb())).add(new BigDecimal(2528.5));
    	BigDecimal slgDenom = new BigDecimal(batter.getAb()).add(new BigDecimal(5993));
    	BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal ops = obp.divide(slg, 2, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.748));
    	ops = ops.divide(new BigDecimal(yahooRotoLeague.getOpsHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    			 	
    	return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
    }

    public double caclLeagueHistSGP(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
    	BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(ottoneuOldSchoolLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	
    	// avg
    	BigDecimal h = new BigDecimal(batter.getH()).add(new BigDecimal(1768));	
    	BigDecimal ab = new BigDecimal(batter.getAb()).add(new BigDecimal(6617));
    	BigDecimal avg = h.divide(ab).subtract(new BigDecimal(0.267));
    	avg = avg.divide(new BigDecimal(ottoneuOldSchoolLeague.getAvgHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    			 	
    	return r.add(hr.add(rbi.add(sb.add(avg)))).doubleValue();
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
	public YahooRotoLeague getYahooRotoLeague() {
		return yahooRotoLeague;
	}

	public void setYahooRotoLeague(YahooRotoLeague league) {
		this.yahooRotoLeague = league;
	}

	@ManyToOne
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}

}
