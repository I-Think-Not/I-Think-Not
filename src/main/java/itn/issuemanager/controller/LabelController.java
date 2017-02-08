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
	
	@GetMapping("/new")	//생성 폼
	public String form() {
		
		return "label/form";
	}
	
	@GetMapping("/")	//리스트 보기
	public String list(Model model) {
		log.debug("list access");
		
		
		// TODO 의미없이 공백 라인을 추가하지 않는다.
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
	public String edit(@PathVariable("id") Long id, Model model) {
		
		Label modifyLabel = labelRepository.findOne(id);
		
		model.addAttribute("modifyLabel", modifyLabel);
		
		return "label/updateForm";
	}
	
	@PutMapping("/{id}")	//수정하기
	public String update(@PathVariable("id") Long id, Label inputLabel) {
	    // TODO URL에 {id}로 전달되는 경우의 값도 Label에 setId()를 통해 자동으로 추가됨. 다음 라인 필요 없음. 테스트 필요함.
		inputLabel.setId(id);
		labelRepository.save(inputLabel);
		return "redirect:/label/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete(@PathVariable("id") Long id) {
		labelRepository.delete(id);
		return "redirect:/label/";
	}

}
