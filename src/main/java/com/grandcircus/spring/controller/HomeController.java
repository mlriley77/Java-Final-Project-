package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import com.grandcircus.spring.util.Cookies;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.UserException;
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
    /*
     * Query string for request
     * %1$s = ApiUrl
     * %2$s = API Key
     * %3$s = Email address to query
     */
    private static final String QueryFormatString = "%1$s/%2$s/%3$s";
    private static final String ApiUrl = "https://api.hippoapi.com/v3/more/json";
    private static final String YourAPIKey = "D4DABD4A"; //Your API Key

    @RequestMapping(value = "/")
    public String helloWorld() {
        return "welcome";
    }

    @RequestMapping(value = "getemail2", method = RequestMethod.GET, produces="application/text")
    public @ResponseBody String getEmail2(@RequestParam("email") String email) {
        String result = null;
        try {
            // Format the request url to the correct structure for the request
            URL requestUrl = new URL(String.format(QueryFormatString, ApiUrl, YourAPIKey, email));
            // Open a connection to the website
            HttpURLConnection myRequest = (HttpURLConnection) requestUrl.openConnection();
            // Set the type to HTTP GET
            myRequest.setRequestMethod("GET");
            // Create a new buffered reader to read the response back from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(myRequest.getInputStream()));

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

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/action=register/family", method = RequestMethod.GET)
    public String registerNewAdmin(HttpServletResponse response) {
        Cookies.deleteUserCookie(response);
        return "newFamily";
    }
    @RequestMapping(value = "/action=register/family/submit", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("famName") String famName,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        clearErrorMessage();

        FamiliesEntity family = newFamily(famName);
        if (doesUserExist(email)) {
            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/family";
        } else {
            clearErrorMessage();
            newUser(fName, lName, email, password, 0, family.getFamilyid());
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/action=register/user", method = RequestMethod.GET)
    public String registerNewChild(HttpServletResponse response) {
        Cookies.deleteUserCookie(response);
        return "newUser";
    }
    @RequestMapping(value = "/action=register/user/submit", method = RequestMethod.POST)
    public String newChild(@RequestParam("famId") int famId,
                           @RequestParam("fName") String fName,
                           @RequestParam("lName") String lName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {

        clearErrorMessage();

        if (!doesFamilyExist(famId)) {
            errorMsg = "This family ID does not exist";
            return "redirect:/action=register/user";
        }

        if (doesUserExist(email)) {
            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/family";
        } else {
            clearErrorMessage();
            newUser(fName, lName, email, password, 1, famId);
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/action=login", method = RequestMethod.GET)
    public String logIn() {
        return "login";
    }
    @RequestMapping(value = "/action=login/submit", method = RequestMethod.POST)
    public String loggedIn(@RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password,
                           HttpServletResponse response) {

        clearErrorMessage();
        Cookies.deleteUserCookie(response);

        // kicks back to login if the email doesn't exist
        if (!(doesUserExist(email))) {
            errorMsg = "Your email or password is incorrect";
            return "redirect:/action=login";
        }

        UsersEntity user = getUserByEmail(email);

        // kicks back if the password is incorrect
        if (!(user.getPassword().equals(password))) {
            errorMsg = "Your email or password is incorrect";
            return "redirect:/action=login";
        }

        // if we get to this point, the username and password are correct
        clearErrorMessage();

        Cookies.createUserCookie("" + user.getUserid(),
                "" + user.getUsergroup(),
                response);

        return "redirect:/dashboard";

    }

    @RequestMapping(value = "/action=logout")
    public String logOut(HttpServletResponse response) {
        Cookies.deleteUserCookie(response);
        return "redirect:/";
    }

    @RequestMapping("/dashboard")
    public String dashboardPage(@CookieValue(value = "userId", defaultValue = "null") String userId,
                                Model model) {

        // if the user is not logged in, it redirects to the login page
        if (userId.equals("null"))
        {
            return "redirect:/action=login";
        }

        UsersEntity thisAccount = loadThisAccount(userId);

        if (thisAccount.getUsergroup() == 0) {
            ArrayList<UsersEntity> childAccounts = loadChildAccounts(thisAccount.getFamilyid());
            FamiliesEntity family = loadFamily(thisAccount.getFamilyid());

            model.addAttribute("user", thisAccount);
            model.addAttribute("children", childAccounts);
            model.addAttribute("family", family);

            return "adminDashboard";
        } else {
            UsersEntity parent = loadParentAccount(thisAccount.getFamilyid());
            FamiliesEntity family = loadFamily(thisAccount.getFamilyid());

            model.addAttribute("user", thisAccount);
            model.addAttribute("parent", parent);
            model.addAttribute("family", family);

            return "childDashboard";
        }
    }

    @RequestMapping(value = "/action=submitlocation", method = RequestMethod.POST)
    public String postcoords(@RequestParam("lat") String checkinLat,
                             @RequestParam("long") String checkinLong,
                             @RequestParam("userId") String userId){

        updateUserCoordinates(checkinLat, checkinLong, userId);

        return "redirect:/dashboard";
    }

    //this will be the DAO stuff
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
    private static void updateUserCoordinates(String checkinLat,
                                              String checkinLong,
                                              String userId) {
        Session browsingSession = loadSession();
        Transaction myTransaction = browsingSession.beginTransaction();

        Criteria criteria = browsingSession.createCriteria(UsersEntity.class);
        UsersEntity personCheckingIn = (UsersEntity) criteria
                .add(Restrictions.eq("userid", Integer.parseInt(userId)))
                .uniqueResult();

        personCheckingIn.setLastlat(checkinLat);
        personCheckingIn.setLastlong(checkinLong);
        personCheckingIn.setLasttime(getCurrentTime());

        browsingSession.save(personCheckingIn);
        myTransaction.commit();
    }
    private static boolean doesUserExist(String email) {
        // this will pass if the email exists, or fail if the user does not exist.
        try {
            Session browsingSession = loadSession();
            Criteria usersCriteria = browsingSession.createCriteria(UsersEntity.class);

            UsersEntity newUser = (UsersEntity) usersCriteria
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();

            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }
    private static boolean doesFamilyExist(int famId) {
        try {
            Session browsingSession = loadSession();
            Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);

            FamiliesEntity family = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", famId))
                    .uniqueResult();
            int doesThisExist = family.getFamilyid();

            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    private static UsersEntity getUserByEmail(String email) {
        Session browsingSession = loadSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);

        return (UsersEntity) userCriteria
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }
    private static UsersEntity loadThisAccount(String userId) {
        Session browsingSession = loadSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);

        return (UsersEntity) userCriteria
                .add(Restrictions.eq("userid",
                        Integer.parseInt(userId)))
                .uniqueResult();
    }
    private static ArrayList<UsersEntity> loadChildAccounts(int familyId) {
        Session browsingSession = loadSession();
        Criteria childCriteria = browsingSession.createCriteria(UsersEntity.class);

        return (ArrayList<UsersEntity>) childCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 1))
                .list();
    }
    private static FamiliesEntity loadFamily(int familyId) {
        Session browsingSession = loadSession();
        Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);

        return (FamiliesEntity) familyCriteria
                .add(Restrictions.eq("familyid", familyId))
                .uniqueResult();
    }
    private static UsersEntity loadParentAccount(int familyId) {
        Session browsingSession = loadSession();
        Criteria adminCriteria = browsingSession.createCriteria(UsersEntity.class);
        return (UsersEntity) adminCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 0))
                .uniqueResult();
    }

    private static Timestamp getCurrentTime() {
        Date dateObject = new Date();
        long currentTimeLong = dateObject.getTime();
        return new Timestamp(currentTimeLong);
    }
    private static String errorMsg = "";
    private static void clearErrorMessage() {
        errorMsg = "";
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
                        "<li><a href=\"/action=register/user\">Register As A Child</a></li>" +
                        "<li><a href=\"/action=register/family\">Register As A Parent</a></li>" +
                        "</ul>\n";

        if (userId.equals("null")) {
            return loggedOutMenu;
        } else {
            return loggedInMenu;
        }
    }
}