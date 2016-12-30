package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.KeeperCosts;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolTeam;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.YahooRotoTeam;

@Transactional
@Repository
public interface KeeperCostsDao extends CrudRepository<KeeperCosts, Integer> {
	
	List<KeeperCosts> findAll(); // get all costs
	List<KeeperCosts> findByUser(User user);

	List<KeeperCosts> findByBatter(FPProjBatter batter);
	List<KeeperCosts> findByPitcher(FPProjPitcher pitcher);

	// Yahoo Roto Leagues
	List<KeeperCosts> findByYahooRotoTeam(YahooRotoTeam yahooRotoTeam);
	List<KeeperCosts> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	List<KeeperCosts> findByYahooRotoLeague(YahooRotoLeague league);
//	List<KeeperCosts> findByPlayerKey(String playerKey);
	KeeperCosts findByBatterAndYahooRotoLeague(FPProjBatter batter, YahooRotoLeague league);
	KeeperCosts findByPitcherAndYahooRotoLeague(FPProjPitcher pitcher, YahooRotoLeague league);
//	KeeperCosts findByPlayerKeyAndYahooRotoLeague(String playerKey, YahooRotoLeague league);


	// Ottoneu Old School Leagues
	List<KeeperCosts> findByOttoneuOldSchoolTeam(OttoneuOldSchoolTeam ottoneuOldSchoolTeam);
	List<KeeperCosts> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	List<KeeperCosts> findByOttoneuOldSchoolLeague(OttoneuOldSchoolLeague league);
	KeeperCosts findByBatterAndOttoneuOldSchoolLeague(FPProjBatter batter, OttoneuOldSchoolLeague league);
	KeeperCosts findByPitcherAndOttoneuOldSchoolLeague(FPProjPitcher pitcher, OttoneuOldSchoolLeague league);
	
}
