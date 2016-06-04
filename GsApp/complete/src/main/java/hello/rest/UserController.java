package hello.rest;

import hello.businessLogic.core.BeanManager;
import hello.dto.UserDTO;
import hello.dto.LoginUser;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;
import hello.StringResources.MarkLogicStrings;
import hello.StringResources.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by aloha on 28-May-16.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/signin",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signin(@RequestBody LoginUser loginTry) {

        BeanManager<Users> bm1 = new BeanManager<>("schema/Users.xsd");
        /*citamo sve usere iz baze*/
        Users users = bm1.read(MarkLogicStrings.USERS_DOC_ID);
        for(User user : users.getUser()){
            if(loginTry.getPassword().equals(user.getPassword())
                    && loginTry.getUsername().equals(user.getUsername())){

                System.out.println("ULOGOVAO SE SABAN:" +user.getUsername()+ " " +user.getRole());
                final Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));
                final Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                HashMap<String, String> res= new HashMap<>();
                res.put("success", "true");
                res.put("data", user.getUsername());
                return new ResponseEntity(res, HttpStatus.OK);
            }
        }


        /*
        //*SIMULATION*//*
        logger.info("REST request for signing in");
        UserDTO user=new UserDTO("usernameTEST", Role.ROLE_GRADJANIN);  //this is user that is found in database
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("Returning user : " + user);
        */

        HashMap<String, String> res= new HashMap<>();
        res.put("success", "false");
        res.put("msg", "Pogresno username ili pass");
        return new ResponseEntity(res, HttpStatus.OK);
    }



    @RequestMapping(value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@RequestBody User userTry) {

        /*user's default role*/
        userTry.setRole(Role.ROLE_GRADJANIN);
        BeanManager<Users> bm1 = new BeanManager<>("schema/Users.xsd");
        /*citamo sve usere iz baze*/
        Users users = bm1.read(MarkLogicStrings.USERS_DOC_ID);

        for(User user : users.getUser()){
            if(user.getUsername().equals(userTry.getUsername())){
                HashMap<String, String> res= new HashMap<>();
                res.put("success", "false");
                res.put("msg", "Postoji vec username");
                return new ResponseEntity(res, HttpStatus.OK);
            }
        }


        /*dodamo novog usera*/
        users.getUser().add(userTry);
        /*upisemo ceo doc nazad*/
        //TODO hashing password
        bm1.write(users, MarkLogicStrings.USERS_DOC_ID, "Proba");

        HashMap<String, String> res= new HashMap<>();
        res.put("success", "true");
        res.put("msg", "Sve kul");
        return new ResponseEntity(res, HttpStatus.OK);
    }



    @RequestMapping(value = "/authenticate")
    public ResponseEntity<UserDTO> auth() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH DATA(ALL): " + auth.toString());
        UserDTO userDTO;
        try{
            User user = (User)auth.getPrincipal();
            userDTO=new UserDTO(user);
        }catch (Exception e){
            userDTO=new UserDTO();
        }

        return ResponseEntity.ok(userDTO);
    }


    @RequestMapping(value = "/signout")
    public HttpStatus signout() {
        SecurityContextHolder.clearContext();
        return HttpStatus.OK;
    }



}
