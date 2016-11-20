package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.colinaardsma.tfbps.models.util.SGPMultCalc;


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
	private double sgp;
	private String category;

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

    protected void calcSgp(double sgpMultW, double sgpMultSV, double sgpMultK, double sgpMultERA, double sgpMultWHIP) {
        double wSGP = this.w / sgpMultW;
        double svSGP = this.sv / sgpMultSV;
        double kSGP = this.k / sgpMultK;
        double eraSGP = ((475 + this.er) * 9 / (1192 + this.ip) - 3.59) / sgpMultERA;
        double whipSGP = ((1466 + this.h + this.bb) / (1192 + this.ip) - 1.23) / sgpMultWHIP;
        
    	this.sgp = wSGP + svSGP + kSGP  +eraSGP + whipSGP;
    }
    
}
