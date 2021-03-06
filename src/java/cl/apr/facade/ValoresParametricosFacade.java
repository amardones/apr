/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.ValoresParametricos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hmardones
 */
@Stateless
public class ValoresParametricosFacade extends AbstractFacade<ValoresParametricos> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValoresParametricosFacade() {
        super(ValoresParametricos.class);
    }
    
    public ValoresParametricos getLastValoresParametricos() {
        try{
            Query query = em.createQuery(""
                                        + "SELECT v FROM ValoresParametricos v order by v.idValoresParametricos desc ", ValoresParametricos.class);
            query.setMaxResults(1);
            // query.setParameter("idPeriodo",idPeriodo);
            return (ValoresParametricos) query.getSingleResult();
        } 
        catch(Exception e){
            return null;
        }
       
    }
    
    public List<ValoresParametricos> getValoresParametricos() {
        try{
            Query query = em.createQuery(""
                                        + "SELECT v FROM ValoresParametricos v order by v.idValoresParametricos desc ", ValoresParametricos.class);
           // query.setMaxResults(1);
            // query.setParameter("idPeriodo",idPeriodo);
            return query.getResultList();
        } 
        catch(Exception e){
            return null;
        }
       
    }
    
     public long getCantidadAvisosCobro(int idValoresParametricos) {
        try{
            Query query = em.createQuery(""
                                        + "SELECT DISTINCT COUNT(ac) FROM AvisoCobro ac JOIN ac.registroEstado re JOIN re.periodo p JOIN p.idValoresParametricos vp WHERE vp.idValoresParametricos  = :idValoresParametricos ");
           //query.set;
            query.setParameter("idValoresParametricos",idValoresParametricos);
            return (Long)query.getSingleResult();
        } 
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
       
    }
    
     
    //
}
