package com.geekster.DoctorApp.repository;

import com.geekster.DoctorApp.model.AuthenticationToken;
import com.geekster.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepo extends JpaRepository<AuthenticationToken , Long> {

    AuthenticationToken findByPatient(Patient patient);

    AuthenticationToken findFirstByToken(String token);
}
