package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.SteamerBatter;

@Transactional
@Repository
public interface SteamerBatterDao extends CrudRepository<SteamerBatter, Integer> {
	
	List<SteamerBatter> findAll(); // get all players
	List<SteamerBatter> findAllByOrderByOpsTotalSGPDesc();
	List<SteamerBatter> findAllByOrderByAvgTotalSGPDesc();
	
	List<SteamerBatter> findByTeam(String team); // get players on a team
	SteamerBatter findByName(String name); // get object with name only
	SteamerBatter findByNameAndTeam(String name, String team); // get player with name and team only
	SteamerBatter findByUid(int uid); // get player with unique id only
	
}
