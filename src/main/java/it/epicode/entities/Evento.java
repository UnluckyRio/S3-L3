package it.epicode.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità JPA che rappresenta un Evento nel sistema di gestione eventi
 */
@Entity
@Table(name = "eventi")
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titolo;
    
    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;
    
    @Column(columnDefinition = "TEXT")
    private String descrizione;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipoEvento;
    
    @Column(name = "numero_massimo_partecipanti", nullable = false)
    private Integer numeroMassimoPartecipanti;
    
    // Relazione Many-to-One con Location
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    
    // Relazione One-to-Many con Partecipazione
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partecipazione> partecipazioni = new ArrayList<>();
    
    // Enum per il tipo di evento
    public enum TipoEvento {
        PUBBLICO, PRIVATO
    }
    
    // Costruttori
    public Evento() {}
    
    public Evento(String titolo, LocalDate dataEvento, String descrizione, 
                  TipoEvento tipoEvento, Integer numeroMassimoPartecipanti, Location location) {
        this.titolo = titolo;
        this.dataEvento = dataEvento;
        this.descrizione = descrizione;
        this.tipoEvento = tipoEvento;
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
        this.location = location;
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitolo() {
        return titolo;
    }
    
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    public LocalDate getDataEvento() {
        return dataEvento;
    }
    
    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }
    
    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
    
    public Integer getNumeroMassimoPartecipanti() {
        return numeroMassimoPartecipanti;
    }
    
    public void setNumeroMassimoPartecipanti(Integer numeroMassimoPartecipanti) {
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public List<Partecipazione> getPartecipazioni() {
        return partecipazioni;
    }
    
    public void setPartecipazioni(List<Partecipazione> partecipazioni) {
        this.partecipazioni = partecipazioni;
    }
    
    // Metodi di utilità per gestire le partecipazioni
    public void addPartecipazione(Partecipazione partecipazione) {
        partecipazioni.add(partecipazione);
        partecipazione.setEvento(this);
    }
    
    public void removePartecipazione(Partecipazione partecipazione) {
        partecipazioni.remove(partecipazione);
        partecipazione.setEvento(null);
    }
    
    // Metodo per verificare se ci sono posti disponibili
    public boolean hasPostiDisponibili() {
        return partecipazioni.size() < numeroMassimoPartecipanti;
    }
    
    // Metodo per ottenere il numero di posti disponibili
    public int getPostiDisponibili() {
        return numeroMassimoPartecipanti - partecipazioni.size();
    }
    
    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", dataEvento=" + dataEvento +
                ", descrizione='" + descrizione + '\'' +
                ", tipoEvento=" + tipoEvento +
                ", numeroMassimoPartecipanti=" + numeroMassimoPartecipanti +
                ", location=" + (location != null ? location.getNome() : "null") +
                '}';
    }
}