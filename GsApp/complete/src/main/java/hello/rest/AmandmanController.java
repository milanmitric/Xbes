package hello.rest;

import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nebojsa on 6/5/2016.
 */
@RestController
@RequestMapping("/api")
public class AmandmanController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AmandmanManager amandmanManager = new AmandmanManager();
    private AktManager aktManager=new AktManager();


    @RequestMapping(value = "/getallamandmans",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAmandmans() {
        logger.info("Called REST for getting all amandments");
        ArrayList<Amandmani> amandmani = amandmanManager.getAllAmendmentsApproved();
        return new ResponseEntity(amandmani, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyallamandmans",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyAllAmandmans() {
        logger.info("Called REST for getting all amandments by a person: ");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();


        ArrayList<Amandmani> amandmanis = amandmanManager.getAllAmendmentProposed();
        System.out.println("ALL: "+amandmanis.size());
        ArrayList<Amandmani> amandmanis2 = amandmanManager.getAllAmendmentsApproved();
        System.out.println("ALL: "+amandmanis2.size());
        for(int i=amandmanis.size()-1; i>=0; i--){
            if(!amandmanis.get(i).getUserName().equals(user.getUsername())){
                amandmanis.remove(i);
            }
        }
        for(int i=amandmanis2.size()-1; i>=0; i--){
            if(!amandmanis2.get(i).getUserName().equals(user.getUsername())){
                amandmanis2.remove(i);
            }
        }
        System.out.println("MY: "+amandmanis.size());
        System.out.println("MY: "+amandmanis2.size());


        HashMap<String,ArrayList<Amandmani>> returnTwoLists = new HashMap<String,ArrayList<Amandmani>>();
        returnTwoLists.put("proposed",amandmanis);
        returnTwoLists.put("approved",amandmanis2);



        return new ResponseEntity(returnTwoLists, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/amandman",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity postAmandman(@RequestBody String amandmanString) {
        logger.info("Called REST for proposing an amandment");
        logger.info("Content of an amandment: " + amandmanString);
        try (PrintWriter pw = new PrintWriter("tmp.xml") ) {
            pw.println(amandmanString);
        } catch (FileNotFoundException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Amandmani amandmani = amandmanManager.convertFromXml(new File("tmp.xml"));
        if(!amandmanManager.validateAmandman(amandmani)){
            logger.info("ERROR: Not validated!");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        String docID = amandmanManager.proposeAmandman(amandmani,user);
        if(docID==null){
            logger.info("ERROR: Not proposed!");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Successfully proposed amandment: " + amandmani.toString() + " with id " + docID);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping(value = "/getamandmantsforakt",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAmandmentsForAkt(@RequestParam("docID") String docID) {
        logger.info("Called REST for getting amandments refering to act with id: " + docID);
        ArrayList<Amandmani> amandmens = amandmanManager.getAllAmandmansForAkt(docID);
        System.out.print(amandmens.size());
        return new ResponseEntity(amandmens, HttpStatus.OK);
    }
    /*opozivanje amandmana*/

    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/opozoviamandman",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity opozoviPredlogAmandmana(@RequestBody String data){
        logger.info("Called REST for accepting or rejecting  amandments");

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean mojAmandman  = false;
        data = data.substring(0, data.length()-1);
        for(Amandmani amandman: amandmanManager.getMyAmandmentsProposed(user)){
            if(amandman.getDocumentId().equals(data)){
                mojAmandman=true;
            }
        }

        logger.info("User " + user.getUsername() + " tries to delete amandman " + data);
        if(mojAmandman){
            if(amandmanManager.deleteAmandman(data)){
                return new ResponseEntity(HttpStatus.OK);
            }
        }


        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}