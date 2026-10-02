package com.ayush.hms.dao;

import com.ayush.hms.database.DBConnection;
import com.ayush.hms.model.Appointment;
import com.ayush.hms.model.AppointmentView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    public boolean bookAppointment(Appointment appointment) {

        String sql =
                "INSERT INTO appointment(patient_id, doctor_id, appointment_date, status) " +
                        "VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    appointment.getPatientId()
            );

            ps.setInt(
                    2,
                    appointment.getDoctorId()
            );

            ps.setString(
                    3,
                    appointment.getAppointmentDate()
            );

            ps.setString(
                    4,
                    appointment.getStatus()
            );

            int rows =
                    ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " +
                            e.getMessage()
            );
        }

        return false;
    }


    // =========================================================
    // VIEW APPOINTMENTS - CONSOLE
    // =========================================================

    public void viewAppointments() {

        String sql = """
            SELECT a.id,
                   p.name AS patient_name,
                   d.name AS doctor_name,
                   a.appointment_date,
                   a.status
            FROM appointment a
            JOIN patient p ON a.patient_id = p.id
            JOIN doctor d ON a.doctor_id = d.id
            """;

        try {
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\n=========================================================================="
            );

            System.out.printf(
                    "%-5s %-20s %-20s %-15s %-15s%n",
                    "ID",
                    "Patient",
                    "Doctor",
                    "Date",
                    "Status"
            );

            System.out.println(
                    "=========================================================================="
            );

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-20s %-15s %-15s%n",
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("status")
                );
            }

            System.out.println(
                    "=========================================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " +
                            e.getMessage()
            );
        }
    }


    // =========================================================
    // CANCEL APPOINTMENT
    // =========================================================

    public boolean cancelAppointment(int id) {

        String sql =
                "UPDATE appointment " +
                        "SET status = 'Cancelled' " +
                        "WHERE id = ?";

        try {
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    id
            );

            int rows =
                    ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " +
                            e.getMessage()
            );
        }

        return false;
    }


    // =========================================================
    // APPOINTMENT COUNT
    // =========================================================

    public int getAppointmentCount() {

        String sql =
                "SELECT COUNT(*) FROM appointment";

        try {
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " +
                            e.getMessage()
            );
        }

        return 0;
    }


    // =========================================================
    // RECENT APPOINTMENTS - WEB
    // =========================================================

    public List<AppointmentView> getRecentAppointments() {

        List<AppointmentView> appointments =
                new ArrayList<>();

        String sql = """
            SELECT a.id,
                   p.name AS patient_name,
                   d.name AS doctor_name,
                   a.appointment_date,
                   a.status
            FROM appointment a
            JOIN patient p ON a.patient_id = p.id
            JOIN doctor d ON a.doctor_id = d.id
            ORDER BY a.appointment_date DESC, a.id DESC
            LIMIT 5
            """;

        try {
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                AppointmentView appointment =
                        new AppointmentView(
                                rs.getInt("id"),
                                rs.getString("patient_name"),
                                rs.getString("doctor_name"),
                                rs.getString("appointment_date"),
                                rs.getString("status")
                        );

                appointments.add(
                        appointment
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " +
                            e.getMessage()
            );
        }

        return appointments;
    }
}