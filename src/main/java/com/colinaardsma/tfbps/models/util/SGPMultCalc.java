package com.colinaardsma.tfbps.models.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SGPMultCalc {

	// http://www.smartfantasybaseball.com/tag/standings-gain-points/
	// http://www.smartfantasybaseball.com/2013/03/create-your-own-fantasy-baseball-rankings-part-5-understanding-standings-gain-points/
	// generic multipliers
	public static double sgpMultR() {
		double sgpMultR = 18.23;
		return sgpMultR;
	}
	
	public static double sgpMultHR() {
		double sgpMultHR = 7.28;
		return sgpMultHR;
	}
	
	public static double sgpMultRBI() {
		double sgpMultRBI = 18.96;
		return sgpMultRBI;
	}
	
	public static double sgpMultSB() {
		double sgpMultSB = 7.16;
		return sgpMultSB;
	}
	
	public static double sgpMultOPS() {
		double sgpMultOPS = 0.006;
		return sgpMultOPS;
	}
	
	public static double sgpMultAVG() {
		double sgpMultAVG = 0.00178;
		return sgpMultAVG;
	}
	
	public static double sgpMultW() {
		double sgpMultW = 2.78;
		return sgpMultW;
	}
	
	public static double sgpMultSV() {
		double sgpMultSV = 6.47;
		return sgpMultSV;
	}
	
	public static double sgpMultK() {
		double sgpMultK = 30.38;
		return sgpMultK;
	}
	
	public static double sgpMultERA() {
		double sgpMultERA = -0.0699;
		return sgpMultERA;
	}

	public static double sgpMultWHIP() {
		double sgpMultWHIP = -0.0128;
		return sgpMultWHIP;
	}
	
	// league specific multipliers	

	public static double calcRSGPMult(List<Integer> rs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcHrSGPMult(List<Integer> hrs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcRbiSGPMult(List<Integer> rbis) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcSbSGPMult(List<Integer> sbs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcOpsSGPMult(List<Double> opss) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcAvgSGPMult(List<Double> avgs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < avgs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(avgs.get(i + 1)).subtract(BigDecimal.valueOf(avgs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average SGP
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double avgSGP = Double.parseDouble(avg.toString());
		
		return avgSGP;
	}

	public static double calcWSGPMult(List<Integer> wins) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcSvSGPMult(List<Integer> svs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcKSGPMult(List<Integer> ks) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcEraSGPMult(List<Double> eras) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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

	public static double calcWhipSGPMult(List<Double> whips) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
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
