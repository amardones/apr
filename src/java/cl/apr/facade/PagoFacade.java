/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.beans.ItemReporte;
import cl.apr.entity.Cuenta;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.Pago;
import java.math.BigDecimal;
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
public class PagoFacade extends AbstractFacade<Pago> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;
    private int control=0;

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
    
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public List<ItemReporte> reporteLibroIngreso(Date fechaInicio,Date fechaFin){
         List<ItemReporte> itemReporte=new ArrayList<ItemReporte>();
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_reporte_libro_ingresos");
                // set parameters
                storedProcedure.registerStoredProcedureParameter(1,ResultSet.class, ParameterMode.REF_CURSOR);
                storedProcedure.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
                
                storedProcedure.setParameter(2, 1);
                storedProcedure.setParameter(3, 1);
                // execute SP
                storedProcedure.execute();
                // get result
                @SuppressWarnings("unchecked")
                List result=storedProcedure.getResultList();
                ItemReporte userRecords = new ItemReporte();
                @SuppressWarnings("rawtypes")
                Iterator it = result.iterator( );
                
                while (it.hasNext( )) {
                    Object[] resulta = (Object[])it.next(); // Iterating through array object 
//                    userRecords.se= result[0];
//                    //userRecords.add(new ItemReporte(result[0], result[1], result[2], result[3], result[4], result[5], result[6], result[7]));               
                    
                    System.out.println("DOC "+(String) resulta[1]+"-Cuenta "+(Integer) resulta[3]+":"+(String) resulta[4]+" "+(String) resulta[5]);
                    
                    //userRecords.setDetalle("DOC"+(String) resulta[1]+"-"+(Integer) resulta[3]+":"+(String) resulta[4]+" "+(String) resulta[5]);
                    }   
                
             return itemReporte;
           }
           
       } catch(Exception e){
             e.printStackTrace();;
        }
        return null;
    }
    
    
}
