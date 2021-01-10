package com.example.application.backend.entity;

import org.junit.Assert;
import org.junit.Test;

public class NurseTest {

//Checks that the format of the email is correct
    @Test
    public void test_email_check_funtion(){
        String email1,email2;
        Nurse n =new Nurse();
        email1 = "alice@ic.ac.uk";
        email2="alice";

        Assert.assertTrue(n.correct_email(email1));
        Assert.assertFalse(n.correct_email(email2));
    }

    //Tests if the name formatting function works
    @Test
    public void test_name_formatting(){
        String name,name1;
        Nurse n =new Nurse();
        name="Alice Duhem";
        name1="alIce DuhEm";

        Assert.assertEquals("Alice Duhem",n.FormatName(name));
        Assert.assertEquals("Alice Duhem",n.FormatName(name1));
    }

    //Checks the email isn't taken in if wrong format and put in lowercase
    @Test
    public void test_email_log(){
        String email1,email2,email3;
        Nurse n=new Nurse();
        Nurse n1=new Nurse();
        Nurse n2=new Nurse();
        email1 = "alice@ic.ac.uk";
        email2="alice";
        email3 = "ALICE@ic.ac.uk";

        n.setEmail(email1);
        Assert.assertEquals("alice@ic.ac.uk",n.getEmail());
        n1.setEmail(email2);
        Assert.assertEquals(null,n1.getEmail());
        n2.setEmail(email3);
        Assert.assertEquals("alice@ic.ac.uk",n2.getEmail());
    }


    @Test
    public void nurse_constructor(){
        Nurse n,n2;
        n= new Nurse("alice dUhem","alice@ic.ac.uk");
        n2= new Nurse("Alice","alice.ic.ac.uk");

        Assert.assertEquals("Alice Duhem",n.getName());
        Assert.assertEquals("alice@ic.ac.uk",n.getEmail());
        Assert.assertEquals(null,n2.getEmail());
    }

}
