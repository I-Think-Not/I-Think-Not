package IThinkNot.IssueManager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/label")
public class LabelController {
	
	@GetMapping("/new")	//생성 폼
	public String form(HttpSession session) {
		
		return "index";
	}
	
	@PostMapping("")	//생성
	public String create(HttpSession session) {
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")	//상세보기
	public String show(HttpSession session) {
		
		return "index";
	}
	
	@GetMapping("/{id}/edit")	//수정 폼
	public String edit(HttpSession session) {
		
		return "index";
	}
	
	@PutMapping("/{id}")	//수정하기
	public String update(HttpSession session) {
		
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete(HttpSession session) {
		
		return "redirect:/";
	}

}
