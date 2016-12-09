package com.colinaardsma.tfbps.models.util;

import java.util.ArrayList;
import java.util.List;

import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.YahooRotoTeam;

public class SGPMultCalc {
	public static double sgpMultR() {
		double sgpMultR = 24.6;
		return sgpMultR;
	}
	
	public static double sgpMultHR() {
		double sgpMultHR = 10.4;
		return sgpMultHR;
	}
	
	public static double sgpMultRBI() {
		double sgpMultRBI = 24.6;
		return sgpMultRBI;
	}
	
	public static double sgpMultSB() {
		double sgpMultSB = 9.4;
		return sgpMultSB;
	}
	
	public static double sgpMultOPS() {
		double sgpMultOPS = 0.006;
		return sgpMultOPS;
	}
	
	
	public static double sgpMultW() {
		double sgpMultW = 3.03;
		return sgpMultW;
	}
	
	public static double sgpMultSV() {
		double sgpMultSV = 9.95;
		return sgpMultSV;
	}
	
	public static double sgpMultK() {
		double sgpMultK = 39.3;
		return sgpMultK;
	}
	
	public static double sgpMultERA() {
		double sgpMultERA = -0.076;
		return sgpMultERA;
	}

	public static double sgpMultWHIP() {
		double sgpMultWHIP = -0.015;
		return sgpMultWHIP;
	}
	
	public static double calcRSGP(YahooRotoLeague league) {
		List<YahooRotoTeam> teams = league.getYahooRotoTeams();
		List<Integer> runs = new ArrayList<Integer>();
		
		for (YahooRotoTeam team : teams) {
			runs.add(team.getrStats());
		}
		// calculate difference between stat values of each rank
		
		double rSGP;
		return rSGP;
	}

}
