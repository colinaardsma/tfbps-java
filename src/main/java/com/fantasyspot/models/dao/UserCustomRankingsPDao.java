package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.FPProjPitcher;
import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.SteamerProjPitcher;
import com.fantasyspot.models.User;
import com.fantasyspot.models.UserCustomRankingsP;
import com.fantasyspot.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserCustomRankingsPDao extends CrudRepository<UserCustomRankingsP, Integer> {
	
	List<UserCustomRankingsP> findAll(); // get all players
	List<UserCustomRankingsP> findAllByOrderByHistSGPDesc();
	List<UserCustomRankingsP> findByUser(User user);
	UserCustomRankingsP findByFpProjPitcher_uid(int pitcher_uid);
	UserCustomRankingsP findByFpProjPitcher(FPProjPitcher fpProjpitcher);
	UserCustomRankingsP findBySteamerProjPitcher(FPProjPitcher fpProjpitcher);

	
	// Yahoo Roto Leagues
	List<UserCustomRankingsP> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserCustomRankingsP findByFpProjPitcherAndUserAndYahooRotoLeague(FPProjPitcher fpProjpitcher, User user, YahooRotoLeague league);
	UserCustomRankingsP findBySteamerProjPitcherAndUserAndYahooRotoLeague(SteamerProjPitcher steamerProjpitcher, User user, YahooRotoLeague league);
	
	// Ottoneu Old School Leagues
	List<UserCustomRankingsP> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserCustomRankingsP findByFpProjPitcherAndUserAndOttoneuOldSchoolLeague(FPProjPitcher fpProjpitcher, User user, OttoneuOldSchoolLeague league);
	UserCustomRankingsP findBySteamerProjPitcherAndUserAndOttoneuOldSchoolLeague(SteamerProjPitcher steamerProjpitcher, User user, OttoneuOldSchoolLeague league);

}
