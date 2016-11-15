package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fpprojb")
public class Batter extends AbstractEntity {
	
	private String name;
	private String team;
	private String pos;
	private int ab;
	private int r;
	private int hr;
	private int rbi;
	private int sb;
	private float avg;
	private float obp;
	private int h;
	private int dbl;
	private int tpl;
	private int bb;
	private int k;
	private float slg;
	private float ops;
	private float sgp;
	private String category;

	public Batter(String name, String team, String pos, int ab, int r, int hr, int rbi, int sb, float avg, float obp, int h, int dbl, int tpl, int bb, int k, float slg, float ops, String category) {
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
		this.sgp = this.setSgp();
		this.category = category;
	}
	
	public Batter() {}
	
//			name = playerList[i]
//            team = playerList[i + 1]
//            pos = playerList[i + 2]
//            r = int(playerList[i + 4])
//            hr = int(playerList[i + 5])
//            rbi = int(playerList[i + 6])
//            sb = int(playerList[i + 7])
//            avg = float(playerList[i + 8])
//            obp = float(playerList[i + 9])
//            h = int(playerList[i + 10])
//            double = int(playerList[i + 11])
//            triple = int(playerList[i + 12])
//            bb = int(playerList[i + 13])
//            k = int(playerList[i + 14])
//            slg = float(playerList[i + 15])
//            ops = float(playerList[i + 16])
//            category = "fpprojb"

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
	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

    @NotNull
    @Column(name = "obp")
	public float getObp() {
		return obp;
	}

	public void setObp(float obp) {
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
	public float getSlg() {
		return slg;
	}

	public void setSlg(float slg) {
		this.slg = slg;
	}

    @NotNull
    @Column(name = "ops")
	public float getOps() {
		return ops;
	}

	public void setOps(float ops) {
		this.ops = ops;
	}

    @NotNull
    @Column(name = "sgp")
	public float getSgp() {
		return sgp;
	}

	public void setSgp(float sgpMultR, float sgpMultHR, float sgpMultRBI, float sgpMultSB, float sgpMultOPS) {
        this.sgp = (float) ((this.r/sgpMultR)+(this.hr/sgpMultHR)+(this.rbi/sgpMultRBI)+(this.sb/sgpMultSB)+((((((this.obp*(this.ab*1.15))+2178.8)/((this.ab*1.15)+6682))+(((this.slg*this.ab)+2528.5)/(this.ab+5993)))-0.748)/sgpMultOPS));
	}

    @NotNull
    @Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
