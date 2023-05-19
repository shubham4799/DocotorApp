package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private IDoctorRepo doctorRepo;

    public void addDoc(Doctor doc) {
        doctorRepo.save(doc);
    }

    public List<Doctor> getAllDoctors() {
       List<Doctor> allDoctors = doctorRepo.findAll();
       return allDoctors;
    }

    public List<Appointment> getMyAppointments(Long docid) {
        Doctor myDoc = doctorRepo.findByDoctorId(docid);
        if(myDoc == null){
            throw new IllegalStateException("The doctor does not exist!!");
        }
        return myDoc.getAppointments();
    }
}
