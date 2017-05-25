package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

//import sun.jvm.hotspot.code.Location;

/**
 * Created by MichaelRiley on 5/21/17.
 */


@Controller
public class HomeController {
    private String errorMsg = "";
    private String loggedInMenu =
            "<ul>\n" +
                    "<li><a href=\"/\">Home</a></li>" +
                    "<li><a href=\"/dashboard\">Dashboard</a></li>" +
                    "<li><a href=\"/action=logout\">Logout</a></li>" +
                    "</ul>\n";
    private String loggedOutMenu=
            "<ul>\n" +
                    "<li><a href=\"/action=login\">Login</a></li>" +
                    "<li><a href=\"/action=register/user\">Join A Family</a></li>" +
                    "<li><a href=\"/action=register/family\">Create A Family</a></li>" +
                    "</ul>\n";

    private static final String ApiUrl = "https://api.hippoapi.com/v3/more/json";
    /*
     * Query string for request
     * %1$s = ApiUrl
     * %2$s = API Key
     * %3$s = Email address to query
     */
    private static final String QueryFormatString = "%1$s/%2$s/%3$s";

    private static final String YourAPIKey = "D4DABD4A"; //Your API Key



    @RequestMapping(value = "getemail2", method = RequestMethod.GET, produces="application/text")
    public @ResponseBody String getEmail2(@RequestParam("email") String email) {
        /**
         * The main program entry point
         * @param args the command line arguments
         * @throws IOException If the server does not return a success response
         */

//        System.out.println("Input email address to verify");
//        // Create a scanner to read in the requested email address
//        Scanner in = new Scanner(System.in);
//        String readLine = in.next();
        String result = null;
        System.out.println("getemail2");
        try {
            // Format the request url to the correct structure for the request
            URL requestUrl = new URL(String.format(QueryFormatString, ApiUrl, YourAPIKey, email));

            // Open a connection to the website
            HttpURLConnection myRequest = (HttpURLConnection) requestUrl.openConnection();

            // Set the type to HTTP GET
            myRequest.setRequestMethod("GET");

            // Create a new buffered reader to read the response back from the server
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(myRequest.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            //Read in the response line from the server
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            // Close the reader
            reader.close();

            JSONObject json = new JSONObject(response.toString());

            result = json.getJSONObject("emailVerification").getJSONObject("mailboxVerification").get("result").toString();

//            addAttribute("jsonString", result.toString());

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/")
    public String helloWorld(@CookieValue(value = "userId", defaultValue = "null") String userId,
                             Model model) {
        if (userId.equals("null")) {
            model.addAttribute("navbar", loggedOutMenu);
        } else {
            model.addAttribute("navbar", loggedInMenu);
        }

        return "welcome";
    }

    @RequestMapping(value = "/action=logout")
    public String logOut(Model model,
                         HttpServletResponse response) {
        model.addAttribute("navbar", loggedOutMenu);

        Cookie userId = new Cookie("userId", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userId);

        return "redirect:/";
    }

    @RequestMapping(value = "/action=register/family", method = RequestMethod.GET)
    public String registerFamily(Model model) {
        model.addAttribute("navbar", loggedOutMenu);

        model.addAttribute("err", errorMsg);
        return "newFamily";
    }

    @RequestMapping(value = "/action=register/user", method = RequestMethod.GET)
    public String registerUser(Model model,
                               HttpServletResponse response) {

        model.addAttribute("navbar", loggedOutMenu);
        Cookie userId = new Cookie("userId", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userId);

        model.addAttribute("err", errorMsg);
        return "newUser";
    }

    @RequestMapping(value = "/dashboard/newuser", method = RequestMethod.POST)
    public String newChild(@RequestParam("famId") int famId,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model) {

        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        Session adminSession = sessionFactory.openSession();
        Transaction myTransaction = adminSession.beginTransaction();

        Criteria familyCriteria = adminSession.createCriteria(FamiliesEntity.class);
        Criteria usersCriteria = adminSession.createCriteria(UsersEntity.class);

        try {
            FamiliesEntity existingFamily = (FamiliesEntity) familyCriteria.add(Restrictions.eq("familyid", famId))
                    .uniqueResult();
            int doesThisExist = existingFamily.getFamilyid();

        } catch (NullPointerException e) {
            errorMsg = "This family ID does not exist";
            return "redirect:/action=register/user";
        }

        try {
            UsersEntity newUser = (UsersEntity) usersCriteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();
            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/user";
        } catch (NullPointerException e) {
            UsersEntity user = newUser(fName, lName, email, 1, password, famId);
            model.addAttribute("user", user);
            return "redirect:/action=login";
        }
    }


    @RequestMapping(value = "/action=login", method = RequestMethod.GET)
    public String logIn(Model model) {
        model.addAttribute("navbar", loggedOutMenu);
        model.addAttribute("err", errorMsg);
        return "login";
    }


    @RequestMapping("/cdash")
    public ModelAndView childDashboard(@CookieValue(value = "userId", defaultValue = "null") String userId,
                                       Model model) {
        return new ModelAndView("childDashboard");
    }

    @RequestMapping("/dashboard")
    public String dashboardPage(@CookieValue(value = "userId", defaultValue = "null") String userId,
                                Model model) {
        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        Session adminSession = sessionFactory.openSession();
        Transaction myTransaction = adminSession.beginTransaction();

        Criteria criteria = adminSession.createCriteria(UsersEntity.class);
        try {
            UsersEntity loggedInUser = (UsersEntity) criteria.add(Restrictions.eq("userid", Integer.parseInt(userId)))
                    .uniqueResult();
            model.addAttribute("navbar", loggedInMenu);
            if (loggedInUser.getUsergroup() == 0) {
                return "adminDashboard";
            } else if (loggedInUser.getUsergroup() == 1) {
                return "childDashboard";
            } else {
                return "redirect:/";
            }
        } catch (NumberFormatException e) {
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/dashboardentry", method = RequestMethod.POST)
    public String loggedIn(@RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password,
                           HttpServletResponse response,
                           Model model) {

        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        Session adminSession = sessionFactory.openSession();
        Transaction myTransaction = adminSession.beginTransaction();

        Criteria criteria = adminSession.createCriteria(UsersEntity.class);
        try {
            UsersEntity loggedInUser = (UsersEntity) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            try {
                if (!(loggedInUser.getPassword().equals(password))) {
                    errorMsg = "Username or Password is incorrect.<br />";
                    return "redirect:/action=login";
                } else {
                    Cookie userId = new Cookie("userId", Integer.toString(loggedInUser.getUserid()));
                    userId.setPath("/");
                    userId.setMaxAge(-1);
                    response.addCookie(userId);
                    errorMsg = "";
                    model.addAttribute("navbar", loggedInMenu);
                    return "redirect:/dashboard";
                }
            } catch (NullPointerException e) {
                errorMsg = "Username or Password is incorrect.<br />";
                return "redirect:/action=login";
            }

        } catch(NullPointerException e) {
            errorMsg = "Username or Password is incorrect.<br />";
            return "redirect:/action=login";
        }
    }




    // Sarah is still working on this
    @RequestMapping(value = "/dashboard/admin/newAccount", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famName") String famName,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model) {

        FamiliesEntity family = newFamily(famName);
        UsersEntity user = newUser(fName, lName, email, 0, password, family.getFamilyid());

        model.addAttribute("family", family);
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


    private UsersEntity newUser(String fName,
                                String lName,
                                String email,
                                int usergroup,
                                String password,
                                int familyid) {

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

//    private LocationsEntity pushMaplatitude (String latitude){
//        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
//        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
//        Session map = sessionFactory.openSession();
//
//        Transaction mapTransaction = map.beginTransaction();
//        LocationsEntity latlocation = new LocationsEntity();
//
//        latlocation.setLatitude(latitude);
//
//
//
//        mapTransaction.commit();
//        return latlocation;
//    }


}



