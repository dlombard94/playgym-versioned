package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    List<Member> members = Member.findAll();
    render ("dashboard.html",members);
  }
}
