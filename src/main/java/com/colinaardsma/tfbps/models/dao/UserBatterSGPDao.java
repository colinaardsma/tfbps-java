package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserBatterSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserBatterSGPDao extends CrudRepository<UserBatterSGP, Integer> {
	
	List<UserBatterSGP> findAll(); // get all players
	List<UserBatterSGP> findAllByOrderByHistSGPDesc();
	List<UserBatterSGP> findByUser(User user);
	UserBatterSGP findByBatter(FPProjBatter batter);
	
	// Yahoo Roto Leagues
	List<UserBatterSGP> findByUserAndYahooRotoLeague(User user, YahooRotoLeague league);
	UserBatterSGP findByBatterAndUserAndYahooRotoLeague(FPProjBatter batter, User user, YahooRotoLeague league);

	// Ottoneu Old School Leagues
	List<UserBatterSGP> findByUserAndOttoneuOldSchoolLeague(User user, OttoneuOldSchoolLeague league);
	UserBatterSGP findByBatterAndUserAndOttoneuOldSchoolLeague(FPProjBatter batter, User user, OttoneuOldSchoolLeague league);
	
}
