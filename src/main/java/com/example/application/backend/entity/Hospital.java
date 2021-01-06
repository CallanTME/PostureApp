package com.example.application.backend.entity;

import com.example.application.backend.FormatString;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hospital implements FormatString {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Override
    public String FormatName(String name) {
        StringBuilder sb = new StringBuilder();
        boolean space = true;

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
        return sb.toString();
    }

    @Override
    public String FormatPostcode(String postcode) {
        StringBuilder sb = new StringBuilder();
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
