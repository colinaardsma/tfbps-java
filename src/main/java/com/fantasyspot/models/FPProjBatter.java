package com.fantasyspot.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
@Table(name = "fpprojb")
public class FPProjBatter extends AbstractEntity {
	
	// player description
	private String name;
	private String team;
	private String pos;

	// stats
	private int ab;
	private int r;
	private int hr;
	private int rbi;
	private int sb;
	private double avg;
	private double obp;
	private int h;
	private int dbl;
	private int tpl;
	private int bb;
	private int k;
	private double slg;
	private double ops;
	
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
	
	// FVARz
	private double r_zScore;
	private double hr_zScore;
	private double rbi_zScore;
	private double sb_zScore;
	private double ops_zScore;
	private double avg_zScore;
	private double opsFVAAz;
	private double avgFVAAz;
	private double opsFVARz;
	private double avgFVARz;

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

	public FPProjBatter(String name, String team, String pos, int ab, int r, int hr, int rbi, int sb, double avg, double obp, int h, int dbl, int tpl, int bb, int k, double slg, double ops, String category) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.ab = ab;
		this.r = r;
		this.hr = hr;
		this.rbi = rbi;
		this.sb = sb;
		this.avg = avg;
		this.obp = obp;
		this.h = h;
		this.dbl = dbl;
		this.tpl = tpl;
		this.bb = bb;
		this.k = k;
		this.slg = slg;
		this.ops = ops;
		calcOpsSgp(SGPMultCalc.sgpMultR(), SGPMultCalc.sgpMultHR(), SGPMultCalc.sgpMultRBI(), SGPMultCalc.sgpMultSB(), SGPMultCalc.sgpMultOPS());
		calcAvgSgp(SGPMultCalc.sgpMultR(), SGPMultCalc.sgpMultHR(), SGPMultCalc.sgpMultRBI(), SGPMultCalc.sgpMultSB(), SGPMultCalc.sgpMultAVG());

