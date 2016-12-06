package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.YahooRotoLeague;

@Transactional
@Repository
public interface YahooRotoLeagueDao extends CrudRepository<YahooRotoLeague, Integer> {

	List<YahooRotoLeague> findAll(); // get all leagues
	YahooRotoLeague findByLeagueKey(String leagueKey); // get league with league key only
	YahooRotoLeague findByUid(int uid); // get league with unique id only

}

