package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.service.RoleService;
import vn.kltn.KLTN.service.UserService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
 // Ds nguoi dung
    @GetMapping
    public String list(Model model) {
        List<User> users = userService.findAll();

        // dem so Admin và User
        long adminCount = users.stream()
                .filter(u -> u.getRole() != null)
                .filter(u -> {
                    String roleType = String.valueOf(u.getRole().getType());
                    return roleType.equalsIgnoreCase("ADMIN") || roleType.equalsIgnoreCase("ROLE_ADMIN");
                })
                .count();

        long userCount = users.size() - adminCount;

        // gui du lieu ra view
        model.addAttribute("users", users);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("totalUsers", users.size());

        return "admin/users/list";
    }

    
 // Form sua user
    @GetMapping("/{username}/edit")
    public String edit(@PathVariable String username, Model model) {
        User user = userService.findById(username);
        model.addAttribute("user", user);
        model.addAttribute("roles", RoleAvailable.values());
        return "admin/users/edit";
    }
    
 // luu cap nhat
    @PostMapping("/{username}/edit")
    public String doEdit(
            @PathVariable String username,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam(required = false) String address,
            @RequestParam(required = false, name = "phoneNumber") String phone,
            @RequestParam RoleAvailable role
    ) {
        User user = userService.findById(username);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhoneNumber(phone);

        Role roleEntity = roleService.findByType(role);
        user.setRole(roleEntity);

        userService.update(user);
        return "redirect:/admin/users";
    }
    
 // Xóa user
    @PostMapping("/{username}/delete")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return "redirect:/admin/users";
    }
}
