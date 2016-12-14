package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserPitcherSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserPitcherSGPDao extends CrudRepository<UserPitcherSGP, Integer> {
	
	List<UserPitcherSGP> findAll(); // get all players
	List<UserPitcherSGP> findAllByOrderByHistSGPDesc();
	List<UserPitcherSGP> findByUser(User user);
	UserPitcherSGP findByPitcher_uid(int pitcher_uid);
	
	// Yahoo Roto Leagues
	List<UserPitcherSGP> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserPitcherSGP findByPitcherAndUserAndYahooRotoLeague(FPProjPitcher pitcher, User user, YahooRotoLeague league);
	
	// Ottoneu Old School Leagues
	List<UserPitcherSGP> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserPitcherSGP findByPitcherAndUserAndOttoneuOldSchoolLeague(FPProjPitcher pitcher, User user, OttoneuOldSchoolLeague league);

}
