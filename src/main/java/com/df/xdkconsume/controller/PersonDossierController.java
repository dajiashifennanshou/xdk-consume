package com.df.xdkconsume.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.df.xdkconsume.entity.PersonDossier;
import com.df.xdkconsume.service.PersonDossierService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author df
 * @since 2018-07-12
 */
@RestController
@RequestMapping("/personDossier")
public class PersonDossierController {
	@Autowired
	private PersonDossierService service;
	
	@RequestMapping("/index")
	public PersonDossier getInfo(String name) {
		return service.selectById(name);
	}
}

