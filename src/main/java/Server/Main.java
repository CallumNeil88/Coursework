package Server;

import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
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
    }

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

    public static void readDatabase() {

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

    public static void writeDatabase(int UserID,String Username, String Password) {

        try {

            PreparedStatement ps = db.prepareStatement("INSERT into Users (UserID, Username, Password) VALUE (?,?,?)");
            ps.setInt(1,UserID);
            ps.setString(2,Username);
            ps.setString(3,Password);

            ps.executeUpdate();

        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }
    }
}
