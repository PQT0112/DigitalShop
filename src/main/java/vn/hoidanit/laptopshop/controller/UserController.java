package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.RequestDispatcher;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUser = this.userService.getAllUserByEmail("truo@gmail.com");
        System.out.println(arrUser);
        model.addAttribute("pqt", "test");
        model.addAttribute("qt0112", "spring");
        return "hello1";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("user1", users);

        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetalPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);

        return "admin/user/user-detail";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User pqt) {
        this.userService.handleSaveUser(pqt);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/create") // GET
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

}
