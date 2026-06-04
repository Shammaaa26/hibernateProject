package com.demo;
import com.demo.entity.Student;
import com.demo.entity.Course;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class MainApp {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("HibernateJPADemo");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Student s1 = new Student("Alice", "Smith", "alice@demo.com");
		Student s2 = new Student("Bob", "Johnson", "bob@demo.com");
		Student s3 = new Student("Charlie", "Brown", "charlie@demo.com");
		em.persist(s1);
		em.persist(s2);
		em.persist(s3);
		em.getTransaction().commit();
		Student foundStudent = em.find(Student.class, 1);
		System.out.println("Student with ID 1:");
		System.out.println(foundStudent);
		
		List<Student> students = em.createQuery(
				"SELECT s FROM Student s",
				Student.class)
				.getResultList();
		System.out.println("\nAll Students:");
		for(Student s : students) {
			System.out.println(s);
		}
		em.getTransaction().begin();
		Student student1 = em.find(Student.class, 1);
		student1.setEmail("alice.new@demo.com");
		em.getTransaction().commit();
		System.out.println("\nStudent 1 email updated!");
		em.getTransaction().begin();
		Student student2 = em.find(Student.class, 2);
		em.remove(student2);
		em.getTransaction().commit();
		System.out.println("Student 2 deleted!");
		List<Student> remainingStudents = em.createQuery(
				"SELECT s FROM Student s",
				Student.class)
				.getResultList();
		System.out.println("\nRemaining Students:");
		for(Student s : remainingStudents) {
			System.out.println(s);
		}
		System.out.println("Student saved successfully!");
		em.getTransaction().begin();
		Course java = new Course("Java Programming", "3 monnths");
		Course spring = new Course("Spring Boot", "2 months");
		em.persist(java);;
		em.persist(spring);
		em.getTransaction().commit();
		em.getTransaction().begin();
		Student st1 = em.find(Student.class , 1);
		Student st3 = em.find(Student.class, 3);
		st1.setCourse(java);
		st3.setCourse(spring);
		em.getTransaction().commit();
		List<Student> studentList = em.createQuery(
		        "SELECT s FROM Student s",
		        Student.class)
		        .getResultList();

		System.out.println("\nStudents with Courses:");

		for(Student s : studentList) {
		    System.out.println(
		        s.getFirstName() + " -> " +
		        s.getCourse().getCourseName()
		    );
		}
		List<Student> javaStudents = em.createQuery(
			    "SELECT s FROM Student s WHERE s.course.courseName = :name",
			    Student.class)
			    .setParameter("name", "Java Programming")
			    .getResultList();

			System.out.println("\nStudents enrolled in Java Programming:");

			for(Student s : javaStudents) {
			    System.out.println(s.getFirstName() + " - " + s.getEmail());
			}
		System.out.println("\nMini Project Completed Successfully!");
		em.close();
		emf.close();

	}

}
