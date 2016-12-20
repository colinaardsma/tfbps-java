package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserCustomRankingsP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserCustomRankingsPDao extends CrudRepository<UserCustomRankingsP, Integer> {
	
	List<UserCustomRankingsP> findAll(); // get all players
	List<UserCustomRankingsP> findAllByOrderByHistSGPDesc();
	List<UserCustomRankingsP> findByUser(User user);
	UserCustomRankingsP findByPitcher_uid(int pitcher_uid);
	
	// Yahoo Roto Leagues
	List<UserCustomRankingsP> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserCustomRankingsP findByPitcherAndUserAndYahooRotoLeague(FPProjPitcher pitcher, User user, YahooRotoLeague league);
	
	// Ottoneu Old School Leagues
	List<UserCustomRankingsP> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserCustomRankingsP findByPitcherAndUserAndOttoneuOldSchoolLeague(FPProjPitcher pitcher, User user, OttoneuOldSchoolLeague league);

}
