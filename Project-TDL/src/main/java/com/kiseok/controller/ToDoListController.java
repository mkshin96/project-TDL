package com.kiseok.controller;

import com.kiseok.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/toDoList")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;
    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("toDoList", toDoListService.findTdlByIdx(idx));
        return "/toDoList/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("tdlList", toDoListService.findTdlList());
        return "/toDoList/list";
    }


}
