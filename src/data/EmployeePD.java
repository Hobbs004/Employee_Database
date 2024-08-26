package data;

import java.util.ArrayList;

/**
 * Store the staff number, name and salary of an employee
 *
 * @author me me me
 */
public class EmployeePD {

    private String name, staffNumber;
    private double salary;

    /**
     * Create a default employee object with: 20200 as the staff number, "None"
     * as the name and 10000 as the salary
     */
    public EmployeePD() {
        this("None", "202001", 10000.00);
    }

    /**
     * Create a new employee with the given staff number, name and salary
     *
     * @param name The new name
     * @param salary The new salary
     */
    public EmployeePD(String name, String staffNo, double salary) {
        setName(name);
        setStaffNumber(staffNo);
        setSalary(salary);
    }

    /**
     * Change the name of the object
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    //No javadoc comment for this method - it is not a public method
    //private methods are for the own class' use. Salaries will be changed
    //using the increaseSalary method
    private void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Return the name of the employee
     *
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Return the salary of the employee
     *
     * @return the salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    /**
     * Increase the salary by the given percentage
     *
     * @param percentage The percentage to increase the salary with
     */
    public void increaseSalary(double percentage) {
        this.salary = this.salary + (this.salary * percentage / 100);
    }

    @Override
    public String toString() {
        return String.format("%-6s\t%-15s\tR %-7.2f\n", staffNumber, name, salary);
    }
    
    
    
    
    
    

    //calling the DA method in the PD class
    public void openConnection() throws DataStorageException {
        EmployeeDA.initializeConnection();
    }

    public void addEmployee(EmployeePD empObj) throws DuplicateException {
        EmployeeDA.addEmployee(empObj);
    }

    public ArrayList<EmployeePD> getAll() throws NotFoundException {
        return EmployeeDA.getAll();
    }

    public void IncreaseSalaries(double perc) throws NotFoundException {
        EmployeeDA.IncreaseSalaries(perc);
    }

    public void deleteEmployee(String empNo) throws NotFoundException {
        EmployeeDA.removeEmployee(empNo);
    }

    
       
    
    
    
    
    public ArrayList<String> getEmpNumbers() throws NotFoundException {
        return EmployeeDA.getEmpNumbers();
    }

    public EmployeePD getEmployeeObj(String empNo) throws NotFoundException {
        return EmployeeDA.getEmployeeObj(empNo);
    }

    public double calcAverageSalary() throws NotFoundException {
        return EmployeeDA.calcAverageSalary();
    }
}
