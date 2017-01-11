package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.FPProjBatter;
import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.SteamerProjBatter;
import com.fantasyspot.models.User;
import com.fantasyspot.models.UserCustomRankingsB;
import com.fantasyspot.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserCustomRankingsBDao extends CrudRepository<UserCustomRankingsB, Integer> {
	
	List<UserCustomRankingsB> findAll(); // get all players
	List<UserCustomRankingsB> findAllByOrderByHistSGPDesc();
	List<UserCustomRankingsB> findByUser(User user);
	UserCustomRankingsB findByFpBatter(FPProjBatter fpBatter);
	UserCustomRankingsB findBySteamerBatter(SteamerProjBatter steamerBatter);
	
	// Yahoo Roto Leagues
	List<UserCustomRankingsB> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserCustomRankingsB findByFpBatterAndUserAndYahooRotoLeague(FPProjBatter fpBatter, User user, YahooRotoLeague league);

	// Ottoneu Old School Leagues
	List<UserCustomRankingsB> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserCustomRankingsB findByFpBatterAndUserAndOttoneuOldSchoolLeague(FPProjBatter fpBatter, User user, OttoneuOldSchoolLeague league);
	
}
