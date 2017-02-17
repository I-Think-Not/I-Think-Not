package itn.issuemanager.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import itn.issuemanager.domain.Milestone;
import itn.issuemanager.repository.MilestoneRepository;

@Controller
@RequestMapping("/milestone")
public class MilestonesController {
	private static final Logger log = LoggerFactory.getLogger(MilestonesController.class);

	@Autowired
	private MilestoneRepository milestoneRepository;

	@GetMapping("/")
	public String index(Model model) {
		List<Milestone> milestones = milestoneRepository.findAll();
		for (Milestone m : milestones) {
			m.countIssueState();
		}
		model.addAttribute("milestones", milestones);
		return "/milestone/list";
	}

	@GetMapping("/new") 
	public String form() {
		return "/milestone/form";
	}

	@PostMapping("/create") 
	public String create(String subject, String startDate, String endDate) throws Exception {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");
		Date sdate = date.parse(startDate);
		Date edate = date.parse(endDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(edate);
		cal.add(Calendar.MONTH, 1);
		edate = cal.getTime();
		Milestone milestone = new Milestone(subject, sdate, edate);
		milestoneRepository.save(milestone);
		return "redirect:/milestone/";
	}

	@GetMapping("/{id}/detail") // 디테일.
	public String detail(@PathVariable long id, Model model) {
		Milestone milestone = milestoneRepository.findOne(id);
		model.addAttribute("milestone", milestone);
		return "/milestone/detail";
	}

	@GetMapping("/{id}/edit") // 수정폼
	public String edit(@PathVariable long id, Model model) {
		log.info("edit");
		Milestone updateMilestone = milestoneRepository.findOne(id);
		model.addAttribute("milestone", updateMilestone);

		return "/milestone/updateform";
	}

	@PutMapping("/{id}") // 수정요청
	public String update(@PathVariable long id, String subject, String startDate, String endDate) throws Exception {
		log.info("update");
		Milestone updateMilestone = milestoneRepository.findOne(id);
		SimpleDateFormat date = new SimpleDateFormat("yyyyy-mm-dd");
		Date sdate = date.parse(startDate);
		Date edate = date.parse(endDate);

		log.info("id" + updateMilestone.getId() + "subject : " + subject + "startDate : " + startDate);
		updateMilestone.update(subject, sdate, edate);
		log.info(updateMilestone.toString());

		milestoneRepository.save(updateMilestone);
		return "redirect:/milestone/";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id) {
		log.info("delete id"+id);
		milestoneRepository.delete(id);
        
		return "redirect:/milestone/";
	}
}
