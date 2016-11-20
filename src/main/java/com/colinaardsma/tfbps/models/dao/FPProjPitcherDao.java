package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.FPProjPitcher;

@Transactional
@Repository
public interface FPProjPitcherDao extends CrudRepository<FPProjPitcher, Integer> {
	
	List<FPProjPitcher> findAll(); // get all players
	List<FPProjPitcher> findAllByOrderBySgpDesc();
	
	List<FPProjPitcher> findByTeam(String team); // get players on a team
	FPProjPitcher findByName(String name); // get object with name only
	FPProjPitcher findByNameAndTeam(String name, String team); // get player with name and team only
	FPProjPitcher findByUid(int uid); // get player with unique id only

}
