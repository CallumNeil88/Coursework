package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.PreparedStatement;

@Path("users/")
public class Users {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newUser(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Firstname") String Firstname, @FormDataParam("Lastname") String Lastname) {

        try {
            if(Username == null || Password == null || Firstname == null || Lastname == null){
                throw new Exception("One or more form data parameters are missing in th HTTP request");
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


}