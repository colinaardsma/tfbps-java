package com.fantasyspot.models;

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

import com.fantasyspot.models.util.SGPMultCalc;


@Entity
@Table(name = "fpprojp")
public class FPProjPitcher extends AbstractEntity {
	
	private String name;
	private String team;
	private String pos;
	private int ip;
	private int k;
	private int w;
	private int sv;
	private double era;
	private double whip;
	private int er;
	private int h;
	private int bb;
	private int hr;
	private int g;
	private int gs;
	private int l;
	private int cg;
	private double wSGP;
	private double svSGP;
	private double kSGP;
	private double eraSGP;
	private double whipSGP;
	private double sgp;
	private BigDecimal aav;
	private String category;
	private Date created;
	
	// join variables
	private List<UserCustomRankingsP> userCustomRankingsP;
    private List<KeeperCosts> keeperCosts;

	public FPProjPitcher(String name, String team, String pos, int ip, int k, int w, int sv, double era, double whip, int er, int h, int bb, int hr, int g, int gs, int l, int cg, String category) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.ip = ip;
		this.k = k;
		this.w = w;
		this.sv = sv;
		this.era = era;
		this.whip = whip;
		this.er = er;
		this.h = h;
		this.bb = bb;
		this.hr = hr;
		this.g = g;
		this.gs = gs;
		this.l = l;
		this.cg = cg;
		calcSgp(SGPMultCalc.sgpMultW(), SGPMultCalc.sgpMultSV(), SGPMultCalc.sgpMultK(), SGPMultCalc.sgpMultERA(), SGPMultCalc.sgpMultWHIP());

