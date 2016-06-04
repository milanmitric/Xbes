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

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.role=user.getRole();
        this.ime=user.getIme();

    }
    public UserDTO(){
        username="ANONYMOUS";
        role="ROLE_ANONYMOUS";
        ime="ANONYMOUS";
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

    @Override
    public String toString() {
        return "[ username = " + username +" ]";
    }
    //TODO
    // function for:
    // IN(real user obj)
    // OUT(userDTO)

}
