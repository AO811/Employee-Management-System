create database employeemanagement;
use employeemanagement;


create table login(username varchar(20), password varchar(20));
select * from login;
insert into login values('Abhik','123456789');


create table employee(name varchar(40), fname varchar(40), dob varchar(40), salary varchar(40), address varchar(40), phone varchar(40), email varchar(40), education varchar(40), designation varchar(40), addhar varchar(40), empID varchar(40));
select * from employee;
ALTER TABLE employee 
ADD CONSTRAINT empID_unique UNIQUE(empID);


CREATE TABLE payroll (  
    EmployeeID VARCHAR(40),
    BaseSalary DOUBLE,
    PayrollDate date,
    FOREIGN KEY (EmployeeID) REFERENCES employee(empID) 
);
alter table payroll
drop PayrollDate;
select * from payroll;


create table employeelogin(
     EmployeeID VARCHAR(40),
     Employeename VARCHAR(40),
     foreign key (EmployeeID) references employee(empID));


DELIMITER //

CREATE TRIGGER after_employee_insert
AFTER INSERT ON employee
FOR EACH ROW
BEGIN
    INSERT INTO employeelogin (EmployeeID, Employeename)
    VALUES (NEW.empID, NEW.name);
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_employee_delete
AFTER DELETE ON employee
FOR EACH ROW
BEGIN
    DELETE FROM employeelogin WHERE EmployeeID = OLD.empID;
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_employee_update
AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
    UPDATE employeelogin 
    SET Employeename = NEW.name 
    WHERE EmployeeID = OLD.empID;
END;
//

DELIMITER ;


create table `leave`(leave_id INT AUTO_INCREMENT PRIMARY KEY,employee_id VARCHAR(10), start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (employee_id) REFERENCES employee(empID)
);
alter table `leave`
modify column end_date varchar(40);
select * from `leave`;


CREATE TABLE attendance (
    employee_id VARCHAR(10),
    date DATE,
    status ENUM('Present', 'Absent') DEFAULT 'Absent',
    month VARCHAR(7),
    PRIMARY KEY (employee_id, date),
    FOREIGN KEY (employee_id) REFERENCES employee(empID)
);
select * from attendance; 


select * from employeelogin;
select * from employee;
select * from login;
select * from payroll;
select * from employee where empID=557104;