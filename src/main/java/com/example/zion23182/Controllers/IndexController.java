package com.example.zion23182.Controllers;

import com.example.zion23182.Configuration.SecurityConfig;
import com.example.zion23182.Model.User;
import com.example.zion23182.Repo.UserRepository;
import com.example.zion23182.services.UserService;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
@Controller
public class IndexController {
   @Autowired private UserService userService;

    @Autowired
    private SecurityConfig bCryptPasswordEncoder;
@GetMapping(value="/")
 public String getPage(Model model)
 {
     if (!userService.appUserEmailExists("admin@gmail.com")){
         User user=new User();
         user.setEmail("admin@gmail.com");
         user.setFName("admin");
         user.setLName("admin");
         user.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt()));
         user.setId(1);
         user.setRole("ROLE_ADMIN");
         userService.saveOrUpdateUser(user);
     }

    return "index";
 }

    @GetMapping(value="/signup")
    public String getSignup(Model model)
    {
        model.addAttribute("user2",new User());
        return "signup";
    }


    @GetMapping(value="/success")
    public String getSuccessPage(Model model)
    {
        return "success";
    }



    @RequestMapping(value = "/register_account", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user2") User user, Model model) {
        try {

            if (userService.appUserEmailExists(user.getEmail())) {
                model.addAttribute("message", "Email " + user.getEmail() + " is Already Taken!");
                return "signup";

            } else
                user.setRole("ROLE_USER");
            user.setPassword(bCryptPasswordEncoder.getPasswordEncoder().encode(user.getPassword()));

            userService.saveOrUpdateUser(user);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "success";

    }




 @RequestMapping(value="/login")
 public String login(Model model)
 {
   return "login";
 }
 @Autowired private UserRepository userRepository;
 @GetMapping(value="/defaultSuccessLogin")
    public String getDefaultLoginPage() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username=auth.getName();
        User authuser=userRepository.findByEmail(username).get();
    if(auth!=null && auth.isAuthenticated())
    {
      Collection<? extends GrantedAuthority> authority=auth.getAuthorities();
      if(authority.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
      { AdminController.user=authuser;
        return "redirect:/admin/list";
      }else if(authority.contains(new SimpleGrantedAuthority("ROLE_USER"))){
         UserController.user=authuser;
        return "redirect:/user/dashboard";
      }
    }
        return "/login";
    }
}
