package com.college.helpdesk_chatbot.controller;

import com.college.helpdesk_chatbot.model.FAQ;
import com.college.helpdesk_chatbot.repository.FAQRepository;
import com.college.helpdesk_chatbot.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private AIService aiService;

    @GetMapping("/")
    public String home(Model model) {
        List<FAQ> faqs = faqRepository.findAll();
        model.addAttribute("faqs", faqs);
        return "index";
    }

    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public String handleChat(@RequestParam String question) {
        // First, try to find answer in FAQ database
        String faqAnswer = findAnswerInFAQ(question);

        if (faqAnswer != null) {
            return "📚 From FAQ Database:\n\n" + faqAnswer;
        }

        // If not found in FAQ, use AI
        String aiAnswer = aiService.getAIResponse(question);
        return "🤖 AI Response:\n\n" + aiAnswer;
    }

    private String findAnswerInFAQ(String question) {
        String lowerQuestion = question.toLowerCase();
        List<FAQ> allFAQs = faqRepository.findAll();

        // Check for keyword matches
        for (FAQ faq : allFAQs) {
            String faqQuestion = faq.getQuestion().toLowerCase();

            // Fee related
            if ((lowerQuestion.contains("fee") || lowerQuestion.contains("tuition") ||
                    lowerQuestion.contains("cost")) && faqQuestion.contains("fee")) {
                return faq.getAnswer();
            }

            // Exam related
            if ((lowerQuestion.contains("exam") || lowerQuestion.contains("test") ||
                    lowerQuestion.contains("final")) && faqQuestion.contains("exam")) {
                return faq.getAnswer();
            }

            // Scholarship related
            if (lowerQuestion.contains("scholarship") && faqQuestion.contains("scholarship")) {
                return faq.getAnswer();
            }

            // Course related
            if ((lowerQuestion.contains("course") || lowerQuestion.contains("class") ||
                    lowerQuestion.contains("subject")) && faqQuestion.contains("course")) {
                return faq.getAnswer();
            }

            // Admission related
            if ((lowerQuestion.contains("admission") || lowerQuestion.contains("apply") ||
                    lowerQuestion.contains("application")) && faqQuestion.contains("admission")) {
                return faq.getAnswer();
            }

            // Grade/passing related
            if ((lowerQuestion.contains("grade") || lowerQuestion.contains("pass") ||
                    lowerQuestion.contains("gpa")) && faqQuestion.contains("grade")) {
                return faq.getAnswer();
            }

            // Schedule/timetable related
            if ((lowerQuestion.contains("schedule") || lowerQuestion.contains("timetable") ||
                    lowerQuestion.contains("library")) &&
                    (faqQuestion.contains("schedule") || faqQuestion.contains("library"))) {
                return faq.getAnswer();
            }

            // Major related
            if (lowerQuestion.contains("major") && faqQuestion.contains("major")) {
                return faq.getAnswer();
            }

            // Deadline related
            if (lowerQuestion.contains("deadline") && faqQuestion.contains("deadline")) {
                return faq.getAnswer();
            }
        }

        return null; // Not found in FAQ
    }
}