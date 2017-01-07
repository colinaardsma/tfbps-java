package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.FPProjBatter;

@Transactional
@Repository
public interface FPProjBatterDao extends CrudRepository<FPProjBatter, Integer> {
	
	List<FPProjBatter> findAll(); // get all players
	List<FPProjBatter> findAllByOrderByOpsTotalSGPDesc();
	List<FPProjBatter> findAllByOrderByAvgTotalSGPDesc();
	
	List<FPProjBatter> findByTeam(String team); // get players on a team
	FPProjBatter findByName(String name); // get object with name only
	FPProjBatter findByNameAndTeam(String name, String team); // get player with name and team only
	FPProjBatter findByUid(int uid); // get player with unique id only
	
}
