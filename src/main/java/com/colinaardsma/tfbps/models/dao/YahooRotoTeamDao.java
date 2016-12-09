package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.YahooRotoTeam;

@Transactional
@Repository
public interface YahooRotoTeamDao extends CrudRepository<YahooRotoTeam, Integer> {
	
	List<YahooRotoTeam> findAll(); // get all teams
	List<YahooRotoTeam> findByLeagueKey(String leagueKey); // get a list of teams in a league with league key only
	List<YahooRotoTeam> findByYahooRotoLeague(YahooRotoLeague league); // get a list of teams in a league by league
	List<YahooRotoTeam> findByRank(int rank); // get a list of teams in any league by rank
	List<YahooRotoTeam> findByTeamGUID(String teamGUID); // get a list of teams by user with Yahoo GUID
	List<YahooRotoTeam> findByUser(User user); // get a list of teams by user with user object
	YahooRotoTeam findByTeamKey(String teamKey); // get team with team key
	YahooRotoTeam findByUid(int uid); // get team with unique id only

}
