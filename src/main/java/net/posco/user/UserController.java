package net.posco.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import net.posco.util.FileUpload;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listFirstPage(Model model) {
        model.addAttribute("pageTitle", "users");
        return listByPage(model, 1, 10, "id", "asc", null);
    }

    @GetMapping("/users/page/{pageNum}")
	public String listByPage(Model model,
	                        @PathVariable(name = "pageNum") int pageNum, 
							@Param("pageSize") int pageSize,
							@Param("sortField") String sortField, 
							@Param("sortDirection") String sortDirection,
							@Param("keyword") String keyword) {
		
		Page<User> page = userService.listByPage(pageNum, pageSize, sortField, sortDirection, keyword);
		List<User> listUsers = page.getContent();
		long startCount = (pageNum - 1) * pageSize + 1;
		long endCount = startCount + pageSize - 1;	
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements(); 
		}
		//Reverse sort direction when click on the column header
		String reverseSortDirection = sortDirection.equals("asc") ? "desc" : "asc";

		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", reverseSortDirection);
		model.addAttribute("keyword", keyword);
		return "/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Add new user");
		return "user_form";	
	}

	@GetMapping("/users/edit/{id}/page/{currentPage}")
	public String editUser(Model model, 
						  @PathVariable(name = "id") Integer id, 
						  @PathVariable("currentPage") Integer currentPage,
						  @Param("pageSize") int pageSize,
			              @Param("sortField") String sortField, 
						  @Param("sortDirection") String sortDirection,
			              RedirectAttributes redirectAttributes) {
		try {
			User user = userService.get(id);		
			model.addAttribute("page", currentPage);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit user");
			return "/user_form";	
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("warning", e.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/users/delete/{id}/page/{currentPage}")
	public String deleteUser(Model model,
							@PathVariable(name = "id") Integer id, 
							@PathVariable("currentPage") Integer currentPage,
							@Param("pageSize") int pageSize,			
							@Param("sortField") String sortField, 
							@Param("sortDirection") String sortDirection,
							RedirectAttributes redirectAttributes) {
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message",
					"The user ID " + id + " has been deleted successfully.");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("warning", e.getMessage());
		}
		return "redirect:/users/page/{currentPage}?pageSize=" + pageSize + "&sortField=" + sortField + "&sortDirection=" + sortDirection;
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserActiveStatus(@PathVariable(name = "id") Integer id,
										 @PathVariable(name = "status") boolean enabled,
										 Model model, RedirectAttributes redirectAttributes) {
		
		userService.updateUserActiveStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;	
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}

	@PostMapping("/users/save")
	public String saveUser(User user,
						   @RequestParam("image") MultipartFile multipartFile,
						   RedirectAttributes redirectAttributes) throws IOException {

		// PasswordGenerator passGenerator = new PasswordGenerator();
		// user.setPassword(passGenerator.generatePassword(8));

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPicture(fileName);
			User saveUser = userService.save(user);		
			String uploadDir = "upload/picture/user/profile-picture/" + saveUser.getId();
			FileUpload.cleanDir(uploadDir);
			FileUpload.saveFile(uploadDir, fileName, multipartFile);
		}
		else {
			//Set default picture
			if (user.getPicture().isEmpty()) user.setPicture(null);		
			userService.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
		return getRedirectUrlToAffectedUser(user);
	}

	//Redirect to the current edited user
	private String getRedirectUrlToAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?pageSize=1&sortField=id&sortDirection=asc&keyword=" + firstPartOfEmail;
	}

	//Export data to excel
	@GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);	
	}

	//Export data to pdf
	@GetMapping("/users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserPdfExporter exporter = new UserPdfExporter();
		exporter.export(listUsers, response);	
	}
}
