package com.fantasyspot.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UserCustomRankingsB")
public class UserCustomRankingsB extends AbstractEntity {
	
	private double histSGP;
	private BigDecimal histAAV;
	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter fpProjBatter;
	private SteamerProjBatter steamerProjBatter;
	private YahooRotoLeague yahooRotoLeague;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private KeeperCosts keeperCost;
	
	// YAHOO ROTO LEAGUE
	public UserCustomRankingsB(FPProjBatter fpProjBatter, YahooRotoLeague yahooRotoLeague, User user) {
		this.fpProjBatter = fpProjBatter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(fpProjBatter, yahooRotoLeague);
	}
	
	public UserCustomRankingsB(SteamerProjBatter steamerProjBatter, YahooRotoLeague yahooRotoLeague, User user) {
		this.steamerProjBatter = steamerProjBatter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(steamerProjBatter, yahooRotoLeague);
	}

	// OTTONEU OLD SCHOOL LEAGUE
	public UserCustomRankingsB(FPProjBatter fpProjBatter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.fpProjBatter = fpProjBatter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(fpProjBatter, ottoneuOldSchoolLeague);
	}
	
	public UserCustomRankingsB(SteamerProjBatter steamerProjBatter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.steamerProjBatter = steamerProjBatter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(steamerProjBatter, ottoneuOldSchoolLeague);
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
	public BigDecimal getHistAAV() {
		return histAAV;
	}

	public void setHistAAV(BigDecimal histAAV) {
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
	public FPProjBatter getFpProjBatter() {
		return fpProjBatter;
	}

	public void setFpProjBatter(FPProjBatter fpProjBatter) {
		this.fpProjBatter = fpProjBatter;
	}

	@ManyToOne
	public SteamerProjBatter getSteamerProjBatter() {
		return steamerProjBatter;
	}

	public void setSteamerProjBatter(SteamerProjBatter steamerProjBatter) {
		this.steamerProjBatter = steamerProjBatter;
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

	@OneToOne
	public KeeperCosts getKeeperCost() {
		return keeperCost;
	}

	public void setKeeperCost(KeeperCosts keeperCost) {
		this.keeperCost = keeperCost;
	}

	// YAHOO ROTO LEAGUE
	public double calcLeagueHistSGP(FPProjBatter batter, YahooRotoLeague yahooRotoLeague) {
		BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(yahooRotoLeague.getRHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(yahooRotoLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(yahooRotoLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(yahooRotoLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_UP);

		// ops
		BigDecimal ab = new BigDecimal(batter.getAb()).multiply(new BigDecimal(1.15));	
		BigDecimal obpNum = new BigDecimal(batter.getObp()).multiply(ab).add(new BigDecimal(2178.8));
		BigDecimal obpDenom = ab.add(new BigDecimal(6682));
		BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_UP);
		BigDecimal slgNum = new BigDecimal(batter.getSlg()).multiply(new BigDecimal(batter.getAb())).add(new BigDecimal(2528.5));
		BigDecimal slgDenom = new BigDecimal(batter.getAb()).add(new BigDecimal(5993));
		BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_UP);
		BigDecimal ops = obp.divide(slg, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(0.748));
		ops = ops.divide(new BigDecimal(yahooRotoLeague.getOpsHistSGPMult()), 4, RoundingMode.HALF_UP);

		return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
	}

	public double calcLeagueHistSGP(SteamerProjBatter batter, YahooRotoLeague yahooRotoLeague) {
		BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(yahooRotoLeague.getRHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(yahooRotoLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(yahooRotoLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(yahooRotoLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_UP);

		// ops
		BigDecimal ab = new BigDecimal(batter.getAb()).multiply(new BigDecimal(1.15));	
		BigDecimal obpNum = new BigDecimal(batter.getObp()).multiply(ab).add(new BigDecimal(2178.8));
		BigDecimal obpDenom = ab.add(new BigDecimal(6682));
		BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_UP);
		BigDecimal slgNum = new BigDecimal(batter.getSlg()).multiply(new BigDecimal(batter.getAb())).add(new BigDecimal(2528.5));
		BigDecimal slgDenom = new BigDecimal(batter.getAb()).add(new BigDecimal(5993));
		BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_UP);
		BigDecimal ops = obp.divide(slg, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(0.748));
		ops = ops.divide(new BigDecimal(yahooRotoLeague.getOpsHistSGPMult()), 4, RoundingMode.HALF_UP);

		return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
	}

	public void calcLeagueHistAAV(List<UserCustomRankingsB> batterList, YahooRotoLeague yahooRotoLeague) {

		// player pool
		int draftedB = (int) yahooRotoLeague.getHistDraftedB(); // historical average of total batters taken
		int oneDollarB = (int) yahooRotoLeague.getHistOneDollarB(); // historical average of total $1 batters taken
		int draftedBOverOneDollar = draftedB - oneDollarB; // historical average of total batters taken minus $1 batters taken

		// total league budget (with $1 players taken out)
		BigDecimal leagueBudgetOverOneB = new BigDecimal(yahooRotoLeague.getHistBudgetPctB()).multiply(new BigDecimal(yahooRotoLeague.getAuctionBudget()).multiply(new BigDecimal(yahooRotoLeague.getTeamCount()))).subtract(new BigDecimal(yahooRotoLeague.getHistOneDollarB()));

		// calculate average SGP value of $1 players
		BigDecimal oneDollarSGPSum = new BigDecimal(0);
		for (int i = draftedBOverOneDollar; i < draftedB; i++) {
			oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(batterList.get(i).getHistSGP()));
		}
		BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarB), 4, RoundingMode.HALF_UP);
    	
    	// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
    	BigDecimal totalSGPAboveOne = new BigDecimal(0);
    	for (int j = 0; j < draftedBOverOneDollar; j++) {
    		totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(batterList.get(j).getHistSGP()).subtract(oneDollarSGP));
    	}
    	
    	// calculate $ / SGP
    	BigDecimal dollarsPerSGP = leagueBudgetOverOneB.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);
    	
    	// check to see if this batter is a $1 batter, if so set value to $1
       	int counter = 0;
    	for (UserCustomRankingsB b : batterList) {
    		if (this.fpProjBatter == b.getFpProjBatter()) {
    			if (counter < draftedBOverOneDollar) {
    		    	this.histAAV = dollarsPerSGP.multiply(new BigDecimal(this.histSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else if (counter < draftedB && counter >= draftedBOverOneDollar) {
    				this.histAAV = new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else {
    				this.histAAV = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			}
    		}
    		counter++;
    	}
    }

	// OTTONEU OLD SCHOOL LEAGUE
	public double calcLeagueHistSGP(FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(ottoneuOldSchoolLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_UP);

		// avg
		BigDecimal h = new BigDecimal(batter.getH()).add(new BigDecimal(1768));	
		BigDecimal ab = new BigDecimal(batter.getAb()).add(new BigDecimal(6617));
		BigDecimal avg = h.divide(ab, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(0.267));
		avg = avg.divide(new BigDecimal(ottoneuOldSchoolLeague.getAvgHistSGPMult()), 4, RoundingMode.HALF_UP);

		return r.add(hr.add(rbi.add(sb.add(avg)))).doubleValue();
	}

	public double calcLeagueHistSGP(SteamerProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		BigDecimal r = new BigDecimal(batter.getR()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal hr = new BigDecimal(batter.getHr()).divide(new BigDecimal(ottoneuOldSchoolLeague.getHrHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal rbi = new BigDecimal(batter.getRbi()).divide(new BigDecimal(ottoneuOldSchoolLeague.getRbiHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sb = new BigDecimal(batter.getSb()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSbHistSGPMult()), 4, RoundingMode.HALF_UP);

		// avg
		BigDecimal h = new BigDecimal(batter.getH()).add(new BigDecimal(1768));	
		BigDecimal ab = new BigDecimal(batter.getAb()).add(new BigDecimal(6617));
		BigDecimal avg = h.divide(ab, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(0.267));
		avg = avg.divide(new BigDecimal(ottoneuOldSchoolLeague.getAvgHistSGPMult()), 4, RoundingMode.HALF_UP);

		return r.add(hr.add(rbi.add(sb.add(avg)))).doubleValue();
	}

    public void calcLeagueHistAAV(List<UserCustomRankingsB> batterList, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
    	
    	// player pool
    	int draftedB = (int) ottoneuOldSchoolLeague.getHistDraftedB(); // historical average of total batters taken
    	int oneDollarB = (int) ottoneuOldSchoolLeague.getHistOneDollarB(); // historical average of total $1 batters taken
    	int draftedBOverOneDollar = draftedB - oneDollarB; // historical average of total batters taken minus $1 batters taken
    	
    	// total league budget (with $1 players taken out)
    	BigDecimal leagueBudgetOverOneB = new BigDecimal(ottoneuOldSchoolLeague.getHistBudgetPctB()).multiply(new BigDecimal(ottoneuOldSchoolLeague.getHistTotalSpent())).subtract(new BigDecimal(ottoneuOldSchoolLeague.getHistOneDollarB()));

    	// calculate average SGP value of $1 players
    	BigDecimal oneDollarSGPSum = new BigDecimal(0);
    	for (int i = draftedBOverOneDollar; i < draftedB; i++) {
    		oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(batterList.get(i).getHistSGP()));
    	}
    	BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarB), 4, RoundingMode.HALF_UP);
    	
    	// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
    	BigDecimal totalSGPAboveOne = new BigDecimal(0);
    	for (int j = 0; j < draftedBOverOneDollar; j++) {
    		totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(batterList.get(j).getHistSGP()).subtract(oneDollarSGP));
    	}
    	
    	// calculate $ / SGP
    	BigDecimal dollarsPerSGP = leagueBudgetOverOneB.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);
    	
    	// check to see if this batter is a $1 batter, if so set value to $1
       	int counter = 0;
    	for (UserCustomRankingsB b : batterList) {
    		if (this.steamerProjBatter == b.getSteamerProjBatter()) {
    			if (counter < draftedBOverOneDollar) {
    		    	this.histAAV = dollarsPerSGP.multiply(new BigDecimal(this.histSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else if (counter < draftedB && counter >= draftedBOverOneDollar) {
    				this.histAAV = new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else {
    				this.histAAV = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			}
    		}
    		counter++;
    	}
    }

}
