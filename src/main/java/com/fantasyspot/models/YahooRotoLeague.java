package com.fantasyspot.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "yahoorotoleague")
public class YahooRotoLeague extends AbstractEntity {
	
	// league variables
	private String leagueKey;
	private String leagueName;
	private String leagueURL;
	private int teamCount;
	private int season;
	private int auctionBudget;
	private int teamBatters;
	private int teamPitchers;
	private int teamBench;
	private int teamRosterSize;
	private Calendar draftDate = Calendar.getInstance();
	
	// yearly sgp variables
	private double rSGPMult;
	private double hrSGPMult;
	private double rbiSGPMult;
	private double sbSGPMult;
	private double opsSGPMult;
	private double wSGPMult;
	private double svSGPMult;
	private double kSGPMult;
	private double eraSGPMult;
	private double whipSGPMult;
	
	// yearly auction value variables
	private double budgetPctB;
	private double budgetPctP;
	private int totalSpent;
	private int oneDollarB;
	private int oneDollarP;
	
	// yearly draft/auction variables
	private int draftedB;
	private int draftedP;
	private int costPickIncrement;
	private int faCostPick;
	
	// historical sgp variables
	private double rHistSGPMult;
	private double hrHistSGPMult;
	private double rbiHistSGPMult;
	private double sbHistSGPMult;
	private double opsHistSGPMult;
	private double wHistSGPMult;
	private double svHistSGPMult;
	private double kHistSGPMult;
	private double eraHistSGPMult;
	private double whipHistSGPMult;
	
	// historical auction value variables
	private double histBudgetPctB;
	private double histBudgetPctP;
	private double histDraftedB;
	private double histDraftedP;
	private double histTotalSpent;
	private double histOneDollarB;
	private double histOneDollarP;
	
	// links to other leagues within this database
	private String previousYearKey;
	private int previousYearUID;
	
	// join variables
	private List<User> users;
	private List<YahooRotoTeam> yahooRotoTeams;
	private List<UserCustomRankingsB> userBatterSGP;
    private List<UserCustomRankingsP> userPitcherSGP;
    private List<KeeperCosts> keeperCosts;
	
	public YahooRotoLeague(String leagueKey, String leagueName, String leagueURL, int teamCount, int season) {
		this.leagueKey = leagueKey;
		this.leagueName = leagueName;
		this.leagueURL = leagueURL;
		this.teamCount = teamCount;
		this.season = season;
		this.costPickIncrement = 5;
		this.faCostPick = 5;
	}
	
	public YahooRotoLeague() {}

    @NotNull
    @Column(name = "leaguekey", unique = true)
	public String getLeagueKey() {
		return leagueKey;
	}

	public void setLeagueKey(String leagueKey) {
		this.leagueKey = leagueKey;
	}

    @NotNull
    @Column(name = "leaguename")
	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

    @NotNull
    @Column(name = "leagueURL", unique = true)
	public String getLeagueURL() {
		return leagueURL;
	}

	public void setLeagueURL(String leagueURL) {
		this.leagueURL = leagueURL;
	}

