package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.repository.BedRepo;
import com.example.application.backend.service.BedService;
import com.example.application.backend.service.PatientService;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.SpringServlet;
import kotlin.jvm.functions.Function0;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import com.github.mvysny.kaributesting.v10.spring.MockSpringServlet;


import static com.github.mvysny.kaributesting.v10.LocatorJ.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext
public class WardViewTest {

    private static Routes routes;
    @BeforeAll
    public static void discoverRoutes() {
        routes = new Routes().autoDiscoverViews("com.example.application.views.WardView");
    }

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private BedRepo repo;
    private PatientService ps;
    private BedService bs;

    @BeforeEach
    public void setup() {
        final Function0<UI> WardView = UI::new;
        final SpringServlet servlet = new MockSpringServlet(routes, ctx, WardView);
        MockVaadin.setup(WardView, servlet);
//        MockVaadin.setup(routes);
   //     MockVaadin.setup { beanFactory!!.getBean(MainUI::class.java)
        }

    @Test
    public void addPatient() {
        _setValue(_get(TextField.class, spec -> spec.withCaption("Name")), "Nikita");
//      _setValue(_get(NumberField.class, spec -> spec.withCaption("Braden Score")),"Braden Score");
//        _setValue(_get(ComboBox.class, spec -> spec.withCaption("ID")), "Small");
        _click(_get(Button.class, spec -> spec.withCaption("Add New")));

        //final List<TShirtOrder> all = repo.findAll();
        //assertEquals("orders=" + all, 1, all.size());
        //assertEquals("Foo", all.get(0).getName());
    }



}