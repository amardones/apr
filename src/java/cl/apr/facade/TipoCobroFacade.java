/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.AvisoCobro;
import cl.apr.entity.TipoCobro;
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
public class TipoCobroFacade extends AbstractFacade<TipoCobro> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoCobroFacade() {
        super(TipoCobro.class);
    }
    
       public List<TipoCobro> getTiposCobroRegistranCobro() {
         Query query = em.createQuery(""
                                        + "SELECT  tc FROM TipoCobro tc WHERE tc.aceptaRegistroCobro = true ", TipoCobro.class);
        // query.setParameter("idPeriodo",idPeriodo);
         return query.getResultList();
       
    }
}
