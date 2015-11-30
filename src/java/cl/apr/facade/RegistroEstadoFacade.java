/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.RegistroEstado;
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
public class RegistroEstadoFacade extends AbstractFacade<RegistroEstado> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroEstadoFacade() {
        super(RegistroEstado.class);
    }
    public List<RegistroEstado> getRegistroEstadoPorPeriodo(int idPeriodo) {
         Query query = em.createQuery(""
                                        + "SELECT DISTINCT a FROM RegistroEstado a WHERE a.periodo.idPeriodo :Periodo ", RegistroEstado.class);
         query.setParameter("Periodo",idPeriodo);
         return query.getResultList();
       
    }
}
