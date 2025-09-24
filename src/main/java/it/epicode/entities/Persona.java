package it.epicode.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità JPA che rappresenta una Persona nel sistema di gestione eventi
 */
@Entity
@Table(name = "persone")
public class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String cognome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sesso sesso;
    
    // Relazione One-to-Many con Partecipazione
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partecipazione> listaPartecipazioni = new ArrayList<>();
    
    // Enum per il sesso
    public enum Sesso {
        M, F
    }
    
    // Costruttori
    public Persona() {}
    
    public Persona(String nome, String cognome, String email, LocalDate dataNascita, Sesso sesso) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
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
    
    public String getCognome() {
        return cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    public Sesso getSesso() {
        return sesso;
    }
    
    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }
    
    public List<Partecipazione> getListaPartecipazioni() {
        return listaPartecipazioni;
    }
    
    public void setListaPartecipazioni(List<Partecipazione> listaPartecipazioni) {
        this.listaPartecipazioni = listaPartecipazioni;
    }
    
    // Metodi di utilità per gestire le partecipazioni
    public void addPartecipazione(Partecipazione partecipazione) {
        listaPartecipazioni.add(partecipazione);
        partecipazione.setPersona(this);
    }
    
    public void removePartecipazione(Partecipazione partecipazione) {
        listaPartecipazioni.remove(partecipazione);
        partecipazione.setPersona(null);
    }
    
    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascita=" + dataNascita +
                ", sesso=" + sesso +
                '}';
    }
}