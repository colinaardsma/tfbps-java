package com.colinaardsma.tfbps.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.colinaardsma.tfbps.models.util.SGPMultCalc;

@Entity
@Table(name = "steamerb")
public class SteamerBatter extends AbstractEntity {

	// player description
	private String name;
	private String team;
	private String pos;
	private String playerId;
	
	// stats
	private int g;
	private int pa;
	private int ab;
	private int h;
	private int dbl;
	private int tpl;
	private int hr;
	private int r;
	private int rbi;
	private int bb;
	private int k;
	private int hbp;
	private int sb;
	private int cs;
	private double avg;
	private double obp;
	private double slg;
	
	// advanced stats
	private double ops;
	private double woba;
	private double wrcPlus;
	private double bsr;
	private double fld;
	private double offWar;
	private double defWar;
	private double war;
	
	// sgp
	private double rSGP;
	private double hrSGP;
	private double rbiSGP;
	private double sbSGP;
	private double opsSGP;
	private double avgSGP;
	private double opsTotalSGP;
	private double avgTotalSGP;
	
	// aav
	private BigDecimal opsTotalAAV;
	private BigDecimal avgTotalAAV;

	// other
	private String category;
	private Date created;
	
	// join variables
	private List<UserCustomRankingsB> userCustomRankingsB;
    private List<KeeperCosts> keeperCosts;
	
	// aav calculation variables
	// player pool
	int draftedB = 154; // total batters taken
	int oneDollarB = 45; // total $1 batters taken
	int draftedBOverOneDollar = draftedB - oneDollarB; // total batters taken minus $1 batters taken
	int teamBudget = 260; // total budget per team
	int teamCount = 12; // total teams in league
	double budgetPctB = 0.65; // % of league budget spent on batters
	
	// total league budget (with $1 players taken out)
	BigDecimal leagueBudgetOverOneB = new BigDecimal(budgetPctB).multiply(new BigDecimal(teamBudget).multiply(new BigDecimal(teamCount))).subtract(new BigDecimal(oneDollarB));

	public SteamerBatter(String name, String team, String playerId, int g, int pa, int ab, int h, int dbl,
			int tpl, int hr, int r, int rbi, int bb, int k, int hbp, int sb, int cs, double avg, double obp, double slg,
			double ops, double woba, double wrcPlus, double bsr, double fld, double offWar, double defWar, double war, String category) {
		this.name = name;
		this.team = team;
		this.playerId = playerId;
		this.g = g;
		this.pa = pa;
		this.ab = ab;
		this.h = h;
		this.dbl = dbl;
		this.tpl = tpl;
		this.hr = hr;
		this.r = r;
		this.rbi = rbi;
		this.bb = bb;
		this.k = k;
		this.hbp = hbp;
		this.sb = sb;
		this.cs = cs;
		this.avg = avg;
		this.obp = obp;
		this.slg = slg;
		this.ops = ops;
		this.woba = woba;
		this.wrcPlus = wrcPlus;
		this.bsr = bsr;
		this.fld = fld;
		this.offWar = offWar;
		this.defWar = defWar;
		this.war = war;
		calcOpsSgp(SGPMultCalc.sgpMultR(), SGPMultCalc.sgpMultHR(), SGPMultCalc.sgpMultRBI(), SGPMultCalc.sgpMultSB(), SGPMultCalc.sgpMultOPS());
		calcAvgSgp(SGPMultCalc.sgpMultR(), SGPMultCalc.sgpMultHR(), SGPMultCalc.sgpMultRBI(), SGPMultCalc.sgpMultSB(), SGPMultCalc.sgpMultAVG());
		
		this.category = category;
		this.created = new Date();		
	}
	
	public SteamerBatter() {}

