package com.ayush.hms.controller;

import com.ayush.hms.dao.AppointmentDAO;
import com.ayush.hms.dao.DoctorDAO;
import com.ayush.hms.dao.PatientDAO;
import com.ayush.hms.dao.UserDAO;

import com.ayush.hms.model.Appointment;
import com.ayush.hms.model.AppointmentView;
import com.ayush.hms.model.Doctor;
import com.ayush.hms.model.DoctorView;
import com.ayush.hms.model.Patient;
import com.ayush.hms.model.PatientView;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final UserDAO userDAO =
            new UserDAO();

    private final PatientDAO patientDAO =
            new PatientDAO();

    private final DoctorDAO doctorDAO =
            new DoctorDAO();

    private final AppointmentDAO appointmentDAO =
            new AppointmentDAO();


    // =========================================================
    // LOGIN
    // =========================================================

    @GetMapping("/")
    public String loginPage() {

        return "login";
    }


    @GetMapping("/login")
    public String loginPageDirect() {

        return "login";
    }


    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {


        System.out.println(
                "LOGIN REQUEST RECEIVED"
        );


        System.out.println(
                "Username: " + username
        );


        boolean success =
                userDAO.login(
                        username,
                        password
                );


        if (success) {

            System.out.println(
                    "LOGIN SUCCESS"
            );


            // Login session create karo
            session.setAttribute(
                    "username",
                    username
            );


            return "redirect:/dashboard";
        }


        System.out.println(
                "LOGIN FAILED"
        );


        model.addAttribute(
                "error",
                "Invalid username or password"
        );


        return "login";
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @GetMapping("/logout")
    public String logout(
            HttpSession session) {


        // Current session destroy karo
        session.invalidate();


        // Login page par wapas bhejo
        return "redirect:/login";
    }


    // =========================================================
    // DASHBOARD
    // =========================================================

    @GetMapping("/dashboard")
    public String dashboard(
            Model model) {


        int patientCount =
                patientDAO.getPatientCount();


        int doctorCount =
                doctorDAO.getDoctorCount();


        int appointmentCount =
                appointmentDAO.getAppointmentCount();


        List<AppointmentView> recentAppointments =
                appointmentDAO.getRecentAppointments();


        model.addAttribute(
                "patientCount",
                patientCount
        );


        model.addAttribute(
                "doctorCount",
                doctorCount
        );


        model.addAttribute(
                "appointmentCount",
                appointmentCount
        );


        model.addAttribute(
                "recentAppointments",
                recentAppointments
        );


        return "dashboard";
    }


    // =========================================================
    // PATIENTS
    // =========================================================

    // ---------------------------------------------------------
    // Patient List + Search
    // ---------------------------------------------------------

    @GetMapping("/patients")
    public String patients(
            @RequestParam(
                    required = false,
                    defaultValue = ""
            )
            String search,

            Model model) {


        List<PatientView> patients;


        if (search == null ||
                search.trim().isEmpty()) {

            patients =
                    patientDAO.getAllPatients();

        } else {

            patients =
                    patientDAO.searchPatients(
                            search.trim()
                    );
        }


        model.addAttribute(
                "patients",
                patients
        );


        model.addAttribute(
                "search",
                search
        );


        return "patients";
    }


    // ---------------------------------------------------------
    // Add Patient Page
    // ---------------------------------------------------------

    @GetMapping("/patients/add")
    public String addPatientPage() {

        return "add-patient";
    }


    // ---------------------------------------------------------
    // Add Patient
    // ---------------------------------------------------------

    @PostMapping("/patients/add")
    public String addPatient(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String gender,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {


        Patient patient =
                new Patient();


        patient.setName(
                name
        );


        patient.setAge(
                age
        );


        patient.setGender(
                gender
        );


        patient.setPhone(
                phone
        );


        patient.setAddress(
                address
        );


        boolean success =
                patientDAO.addPatient(
                        patient
                );


        if (success) {

            return "redirect:/patients";
        }


        model.addAttribute(
                "error",
                "Failed to add patient"
        );


        return "add-patient";
    }


    // ---------------------------------------------------------
    // Update Patient Page
    // ---------------------------------------------------------

    @GetMapping("/patients/update")
    public String updatePatientPage(
            @RequestParam int id,
            Model model) {


        List<PatientView> patients =
                patientDAO.getAllPatients();


        PatientView selectedPatient =
                null;


        for (PatientView patient : patients) {

            if (patient.getId() == id) {

                selectedPatient =
                        patient;

                break;
            }
        }


        if (selectedPatient == null) {

            return "redirect:/patients";
        }


        model.addAttribute(
                "patient",
                selectedPatient
        );


        return "update-patient";
    }


    // ---------------------------------------------------------
    // Update Patient
    // ---------------------------------------------------------

    @PostMapping("/patients/update")
    public String updatePatient(
            @RequestParam int id,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {


        boolean success =
                patientDAO.updatePatient(
                        id,
                        phone,
                        address
                );


        if (success) {

            return "redirect:/patients";
        }


        model.addAttribute(
                "error",
                "Failed to update patient"
        );


        return "redirect:/patients";
    }


    // ---------------------------------------------------------
    // Delete Patient
    // ---------------------------------------------------------

    @GetMapping("/patients/delete")
    public String deletePatient(
            @RequestParam int id) {


        patientDAO.deletePatient(
                id
        );


        return "redirect:/patients";
    }


    // =========================================================
    // DOCTORS
    // =========================================================

    // ---------------------------------------------------------
    // Doctor List + Search
    // ---------------------------------------------------------

    @GetMapping("/doctors")
    public String doctors(
            @RequestParam(
                    required = false,
                    defaultValue = ""
            )
            String search,

            Model model) {


        List<DoctorView> doctors;


        if (search == null ||
                search.trim().isEmpty()) {

            doctors =
                    doctorDAO.getAllDoctors();

        } else {

            doctors =
                    doctorDAO.searchDoctors(
                            search.trim()
                    );
        }


        model.addAttribute(
                "doctors",
                doctors
        );


        model.addAttribute(
                "search",
                search
        );


        return "doctors";
    }


    // ---------------------------------------------------------
    // Add Doctor Page
    // ---------------------------------------------------------

    @GetMapping("/doctors/add")
    public String addDoctorPage() {

        return "doctor-add";
    }


    // ---------------------------------------------------------
    // Add Doctor
    // ---------------------------------------------------------

    @PostMapping("/doctors/add")
    public String addDoctor(
            @RequestParam String name,
            @RequestParam String specialization,
            @RequestParam String experience,
            @RequestParam String phone,
            Model model) {


        Doctor doctor =
                new Doctor();


        doctor.setName(
                name
        );


        doctor.setSpecialization(
                specialization
        );


        doctor.setExperience(
                experience
        );


        doctor.setPhone(
                phone
        );


        boolean success =
                doctorDAO.addDoctor(
                        doctor
                );


        if (success) {

            return "redirect:/doctors";
        }


        model.addAttribute(
                "error",
                "Failed to add doctor"
        );


        return "doctor-add";
    }


    // ---------------------------------------------------------
    // Update Doctor Page
    // ---------------------------------------------------------

    @GetMapping("/doctors/update")
    public String updateDoctorPage(
            @RequestParam int id,
            Model model) {


        List<DoctorView> doctors =
                doctorDAO.getAllDoctors();


        DoctorView selectedDoctor =
                null;


        for (DoctorView doctor : doctors) {

            if (doctor.getId() == id) {

                selectedDoctor =
                        doctor;

                break;
            }
        }


        if (selectedDoctor == null) {

            return "redirect:/doctors";
        }


        model.addAttribute(
                "doctor",
                selectedDoctor
        );


        return "doctor-update";
    }


    // ---------------------------------------------------------
    // Update Doctor
    // ---------------------------------------------------------

    @PostMapping("/doctors/update")
    public String updateDoctor(
            @RequestParam int id,
            @RequestParam String specialization,
            @RequestParam String phone) {


        boolean success =
                doctorDAO.updateDoctor(
                        id,
                        specialization,
                        phone
                );


        if (success) {

            return "redirect:/doctors";
        }


        return "redirect:/doctors";
    }


    // ---------------------------------------------------------
    // Delete Doctor
    // ---------------------------------------------------------

    @GetMapping("/doctors/delete")
    public String deleteDoctor(
            @RequestParam int id) {


        doctorDAO.deleteDoctor(
                id
        );


        return "redirect:/doctors";
    }


    // =========================================================
    // APPOINTMENTS
    // =========================================================

    // ---------------------------------------------------------
    // Appointment List
    // ---------------------------------------------------------

    @GetMapping("/appointments")
    public String appointments(
            Model model) {


        List<AppointmentView> appointments =
                appointmentDAO
                        .getRecentAppointments();


        model.addAttribute(
                "appointments",
                appointments
        );


        return "appointments";
    }


    // ---------------------------------------------------------
    // Cancel Appointment
    // ---------------------------------------------------------

    @GetMapping("/appointments/cancel")
    public String cancelAppointment(
            @RequestParam int id) {


        appointmentDAO.cancelAppointment(
                id
        );


        return "redirect:/appointments";
    }


    // ---------------------------------------------------------
    // Add Appointment Page
    // ---------------------------------------------------------

    @GetMapping("/appointments/add")
    public String addAppointmentPage(
            Model model) {


        List<PatientView> patients =
                patientDAO.getAllPatients();


        List<DoctorView> doctors =
                doctorDAO.getAllDoctors();


        model.addAttribute(
                "patients",
                patients
        );


        model.addAttribute(
                "doctors",
                doctors
        );


        return "appointment-add";
    }


    // ---------------------------------------------------------
    // Add / Book Appointment
    // ---------------------------------------------------------

    @PostMapping("/appointments/add")
    public String addAppointment(
            @RequestParam int patientId,
            @RequestParam int doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String status,
            Model model) {


        Appointment appointment =
                new Appointment();


        appointment.setPatientId(
                patientId
        );


        appointment.setDoctorId(
                doctorId
        );


        appointment.setAppointmentDate(
                appointmentDate
        );


        appointment.setStatus(
                status
        );


        boolean success =
                appointmentDAO.bookAppointment(
                        appointment
                );


        if (success) {

            return "redirect:/appointments";
        }


        // Agar appointment save nahi hui
        // to dropdowns dobara load karo

        List<PatientView> patients =
                patientDAO.getAllPatients();


        List<DoctorView> doctors =
                doctorDAO.getAllDoctors();


        model.addAttribute(
                "patients",
                patients
        );


        model.addAttribute(
                "doctors",
                doctors
        );


        model.addAttribute(
                "error",
                "Failed to book appointment"
        );


        return "appointment-add";
    }

}