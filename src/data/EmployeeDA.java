package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Accesses the Employee database and
 *
 * @author me me me
 */
public abstract class EmployeeDA {

    //declare arraylist of type EmployeePD object
    private static ArrayList<EmployeePD> arListEmployee = new ArrayList<>();

    //declare all classes 
    private static Connection con;
    private static PreparedStatement ps;
    private static Statement st;
    private static ResultSet rs;

    /**
     * initialize opens connection to database
     *
     * @throws data.DataStorageException
     */
    public static void initializeConnection() throws DataStorageException {

        final String USERNAME = "root";
        final String PASSWORD = "";
        final String URL = "jdbc:mysql://localhost/Employee_db";
        final String DRIVER = "com.mysql.jdbc.Driver";

        try {
            //register driver
            Class.forName(DRIVER);
            //create connection
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            //driver exception
            throw new DataStorageException("Database driver not found\n" + e.getMessage());
        } catch (SQLException e) {
            //connection exception
            throw new DataStorageException("Connection Error " + e.getMessage());
        }
    }
    
      
      
    
    
    
    public static void addEmployee(EmployeePD empObj) throws DuplicateException {

        String qry = "INSERT INTO tblEmployee VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(qry);
            ps.setString(2, empObj.getName());
            ps.setString(1, empObj.getStaffNumber());
            ps.setDouble(3, empObj.getSalary());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DuplicateException(empObj.getName() + " Not added\n"
                    + e.getMessage());
        }
    }
    
    
    
    
    
    public static ArrayList<EmployeePD> getAll() throws NotFoundException {
        arListEmployee.clear();
        String query = "SELECT * FROM tblEmployee";
        try {
            //send ur query to the database using ur preparedstatemnt and ur connection object
            ps = con.prepareStatement(query);
            //execute and store the returned query result in resultset object
            rs = ps.executeQuery();
            while (rs.next()) {
                arListEmployee.add(new EmployeePD(rs.getString(2), rs.getString(1), rs.getDouble(3)));
            }
        } catch (SQLException e) {
            throw new NotFoundException("No Data retrived.\n" + e.getMessage());
        }
        return arListEmployee;
    }
  
    
    
    
    
    public static void terminate() throws DataStorageException {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new DataStorageException(e.getMessage());
        }
    }

    public static void IncreaseSalaries(double perc) throws NotFoundException {

        String qry = "UPDATE tblEmployee  SET Annual_Salary = Annual_Salary + (Annual_Salary * ('" + perc / 100 + "'))";
        try {
            st = con.createStatement();
            st.executeUpdate(qry);

        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    
    
   
    public static void removeEmployee(String empNumber) throws NotFoundException {
        String qry = "DELETE FROM tblEmployee  WHERE Employee_Number = ?";
        try {
            ps = con.prepareStatement(qry);
            ps.setString(1, empNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

         
    
    
    public static ArrayList<String> getEmpNumbers() throws NotFoundException {

        ArrayList<String> arListEmpNo = new ArrayList<>();

        String query = "SELECT Employee_Number FROM tblEmployee";
        try {
            //send ur query to the database using ur preparedstatemnt and ur connection object
            ps = con.prepareStatement(query);
            //execute and store the returned query result in resultset object
            rs = ps.executeQuery();
            while (rs.next()) {
                arListEmpNo.add(rs.getString("Employee_Number"));
            }
        } catch (SQLException e) {
            throw new NotFoundException("No Data retrived.\n" + e.getMessage());
        }
        return arListEmpNo;
    }
    
    
    
    
    
    
    
    
    
    
    
    

    public static EmployeePD getEmployeeObj(String empNo) throws NotFoundException {
        EmployeePD objEmp = null;
        try {
            ps = con.prepareStatement("SELECT * FROM tblEmployee WHERE Employee_Number =?");
            ps.setString(1, empNo);
            rs = ps.executeQuery();
            rs.next();
            objEmp = new EmployeePD(rs.getString(2), rs.getString(1), rs.getDouble(3));

        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }
        return objEmp;
    }
    
    
    
    
    
    
    

    public static double calcAverageSalary() throws NotFoundException {
        double total, avrg;
        int count = 0;
        total = avrg = 0;

        String qry = "SELECT Annual_Salary FROM tblEmployee ";
        try {
            st = con.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                count++;
                total += rs.getDouble("Annual_Salary");
            }
            avrg = total / count;
        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }

        return avrg;
    }
}
