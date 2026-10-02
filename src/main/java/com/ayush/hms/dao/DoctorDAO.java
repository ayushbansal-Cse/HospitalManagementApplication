package com.ayush.hms.dao;

import com.ayush.hms.database.DBConnection;
import com.ayush.hms.model.Doctor;
import com.ayush.hms.model.DoctorView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // =========================================================
    // ADD DOCTOR
    // =========================================================

    public boolean addDoctor(Doctor doctor) {

        String sql =
                "INSERT INTO doctor(name, specialization, experience, phone) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    doctor.getName()
            );

            ps.setString(
                    2,
                    doctor.getSpecialization()
            );

            ps.setString(
                    3,
                    doctor.getExperience()
            );

            ps.setString(
                    4,
                    doctor.getPhone()
            );

            int rows =
                    ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return false;
    }


    // =========================================================
    // VIEW DOCTORS - CONSOLE
    // =========================================================

    public void viewDoctors() {

        String sql =
                "SELECT * FROM doctor";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\n=========================================================="
            );

            System.out.printf(
                    "%-5s %-20s %-20s %-12s %-15s%n",
                    "ID",
                    "Name",
                    "Specialization",
                    "Experience",
                    "Phone"
            );

            System.out.println(
                    "=========================================================="
            );

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-20s %-12s %-15s%n",

                        rs.getInt("id"),

                        rs.getString("name"),

                        rs.getString("specialization"),

                        rs.getString("experience"),

                        rs.getString("phone")
                );
            }

            System.out.println(
                    "=========================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // SEARCH DOCTOR - CONSOLE
    // =========================================================

    public void searchDoctor(String name) {

        String sql =
                "SELECT * FROM doctor WHERE name LIKE ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    "%" + name + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\n=========================================================="
            );

            System.out.printf(
                    "%-5s %-20s %-20s %-15s%n",
                    "ID",
                    "Name",
                    "Specialization",
                    "Phone"
            );

            System.out.println(
                    "=========================================================="
            );

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-20s %-15s%n",

                        rs.getInt("id"),

                        rs.getString("name"),

                        rs.getString("specialization"),

                        rs.getString("phone")
                );
            }

            System.out.println(
                    "=========================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // SEARCH DOCTORS - WEB
    // =========================================================

    public List<DoctorView> searchDoctors(String name) {

        List<DoctorView> doctors =
                new ArrayList<>();

        String sql =
                "SELECT * FROM doctor " +
                        "WHERE name LIKE ? " +
                        "ORDER BY id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    "%" + name + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                DoctorView doctor =
                        new DoctorView(

                                rs.getInt("id"),

                                rs.getString("name"),

                                rs.getString(
                                        "specialization"
                                ),

                                rs.getString(
                                        "experience"
                                ),

                                rs.getString("phone")
                        );

                doctors.add(doctor);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return doctors;
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    public boolean updateDoctor(
            int id,
            String specialization,
            String phone) {

        String sql =
                "UPDATE doctor " +
                        "SET specialization=?, phone=? " +
                        "WHERE id=?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    specialization
            );

            ps.setString(
                    2,
                    phone
            );

            ps.setInt(
                    3,
                    id
            );

            int rows =
                    ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return false;
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    public boolean deleteDoctor(int id) {

        String deleteAppointments =
                "DELETE FROM appointment WHERE doctor_id=?";

        String deleteDoctor =
                "DELETE FROM doctor WHERE id=?";

        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();

            // Start transaction
            con.setAutoCommit(false);


            // -------------------------------------------------
            // First delete doctor's appointments
            // -------------------------------------------------

            PreparedStatement psAppointments =
                    con.prepareStatement(
                            deleteAppointments
                    );

            psAppointments.setInt(
                    1,
                    id
            );

            psAppointments.executeUpdate();


            // -------------------------------------------------
            // Then delete doctor
            // -------------------------------------------------

            PreparedStatement psDoctor =
                    con.prepareStatement(
                            deleteDoctor
                    );

            psDoctor.setInt(
                    1,
                    id
            );

            int rows =
                    psDoctor.executeUpdate();


            // Commit transaction
            con.commit();


            return rows > 0;

        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception rollbackError) {

                System.out.println(
                        "Rollback Error : "
                                + rollbackError.getMessage()
                );
            }

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return false;
    }


    // =========================================================
    // GET DOCTOR COUNT
    // =========================================================

    public int getDoctorCount() {

        String sql =
                "SELECT COUNT(*) FROM doctor";

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
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return 0;
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    public List<DoctorView> getAllDoctors() {

        List<DoctorView> doctors =
                new ArrayList<>();

        String sql =
                "SELECT * FROM doctor " +
                        "ORDER BY id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                DoctorView doctor =
                        new DoctorView(

                                rs.getInt("id"),

                                rs.getString("name"),

                                rs.getString(
                                        "specialization"
                                ),

                                rs.getString(
                                        "experience"
                                ),

                                rs.getString("phone")
                        );

                doctors.add(doctor);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );
        }

        return doctors;
    }
}