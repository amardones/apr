/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.Periodo;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author hmardones
 */
@Stateless
public class PeriodoFacade extends AbstractFacade<Periodo> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoFacade() {
        super(Periodo.class);
    }
    
    public List<Periodo> getPeriodos() {
         Query query = em.createQuery(""
                                        + "SELECT p FROM Periodo p order by p.idPeriodo desc", Periodo.class);
        // query.setParameter("idPeriodo",idPeriodo);
         return query.getResultList();
       
    }
    public Periodo getLastPeriodo() {
        try{
            Query query = em.createQuery(""
                                        + "SELECT p FROM Periodo p order by p.idPeriodo desc ", Periodo.class);
            query.setMaxResults(1);
            // query.setParameter("idPeriodo",idPeriodo);
            return (Periodo) query.getSingleResult();
        }catch(Exception e){
           return null; 
        }    
    }
    
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public boolean crearRegistrosEstados(int periodo){
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_calcular_registro_estado_default");
                // set parameters
                storedProcedure.registerStoredProcedureParameter("id_periodo", Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter("result", Boolean.class, ParameterMode.OUT);
                storedProcedure.setParameter("id_periodo", periodo);
                // execute SP
                storedProcedure.execute();
                // get result
                Boolean result = (Boolean)storedProcedure.getOutputParameterValue("result");
                System.out.println("result is: " + result);
                return result;
              
           }
       } catch(Exception e){
             e.printStackTrace();;
        }
        return false;
    }
}
