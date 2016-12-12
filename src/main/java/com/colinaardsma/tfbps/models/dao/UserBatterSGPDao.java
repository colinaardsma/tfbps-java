package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserBatterSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserBatterSGPDao extends CrudRepository<UserBatterSGP, Integer> {
	
	List<UserBatterSGP> findAll(); // get all players
	List<UserBatterSGP> findAllByOrderByHistSGPDesc();
	List<UserBatterSGP> findByUserAndLeague(User user, YahooRotoLeague league);
	UserBatterSGP findByBatterAndUserAndLeague(FPProjBatter batter, User user, YahooRotoLeague league);
	UserBatterSGP findByBatter(FPProjBatter batter);
	
//	List<UserBatterSGP> findByTeam(String team); // get players on a team
//	UserBatterSGP findByName(String name); // get object with name only
//	UserBatterSGP findByNameAndTeam(String name, String team); // get player with name and team only
//	UserBatterSGP findByUid(int uid); // get player with unique id only
	
}
