/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.DetalleAvisoCobro;
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
public class DetalleAvisoCobroFacade extends AbstractFacade<DetalleAvisoCobro> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleAvisoCobroFacade() {
        super(DetalleAvisoCobro.class);
    }

    public List<DetalleAvisoCobro> getDetalleAvisoCobroDisponibles(Integer idCuenta) {    
        List<DetalleAvisoCobro> result = new ArrayList<DetalleAvisoCobro>();
        try{
             Query query1 = em.createQuery(""
                                        + "SELECT v.avisoCobroPK.idPeriodo FROM AvisoCobro v WHERE v.avisoCobroPK.idCuenta=:idCuenta order by v.fechaCreacion desc ", Integer.class);
            query1.setMaxResults(1);
            query1.setParameter("idCuenta",idCuenta);
            Integer idPeriodo =  (Integer) query1.getSingleResult(); 
            if(idPeriodo != null){
                Query query = em.createQuery(""
                                                + "SELECT m FROM DetalleAvisoCobro m WHERE m.avisoCobro.avisoCobroPK.idCuenta=:idCuenta  and m.avisoCobro.avisoCobroPK.idPeriodo=:idPeriodo and m.pagado=FALSE", DetalleAvisoCobro.class);
                 query.setParameter("idCuenta",idCuenta);
                 query.setParameter("idPeriodo",idPeriodo);
                 result = query.getResultList(); 
            }
        }catch(Exception e){
            
        }
        
       return result;
    }
    /*
     public Integer getLastIdPeriodo(int idCuenta) { 
         
         
            Query query = em.createQuery(""
                                        + "SELECT v.avisoCobroPK.idPeriodo FROM AvisoCobro v WHERE v.avisoCobroPK.idCuenta=:idCuenta order by v.fechaCreacion desc ", Integer.class);
            query.setMaxResults(1);
            query.setParameter("idCuenta",idCuenta);
            return (Integer) query.getSingleResult();  
//            try{
//            Query query = em.createQuery(""
//                                        + "SELECT p.idPeriodo FROM Periodo p order by p.idPeriodo desc ", Integer.class);
//            query.setMaxResults(1);
//            return (Integer) query.getSingleResult();
//        }catch(Exception e){
//           return null; 
//        }    
    }
    */
}
