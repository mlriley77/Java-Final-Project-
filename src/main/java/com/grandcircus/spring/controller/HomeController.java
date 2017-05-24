package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import org.apache.http.HttpResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MichaelRiley on 5/21/17.
 */


@Controller
public class HomeController {
    private static final String ApiUrl = "https://api.hippoapi.com/v3/more/json";
    /*
     * Query string for request
     * %1$s = ApiUrl
     * %2$s = API Key
     * %3$s = Email address to query
     */
    private static final String QueryFormatString = "%1$s/%2$s/%3$s";

    private static final String YourAPIKey = "D4DABD4A"; //Your API Key

    @RequestMapping(value = "getemailJSON", method = RequestMethod.GET)
    public String getEmailJSON(Model model, @RequestParam("email") String email) {
        /**
         * The main program entry point
         * @param args the command line arguments
         * @throws IOException If the server does not return a success response
         */

//        System.out.println("Input email address to verify");
//        // Create a scanner to read in the requested email address
//        Scanner in = new Scanner(System.in);
//        String readLine = in.next();

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

            String result = json.getJSONObject("emailVerification").getJSONObject("mailboxVerification").get("result").toString();

            model.addAttribute("jsonString", result.toString());

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "welcome";
    }

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        return new ModelAndView("welcome", "hello", "hello world");
    }

    @RequestMapping(value = "/register/family", method = RequestMethod.GET)
    public String registerFamily() {
        return "newFamily";
    }

    /**
     * Sends newly registered admin to their dashboard
     *
     * @return admin dashboard
     */
    @RequestMapping(value = "/dashboard/admin/newAccount", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famName") String famName,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model,
                           HttpServletResponse response) {

        FamiliesEntity family = newFamily(famName);
        UsersEntity user = newUser(fName, lName, email, 0, password, family.getFamilyid());

        model.addAttribute("family", family);
        model.addAttribute("user", user);

        Cookie loginCookie = new Cookie("userId", Integer.toString(user.getUserid()));
        response.addCookie(loginCookie);

        return "adminDashboard";
    }

    @RequestMapping("/dashboard/admin")
    public String adminPage(@CookieValue(value = "userId", defaultValue = "null") String userId,
                            Model model) {
        if (userId.equals("null")) {
            return "welcome";
        }

        model.addAttribute("userId", userId);

        return "testView";
    }

    @RequestMapping("/dashboard/admin/newChild")
    public String registerUser(@RequestParam("id") int famId,
                               Model model) {
        model.addAttribute("famId", famId);
        return "newUser";
    }

    @RequestMapping(value = "/dashboard/admin/newChild/done", method = RequestMethod.POST)
    public String newChild(@RequestParam("famId") int famId,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model) {

        UsersEntity user = newUser(fName, lName, email, 1, password, famId);

        model.addAttribute("user", user);

        return "adminDashboard";
    }

    @RequestMapping("/childConsole")
    public ModelAndView viewChildConsole() {
        return new ModelAndView("childConsole", "hello", "hello world");
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

    private UsersEntity newUser(String fName, String lName, String email, int usergroup, String password, int familyid) {
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



