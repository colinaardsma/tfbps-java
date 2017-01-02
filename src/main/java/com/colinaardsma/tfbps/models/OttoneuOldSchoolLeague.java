package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
@Table(name = "ottoneuoldschoolleague")
public class OttoneuOldSchoolLeague extends AbstractEntity {
	
	// league variables
	private int leagueNumber;
	private String leagueKey;
	private String prevYearKey;
	private String leagueName;
	private String leagueURL;
	private int teamCount;
	private int season;
	
	// yearly sgp variables
	private double rSGPMult;
	private double hrSGPMult;
	private double rbiSGPMult;
	private double sbSGPMult;
	private double avgSGPMult;
	private double wSGPMult;
	private double svSGPMult;
	private double kSGPMult;
	private double eraSGPMult;
	private double whipSGPMult;
	
	// historical sgp variables
	private double rHistSGPMult;
	private double hrHistSGPMult;
	private double rbiHistSGPMult;
	private double sbHistSGPMult;
	private double avgHistSGPMult;
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
	private int previousYearUID;
	
	// join variables
	private List<User> users;
	private List<OttoneuOldSchoolTeam> ottoneuTeams;
	private List<UserCustomRankingsB> userBatterSGP;
    private List<UserCustomRankingsP> userPitcherSGP;
    private List<KeeperCosts> keeperCosts;
	
	public OttoneuOldSchoolLeague(int leagueNumber, String leagueName, String leagueURL, int season) {
		this.leagueNumber = leagueNumber;
		this.leagueKey = leagueNumber + "." + season;
		this.leagueName = leagueName;
		this.leagueURL = leagueURL;
		this.teamCount = 12;
		this.season = season;
	}
	
	public OttoneuOldSchoolLeague() {}

    @NotNull
    @Column(name = "leaguenumber")
	public int getLeagueNumber() {
		return leagueNumber;
	}

	public void setLeagueNumber(int leagueNumber) {
		this.leagueNumber = leagueNumber;
	}

    @NotNull
    @Column(name = "leaguekey", unique = true)
	public String getLeagueKey() {
		return leagueKey;
	}

	public void setLeagueKey(String leagueKey) {
		this.leagueKey = leagueKey;
	}

    @NotNull
    @Column(name = "prevyearkey", unique = true)
	public String getPreviousYearKey() {
		return prevYearKey;
	}

	public void setPreviousYearKey(String prevYearKey) {
		this.prevYearKey = prevYearKey;
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
    @Column(name = "leagueURL")
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

    @Column(name = "avgSGPMult")
	public double getAvgSGPMult() {
		return avgSGPMult;
	}

	public void setAvgSGPMult(double avgSGP) {
		this.avgSGPMult = avgSGP;
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

    @Column(name = "AvgHistSGPMult")
	public double getAvgHistSGPMult() {
		return avgHistSGPMult;
	}

	public void setAvgHistSGPMult(double avgHistSGPMult) {
		this.avgHistSGPMult = avgHistSGPMult;
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

    @Column(name = "getHistOneDollarB")
	public double getHistOneDollarB() {
		return histOneDollarB;
	}

	public void setHistOneDollarB(double histOneDollarB) {
		this.histOneDollarB = histOneDollarB;
	}

    @Column(name = "getHistOneDollarP")
	public double getHistOneDollarP() {
		return histOneDollarP;
	}

	public void setHistOneDollarP(double histOneDollarP) {
		this.histOneDollarP = histOneDollarP;
	}

	@Column(name = "prevyearUID")
	public int getPreviousYearUID() {
		return previousYearUID;
	}

	public void setPreviousYearUID(int previousYearUID) {
		this.previousYearUID = previousYearUID;
	}

    @ManyToMany
    @JoinTable(name="USER_OTTONEUOLDSCHOOLLEAGUE")
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
    @OneToMany
    @JoinColumn(name = "ottoneu_old_school_league_uid")
	public List<OttoneuOldSchoolTeam> getOttoneuTeams() {
		return ottoneuTeams;
	}
	
	public void setOttoneuTeams(List<OttoneuOldSchoolTeam> ottoneuTeams) {
		this.ottoneuTeams = ottoneuTeams;
	}

    @OneToMany
    @JoinColumn(name = "ottoneu_old_school_league_uid")
    public List<UserCustomRankingsB> getUserBatterSGP() {
    	return userBatterSGP;
    }
    
    @SuppressWarnings("unused")
    private void setUserBatterSGP(List<UserCustomRankingsB> userBatterSGP) {
    	this.userBatterSGP = userBatterSGP;
    }
    
    @OneToMany
    @JoinColumn(name = "ottoneu_old_school_league_uid")
    public List<UserCustomRankingsP> getUserPitcherSGP() {
    	return userPitcherSGP;
    }
    
    @SuppressWarnings("unused")
    private void setUserPitcherSGP(List<UserCustomRankingsP> userPitcherSGP) {
    	this.userPitcherSGP = userPitcherSGP;
    }

    @OneToMany
    @JoinColumn(name = "ottoneu_old_school_league_uid")
	private List<KeeperCosts> getKeeperCosts() {
		return keeperCosts;
	}

    @SuppressWarnings("unused")
	private void setKeeperCosts(List<KeeperCosts> keeperCosts) {
		this.keeperCosts = keeperCosts;
	}

	// calculates historical sgp for league (number of years is based on size of list provided)
	public void calcHistSGPs(List<OttoneuOldSchoolLeague> leagues) {
		
		// BigDecimal variables used since double is not accurate when dividing small decimals
		BigDecimal rSum = new BigDecimal(0);
		BigDecimal hrSum = new BigDecimal(0);
		BigDecimal rbiSum = new BigDecimal(0);
		BigDecimal sbSum = new BigDecimal(0);
		BigDecimal avgSum = new BigDecimal(0);
		BigDecimal wSum = new BigDecimal(0);
		BigDecimal svSum = new BigDecimal(0);
		BigDecimal kSum = new BigDecimal(0);
		BigDecimal eraSum = new BigDecimal(0);
		BigDecimal whipSum = new BigDecimal(0);
		
		// loop through list and pull out relevant data
		for (OttoneuOldSchoolLeague league : leagues) {
			rSum = rSum.add(BigDecimal.valueOf(league.getRSGPMult()));
			hrSum = hrSum.add(BigDecimal.valueOf(league.getHrSGPMult()));
			rbiSum = rbiSum.add(BigDecimal.valueOf(league.getRbiSGPMult()));
			sbSum = sbSum.add(BigDecimal.valueOf(league.getSbSGPMult()));
			avgSum = avgSum.add(BigDecimal.valueOf(league.getAvgSGPMult()));
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
		BigDecimal avgAvg = avgSum.divide(new BigDecimal(leagues.size()), 4, RoundingMode.HALF_UP);
		this.avgHistSGPMult = Double.parseDouble(avgAvg.toString());	
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
	
}