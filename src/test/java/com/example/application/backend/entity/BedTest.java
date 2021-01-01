package com.example.application.backend.entity;

import junit.framework.Assert;
import org.junit.*;

import java.sql.*;

public class BedTest {
    private Bed bed;
    private Patient patient;

    //need to use DBTest
    /*@BeforeClass
        public static void setUp(){
            Statement stmt = null;
            Connection c = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "06AL12du");
            } catch (Exception p) {
                p.printStackTrace();
                System.err.println(p.getClass().getName() + ": " + p.getMessage());
                System.exit(0);
            }
            try {
                stmt = c.createStatement();

                String sql1 = ("delete from public.info;\n"+
                                "insert into Info ([left],[right],[under],[bed_num]) (1.0,1.1,1.2,1)\t");
                stmt.executeUpdate(sql1);


            } catch (Exception f) {
                f.printStackTrace();
                System.err.println(f.getClass().getName() + ": " + f.getMessage());
                System.exit(0);
            }

            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
    @Before
    public void setUpData(){
        bed =new Bed(3);
        patient = new Patient("Ollie",13);
    }


    @Test
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


    /*@Test
    public void test_GetPressureData(){
        Bed bed1 =new Bed(1);
        bed1.getPressureData();
        Assert.assertEquals(1.0,bed1.getCurrentPressure().getpLeft());
        Assert.assertEquals(1.1,bed1.getCurrentPressure().getpRight());
        Assert.assertEquals(1.2,bed1.getCurrentPressure().getpUnder());

    }*/

    /*@AfterClass
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/groupProject", "postgres", "06AL12du");
        } catch (Exception p) {
            p.printStackTrace();
            System.err.println(p.getClass().getName() + ": " + p.getMessage());
            System.exit(0);
        }
        try {
            stmt = c.createStatement();
            String sql = "DROP TABLE if exists Info\t);";
            stmt.executeUpdate(sql);

        }catch (Exception f) {
            f.printStackTrace();
            System.err.println(f.getClass().getName() + ": " + f.getMessage());
            System.exit(0);
        }

        try {
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/



    }


