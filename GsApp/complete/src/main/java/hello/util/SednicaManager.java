package hello.util;

import hello.entity.Sednica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by aloha on 10-Jun-16.
 */
public class SednicaManager {

    /**
     * Get status of sednica
     * @return state of sednica
     * */
    public boolean getSednicaStatus(){

        Sednica sb=new Sednica();
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("sednica.ser");
            in = new ObjectInputStream(fis);
            sb = (Sednica) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.isItNow();
    }

    /**
     * Set status of sednica
     * @param status of Sednica
     * */
    public void updateSednica(boolean status){

        Sednica sb=new Sednica(status);
        String filename = "sednica.ser";
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try {
            fos = new FileOutputStream(filename);
            out= new ObjectOutputStream(fos);
            out.writeObject(sb);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
