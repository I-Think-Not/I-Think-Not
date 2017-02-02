package IThinkNot.IssueManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestone")
public class MilestonesController {
	
	@GetMapping("/")
	public String index(){
		return "milestone/list";
	}
	@GetMapping("/new")
	public String form(){
		return "milestone/form";
	}
	@PostMapping("/create")
	public String create(){
		return "";
	}
	@GetMapping("/{id}/edit")
	public String edit(){
		return "";
	}
	@PutMapping("/{id}")
	public String update(){
		return "";
	}
	@DeleteMapping("/{id}")
	public String delete(){
		return "";
	}
}
