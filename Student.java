public class Student
{
        private String FName, LName;
        private String ID;

        public Student()
        {
                FName = null;
                LName = null;
                ID=null;
        }

        public Student(String fname, String lname, String id)
        {
                LName = lname;
                FName = fname;
                ID = id;
        }
        public String getFName(){
                return FName;
        }


        public String getLName(){
                return LName;
        }


        public String getID(){
                return ID;
        }

        public void setFName(String fname){
                FName = fname;
        }

        public void setLName(String lname){
                LName = lname;
        }
          
        public void setID(String id){
                ID=id;
        