    @NotNull
    @Column(name = "teamcount")
	public int getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}

    @NotNull
    @Column(name = "season")
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}
	
    @Column(name = "auctionbudget")
    public int getAuctionBudget() {
		return auctionBudget;
	}

	public void setAuctionBudget(int auctionBudget) {
		this.auctionBudget = auctionBudget;
	}

    @Column(name = "teambatters")
	public int getTeamBatters() {
		return teamBatters;
	}

	public void setTeamBatters(int teamBatters) {
		this.teamBatters = teamBatters;
	}

    @Column(name = "teampitchers")
	public int getTeamPitchers() {
		return teamPitchers;
	}

	public void setTeamPitchers(int teamPitchers) {
		this.teamPitchers = teamPitchers;
	}

    @Column(name = "teambench")
	public int getTeamBench() {
		return teamBench;
	}

	public void setTeamBench(int teamBench) {
		this.teamBench = teamBench;
	}

    @Column(name = "teamrostersize")
	public int getTeamRosterSize() {
		return teamRosterSize;
	}

	public void setTeamRosterSize(int teamRosterSize) {
		this.teamRosterSize = teamRosterSize;
	}

    @Column(name = "draftDate")
	public Calendar getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Calendar draftDate) {
		this.draftDate = draftDate;
	}

	@Column(name = "rSGPMult")
    public double getRSGPMult() {
		return rSGPMult;
	}

	public void setRSGPMult(double rSGP) {
		this.rSGPMult = rSGP;
	}

    @Column(name = "hrSGPMult")
	public double getHrSGPMult() {
		return hrSGPMult;
	}

	public void setHrSGPMult(double hrSGP) {
		this.hrSGPMult = hrSGP;
	}

    @Column(name = "rbiSGPMult")
	public double getRbiSGPMult() {
		return rbiSGPMult;
	}

	public void setRbiSGPMult(double rbiSGP) {
		this.rbiSGPMult = rbiSGP;
	}

    @Column(name = "sbSGPMult")
	public double getSbSGPMult() {
		return sbSGPMult;
	}

	public void setSbSGPMult(double sbSGP) {
		this.sbSGPMult = sbSGP;
	}

    @Column(name = "opsSGPMult")
	public double getOpsSGPMult() {
		return opsSGPMult;
	}

	public void setOpsSGPMult(double opsSGP) {
		this.opsSGPMult = opsSGP;
	}

    @Column(name = "wSGPMult")
	public double getWSGPMult() {
		return wSGPMult;
	}

	public void setWSGPMult(double wSGP) {
		this.wSGPMult = wSGP;
	}

    @Column(name = "svSGPMult")
	public double getSvSGPMult() {
		return svSGPMult;
	}

	public void setSvSGPMult(double svSGP) {
		this.svSGPMult = svSGP;
	}

    @Column(name = "kSGPMult")
	public double getKSGPMult() {
		return kSGPMult;
	}

	public void setKSGPMult(double kSGP) {
		this.kSGPMult = kSGP;
	}

    @Column(name = "eraSGPMult")
	public double getEraSGPMult() {
		return eraSGPMult;
	}

	public void setEraSGPMult(double eraSGP) {
		this.eraSGPMult = eraSGP;
	}

    @Column(name = "whipSGPMult")
	public double getWhipSGPMult() {
		return whipSGPMult;
	}

	public void setWhipSGPMult(double whipSGP) {
		this.whipSGPMult = whipSGP;
	}

    @Column(name = "budgetPctB")
    public double getBudgetPctB() {
		return budgetPctB;
	}

	public void setBudgetPctB(double budgetPctB) {
		this.budgetPctB = budgetPctB;
	}

    @Column(name = "budgetPctP")
	public double getBudgetPctP() {
		return budgetPctP;
	}

	public void setBudgetPctP(double budgetPctP) {
		this.budgetPctP = budgetPctP;
	}

    @Column(name = "draftedB")
	public int getDraftedB() {
		return draftedB;
	}

	public void setDraftedB(int draftedB) {
		this.draftedB = draftedB;
	}

    @Column(name = "draftedP")
	public int getDraftedP() {
		return draftedP;
	}

	public void setDraftedP(int draftedP) {
		this.draftedP = draftedP;
	}

    @Column(name = "totalSpent")
	public int getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(int totalSpent) {
		this.totalSpent = totalSpent;
	}

	@Column(name = "oneDollarB")
	public int getOneDollarB() {
		return oneDollarB;
	}

	public void setOneDollarB(int oneDollarB) {
		this.oneDollarB = oneDollarB;
	}

	@Column(name = "oneDollarP")
	public int getOneDollarP() {
		return oneDollarP;
	}

	public void setOneDollarP(int oneDollarP) {
		this.oneDollarP = oneDollarP;
	}

	@Column(name = "costPickIncrement")
	public int getCostPickIncrement() {
		return costPickIncrement;
	}

	public void setCostPickIncrement(int costPickIncrement) {
		this.costPickIncrement = costPickIncrement;
	}

	@Column(name = "faCostPick")
	public int getFaCostPick() {
		return faCostPick;
	}

	public void setFaCostPick(int faCostPick) {
		this.faCostPick = faCostPick;
	}

	@Column(name = "rHistSGPMult")
	public double getRHistSGPMult() {
		return rHistSGPMult;
	}

	public void setRHistSGPMult(double rHistSGPMult) {
		this.rHistSGPMult = rHistSGPMult;
	}

    @Column(name = "HrHistSGPMult")
	public double getHrHistSGPMult() {
		return hrHistSGPMult;
	}

	public void setHrHistSGPMult(double hrHistSGPMult) {
		this.hrHistSGPMult = hrHistSGPMult;
	}

    @Column(name = "RbiHistSGPMult")
	public double getRbiHistSGPMult() {
		return rbiHistSGPMult;
	}

	public void setRbiHistSGPMult(double rbiHistSGPMult) {
		this.rbiHistSGPMult = rbiHistSGPMult;
	}

    @Column(name = "SbHistSGPMult")
	public double getSbHistSGPMult() {
		return sbHistSGPMult;
	}

	public void setSbHistSGPMult(double sbHistSGPMult) {
		this.sbHistSGPMult = sbHistSGPMult;
	}

    @Column(name = "OpsHistSGPMult")
	public double getOpsHistSGPMult() {
		return opsHistSGPMult;
	}

	public void setOpsHistSGPMult(double opsHistSGPMult) {
		this.opsHistSGPMult = opsHistSGPMult;
	}

    @Column(name = "wHistSGPMult")
	public double getWHistSGPMult() {
		return wHistSGPMult;
	}

	public void setWHistSGPMult(double wHistSGPMult) {
		this.wHistSGPMult = wHistSGPMult;
	}

    @Column(name = "svHistSGPMult")
	public double getSvHistSGPMult() {
		return svHistSGPMult;
	}

	public void setSvHistSGPMult(double svHistSGPMult) {
		this.svHistSGPMult = svHistSGPMult;
	}

    @Column(name = "kHistSGPMult")
	public double getKHistSGPMult() {
		return kHistSGPMult;
	}

	public void setKHistSGPMult(double kHistSGPMult) {
		this.kHistSGPMult = kHistSGPMult;
	}

    @Column(name = "eraHistSGPMult")
	public double getEraHistSGPMult() {
		return eraHistSGPMult;
	}

	public void setEraHistSGPMult(double eraHistSGPMult) {
		this.eraHistSGPMult = eraHistSGPMult;
	}

    @Column(name = "whipHistSGPMult")
	public double getWhipHistSGPMult() {
		return whipHistSGPMult;
	}

	public void setWhipHistSGPMult(double whipHistSGPMult) {
		this.whipHistSGPMult = whipHistSGPMult;
	}

    @Column(name = "histBudgetPctB")
	public double getHistBudgetPctB() {
		return histBudgetPctB;
	}

	public void setHistBudgetPctB(double histBudgetPctB) {
		this.histBudgetPctB = histBudgetPctB;
	}

    @Column(name = "histBudgetPctP")
	public double getHistBudgetPctP() {
		return histBudgetPctP;
	}

	public void setHistBudgetPctP(double histBudgetPctP) {
		this.histBudgetPctP = histBudgetPctP;
	}

    @Column(name = "histDraftedB")
	public double getHistDraftedB() {
		return histDraftedB;
	}

	public void setHistDraftedB(double histDraftedB) {
		this.histDraftedB = histDraftedB;
	}

    @Column(name = "histDraftedP")
	public double getHistDraftedP() {
		return histDraftedP;
	}

	public void setHistDraftedP(double histDraftedP) {
		this.histDraftedP = histDraftedP;
	}

   @Column(name = "histTotalSpent")
	public double getHistTotalSpent() {
		return histTotalSpent;
	}

	public void setHistTotalSpent(double histTotalSpent) {
		this.histTotalSpent = histTotalSpent;
	}

	@Column(name = "histOneDollarB")
	public double getHistOneDollarB() {
		return histOneDollarB;
	}

	public void setHistOneDollarB(double histOneDollarB) {
		this.histOneDollarB = histOneDollarB;
	}

	@Column(name = "histOneDollarP")
	public double getHistOneDollarP() {
		return histOneDollarP;
	}

	public void setHistOneDollarP(double histOneDollarP) {
		this.histOneDollarP = histOneDollarP;
	}

	@Column(name = "prevyearkey")
	public String getPreviousYearKey() {
		return previousYearKey;
	}

	public void setPreviousYearKey(String previousYearKey) {
		this.previousYearKey = previousYearKey;
	}

    @Column(name = "prevyearUID")
	public int getPreviousYearUID() {
		return previousYearUID;
	}

	public void setPreviousYearUID(int previousYearUID) {
		this.previousYearUID = previousYearUID;
	}

    @ManyToMany
    @JoinTable(name="USER_YAHOOROTOLEAGUES")
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
    @OneToMany
    @JoinColumn(name = "yahoo_roto_league_uid")
	public List<YahooRotoTeam> getYahooRotoTeams() {
		return yahooRotoTeams;
	}
	
	public void setYahooRotoTeams(List<YahooRotoTeam> yahooRotoTeams) {
		this.yahooRotoTeams = yahooRotoTeams;
	}

    @OneToMany
    @JoinColumn(name = "yahoo_roto_league_uid")
    public List<UserCustomRankingsB> getUserBatterSGP() {
    	return userBatterSGP;
    }
    
    @SuppressWarnings("unused")
   private void setUserBatterSGP(List<UserCustomRankingsB> userBatterSGP) {
    	this.userBatterSGP = userBatterSGP;
    }
    
    @OneToMany
    @JoinColumn(name = "yahoo_roto_league_uid")
    public List<UserCustomRankingsP> getUserPitcherSGP() {
    	return userPitcherSGP;
    }
    
    @SuppressWarnings("unused")
    private void setUserPitcherSGP(List<UserCustomRankingsP> userPitcherSGP) {
    	this.userPitcherSGP = userPitcherSGP;
    }

    @OneToMany
    @JoinColumn(name = "yahoo_roto_league_uid")
    public List<KeeperCosts> getKeeperCosts() {
    	return keeperCosts;
    }
    
    @SuppressWarnings("unused")
    private void setKeeperCosts(List<KeeperCosts> keeperCosts) {
    	this.keeperCosts = keeperCosts;
    }

	// calculates historical sgp for league (number of years is based on size of list provided)
	public void calcHistSGPs(List<YahooRotoLeague> leagues) {
		
		// BigDecimal variables used since double is not accurate when dividing small decimals
		BigDecimal rSum = new BigDecimal(0);
		BigDecimal hrSum = new BigDecimal(0);
		BigDecimal rbiSum = new BigDecimal(0);
		BigDecimal sbSum = new BigDecimal(0);
		BigDecimal opsSum = new BigDecimal(0);
		BigDecimal wSum = new BigDecimal(0);
		BigDecimal svSum = new BigDecimal(0);
		BigDecimal kSum = new BigDecimal(0);
		BigDecimal eraSum = new BigDecimal(0);
		BigDecimal whipSum = new BigDecimal(0);
		
		// loop through list and pull out relevant data
		for (YahooRotoLeague league : leagues) {
			rSum = rSum.add(BigDecimal.valueOf(league.getRSGPMult()));
			hrSum = hrSum.add(BigDecimal.valueOf(league.getHrSGPMult()));
			rbiSum = rbiSum.add(BigDecimal.valueOf(league.getRbiSGPMult()));
			sbSum = sbSum.add(BigDecimal.valueOf(league.getSbSGPMult()));
			opsSum = opsSum.add(BigDecimal.valueOf(league.getOpsSGPMult()));
			wSum = wSum.add(BigDecimal.valueOf(league.getWSGPMult()));
			svSum = svSum.add(BigDecimal.valueOf(league.getSvSGPMult()));
			kSum = kSum.add(BigDecimal.valueOf(league.getKSGPMult()));
			eraSum = eraSum.add(BigDecimal.valueOf(league.getEraSGPMult()));
			whipSum = whipSum.add(BigDecimal.valueOf(league.getWhipSGPMult()));
		}
		
		// calculate averages and set to respective variables
		BigDecimal rAvg = rSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.rHistSGPMult = Double.parseDouble(rAvg.toString());	
		BigDecimal hrAvg = hrSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.hrHistSGPMult = Double.parseDouble(hrAvg.toString());	
		BigDecimal rbiAvg = rbiSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.rbiHistSGPMult = Double.parseDouble(rbiAvg.toString());	
		BigDecimal sbAvg = sbSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.sbHistSGPMult = Double.parseDouble(sbAvg.toString());	
		BigDecimal opsAvg = opsSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.opsHistSGPMult = Double.parseDouble(opsAvg.toString());	
		BigDecimal wAvg = wSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.wHistSGPMult = Double.parseDouble(wAvg.toString());	
		BigDecimal svAvg = svSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.svHistSGPMult = Double.parseDouble(svAvg.toString());	
		BigDecimal kAvg = kSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.kHistSGPMult = Double.parseDouble(kAvg.toString());	
		BigDecimal eraAvg = eraSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.eraHistSGPMult = Double.parseDouble(eraAvg.toString());	
		BigDecimal whipAvg = whipSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.whipHistSGPMult = Double.parseDouble(whipAvg.toString());	
		
	}

	// calculates historical aav for league (number of years is based on size of list provided)
	public void calcHistAAVs(List<YahooRotoLeague> leagues) {
		
		// BigDecimal variables used since double is not accurate when dividing small decimals
		BigDecimal budgetPctBSum = new BigDecimal(0);
		BigDecimal budgetPctPSum = new BigDecimal(0);
		BigDecimal draftedBSum = new BigDecimal(0);
		BigDecimal draftedPSum = new BigDecimal(0);
		BigDecimal totalSpentSum = new BigDecimal(0);
		
		// loop through list and pull out relevant data
		int counter = 0;
		for (YahooRotoLeague league : leagues) {
			if (league.getAuctionBudget() > 0) {
				budgetPctBSum = budgetPctBSum.add(BigDecimal.valueOf(league.getBudgetPctB()));
				budgetPctPSum = budgetPctPSum.add(BigDecimal.valueOf(league.getBudgetPctP()));
				draftedBSum = draftedBSum.add(BigDecimal.valueOf(league.getDraftedB()));
				draftedPSum = draftedPSum.add(BigDecimal.valueOf(league.getDraftedP()));
				totalSpentSum = totalSpentSum.add(BigDecimal.valueOf(league.getTotalSpent()));
				counter++;
			}
		}
		
		// calculate averages and set to respective variables
		if (counter > 0) {
			BigDecimal budgetPctBAvg = budgetPctBSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histBudgetPctB = Double.parseDouble(budgetPctBAvg.toString());	
			BigDecimal budgetPctPAvg = budgetPctPSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histBudgetPctP = Double.parseDouble(budgetPctPAvg.toString());	
			BigDecimal draftedBAvg = draftedBSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histDraftedB = Double.parseDouble(draftedBAvg.toString());	
			BigDecimal draftedPAvg = draftedPSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histDraftedP = Double.parseDouble(draftedPAvg.toString());	
			BigDecimal totalSpentAvg = totalSpentSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histTotalSpent = Double.parseDouble(totalSpentAvg.toString());	
		}
	}

	// calculates historical dollar players for league (number of years is based on size of list provided)
	public void calcHistDollarPlayers(List<YahooRotoLeague> leagues) {
		
		// BigDecimal variables used since double is not accurate when dividing small decimals
		BigDecimal dollarBSum = new BigDecimal(0);
		BigDecimal dollarPSum = new BigDecimal(0);
		
		// loop through list and pull out relevant data
		int counter = 0;
		for (YahooRotoLeague league : leagues) {
			if (league.getAuctionBudget() > 0) {
				dollarBSum = dollarBSum.add(BigDecimal.valueOf(league.getOneDollarB()));
				dollarPSum = dollarPSum.add(BigDecimal.valueOf(league.getOneDollarP()));
				counter++;
			}
		}
		
		// calculate averages and set to respective variables
		if (counter > 0) {
			BigDecimal dollarBAvg = dollarBSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histOneDollarB = Double.parseDouble(dollarBAvg.toString());	
			BigDecimal dollarPAvg = dollarPSum.divide(new BigDecimal(counter), 4, RoundingMode.HALF_UP);
			this.histOneDollarP = Double.parseDouble(dollarPAvg.toString());	
		}
	}

}