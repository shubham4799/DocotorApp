package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.AppointmentKey;
import com.geekster.DoctorApp.repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;

    public void bookAppontment(Appointment appointment) {



        appointmentRepo.save(appointment);
    }

    public void cancelAppointmentKey(AppointmentKey key) {
        appointmentRepo.deleteById(key);
    }
}
