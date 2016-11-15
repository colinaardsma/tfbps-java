package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OrderColumn;
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
	private double sgp;
	private String category;

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
	}
	
	public FPProjBatter() {}
	
//			name = playerList[i]
//            team = playerList[i + 1]
//            pos = playerList[i + 2]
//            r = int(playerList[i + 4])
//            hr = int(playerList[i + 5])
//            rbi = int(playerList[i + 6])
//            sb = int(playerList[i + 7])
//            avg = double(playerList[i + 8])
//            obp = double(playerList[i + 9])
//            h = int(playerList[i + 10])
//            double = int(playerList[i + 11])
//            triple = int(playerList[i + 12])
//            bb = int(playerList[i + 13])
//            k = int(playerList[i + 14])
//            slg = double(playerList[i + 15])
//            ops = double(playerList[i + 16])
//            category = "fpprojb"

    @NotNull
    @Column(name = "name")
	public String getName() {
		return name;
	}

    protected void setName(String name) {
		this.name = name;
	}

    @NotNull
    @Column(name = "team")
	public String getTeam() {
		return team;
	}

    protected void setTeam(String team) {
		this.team = team;
	}

    @NotNull
    @Column(name = "pos")
	public String getPos() {
		return pos;
	}

    protected void setPos(String pos) {
		this.pos = pos;
	}

    @NotNull
    @Column(name = "ab")
	public int getAb() {
		return ab;
	}

    protected void setAb(int ab) {
		this.ab = ab;
	}

    @NotNull
    @Column(name = "r")
	public int getR() {
		return r;
	}

    protected void setR(int r) {
		this.r = r;
	}

    @NotNull
    @Column(name = "hr")
	public int getHr() {
		return hr;
	}

    protected void setHr(int hr) {
		this.hr = hr;
	}

    @NotNull
    @Column(name = "rbi")
	public int getRbi() {
		return rbi;
	}

    protected void setRbi(int rbi) {
		this.rbi = rbi;
	}

    @NotNull
    @Column(name = "sb")
	public int getSb() {
		return sb;
	}

    protected void setSb(int sb) {
		this.sb = sb;
	}

    @NotNull
    @Column(name = "avg")
	public double getAvg() {
		return avg;
	}

    protected void setAvg(double avg) {
		this.avg = avg;
	}

    @NotNull
    @Column(name = "obp")
	public double getObp() {
		return obp;
	}

    protected void setObp(double obp) {
		this.obp = obp;
	}

    @NotNull
    @Column(name = "h")
	public int getH() {
		return h;
	}

    protected void setH(int h) {
		this.h = h;
	}

    @NotNull
    @Column(name = "dbl")
	public int getDbl() {
		return dbl;
	}

    protected void setDbl(int dbl) {
		this.dbl = dbl;
	}

    @NotNull
    @Column(name = "tpl")
	public int getTpl() {
		return tpl;
	}

    protected void setTpl(int tpl) {
		this.tpl = tpl;
	}

    @NotNull
    @Column(name = "bb")
	public int getBb() {
		return bb;
	}

    protected void setBb(int bb) {
		this.bb = bb;
	}

    @NotNull
    @Column(name = "k")
	public int getK() {
		return k;
	}

    protected void setK(int k) {
		this.k = k;
	}

    @NotNull
    @Column(name = "slg")
	public double getSlg() {
		return slg;
	}

    protected void setSlg(double slg) {
		this.slg = slg;
	}

    @NotNull
    @Column(name = "ops")
	public double getOps() {
		return ops;
	}

    protected void setOps(double ops) {
		this.ops = ops;
	}

    @NotNull
    @Column(name = "sgp")
	public double getSgp() {
		return sgp;
	}

    protected void setSgp(double sgp) {
    	this.sgp = sgp;
    }

    @NotNull
    @Column(name = "category")
	public String getCategory() {
		return category;
	}

    protected void setCategory(String category) {
		this.category = category;
	}

    protected void calcSgp(double sgpMultR, double sgpMultHR, double sgpMultRBI, double sgpMultSB, double sgpMultOPS) {
        this.sgp = (this.r/sgpMultR)+(this.hr/sgpMultHR)+(this.rbi/sgpMultRBI)+(this.sb/sgpMultSB)+((((((this.obp*(this.ab*1.15))+2178.8)/((this.ab*1.15)+6682))+(((this.slg*this.ab)+2528.5)/(this.ab+5993)))-0.748)/sgpMultOPS);
	}
    
}
