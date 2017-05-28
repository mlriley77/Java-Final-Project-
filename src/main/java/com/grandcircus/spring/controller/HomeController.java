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


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HomeController {
    private static String errorMsg = "";

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

    @RequestMapping(value = "/")
    public String helloWorld() {
        return "welcome";
    }

    @RequestMapping(value = "/action=register/family", method = RequestMethod.GET)
    public String registerNewAdmin(HttpServletResponse response) {
        deleteUserCookie(response);
        return "newFamily";
    }
    @RequestMapping(value = "/action=register/family/submit", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famName") String famName,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        clearErrorMessage();
        Session browsingSession = loadSession();
        Criteria usersCriteria = browsingSession.createCriteria(UsersEntity.class);

        FamiliesEntity family = newFamily(famName);

        try {
            // this will pass if the email exists, which we do not want.
            //redirects to the register page
            UsersEntity newUser = (UsersEntity) usersCriteria
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();

            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/family";
        } catch (NullPointerException e) {
            newUser(fName, lName, email, password, 0, family.getFamilyid());
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/action=register/user", method = RequestMethod.GET)
    public String registerNewChild(HttpServletResponse response) {
        deleteUserCookie(response);
        return "newUser";
    }
    @RequestMapping(value = "/action=register/user/submit", method = RequestMethod.POST)
    public String newChild(@RequestParam("famId") int famId,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {

        clearErrorMessage();
        Session browsingSession = loadSession();

        Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);
        Criteria usersCriteria = browsingSession.createCriteria(UsersEntity.class);

        // verifies whether or not the input family id is real
        try {
            FamiliesEntity family = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", famId))
                    .uniqueResult();
            int doesThisExist = family.getFamilyid();
        } catch (NullPointerException e) {
            errorMsg = "This family ID does not exist";
            return "redirect:/action=register/user";
        }

        try {
            // this will run only if the email already exists, which we don't want.
            UsersEntity newUser = (UsersEntity) usersCriteria
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();

            errorMsg = "This email is already associated with an account";
            return "redirect:/action=register/user";
        } catch (NullPointerException e) {
            newUser(fName, lName, email, password, 1, famId);
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/action=login", method = RequestMethod.GET)
    public String logIn() {
        return "login";
    }
    @RequestMapping(value = "/action=login/submit", method = RequestMethod.POST)
    public String loggedIn(@CookieValue(value = "userId", defaultValue = "null") String userId,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password,
                           HttpServletResponse response) {

        Session browsingSession = loadSession();
        clearErrorMessage();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);

        if (userId.equals("null")) {
            //do this if the user is not logged in
            try {
                UsersEntity user = (UsersEntity) userCriteria
                        .add(Restrictions.eq("email", email))
                        .uniqueResult();

                // this throws a NullPointerException if the email doesn't exist
                String doesThisExist = user.getEmail();

                if (user.getPassword().equals(password)) {
                    // accepts the correct login and creates a cookie
                    createUserCookie("" + user.getUserid(),
                            "" + user.getUsergroup(),
                            response);
                    clearErrorMessage();
                    return "redirect:/dashboard";
                } else {
                    errorMsg = "Your email or password is incorrect";
                    return "redirect:/action=login";
                }
            } catch(NullPointerException e) {
                // this happens if the user's submitted email does not have an account
                errorMsg = "Your email or password is incorrect";
                return "redirect:/action=login";
            }
        } else {
            // do this if the user is already logged in
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/action=logout")
    public String logOut(HttpServletResponse response) {
        deleteUserCookie(response);
        return "redirect:/";
    }

    @RequestMapping("/dashboard")
    public String dashboardPage(@CookieValue(value = "userId", defaultValue = "null") String userId,
                                Model model) {
        Session browsingSession = loadSession();

        // if the user is not logged in, it redirects to the login page
        if (userId.equals("null"))
        {
            System.out.println("and then this happened");
            return "redirect:/action=login";
        }

        //builds our criteria tools
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);
        Criteria childCriteria = browsingSession.createCriteria(UsersEntity.class);
        Criteria adminCriteria = browsingSession.createCriteria(UsersEntity.class);
        Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);


        UsersEntity thisAccount = (UsersEntity) userCriteria
                .add(Restrictions.eq("userid",
                        Integer.parseInt(userId)))
                .uniqueResult();

        // Loads the admin or child panel depending on the user's group
        // 0 is admin, anything else is sub
        if (thisAccount.getUsergroup() == 0)
        {
            ArrayList<UsersEntity> childAccounts = (ArrayList<UsersEntity>) childCriteria
                    .add(Restrictions.eq("familyid", thisAccount.getFamilyid()))
                    .add(Restrictions.eq("usergroup", 1))
                    .list();
            FamiliesEntity familyObject = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", thisAccount.getFamilyid()))
                    .uniqueResult();

            model.addAttribute("user", thisAccount);
            model.addAttribute("children", childAccounts);
            model.addAttribute("family", familyObject);

            return "adminDashboard";
        } else {
            UsersEntity adminAccount = (UsersEntity) adminCriteria
                    .add(Restrictions.eq("familyid", thisAccount.getFamilyid()))
                    .add(Restrictions.eq("usergroup", 0))
                    .uniqueResult();
            FamiliesEntity familyUnit = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", thisAccount.getFamilyid()))
                    .uniqueResult();

            model.addAttribute("user", thisAccount);
            model.addAttribute("parent", adminAccount);
            model.addAttribute("family", familyUnit);

            return "childDashboard";
        }
    }

    @RequestMapping(value = "/action=submitlocation", method = RequestMethod.POST)
    public String postcoords(@RequestParam("lat") String checkinLat,
                             @RequestParam("long") String checkinLong,
                             @RequestParam("userId") String userId){
        Session browsingSession = loadSession();
        Transaction myTransaction = browsingSession.beginTransaction();

        Criteria criteria = browsingSession.createCriteria(UsersEntity.class);
        UsersEntity childCheckingIn = (UsersEntity) criteria
                .add(Restrictions.eq("userid", Integer.parseInt(userId)))
                .uniqueResult();

        childCheckingIn.setLastlat(checkinLat);
        childCheckingIn.setLastlong(checkinLong);
        childCheckingIn.setLasttime(getCurrentTime());

        browsingSession.save(childCheckingIn);
        myTransaction.commit();

        return "redirect:/dashboard";
    }

    private static void deleteUserCookie(HttpServletResponse response) {
        Cookie userId = new Cookie("userId", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userId);

        Cookie userGroup = new Cookie("userGroup", "null");
        userId.setPath("/");
        userId.setMaxAge(0);
        response.addCookie(userGroup);
    }
    private static void createUserCookie(String userIdString,
                                         String userGroupString,
                                         HttpServletResponse response) {
        Cookie userId = new Cookie("userId", userIdString);
        userId.setPath("/");
        userId.setMaxAge(-1);
        response.addCookie(userId);

        System.out.println();
        Cookie userGroup = new Cookie("userGroup", userGroupString);
        userId.setPath("/");
        userId.setMaxAge(-1);
        response.addCookie(userGroup);
    }
    private static void clearErrorMessage() {
        errorMsg = "";
    }
    private static Session loadSession() {
        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();

        return sessionFactory.openSession();
    }
    private FamiliesEntity newFamily(String famName) {
        Session browsingSession = loadSession();
        Transaction databaseTransaction = browsingSession.beginTransaction();

        FamiliesEntity newFamily = new FamiliesEntity();
        newFamily.setName(famName);

        browsingSession.save(newFamily);
        databaseTransaction.commit();

        return newFamily;
    }
    private static void newUser(String fName, String lName,
                                String email, String password,
                                int usergroup, int familyid) {
        Session browsingSession = loadSession();
        Transaction databaseTransaction = browsingSession.beginTransaction();

        UsersEntity user = new UsersEntity();

        user.setFname(fName);
        user.setLname(lName);
        user.setEmail(email);
        user.setUsergroup(usergroup);
        user.setPassword(password);
        user.setFamilyid(familyid);

        browsingSession.save(user);
        databaseTransaction.commit();
    }
    private static Timestamp getCurrentTime() {
        Date dateObject = new Date();
        long currentTimeLong = dateObject.getTime();
        return new Timestamp(currentTimeLong);
    }

    @ModelAttribute("err")
    public String displayErrorMessage() {
        return errorMsg;
    }
    @ModelAttribute("navbar")
    public String loadNavBar(@CookieValue(value = "userId", defaultValue = "null") String userId) {
        String loggedInMenu =
                "<ul>\n" +
                        "<li><a href=\"/\">Home</a></li>" +
                        "<li><a href=\"/dashboard\">Dashboard</a></li>" +
                        "<li><a href=\"/action=logout\">Logout</a></li>" +
                        "</ul>\n";
        String loggedOutMenu=
                "<ul>\n" +
                        "<li><a href=\"/\">Home</a></li>" +
                        "<li><a href=\"/action=login\">Login</a></li>" +
                        "<li><a href=\"/action=register/user\">Join A Family</a></li>" +
                        "<li><a href=\"/action=register/family\">Create A Family</a></li>" +
                        "</ul>\n";

        if (userId.equals("null")) {
            return loggedOutMenu;
        } else {
            return loggedInMenu;
        }
    }

}



