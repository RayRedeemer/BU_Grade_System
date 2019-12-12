package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AcademicStatistics implements Statisticsable{

    private double _mean;
    private double _median;
    private double _minimum;
    private double _maximum;
    private double _range;

    private AcademicStatistics() {
        _mean = 0;
        _median = 0;
        _minimum = 0;
        _maximum = 0;
        _range = 0;
    }

    public static AcademicStatistics of(Course c, boolean is_grad) {
        AcademicStatistics as = new AcademicStatistics();
        c.calculateGrades();
        int count = 0;
        double max = -1;
        double min = 101;
        double sum = 0;
        ArrayList<Double> medianList = new ArrayList<Double>();
        for (Student s : c.getAllStudents()) {
            if (s.isGradStudent() == is_grad && !s.getWithdrawn()) {
                double score = s.getGrade();
                count++;
                sum += score;
                if (score < min) min = score;
                if (score > max) max = score;
                medianList.add(score);
            }
        }
        if (count == 0) {
            return as;
        }
        as.setMean(sum / count);
        as.setMaximum(max);
        as.setMinimum(min);
        as.setRange(max - min);
        Collections.sort(medianList);
        int mid = medianList.size()/2;
        if (medianList.size() % 2 == 0) {
            as.setMedian((medianList.get(mid) + medianList.get(mid+1))/2);
        } else {
            as.setMedian(medianList.get(mid));
        }
        return as;
    }

    public static AcademicStatistics of(Category cat, boolean is_grad) {
        AcademicStatistics as = new AcademicStatistics();
        HashMap<Student, Double> gradeMap = new HashMap<Student, Double>();
        for (AcademicObject aoa : cat.getAllDescendants()) {
            Assignment a = (Assignment) aoa;
            double aWeight = a.getWeight();
            double outOf = a.getMaxScore();
            for (AcademicObject aosub : a.getAllDescendants()) {
                Submission sub = (Submission) aosub;
                Student s = sub.getStudent();
                if (s.isGradStudent() == is_grad && !s.getWithdrawn()) {
                    double score;
                    if (sub.getStyle()) {
                        score = (sub.getScore() + sub.getBonus()) / outOf;
                    } else {
                        score = (sub.getScore() + sub.getBonus() + outOf) / outOf;
                    }
                    score *= aWeight;
                    if (gradeMap.containsKey(s)) {
                        gradeMap.put(s, gradeMap.get(s) + score);
                    } else {
                        gradeMap.put(s, score);
                    }
                }
            }
        }
        ArrayList<Double> medianList = new ArrayList<Double>();
        medianList.addAll(gradeMap.values());
        Collections.sort(medianList);
        int count = 0;
        int sum = 0;
        for (double d : medianList) {
            count++;
            sum += d;
        }
        if (count == 0) return as;
        as.setMean((sum / count) * 100);
        as.setMinimum(medianList.get(0) * 100);
        as.setMaximum(medianList.get(medianList.size()-1) * 100);
        as.setRange(as.getMaximum() - as.getMinimum());
        int mid = medianList.size()/2;
        if (medianList.size() % 2 == 0) {
            as.setMedian(((medianList.get(mid) + medianList.get(mid+1))/2) * 100);
        } else {
            as.setMedian(medianList.get(mid) * 100);
        }
        return as;
    }

    public static AcademicStatistics of(Assignment a, boolean is_grad) {
        AcademicStatistics as = new AcademicStatistics();
        int count = 0;
        double max = -1;
        double min = 101;
        double sum = 0;
        ArrayList<Double> medianList = new ArrayList<Double>();
        double outOf = a.getMaxScore();
        for (AcademicObject aosub : a.getAllDescendants()) {
            Submission sub = (Submission) aosub;
            Student s = sub.getStudent();
            if (s.isGradStudent() == is_grad && !s.getWithdrawn()) {
                double score;
                if (sub.getStyle()) {
                    score = (sub.getScore() + sub.getBonus()) / outOf;
                } else {
                    score = (outOf + sub.getScore() + sub.getBonus()) / outOf;
                }
                count++;
                if (score > max) max = score;
                if (score < min) min = score;
                sum += score;
                medianList.add(score);
            }
        }

        if(count == 0) return as;
        as.setMean((sum / count) * 100);
        as.setMinimum(min * 100);
        as.setMaximum(max * 100);
        as.setRange((max - min) * 100);
        Collections.sort(medianList);
        int mid = medianList.size()/2;
        if (medianList.size() % 2 == 0) {
            as.setMedian(((medianList.get(mid) + medianList.get(mid+1))/2) * 100);
        } else {
            as.setMedian(medianList.get(mid) * 100);
        }
        return as;
    }

    // setters
    public void setMean(double m) {
        _mean = m;
    }

    public void setMedian(double m) {
        _median = m;
    }

    public void setMinimum(double m) {
        _minimum = m;
    }

    public void setMaximum(double m) {
        _maximum = m;
    }

    public void setRange(double r) {
        _range = r;
    }

    // getters
    public double getMean() {
        return _mean;
    }

    public double getMinimum() {
        return _minimum;
    }

    public double getMaximum() {
        return _maximum;
    }

    public double getRange() {
        return _range;
    }

    public double getMedian() {
        return _median;
    }
}
