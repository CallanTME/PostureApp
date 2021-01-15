package com.example.application.backend.entity;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatientTest {
    private Patient patient;
    private Patient patient1;

    @Before
    public void setUp(){
        patient = new Patient();
        patient1 =new Patient ("alice DUHem",12);
    }

    @Test
    public void test_constructor(){
        //Tests the name formatting in the constructor works as desired
        Assert.assertEquals(12, Math.round(patient1.getbScore()));
        Assert.assertEquals("Alice Duhem", patient1.getName());
    }

    @Test
    public void test_setBscore(){
        patient.setbScore(14);
        Assert.assertEquals(14, Math.round(patient.getbScore()));
    }

    @Test
    public void test_setName(){
        //Tests the name formatting works as desired
        patient.setName("olLie BarBaresi");
        Assert.assertEquals("Ollie Barbaresi", patient.getName());
    }

    @Test
    public void test_setId(){
        patient1.setId(1201201222);
        Assert.assertEquals(1201201222, patient1.getId());
    }
}
