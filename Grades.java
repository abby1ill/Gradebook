import java.util.ArrayList;

public class Grades
{
        private String Name;
        private String Type;
        private String totalpoints;
        private ArrayList<String> pointsReceived;
//      private averageGrade;

        public Grades(String name, String type, ArrayList<String> points)

        {
                Name     = name;
                Type     = type;
                pointsReceived = new ArrayList<String>();
                pointsReceived = points;
        }

        public Grades(String name, String type)
        {
                Name = name;
                Type = type;
        }


        public Grades()
        {
                Name = null;
                Type = null;
        }




        public String getName(){return Name;}
        public String getType(){return Type;}
        public String getTotalPoints(){ return totalpoints;}
        public String getPointsReceived(int i){ return pointsReceived.get(i);}
        public void setName(String name){Name=name;}
        public void setType(String type){Type=type;}
        public void setTotalPoints(String points){totalpoints = points;}
        public void addPointsReceived(String points){ pointsReceived.add(points);}

}
