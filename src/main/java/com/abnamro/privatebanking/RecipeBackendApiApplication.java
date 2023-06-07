package com.abnamro.privatebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeBackendApiApplication.class, args);
	}

	/*
	 * @Bean
	 * public CommandLineRunner run(UserRepository userRepository) throws Exception
	 * {
	 * return (String[] args) -> {
	 * User user1 = new User("Bob", "bob@domain.com");
	 * User user2 = new User("Jenny", "jenny@domain.com");
	 * userRepository.save(user1);
	 * userRepository.save(user2);
	 * userRepository.findAll().forEach(System.out::println);
	 * };
	 * }
	 */

	/*
	 * @Bean
	 * CommandLineRunner init(EmployeeRepository employeeRepository){
	 * return args -> {
	 * List employees = Stream.of(1, 2, 3, 4).map(count -> {
	 * Employee employee = new Employee();
	 * employee.setName("Emp " + count);
	 * employee.setDescription("Emp " + 1 + " Description");
	 * employee.setBand("E " + count);
	 * employee.setPosition("POS " + count);
	 * employee.setSalary(4563 + (count * 10 + 45 + count));
	 * return employee;
	 * }).collect(Collectors.toList());
	 * 
	 * employeeRepository.saveAll(employees);
	 * };
	 */

}
