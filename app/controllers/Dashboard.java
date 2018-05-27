package controllers;

import GymUtility.GymUtility;
import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.Collections;
import java.util.List;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;
    Collections.reverse(assessments);//java.util.Collections class method. It reverses the order of elements in a list passed as an argument.
    double memberBmi = Math.round(GymUtility.calculateBMI(member,assessments)*100.0)/100.0;

    //uses utility method from programming assignment to return String representation of the members BMI Category
    String memberCategory = GymUtility.determineBMICategory(memberBmi);
    boolean memberIdealWeight = true;
    if (assessments.size()>0){
      memberIdealWeight = GymUtility.isIdealBodyWeight(member,member.assessments.get(0));
    }
    render ("dashboard.html",member, assessments, memberBmi, memberCategory, memberIdealWeight);
  }

  public static void addAssessment(double weight, double chest, double thigh,double upperarm, double waist, double hips){
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight,chest,thigh,upperarm,waist,hips);
    member.assessments.add(assessment);
    member.save();
    redirect ("/dashboard");
  }

  public static void deleteAssessment(Long id, Long assessmentid){
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    Logger.info("Removing "+assessment);
    member.assessments.remove(assessment);
    member.save();
    assessment.delete();

    redirect("/dashboard");
  }


}
