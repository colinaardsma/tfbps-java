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
@Table(name = "fpprojb")
public class FPProjBatter extends AbstractEntity {
	
	private String name;
	private String team;
	private String pos;
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
	private double rSGP;
	private double hrSGP;
	private double rbiSGP;
	private double sbSGP;
	private double opsSGP;
	private double sgp;
	private String category;
	private Date created;
	
	private List<UserBatterSGP> userBatterSGP;

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
		calcSgp(SGPMultCalc.sgpMultR(), SGPMultCalc.sgpMultHR(), SGPMultCalc.sgpMultRBI(), SGPMultCalc.sgpMultSB(), SGPMultCalc.sgpMultOPS());

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
    @Column(name = "sgp")
	public double getSgp() {
		return sgp;
	}

    public void setSgp(double sgp) {
    	this.sgp = sgp;
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
    @JoinColumn(name = "fpprojb_uid")
    public List<UserBatterSGP> getUserBatterSGP() {
    	return userBatterSGP;
    }
    
    @SuppressWarnings("unused")
    private void setUserBatterSGP(List<UserBatterSGP> userBatterSGP) {
    	this.userBatterSGP = userBatterSGP;
    }
    
    protected void calcSgp(double sgpMultR, double sgpMultHR, double sgpMultRBI, double sgpMultSB, double sgpMultOPS) {
    	BigDecimal r = new BigDecimal(this.r).divide(new BigDecimal(sgpMultR), 4, RoundingMode.HALF_EVEN);
    	this.rSGP = r.doubleValue();
    	BigDecimal hr = new BigDecimal(this.hr).divide(new BigDecimal(sgpMultHR), 4, RoundingMode.HALF_EVEN);
    	this.hrSGP = hr.doubleValue();
    	BigDecimal rbi = new BigDecimal(this.rbi).divide(new BigDecimal(sgpMultRBI), 4, RoundingMode.HALF_EVEN);
    	this.rbiSGP = rbi.doubleValue();
    	BigDecimal sb = new BigDecimal(this.sb).divide(new BigDecimal(sgpMultSB), 4, RoundingMode.HALF_EVEN);
    	this.sbSGP = sb.doubleValue();
    	
    	// ops
    	BigDecimal ab = new BigDecimal(this.ab).multiply(new BigDecimal(1.15));	
    	BigDecimal obpNum = new BigDecimal(this.obp).multiply(ab).add(new BigDecimal(2178.8));
    	BigDecimal obpDenom = ab.add(new BigDecimal(6682));
    	BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal slgNum = new BigDecimal(this.slg).multiply(new BigDecimal(this.ab)).add(new BigDecimal(2528.5));
    	BigDecimal slgDenom = new BigDecimal(this.ab).add(new BigDecimal(5993));
    	BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_EVEN);
    	BigDecimal ops = obp.divide(slg, 2, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.748));
    	ops = ops.divide(new BigDecimal(sgpMultOPS), 4, RoundingMode.HALF_EVEN);
    	this.opsSGP = ops.doubleValue();
    			 	
    	this.sgp = r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
	}
    
//    public double caclLeagueHistSGP(double rHistSGPMult, double hrHistSGPMult, double rbiHistSGPMult, double sbHistSGPMult, double opsHistSGPMult) {
//    	BigDecimal r = new BigDecimal(this.r).divide(new BigDecimal(rHistSGPMult), 4, RoundingMode.HALF_EVEN);
//    	BigDecimal hr = new BigDecimal(this.hr).divide(new BigDecimal(hrHistSGPMult), 4, RoundingMode.HALF_EVEN);
//    	BigDecimal rbi = new BigDecimal(this.rbi).divide(new BigDecimal(rbiHistSGPMult), 4, RoundingMode.HALF_EVEN);
//    	BigDecimal sb = new BigDecimal(this.sb).divide(new BigDecimal(sbHistSGPMult), 4, RoundingMode.HALF_EVEN);
//    	
//    	// ops
//    	BigDecimal ab = new BigDecimal(this.ab).multiply(new BigDecimal(1.15));	
//    	BigDecimal obpNum = new BigDecimal(this.obp).multiply(ab).add(new BigDecimal(2178.8));
//    	BigDecimal obpDenom = ab.add(new BigDecimal(6682));
//    	BigDecimal obp = obpNum.divide(obpDenom, 4, RoundingMode.HALF_EVEN);
//    	BigDecimal slgNum = new BigDecimal(this.slg).multiply(new BigDecimal(this.ab)).add(new BigDecimal(2528.5));
//    	BigDecimal slgDenom = new BigDecimal(this.ab).add(new BigDecimal(5993));
//    	BigDecimal slg = slgNum.divide(slgDenom, 4, RoundingMode.HALF_EVEN);
//    	BigDecimal ops = obp.divide(slg, 2, RoundingMode.HALF_EVEN).subtract(new BigDecimal(0.748));
//    	ops = ops.divide(new BigDecimal(opsHistSGPMult), 4, RoundingMode.HALF_EVEN);
//    			 	
//    	return r.add(hr.add(rbi.add(sb.add(ops)))).doubleValue();
//    }
//    
}
