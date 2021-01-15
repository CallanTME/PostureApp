package com.example.application.backend.entity;

import com.example.application.backend.Pressure;
import junit.framework.Assert;
import org.junit.*;

import java.sql.*;

public class BedTest {
    private Bed bed;
    private Patient patient;

    @Before
    public void setUpData(){ //Creates a bed used in most of the test
        bed =new Bed(3);
        patient = new Patient("Ollie",13);
    }


    @Test
    //Asserts the constructor works correctly
    public void test_Bed_constructor() {
        Bed bed1 =new Bed(1);
        Bed bed2 = new Bed(2,false);

        Assert.assertEquals(1, Math.round(bed1.getBedNum()));
        Assert.assertEquals(true, bed1.isEmpty());
        Assert.assertEquals(1, bed1.getId());
        Assert.assertEquals(0.0, bed1.getTimeInPos());

        Assert.assertEquals(2, Math.round(bed2.getBedNum()));
        Assert.assertEquals(false, bed2.isEmpty());
        Assert.assertEquals(2, bed2.getId());
        Assert.assertEquals(0.0, bed2.getTimeInPos());
    }

    @Test
    public void test_setId(){
        bed.setId(100100);
        Assert.assertEquals(100100, bed.getId());
    }

    @Test
    public void test_setStatus(){
        //Check that it is empty by default
        Assert.assertEquals(true, bed.isEmpty());

        bed.setFull();
        Assert.assertEquals(false, bed.isEmpty());
        bed.setEmpty();
        Assert.assertEquals(true, bed.isEmpty());
    }

    @Test
    public void test_getTime_in_Position(){
        bed.setTimeInPos(10.3);
        Assert.assertEquals(10.3, bed.getTimeInPos());
    }

    @Test
    public void test_getPatient(){
        bed.setPatient(patient);
        Assert.assertEquals(13, Math.round(bed.getPatient().getbScore()));
        Assert.assertEquals("Ollie", bed.getPatient().getName());
    }


}


