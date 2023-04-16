package ma.enset;

import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;


@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}
	
	/*CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
		return args -> {
			patientRepository.save(new Patient(2,"Firdaous",new Date(2001),false,8));
			patientRepository.save(new Patient(3,"Mariam",new Date(2002),true,18));
			
			patientRepository.findAll().forEach(p -> {
				System.out.println(p.getNom());	
			}
			);
		};*/

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
