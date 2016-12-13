package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;

@Transactional
@Repository
public interface OttoneuOldSchoolLeagueDao extends CrudRepository<OttoneuOldSchoolLeague, Integer> {

	List<OttoneuOldSchoolLeague> findAll(); // get all leagues
	List<OttoneuOldSchoolLeague> findByUsers_uid(int uid); // get list of leagues per user
	OttoneuOldSchoolLeague findByLeagueKey(String leagueKey); // get league with league key only
	OttoneuOldSchoolLeague findByLeagueNumberAndSeason(int leagueNumber, int season); // get league with league number and season
	OttoneuOldSchoolLeague findByUid(int uid); // get league with unique id only

}

