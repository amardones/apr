/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.TipoMovimiento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hmardones
 */
@Stateless
public class TipoMovimientoFacade extends AbstractFacade<TipoMovimiento> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoMovimientoFacade() {
        super(TipoMovimiento.class);
    }
    
}
