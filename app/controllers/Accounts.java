package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }

    public static void login()
    {
        render("login.html");
    }

    public static void settings(){render("settings.html");}

    public static void register(String name, String gender, String email, String password, String address, double height, double startingweight)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, gender, email, password,address,height,startingweight);
        member.save();
//        Takes you back to the start menu where you now login in with you credentials that you just registered with
        redirect("/");
    }

    public static void updateSettings(String name, String gender, String email, String password, String address, double height, double startingweight){
        Member member = getLoggedInMember();
        //takes all the information from the updateSettings form and passes them here via the route
        //sets the login Member's settings to the new ones
        Logger.info("Updating user " + email);
        member.name = name;
        member.gender = gender;
        member.email = email;
        member.password = password;
        member.address = address;
        member.height = height;
        member.startingweight = startingweight;
        //saves new details and redirects you back to the member dashboard
        member.save();
        redirect("/dashboard");
    }

    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        //when logging in it checks if the email and password belong to a member
        //if so and if credentials are correct - it redirects you to the assoiciated member dashboard
        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        }else{
                //if credentials don't belong to wither then it brings you back to the login page
                Logger.info("Authentication failed");
                redirect("/login");
            }
        }


    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
}

