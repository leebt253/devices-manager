package net.posco.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.posco.util.FileUpload;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal UserProfile loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "profile");
        return "user_profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            User user,
            @RequestParam("image") MultipartFile multipartFile,
            @AuthenticationPrincipal UserProfile loggedUser,
            RedirectAttributes redirectAttributes) throws IOException {    

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(
                    multipartFile.getOriginalFilename());
            user.setPicture(fileName);
            User saveUser = userService.updateProfile(user);
            String uploadDir = "upload/picture/user/profile-picture/" + saveUser.getId();
            FileUpload.cleanDir(uploadDir);
            FileUpload.saveFile(uploadDir, fileName, multipartFile);
        } else {
            // Set default picture
            if (user.getPicture().isEmpty())
                user.setPicture(null);
            userService.updateProfile(user);
        }

        loggedUser.setPicture(user.getPicture());

        redirectAttributes.addFlashAttribute(
                "message",
                "Your profile has been saved successfully!");
        return "redirect:/profile";
    }
}
