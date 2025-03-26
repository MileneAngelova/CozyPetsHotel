package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.views.UserViewModel;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.services.AdminService;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.RoleService;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import bg.softuni.cozypetshotel.session.AppUserDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
//@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final RoleService roleService;
    private final BookingService bookingService;
    private static int currentPageOn;
    private final UserRepository userRepository;

    public AdminController(AdminService adminService, UserService userService, RoleService roleService, BookingService bookingService,
                           UserRepository userRepository) {
        this.adminService = adminService;
        this.userService = userService;
        this.roleService = roleService;
        this.bookingService = bookingService;
        this.userRepository = userRepository;
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
    @GetMapping("/admin/users/all/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<UserViewModel> page = adminService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalUsers = userRepository.count();
        List<UserViewModel> users = page.getContent();
        currentPageOn = currentPage;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("users", users);

        return "redirect:/admin";
    }

//    @GetMapping("/admin/users/all")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String getAllPages(Model model) {
//        return getOnePage(model, 1);
//    }

    //    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/admin/users/activate/{id}")
    public String activateUserAccount(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        adminService.activateUserAccount(id);
        String successMessage = "User activated";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/users/deactivate/{id}")
    public String blockUserAccount(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        adminService.blockUser(id);
        String successMessage = "The user was disabled.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/users/promote/{id}")
    public String promoteUser(@PathVariable("id") Long id) {
        adminService.addRoleAdminToUser(id);
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/users/demote/{id}")
    @Transactional
    public String demoteUser(@PathVariable("id") Long id) {
        adminService.removeRoleAdminFromUser(id);
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/bookings/all")
    public String getAllBookings(Model model, @PageableDefault(size = 5, sort = "id") Pageable pageable, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        Page<BookingDTO> allBookings = bookingService.getAllBookings(pageable);
        model.addAttribute("allBookings", allBookings);
        return "admin_bookings-all";
    }
}
