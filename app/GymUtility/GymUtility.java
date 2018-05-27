package GymUtility;

import models.Assessment;
import models.Member;

import java.util.ArrayList;
import java.util.List;

public class GymUtility {

    public static double calculateBMI(Member member, Assessment assessment) {
        return Math.round((assessment.weight / (member.height* member.height))*100.0)/100.0;
    }

    public static double calculateBMI(Member member, List<Assessment> assessments){
        double memberBmi;
        Assessment assessment;
        //if the member has more than 1 assessment get the most recent
        if (member.assessments.size()>1){
            //here the first index is the last entered assessment dueto Collections.reverse()
            assessment = member.assessments.get(0);
            memberBmi = GymUtility.calculateBMI(member,assessment);
        }else{
            //if the member has no assessment then use their starting weight
            memberBmi = (member.startingweight / ((member.height* member.height))*10)/10;
        }
        return memberBmi;
    }

    public static String determineBMICategory(double bmivalue) {
        if (bmivalue < 16d) {
            return "SEVERELY UNDERWEIGHT";
        } else if (bmivalue >= 16d && bmivalue < 18.5d) {
            return "UNDERWEIGHT";
        } else if (bmivalue >= 18.5d && bmivalue < 25d) {
            return "NORMAL";
        } else if (bmivalue >= 25d && bmivalue < 30d) {
            return "OVERWEIGHT";
        } else if (bmivalue >= 30d && bmivalue < 35d) {
            return "MODERATELY OBESE";
        } else {
            return "SEVERELY OBESE";
        }
    }

    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {

        if (member.gender.equals("M")) {
            if (member.height*39.3701 < 60) {
                double idealWeight = 50;
                if (assessment.weight > (idealWeight - 0.5) && assessment.weight < (idealWeight + 0.5)) {
                    return true;
                }
                return false;
            } else {
                double idealWeight = 50 + (((member.height * 39.3701) - 60) * 2.3);
                if (assessment.weight > (idealWeight - 0.5) && assessment.weight < (idealWeight + 0.5)) {
                    return true;
                }
                return false;
            }
        } else

        if (member.height*39.3701 < 60) {
            double idealWeight = 45.5;
            if (assessment.weight > (idealWeight - 0.5) && assessment.weight < (idealWeight + 0.5)) {
                return true;
            }
            return false;
        } else {
            double idealWeight = 45.5 +(((member.height * 39.3701) - 60) * 2.3);
            if (assessment.weight > (idealWeight - 0.5) && assessment.weight < (idealWeight + 0.5)) {
                return true;
            }
            return false;
        }


    }
}
