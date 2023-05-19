package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.dto.SignInInput;
import com.geekster.DoctorApp.dto.SignInOutput;
import com.geekster.DoctorApp.dto.SignUpInput;
import com.geekster.DoctorApp.dto.SignUpOutput;
import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.AppointmentKey;
import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.service.AuthenticationService;
import com.geekster.DoctorApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    AuthenticationService authService;
    //sign up

    //sign up input
    //sign up output
    @PostMapping("/signup")
    public SignUpOutput signup(@RequestBody SignUpInput signUpDto){
        return patientService.signUp(signUpDto);
    }



    //sign in
    @PostMapping("/signin")
    public SignInOutput signup(@RequestBody SignInInput signInDto){
        return patientService.signIn(signInDto);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String userEmail , @RequestParam String token){

        HttpStatus status;
        List<Doctor> allDoctors = null;
        //authenticate

        if(authService.authenticate(userEmail , token)){
            allDoctors = patientService.getAllDoctors();
            status = HttpStatus.OK;
        }else {
            status = HttpStatus.FORBIDDEN;
        }

       // List<Doctor> allDoctors = patientService.getAllDoctors();

        return new ResponseEntity<List<Doctor>>(allDoctors ,status);
    }

    //todo : move the appointment in Appointment controller in here along wirh authentication...!

    //delete my appointment

    @DeleteMapping("/appointment")
    ResponseEntity<Void> cancelAppointment(@RequestParam String userEmail , @RequestParam String token , @RequestBody AppointmentKey key){
        HttpStatus status;
        if(authService.authenticate(userEmail , token)){
            patientService.cancelAppointmentKey(key);
            status = HttpStatus.OK;
        }else {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<Void>(status);
    }


}
