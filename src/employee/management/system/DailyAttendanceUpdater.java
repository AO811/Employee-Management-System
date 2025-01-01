package employee.management.system;

import java.sql.*;
import java.time.LocalDate;

public class DailyAttendanceUpdater {
    public static void main(String[] args) {
        markAbsentForMissingEntries();
    }

    public static void markAbsentForMissingEntries() {
        LocalDate currentDate = LocalDate.now();
        String month = currentDate.getMonthValue() + "-" + currentDate.getYear();

        try {
            conn c = new conn();
            
            
            String query = "INSERT INTO attendance (employee_id, date, status, month) " +
                           "SELECT empID, ?, 'Absent', ? FROM employee " +
                           "WHERE empID NOT IN (SELECT employee_id FROM attendance WHERE date = ?)";
            PreparedStatement pstmt = c.getConnection().prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(currentDate));
            pstmt.setString(2, month);                   
            pstmt.setDate(3, Date.valueOf(currentDate)); 

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Marked " + rowsUpdated + " employees as Absent for " + currentDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