		this.category = category;
		this.created = new Date();
	}
	
	public FPProjPitcher() {}
	
	@NotNull
    @Column(name = "name")
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
    @Column(name = "team")
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	@NotNull
    @Column(name = "pos")
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@NotNull
    @Column(name = "ip")
	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	@NotNull
    @Column(name = "k")
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	@NotNull
    @Column(name = "w")
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	@NotNull
    @Column(name = "sv")
	public int getSv() {
		return sv;
	}

	public void setSv(int sv) {
		this.sv = sv;
	}

	@NotNull
    @Column(name = "era")
	public double getEra() {
		return era;
	}

	public void setEra(double era) {
		this.era = era;
	}

	@NotNull
    @Column(name = "whip")
	public double getWhip() {
		return whip;
	}

	public void setWhip(double whip) {
		this.whip = whip;
	}

	@NotNull
    @Column(name = "er")
	public int getEr() {
		return er;
	}

	public void setEr(int er) {
		this.er = er;
	}

	@NotNull
    @Column(name = "h")
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	@NotNull
    @Column(name = "bb")
	public int getBb() {
		return bb;
	}

	public void setBb(int bb) {
		this.bb = bb;
	}

	@NotNull
    @Column(name = "hr")
	public int getHr() {
		return hr;
	}

	public void setHr(int hr) {
		this.hr = hr;
	}

	@NotNull
    @Column(name = "g")
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	@NotNull
    @Column(name = "gs")
	public int getGs() {
		return gs;
	}

	public void setGs(int gs) {
		this.gs = gs;
	}

	@NotNull
    @Column(name = "l")
	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	@NotNull
    @Column(name = "cg")
	public int getCg() {
		return cg;
	}

	public void setCg(int cg) {
		this.cg = cg;
	}

	@NotNull
    @Column(name = "wsgp")
	public double getWSGP() {
		return wSGP;
	}

	public void setWSGP(double wSGP) {
		this.wSGP = wSGP;
	}

	@NotNull
    @Column(name = "svsgp")
	public double getSvSGP() {
		return svSGP;
	}

	public void setSvSGP(double svSGP) {
		this.svSGP = svSGP;
	}

	@NotNull
    @Column(name = "ksgp")
	public double getKSGP() {
		return kSGP;
	}

	public void setKSGP(double kSGP) {
		this.kSGP = kSGP;
	}

	@NotNull
    @Column(name = "erasgp")
	public double getEraSGP() {
		return eraSGP;
	}

	public void setEraSGP(double eraSGP) {
		this.eraSGP = eraSGP;
	}

	@NotNull
    @Column(name = "whipsgp")
	public double getWhipSGP() {
		return whipSGP;
	}

	public void setWhipSGP(double whipSGP) {
		this.whipSGP = whipSGP;
	}

	@NotNull
    @Column(name = "sgp")
	public double getSgp() {
		return sgp;
	}

	public void setSgp(double sgp) {
		this.sgp = sgp;
	}

    @Column(name = "aav")
	public BigDecimal getAav() {
		return aav;
	}

	public void setAav(BigDecimal aav) {
		this.aav = aav;
	}

	@NotNull
    @Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
    @OneToMany
    @JoinColumn(name = "pitcher_uid")
    public List<UserCustomRankingsP> getUserPitcherSGP() {
    	return userCustomRankingsP;
    }
    
    @SuppressWarnings("unused")
    private void setUserPitcherSGP(List<UserCustomRankingsP> userPitcherSGP) {
    	this.userCustomRankingsP = userPitcherSGP;
    }

    @OneToMany
    @JoinColumn(name = "pitcher_uid")
    public List<KeeperCosts> getKeeperCosts() {
    	return keeperCosts;
    }
    
    @SuppressWarnings("unused")
    private void setKeeperCosts(List<KeeperCosts> keeperCosts) {
    	this.keeperCosts = keeperCosts;
    }

    protected void calcSgp(double sgpMultW, double sgpMultSV, double sgpMultK, double sgpMultERA, double sgpMultWHIP) {
    	BigDecimal w = new BigDecimal(this.w).divide(new BigDecimal(sgpMultW), 4, RoundingMode.HALF_UP);
    	this.wSGP = w.doubleValue();
    	BigDecimal sv = new BigDecimal(this.sv).divide(new BigDecimal(sgpMultSV), 4, RoundingMode.HALF_UP);
    	this.svSGP = sv.doubleValue();
    	BigDecimal k = new BigDecimal(this.k).divide(new BigDecimal(sgpMultK), 4, RoundingMode.HALF_UP);
    	this.kSGP = k.doubleValue();
    	
    	// era
        BigDecimal er = new BigDecimal(475).add(new BigDecimal(this.er));
        BigDecimal eraNum = er.multiply(new BigDecimal(9));
        BigDecimal ip = new BigDecimal(1192).add(new BigDecimal(this.ip));
        BigDecimal eraDenom = ip;
        BigDecimal era = eraNum.divide(eraDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(3.59)).divide(new BigDecimal(sgpMultERA), 4, RoundingMode.HALF_UP);;
        this.eraSGP = era.doubleValue();
        
        // whip
        BigDecimal whipNum = new BigDecimal(1466).add(new BigDecimal(this.h)).add(new BigDecimal(this.bb));
        BigDecimal whipDenom = ip;
        BigDecimal whip = whipNum.divide(whipDenom, 4, RoundingMode.HALF_UP).subtract(new BigDecimal(1.23)).divide(new BigDecimal(sgpMultWHIP), 4, RoundingMode.HALF_UP);;
        this.whipSGP = whip.doubleValue();
        
    	this.sgp = w.add(sv.add(k.add(era.add(whip)))).doubleValue();
    }
    
	public void calcAav(List<FPProjPitcher> pitcherList) {

		// player pool
		int draftedP = 154; // total batters taken
		int oneDollarP = 45; // total $1 batters taken
		int draftedPOverOneDollar = draftedP - oneDollarP; // total batters taken minus $1 batters taken
		int teamBudget = 260; // total budget per team
		int teamCount = 12; // total teams in league
		double budgetPctP = 0.35; // % of league budget spent on pitchers
		
		// total league budget (with $1 players taken out)
		BigDecimal leagueBudgetOverOneP = new BigDecimal(budgetPctP).multiply(new BigDecimal(teamBudget).multiply(new BigDecimal(teamCount))).subtract(new BigDecimal(oneDollarP));

    	// calculate average SGP value of $1 players
    	BigDecimal oneDollarSGPSum = new BigDecimal(0);
    	for (int i = draftedPOverOneDollar; i < draftedP; i++) {
    		oneDollarSGPSum = oneDollarSGPSum.add(new BigDecimal(pitcherList.get(i).getSgp()));
    	}
    	BigDecimal oneDollarSGP = oneDollarSGPSum.divide(new BigDecimal(oneDollarP), 4, RoundingMode.HALF_UP);
    	
    	// calculate total SGP value above $1 players (with average $1 SGP value subtracted)
    	BigDecimal totalSGPAboveOne = new BigDecimal(0);
    	for (int j = 0; j < draftedPOverOneDollar; j++) {
    		totalSGPAboveOne = totalSGPAboveOne.add(new BigDecimal(pitcherList.get(j).getSgp()).subtract(oneDollarSGP));
    	}
    	
    	// calculate $ / SGP
    	BigDecimal dollarsPerSGP = leagueBudgetOverOneP.divide(totalSGPAboveOne, 4, RoundingMode.HALF_UP);
    	
    	// check to see if this pitcher is a $1 pitcher, if so set value to $1
       	int counter = 0;
    	for (FPProjPitcher b : pitcherList) {
    		if (this == b) {
    			if (counter < draftedPOverOneDollar) {
    		    	this.aav = dollarsPerSGP.multiply(new BigDecimal(this.sgp).subtract(oneDollarSGP)).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else if (counter < draftedP && counter >= draftedPOverOneDollar) {
    				this.aav = new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			} else {
    				this.aav = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    		    	break;
    			}
    		}
    		counter++;
    	}
    }

}