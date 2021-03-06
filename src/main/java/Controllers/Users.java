package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("users/")
public class Users {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newUser(@FormDataParam("username") String Username, @FormDataParam("password") String Password, @FormDataParam("firstname") String Firstname, @FormDataParam("lastname") String Lastname) {



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

            JSONObject response = new JSONObject();
            response.put("username", Username);
            response.put("password", Password);
            response.put("firstname", Firstname);
            response.put("lastname", Lastname);
            return response.toString();

        } catch (Exception exception) {
            return ("Database error: " + exception.getMessage());
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("username") String username, @FormDataParam("password") String password) {

        try {

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Users WHERE Username = ?");
            ps1.setString(1, username);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);

                if (password.equals(correctPassword)) {

                    String token = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, username);
                    ps2.executeUpdate();

                    JSONObject response = new JSONObject();
                    response.put("username", username);
                    response.put("token", token);
                    return response.toString();


                } else {

                    return "{\"error\": \"Incorrect password!\"}";

                }

            } else {

                return "{\"error\": \"Unknown user!\"}";

            }

        }catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }


    }

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) {

        try {

            System.out.println("user/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1);

                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = NULL WHERE UserID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return false;
        }
    }



   /* @POST
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

    }*/

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
    public String studyTime(@FormDataParam("StudyStartTime") String StudyStartTime, @FormDataParam("StudyEndTime") String StudyEndTime, @FormDataParam("Token") String Token, @CookieParam("token") String token){

        if (!Users.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }
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


