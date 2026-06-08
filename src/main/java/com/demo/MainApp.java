package com.demo;
import com.demo.entity.Student;
import com.demo.entity.Address;
import com.demo.entity.Course;
import com.demo.entity.Department;
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

		Address a1 = new Address();
		a1.setStreet("MG Road");
		a1.setCity("Bangalore");
		a1.setPincode("560001");

		Address a2 = new Address();
		a2.setStreet("Beach Road");
		a2.setCity("Kochi");
		a2.setPincode("682001");

		Student s1 = new Student("Alice", "John", "alice5@gmail.com");
		s1.setAddress(a1);

		Student s2 = new Student("Bob", "Thomas", "bob5@gmail.com");
		s2.setAddress(a2);

		em.persist(s1);
		em.persist(s2);

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
		Student student1 = em.find(Student.class, 7);
		student1.setEmail("alice.new@demo.com");
		em.getTransaction().commit();
		System.out.println("\nStudent 1 email updated!");
		em.getTransaction().begin();
		Student student2 = em.find(Student.class, 8);
		if(student2 != null) {
		em.remove(student2);
		}
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
		Student st1 = s1;
		Student st2 = s2;
		
		em.getTransaction().commit();
		
		Department cs = new Department();
		cs.setName("Computer Science");

		Department it = new Department();
		it.setName("Information Technology");

		cs.addStudent(st1);
		it.addStudent(st2);
		st1.addCourse(java);
		st1.addCourse(spring);
		st2.addCourse(spring);
		em.getTransaction().begin();

		em.persist(java);
		em.persist(spring);
		em.persist(cs);
		em.persist(it);
		cs.getStudents().forEach(System.out::println);
		it.getStudents().forEach(System.out::println);
		em.getTransaction().commit();
		List<Department> departments = em.createQuery(
		        "SELECT d FROM Department d",
		        Department.class)
		        .getResultList();

		System.out.println("\nDepartments and Students:");

		for (Department d : departments) {
		    System.out.println("Department: " + d.getName());

		    for (Student s : d.getStudents()) {
		        System.out.println("   " + s.getFirstName());
		    }
		}

		List<Student> studentList = em.createQuery(
		        "SELECT s FROM Student s",
		        Student.class)
		        .getResultList();
		System.out.println("\nStudents and Courses:");

		for(Student s : studentList) {
		    System.out.println(s.getFirstName());

		    for(Course c : s.getCourses()) {
		        System.out.println("   " + c.getCourseName());
		    }
		}

		System.out.println("\nStudents with Addresses:");

		for(Student s : studentList) {
		    System.out.println(
		        s.getFirstName() + "-" +
		        s.getAddress().getStreet() + "," +
		        s.getAddress().getCity()
		        
		    );
		}
		
		System.out.println("\nMini Project Completed Successfully!");
		em.close();
		emf.close();

	}

}
