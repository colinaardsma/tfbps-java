package com.colinaardsma.tfbps.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "KeeperPicks")
public class KeeperPicks extends AbstractEntity {
	
	private String name;
	private String team;
	private String pos;
	private String playerKey;
	private int pick;
	private String posType;

	private Date created;
	
	// join variables
	private User user;
	private FPProjBatter batter;
	private FPProjPitcher pitcher;
	private YahooRotoLeague yahooRotoLeague;
	private YahooRotoTeam yahooRotoTeam;
	private OttoneuOldSchoolLeague ottoneuOldSchoolLeague;
	private OttoneuOldSchoolTeam ottoneuOldSchoolTeam;
	private UserCustomRankingsB userCustomRankingsB;
	private UserCustomRankingsP userCustomRankingsP;

	// yahoo roto batter
	public KeeperPicks(String name, String team, String pos, String playerKey, FPProjBatter batter, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam, int pick, String posType) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.playerKey = playerKey;
		this.batter = batter;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.pick = pick;
		this.posType = posType;
		this.created = new Date();
	}
	
	// yahoo roto pitcher
	public KeeperPicks(String name, String team, String pos, String playerKey, FPProjPitcher pitcher, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam, int pick, String posType) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.playerKey = playerKey;
		this.pitcher = pitcher;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.pick = pick;
		this.posType = posType;
		this.created = new Date();
	}
	
	// yahoo roto player not in projections
	public KeeperPicks(String name, String team, String pos, String playerKey, YahooRotoLeague yahooRotoLeague, YahooRotoTeam yahooRotoTeam, int pick, String posType) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.playerKey = playerKey;
		this.yahooRotoLeague = yahooRotoLeague;
		this.yahooRotoTeam = yahooRotoTeam;
		if (yahooRotoTeam.getUser() != null) {
			this.user = yahooRotoTeam.getUser();
		}
		this.pick = pick;
		this.posType = posType;
		this.created = new Date();
	}
	
// http://www.fangraphs.com/fantasy/auction-values-for-ottoneu-leagues/
	public KeeperPicks(String name, String team, String pos, String playerKey, FPProjBatter batter, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam, int pick, String posType) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.playerKey = playerKey;
		this.batter = batter;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.pick = pick;
		this.posType = posType;
		this.created = new Date();
	}
	
	public KeeperPicks(String name, String team, String pos, String playerKey, FPProjPitcher pitcher, OttoneuOldSchoolLeague ottoneuOldSchoolLeague, OttoneuOldSchoolTeam ottoneuOldSchoolTeam, int pick, String posType) {
		this.name = name;
		this.team = team;
		this.pos = pos;
		this.playerKey = playerKey;
		this.pitcher = pitcher;
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
		if (ottoneuOldSchoolTeam.getUser() != null) {
			this.user = ottoneuOldSchoolTeam.getUser();
		}
		this.pick = pick;
		this.posType = posType;
		this.created = new Date();
	}
	
	public KeeperPicks() {}
	
	@Column(name = "name")
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "team")
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	@Column(name = "pos")
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@Column(name = "pick")
	public int getPick() {
		return pick;
	}

	public void setPick(int pick) {
		this.pick = pick;
	}

    @Column(name = "posType")
	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType;
	}

	@NotNull
    @Column(name = "playerKey")
	public String getPlayerKey() {
		return playerKey;
	}

	public void setPlayerKey(String playerKey) {
		this.playerKey = playerKey;
	}

	@NotNull
    @Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public FPProjBatter getBatter() {
		return batter;
	}

	public void setBatter(FPProjBatter batter) {
		this.batter = batter;
	}

	@ManyToOne
	public FPProjPitcher getPitcher() {
		return pitcher;
	}

	public void setPitcher(FPProjPitcher pitcher) {
		this.pitcher = pitcher;
	}

	@ManyToOne
	public YahooRotoLeague getYahooRotoLeague() {
		return yahooRotoLeague;
	}

	public void setYahooRotoLeague(YahooRotoLeague yahooRotoLeague) {
		this.yahooRotoLeague = yahooRotoLeague;
	}

	@ManyToOne
	public YahooRotoTeam getYahooRotoTeam() {
		return yahooRotoTeam;
	}

	public void setYahooRotoTeam(YahooRotoTeam yahooRotoTeam) {
		this.yahooRotoTeam = yahooRotoTeam;
	}

	@ManyToOne
	public OttoneuOldSchoolLeague getOttoneuOldSchoolLeague() {
		return ottoneuOldSchoolLeague;
	}

	public void setOttoneuOldSchoolLeague(OttoneuOldSchoolLeague ottoneuOldSchoolLeague) {
		this.ottoneuOldSchoolLeague = ottoneuOldSchoolLeague;
	}

	@ManyToOne
    public OttoneuOldSchoolTeam getOttoneuOldSchoolTeam() {
		return ottoneuOldSchoolTeam;
	}

	public void setOttoneuOldSchoolTeam(OttoneuOldSchoolTeam ottoneuOldSchoolTeam) {
		this.ottoneuOldSchoolTeam = ottoneuOldSchoolTeam;
	}

	@OneToOne
	public UserCustomRankingsB getUserCustomRankingsB() {
		return userCustomRankingsB;
	}

	public void setUserCustomRankingsB(UserCustomRankingsB userCustomRankingsB) {
		this.userCustomRankingsB = userCustomRankingsB;
	}

	@OneToOne
	public UserCustomRankingsP getUserCustomRankingsP() {
		return userCustomRankingsP;
	}

	public void setUserCustomRankingsP(UserCustomRankingsP userCustomRankingsP) {
		this.userCustomRankingsP = userCustomRankingsP;
	}

}
