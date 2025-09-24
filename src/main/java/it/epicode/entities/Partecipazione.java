package it.epicode.entities;

import jakarta.persistence.*;

/**
 * Entità JPA che rappresenta una Partecipazione nel sistema di gestione eventi
 * Gestisce la relazione Many-to-Many tra Persona ed Evento
 */
@Entity
@Table(name = "partecipazioni")
public class Partecipazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relazione Many-to-One con Persona
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;
    
    // Relazione Many-to-One con Evento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stato stato;
    
    // Enum per lo stato della partecipazione
    public enum Stato {
        CONFERMATA, DA_CONFERMARE
    }
    
    // Costruttori
    public Partecipazione() {}
    
    public Partecipazione(Persona persona, Evento evento, Stato stato) {
        this.persona = persona;
        this.evento = evento;
        this.stato = stato;
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Persona getPersona() {
        return persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public Evento getEvento() {
        return evento;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    public Stato getStato() {
        return stato;
    }
    
    public void setStato(Stato stato) {
        this.stato = stato;
    }
    
    @Override
    public String toString() {
        return "Partecipazione{" +
                "id=" + id +
                ", persona=" + (persona != null ? persona.getNome() + " " + persona.getCognome() : "null") +
                ", evento=" + (evento != null ? evento.getTitolo() : "null") +
                ", stato=" + stato +
                '}';
    }
}