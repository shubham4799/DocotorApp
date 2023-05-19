package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping()
    public void bookAppontment(@RequestBody Appointment appointment)
    {
        appointmentService.bookAppontment(appointment);
    }
}
