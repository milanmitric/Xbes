package hello.rest;

import hello.dto.UserDTO;
import hello.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aloha on 28-May-16.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/signin")
    public ResponseEntity<UserDTO> signin() {
        //TODO
        /*SIMULATION*/
        logger.info("REST request for signing in");
        UserDTO user=new UserDTO("usernameTEST", Role.ROLE_GRADJANIN);  //this is user that is found in database
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("Returning user : " + user);
        return ResponseEntity.ok(user);
    }


    @RequestMapping(value = "/signup")
    public HttpStatus signup() {
        //TODO
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/auth")
    public ResponseEntity<UserDTO> auth() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH DATA(ALL): " + auth.toString());
        UserDTO user=new UserDTO(Role.ROLE_ANONYMOUS, "ANONYMOUS");
        try{
            user.setRole(((UserDTO)auth.getPrincipal()).getRole());
            user.setUsername(((UserDTO)auth.getPrincipal()).getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(user);
    }


    @RequestMapping(value = "/signout")
    public HttpStatus signout() {
        SecurityContextHolder.clearContext();
        return HttpStatus.OK;
    }


}
