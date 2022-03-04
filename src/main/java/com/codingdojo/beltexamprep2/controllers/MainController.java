package com.codingdojo.beltexamprep2.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingdojo.beltexamprep2.models.CurrentUser;
import com.codingdojo.beltexamprep2.models.Project;
import com.codingdojo.beltexamprep2.models.Task;
import com.codingdojo.beltexamprep2.models.User;
import com.codingdojo.beltexamprep2.services.ProjectService;
import com.codingdojo.beltexamprep2.services.TaskService;
import com.codingdojo.beltexamprep2.services.UserService;


@Controller
public class MainController {
	
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	TaskService taskService;
	
// Registration, Login, and Logout
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new CurrentUser());
		return "index.jsp";
	}
		
	@PostMapping("/registration")
	public String register(
			@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {	
		
		User user= userService.register(newUser,result);
		if (user==null) {
			model.addAttribute("newLogin", new CurrentUser());
			return "index.jsp";
		}
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new CurrentUser());
			return "index.jsp";
		}		
		session.setAttribute("currentUser", user);
		session.setAttribute("currentID", user.getId());
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/login")
    public String login() {
        return "loginPage.jsp";
    }
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") CurrentUser newLogin, BindingResult result, Model model, HttpSession session) {
				
		User user=userService.login(newLogin,result);
		
		if (user==null || result.hasErrors()) {
			model.addAttribute("newUser",new User());
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
			return "index.jsp";		
		}
		session.setAttribute("currentUser", user);
		session.setAttribute("currentID", user.getId());
		return "redirect:/dashboard";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}

//	Routes the user to the dashboard page
	
	@GetMapping("/dashboard")
	public String dashboard(@ModelAttribute("project") Project project, Model model, HttpSession session) {
		if(session.getAttribute("currentUser")==null) {
			return "redirect:/";
		}
		
		List<Project> otherProjects=projectService.findOtherProjects((Long) session.getAttribute("currentID"));
		List<Project> myProjects= projectService.findMyProjects((Long) session.getAttribute("currentID"));
		
		model.addAttribute("otherProjects", otherProjects);
		model.addAttribute("myProjects", myProjects);
		
		return "dashboard.jsp";
	}
	
//	Routes the user to a certain project page

	@GetMapping("/project/{id}")
	public String project(@PathVariable ("id") Long id, Model model, HttpSession session) {		
		if(session.getAttribute("currentUser")==null) {
			return "redirect:/";
		}
		for( User teamMember:projectService.findOneById(id).getUsers() ) {
			if( teamMember.getId() == session.getAttribute("currentID")){
				model.addAttribute("teamMember","true");
			}
		}
		model.addAttribute("thisProject",projectService.findOneById(id));
		return "viewProject.jsp";
	}

//	Routes the user to the new project page
	
	@GetMapping("/project/new")
	public String newProject(@ModelAttribute("project") Project project, Model model, @ModelAttribute("user") User user, HttpSession session) {
		if(session.getAttribute("currentUser")==null) {
			return "redirect:/";
		}
		else return "newProject.jsp";
	}	
	
//	Process the user's request to add a new project

	@PostMapping("/addproject")
	public String addProject(@Valid @ModelAttribute("project") Project project, BindingResult result) {
		if(result.hasErrors()) {
			return "newProject.jsp";
		}
		else {
		projectService.createProject(project);
		return"redirect:/dashboard";
		}
	}	
	
//	Routes the user to the edit project page

	@GetMapping("/project/{id}/edit")
	public String editProject(@PathVariable("id") Long id, Model model, HttpSession session) {
		Project check=projectService.findOneById(id);
		if (check!=null) {
			if (check.getLead().getId() != session.getAttribute("currentID")) {
				return "redirect:/dashboard";
			}
				model.addAttribute("project",check);
				model.addAttribute("temp",projectService.findOneById(id).getTitle());
				return "editProject.jsp";
			}
		else return "redirect:/dashboard";		
	}	
	
//	Process the user's request to edit the project

	@PutMapping("/project/{id}/update")
	public String updateProjct(@PathVariable ("id") Long id, @Valid @ModelAttribute("project") Project project, BindingResult result, Model model) {		
		if (result.hasErrors()) {
			model.addAttribute("temp",projectService.findOneById(id).getTitle());
			return "editProject.jsp";
		} else {
			projectService.updateProject(project);
			return "redirect:/dashboard";
		}				
	}

//	Routes the user to tasks page

	@GetMapping("/project/{id}/tasks")
	public String newTask(@PathVariable("id") Long id, @ModelAttribute("task") Task task, Model model, HttpSession session) {		
		Project check=projectService.findOneById(id);
		if (check!=null) {
			for (User teamMember:check.getUsers() ) {
				if( teamMember.getId() == session.getAttribute("currentID")){
					model.addAttribute("thisProject",check);
					model.addAttribute("allTasks",taskService.retrieveTasks(id));
					return "viewTasks.jsp";
				}
			}
			return "redirect:/dashboard";
		}
		else return "redirect:/dashboard";		
	}	
	
//	Process the user's request to add a new task

	@PostMapping("/project/{taskAdded}")
	public String addTask(@PathVariable ("taskAdded") Long id, @Valid @ModelAttribute("task") Task task, BindingResult result, Model model) {	
		if (result.hasErrors()) {
			Project check=projectService.findOneById(id);
			model.addAttribute("allTasks",taskService.retrieveTasks(id));
			model.addAttribute("thisProject",check);
			return "viewTasks.jsp";
		}
		else {
			taskService.createTask(task);	
			return "redirect:/project/{taskAdded}/tasks";
		}
	}	
	
//	Process the user's request to join a project

	@PutMapping("/project/{id}/join")
	public String joinProject(@PathVariable ("id") Long id, @ModelAttribute ("project") Project project) {		
		projectService.addUser(project,id);
		return "redirect:/dashboard";	
	}
	
	
//	Process the user's request to leave a project

	@PutMapping("/project/{id}/leave")
	public String leaveProject(@PathVariable ("id") Long id, @ModelAttribute ("project") Project project) {		
		projectService.removeUser(project,id);
		return "redirect:/dashboard";	
	}	
	
//	Process the user's request to delete a project
	
	@RequestMapping("/project/{id}/delete")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
		if(session.getAttribute("currentUser")==null) {
			return "redirect:/";
		}
    	projectService.deleteProject(id);
    	return "redirect:/dashboard";
    }
}
