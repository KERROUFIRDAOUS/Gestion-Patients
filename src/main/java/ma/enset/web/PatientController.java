package ma.enset.web;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
	@Autowired
	private PatientRepository patientRepository;

	@GetMapping("/")
	public String home(){
		return "redirect:/user/index";
	}

	@GetMapping(path = "/user/index")
	public String patients(Model model,
						   @RequestParam(name = "page", defaultValue = "0") int page,
						   @RequestParam(name = "size", defaultValue = "5") int size,
						   @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Page<Patient> patientPage = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
		model.addAttribute("listPatients", patientPage.getContent());
		model.addAttribute("pages", new int[patientPage.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		return "patients";
	}

	@GetMapping("/admin/delete")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(Long id, String keyword, int page){
		patientRepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&keyword="+keyword;
	}

	@GetMapping("/admin/formPatients")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String formPatients(Model model){
		model.addAttribute("patient",new Patient());
		return "formPatients";
	}

	@PostMapping(path = "/admin/save")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
					   @RequestParam(defaultValue = "0") int page,
					   @RequestParam(defaultValue = "") String keyword){
		if(bindingResult.hasErrors()) return "formPatients";
		patientRepository.save(patient);
		return "redirect:/user/index?page="+page+"&keyword="+keyword;
	}

	@GetMapping("/admin/editPatient")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editPatient(Model model, Long id, String keyword, int page){
		Patient patient = patientRepository.findById(id).orElse(null);
		if(patient==null) throw new RuntimeException("Patient introuvable");
		model.addAttribute("patient",patient);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page",page);
		return "editPatient";
	}
}
