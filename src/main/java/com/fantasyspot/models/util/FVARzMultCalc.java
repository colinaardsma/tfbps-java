package com.fantasyspot.models.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FVARzMultCalc {

	// http://www.fangraphs.com/plus/auction-values-for-all-three-ottoneu-formats/
	// http://www.fangraphs.com/fantasy/value-above-replacement-part-one/
	// generic multipliers
	public static double fvarzMultAB() {
		double fvarzMultAB = -1;
		return fvarzMultAB;
	}

	public static double fvarzMultR() {
		double fvarzMultR = 18.23;
		return fvarzMultR;
	}
	
	public static double fvarzMultHR() {
		double fvarzMultHR = 7.28;
		return fvarzMultHR;
	}
	
	public static double fvarzMultRBI() {
		double fvarzMultRBI = 18.96;
		return fvarzMultRBI;
	}
	
	public static double fvarzMultSB() {
		double fvarzMultSB = 7.16;
		return fvarzMultSB;
	}
	
	public static double fvarzpMultOPS() {
		double fvarzMultOPS = 0.006;
		return fvarzMultOPS;
	}
	
	public static double fvarzMultAVG() {
		double fvarzMultAVG = 0.00178;
		return fvarzMultAVG;
	}
	
	public static double fvarzMultW() {
		double fvarzMultW = 2.78;
		return fvarzMultW;
	}
	
	public static double fvarzMultSV() {
		double fvarzMultSV = 6.47;
		return fvarzMultSV;
	}
	
	public static double fvarzMultK() {
		double fvarzMultK = 30.38;
		return fvarzMultK;
	}
	
	public static double fvarzMultERA() {
		double fvarzMultERA = -0.0699;
		return fvarzMultERA;
	}

	public static double fvarzMultWHIP() {
		double fvarzMultWHIP = -0.0128;
		return fvarzMultWHIP;
	}
	
	// league specific multipliers	

	// https://www.mathsisfun.com/data/standard-deviation-formulas.html
	public static double calcRzScore(List<Integer> rs, int run) {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal zSum = new BigDecimal(0);

		for (int r : rs) {
			sum = sum.add(new BigDecimal(r));
		}
		BigDecimal mean = sum.divide(new BigDecimal(rs.size()), 4, RoundingMode.HALF_UP);

		for (int r : rs) {
			BigDecimal zR = new BigDecimal(r).subtract(mean).pow(2);
			zSum = zSum.add(zR);
		}
		BigDecimal zMean = zSum.divide(new BigDecimal(rs.size()), 4, RoundingMode.HALF_UP);
		
		BigDecimal stdDev = new BigDecimal(Math.sqrt(zMean.doubleValue()));
		
		BigDecimal rZScore = new BigDecimal(run).subtract(mean).divide(stdDev);
		
		return rZScore.doubleValue();
	}
	
	public static double calcRFVARzMult(List<Integer> rs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate mean
		for (int r : rs) {
			sum = sum.add(new BigDecimal(r));
		}
		BigDecimal mean = sum.divide(new BigDecimal(rs.size()), 4, RoundingMode.HALF_UP);
		
		for (int i = 0; i < rs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(rs.get(i + 1)).subtract(BigDecimal.valueOf(rs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double rFVARz = Double.parseDouble(avg.toString());
		
		return rFVARz;
	}

	public static double calcHrFVARzMult(List<Integer> hrs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < hrs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(hrs.get(i + 1)).subtract(BigDecimal.valueOf(hrs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double hrFVARz = Double.parseDouble(avg.toString());
		
		return hrFVARz;
	}

	public static double calcRbiFVARzMult(List<Integer> rbis) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < rbis.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(rbis.get(i + 1)).subtract(BigDecimal.valueOf(rbis.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double rbiFVARz = Double.parseDouble(avg.toString());
		
		return rbiFVARz;
	}

	public static double calcSbFVARzMult(List<Integer> sbs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < sbs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(sbs.get(i + 1)).subtract(BigDecimal.valueOf(sbs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double sbFVARz = Double.parseDouble(avg.toString());
		
		return sbFVARz;
	}

	public static double calcOpsFVARzMult(List<Double> opss) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < opss.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(opss.get(i + 1)).subtract(BigDecimal.valueOf(opss.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double opsFVARz = Double.parseDouble(avg.toString());
		
		return opsFVARz;
	}

	public static double calcAvgFVARzMult(List<Double> avgs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < avgs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(avgs.get(i + 1)).subtract(BigDecimal.valueOf(avgs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double avgFVARz = Double.parseDouble(avg.toString());
		
		return avgFVARz;
	}

	public static double calcWFVARzMult(List<Integer> wins) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < wins.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(wins.get(i + 1)).subtract(BigDecimal.valueOf(wins.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double winFVARz = Double.parseDouble(avg.toString());
		
		return winFVARz;
	}

	public static double calcSvFVARzMult(List<Integer> svs) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < svs.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(svs.get(i + 1)).subtract(BigDecimal.valueOf(svs.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double svFVARz = Double.parseDouble(avg.toString());
		
		return svFVARz;
	}

	public static double calcKFVARzMult(List<Integer> ks) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < ks.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(ks.get(i + 1)).subtract(BigDecimal.valueOf(ks.get(i))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double kFVARz = Double.parseDouble(avg.toString());
		
		return kFVARz;
	}

	public static double calcEraFVARzMult(List<Double> eras) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < eras.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(eras.get(i)).subtract(BigDecimal.valueOf(eras.get(i + 1))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double eraFVARz = Double.parseDouble(avg.toString());
		
		return eraFVARz;
	}

	public static double calcWhipFVARzMult(List<Double> whips) {
		List<BigDecimal> diff = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		
		// calculate difference between stats at each rank
		for (int i = 0; i < whips.size() - 1; i++) {
			diff.add(BigDecimal.valueOf(whips.get(i)).subtract(BigDecimal.valueOf(whips.get(i + 1))));
		}
		Collections.sort(diff); // sort list from smallest to largest
		diff.remove(diff.get(0)); // remove smallest value
		diff.remove(diff.get(diff.size() - 1)); // remove largest value
		
		// calculate average FVARz
		for (int j = 0; j < diff.size(); j++) {
			sum = sum.add(diff.get(j));
		}
		BigDecimal avg = sum.divide(BigDecimal.valueOf(diff.size()), 4, RoundingMode.HALF_EVEN);
		double whipFVARz = Double.parseDouble(avg.toString());
		
		return whipFVARz;
	}


}
