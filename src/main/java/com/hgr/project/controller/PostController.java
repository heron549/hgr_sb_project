package com.hgr.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;


@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {
	
	@GetMapping("/list")
	public String list() {
		
		return "post_list";
	}
	
	
	
	
	
	

}
