package com.college.helpdesk_chatbot.controller;

import com.college.helpdesk_chatbot.model.FAQ;
import com.college.helpdesk_chatbot.repository.FAQRepository;
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
        // Simple keyword-based search for now
        String answer = findAnswer(question);
        return answer;
    }

    private String findAnswer(String question) {
        String lowerQuestion = question.toLowerCase();

        // Search through all FAQs
        List<FAQ> allFAQs = faqRepository.findAll();

        for (FAQ faq : allFAQs) {
            String faqQuestion = faq.getQuestion().toLowerCase();

            // Check if FAQ question contains similar keywords
            if (containsSimilarKeywords(lowerQuestion, faqQuestion)) {
                return faq.getAnswer();
            }
        }

        // If no match found, try keyword search in questions
        for (FAQ faq : allFAQs) {
            String faqQuestion = faq.getQuestion().toLowerCase();
            String faqAnswer = faq.getAnswer().toLowerCase();

            // Check for key terms
            if (lowerQuestion.contains("fee") && faqQuestion.contains("fee")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("exam") && faqQuestion.contains("exam")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("scholarship") && faqQuestion.contains("scholarship")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("course") && faqQuestion.contains("course")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("admission") && faqQuestion.contains("admission")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("deadline") && faqQuestion.contains("deadline")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("schedule") && faqQuestion.contains("schedule")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("library") && faqQuestion.contains("library")) {
                return faq.getAnswer();
            }
            if (lowerQuestion.contains("grade") || lowerQuestion.contains("passing")) {
                if (faqQuestion.contains("grade") || faqQuestion.contains("passing")) {
                    return faq.getAnswer();
                }
            }
            if (lowerQuestion.contains("major") && faqQuestion.contains("major")) {
                return faq.getAnswer();
            }
        }

        return "I'm sorry, I don't have information about that. Please try asking about fees, exams, courses, admissions, or schedules. You can also contact the admin office for more specific queries.";
    }

    private boolean containsSimilarKeywords(String question, String faqQuestion) {
        String[] questionWords = question.split("\\s+");
        int matchCount = 0;

        for (String word : questionWords) {
            if (word.length() > 3 && faqQuestion.contains(word)) {
                matchCount++;
            }
        }

        return matchCount >= 2;
    }
}