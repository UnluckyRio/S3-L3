package it.epicode.dao;

import it.epicode.entities.Partecipazione;
import it.epicode.entities.Persona;
import it.epicode.entities.Evento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object per l'entità Partecipazione
 * Gestisce le operazioni CRUD per le partecipazioni
 */
public class PartecipazioneDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione-eventi-pu");
    
    /**
     * Salva una nuova partecipazione nel database
     * @param partecipazione la partecipazione da salvare
     * @return la partecipazione salvata con l'ID generato
     */
    public Partecipazione save(Partecipazione partecipazione) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(partecipazione);
            em.getTransaction().commit();
            return partecipazione;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio della partecipazione", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova una partecipazione per ID
     * @param id l'ID della partecipazione
     * @return la partecipazione trovata o null se non esiste
     */
    public Partecipazione findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Partecipazione.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova tutte le partecipazioni
     * @return lista di tutte le partecipazioni
     */
    public List<Partecipazione> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partecipazione> query = em.createQuery("SELECT p FROM Partecipazione p", Partecipazione.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Aggiorna una partecipazione esistente
     * @param partecipazione la partecipazione da aggiornare
     * @return la partecipazione aggiornata
     */
    public Partecipazione update(Partecipazione partecipazione) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Partecipazione updatedPartecipazione = em.merge(partecipazione);
            em.getTransaction().commit();
            return updatedPartecipazione;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della partecipazione", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina una partecipazione per ID
     * @param id l'ID della partecipazione da eliminare
     */
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Partecipazione partecipazione = em.find(Partecipazione.class, id);
            if (partecipazione != null) {
                em.remove(partecipazione);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione della partecipazione", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova partecipazioni per persona
     * @param persona la persona di cui cercare le partecipazioni
     * @return lista delle partecipazioni della persona
     */
    public List<Partecipazione> findByPersona(Persona persona) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partecipazione> query = em.createQuery(
                "SELECT p FROM Partecipazione p WHERE p.persona = :persona", Partecipazione.class);
            query.setParameter("persona", persona);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova partecipazioni per evento
     * @param evento l'evento di cui cercare le partecipazioni
     * @return lista delle partecipazioni all'evento
     */
    public List<Partecipazione> findByEvento(Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partecipazione> query = em.createQuery(
                "SELECT p FROM Partecipazione p WHERE p.evento = :evento", Partecipazione.class);
            query.setParameter("evento", evento);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Trova partecipazioni per stato
     * @param stato lo stato delle partecipazioni da cercare
     * @return lista delle partecipazioni con lo stato specificato
     */
    public List<Partecipazione> findByStato(Partecipazione.Stato stato) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partecipazione> query = em.createQuery(
                "SELECT p FROM Partecipazione p WHERE p.stato = :stato", Partecipazione.class);
            query.setParameter("stato", stato);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Verifica se una persona è già iscritta a un evento
     * @param persona la persona da verificare
     * @param evento l'evento da verificare
     * @return true se la persona è già iscritta, false altrimenti
     */
    public boolean isPersonaIscritta(Persona persona, Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Partecipazione p WHERE p.persona = :persona AND p.evento = :evento", 
                Long.class);
            query.setParameter("persona", persona);
            query.setParameter("evento", evento);
            return query.getSingleResult() > 0;
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