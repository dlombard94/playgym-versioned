package controllers;

import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;
    render ("dashboard.html",member,assessments);
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
