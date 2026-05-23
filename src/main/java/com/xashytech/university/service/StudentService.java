package com.xashytech.university.service;

import com.xashytech.university.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Service class for managing students
 * In a real application, this would interact with a database
 * For teaching purposes, we'll use an in-memory list
 */
public class StudentService {

    private static final Logger LOGGER = Logger.getLogger(StudentService.class.getName());
    private static final List<Student> students = new ArrayList<>();
    private static final AtomicLong idCounter = new AtomicLong(1);
    private static boolean initialized = false;
    
    // Initialize with some sample data
    static {
        initializeSampleData();
    }
    
    private static synchronized void initializeSampleData() {
        if (!initialized) {
            students.clear();
            idCounter.set(1);
            
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            Student student1 = new Student();
            student1.setId(idCounter.getAndIncrement());
            student1.setName("Alice Johnson");
            student1.setEmail("alice.johnson@xashytech.edu");
            student1.setMajor("Computer Science");
            student1.setEnrollmentDate(currentDate);
            students.add(student1);
            
            Student student2 = new Student();
            student2.setId(idCounter.getAndIncrement());
            student2.setName("Bob Smith");
            student2.setEmail("bob.smith@xashytech.edu");
            student2.setMajor("Information Technology");
            student2.setEnrollmentDate(currentDate);
            students.add(student2);
            
            Student student3 = new Student();
            student3.setId(idCounter.getAndIncrement());
            student3.setName("Carol Davis");
            student3.setEmail("carol.davis@xashytech.edu");
            student3.setMajor("Software Engineering");
            student3.setEnrollmentDate(currentDate);
            students.add(student3);
            
            Student student4 = new Student();
            student4.setId(idCounter.getAndIncrement());
            student4.setName("David Wilson");
            student4.setEmail("david.wilson@xashytech.edu");
            student4.setMajor("Data Science");
            student4.setEnrollmentDate(currentDate);
            students.add(student4);
            
            initialized = true;
            LOGGER.info(() -> "Initialized " + students.size() + " sample students");
        }
    }
    
    /**
     * Reset service to initial state (for testing)
     */
    public static synchronized void resetToInitialState() {
        initialized = false;
        initializeSampleData();
    }
    
    /**
     * Get all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    /**
     * Get a student by ID
     */
    public Student getStudentById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Add a new student
     */
    public Student addStudent(Student student) {
        student.setId(idCounter.getAndIncrement());
        student.setEnrollmentDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        students.add(student);
        LOGGER.info(() -> "Added new student: " + student.getName() + " (ID: " + student.getId() + ")");
        return student;
    }
    
    /**
     * Update an existing student
     */
    public boolean updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updatedStudent.getId())) {
                students.set(i, updatedStudent);
                LOGGER.info(() -> "Updated student: " + updatedStudent.getName());
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete a student by ID
     */
    public boolean deleteStudent(Long id) {
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        if (removed) {
            LOGGER.info(() -> "Deleted student with ID: " + id);
        }
        return removed;
    }
    
    /**
     * Find students by major
     */
    public List<Student> getStudentsByMajor(String major) {
        return students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get total number of students
     */
    public int getStudentCount() {
        return students.size();
    }
}
