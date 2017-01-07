package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.FPProjBatter;
import com.fantasyspot.models.FPProjPitcher;
import com.fantasyspot.models.KeeperPicks;
import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.OttoneuOldSchoolTeam;
import com.fantasyspot.models.User;
import com.fantasyspot.models.YahooRotoLeague;
import com.fantasyspot.models.YahooRotoTeam;

@Transactional
@Repository
public interface KeeperPicksDao extends CrudRepository<KeeperPicks, Integer> {
	
	List<KeeperPicks> findAll(); // get all costs
	List<KeeperPicks> findByUser(User user);

	List<KeeperPicks> findByBatter(FPProjBatter batter);
	List<KeeperPicks> findByPitcher(FPProjPitcher pitcher);

	// Yahoo Roto Leagues
	List<KeeperPicks> findByYahooRotoTeam(YahooRotoTeam yahooRotoTeam);
	List<KeeperPicks> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	List<KeeperPicks> findByYahooRotoLeague(YahooRotoLeague league);
//	List<KeeperRounds> findByPlayerKey(String playerKey);
	KeeperPicks findByBatterAndYahooRotoLeague(FPProjBatter batter, YahooRotoLeague league);
	KeeperPicks findByPitcherAndYahooRotoLeague(FPProjPitcher pitcher, YahooRotoLeague league);
//	KeeperRounds findByPlayerKeyAndYahooRotoLeague(String playerKey, YahooRotoLeague league);


	// Ottoneu Old School Leagues
	List<KeeperPicks> findByOttoneuOldSchoolTeam(OttoneuOldSchoolTeam ottoneuOldSchoolTeam);
	List<KeeperPicks> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	List<KeeperPicks> findByOttoneuOldSchoolLeague(OttoneuOldSchoolLeague league);
	KeeperPicks findByBatterAndOttoneuOldSchoolLeague(FPProjBatter batter, OttoneuOldSchoolLeague league);
	KeeperPicks findByPitcherAndOttoneuOldSchoolLeague(FPProjPitcher pitcher, OttoneuOldSchoolLeague league);
	
}
