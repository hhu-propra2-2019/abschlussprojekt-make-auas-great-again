package mops.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@Controller
public class webController {
    @GetMapping("/")
    public String init(Model model){
        return "index";
    }
}
