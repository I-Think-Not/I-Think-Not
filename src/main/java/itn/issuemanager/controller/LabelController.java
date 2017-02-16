package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.Label;
import itn.issuemanager.repository.LabelRepository;

@Controller
@RequestMapping("/label")
public class LabelController {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelRepository labelRepository;
	
	@GetMapping("/new")	
	public String form() {
		
		return "label/form";
	}
	
	@GetMapping("/")	
	public String list(Model model) {
		log.debug("list access");
		
		List<Label> labelList = (List<Label>) labelRepository.findAll();
		model.addAttribute("labelList",labelList);
		return "label/list";
	}
	
	@PostMapping("/")	
	public String create(Label inputLabel) {
		labelRepository.save(inputLabel);
		return "redirect:/label/";
	}
	
	@GetMapping("/{id}")	
	public String show() {
		return "index";
	}
	
	@GetMapping("/{id}/edit")	
	public String edit(@PathVariable("id") Long id, Model model) {
		Label modifyLabel = labelRepository.findOne(id);
		model.addAttribute("modifyLabel", modifyLabel);
		return "label/updateForm";
	}
	
	@PutMapping("/{id}")	
	public String update(@PathVariable("id") Long id, Label inputLabel) {
		labelRepository.save(inputLabel);
		return "redirect:/label/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete(@PathVariable("id") Long id) {
		labelRepository.delete(id);
		return "redirect:/label/";
	}

}
