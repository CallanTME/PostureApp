package com.example.application.backend.entity;

import com.example.application.backend.FormatString;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
Class that stores the hospital / care homes using the system
 */

@Entity
public class Hospital implements FormatString {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //Id generated automatically as a sequence when pushed into the database
    private long hosp_id;
    private String hosp_name;
    private String zipcode;


    public Hospital(String name, String zipcode) {
        this.hosp_name = FormatName(name);
        this.zipcode = FormatPostcode(zipcode);
    }

    public Hospital(){

    }

    public String getZipcode() {
        return zipcode;
    }

    public String getHosp_name() {
        return hosp_name;
    }

    public long getHosp_id() {
        return hosp_id;
    }

    /*
    Calls the interface FormatString
    Formats the name entry before pushing the hospital object in the database to keep clean data
    All the letters are lower case except the first letter of each word
     */
    @Override
    public String FormatName(String name) {
        //Create a stringbuilder to hold each letter
        StringBuilder sb = new StringBuilder();
        //Returns true if the character is a space character
        boolean space = true;

        //For each letter in the name, append it to the stringbuilder;
        //Upper case if previous character is a space, lowercase otherwise
        for (char c : name.toCharArray()){
            if(Character.isLetter(c)){
                if(space){
                    c= Character.toTitleCase(c);
                }
                else{
                    c= Character.toLowerCase(c);
                }
                space=false;
            }
            else if (Character.isSpaceChar(c)){
                space=true;
            }
            sb.append(c);
        }
        //Converts the stringbuilder to string
        return sb.toString();
    }

    /*
    Calls the interface FormatString
    Formats the postcode entry before pushing the hospital object in the database to keep clean data
    All the postcodes in the UK have 3 characters after the space
    All the letters are upper case, it adds a space before the 3 last character of the postcode
     */
    @Override
    public String FormatPostcode(String postcode) {
        StringBuilder sb = new StringBuilder();
        //puts the postcode string in a char array
        //Then appends the letter or digit in the stringbuilder but not space
        char[] char_postcode = postcode.toCharArray();
        for (int i = char_postcode.length - 1; i >= 0; i--) {
            if (Character.isLetter(char_postcode[i])) {
                char_postcode[i] = Character.toTitleCase(char_postcode[i]);
                sb.append(char_postcode[i]);
            } else if (Character.isDigit(char_postcode[i])) {
                sb.append(char_postcode[i]);
            }
        }

        //insert a space after 3 chars from the back
        sb.insert(3, ' ');
        int lpostcode = sb.length();

        //Reverse the string
        char[] char_postcode2 = new char[lpostcode];
        for (int i = 0; i < lpostcode; i++) {
            char_postcode2[lpostcode - 1-i] = sb.charAt(i);
        }
        return String.valueOf(char_postcode2);
    }
}
