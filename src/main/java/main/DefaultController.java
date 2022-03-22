package main;

import main.model.Task;
import main.model.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    TaskListRepository taskListRepository;

    @RequestMapping("/")
    public String index(Model model) {

        List<Task> tasks = new LinkedList<>();
        for(Task task : taskListRepository.findAll()) {
            tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        return "index";
    }
}
