/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.beans.ItemReporte;
import cl.apr.beans.RegistroEstadoReporte;
import cl.apr.entity.Periodo;
import cl.apr.entity.RegistroEstado;
import java.sql.ResultSet;
import java.util.ArrayList;
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
                                        + "SELECT DISTINCT a FROM RegistroEstado a WHERE a.periodo.idPeriodo = :idPeriodo ORDER BY a.cuenta.idCuenta", RegistroEstado.class);
         query.setParameter("idPeriodo",idPeriodo);
         return query.getResultList();
       
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RegistroEstadoReporte> reporteLibroRegistroEstado(int periodo) {
        
        List<RegistroEstadoReporte> itemReporte=new ArrayList<RegistroEstadoReporte>();
        try{
            if(em.isOpen()){
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("fn_reporte_libro_registro_estado");
            storedProcedure.registerStoredProcedureParameter(1,ResultSet.class, ParameterMode.REF_CURSOR);
            storedProcedure.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
            
            storedProcedure.setParameter(2,periodo);
            storedProcedure.execute();
            @SuppressWarnings("unchecked")
                List result=storedProcedure.getResultList();
            @SuppressWarnings("rawtypes")
                Iterator it = result.iterator( );
                RegistroEstadoReporte irep;
                while (it.hasNext( )) {
                    Object[] resulta = (Object[])it.next();
                    irep = new RegistroEstadoReporte();
                    irep.setCuenta((String) resulta[0]);
                    irep.setPeriodo((String) resulta[1]);
                    irep.setEstadoAnterior((Integer) resulta[2]);
                    irep.setEstadoActual((Integer) resulta[3]);                    
                    irep.setMetroCubico((Integer) resulta[4]);
                itemReporte.add(irep);   
                }
            
            }
        return itemReporte;
        } catch(Exception e){
             e.printStackTrace();;
        }
        return null;
    }
    

}
