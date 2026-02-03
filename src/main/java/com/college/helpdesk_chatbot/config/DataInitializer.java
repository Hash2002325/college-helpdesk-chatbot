package com.college.helpdesk_chatbot.config;

import com.college.helpdesk_chatbot.model.FAQ;
import com.college.helpdesk_chatbot.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FAQRepository faqRepository;

    @Override
    public void run(String... args) throws Exception {
        if (faqRepository.count() == 0) {

            FAQ faq1 = new FAQ();
            faq1.setQuestion("What are the tuition fees for undergraduate programs?");
            faq1.setAnswer("The tuition fees for undergraduate programs are $5,000 per semester. This includes all academic charges but excludes accommodation and meal plans.");
            faq1.setCategory("FEES");
            faqRepository.save(faq1);

            FAQ faq2 = new FAQ();
            faq2.setQuestion("Are there any scholarships available?");
            faq2.setAnswer("Yes! We offer merit-based scholarships up to 50% tuition waiver for students with GPA above 3.5. Need-based financial aid is also available.");
            faq2.setCategory("FEES");
            faqRepository.save(faq2);

            FAQ faq3 = new FAQ();
            faq3.setQuestion("When are the final exams scheduled?");
            faq3.setAnswer("Final exams are typically held in the last two weeks of each semester. The exact schedule is published 4 weeks before exam week.");
            faq3.setCategory("EXAMS");
            faqRepository.save(faq3);

            FAQ faq4 = new FAQ();
            faq4.setQuestion("What is the passing grade?");
            faq4.setAnswer("The minimum passing grade is D (50%). However, a C grade (60%) is required to progress to advanced courses in your major.");
            faq4.setCategory("EXAMS");
            faqRepository.save(faq4);

            FAQ faq5 = new FAQ();
            faq5.setQuestion("How many courses can I take per semester?");
            faq5.setAnswer("Full-time students typically take 4-6 courses per semester (12-18 credits). You need advisor approval to take more than 18 credits.");
            faq5.setCategory("COURSES");
            faqRepository.save(faq5);

            FAQ faq6 = new FAQ();
            faq6.setQuestion("Can I change my major?");
            faq6.setAnswer("Yes, you can change your major once during your first two years without penalty. Visit the Registrar's Office with your advisor's approval.");
            faq6.setCategory("COURSES");
            faqRepository.save(faq6);

            FAQ faq7 = new FAQ();
            faq7.setQuestion("What are the admission requirements?");
            faq7.setAnswer("You need a high school diploma with minimum 3.0 GPA, SAT scores (optional), recommendation letters, and a personal statement.");
            faq7.setCategory("ADMISSIONS");
            faqRepository.save(faq7);

            FAQ faq8 = new FAQ();
            faq8.setQuestion("When is the application deadline?");
            faq8.setAnswer("Fall semester: June 30th, Spring semester: November 30th. Early admission deadline is one month earlier for both semesters.");
            faq8.setCategory("ADMISSIONS");
            faqRepository.save(faq8);

            FAQ faq9 = new FAQ();
            faq9.setQuestion("Where can I find my class schedule?");
            faq9.setAnswer("Log in to the student portal at portal.college.edu and click on 'My Schedule'. You can also download the mobile app for easy access.");
            faq9.setCategory("TIMETABLE");
            faqRepository.save(faq9);

            FAQ faq10 = new FAQ();
            faq10.setQuestion("What are the library hours?");
            faq10.setAnswer("Main library: Monday-Friday 8AM-10PM, Saturday-Sunday 10AM-8PM. During finals week, we're open 24/7.");
            faq10.setCategory("TIMETABLE");
            faqRepository.save(faq10);

            System.out.println("✅ Sample FAQ data has been loaded!");
        }
    }
}