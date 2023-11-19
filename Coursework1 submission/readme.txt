You will need to download 5 jar files
that is

junit-platform-console-standalone-1.2.0.jar
maven-surefire-plugin-2.19.1.jar
mysql-connector-j-8.0.32.jar
org.testng.jar
plantuml-1.2023.5jar

when you use my database, you should change the java code content in Database class.
static void connection()  {
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8080/restaurant",
                    "root", "");
            statement = connection.createStatement();
        }catch (Exception e){
            System.out.println(e);
        }
    }


change "root" to your database username and change "" to your database password
change the path if your path is not equal to mine.

all the picture and java code in coursework1 file
You can open the entire coursework1 file in intellij
restaurant is sql file.

Thank you Sir ~