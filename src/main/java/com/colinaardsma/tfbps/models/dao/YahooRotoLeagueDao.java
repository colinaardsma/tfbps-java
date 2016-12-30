package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface YahooRotoLeagueDao extends CrudRepository<YahooRotoLeague, Integer> {

	List<YahooRotoLeague> findAll(); // get all leagues
	List<YahooRotoLeague> findByUsers_uid(int uid); // get list of leagues per user
	List<YahooRotoLeague> findByUsers(User user); // get list of leagues per user
	YahooRotoLeague findByLeagueKey(String leagueKey); // get league with league key only
	YahooRotoLeague findByUid(int uid); // get league with unique id only

}

