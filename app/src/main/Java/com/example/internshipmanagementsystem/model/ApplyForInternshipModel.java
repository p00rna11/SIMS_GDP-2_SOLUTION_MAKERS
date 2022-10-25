package com.example.internshipmanagementsystem.model;

public class ApplyForInternshipModel {
    String FirstName;
    String MiddleName;
    String LastName;
    String UserId;
    String Address;
    String PhoneNumber;
    String CompanyName;
    String CompanyContact;
    String CompanyMail;
    String CompanyAddress;
    String SemesterTerm;
    String Year;
    String Crn;
    String CreditHours;
    String CreditTitle;
    String StartDate;
    String Enddate;
    String Hoursofwork;
    String facultymail;
    String facultyname;
    String random;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public ApplyForInternshipModel(String firstName, String middleName, String lastName, String userId, String address, String phoneNumber, String companyName, String companyContact, String companyMail, String companyAddress, String semesterTerm, String year, String crn, String creditHours, String creditTitle, String startDate, String enddate, String hoursofwork, String facultymail, String facultyname, String random,String key) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        UserId = userId;
        Address = address;
        PhoneNumber = phoneNumber;
        CompanyName = companyName;
        CompanyContact = companyContact;
        CompanyMail = companyMail;
        CompanyAddress = companyAddress;
        SemesterTerm = semesterTerm;
        Year = year;
        Crn = crn;
        CreditHours = creditHours;
        CreditTitle = creditTitle;
        StartDate = startDate;
        Enddate = enddate;
        Hoursofwork = hoursofwork;
        this.facultymail = facultymail;
        this.facultyname = facultyname;
        this.random = random;
        this.key=key;
    }

    public String getFacultymail() {
        return facultymail;
    }

    public void setFacultymail(String facultymail) {
        this.facultymail = facultymail;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public ApplyForInternshipModel() {
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyContact() {
        return CompanyContact;
    }

    public void setCompanyContact(String companyContact) {
        CompanyContact = companyContact;
    }

    public String getCompanyMail() {
        return CompanyMail;
    }

    public void setCompanyMail(String companyMail) {
        CompanyMail = companyMail;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getSemesterTerm() {
        return SemesterTerm;
    }

    public void setSemesterTerm(String semesterTerm) {
        SemesterTerm = semesterTerm;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getCrn() {
        return Crn;
    }

    public void setCrn(String crn) {
        Crn = crn;
    }

    public String getCreditHours() {
        return CreditHours;
    }

    public void setCreditHours(String creditHours) {
        CreditHours = creditHours;
    }

    public String getCreditTitle() {
        return CreditTitle;
    }

    public void setCreditTitle(String creditTitle) {
        CreditTitle = creditTitle;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public String getHoursofwork() {
        return Hoursofwork;
    }

    public void setHoursofwork(String hoursofwork) {
        Hoursofwork = hoursofwork;
    }

    public ApplyForInternshipModel(String firstName, String middleName, String lastName, String userId, String address, String phoneNumber, String companyName, String companyContact, String companyMail, String companyAddress, String semesterTerm, String year, String crn, String creditHours, String creditTitle, String startDate, String enddate, String hoursofwork) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        UserId = userId;
        Address = address;
        PhoneNumber = phoneNumber;
        CompanyName = companyName;
        CompanyContact = companyContact;
        CompanyMail = companyMail;
        CompanyAddress = companyAddress;
        SemesterTerm = semesterTerm;
        Year = year;
        Crn = crn;
        CreditHours = creditHours;
        CreditTitle = creditTitle;
        StartDate = startDate;
        Enddate = enddate;
        Hoursofwork = hoursofwork;
    }
}
