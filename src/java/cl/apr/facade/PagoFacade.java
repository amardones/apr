/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.beans.ItemReporte;
import cl.apr.beans.SubsidioReporte;
import cl.apr.entity.Cuenta;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.Pago;
import cl.apr.entity.PagoTipoCobro;
import cl.apr.entity.Periodo;
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
                storedProcedure.registerStoredProcedureParameter(2, java.sql.Date.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter(3, java.sql.Date.class, ParameterMode.IN);
                
                storedProcedure.setParameter(2,  new java.sql.Date(fechaInicio.getTime()));
                storedProcedure.setParameter(3,  new java.sql.Date(fechaFin.getTime()));
                // execute SP
                storedProcedure.execute();
                // get result
                @SuppressWarnings("unchecked")
                List result=storedProcedure.getResultList();
                //ItemReporte userRecords = new ItemReporte();
                @SuppressWarnings("rawtypes")
                Iterator it = result.iterator( );
                ItemReporte irep;
                while (it.hasNext( )) {
                    Object[] resulta = (Object[])it.next(); // Iterating through array object 
//                    userRecords.se= result[0];
//                    //userRecords.add(new ItemReporte(result[0], result[1], result[2], result[3], result[4], result[5], result[6], result[7]));               
                    
                    System.out.println("DOC "+(String) resulta[1]+"-Cuenta "+(Integer) resulta[3]+":"+(String) resulta[4]+" "+(String) resulta[5]);
                    System.out.print("");
                    //userRecords.setDetalle("DOC"+(String) resulta[1]+"-"+(Integer) resulta[3]+":"+(String) resulta[4]+" "+(String) resulta[5]);
                irep = new ItemReporte();
                irep.setIdPago((Integer) resulta[0]);
                irep.setNumeroDocumento((String) resulta[1]);
                irep.setFechaCreacion((String) resulta[2]);
                irep.setCuenta((Integer) resulta[3]+":"+(String) resulta[4]+" "+(String) resulta[5]);
                
                irep.setConsumoAgua((Integer) resulta[6]);
                irep.setCuotaSocial((Integer) resulta[7]);
                irep.setInteres((Integer) resulta[8]);
                irep.setMultas((Integer) resulta[9]);
                irep.setCorteReposicion((Integer) resulta[10]);
                irep.setDerechoIncorporacion((Integer) resulta[11]);
                irep.setOtrosCobros((Integer) resulta[12]);
                irep.setTotalItem(irep.getConsumoAgua()
                                 +irep.getCuotaSocial()
                                 +irep.getInteres()
                                 +irep.getMultas()
                                 +irep.getCorteReposicion()
                                 +irep.getDerechoIncorporacion()   
                                 +irep.getOtrosCobros());
                itemReporte.add(irep);
                
                }   
                
             return itemReporte;
           }
           
       } catch(Exception e){
             e.printStackTrace();;
        }
        return null;
    }
     
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public Integer obtenerInteres(int idCuenta,Date fecha){
         
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_calcular_interes");
                // set parameters
                storedProcedure.registerStoredProcedureParameter(1,Integer.class, ParameterMode.OUT);
                storedProcedure.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter(3, Date.class, ParameterMode.IN);
                
                storedProcedure.setParameter(2, idCuenta);
                storedProcedure.setParameter(3, fecha);
                // execute SP
                storedProcedure.execute();
                // get result
                Integer result = (Integer)storedProcedure.getFirstResult();
                
                System.out.println("result is: " + result); 
                
             return result;
           }
           
       } catch(Exception e){
             e.printStackTrace();;
        }
        return null;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SubsidioReporte> reporteLibroSubsidio(Date fechaInicio, Date fechaFin) {
            List<SubsidioReporte> itemReporte=new ArrayList<SubsidioReporte>();
       try{
           if(em.isOpen()){
                //em.getTransaction().begin();
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_reporte_libro_subsidio");
                // set parameters
                storedProcedure.registerStoredProcedureParameter(1,ResultSet.class, ParameterMode.REF_CURSOR);
                storedProcedure.registerStoredProcedureParameter(2, java.sql.Date.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter(3, java.sql.Date.class, ParameterMode.IN);
                
                storedProcedure.setParameter(2,  new java.sql.Date(fechaInicio.getTime()));
                storedProcedure.setParameter(3,  new java.sql.Date(fechaFin.getTime()));
                // execute SP
                storedProcedure.execute();
                // get result
                @SuppressWarnings("unchecked")
                List result=storedProcedure.getResultList();
                //ItemReporte userRecords = new ItemReporte();
                @SuppressWarnings("rawtypes")
                Iterator it = result.iterator( );
                SubsidioReporte irep;
                while (it.hasNext( )) {
                    Object[] resulta = (Object[])it.next(); // Iterating through array object 
//                    userRecords.se= result[0];
//                    //userRecords.add(new ItemReporte(result[0], result[1], result[2], result[3], result[4], result[5], result[6], result[7]));               
                    
                    //System.out.println("Cuenta"+(Integer) resulta[0]+"-periodo "+(Integer) resulta[1]+"-fecha"+(String) resulta[2]+"descuento"+(Integer) resulta[3]);
                    System.out.print("");
                irep = new SubsidioReporte();
                int idC=(Integer) resulta[0];
                int idP=(Integer) resulta[1];
                
                irep.setIdcuenta(Integer.toString(idC));
                irep.setIdPeriodo(Integer.toString(idP));
                irep.setFechaCreacion((String) resulta[2]);
                irep.setDescuento_periodo((Integer) resulta[3]);
                
                itemReporte.add(irep);
                
                }   
                
             return itemReporte;
           }
           
       } catch(Exception e){
             e.printStackTrace();;
        }
        return null;
    }
    
    
}
