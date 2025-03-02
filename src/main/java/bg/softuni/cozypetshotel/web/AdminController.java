package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.services.AdminService;
import bg.softuni.cozypetshotel.services.RoleService;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.web.exceptions.RoleAlreadyExistsException;
import bg.softuni.cozypetshotel.web.exceptions.RoleDoesNotExistsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(AdminService adminService, UserService userService, RoleService roleService) {
        this.adminService = adminService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPanel() {
        return "/admin";
    }

//    @GetMapping("/contacts")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String viewMessages(Model model) {
//        List<ContactMessage> messages = contactService.findAllContactMessages();
//        model.addAttribute("messages", messages);
//        return "contact-messages";
//    }

//    @GetMapping("/reports")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String viewReports(Model model) {
//        List<Report> reports = reportService.getAllReports();
//        model.addAttribute("reports", reports);
//        return "admin-reports";
//    }

//    @PostMapping("/sendReply")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String sendReply(@Valid ReplyContactMessageDto replyMessageDto,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изпращане на отговора. Моля, проверете въведените данни!");
//            return "redirect:/admin/contacts";
//        }
//
//        try {
//            ContactMessageDto contactMessage = contactService.findMessageById(replyMessageDto.getMessageId());
//            emailService.sendReply(replyMessageDto.getRecipientEmail(), replyMessageDto.getReplyMessage(), contactMessage);
//            redirectAttributes.addFlashAttribute("successMessage", "Отговорът беше изпратен успешно!");
//        } catch (MailException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изпращане на отговора. Моля, опитайте отново!");
//        }
//        return "redirect:/admin/contacts";
//    }

    @PostMapping("/users/{userId}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addRoleToUser(@PathVariable Long userId,
                                @RequestParam("role") String roleName,
                                RedirectAttributes redirectAttributes) {
        try {
            Optional<User> user = adminService.findById(userId);
            user.ifPresent(value -> roleService.addRoleToUser(value, RoleNameEnum.valueOf(roleName)));
            return "redirect:/admin";
        } catch (RoleAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/users/{userId}/remove-role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeRoleFromUser(@PathVariable Long userId,
                                     @RequestParam("role") String roleName,
                                     RedirectAttributes redirectAttributes) {
        try {
            Optional<User> user = adminService.findById(userId);
            user.ifPresent(u -> roleService.removeRoleFromUser(u, RoleNameEnum.valueOf(roleName)));
            return "redirect:/admin";
        } catch (RoleDoesNotExistsException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/users/{userId}/disable")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String disableUser(@PathVariable Long userId,
                              RedirectAttributes redirectAttributes) {
        adminService.disableUser(userId);
        String successMessage = "The user was disabled.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin";
    }

    @PostMapping("/users/{userId}/enable")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String enableUser(@PathVariable Long userId,
                             RedirectAttributes redirectAttributes) {
        adminService.enableUser(userId);
        String successMessage = "Потребителят е активиран.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin";
    }
}
