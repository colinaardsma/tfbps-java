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
@Table(name = "UserCustomRankingsP")
public class UserCustomRankingsP extends AbstractEntity {

	private double histSGP;
	private BigDecimal histAAV;
	private Date created;

	// join variables
	private User user;
	private FPProjPitcher fpProjPitcher;
	private SteamerProjPitcher steamerProjPitcher;
	private YahooRotoLeague yahooRotoLeague;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private KeeperCosts keeperCost;

	// YAHOO ROTO LEAGUE
	public UserCustomRankingsP(FPProjPitcher fpProjPitcher, YahooRotoLeague yahooRotoLeague, User user) {
		this.fpProjPitcher = fpProjPitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(fpProjPitcher, yahooRotoLeague);
	}

	public UserCustomRankingsP(SteamerProjPitcher steamerProjPitcher, YahooRotoLeague yahooRotoLeague, User user) {
		this.steamerProjPitcher = steamerProjPitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(fpProjPitcher, yahooRotoLeague);
	}

	// OTTONEU OLD SCHOOL LEAGUE
	public UserCustomRankingsP(FPProjPitcher fpProjPitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.fpProjPitcher = fpProjPitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(fpProjPitcher, ottoneuOldSchoolLeague);
	}

	public UserCustomRankingsP(SteamerProjPitcher steamerProjPitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.steamerProjPitcher = steamerProjPitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(steamerProjPitcher, ottoneuOldSchoolLeague);
	}

	public UserCustomRankingsP() {}

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
	public FPProjPitcher getFpProjPitcher() {
		return fpProjPitcher;
	}

	public void setFpProjPitcher(FPProjPitcher fpProjpitcher) {
		this.fpProjPitcher = fpProjpitcher;
	}

	@ManyToOne
	public SteamerProjPitcher getSteamerProjPitcher() {
		return steamerProjPitcher;
	}

	public void setSteamerProjPitcher(SteamerProjPitcher steamerProjPitcher) {
		this.steamerProjPitcher = steamerProjPitcher;
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

	// yahoo roto league
	public double calcLeagueHistSGP(FPProjPitcher fpProjpitcher, YahooRotoLeague yahooRotoLeague) {
		BigDecimal w = new BigDecimal(fpProjpitcher.getW()).divide(new BigDecimal(yahooRotoLeague.getWHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sv = new BigDecimal(fpProjpitcher.getSv()).divide(new BigDecimal(yahooRotoLeague.getSvHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal k = new BigDecimal(fpProjpitcher.getK()).divide(new BigDecimal(yahooRotoLeague.getKHistSGPMult()), 4, RoundingMode.HALF_UP);

		// era
		BigDecimal er = new BigDecimal(475).add(new BigDecimal(fpProjpitcher.getEr()));
		BigDecimal eraNum = er.multiply(new BigDecimal(9));
		BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(fpProjpitcher.getIp()));
		BigDecimal eraDenom = ip;
		BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(yahooRotoLeague.getEraHistSGPMult()), 4, RoundingMode.HALF_UP);

		// whip
		BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(fpProjpitcher.getH())).add(new BigDecimal(fpProjpitcher.getBb()));
		BigDecimal whipDenom = ip;
		BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(1.23)).divide(new BigDecimal(yahooRotoLeague.getWhipHistSGPMult()), 4, RoundingMode.HALF_UP);

		return w.add(sv.add(k.add(era.add(whip)))).doubleValue();
	}

	// ottoneu old school league
	public double calcLeagueHistSGP(FPProjPitcher fpProjpitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		BigDecimal w = new BigDecimal(fpProjpitcher.getW()).divide(new BigDecimal(ottoneuOldSchoolLeague.getWHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sv = new BigDecimal(fpProjpitcher.getSv()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSvHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal k = new BigDecimal(fpProjpitcher.getK()).divide(new BigDecimal(ottoneuOldSchoolLeague.getKHistSGPMult()), 4, RoundingMode.HALF_UP);

		// era
		BigDecimal er = new BigDecimal(475).add(new BigDecimal(fpProjpitcher.getEr()));
		BigDecimal eraNum = er.multiply(new BigDecimal(9));
		BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(fpProjpitcher.getIp()));
		BigDecimal eraDenom = ip;
		BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(ottoneuOldSchoolLeague.getEraHistSGPMult()), 4, RoundingMode.HALF_UP);

		// whip
		BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(fpProjpitcher.getH())).add(new BigDecimal(fpProjpitcher.getBb()));
		BigDecimal whipDenom = ip;
		BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(1.23)).divide(new BigDecimal(ottoneuOldSchoolLeague.getWhipHistSGPMult()), 4, RoundingMode.HALF_UP);

		return w.add(sv.add(k.add(era.add(whip)))).doubleValue();
	}

