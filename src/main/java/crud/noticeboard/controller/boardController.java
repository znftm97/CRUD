package crud.noticeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class boardController {

    @GetMapping("/board")
    public String board(){

        return "/boardList";
    }
}
