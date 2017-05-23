package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by MichaelRiley on 5/21/17.
 */


@Controller
public class HomeController {

    @RequestMapping ("/")
    public ModelAndView helloWorld(){
        return new ModelAndView("welcome", "hello", "hello world");
    }

    @RequestMapping (value = "/register/family", method = RequestMethod.GET)
    public String registerFamily(){
        return "newFamily";
    }

    /**
     * Sends newly registered admin to their dashboard
     * @return admin dashboard
     */
    @RequestMapping (value = "/dashboard/admin/newAccount", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famName") String famName,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model){

        FamiliesEntity family = newFamily(famName);
        UsersEntity user = newAdmin(fName,lName,email,0,password,family.getFamilyid());

        model.addAttribute("family", family);
        model.addAttribute("user", user);

        return "adminDashboard";
    }

    @RequestMapping ("/dashboard/admin/newChild")
    public String registerUser(@RequestParam("id") int famId,
                               Model model){
        model.addAttribute("famId", famId);
        return "newUser";
    }

    @RequestMapping (value = "/dashboard/admin/newChild/done", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famId") int famId,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model){

        UsersEntity user = newAdmin(fName,lName,email,1,password,famId);

        model.addAttribute("user", user);

        return "adminDashboard";
    }

    private FamiliesEntity newFamily(String famName) {
        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        Session adminSession = sessionFactory.openSession();
        Transaction familyTransaction = adminSession.beginTransaction();
        FamiliesEntity newFamily = new FamiliesEntity();

        newFamily.setName(famName);
        adminSession.save(newFamily);
        familyTransaction.commit();

        return newFamily;
    }

    private UsersEntity newAdmin(String fName, String lName, String email, int usergroup, String password, int familyid) {
        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        Session adminSession = sessionFactory.openSession();
        Transaction userTransaction = adminSession.beginTransaction();
        UsersEntity user = new UsersEntity();

        user.setFname(fName);
        user.setLname(lName);
        user.setEmail(email);
        user.setUsergroup(usergroup);
        user.setPassword(password);
        user.setFamilyid(familyid);

        adminSession.save(user);
        userTransaction.commit();

        return user;
    }
}



