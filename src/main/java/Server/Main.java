package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        openDatabase("Database.db");

        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*Scanner inputInt = new Scanner(System.in);
        Scanner inputStr = new Scanner(System.in);


        System.out.println("Enter your Username");
        String Username = inputStr.nextLine();
        System.out.println("Enter your Password");
        String Password = inputStr.nextLine();
        System.out.println("Enter your Firstname");
        String Firstname = inputInt.nextLine();
        System.out.println("Enter your Lastname");
        String Lastname = inputInt.nextLine();

        newUser(Username,Password,Firstname,Lastname);

        //readDatabase();

        closeDatabase();*/
    }

    public static Connection db = null;

    /*public static void main(String[] args) {
        openDatabase("Database.db");
        readDatabase();

        Scanner inputInt = new Scanner(System.in);
        Scanner inputStr = new Scanner(System.in);

        System.out.println("Enter your UserID");
        int UserID = inputInt.nextInt();
        System.out.println("Enter your Username");
        String Username = inputStr.nextLine();
        System.out.println("Enter your Password");
        String Password = inputStr.nextLine();

        writeDatabase(UserID,Username,Password);

        readDatabase();

        closeDatabase();
    }*/

    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC"); //loads the database driver
            SQLiteConfig config = new SQLiteConfig(); // This does the database's settings
            config.enforceForeignKeys(true);  //Allows you to pass things by ref(allows you to delete from everywhere)
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) { //catches any errors and gives the user an error message
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) { //catches any errors and gives the user an error message
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

    /*public static void readDatabase() {

        try {

            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int userID = results.getInt(1);
                String username= results.getString(2);
                String Password = results.getString(3);
                System.out.println("UserID: " + userID + " " +"Username: " +username + " " + "Password: " + Password);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }


    }

    public static void newUser(String Username, String Password, String Firstname, String Lastname) {

        try {

            PreparedStatement ps = db.prepareStatement("INSERT into Users (Username,Password,Firstname,Lastname) VALUES (?,?,?,?)");
            ps.setString(1,Username);
            ps.setString(2,Password);
            ps.setString(3,Firstname);
            ps.setString(4,Lastname);

            ps.executeUpdate();

        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }
    }*/
}
