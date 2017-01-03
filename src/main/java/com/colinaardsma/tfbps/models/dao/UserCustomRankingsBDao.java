package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserCustomRankingsB;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserCustomRankingsBDao extends CrudRepository<UserCustomRankingsB, Integer> {
	
	List<UserCustomRankingsB> findAll(); // get all players
	List<UserCustomRankingsB> findAllByOrderByHistSGPDesc();
	List<UserCustomRankingsB> findByUser(User user);
	UserCustomRankingsB findByFpBatter(FPProjBatter fpBatter);
	
	// Yahoo Roto Leagues
	List<UserCustomRankingsB> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserCustomRankingsB findByFpBatterAndUserAndYahooRotoLeague(FPProjBatter fpBatter, User user, YahooRotoLeague league);

	// Ottoneu Old School Leagues
	List<UserCustomRankingsB> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserCustomRankingsB findByFpBatterAndUserAndOttoneuOldSchoolLeague(FPProjBatter fpBatter, User user, OttoneuOldSchoolLeague league);
	
}
