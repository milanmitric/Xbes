package hello.businessLogic;

import hello.entity.*;

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
        BeanManager<TAkt> aktManager = new BeanManager<>();
        container.put(TAkt.class,aktManager);
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
        BeanManager<TPotpisnik> potpisnikBeanManager = new BeanManager<>();
        container.put(TPotpisnik.class,potpisnikBeanManager);
        BeanManager<TReferenca> referencaBeanManager = new BeanManager<>();
        container.put(TReferenca.class,referencaBeanManager);
        BeanManager<TSemiStruktuiraniTekst> tSemiStruktuiraniTekstBeanManager = new BeanManager<>();
        container.put(TSemiStruktuiraniTekst.class,tSemiStruktuiraniTekstBeanManager);
        BeanManager<TStav> tStavBeanManager = new BeanManager<>();
        container.put(TStav.class,tStavBeanManager);
        BeanManager<TTacka> tTackaBeanManager = new BeanManager<>();
        container.put(TTacka.class,tTackaBeanManager);
        BeanManager<TZavrsneOdredbe> tZavrsneOdredbeBeanManager = new BeanManager<>();
        container.put(TZavrsneOdredbe.class,tZavrsneOdredbeBeanManager);
    }

    public  BeanManager<?> getContainerManager(Class T){
        if (container == null){
            initializeBeans();
        }
        return container.get(T);
    }


}
