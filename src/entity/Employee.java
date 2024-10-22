package entity;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private int EmployeeID;
    private String FirstName;
    private String LastName;
    private LocalDate DateOfBirth;
    private String Gender;
    private String Email;
    private String PhoneNumber;
    private String Address;
    private String Position;
    private LocalDate JoiningDate;
    private LocalDate TerminationDate;

    public Employee() {
    }

    public Employee(int employeeID, String firstName, String lastName, LocalDate dateOfBirth, String gender,
                    String email, String phoneNumber, String address, String position,
                    LocalDate joiningDate, LocalDate terminationDate) {
        EmployeeID = employeeID;
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        Email = email;
        PhoneNumber = phoneNumber;
        Address = address;
        Position = position;
        JoiningDate = joiningDate;
        TerminationDate = terminationDate;
    }

    // Getters and Setters
    public int getEmployeeID() { return EmployeeID; }
    public void setEmployeeID(int employeeID) { EmployeeID = employeeID; }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }

    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }

    public LocalDate getDateOfBirth() { return DateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { DateOfBirth = dateOfBirth; }

    public String getGender() { return Gender; }
    public void setGender(String gender) { Gender = gender; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getPhoneNumber() { return PhoneNumber; }
    public void setPhoneNumber(String phoneNumber) { PhoneNumber = phoneNumber; }

    public String getAddress() { return Address; }
    public void setAddress(String address) { Address = address; }

    public String getPosition() { return Position; }
    public void setPosition(String position) { Position = position; }

    public LocalDate getJoiningDate() { return JoiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { JoiningDate = joiningDate; }

    public LocalDate getTerminationDate() { return TerminationDate; }
    public void setTerminationDate(LocalDate terminationDate) { TerminationDate = terminationDate; }

    // Method: CalculateAge()
    public int CalculateAge() {
        if (DateOfBirth == null) {
            return 0;
        }
        return Period.between(DateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "EmployeeID: " + EmployeeID +
                ", Name: " + FirstName + " " + LastName +
                ", Position: " + Position;
    }
}
