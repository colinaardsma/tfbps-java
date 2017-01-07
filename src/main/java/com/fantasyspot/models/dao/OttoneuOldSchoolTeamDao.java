package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.OttoneuOldSchoolTeam;
import com.fantasyspot.models.User;

@Transactional
@Repository
public interface OttoneuOldSchoolTeamDao extends CrudRepository<OttoneuOldSchoolTeam, Integer> {
	
	List<OttoneuOldSchoolTeam> findAll(); // get all teams
	List<OttoneuOldSchoolTeam> findByLeagueKey(int leagueKey); // get a list of teams in a league with league key only
	List<OttoneuOldSchoolTeam> findByLeagueNumberAndSeason(int leagueNumber, int season); // get a list of teams in a league with league number and season
	List<OttoneuOldSchoolTeam> findByOttoneuOldSchoolLeague(OttoneuOldSchoolLeague league); // get a list of teams in a league by league
	List<OttoneuOldSchoolTeam> findByRank(int rank); // get a list of teams in any league by rank
	List<OttoneuOldSchoolTeam> findByUser(User user); // get a list of teams by user with user object
	List<OttoneuOldSchoolTeam> findByTeamNumber(int teamNumber); // get team with team number
	OttoneuOldSchoolTeam findByTeamNumberAndSeason(int teamNumber, int season); // get team with team key
	OttoneuOldSchoolTeam findByTeamKey(String teamKey); // get team with team key
	OttoneuOldSchoolTeam findByUid(int uid); // get team with unique id only

}
