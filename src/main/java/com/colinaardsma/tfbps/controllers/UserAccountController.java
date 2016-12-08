package com.colinaardsma.tfbps.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class UserAccountController extends AbstractController {
	
	@Autowired
	UserDao userdao;

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
    @RequestMapping(value = "/useraccount")
    public String useraccount(HttpServletRequest request, Model model) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		
		// get date of last data pull
		Date lastPullDate = new Date();

		FPProjBatter batter = fpProjBatterDao.findByUid(1);
		FPProjPitcher pitcher = fpProjPitcherDao.findByUid(1);
		if (batter.getCreated().after(pitcher.getCreated())) {
			lastPullDate = batter.getCreated();
		} else {
			lastPullDate = pitcher.getCreated();
		}

        model.addAttribute("lastPullDate", lastPullDate);
    	model.addAttribute("currentUser", currentUser);
    	
    	return "useraccount";
    }

}
