/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.Medidor;
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
public class MedidorFacade extends AbstractFacade<Medidor> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedidorFacade() {
        super(Medidor.class);
    }
    
    public List<Medidor> getMedidoresDisponibles() {
       /*
         javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Medidor.class));
        return getEntityManager().createQuery(cq).getResultList();
        */
         //NOT IN (SELECT numero_medidor FROM cuenta 
         //SELECT numero_medidor, descripcion FROM Medidor WHERE numero_medidor NOT IN (SELECT numero_medidor FROM cuenta )
         //TypedQuery<Medidor> query = em.createQuery(" SELECT m FROM Medidor m WHERE m.numeroMedidor = :numeroMedidor" , Medidor.class);
         Query query = em.createQuery(""
                                        + "SELECT m FROM Cuenta c RIGHT JOIN c.numeroMedidor m WHERE c.idCuenta IS NULL ", Medidor.class);
         //query.setParameter("numeroMedidor","00001");
         return query.getResultList();
       
    }
}
