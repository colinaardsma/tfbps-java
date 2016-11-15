package com.colinaardsma.tfbps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectionController extends AbstractController {

	@RequestMapping(value = "/fpprojb")
    public String fpprojb(){
        return "fpprojb";
    }

}
