package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserPitcherSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface UserPitcherSGPDao extends CrudRepository<UserPitcherSGP, Integer> {
	
	List<UserPitcherSGP> findAll(); // get all players
	List<UserPitcherSGP> findAllByOrderByHistSGPDesc();
	List<UserPitcherSGP> findByUser(User user);
	List<UserPitcherSGP> findByUserAndLeague(User user, YahooRotoLeague league);
	UserPitcherSGP findByPitcherAndUserAndLeague(FPProjPitcher pitcher, User user, YahooRotoLeague league);
	UserPitcherSGP findByPitcher_uid(int pitcher_uid);
	
//	List<UserPitcherSGP> findByTeam(String team); // get players on a team
//	UserPitcherSGP findByName(String name); // get object with name only
//	UserPitcherSGP findByNameAndTeam(String name, String team); // get player with name and team only
//	UserPitcherSGP findByUid(int uid); // get player with unique id only
	
}
