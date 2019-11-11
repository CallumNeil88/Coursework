package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Path("users/")
public class Users {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newUser(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Firstname") String Firstname, @FormDataParam("Lastname") String Lastname) {

        try {
            if(Username == null || Password == null || Firstname == null || Lastname == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT into Users (Username,Password,Firstname,Lastname) VALUES (?,?,?,?)");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setString(3, Firstname);
            ps.setString(4, Lastname);

            ps.executeUpdate();
            return("{\"Status\": \"OK\"}");

        } catch (Exception exception) {
            return ("Database error: " + exception.getMessage());
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("Username") String Username,@FormDataParam("Password") String Password) {
        System.out.println("test1");
        try {
            System.out.println("test2");
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password from Users where Username = ?");
            ps1.setString(1, Username);
            ResultSet loginResults = ps1.executeQuery();
            System.out.println("TEst3");
            if (loginResults.next()) {
                System.out.println("test4");
                String correctPassword = loginResults.getString(1);
                System.out.println("test5");
                if (Password.equals(correctPassword)) {
                    System.out.println("test6");
                    String token = UUID.randomUUID().toString();
                    System.out.println("test7");
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, Username);
                    System.out.println("test8");
                    ps2.executeUpdate();
                    System.out.println("test9");

                    return "{\"token\": \""+ token + "\"}";

                } else {

                    return "{\"error\": \"Incorrect password!\"}";

                }

            } else {

                return "{\"error\": \"Unknown user!\"}";

            }
        }catch (Exception exception){
            System.out.println("Database error during /users/login: " + exception.getMessage());
            return "{\"error\": \"server side error\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@FormDataParam("Username") String Username){
        try {
            if (Username == null){
                throw new Exception("Username missing");
            }
            PreparedStatement ps = Main.db.prepareStatement("DELETE from Users Where Username = ?");
            ps.setString(1,Username);
            ps.execute();

            return("{\"Status\": \"OK\"}");

        }catch (Exception exception){
            System.out.println("Database error during /users/delete: " + exception.getMessage());
            return "{\"error\": \"server side error\"}";
        }
    }

    @POST
    @Path("studyTime")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String studyTime(@FormDataParam("StudyStartTime") String StudyStartTime, @FormDataParam("StudyEndTime") String StudyEndTime, @FormDataParam("Token") String Token){
        try {
            if(StudyStartTime == null || StudyEndTime == null || Token == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request");
            }

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users set StudyStartTime = ?,StudyEndTime = ? Where Token = ?");
            ps.setString(1, StudyStartTime);
            ps.setString(2, StudyEndTime);
            ps.setString(3, Token);

            ps.executeUpdate();
            return("{\"Status\": \"OK\"}");

        } catch (Exception exception) {
            return ("Database error: " + exception.getMessage());
        }
    }

}


