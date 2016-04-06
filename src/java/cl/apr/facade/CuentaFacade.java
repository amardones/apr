/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.Cuenta;
import cl.apr.entity.Medidor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hmardones
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }
    
    public boolean permiteRecalcular(Integer idPeriodo, Integer idCuenta) {        
     
         Query query = em.createQuery("select count(dac) from DetalleAvisoCobro dac INNER JOIN dac.avisoCobro a where a.avisoCobroPK.idPeriodo = :idPeriodo and a.avisoCobroPK.idCuenta = :idCuenta AND dac.pagado = true ");
    
            query.setMaxResults(1);
            query.setParameter("idPeriodo",idPeriodo);
            query.setParameter("idCuenta",idCuenta);
            Long r  =(Long) query.getSingleResult();
            //System.out.println("Count:"+r);
            return r == 0;       
     }
    
}
