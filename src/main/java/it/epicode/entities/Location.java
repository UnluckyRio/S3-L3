package it.epicode.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità JPA che rappresenta una Location nel sistema di gestione eventi
 */
@Entity
@Table(name = "locations")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String citta;
    
    // Relazione One-to-Many con Evento
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evento> eventi = new ArrayList<>();
    
    // Costruttori
    public Location() {}
    
    public Location(String nome, String citta) {
        this.nome = nome;
        this.citta = citta;
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCitta() {
        return citta;
    }
    
    public void setCitta(String citta) {
        this.citta = citta;
    }
    
    public List<Evento> getEventi() {
        return eventi;
    }
    
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }
    
    // Metodi di utilità per gestire gli eventi
    public void addEvento(Evento evento) {
        eventi.add(evento);
        evento.setLocation(this);
    }
    
    public void removeEvento(Evento evento) {
        eventi.remove(evento);
        evento.setLocation(null);
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                '}';
    }
}