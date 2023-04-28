package net.posco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("pageTitle", "dashboard");
        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        model.addAttribute("pageTitle", "login");
        return "login";
    }

    @GetMapping("/construction")
    public String viewconstructionPage(Model model) {
        model.addAttribute("pageTitle", "construction");
        return "error/construction";
    }

    @GetMapping("/maintanance")
    public String viewMaintanancePage(Model model) {
        model.addAttribute("pageTitle", "maintanance");
        return "error/maintanance";
    }
}
