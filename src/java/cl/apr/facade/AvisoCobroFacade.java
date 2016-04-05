/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.beans.BarChartItem;
import cl.apr.beans.ItemReporte;
import cl.apr.entity.AvisoCobro;
import cl.apr.entity.Medidor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
                storedProcedure.setParameter("id_periodo", periodo);
                storedProcedure.setParameter("id_cuenta", idCuenta);
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
     
      @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public List<BarChartItem> obtenerRegistroEstadoHistoricos(Integer idCuenta, Integer idPeriodo){
         
       List<BarChartItem> barChartItems = new ArrayList<BarChartItem>();
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_obtener_estados_historicos");
                // set parameters
                storedProcedure.registerStoredProcedureParameter(1,ResultSet.class, ParameterMode.REF_CURSOR);
                storedProcedure.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
              
                storedProcedure.setParameter(2,  idCuenta);
                storedProcedure.setParameter(3,  idPeriodo);
                
 
                // execute SP
                storedProcedure.execute();
                // get result
                @SuppressWarnings("unchecked")
                List result=storedProcedure.getResultList();
                //ItemReporte userRecords = new ItemReporte();
                @SuppressWarnings("rawtypes")
                Iterator it = result.iterator( );
                BarChartItem bcItem;
                while (it.hasNext( )) {
                    Object[] resulta = (Object[])it.next(); // Iterating through array object 

                bcItem = new BarChartItem();
                bcItem.setNombre((String) resulta[1]);
                bcItem.setValor((Integer) resulta[2]);
               
                barChartItems.add(bcItem);
                
                }   

           }
       } catch(Exception e){
             e.printStackTrace();
        }

       return barChartItems;
    }
     
     public List<AvisoCobro> getAvisosPorPeriodo(int idPeriodo) {
         Query query = em.createQuery(""
                                        + "SELECT DISTINCT a FROM AvisoCobro a WHERE a.registroEstado.periodo.idPeriodo = :idPeriodo order by  a.avisoCobroPK.idCuenta", AvisoCobro.class);
         query.setParameter("idPeriodo",idPeriodo);
         return query.getResultList();
       
    }

     
     public AvisoCobro getAviso(int idPeriodo, int idCuenta) {
         Query query = em.createQuery(""
                                        + "SELECT DISTINCT a FROM AvisoCobro a WHERE a.registroEstado.periodo.idPeriodo  = :idPeriodo AND a.registroEstado.cuenta.idCuenta  = :idCuenta ", AvisoCobro.class);
         query.setParameter("idPeriodo",idPeriodo);
         query.setParameter("idCuenta",idCuenta);
         return (AvisoCobro) query.getSingleResult();
    }

    /* 
//buscar los aviso que no han sido pagados
    public List<AvisoCobro> avisodeCobroDisponibles() {
         Query query = em.createQuery(""
                                        + "SELECT m FROM Boleta c RIGHT JOIN c.avisoCobro m WHERE c.idBoleta IS NULL ", AvisoCobro.class);        
         return query.getResultList();
    }

    public List<AvisoCobro> getavisoDeCobroDisponiblesEditar(Integer idCuenta, Integer idPeriodo) {
        Query query = em.createQuery(""
                                        + "SELECT DISTINCT m FROM Boleta c RIGHT JOIN c.avisoCobro m WHERE c.idBoleta IS NULL OR c.avisoCobro = :idPeriodo", AvisoCobro.class);
         query.setParameter("idPeriodo",idPeriodo);
         query.setParameter("idCuenta",idCuenta);
         return query.getResultList();
    }
*/ 
}
