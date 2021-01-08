package com.example.application.backend.entity;

import org.junit.Assert;
import org.junit.Test;

public class NurseTest {
    @Test
    public void nurse_constructor(){
        Nurse n;
        n= new Nurse("alice dUhem","alice@ic.ac.uk");

        Assert.assertEquals("Alice Duhem",n.getName());
        Assert.assertEquals("alice@ic.ac.uk",n.getEmail());
    }

}
