package IThinkNot.IssueManager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@GetMapping("")
	public String index(){
		return "/list";
	}
	@GetMapping("/new")
	public String form(){
		return "/form";
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
