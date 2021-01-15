package com.example.application.views;
import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.repository.BedRepo;
import com.example.application.backend.service.BedService;
import com.example.application.backend.service.PatientService;
import com.example.application.views.BedLayout;
import com.example.application.views.WardView;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static com.github.mvysny.kaributesting.v10.LocatorJ.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext

public class WardViewTest {

    private Bed bed;
    private Patient patient;
    private PatientService ps;
    private BedService bs;
    private BedLayout bedLayout;
    private WardView wardview;

    private static Routes routes;

    @BeforeAll
    public static void createRoutes() {
        // initialize routes only once, to avoid view auto-detection before every test and to speed up the tests
        routes = new Routes().autoDiscoverViews("com.example.application.views");
    }

    @BeforeEach
    public void setupVaadin() {
        MockVaadin.setup(routes);
    }

    @Before
    public void setUp(){
        patient =new Patient ("Nikita Narayanan",12);

        BedRepo bedRepo = new BedRepo(){

            private List<Bed> beds = new ArrayList<>();
            @Override
            public List<Bed> findAll() {
                return beds;
            }

            @Override
            public List<Bed> findAll(Sort sort) {
                return beds;
            }

            @Override
            public List<Bed> findAllById(Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends Bed> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Bed> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Bed> iterable) {

            }

            @Override
            public void deleteAllInBatch() {
                beds.clear();
            }

            @Override
            public Bed getOne(Long aLong) {
                return null;
            }

            @Override
            public <S extends Bed> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Bed> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Bed> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Bed> S save(S s) {
                beds.add(s);
                return s;
            }

            @Override
            public Optional<Bed> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {
                beds.forEach((bed1 -> {
                    if(bed1.getId() == aLong)
                    {
                        beds.remove(bed1);
                    }
                }));
            }

            @Override
            public void delete(Bed bed) {
                beds.forEach((bed1 -> {
                    if(bed1.getId() == bed.getId())
                    {
                        beds.remove(bed1);
                    }
                }));

            }

            @Override
            public void deleteAll(Iterable<? extends Bed> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Bed> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Bed> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Bed> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Bed> boolean exists(Example<S> example) {
                return false;
            }
        };
        bs = new BedService(bedRepo);
        //wardview = new WardView(bs, ps);
    }

    @Test
    public void test_Save_Patient(){

        wardview = new WardView(bs, ps);


        wardview.nameIn.setValue(patient.getName());
        wardview.bScoreIn.setValue(patient.getbScore());
        wardview.bedNumIn.setValue(2.0);
        wardview.addButton.click();
        final CountDownLatch waiter = new CountDownLatch(1);
        try {
            waiter.await(2, TimeUnit.SECONDS); // 2s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Optional<Bed> result = bs.getByBedNum(2.0);
        if (result.isEmpty()){
            fail();
            return;
        }
        assertEquals(result.get().getPatient().getName(), patient.getName());
        assertEquals(result.get().getPatient().getbScore(), patient.getbScore());
    }

