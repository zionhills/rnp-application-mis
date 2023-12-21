package com.example.zion23182.Controllers;

import com.example.zion23182.Configuration.EmailSenderServiceConfig;
import com.example.zion23182.Model.Police;
import com.example.zion23182.Model.User;
import com.example.zion23182.Repo.PoliceRepository;
import com.example.zion23182.services.FilesStorageService;
import com.example.zion23182.services.PoliceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("user")
@RequestMapping(value = "/user")
public class UserController {
    public static User user;
    public static String message = "";
    @Autowired
    private EmailSenderServiceConfig sendEmail;
    @Autowired
    FilesStorageService storageService;
    private final PoliceRepository repo;
    private final PoliceService policeService;

    public UserController(PoliceRepository repo, PoliceService policeService) {
        this.policeService = policeService;
        this.repo = repo;
    }

    @GetMapping("/dashboard")
    public String showRegisterForm(Model model) {
        model.addAttribute("candidate", new Police());
        return "userDashboard";
    }

    @PostMapping("/files/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            storageService.save(file);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @PostMapping("/register")
    public String addCitizenAndFile(
            @Valid Police police,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("police") Police polices,
            RedirectAttributes redirectAttributes) {

        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("message", "Something went wrong!");
                return "redirect:/user/dashboard";
            }

            if (!file.isEmpty()) {
                uploadFile(file);
            }

            Police foundPolice = policeService.findMember(polices.getEmail());

            if (foundPolice != null) {
                redirectAttributes.addFlashAttribute("message", "this person is already exist");
                return "redirect:/user/dashboard";
            }

            repo.save(polices);
            sendEmail.sendPoliceEmail(police.getEmail(), "RNP application ",
                    police.getFirstName() + " " + police.getLastName(), police.getAddress());

            redirectAttributes.addFlashAttribute("message", "Your Application has been submitted successfully!");

            return "redirect:/user/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
}
