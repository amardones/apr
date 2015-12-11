/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.facade;

import cl.apr.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author hmardones
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "AguaPotablePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }   
    
    public Usuario getByCodigoUsuario(Session session, Integer id_usuario) throws Exception {
        return (Usuario) session.get(Usuario.class, id_usuario);
    }
    public Usuario getByCorreoElectronicoDiferent(Session session, String id_usuario ,String mail)throws Exception
    {
        Query query = em.createQuery("from usuario where id_usuario!=:id_usuario and mail=:mail", Usuario.class);
        query.setParameter("id_usuario", id_usuario);
        query.setParameter("mail", mail);        
        Usuario Usuario=(Usuario) query.getSingleResult();        
        return Usuario;
    }
    
  
    public Usuario getByCorreoElectronico(String mail) throws Exception { 
        Query query = em.createQuery(""
                                        + "SELECT DISTINCT a FROM Usuario a WHERE a.mail  = :mail", Usuario.class);
        query.setParameter("mail", mail);
         return (Usuario) query.getSingleResult();
    }
    
     public boolean register(Session session, Usuario Usuario) throws Exception {
        session.save(Usuario);        
        return true;
    }
}
