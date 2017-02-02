package itn.issuemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issue")
public class IssuesController {
	
	@GetMapping("/new")	//생성 폼
	public String form() {
		
		return "index";
	}
	
	@PostMapping("/")	//생성
	public String create() {
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")	//상세보기
	public String show() {
		
		return "index";
	}
	
	@GetMapping("/{id}/edit")	//수정 폼
	public String edit() {
		
		return "index";
	}
	
	@PutMapping("/{id}")	//수정하기
	public String update() {
		
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete() {
		
		return "redirect:/";
	}
}
