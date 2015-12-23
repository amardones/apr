/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.Cuenta;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.Pago;
import java.util.ArrayList;
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
public class PagoFacade extends AbstractFacade<Pago> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }

//    public List<DetalleAvisoCobro> getDetalleAvisoCobroDisponibles(Integer idCuenta) {       
//       Query query = em.createQuery(""
//                                       + "SELECT m FROM DetalleAvisoCobro m WHERE m.avisoCobro.avisoCobroPK.idCuenta=:idCuenta  and m.avisoCobro.avisoCobroPK.idPeriodo=:idPeriodo ", DetalleAvisoCobro.class);
//        query.setParameter("idCuenta",'1');
//        query.setParameter("idPeriodo",'1');
//         return query.getResultList();
//      
//       
//    }
    public Integer getLastIdPeriodo(Integer idCuenta) {        
            Query query = em.createQuery(""
                                        + "SELECT v.avisoCobroPK.idPeriodo FROM AvisoCobro v WHERE v.avisoCobroPK.idCuenta=:idCuenta order by v.fechaCreacion desc ", Integer.class);
            query.setMaxResults(1);
            query.setParameter("idCuenta",idCuenta);
            return (Integer) query.getSingleResult();       
    }
    
    
}
