package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import com.grandcircus.spring.util.Cookies;
import com.grandcircus.spring.util.DAO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private static final String YourAPIKey = "F491A273"; //Your API Key

    @RequestMapping(value = "/")
    public String helloWorld(@CookieValue(value = "userId", defaultValue = "null") String userId) {
        if(!(userId.equals("null"))) {
          return "redirect:/dashboard";
        }
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

        FamiliesEntity family = DAO.newFamily(famName);
        if (DAO.doesUserExist(email)) {
            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/family";
        } else {
            clearErrorMessage();
            DAO.newUser(fName, lName, email, password, 0, family.getFamilyid());
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

        if (!DAO.doesFamilyExist(famId)) {
            errorMsg = "This family ID does not exist";
            return "redirect:/action=register/user";
        }

        if (DAO.doesUserExist(email)) {
            errorMsg = "This email is already associated with an account.";
            return "redirect:/action=register/family";
        } else {
            clearErrorMessage();
            DAO.newUser(fName, lName, email, password, 1, famId);
            return "redirect:/action=login";
        }
    }

    @RequestMapping(value = "/action=login", method = RequestMethod.GET)
    public String logIn(HttpServletResponse response) {
        Cookies.deleteUserCookie(response);
        return "login";
    }

    @RequestMapping(value = "/action=login/submit", method = RequestMethod.POST)
    public String loggedIn(@RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password,
                           HttpServletResponse response) {

        clearErrorMessage();
        Cookies.deleteUserCookie(response);

        // kicks back to login if the email doesn't exist
        if (!(DAO.doesUserExist(email))) {
            errorMsg = "Your email or password is incorrect";
            return "redirect:/action=login";
        }

        UsersEntity user = DAO.getUserByEmail(email);

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

        DAO.getSessionStats();

        // if the user is not logged in, it redirects to the login page
        if (userId.equals("null"))
        {
            return "redirect:/";
        }

        UsersEntity thisAccount = DAO.loadThisAccount(userId);

        if (thisAccount.getUsergroup() == 0) {
            ArrayList<UsersEntity> childAccounts = DAO.loadChildAccounts(thisAccount.getFamilyid());
            FamiliesEntity family = DAO.loadFamily(thisAccount.getFamilyid());

            model.addAttribute("user", thisAccount);
            model.addAttribute("children", childAccounts);
            model.addAttribute("family", family);

            return "adminDashboard";
        } else {
            UsersEntity parent = DAO.loadParentAccount(thisAccount.getFamilyid());
            FamiliesEntity family = DAO.loadFamily(thisAccount.getFamilyid());

            model.addAttribute("user", thisAccount);
            model.addAttribute("parent", parent);
            model.addAttribute("family", family);

            return "childDashboard";
        }
    }

    @RequestMapping(value = "/action=submitlocation", method = RequestMethod.POST)
    public String postcoords(@RequestParam("lat") String checkinLat,
                             @RequestParam("long") String checkinLong,
                             @RequestParam("userId") String userId) {

        DAO.updateUserCoordinates(checkinLat, checkinLong, userId);

        return "redirect:/dashboard";
    }

    public static Timestamp getCurrentTime() {
        Date dateObject = new Date();
        long currentTimeLong = dateObject.getTime();
        return new Timestamp(currentTimeLong);
    }

    private static String errorMsg = "";

    private static void clearErrorMessage() {
        errorMsg = "";
    }

    @ModelAttribute("err")
    public String setErrorMessage() {
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