package it.epicode.dao;

import it.epicode.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object per l'entità Location
 * Gestisce le operazioni CRUD per le location
 */
public class LocationDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione-eventi-pu");
    
    /**
     * Salva una nuova location nel database
     * @param location la location da salvare
     * @return la location salvata con l'ID generato
     */
    public Location save(Location location) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return location;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio della location", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova una location per ID
     * @param id l'ID della location
     * @return la location trovata o null se non esiste
     */
    public Location findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova tutte le location
     * @return lista di tutte le location
     */
    public List<Location> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Aggiorna una location esistente
     * @param location la location da aggiornare
     * @return la location aggiornata
     */
    public Location update(Location location) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Location updatedLocation = em.merge(location);
            em.getTransaction().commit();
            return updatedLocation;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della location", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina una location per ID
     * @param id l'ID della location da eliminare
     */
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Location location = em.find(Location.class, id);
            if (location != null) {
                em.remove(location);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione della location", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova location per nome (ricerca parziale)
     * @param nome il nome da cercare
     * @return lista delle location che contengono il nome specificato
     */
    public List<Location> findByNome(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Location> query = em.createQuery(
                "SELECT l FROM Location l WHERE l.nome LIKE :nome", Location.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova location per città
     * @param citta la città da cercare
     * @return lista delle location nella città specificata
     */
    public List<Location> findByCitta(String citta) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Location> query = em.createQuery(
                "SELECT l FROM Location l WHERE l.citta LIKE :citta", Location.class);
            query.setParameter("citta", "%" + citta + "%");
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