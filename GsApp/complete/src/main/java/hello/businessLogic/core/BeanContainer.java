package hello.businessLogic.core;

import hello.entity.gov.gradskaskupstina.*;
import hello.entity.gov.gradskaskupstina.pro.*;

import java.util.HashMap;

/**
 * Created by milan on 28.5.2016..
 * Container class for JAXB beans.
 */
public class BeanContainer {

    private HashMap<Class, BeanManager<?>> container = null;

    public BeanContainer(){
        initializeBeans();
    }

    private void initializeBeans(){
        container = new HashMap<Class, BeanManager<?>>();
        BeanManager<Akt> aktManager = new BeanManager<>();
        container.put(Akt.class,aktManager);
        BeanManager<Amandman> amandmanManager = new BeanManager<>();
        container.put(Amandman.class,amandmanManager);
        BeanManager<TAmandmani> amandmaniManager = new BeanManager<>();
        container.put(TAmandmani.class,amandmaniManager);
        BeanManager<TAmandman> tAmandmanManager = new BeanManager<>();
        container.put(TAmandman.class,tAmandmanManager);
        BeanManager<TClan> clanManager = new BeanManager<>();
        container.put(TClan.class,clanManager);
        BeanManager<TDeo> deoManager = new BeanManager<>();
        container.put(TDeo.class,deoManager);
        BeanManager<TGlava> glavaManager = new BeanManager<>();
        container.put(TGlava.class,glavaManager);
        BeanManager<TOdeljak> odeljakManager = new BeanManager<>();
        container.put(TOdeljak.class,odeljakManager);
        BeanManager<TPododeljak> pododeljakManager = new BeanManager<>();
        container.put(TPododeljak.class,pododeljakManager);
        BeanManager<TPotpisnici> potpisniciBeanManager = new BeanManager<>();
        container.put(TPotpisnici.class,potpisniciBeanManager);
        BeanManager<TPotpisnik> potpisnikBeanManager = new BeanManager<>();
        container.put(TPotpisnik.class,potpisnikBeanManager);
        BeanManager<TReferenca> referencaBeanManager = new BeanManager<>();
        container.put(TReferenca.class,referencaBeanManager);
        BeanManager<TSemiStruktuiraniTekst> semiStruktuiraniTekstBeanManager = new BeanManager<>();
        container.put(TSemiStruktuiraniTekst.class,semiStruktuiraniTekstBeanManager);
        BeanManager<TStav> stavBeanManager = new BeanManager<>();
        container.put(TStav.class,stavBeanManager);
        BeanManager<TTacka> tackaBeanManager = new BeanManager<>();
        container.put(TTacka.class,tackaBeanManager);
        BeanManager<TTipIzmene> tipIzmeneBeanManager = new BeanManager<>();
        container.put(TTipIzmene.class,tipIzmeneBeanManager);
        BeanManager<TZavrsniDeo> zavrsniDeoBeanManager = new BeanManager<>();
        container.put(TZavrsniDeo.class,zavrsniDeoBeanManager);
    }

    public  BeanManager<?> getContainerManager(Class T){
        if (container == null){
            initializeBeans();
        }
        return container.get(T);
    }


}
