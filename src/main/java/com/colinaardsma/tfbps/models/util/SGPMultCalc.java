package com.colinaardsma.tfbps.models.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.colinaardsma.tfbps.models.YahooRotoTeam;

public class SGPMultCalc {

	// generic multipliers
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
	
	// league specific multipliers	

	public static double calcRSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> rs = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			rs.add(team.getRStats());
		}
		Collections.sort(rs); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < rs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(rs.get(i + 1)).subtract(BigDecimal.valueOf(rs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double rSGP = Double.parseDouble(avg.toString());
		
		return rSGP;
	}

	public static double calcHrSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> hrs = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			hrs.add(team.getHrStats());
		}
		Collections.sort(hrs); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < hrs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(hrs.get(i + 1)).subtract(BigDecimal.valueOf(hrs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double hrSGP = Double.parseDouble(avg.toString());
		
		return hrSGP;
	}

	public static double calcRbiSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> rbis = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			rbis.add(team.getRbiStats());
		}
		Collections.sort(rbis); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < rbis.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(rbis.get(i + 1)).subtract(BigDecimal.valueOf(rbis.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double rbiSGP = Double.parseDouble(avg.toString());
		
		return rbiSGP;
	}

	public static double calcSbSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> sbs = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			sbs.add(team.getSbStats());
		}
		Collections.sort(sbs); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < sbs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(sbs.get(i + 1)).subtract(BigDecimal.valueOf(sbs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double sbSGP = Double.parseDouble(avg.toString());
		
		return sbSGP;
	}

	public static double calcOpsSGPMult(List<YahooRotoTeam> teams) {
		List<Double> opss = new ArrayList<Double>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			opss.add(team.getOpsStats());
		}
		Collections.sort(opss); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < opss.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(opss.get(i + 1)).subtract(BigDecimal.valueOf(opss.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double opsSGP = Double.parseDouble(avg.toString());
		
		return opsSGP;
	}

	public static double calcWSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> wins = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			wins.add(team.getWStats());
		}
		Collections.sort(wins); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < wins.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(wins.get(i + 1)).subtract(BigDecimal.valueOf(wins.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double winSGP = Double.parseDouble(avg.toString());
		
		return winSGP;
	}

	public static double calcSvSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> svs = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			svs.add(team.getSvStats());
		}
		Collections.sort(svs); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < svs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(svs.get(i + 1)).subtract(BigDecimal.valueOf(svs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double svSGP = Double.parseDouble(avg.toString());
		
		return svSGP;
	}

	public static double calcKSGPMult(List<YahooRotoTeam> teams) {
		List<Integer> ks = new ArrayList<Integer>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			ks.add(team.getKStats());
		}
		Collections.sort(ks); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < ks.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(ks.get(i + 1)).subtract(BigDecimal.valueOf(ks.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double kSGP = Double.parseDouble(avg.toString());
		
		return kSGP;
	}

	public static double calcEraSGPMult(List<YahooRotoTeam> teams) {
		List<Double> eras = new ArrayList<Double>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			eras.add(team.getEraStats());
		}
		Collections.sort(eras); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < eras.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(eras.get(i)).subtract(BigDecimal.valueOf(eras.get(i + 1))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double eraSGP = Double.parseDouble(avg.toString());
		
		return eraSGP;
	}

	public static double calcWhipSGPMult(List<YahooRotoTeam> teams) {
		List<Double> whips = new ArrayList<Double>();
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// pull run values from team
		for (YahooRotoTeam team : teams) {
			whips.add(team.getWhipStats());
		}
		Collections.sort(whips); // sort list from smallest to largest
		
		// calculate difference between stats at each rank
		for (int i = 0; i < whips.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(whips.get(i)).subtract(BigDecimal.valueOf(whips.get(i + 1))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double whipSGP = Double.parseDouble(avg.toString());
		
		return whipSGP;
	}


}
