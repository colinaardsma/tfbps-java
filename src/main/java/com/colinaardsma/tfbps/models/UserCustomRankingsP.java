package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	private FPProjPitcher pitcher;
	private YahooRotoLeague yahooRotoLeague;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;

	public UserCustomRankingsP(FPProjPitcher pitcher, YahooRotoLeague yahooRotoLeague, User user) {
		this.pitcher = pitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(pitcher, yahooRotoLeague);
	}

	public UserCustomRankingsP(FPProjPitcher pitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, User user) {
		this.pitcher = pitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		this.user = user;
		this.created = new Date();
		this.histSGP = calcLeagueHistSGP(pitcher, ottoneuOldSchoolLeague);
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
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}

	// yahoo roto league
	public double calcLeagueHistSGP(FPProjPitcher pitcher, YahooRotoLeague yahooRotoLeague) {
		BigDecimal w = new BigDecimal(pitcher.getW()).divide(new BigDecimal(yahooRotoLeague.getWHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sv = new BigDecimal(pitcher.getSv()).divide(new BigDecimal(yahooRotoLeague.getSvHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal k = new BigDecimal(pitcher.getK()).divide(new BigDecimal(yahooRotoLeague.getKHistSGPMult()), 4, RoundingMode.HALF_UP);

		// era
		BigDecimal er = new BigDecimal(475).add(new BigDecimal(pitcher.getEr()));
		BigDecimal eraNum = er.multiply(new BigDecimal(9));
		BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(pitcher.getIp()));
		BigDecimal eraDenom = ip;
		BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(yahooRotoLeague.getEraHistSGPMult()), 4, RoundingMode.HALF_UP);

		// whip
		BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(pitcher.getH())).add(new BigDecimal(pitcher.getBb()));
		BigDecimal whipDenom = ip;
		BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(1.23)).divide(new BigDecimal(yahooRotoLeague.getWhipHistSGPMult()), 4, RoundingMode.HALF_UP);

		return w.add(sv.add(k.add(era.add(whip)))).doubleValue();
	}

	// ottoneu old school league
	public double calcLeagueHistSGP(FPProjPitcher pitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		BigDecimal w = new BigDecimal(pitcher.getW()).divide(new BigDecimal(ottoneuOldSchoolLeague.getWHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal sv = new BigDecimal(pitcher.getSv()).divide(new BigDecimal(ottoneuOldSchoolLeague.getSvHistSGPMult()), 4, RoundingMode.HALF_UP);
		BigDecimal k = new BigDecimal(pitcher.getK()).divide(new BigDecimal(ottoneuOldSchoolLeague.getKHistSGPMult()), 4, RoundingMode.HALF_UP);

		// era
		BigDecimal er = new BigDecimal(475).add(new BigDecimal(pitcher.getEr()));
		BigDecimal eraNum = er.multiply(new BigDecimal(9));
		BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(pitcher.getIp()));
		BigDecimal eraDenom = ip;
		BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(ottoneuOldSchoolLeague.getEraHistSGPMult()), 4, RoundingMode.HALF_UP);

		// whip
		BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(pitcher.getH())).add(new BigDecimal(pitcher.getBb()));
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
			if (this.pitcher == p.getPitcher()) {
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
