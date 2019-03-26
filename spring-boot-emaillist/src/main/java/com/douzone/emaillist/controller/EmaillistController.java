package com.douzone.emaillist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.emaillist.dao.EmaillistDao;
import com.douzone.emaillist.vo.EmaillistVo;

@Controller
public class EmaillistController {
	@Autowired
	private EmaillistDao emaillistDao;
	
	@RequestMapping("")
	public String list(Model model) {
		model.addAttribute("list",emaillistDao.getList());
		return "list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add() {
		return "form";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(EmaillistVo vo) {
		emaillistDao.insert(vo);
		return "redirect:/";
	}
}
