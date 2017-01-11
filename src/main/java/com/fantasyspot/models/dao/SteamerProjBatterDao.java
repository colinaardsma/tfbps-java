package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.SteamerProjBatter;

@Transactional
@Repository
public interface SteamerProjBatterDao extends CrudRepository<SteamerProjBatter, Integer> {
	
	List<SteamerProjBatter> findAll(); // get all players
	List<SteamerProjBatter> findAllByOrderByOpsTotalSGPDesc();
	List<SteamerProjBatter> findAllByOrderByAvgTotalSGPDesc();
	
	List<SteamerProjBatter> findByTeam(String team); // get players on a team
	SteamerProjBatter findByName(String name); // get object with name only
	SteamerProjBatter findByNameAndTeam(String name, String team); // get player with name and team only
	SteamerProjBatter findByUid(int uid); // get player with unique id only
	
}