		this.category = category;
		this.created = new Date();		
	}
	
	public FPProjBatter() {}
	
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
    @Column(name = "ab")
	public int getAb() {
		return ab;
	}

    public void setAb(int ab) {
		this.ab = ab;
	}

    @NotNull
    @Column(name = "r")
	public int getR() {
		return r;
	}

    public void setR(int r) {
		this.r = r;
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
    @Column(name = "rbi")
	public int getRbi() {
		return rbi;
	}

    public void setRbi(int rbi) {
		this.rbi = rbi;
	}

    @NotNull
    @Column(name = "sb")
	public int getSb() {
		return sb;
	}

    public void setSb(int sb) {
		this.sb = sb;
	}

    @NotNull
    @Column(name = "avg")
	public double getAvg() {
		return avg;
	}

    public void setAvg(double avg) {
		this.avg = avg;
	}

    @NotNull
    @Column(name = "obp")
	public double getObp() {
		return obp;
	}

    public void setObp(double obp) {
		this.obp = obp;
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
    @Column(name = "dbl")
	public int getDbl() {
		return dbl;
	}

    public void setDbl(int dbl) {
		this.dbl = dbl;
	}

    @NotNull
    @Column(name = "tpl")
	public int getTpl() {
		return tpl;
	}

    public void setTpl(int tpl) {
		this.tpl = tpl;
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
    @Column(name = "k")
	public int getK() {
		return k;
	}

    public void setK(int k) {
		this.k = k;
	}

    @NotNull
    @Column(name = "slg")
	public double getSlg() {
		return slg;
	}

    public void setSlg(double slg) {
		this.slg = slg;
	}

    @NotNull
    @Column(name = "ops")
	public double getOps() {
		return ops;
	}

    public void setOps(double ops) {
		this.ops = ops;
	}

	@NotNull
    @Column(name = "rsgp")
	public double getRSGP() {
		return rSGP;
	}

	public void setRSGP(double rSGP) {
		this.rSGP = rSGP;
	}

	@NotNull
    @Column(name = "hrsgp")
	public double getHrSGP() {
		return hrSGP;
	}

	public void setHrSGP(double hrSGP) {
		this.hrSGP = hrSGP;
	}

	@NotNull
    @Column(name = "rbisgp")
	public double getRbiSGP() {
		return rbiSGP;
	}

	public void setRbiSGP(double rbiSGP) {
		this.rbiSGP = rbiSGP;
	}

	@NotNull
    @Column(name = "sbsgp")
	public double getSbSGP() {
		return sbSGP;
	}

	public void setSbSGP(double sbSGP) {
		this.sbSGP = sbSGP;
	}

	@NotNull
    @Column(name = "opssgp")
	public double getOpsSGP() {
		return opsSGP;
	}

	public void setOpsSGP(double opsSGP) {
		this.opsSGP = opsSGP;
	}

	@NotNull
    @Column(name = "avgsgp")
	public double getAvgSGP() {
		return avgSGP;
	}

	public void setAvgSGP(double avgSGP) {
		this.avgSGP = avgSGP;
	}

    @NotNull
    @Column(name = "opssgptotal")
	public double getOpsTotalSGP() {
		return opsTotalSGP;
	}

    public void setOpsTotalSGP(double opsTotalSGP) {
    	this.opsTotalSGP = opsTotalSGP;
    }

    @NotNull
    @Column(name = "avgsgptotal")
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

	@Column(name = "r_zScore")
    public double getR_zScore() {
		return r_zScore;
	}

	public void setR_zScore(double r_zScore) {
		this.r_zScore = r_zScore;
	}

	@Column(name = "hr_zScore")
	public double getHr_zScore() {
		return hr_zScore;
	}

	public void setHr_zScore(double hr_zScore) {
		this.hr_zScore = hr_zScore;
	}

	@Column(name = "rbi_zScore")
	public double getRbi_zScore() {
		return rbi_zScore;
	}

	public void setRbi_zScore(double rbi_zScore) {
		this.rbi_zScore = rbi_zScore;
	}

	@Column(name = "sb_zScore")
	public double getSb_zScore() {
		return sb_zScore;
	}

	public void setSb_zScore(double sb_zScore) {
		this.sb_zScore = sb_zScore;
	}

	@Column(name = "ops_zScore")
	public double getOps_zScore() {
		return ops_zScore;
	}

	public void setOps_zScore(double ops_zScore) {
		this.ops_zScore = ops_zScore;
	}

	@Column(name = "avg_zScore")
	public double getAvg_zScore() {
		return avg_zScore;
	}

	public void setAvg_zScore(double avg_zScore) {
		this.avg_zScore = avg_zScore;
	}

	@Column(name = "opsFVAAz")
	public double getOpsFVAAz() {
		return opsFVAAz;
	}

	public void setOpsFVAAz(double opsFVAAz) {
		this.opsFVAAz = opsFVAAz;
	}

	@Column(name = "avgFVAAz")
	public double getAvgFVAAz() {
		return avgFVAAz;
	}

	public void setAvgFVAAz(double avgFVAAz) {
		this.avgFVAAz = avgFVAAz;
	}

	@Column(name = "opsFVARz")
	public double getOpsFVARz() {
		return opsFVARz;
	}

	public void setOpsFVARz(double opsFVARz) {
		this.opsFVARz = opsFVARz;
	}

	@Column(name = "avgFVARz")
	public double getAvgFVARz() {
		return avgFVARz;
	}

	public void setAvgFVARz(double avgFVARz) {
		this.avgFVARz = avgFVARz;
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
    	BigDecimal ab = new BigDecimal(this.ab);
    	BigDecimal bb = new BigDecimal(this.bb);
    	BigDecimal h = new BigDecimal(this.h);
    	BigDecimal obpNum = h.add(bb).add(new BigDecimal(2178.8));
    	System.out.println("Player Name: " + name);
    	System.out.println("OBP Num: " + obpNum);
    	BigDecimal obpDenom = ab.add(bb).add(new BigDecimal(6682));
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
    
    public void calcOpsAav(List<FPProjBatter> batterList) {
    	
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
    	for (FPProjBatter b : batterList) {
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
    
    public void calcAvgAav(List<FPProjBatter> batterList) {
    	
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
    	for (FPProjBatter b : batterList) {
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
    
    public void calcFVARz(List<FPProjBatter> batterList, String orderedBy) {
    	int repC = 12;
    	int rep1B = 23;
    	int rep2B = 18;
    	int rep3B = 17;
    	int repSS = 16;
    	int repOF = 62;
    	int repSP = 62;
    	int repRP = 36;
    	
    	if (this.pos.contains("C")) {
    		replacementLevel(batterList, orderedBy, "C", repC);
//    		// sort list by user's custom sgp calculation (desc)
//    		Collections.sort(cList, new Comparator<FPProjBatter>() {
//    			@Override
//    			public int compare(FPProjBatter b1, FPProjBatter b2) {
//    				if (b1.getAvgTotal_zScore() < b2.getAvgTotal_zScore()) return 1;
//    				if (b1.getAvgTotal_zScore() > b2.getAvgTotal_zScore()) return -1;
//    				return 0;
//    			}
//    		});    		
    	} else if (this.pos.contains("SS")) {
    		replacementLevel(batterList, orderedBy, "SS", repSS);
    	} else if (this.pos.contains("2B")) {
    		replacementLevel(batterList, orderedBy, "2B", rep2B);
    	} else if (this.pos.contains("3B")) {
    		replacementLevel(batterList, orderedBy, "3B", rep3B);
    	} else if (this.pos.contains("1B")) {
    		replacementLevel(batterList, orderedBy, "1B", rep1B);
    	} else if (this.pos.contains("OF")) {
    		replacementLevel(batterList, orderedBy, "OF", repOF);
    	}
    }

    public void replacementLevel(List<FPProjBatter> batterList, String orderedBy, String pos, int repLvl) {
		List<FPProjBatter> posList = new ArrayList<FPProjBatter>();
		for (FPProjBatter batter : batterList) {
			if (batter.pos.contains(pos)) {
				posList.add(batter);
			}
		}
		if (orderedBy.equals("AVG")) {
    		double repFVAAz = posList.get(repLvl).getAvgFVAAz();
    		this.avgFVARz = this.avgFVAAz - repFVAAz;
		} else {
    		double repFVAAz = posList.get(repLvl).getOpsFVAAz();
    		this.opsFVARz = this.opsFVAAz - repFVAAz;
		}
    }
    
    //FVARz
    public void calcFVAAz(List<FPProjBatter> batterList) {
    	List<Double> rList = new ArrayList<Double>();
    	List<Double> rbiList = new ArrayList<Double>();
    	List<Double> hrList = new ArrayList<Double>();
    	List<Double> sbList = new ArrayList<Double>();
    	List<Double> avgList = new ArrayList<Double>();
    	List<Double> opsList = new ArrayList<Double>();

    	for (FPProjBatter batter : batterList) {
    		rList.add((double) batter.getR());
    		hrList.add((double) batter.getHr());
    		rbiList.add((double) batter.getRbi());
    		sbList.add((double) batter.getSb());
    		avgList.add(batter.getAvg());
    		opsList.add(batter.getOps());
    	}
    	this.r_zScore = calc_zScore(rList, (double) this.r);
    	this.hr_zScore = calc_zScore(rbiList, (double) this.hr);
    	this.rbi_zScore = calc_zScore(hrList, (double) this.rbi);
    	this.sb_zScore = calc_zScore(sbList, (double) this.sb);
    	this.avg_zScore = calc_zScore(avgList, this.avg);
    	this.ops_zScore = calc_zScore(opsList, this.ops);
    	
    	this.avgFVAAz = this.r_zScore + this.hr_zScore + this.rbi_zScore + this.sb_zScore + this.avg_zScore; 
    	this.opsFVAAz = this.r_zScore + this.hr_zScore + this.rbi_zScore + this.sb_zScore + this.ops_zScore; 
    }
    
	private double calc_zScore(List<Double> statList, double stat) {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal zSum = new BigDecimal(0);

		for (double s : statList) {
			sum = sum.add(new BigDecimal(s));
		}
		BigDecimal mean = sum.divide(new BigDecimal(statList.size()), 4, RoundingMode.HALF_UP);

		for (double s : statList) {
			BigDecimal zS = new BigDecimal(s).subtract(mean).pow(2);
			zSum = zSum.add(zS);
		}
		BigDecimal zMean = zSum.divide(new BigDecimal(statList.size()), 4, RoundingMode.HALF_UP);
		
		BigDecimal stdDev = new BigDecimal(Math.sqrt(zMean.doubleValue()));
		BigDecimal stat_zScore = new BigDecimal(stat).subtract(mean).divide(stdDev);
		
		return stat_zScore.doubleValue();
	}
	
}