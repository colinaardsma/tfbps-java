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
@Table(name = "userpitchersgp")
public class UserPitcherSGP extends AbstractEntity {
	
	private double histSGP;
	private Date created;
	
	// join variables
	private User user;
	private FPProjPitcher pitcher;
	private YahooRotoLeague league;
	
	public UserPitcherSGP(FPProjPitcher pitcher, YahooRotoLeague league, User user) {
		this.pitcher = pitcher;
		this.league = league;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(pitcher, league);
	}
	
	public UserPitcherSGP() {}
	
	public double calcLeagueHistSGP(FPProjPitcher pitcher, YahooRotoLeague league) {
	  BigDecimal w = new BigDecimal(pitcher.getW()).divide(new BigDecimal(league.getWHistSGPMult()), 4, RoundingMode.HALF_EVEN);
	  BigDecimal sv = new BigDecimal(pitcher.getSv()).divide(new BigDecimal(league.getSvHistSGPMult()), 4, RoundingMode.HALF_EVEN);
	  BigDecimal k = new BigDecimal(pitcher.getK()).divide(new BigDecimal(league.getKHistSGPMult()), 4, RoundingMode.HALF_EVEN);

	  // era
	  BigDecimal er = new BigDecimal(475).add(new BigDecimal(pitcher.getEr()));
	  BigDecimal eraNum = er.multiply(new BigDecimal(9));
	  BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(pitcher.getIp()));
	  BigDecimal eraDenom = ip;
	  BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_EVEN).subtract(new BigDecimal(3.59)).divide(new BigDecimal(league.getEraHistSGPMult()), 4, RoundingMode.HALF_EVEN);

	  // whip
	  BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(pitcher.getH())).add(new BigDecimal(pitcher.getBb()));
	  BigDecimal whipDenom = ip;
	  BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_EVEN).subtract(new BigDecimal(1.23)).divide(new BigDecimal(league.getWhipHistSGPMult()), 4, RoundingMode.HALF_EVEN);

	  return w.add(sv.add(k.add(era.add(whip)))).doubleValue();
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

    @SuppressWarnings("unused")
	private void setCreated(Date created) {
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
	public FPProjPitcher getPitcher() {
		return pitcher;
	}

	public void setPitcher(FPProjPitcher pitcher) {
		this.pitcher = pitcher;
	}

	@ManyToOne
	public YahooRotoLeague getLeague() {
		return league;
	}

	public void setLeague(YahooRotoLeague league) {
		this.league = league;
	}

}
