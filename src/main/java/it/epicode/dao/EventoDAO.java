package it.epicode.dao;

import it.epicode.entities.Evento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object per l'entità Evento
 * Gestisce le operazioni CRUD per gli eventi
 */
public class EventoDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione-eventi-pu");
    
    /**
     * Salva un nuovo evento nel database
     * @param evento l'evento da salvare
     * @return l'evento salvato con l'ID generato
     */
    public Evento save(Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
            return evento;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio dell'evento", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova un evento per ID
     * @param id l'ID dell'evento
     * @return l'evento trovato o null se non esiste
     */
    public Evento findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova tutti gli eventi
     * @return lista di tutti gli eventi
     */
    public List<Evento> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Evento> query = em.createQuery("SELECT e FROM Evento e", Evento.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Aggiorna un evento esistente
     * @param evento l'evento da aggiornare
     * @return l'evento aggiornato
     */
    public Evento update(Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Evento updatedEvento = em.merge(evento);
            em.getTransaction().commit();
            return updatedEvento;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento dell'evento", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina un evento per ID
     * @param id l'ID dell'evento da eliminare
     */
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Evento evento = em.find(Evento.class, id);
            if (evento != null) {
                em.remove(evento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione dell'evento", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova eventi per titolo (ricerca parziale)
     * @param titolo il titolo da cercare
     * @return lista degli eventi che contengono il titolo specificato
     */
    public List<Evento> findByTitolo(String titolo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Evento> query = em.createQuery(
                "SELECT e FROM Evento e WHERE e.titolo LIKE :titolo", Evento.class);
            query.setParameter("titolo", "%" + titolo + "%");
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