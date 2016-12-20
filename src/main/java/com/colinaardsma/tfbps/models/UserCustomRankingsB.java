package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UserCustomRankingsB")
public class UserCustomRankingsB extends AbstractEntity {
	
	private double histSGP;
	private double histAAV;
	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter batter;
	private YahooRotoLeague yahooRotoLeague;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	
	public UserCustomRankingsB(List<FPProjBatter> batterList, FPProjBatter batter, YahooRotoLeague yahooRotoLeague, User user) {
		this.batter = batter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(batter, yahooRotoLeague);
		this.histAAV = calcLeagueHistAAV(batterList, batter, yahooRotoLeague);
	}
	
	public UserCustomRankingsB(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.batter = batter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(batter, ottoneuOldSchoolLeague);
	}
	
	public UserCustomRankingsB() {}
	
	@NotNull
    @Column(name = "SGP")
	public double getHistSGP() {
		return histSGP;
	}

	public void setHistSGP(double histSGP) {
		this.histSGP = histSGP;
	}

    @Column(name = "AAV")
	public double getHistAAV() {
		return histAAV;
	}

	public void setHistAAV(double histAAV) {
		this.histAAV = histAAV;
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

	public void setYahooRotoLeague(YahooRotoLeague yahooRotoLeague) {
		this.yahooRotoLeague = yahooRotoLeague;
	}

	@ManyToOne
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}

    public double calcLeagueHistSGP(FPProjBatter batter, YahooRotoLeague yahooRotoLeague) {
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
    	BigDecimal ops = obp.divide(slg, 4, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.748));
    	ops = ops.divide(new BigDecimal(yahooRotoLeague.getOpsHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    			 	
    	return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
    }

    public double calcLeagueHistSGP(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
    	BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(ottoneuOldSchoolLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    	
    	// avg
    	BigDecimal h = new BigDecimal(batter.getH()).add(new BigDecimal(1768));	
    	BigDecimal ab = new BigDecimal(batter.getAb()).add(new BigDecimal(6617));
    	BigDecimal avg = h.divide(ab, 4, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.267));
    	avg = avg.divide(new BigDecimal(ottoneuOldSchoolLeague.getAvgHistSGPMult()), 4, RoundingMode.HALF_EVEN);
    			 	
    	return r.add(hr.add(rbi.add(sb.add(avg)))).doubleValue();
    }

    public double calcLeagueHistAAV(List<FPProjBatter> batterList, FPProjBatter batter, YahooRotoLeague yahooRotoLeague) {
    	
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(batterList, new Comparator<FPProjBatter>() {
			@Override
			public int compare(FPProjBatter b1, FPProjBatter b2) {
				if (b1.getOpsTotalSGP() < b2.getOpsTotalSGP()) return 1;
				if (b1.getOpsTotalSGP() > b2.getOpsTotalSGP()) return -1;
				return 0;
			}
		});

//    	BigDecimal totalBudgetB = new BigDecimal(yahooRotoLeague.getHistBudgetPctB()).multiply(new BigDecimal(yahooRotoLeague.getAuctionBudget()));
    	BigDecimal leagueBudgetB = new BigDecimal(yahooRotoLeague.getHistBudgetPctB()).multiply(new BigDecimal(yahooRotoLeague.getAuctionBudget()).multiply(new BigDecimal(yahooRotoLeague.getTeamCount())));
//    	BigDecimal bOverOneDollar = new BigDecimal(yahooRotoLeague.getHistDraftedB()).subtract(new BigDecimal(yahooRotoLeague.getHistOneDollarB()));
//    	BigDecimal dollarBatterSGP = new BigDecimal(batterList.get(bOverOneDollar.intValue()).getOpsTotalSGP());
    	
    	// need to sort list
    	    	
    	BigDecimal totalSGPb = new BigDecimal(0);
    	for (FPProjBatter b : batterList) {
    		totalSGPb = totalSGPb.add(new BigDecimal(b.getOpsTotalSGP()));
    	}
    	BigDecimal dollarPerSGP = leagueBudgetB.divide(totalSGPb, 4, RoundingMode.HALF_EVEN);
    	
    	BigDecimal aav = new BigDecimal(batter.getOpsTotalSGP()).multiply(dollarPerSGP);
    	return aav.doubleValue();
//    	BigDecimal sgpAboveZero = new BigDecimal(batter.getOpsTotalSGP()).subtract(dollarBatterSGP);
//    	
//    	
//    	for (int i = 0; i < yahooRotoLeague.getHistDraftedB(); i++) {
//    		aav = 0;
//    		if (batterList.get(i) == batter) {
//    			return aav;
//    		}
//    		if (i >= bOverOneDollar.intValue()) {
//    			aav = 1;
//    		}
//    	}
//    			 	
//    	return 0;
    }

}
