package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.OttoneuTeam;
import com.colinaardsma.tfbps.models.User;

@Transactional
@Repository
public interface OttoneuTeamDao extends CrudRepository<OttoneuTeam, Integer> {
	
	List<OttoneuTeam> findAll(); // get all teams
	List<OttoneuTeam> findByLeagueKey(int leagueKey); // get a list of teams in a league with league key only
	List<OttoneuTeam> findByLeagueNumberAndSeason(int leagueNumber, int season); // get a list of teams in a league with league number and season
	List<OttoneuTeam> findByOttoneuOldSchoolLeague(OttoneuOldSchoolLeague league); // get a list of teams in a league by league
	List<OttoneuTeam> findByRank(int rank); // get a list of teams in any league by rank
	List<OttoneuTeam> findByUser(User user); // get a list of teams by user with user object
	List<OttoneuTeam> findByTeamNumber(int teamNumber); // get team with team number
	OttoneuTeam findByTeamNumberAndSeason(int teamNumber, int season); // get team with team key
	OttoneuTeam findByTeamKey(String teamKey); // get team with team key
	OttoneuTeam findByUid(int uid); // get team with unique id only

}
