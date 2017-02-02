package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.LabelRepository;

@Controller
@RequestMapping("/label")
public class LabelController {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelRepository labelRepository;
	
	@GetMapping("/new")	//생성 폼
	public String form() {
		
		return "label/form";
	}
	
	@GetMapping("/")	//리스트 보기
	public String list(Model model) {
		log.debug("list access");
		
		
		List<Label> labelList = (List<Label>) labelRepository.findAll();
		
		model.addAttribute("labelList",labelList);
		
		return "label/list";
	}
	
	
	@PostMapping("/")	//생성
	public String create(Label inputLabel) {
		labelRepository.save(inputLabel);
		return "redirect:/label/";
	}
	
	@GetMapping("/{id}")	//상세보기
	public String show() {
		
		return "index";
	}
	
	@GetMapping("/{id}/edit")	//수정 폼
	public String edit() {
		
		return "label/form";
	}
	
	@PutMapping("/{id}")	//수정하기
	public String update() {
		
		return "redirect:/label/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete() {
		
		return "redirect:/label/";
	}

}
