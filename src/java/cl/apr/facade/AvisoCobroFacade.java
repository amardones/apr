/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.AvisoCobro;
import cl.apr.entity.Medidor;
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
public class AvisoCobroFacade extends AbstractFacade<AvisoCobro> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AvisoCobroFacade() {
        super(AvisoCobro.class);
    }
    
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public boolean crearAvisosDeCobro(int periodo, int idCuenta){
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_calcular_avisos_de_cobro");
                // set parameters
                storedProcedure.registerStoredProcedureParameter("id_periodo", Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter("id_cuenta", Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter("result", String.class, ParameterMode.OUT);
                storedProcedure.setParameter("id_periodo", 1);
                storedProcedure.setParameter("id_cuenta", -1);
                // execute SP
                storedProcedure.execute();
                // get result
                String result = (String)storedProcedure.getOutputParameterValue("result");
                System.out.println("result is: " + result);
                if(result.equalsIgnoreCase("OK")){
                     //em.getTransaction().commit();
                     //em.close();
                    // List<AvisoCobro> findAllActives = findAllActives();
                    // System.out.println(" findAllActives.size() is: " + findAllActives.size());
                    //em.getEntityManagerFactory().getCache().evictAll();
                     return true;
                }else{
                     //em.close();
                     return false;
                }
              
           }
       } catch(Exception e){
             e.printStackTrace();;
        }
        return false;
    }
     
      public List<AvisoCobro> getAvisosPorPeriodo(int idPeriodo) {
         Query query = em.createQuery(""
                                        + "SELECT DISTINCT a FROM AvisoCobro a WHERE a.periodo.idPeriodo  = :idPeriodo ", AvisoCobro.class);
         query.setParameter("idPeriodo",idPeriodo);
         return query.getResultList();
       
    }
    
}
