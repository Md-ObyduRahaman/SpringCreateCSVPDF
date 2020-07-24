package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Student;
import com.example.repo.StudentRepo;

@Controller
public class MainController 
{
	@Autowired
	private StudentRepo studentRepo;

	public MainController(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}
												//Goto index Page
	@RequestMapping("/")
	public String view()
	{
		return "index";
	}
												//Goto insertPage
	@RequestMapping("/insert")
	public String view1()
	{
		return "insertPage";
	}
												//Save Record
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String view2(Student stud, Model m)
	{
		studentRepo.save(stud);
		m.addAttribute("msg","Record insert successfully...");
		return "insertPage";
	}
												//Display Record
	@GetMapping("/display")
	public String view3(Model m)
	{
		List list = studentRepo.findAll();
		if(!list.isEmpty())
		{
			m.addAttribute("data", list);
		}
		else {
			m.addAttribute("msg", "Sorry record not found!");
		}
		return "displayPage";
	}
}
