package models;

import java.time.LocalDate;

public class clsPerson {
    private String _FirstName;
    private String _LastName;
    private LocalDate _BirthDate;
    private String _Gender;
    

    public clsPerson(String firstName, String lastName, LocalDate BirthDate,String Gender) {
        this._FirstName = firstName;
        this._LastName = lastName;
        this._BirthDate =BirthDate;
        this._Gender = Gender;
       
    }

    // ======Getter&Setter
    public void set_FirstName(String firstName) {
        this._FirstName = firstName;
    }

    public String get_FirstName() {
        return _FirstName;
    }

  
    public void set_LastName(String lastName) {
        this._LastName = lastName;
    }

    public String get_LastName() {
        return _LastName;
    }

   
   public void set_BirthDate(LocalDate _BirthDate) {
        this._BirthDate = _BirthDate;
    }

    public LocalDate get_BirthDate() {
        return _BirthDate;
    }

    public void set_Gender(String _Gender) {
        this._Gender = _Gender;
    }


    public String get_Gender() {
        return _Gender;
    }


    // FullName method
    public String getFullName() {
        return _FirstName + " " + _LastName;
    }


}
