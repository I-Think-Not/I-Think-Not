package itn.issuemanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.MilestoneRepository;

@Controller
@RequestMapping("/milestone")
public class MilestonesController {
	private static final Logger log = LoggerFactory.getLogger(MilestonesController.class);

	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("milestones",milestoneRepository.findAll());
		System.out.println("index"+milestoneRepository.findAll().size());
		
		return "/milestone/list";
	}
	@GetMapping("/new") //생성폼
	public String form(){
		return "/milestone/form";
	}
	@PostMapping("/create") //생성요청
	public String create(String subject,String startDate,String endDate){
		SimpleDateFormat date=new SimpleDateFormat("yyyyy-mm-dd");
		Date sdate = null,edate = null;
		try {
			sdate = date.parse(startDate);
			edate = date.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Milestone milestone=new Milestone(subject,sdate,edate);
		milestoneRepository.save(milestone);
		milestone.toString();
		System.out.println("create");
		
		return "redirect:/milestone/";
	}
	@GetMapping("/{id}/edit")  //수정폼
	public String edit(@PathVariable long id,Model model){
		Milestone updateMilestone=milestoneRepository.findOne(id);
		model.addAttribute("milestone", updateMilestone);
		
		return "/milestone/updateform";
	}
	@PutMapping("/{id}")  //수정요청
	public String update(@PathVariable long id,String subject,String startDate,String endDate){
		log.info("update");
		Milestone updateMilestone=milestoneRepository.findOne(id);
		SimpleDateFormat date=new SimpleDateFormat("yyyyy-mm-dd");
		Date sdate = null,edate = null;
		try {
			sdate = date.parse(startDate);
			edate = date.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("id" + updateMilestone.getId() + "subject : "+subject+"startDate : "+startDate);
		updateMilestone.update(subject, sdate, edate);
		log.info(updateMilestone.toString());
		milestoneRepository.save(updateMilestone);
		
		return "redirect:/milestone/";
	}
	@DeleteMapping("/{id}")  
	public String delete(){
		return "";
	}
}
