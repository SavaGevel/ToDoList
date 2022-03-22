package main;

import main.model.Task;
import main.model.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoListController {

    @Autowired
    private TaskListRepository taskListRepository;

    @GetMapping("/tasks")
    public List<Task> getTasksList() {
        List<Task> taskList = new LinkedList<>();
        for(Task task : taskListRepository.findAll()) {
            taskList.add(task);
        }
        return taskList;
    }

    @PostMapping("/tasks")
    public int createTask(String text) {
        Task task = new Task();
        task.setTask(text);
        return taskListRepository.save(task).getId();
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity updateTask(@PathVariable int id, String text) {

        Optional<Task> optionalTask = taskListRepository.findById(id);

        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTask(text);
            taskListRepository.save(task);
            return new ResponseEntity(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {

        Optional<Task> optionalTask = taskListRepository.findById(id);

        if(optionalTask.isPresent()) {
            taskListRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    @DeleteMapping("/tasks")
    public void deleteAllTasks() {
        taskListRepository.deleteAll();
    }

}
