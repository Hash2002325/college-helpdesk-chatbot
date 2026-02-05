package com.college.helpdesk_chatbot.controller;

import com.college.helpdesk_chatbot.model.FAQ;
import com.college.helpdesk_chatbot.repository.FAQRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FAQRepository faqRepository;

    // Simple hardcoded credentials (In real project, use database + encryption)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // Login page
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "admin-login";
    }

    // Handle login
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/login?error=true";
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        List<FAQ> faqs = faqRepository.findAll();
        model.addAttribute("faqs", faqs);
        model.addAttribute("totalFaqs", faqs.size());

        // Count unique categories
        Set<String> uniqueCategories = new HashSet<>();
        for (FAQ faq : faqs) {
            uniqueCategories.add(faq.getCategory());
        }
        model.addAttribute("categories", uniqueCategories.size());

        return "admin-dashboard";
    }

    // Add FAQ page
    @GetMapping("/faq/add")
    public String addFaqPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin-add-faq";
    }

    // Handle add FAQ
    @PostMapping("/faq/add")
    public String handleAddFaq(@RequestParam String question,
                               @RequestParam String answer,
                               @RequestParam String category,
                               HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        FAQ faq = new FAQ();
        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setCategory(category);
        faqRepository.save(faq);

        return "redirect:/admin/dashboard";
    }

    // Edit FAQ page
    @GetMapping("/faq/edit/{id}")
    public String editFaqPage(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        FAQ faq = faqRepository.findById(id).orElse(null);
        if (faq == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("faq", faq);
        return "admin-edit-faq";
    }

    // Handle edit FAQ
    @PostMapping("/faq/edit/{id}")
    public String handleEditFaq(@PathVariable Long id,
                                @RequestParam String question,
                                @RequestParam String answer,
                                @RequestParam String category,
                                HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        FAQ faq = faqRepository.findById(id).orElse(null);
        if (faq != null) {
            faq.setQuestion(question);
            faq.setAnswer(answer);
            faq.setCategory(category);
            faqRepository.save(faq);
        }

        return "redirect:/admin/dashboard";
    }

    // Delete FAQ
    @PostMapping("/faq/delete/{id}")
    public String deleteFaq(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        faqRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}