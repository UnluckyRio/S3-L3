package it.epicode.dao;

import it.epicode.entities.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object per l'entità Persona
 * Gestisce le operazioni CRUD per le persone
 */
public class PersonaDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione-eventi-pu");
    
    /**
     * Salva una nuova persona nel database
     * @param persona la persona da salvare
     * @return la persona salvata con l'ID generato
     */
    public Persona save(Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            return persona;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio della persona", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova una persona per ID
     * @param id l'ID della persona
     * @return la persona trovata o null se non esiste
     */
    public Persona findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova tutte le persone
     * @return lista di tutte le persone
     */
    public List<Persona> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p", Persona.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Aggiorna una persona esistente
     * @param persona la persona da aggiornare
     * @return la persona aggiornata
     */
    public Persona update(Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona updatedPersona = em.merge(persona);
            em.getTransaction().commit();
            return updatedPersona;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della persona", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina una persona per ID
     * @param id l'ID della persona da eliminare
     */
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona persona = em.find(Persona.class, id);
            if (persona != null) {
                em.remove(persona);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione della persona", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova una persona per email
     * @param email l'email della persona
     * @return la persona trovata o null se non esiste
     */
    public Persona findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                "SELECT p FROM Persona p WHERE p.email = :email", Persona.class);
            query.setParameter("email", email);
            List<Persona> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova persone per nome e cognome (ricerca parziale)
     * @param nome il nome da cercare
     * @param cognome il cognome da cercare
     * @return lista delle persone che corrispondono ai criteri
     */
    public List<Persona> findByNomeCognome(String nome, String cognome) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                "SELECT p FROM Persona p WHERE p.nome LIKE :nome AND p.cognome LIKE :cognome", 
                Persona.class);
            query.setParameter("nome", "%" + nome + "%");
            query.setParameter("cognome", "%" + cognome + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Chiude l'EntityManagerFactory
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}