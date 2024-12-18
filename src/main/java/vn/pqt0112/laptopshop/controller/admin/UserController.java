package vn.pqt0112.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.pqt0112.laptopshop.domain.Product;
import vn.pqt0112.laptopshop.domain.User;
import vn.pqt0112.laptopshop.service.UploadService;
import vn.pqt0112.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;

    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {

            }
        } catch (Exception e) {

        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<User> usrs = this.userService.fetchUsers(pageable);
        List<User> users = usrs.getContent();
        model.addAttribute("user1", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usrs.getTotalPages());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);

        return "admin/user/detail";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User pqt0112,
            BindingResult newUserBindingResult,
            @RequestParam("pqt0112File") MultipartFile file
    ) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        //validate
        if (newUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(pqt0112.getPassword());

        pqt0112.setAvatar(avatar);
        pqt0112.setPassword(hashPassword);
        pqt0112.setRole(this.userService.getRoleByName(pqt0112.getRole().getName()));

        this.userService.handleSaveUser(pqt0112);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update") // GET
    public String getUpdateUser(Model model, @ModelAttribute("newUser") User pqt) {
        User currentUser = this.userService.getUserById(pqt.getId());
        if (currentUser != null) {
            currentUser.setAddress(pqt.getAddress());
            currentUser.setEmail(pqt.getEmail());
            currentUser.setFullName(pqt.getFullName());
            currentUser.setPhone(pqt.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);

        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String DeleteUser(Model model, @ModelAttribute("newUser") User pqt) {
        this.userService.deleteUser(pqt.getId());
        return "redirect:/admin/user";
    }

    @RequestMapping("/client/profile/edit-profile/{id}") // GET
    public String getUpdateProfilePage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Kiểm tra xem session có tồn tại và có chứa id hay không
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập nếu không có session
        }

        long id = (long) session.getAttribute("id");
        User profile = this.userService.getUserById(id);

        if (profile == null) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu user không tồn tại
        }

        model.addAttribute("user", profile);
        return "client/profile/edit-profile";
    }

    // @PostMapping("/client/profile/edit-profile")
    // public String getUpdateProfile(Model model, @ModelAttribute("edit-profile") User pqt) {
    //     User profile = this.userService.getUserById(pqt.getId());
    //     if (profile != null) {
    //         profile.setAddress(pqt.getAddress());
    //         profile.setEmail(pqt.getEmail());
    //         profile.setFullName(pqt.getFullName());
    //         profile.setPhone(pqt.getPhone());
    //         this.userService.handleSaveUser(profile);
    //         return "redirect:/";
    //     }
    //     // Nếu không tìm thấy profile hoặc lỗi xảy ra
    //     model.addAttribute("profile", pqt); // Gắn lại thông tin người dùng vào Model để hiển thị form
    //     return "client/profile/edit-profile"; // Hiển thị lại trang chỉnh sửa với thông tin lỗi
    // }
}
