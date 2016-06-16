package hello.dto;

import hello.entity.gov.gradskaskupstina.User;

/**
 * Created by aloha on 29-May-16.
 * obj is used when we need to send user details from backend to frontend
 */
public class UserDTO {

    private String username;
    private String role;
    private String ime;
    private String prezime;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.role=user.getRole();
        this.ime=user.getIme();
        this.prezime=user.getPrezime();

    }
    public UserDTO(){
        username="";
        role="ROLE_ANONYMOUS";
        ime="";
        prezime="";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIme(){return ime;}

    public void setIme(String ime){this.ime=ime;}

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return "[ username = " + username +" ]";
    }


}
