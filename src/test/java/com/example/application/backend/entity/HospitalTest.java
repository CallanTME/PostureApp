package com.example.application.backend.entity;

import com.example.application.backend.entity.Hospital;
import org.junit.Assert;
import org.junit.Test;

public class HospitalTest {
    @Test
    //This mainly tests the formatting methods are taken into account
    public void test_hospital_constructor() {
        Hospital h, h1;
        h = new Hospital("St mary's hospital", "W21nY");
        h1 = new Hospital("Royal lOndon Hospital", "e11 1Fr");

        Assert.assertEquals("St Mary's Hospital",h.getHosp_name() );
        Assert.assertEquals("W2 1NY",h.getZipcode());
        Assert.assertEquals("Royal London Hospital",h1.getHosp_name());
        Assert.assertEquals("E11 1FR",h1.getZipcode());
    }
}