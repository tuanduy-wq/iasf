package com.example.iasf.controller;

import com.example.iasf.entity.User;
import com.example.iasf.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Luôn khởi tạo sẵn đối tượng User trống cho Model trong mọi request
    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }

    // Trang chủ: danh sách + tìm kiếm (đã rút gọn gọn gàng)
    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("users", service.search(keyword));
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // Tạo mới hoặc cập nhật
    @PostMapping("/users")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {

        boolean isCreate = (user.getId() == null);

        if (!result.hasFieldErrors("name")) {
            if (isCreate && service.isNameExist(user.getName())) {
                model.addAttribute("errorMessage",
                        "Error while creating User: Unable to create. A User with name "
                                + user.getName() + " already exist.");
                return reloadForm(model);
            }
            if (!isCreate && service.isNameExistForOther(user.getName(), user.getId())) {
                model.addAttribute("errorMessage",
                        "Error while updating User: Unable to update. A User with name "
                                + user.getName() + " already exist.");
                return reloadForm(model);
            }
        }

        if (result.hasErrors()) {
            return reloadForm(model);
        }

        user.setName(user.getName().trim());
        User saved = service.save(user);

        if (isCreate) {
            ra.addFlashAttribute("successMessage", "User created successfully");
            return "redirect:/";
        }
        ra.addFlashAttribute("successMessage", "User updated successfully");
        return "redirect:/users/edit/" + saved.getId();
    }

    // Edit: nạp dữ liệu lên form
    @GetMapping("/users/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        User user = service.findById(id);
        if (user == null) {
            ra.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return reloadForm(model);
    }

    // Remove
    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("successMessage", "User deleted successfully");
        return "redirect:/";
    }

    private String reloadForm(Model model) {
        model.addAttribute("users", service.findAll());
        return "index";
    }
}