package com.geekster.DoctorApp.repository;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.AppointmentKey;
import com.geekster.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppointmentRepo extends JpaRepository<Appointment, AppointmentKey> {
}
