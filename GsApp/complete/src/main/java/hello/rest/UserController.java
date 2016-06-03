package hello.rest;

import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import hello.businessLogic.BeanManager;
import hello.businessLogic.CustomManager;
import hello.dto.UserDTO;
import hello.dto.LoginUser;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;
import hello.util.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public HttpStatus signin(@RequestBody LoginUser loginTry) {

        BeanManager<Users> bm1 = new BeanManager<>("schema/Users.xsd");
        /*citamo sve usere iz baze*/
        Users users = bm1.read("/test17/coveci.xml");
        for(User user : users.getUser()){
            if(loginTry.getPassword().equals(user.getPassword())
                    && loginTry.getUsername().equals(user.getUsername())){
                
            }
        }


        /*
        *//*SIMULATION*//*
        logger.info("REST request for signing in");
        UserDTO user=new UserDTO("usernameTEST", Role.ROLE_GRADJANIN);  //this is user that is found in database
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("Returning user : " + user);
        */

        return HttpStatus.OK;
    }



    @RequestMapping(value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@RequestBody User userTry) {

        //TODO validate userTry
        BeanManager<Users> bm1 = new BeanManager<>("schema/Users.xsd");
        /*citamo sve usere iz baze*/
        Users users = bm1.read("/test17/coveci.xml");
        /*dodamo novog usera*/
        users.getUser().add(userTry);
        /*upisemo ceo doc nazad*/
        //TODO hashing pass
        bm1.write(users, "/test17/coveci.xml", "Proba");

        return new ResponseEntity("SVE KUL", HttpStatus.OK);
    }



    @RequestMapping(value = "/authenticate")
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
