package com.safe.online.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JournalController {
    @Autowired
    JournalHelper journalHelper;
    @PostMapping(value = "/addJournal")
    public String addJournal(@ModelAttribute Journal journal, Model model,HttpSession session) {
        String userName = (String) session.getAttribute("UserID");
        journalHelper.createJournal(journal.getComment(),userName);
        return "home";
    }

    @GetMapping(value = "/getJournal")
    public String getJournal(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("UserID");
        List<Journal> list = journalHelper.findAll(userName);
        model.addAttribute("journals", list);
        return "homeHistory";
    }

    @RequestMapping(value = "/viewJournal/{id}", method = RequestMethod.GET)
    public String viewJournal(Model model, HttpSession session,@PathVariable String id) {
        String userName = (String) session.getAttribute("UserID");
        Journal journal = journalHelper.find(userName,Integer.parseInt(id));
        model.addAttribute("journal", journal);
        return "homeView";
    }

    @RequestMapping(value = "/deleteJournal/{id}", method = RequestMethod.GET)
    public String deleteJournal(Model model, HttpSession session,@PathVariable String id) {
        String userName = (String) session.getAttribute("UserID");
        journalHelper.delete(userName,Integer.parseInt(id));
        List<Journal> list = journalHelper.findAll(userName);
        model.addAttribute("journals", list);
        return "homeHistory";
    }
}
