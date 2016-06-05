/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.CobroCuota;
import cl.apr.entity.RegistroCobro;
import java.math.BigInteger;
import java.util.Date;
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
public class RegistroCobroFacade extends AbstractFacade<RegistroCobro> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroCobroFacade() {
        super(RegistroCobro.class);
    }
    
     public BigInteger getNextIdRegistroCobro() {
         BigInteger result = null; 
         result = (BigInteger) em.createNativeQuery("select nextval('registro_cobro_id_registro_cobro_seq') ").getSingleResult();
        return result;
    }
     
      public boolean guardarRegistroCobrosmasCuotas(RegistroCobro reg, List<CobroCuota> cuotas) {
          try{ 
              System.out.println("reg.getIdRegistroCobro(): "+reg.getIdRegistroCobro());
             //reg.setCobroCuotaList(cuotas);
             em.merge(reg);             
             //em.flush();
            
            // em.refresh(reg);
              for (CobroCuota cobroCuota :cuotas) {
                  System.out.println("cobroCuota.getCobroCuotaPK().getIdRegistroCobro(): "+cobroCuota.getCobroCuotaPK().getIdRegistroCobro());
                  System.out.println(cobroCuota.getValorCuota());
                  em.merge(cobroCuota);
                    
              }
             //em.flush();
             //em.merge(reg.getCobroCuotaList());
             /*
            BigInteger idRegistroCobro  = (BigInteger) em.createNativeQuery("select nextval('registro_cobro_id_registro_cobro_seq') ").getSingleResult();
            if(idRegistroCobro !=null){
                reg.setIdRegistroCobro(idRegistroCobro.intValue());
                em.persist(reg);
                for (CobroCuota cobroCuota :cuotas) {
                    cobroCuota.getCobroCuotaPK().setIdRegistroCobro(idRegistroCobro.intValue());
                    em.persist(cobroCuota);
                }
                em.flush();
              
            }
                     */
          }catch(Exception e){
              e.printStackTrace();
              return false;
          }
        return true;
    }
      
    public List<RegistroCobro> findByRange(Date fechaInicio,Date fechaFin) {
        // return this.findAll();
          Query query = em.createQuery(""
                                        + "SELECT  r FROM RegistroCobro r  where r.fechaCreacion >= :fechaInicio and r.fechaCreacion <= :fechaFin order by r.fechaCreacion DESC", RegistroCobro.class);
        
          query.setParameter("fechaInicio",fechaInicio);
          query.setParameter("fechaFin",fechaFin);
          return query.getResultList();
    }
   
}
