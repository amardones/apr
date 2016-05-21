/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.ValorTramoM3;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hmardones
 */
@Stateless
public class ValorTramoM3Facade extends AbstractFacade<ValorTramoM3> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValorTramoM3Facade() {
        super(ValorTramoM3.class);
    }
    
    public ValorTramoM3 getLastValorTramoM3() {
        try{
            Query query = em.createQuery(""
                                        + "SELECT v FROM ValorTramoM3 v order by v.idValorTramo desc ", ValorTramoM3.class);
            query.setMaxResults(1);
            // query.setParameter("idPeriodo",idPeriodo);
            return (ValorTramoM3) query.getSingleResult();
        } 
        catch(Exception e){
           // e.printStackTrace();
            return null;
        }
       
    }
}
