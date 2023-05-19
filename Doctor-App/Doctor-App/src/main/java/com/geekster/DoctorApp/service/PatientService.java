package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.dto.SignInInput;
import com.geekster.DoctorApp.dto.SignInOutput;
import com.geekster.DoctorApp.dto.SignUpInput;
import com.geekster.DoctorApp.dto.SignUpOutput;
import com.geekster.DoctorApp.model.AppointmentKey;
import com.geekster.DoctorApp.model.AuthenticationToken;
import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.model.Patient;
import com.geekster.DoctorApp.repository.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    IPatientRepo iPatientRepo;

    @Autowired
    AuthenticationService tokenService;

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    DoctorService doctorService;

    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if user exist or not based on email

        Patient patient= iPatientRepo.findFirstByPatientEmail(signUpDto.getUserEmail());

        if(patient != null){
            throw new IllegalStateException("Sign In again...Patient already exist!!");
        }
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //encryption

        //save the user
        patient = new Patient(signUpDto.getUserFirstName(),signUpDto.getUserLastName(),signUpDto.getUserEmail(),encryptedPassword,signUpDto.getUserContact());


        iPatientRepo.save(patient);

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(patient);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","Patient created successfully");

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(userPassword.getBytes());
        byte[] digested =md5.digest();
        String hash = new String(digested);
       // md5.update(userPassword.getBytes());
      //  byte[] digested = md5.digest();

       // String hash = DatatypeConverter.printHexBinary(digested);
       // String hash = new String(digested);
        return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //get email
        Patient patient= iPatientRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

        if(patient == null){
            throw new IllegalStateException("Sign Up again...User invalid insted!!");
        }

        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signInDto.getPatientPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //encrypy the password
        //match it in database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(patient.getPatientPassword());

        if(!isPasswordValid){
            throw new IllegalStateException("Sign Up again...User invalid insted!!");
        }

        //figure out the token

        AuthenticationToken authToken = tokenService.getToken(patient);

       // set up output response

        return new SignInOutput("Authentication Successfull !!",authToken.getToken());
    }

    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    public void cancelAppointmentKey(AppointmentKey key) {
        appointmentService.cancelAppointmentKey(key);
    }
}
