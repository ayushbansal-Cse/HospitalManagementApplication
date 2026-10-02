package com.ayush.hms.dao;

import com.ayush.hms.database.DBConnection;
import com.ayush.hms.model.Patient;
import com.ayush.hms.model.PatientView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // ================= ADD PATIENT =================

    public boolean addPatient(Patient patient) {

        String sql = "INSERT INTO patient(name, age, gender, phone, address) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, patient.getName());
            ps.setInt(2, patient.getAge());
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getPhone());
            ps.setString(5, patient.getAddress());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println("Database Error : " + e.getMessage());

        }

        return false;
    }


    // ================= VIEW PATIENTS =================

    public void viewPatients() {

        String sql = "SELECT * FROM patient";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n==============================================================");

            System.out.printf(
                    "%-5s %-15s %-5s %-10s %-15s %-20s%n",
                    "ID",
                    "Name",
                    "Age",
                    "Gender",
                    "Phone",
                    "Address"
            );

            System.out.println("==============================================================");

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-15s %-5d %-10s %-15s %-20s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address")
                );

            }

            System.out.println("==============================================================");

        } catch (Exception e) {

            System.out.println("Database Error : " + e.getMessage());

        }
    }


    // ================= UPDATE PATIENT =================

    public boolean updatePatient(
            int id,
            String phone,
            String address
    ) {

        String sql =
                "UPDATE patient SET phone=?, address=? WHERE id=?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " + e.getMessage()
            );

        }

        return false;
    }


    // ================= DELETE PATIENT =================

    public boolean deletePatient(int id) {

        String deleteAppointments =
                "DELETE FROM appointment WHERE patient_id=?";

        String deletePatient =
                "DELETE FROM patient WHERE id=?";

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // Transaction start
            con.setAutoCommit(false);


            // First delete appointments
            PreparedStatement psAppointments =
                    con.prepareStatement(deleteAppointments);

            psAppointments.setInt(1, id);

            psAppointments.executeUpdate();


            // Then delete patient
            PreparedStatement psPatient =
                    con.prepareStatement(deletePatient);

            psPatient.setInt(1, id);

            int rows = psPatient.executeUpdate();


            // Commit
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


    // ================= CONSOLE SEARCH =================

    public void searchPatient(String name) {

        String sql =
                "SELECT * FROM patient WHERE name LIKE ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            System.out.println(
                    "\n=============================================================="
            );

            System.out.printf(
                    "%-5s %-15s %-5s %-10s %-15s %-20s%n",
                    "ID",
                    "Name",
                    "Age",
                    "Gender",
                    "Phone",
                    "Address"
            );

            System.out.println(
                    "=============================================================="
            );

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-15s %-5d %-10s %-15s %-20s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address")
                );

            }

            System.out.println(
                    "=============================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Database Error : " + e.getMessage()
            );

        }
    }


    // ================= WEB SEARCH =================

    public List<PatientView> searchPatients(String name) {

        List<PatientView> patients =
                new ArrayList<>();

        String sql =
                "SELECT * FROM patient " +
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

                PatientView patient =
                        new PatientView(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("phone"),
                                rs.getString("address")
                        );

                patients.add(patient);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );

        }

        return patients;
    }


    // ================= GET PATIENT COUNT =================

    public int getPatientCount() {

        String sql =
                "SELECT COUNT(*) FROM patient";

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


    // ================= GET ALL PATIENTS =================

    public List<PatientView> getAllPatients() {

        List<PatientView> patients =
                new ArrayList<>();

        String sql =
                "SELECT * FROM patient ORDER BY id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                PatientView patient =
                        new PatientView(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("phone"),
                                rs.getString("address")
                        );

                patients.add(patient);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error : "
                            + e.getMessage()
            );

        }

        return patients;
    }
}