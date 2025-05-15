package org.diploma.fordiplom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
    @GetMapping("/project/{projectId}")
    public String showProjectChat(@PathVariable Long projectId, Model model) {
        model.addAttribute("chatId", "projectChat");
        model.addAttribute("projectId", projectId);
        return "chat"; // chat.html
    }

    @GetMapping("/teamChat")
    public String showTeamChat(@RequestParam Long id, Model model) {
        model.addAttribute("chatId", "teamChat");
        model.addAttribute("teamId", id);
        return "chat"; // chat.html
    }

    @GetMapping("/sprint/{sprintId}")
    public String showSprintChat(@PathVariable Long sprintId, Model model) {
        model.addAttribute("chatId", "sprintChat");
        model.addAttribute("sprintId", sprintId);
        return "chat";
    }
}
