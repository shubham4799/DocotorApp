package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService docService;

    @PostMapping()
    void addDoctors(@RequestBody Doctor doc){
        docService.addDoc(doc);
    }

    @GetMapping("{docid}/appointment")
    ResponseEntity<List<Appointment>> getDocMyAppointments(@PathVariable Long docid){
        List<Appointment> myAppointments = null;
        HttpStatus status;
        try {
            myAppointments = docService.getMyAppointments(docid);
            if(myAppointments.isEmpty()){
                status = HttpStatus.NO_CONTENT;
            }else {
                status = HttpStatus.OK;
            }
        }catch (Exception e){
            System.out.println("The doc Id is not valid!!");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<List<Appointment>>(myAppointments , status);
    }
}
