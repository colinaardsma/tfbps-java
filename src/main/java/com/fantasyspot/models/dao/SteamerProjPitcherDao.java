package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.SteamerProjPitcher;

@Transactional
@Repository
public interface SteamerProjPitcherDao extends CrudRepository<SteamerProjPitcher, Integer> {
	
	List<SteamerProjPitcher> findAll(); // get all players
	List<SteamerProjPitcher> findAllByOrderBySgpDesc();
	
	List<SteamerProjPitcher> findByTeam(String team); // get players on a team
	SteamerProjPitcher findByName(String name); // get object with name only
	SteamerProjPitcher findByNameAndTeam(String name, String team); // get player with name and team only
	SteamerProjPitcher findByUid(int uid); // get player with unique id only
	
}
