/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.ValoresParametricos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
