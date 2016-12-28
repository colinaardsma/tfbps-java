package com.colinaardsma.tfbps.models.util;

public class TeamNameNormalization {

	public static String Normalize (String teamName) {
		teamName.toUpperCase();
		String normalName = null;

		switch (teamName) {
		case "ATLANTA" :
		case "BRAVES" :
		case "ATLANTA BRAVES" : normalName = "ATL";
		break;
		case "ARIZONA" :
		case "DIAMONDBACKS" :
		case "ARIZONA DIAMONDBACKS" : normalName = "ARI";
		break;
		case "BALTIMORE" :
		case "ORIOLES" :
		case "BALTIMORE ORIOLES" : normalName = "BAL";
		break;
		case "BOSTON" :
		case "RED SOX" :
		case "BOSTON RED SOX" : normalName = "BOS";
		break;
		case "CHN" :
		case "CUBS" :
		case "CHICAGO CUBS" : normalName = "CHC";
		break;
		case "CHA" :
		case "CWS" :
		case "WHITE SOX" :
		case "CHICAGO WHITE SOX" : normalName = "CHW";
		break;
		case "CINCINNATI" :
		case "REDS" :
		case "CINCINNATI REDS" : normalName = "CIN";
		break;
		case "CLEVELAND" :
		case "INDIANS" :
		case "CLEVELAND INDIANS" : normalName = "CLE";
		break;
		case "COLORADO" :
		case "ROCKIES" :
		case "COLORADO ROCKIES" : normalName = "COL";
		break;
		case "DETROIT" :
		case "TIGERS" :
		case "DETROIT TIGERS" : normalName = "DET";
		break;
		case "HOUSTON" :
		case "ASTROS" :
		case "HOUSTON ASTROS" : normalName = "HOU";
		break;
		case "AN" :
		case "ANA" :
		case "ANGELS" :
		case "ANAHEIM ANGELS" :
		case "LOS ANGELES ANGELS OF ANAHEIM" :
		case "LA ANGELS" :
		case "LOS ANGELES ANGELS" : normalName = "LAA";
		break;
		case "LAN" :
		case "DODGERS" :
		case "LA DODGERS" :
		case "LOS ANGELES DODGERS" : normalName = "LAD";
		break;
		case "KCA" :
		case "KCR" :
		case "KANSAS CITY" :
		case "ROYALS" :
		case "KANSAS CITY ROYALS" : normalName = "KC";
		break;
		case "FL" :
		case "FLO" :
		case "MIAMI" :
		case "MARLINS" :
		case "MIAMI MARLINS" : normalName = "MIA";
		break;
		case "MILWAUKEE" :
		case "BREWERS" :
		case "MILWAUKEE BREWERS" : normalName = "MIL";
		break;
		case "MINNESOTA" :
		case "TWINS" :
		case "MINNESOTA TWINS" : normalName = "MIN";
		break;
		case "NYN" :
		case "METS" :
		case "NY METS" :
		case "NEW YORK METS" : normalName = "NYM";
		break;
		case "NYA" :
		case "YANKEES" :
		case "NY YANKEES" :
		case "NEW YORK YANKEES" : normalName = "NYY";
		break;
		case "OAKLAND" :
		case "AS" :
		case "A'S" :
		case "OAKLAND AS" :
		case "OAKLAND A'S" : normalName = "OAK";
		break;
		case "PHILADELPHIA" :
		case "PHILLIES" :
		case "PHILADELPHIA PHILLIES" : normalName = "PHI";
		break;
		case "PITTSBURGH" :
		case "PIRATES" :
		case "PITTSBURGH PIRATES" : normalName = "PIT";
		break;
		case "SDN" :
		case "SDP" :
		case "SAN DIEGO" :
		case "PADRES" :
		case "SAN DIEGO PADRES" : normalName = "SD";
		break;
		case "SEATTLE":
		case "MARINERS" :
		case "SEATTLE MARINERS" : normalName = "SEA";
		break;
		case "SFN" :
		case "SFG" :
		case "SAN FRANCISCO" :
		case "GIANTS" :
		case "SF GIANTS" :
		case "SAN FRANCISCO GIANTS" : normalName = "SF";
		break;
		case "SLN" :
		case "SAINT LOUIS" :
		case "ST LOUIS" :
		case "CARDINALS" :
		case "SAINT LOUIS CARDINALS" :
		case "ST LOUIS CARDINALS" : normalName = "STL";
		break;
		case "TEXAS" :
		case "RANGERS" :
		case "TEXAS RANGERS" : normalName = "TEX";
		break;
		case "TAM" :
		case "TBA" :
		case "TBR" :
		case "TAMPA BAY" :
		case "DEVIL RAYS" :
		case "RAYS" :
		case "TB DEVIL RAYS" :
		case "TB RAYS" :
		case "TAMPA BAY DEVIL RAYS" :
		case "TAMPA BAY RAYS" : normalName = "TB";
		break;
		case "TORONTO" :
		case "BLUE JAYS" :
		case "TORONTO BLUE JAYS" : normalName = "TOR";
		break;
		case "WSH" :
		case "WSN" :
		case "WASHINGTON" :
		case "NATIONALS" :
		case "WASHINGTON NATIONALS" : normalName = "WAS";
		break;
		case "FREE AGENT" : normalName = "FA";
		default : normalName = teamName;
		break;
		}
		
		return normalName;
	}
}
