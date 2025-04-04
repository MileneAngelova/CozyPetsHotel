package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.services.AdminService;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {
    private final AdminService adminService;
    private final BookingService bookingService;

    public AdminController(AdminService adminService, BookingService bookingService) {
        this.adminService = adminService;
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getAllUsers(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<UserDTO> allUsers = this.adminService.getAllUsers();
            model.addAttribute("allUsers", allUsers);
        } catch (NullPointerException exception) {
            redirectAttributes.addFlashAttribute(exception.getMessage());
        }
        return "admin_users-all";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/admin/users/activate/{email}")
    public String activateUserAccount(@PathVariable("email") String email, RedirectAttributes redirectAttributes) {
        adminService.activateUserAccount(email);
        String successMessage = "User activated";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/users/deactivate/{email}")
    public String blockUserAccount(@PathVariable String email, RedirectAttributes redirectAttributes) {
        adminService.blockUser(email);
        String successMessage = "The user was disabled.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin";
    }

    @PostMapping("/admin/users/promote/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String promoteUser(@PathVariable("email") String email) {
        adminService.addRoleAdminToUser(email);
        return "redirect:/admin";
    }

    @PostMapping("/admin/users/demote/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public String demoteUser(@PathVariable("email") String email) {
        adminService.removeRoleAdminFromUser(email);
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/bookings/all")
    public String getAllBookings(Model model) {
        List<BookingDTO> allBookings = bookingService.getAllBookings();
        model.addAttribute("allBookings", allBookings);
        return "admin_bookings-all";
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/users/delete/{email}")
    public String deleteUser(@PathVariable String email) {
        this.adminService.deleteUser(email);
        return "redirect:/admin";
    }
}
