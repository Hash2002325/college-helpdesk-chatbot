package com.college.helpdesk_chatbot.repository;

import com.college.helpdesk_chatbot.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

    // Find FAQs by category
    List<FAQ> findByCategory(String category);

    // Search FAQs by question containing keyword
    List<FAQ> findByQuestionContainingIgnoreCase(String keyword);
}