package hello.dto;

/**
 * Created by aloha on 29-May-16.
 * obj is used when we need to send user details from backend to frontend
 */
public class UserDTO {

    private String username;
    private String role;

    public UserDTO(String username, String role) {
        this.username=username;
        this.role=role;
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

    //TODO
    // function for:
    // IN(real user obj)
    // OUT(userDTO)

}