    @NotNull
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "team")
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

    @Column(name = "pos")
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

    @NotNull
    @Column(name = "playerId")
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

    @Column(name = "g")
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

    @Column(name = "pa")
	public int getPa() {
		return pa;
	}

	public void setPa(int pa) {
		this.pa = pa;
	}

    @Column(name = "ab")
	public int getAb() {
		return ab;
	}

	public void setAb(int ab) {
		this.ab = ab;
	}

    @Column(name = "h")
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

    @Column(name = "dbl")
	public int getDbl() {
		return dbl;
	}

	public void setDbl(int dbl) {
		this.dbl = dbl;
	}

    @Column(name = "tpl")
	public int getTpl() {
		return tpl;
	}

	public void setTpl(int tpl) {
		this.tpl = tpl;
	}

    @Column(name = "hr")
	public int getHr() {
		return hr;
	}

	public void setHr(int hr) {
		this.hr = hr;
	}

    @Column(name = "r")
	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

    @Column(name = "rbi")
	public int getRbi() {
		return rbi;
	}

	public void setRbi(int rbi) {
		this.rbi = rbi;
	}

    @Column(name = "bb")
	public int getBb() {
		return bb;
	}

	public void setBb(int bb) {
		this.bb = bb;
	}

    @Column(name = "k")
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

    @Column(name = "hbp")
	public int getHbp() {
		return hbp;
	}

	public void setHbp(int hbp) {
		this.hbp = hbp;
	}

    @Column(name = "sb")
	public int getSb() {
		return sb;
	}

	public void setSb(int sb) {
		this.sb = sb;
	}

    @Column(name = "cs")
	public int getCs() {
		return cs;
	}

	public void setCs(int cs) {
		this.cs = cs;
	}

    @Column(name = "avg")
	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

    @Column(name = "obp")
	public double getObp() {
		return obp;
	}

	public void setObp(double obp) {
		this.obp = obp;
	}

    @Column(name = "slg")
	public double getSlg() {
		return slg;
	}

	public void setSlg(double slg) {
		this.slg = slg;
	}

    @Column(name = "ops")
	public double getOps() {
		return ops;
	}

	public void setOps(double ops) {
		this.ops = ops;
	}

    @Column(name = "woba")
	public double getWoba() {
		return woba;
	}

	public void setWoba(double woba) {
		this.woba = woba;
	}

    @Column(name = "wrcPlus")
	public double getWrcPlus() {
		return wrcPlus;
	}

	public void setWrcPlus(double wrcPlus) {
		this.wrcPlus = wrcPlus;
	}

    @Column(name = "bsr")
	public double getBsr() {
		return bsr;
	}

	public void setBsr(double bsr) {
		this.bsr = bsr;
	}

    @Column(name = "fld")
	public double getFld() {
		return fld;
	}

	public void setFld(double fld) {
		this.fld = fld;
	}

    @Column(name = "offWar")
	public double getOffWar() {
		return offWar;
	}

	public void setOffWar(double offWar) {
		this.offWar = offWar;
	}

    @Column(name = "defWar")
	public double getDefWar() {
		return defWar;
	}

	public void setDefWar(double defWar) {
		this.defWar = defWar;
	}

    @Column(name = "war")
	public double getWar() {
		return war;
	}

	public void setWar(double war) {
		this.war = war;
	}

    @Column(name = "rSGP")
	public double getRSGP() {
		return rSGP;
	}

	public void setRSGP(double rSGP) {
		this.rSGP = rSGP;
	}

    @Column(name = "hrSGP")
	public double getHrSGP() {
		return hrSGP;
	}

	public void setHrSGP(double hrSGP) {
		this.hrSGP = hrSGP;
	}

    @Column(name = "rbiSGP")
	public double getRbiSGP() {
		return rbiSGP;
	}

	public void setRbiSGP(double rbiSGP) {
		this.rbiSGP = rbiSGP;
	}

    @Column(name = "sbSGP")
	public double getSbSGP() {
		return sbSGP;
	}

	public void setSbSGP(double sbSGP) {
		this.sbSGP = sbSGP;
	}

    @Column(name = "opsSGP")
	public double getOpsSGP() {
		return opsSGP;
	}

	public void setOpsSGP(double opsSGP) {
		this.opsSGP = opsSGP;
	}

    @Column(name = "avgSGP")
	public double getAvgSGP() {
		return avgSGP;
	}

	public void setAvgSGP(double avgSGP) {
		this.avgSGP = avgSGP;
	}

    @Column(name = "opsTotalSGP")
	public double getOpsTotalSGP() {
		return opsTotalSGP;
	}

	public void setOpsTotalSGP(double opsTotalSGP) {
		this.opsTotalSGP = opsTotalSGP;
	}

    @Column(name = "avgTotalSGP")
	public double getAvgTotalSGP() {
		return avgTotalSGP;
	}

	public void setAvgTotalSGP(double avgTotalSGP) {
		this.avgTotalSGP = avgTotalSGP;
	}

    @Column(name = "opsTotalAAV")
	public BigDecimal getOpsTotalAAV() {
		return opsTotalAAV;
	}

	public void setOpsTotalAAV(BigDecimal opsTotalAAV) {
		this.opsTotalAAV = opsTotalAAV;
	}

    @Column(name = "avgTotalAAV")
	public BigDecimal getAvgTotalAAV() {
		return avgTotalAAV;
	}

	public void setAvgTotalAAV(BigDecimal avgTotalAAV) {
		this.avgTotalAAV = avgTotalAAV;
	}

    @Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

    @Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
    @OneToMany
    @JoinColumn(name = "batter_uid")
    public List<UserCustomRankingsB> getUserBatterSGP() {
    	return userCustomRankingsB;
    }
    
    @SuppressWarnings("unused")
    private void setUserBatterSGP(List<UserCustomRankingsB> userBatterSGP) {
    	this.userCustomRankingsB = userBatterSGP;
    }
    
    @OneToMany
    @JoinColumn(name = "batter_uid")
    public List<KeeperCosts> getKeeperCosts() {
    	return keeperCosts;
    }
    
    @SuppressWarnings("unused")
    private void setKeeperCosts(List<KeeperCosts> keeperCosts) {
    	this.keeperCosts = keeperCosts;
    }

    protected void calcOpsSgp(double sgpMultR, double sgpMultHR, double sgpMultRBI, double sgpMultSB, double sgpMultOPS) {
    	BigDecimal r = new BigDecimal(this.r).divide(new BigDecimal(sgpMultR), 4, RoundingMode.HALF_UP);
    	this.rSGP = r.doubleValue();
    	BigDecimal hr = new BigDecimal(this.hr).divide(new BigDecimal(sgpMultHR), 4, RoundingMode.HALF_UP);
    	this.hrSGP = hr.doubleValue();
    	BigDecimal rbi = new BigDecimal(this.rbi).divide(new BigDecimal(sgpMultRBI), 4, RoundingMode.HALF_UP);
    	this.rbiSGP = rbi.doubleValue();
    	BigDecimal sb = new BigDecimal(this.sb).divide(new BigDecimal(sgpMultSB), 4, RoundingMode.HALF_UP);
    	this.sbSGP = sb.doubleValue();

    	// ops
    	BigDecimal h = new BigDecimal(this.h);
    	BigDecimal obpNum = h.add(new BigDecimal(this.bb)).add(new BigDecimal(this.hbp)).add(new BigDecimal(2178.8));
    	System.out.println("Player Name: " + name);
    	System.out.println("OBP Num: " + obpNum);
    	BigDecimal obpDenom = new BigDecimal(this.pa).add(new BigDecimal(6682));
    	System.out.println("OBP Denom: " + obpDenom);
    	BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_UP);
    	System.out.println("OBP: " + obp);
    	BigDecimal slgNum = h.add(new BigDecimal(this.dbl)).add(new BigDecimal(this.tpl).multiply(new BigDecimal(2))).add(new BigDecimal(this.hr).multiply(new BigDecimal(3))).add(new BigDecimal(2528.5));
    	System.out.println("SLG Num: " + slgNum);
    	BigDecimal slgDenom = new BigDecimal(this.ab).add(new BigDecimal(5993));
    	System.out.println("SLG Denom: " + slgDenom);
    	BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_UP);
    	System.out.println("SLG: " + slg);
    	BigDecimal ops = obp.add(slg).subtract(new BigDecimal(0.748));
    	System.out.println("OPS: " + ops);
    	ops = ops.divide(new BigDecimal(sgpMultOPS), 4, RoundingMode.HALF_UP);
    	System.out.println("OPS SGP: " + ops);
    	this.opsSGP = ops.doubleValue();

    	this.opsTotalSGP = r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
    }

    protected void calcAvgSgp(double sgpMultR, double sgpMultHR, double sgpMultRBI, double sgpMultSB, double sgpMultAVG) {
    	BigDecimal r = new BigDecimal(this.r).divide(new BigDecimal(sgpMultR), 4, RoundingMode.HALF_UP);
    	this.rSGP = r.doubleValue();
    	BigDecimal hr = new BigDecimal(this.hr).divide(new BigDecimal(sgpMultHR), 4, RoundingMode.HALF_UP);
    	this.hrSGP = hr.doubleValue();
    	BigDecimal rbi = new BigDecimal(this.rbi).divide(new BigDecimal(sgpMultRBI), 4, RoundingMode.HALF_UP);
    	this.rbiSGP = rbi.doubleValue();
    	BigDecimal sb = new BigDecimal(this.sb).divide(new BigDecimal(sgpMultSB), 4, RoundingMode.HALF_UP);
    	this.sbSGP = sb.doubleValue();

    	// avg
    	BigDecimal h = new BigDecimal(this.h).add(new BigDecimal(1768));	
    	BigDecimal ab = new BigDecimal(this.ab).add(new BigDecimal(6617));
    	BigDecimal avg = h.divide(ab, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(0.267));
    	avg = avg.divide(new BigDecimal(sgpMultAVG), 4, RoundingMode.HALF_UP);
    	this.avgSGP = avg.doubleValue();

    	this.avgTotalSGP = r.add(hr.add(rbi.add(sb.add(avg)))).doubleValue();
    }

    public void calcOpsAav(List<SteamerBatter> batterList) {

    	// calculate average SGP value of $1 players
    	BigDecimal oneDollarSGPSum = new BigDecimal(0);
    	for (int i = draftedBOverOneDollar; i < draftedB; i++) {
    		oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(batterList.get(i).getOpsTotalSGP()));
    	}
    	BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarB), 4, RoundingMode.HALF_UP);

    	// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
    	BigDecimal totalSGPAboveOne = new BigDecimal(0);
    	for (int j = 0; j < draftedBOverOneDollar; j++) {
    		totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(batterList.get(j).getOpsTotalSGP()).subtract(oneDollarSGP));
    	}

    	// calculate $ / SGP
    	BigDecimal dollarsPerSGP = leagueBudgetOverOneB.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);

    	// check to see if this batter is a $1 batter, if so set value to $1
    	int counter = 0;
    	for (SteamerBatter b : batterList) {
    		if (this == b) {
    			if (counter < draftedBOverOneDollar) {
    				this.opsTotalAAV = dollarsPerSGP.multiply(new BigDecimal(this.opsTotalSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			} else if (counter < draftedB && counter >= draftedBOverOneDollar) {
    				this.opsTotalAAV = new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			} else {
    				this.opsTotalAAV = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			}
    		}
    		counter++;
    	}
    }

    public void calcAvgAav(List<SteamerBatter> batterList) {

    	// calculate average SGP value of $1 players
    	BigDecimal oneDollarSGPSum = new BigDecimal(0);
    	for (int i = draftedBOverOneDollar; i < draftedB; i++) {
    		oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(batterList.get(i).getAvgTotalSGP()));
    	}
    	BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarB), 4, RoundingMode.HALF_UP);

    	// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
    	BigDecimal totalSGPAboveOne = new BigDecimal(0);
    	for (int j = 0; j < draftedBOverOneDollar; j++) {
    		totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(batterList.get(j).getAvgTotalSGP()).subtract(oneDollarSGP));
    	}

    	// calculate $ / SGP
    	BigDecimal dollarsPerSGP = leagueBudgetOverOneB.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);

    	// check to see if this batter is a $1 batter, if so set value to $1
    	int counter = 0;
    	for (SteamerBatter b : batterList) {
    		if (this == b) {
    			if (counter < draftedBOverOneDollar) {
    				this.avgTotalAAV = dollarsPerSGP.multiply(new BigDecimal(this.avgTotalSGP).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			} else if (counter < draftedB && counter >= draftedBOverOneDollar) {
    				this.avgTotalAAV = new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			} else {
    				this.avgTotalAAV = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    				break;
    			}
    		}
    		counter++;
    	}
    }


}
