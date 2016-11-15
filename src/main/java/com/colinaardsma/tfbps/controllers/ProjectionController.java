package com.colinaardsma.tfbps.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;

@Controller
public class ProjectionController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@RequestMapping(value = "/fpprojb")
    public String fpprojb(Model model){
		
		//populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAll();
		model.addAttribute("players", players);

        return "fpprojb";
    }

}
