package controllers;

import GymUtility.GymUtility;
import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.Collections;
import java.util.List;

public class TrainerPage extends Controller{

    public static void index() {
        Logger.info("Rendering Trainer Page");
        List<Member> membersList = Member.findAll();
        render("trainerpage.html",membersList);
    }

    public static void deleteMember(Long id){
        Member member = Member.findById(id);
        member.delete();
        redirect("/trainerpage");
    }

    public static void viewMember(Long id){
        Logger.info("Rendering Trainer Page");
        Member member = Member.findById(id);
        List<Assessment> assessments = member.assessments;
        Collections.reverse(assessments);//java.util.Collections class method. It reverses the order of elements in a list passed as an arg
        double memberBmi = Math.round(GymUtility.calculateBMI(member,assessments)*100.0)/100.0;

        //uses utility method from programming assignment to return String representation of the members BMI Category
        String memberCategory = GymUtility.determineBMICategory(memberBmi);
        boolean memberIdealWeight = true;
        if (assessments.size()>0){
            memberIdealWeight = GymUtility.isIdealBodyWeight(member,member.assessments.get(0));
        }

        render ("trainermemberpage.html",member, assessments, memberBmi, memberCategory, memberIdealWeight);
    }

    public static void updateComment(Long id, String comment){
        //finding the assessment by id
        //no need to find specific member as every assessment in database has unique id
        //set comment field to passed in comment from form
        //save the new assessment
        //reload the main trainer page
        Assessment assessment = Assessment.findById(id);
        assessment.comment = comment;
        assessment.save();
        redirect("/trainerpage");
    }
}

