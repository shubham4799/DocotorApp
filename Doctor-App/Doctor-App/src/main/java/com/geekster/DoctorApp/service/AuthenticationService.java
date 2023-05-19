package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.AuthenticationToken;
import com.geekster.DoctorApp.model.Patient;
import com.geekster.DoctorApp.repository.ITokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    ITokenRepo tokenRepo;
    public void saveToken(AuthenticationToken token){
        tokenRepo.save(token);
    }

    public AuthenticationToken getToken(Patient patient) {
       return tokenRepo.findByPatient(patient);
    }

    public boolean authenticate(String userEmail, String token) {
       AuthenticationToken authToken = tokenRepo.findFirstByToken(token);
       String expectedEmail = authToken.getPatient().getPatientEmail();
       return expectedEmail.equals(userEmail);
    }
}