	public void calcLeagueHistAAV(List<UserCustomRankingsP> pitcherList, YahooRotoLeague yahooRotoLeague) {
		// player pool
		int draftedP = (int) yahooRotoLeague.getHistDraftedP(); // historical average of total pitchers taken
		int oneDollarP = (int) yahooRotoLeague.getHistOneDollarP(); // historical average of total $1 pitchers taken
		int draftedPOverOneDollar = draftedP - oneDollarP; // historical average of total pitchers taken minus $1 pitchers taken

		// total league budget (with $1 players taken out)
		BigDecimal leagueBudgetOverOneP = new BigDecimal(yahooRotoLeague.getHistBudgetPctP()).multiply(new BigDecimal(yahooRotoLeague.getAuctionBudget()).multiply(new BigDecimal(yahooRotoLeague.getTeamCount()))).subtract(new BigDecimal(yahooRotoLeague.getHistOneDollarP()));

		// calculate average SGP value of $1 players
		BigDecimal oneDollarSGPSum = new BigDecimal(0);
		for (int i = draftedPOverOneDollar; i < draftedP; i++) {
			oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(pitcherList.get(i).getHistSGP()));
		}
		BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarP), 4, RoundingMode.HALF_UP);

		// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
		BigDecimal totalSGPAboveOne = new BigDecimal(0);
		for (int j = 0; j < draftedPOverOneDollar; j++) {
			totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(pitcherList.get(j).getHistSGP()).subtract(oneDollarSGP));
		}

		// calculate $ / SGP
		BigDecimal dollarsPerSGP = leagueBudgetOverOneP.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);

		// check to see if this pitcher is a $1 pitcher, if so set value to $1
		int counter = 0;
		for (UserCustomRankingsP p : pitcherList) {
			if (this.fpProjPitcher == p.getFpProjPitcher()) {
				if (counter < draftedPOverOneDollar) {
					this.histAAV = dollarsPerSGP.multiply(new BigDecimal(this.histSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				} else if (counter < draftedP && counter >= draftedPOverOneDollar) {
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

	public double calcLeagueHistSGP(SteamerProjPitcher steamerProjPitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		BigDecimal w = new BigDecimal(steamerProjPitcher.getW()).divide(new BigDecimal(ottoneuOldSchoolLeague.getWHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sv = new BigDecimal(steamerProjPitcher.getSv()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSvHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal k = new BigDecimal(steamerProjPitcher.getK()).divide(new BigDecimal(ottoneuOldSchoolLeague.getKHistSGPMult()), 4, RoundingMode.HALF_UP);

		// era
		BigDecimal er = new BigDecimal(475).add(new BigDecimal(steamerProjPitcher.getEr()));
		BigDecimal eraNum = er.multiply(new BigDecimal(9));
		BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(steamerProjPitcher.getIp()));
		BigDecimal eraDenom = ip;
		BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(ottoneuOldSchoolLeague.getEraHistSGPMult()), 4, RoundingMode.HALF_UP);

		// whip
		BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(steamerProjPitcher.getH())).add(new BigDecimal(steamerProjPitcher.getBb()));
		BigDecimal whipDenom = ip;
		BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(1.23)).divide(new BigDecimal(ottoneuOldSchoolLeague.getWhipHistSGPMult()), 4, RoundingMode.HALF_UP);

		return w.add(sv.add(k.add(era.add(whip)))).doubleValue();
	}

	public void calcLeagueHistAAV(List<UserCustomRankingsP> pitcherList, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		// player pool
		int draftedP = (int) ottoneuOldSchoolLeague.getHistDraftedP(); // historical average of total pitchers taken
		int oneDollarP = (int) ottoneuOldSchoolLeague.getHistOneDollarP(); // historical average of total $1 pitchers taken
		int draftedPOverOneDollar = draftedP - oneDollarP; // historical average of total pitchers taken minus $1 pitchers taken

		// total league budget (with $1 players taken out)
//		BigDecimal leagueBudgetOverOneP = new BigDecimal(ottoneuOldSchoolLeague.getHistBudgetPctP()).multiply(new BigDecimal(ottoneuOldSchoolLeague.getHistTotalSpent())).subtract(new BigDecimal(ottoneuOldSchoolLeague.getHistOneDollarP()));
		BigDecimal leagueBudgetOverOneP = new BigDecimal(ottoneuOldSchoolLeague.getHistBudgetPctP()).multiply(new BigDecimal(4800)).subtract(new BigDecimal(ottoneuOldSchoolLeague.getHistOneDollarP()));

		// calculate average SGP value of $1 players
		BigDecimal oneDollarSGPSum = new BigDecimal(0);
		for (int i = draftedPOverOneDollar; i < draftedP; i++) {
			oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(pitcherList.get(i).getHistSGP()));
		}
		BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarP), 4, RoundingMode.HALF_UP);

		// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
		BigDecimal totalSGPAboveOne = new BigDecimal(0);
		for (int j = 0; j < draftedPOverOneDollar; j++) {
			totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(pitcherList.get(j).getHistSGP()).subtract(oneDollarSGP));
		}

		// calculate $ / SGP
		BigDecimal dollarsPerSGP = leagueBudgetOverOneP.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);

		// check to see if this pitcher is a $1 pitcher, if so set value to $1
		int counter = 0;
		for (UserCustomRankingsP p : pitcherList) {
			if (this.steamerProjPitcher == p.getSteamerProjPitcher()) {
				if (counter < draftedPOverOneDollar) {
					this.histAAV = dollarsPerSGP.multiply(new BigDecimal(this.histSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				} else if (counter < draftedP && counter >= draftedPOverOneDollar) {
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
