package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.User;

@Transactional
@Repository
public interface OttoneuOldSchoolLeagueDao extends CrudRepository<OttoneuOldSchoolLeague, Integer> {

	List<OttoneuOldSchoolLeague> findAll(); // get all leagues
	List<OttoneuOldSchoolLeague> findByUsers_uid(int uid); // get list of leagues per user
	List<OttoneuOldSchoolLeague> findByUsers(User user); // get list of leagues per user
	OttoneuOldSchoolLeague findByLeagueKey(String leagueKey); // get league with league key only
	OttoneuOldSchoolLeague findByLeagueNumberAndSeason(int leagueNumber, int season); // get league with league number and season
	OttoneuOldSchoolLeague findByUid(int uid); // get league with unique id only

}

