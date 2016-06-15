package hello.rest;

import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.entity.gov.gradskaskupstina.Akt;
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

import java.io.*;
import java.util.ArrayList;

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
        ArrayList<Amandmani> amandmani = amandmanManager.getAllAmendmentProposed();
        return new ResponseEntity(amandmani, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyallamandmans",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyAllAmandmans() {

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/amandman",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity postAmandman(@RequestBody String amandmanString) {
        logger.info("[CONTENT OF ADDED AMANDMAN]:" + amandmanString);
        try (PrintWriter pw = new PrintWriter("tmp.xml") ) {
            pw.println(amandmanString);
        } catch (FileNotFoundException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Amandmani amandmani = amandmanManager.convertFromXml(new File("tmp.xml"));
        if(!amandmanManager.validateAmandman(amandmani)){
            logger.info("[AmandmanController] ERROR: NOT VALIDATED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        String docID = amandmanManager.proposeAmandman(amandmani,user);
        if(docID==null){
            logger.info("[AmandmanController] ERROR: NOT PROPOSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("[AmandmanController] Successfully proposed amandman: " + amandmani.toString() + " with id " + docID);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping(value = "/getamandmantsforakt",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getamandmantsforakt(@RequestParam("docID") String docID) {
       // System.out.println("USATO, ID: "+docID);

        Akt a =aktManager.read(docID, false);
        //System.out.print(amandmens.size());
        ArrayList<Amandmani> amandmens = amandmanManager.getAllAmandmansForAkt(a);
        System.out.print(amandmens.size());
        return new ResponseEntity(amandmens, HttpStatus.OK);
    }
















}
