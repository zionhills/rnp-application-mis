package com.example.zion23182.Controllers;

import com.example.zion23182.Configuration.EmailSenderServiceConfig;
import com.example.zion23182.Configuration.SecurityConfig;
import com.example.zion23182.Model.Police;
import com.example.zion23182.Model.User;
import com.example.zion23182.Repo.PoliceRepository;
import com.example.zion23182.services.PoliceService;
import com.example.zion23182.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@SessionAttributes("admin")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    private EmailSenderServiceConfig sendEmail;

    @Autowired
    private SecurityConfig bCryptPasswordEncoder;
    private final PoliceRepository repo;
    private final PoliceService policeService;
    private final UserService service;

    public static User user;
    public static String message = "";

    public AdminController(PoliceService policeService, PoliceRepository repo, UserService service) {
        this.policeService = policeService;
        this.repo = repo;
        this.service = service;
    }

    @GetMapping("/search")
    public String showFoundPolice() {
        return "search";
    }

    @GetMapping("/searchPerson")
    public String search(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttribute) {
        Police foundPolice = policeService.findMember(email);

        if (foundPolice != null) {
            model.addAttribute("foundPolice", foundPolice);

            return "search";
        }
        redirectAttribute.addFlashAttribute("message", "Sorry your search does not found");
        return "search";
    }

    @GetMapping("/add")
    public String showRegisterForm(Model model) {
        model.addAttribute("police", new Police());
        return "Dashboard/add-citizen";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("viewCitizens", repo.findAll());
        return "Dashboard/viewCitizens";
    }

    @PostMapping("/register")
    public String addCitizen(@Valid Police police, BindingResult result, @ModelAttribute("police") Police polices,
            RedirectAttributes redirectAttributes, Principal principal, @AuthenticationPrincipal User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (result.hasErrors()) {
                return "Dashboard/add-citizen";
            }

             Police foundPolice = policeService.findMember(polices.getEmail());

        if (foundPolice != null) {
            redirectAttributes.addFlashAttribute("message", "this person is already exist");
            return "redirect:/admin/add";
        }
       
    
            repo.save(polices);
            sendEmail.sendPoliceEmail(police.getEmail(), "Citizen Registration",
                    police.getFirstName() + " " + police.getLastName(), police.getMartialStatus());

            redirectAttributes.addFlashAttribute("message", "Information saved successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Police police = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        model.addAttribute("budded", police);
        return "Dashboard/edit-citizen";
    }

    @PostMapping("update/{id}")
    public String updateCitizen(@PathVariable("id") long id, @Valid Police police, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes, Principal principal) {

        try {
            if (result.hasErrors()) {
                police.setId(id);
                return "Dashboard/edit-citizen";
            }
            repo.save(police);
            sendEmail.sendPoliceEmail(police.getAddress(), "Citizen Category Change",
                    police.getFirstName() + " " + police.getLastName(), police.getMartialStatus());
            model.addAttribute("viewCitizens", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Information updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCitizen(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Police police = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
            repo.delete(police);
            model.addAttribute("viewCitizens", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Information deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/admin/list";
    }

    @GetMapping("/usersList")
    public String showUsers(Model model) {
        model.addAttribute("viewUsers", service.getAllUsers());
        return "Dashboard/view-users";
    }

    @GetMapping("/userPage")
    public String showUsersRegisterForm(Model model) {
        model.addAttribute("user1", new User());
        return "Dashboard/user";
    }

    @PostMapping("/registerUser")
    public String addUser(@Valid User userData, BindingResult result, Model model,
            RedirectAttributes redirectAttributes, Principal principal, @AuthenticationPrincipal User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (result.hasErrors()) {
                return "Dashboard/view-users";
            }

            userData.setPassword(bCryptPasswordEncoder.getPasswordEncoder().encode(userData.getPassword()));
            service.saveOrUpdateUser(userData);

            redirectAttributes.addFlashAttribute("message", "User Account created successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/admin/usersList";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteAccount(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            int value = id.intValue();
            User policeUser = service.findOrderById(value);
            service.deleteUser(policeUser);
            model.addAttribute("viewUsers", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/admin/usersList";
    }
}
