package it.epicode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Versione demo del sistema di gestione eventi
 * Simula il funzionamento senza dipendenze JPA/Hibernate
 */
public class MainDemo {
    
    // Simulazione delle entità senza JPA
    static class Persona {
        private Long id;
        private String nome;
        private String cognome;
        private String email;
        private LocalDate dataNascita;
        private Sesso sesso;
        private List<Partecipazione> listaPartecipazioni = new ArrayList<>();
        
        public enum Sesso { M, F }
        
        public Persona(Long id, String nome, String cognome, String email, LocalDate dataNascita, Sesso sesso) {
            this.id = id;
            this.nome = nome;
            this.cognome = cognome;
            this.email = email;
            this.dataNascita = dataNascita;
            this.sesso = sesso;
        }
        
        // Getter
        public Long getId() { return id; }
        public String getNome() { return nome; }
        public String getCognome() { return cognome; }
        public String getEmail() { return email; }
        public LocalDate getDataNascita() { return dataNascita; }
        public Sesso getSesso() { return sesso; }
        public List<Partecipazione> getListaPartecipazioni() { return listaPartecipazioni; }
        
        @Override
        public String toString() {
            return "Persona{id=" + id + ", nome='" + nome + "', cognome='" + cognome + 
                   "', email='" + email + "', dataNascita=" + dataNascita + ", sesso=" + sesso + "}";
        }
    }
    
    static class Location {
        private Long id;
        private String nome;
        private String citta;
        private List<Evento> eventi = new ArrayList<>();
        
        public Location(Long id, String nome, String citta) {
            this.id = id;
            this.nome = nome;
            this.citta = citta;
        }
        
        // Getter
        public Long getId() { return id; }
        public String getNome() { return nome; }
        public String getCitta() { return citta; }
        public List<Evento> getEventi() { return eventi; }
        
        @Override
        public String toString() {
            return "Location{id=" + id + ", nome='" + nome + "', citta='" + citta + "'}";
        }
    }
    
    static class Evento {
        private Long id;
        private String titolo;
        private LocalDate dataEvento;
        private String descrizione;
        private TipoEvento tipoEvento;
        private Integer numeroMassimoPartecipanti;
        private Location location;
        private List<Partecipazione> partecipazioni = new ArrayList<>();
        
        public enum TipoEvento { PUBBLICO, PRIVATO }
        
        public Evento(Long id, String titolo, LocalDate dataEvento, String descrizione, 
                      TipoEvento tipoEvento, Integer numeroMassimoPartecipanti, Location location) {
            this.id = id;
            this.titolo = titolo;
            this.dataEvento = dataEvento;
            this.descrizione = descrizione;
            this.tipoEvento = tipoEvento;
            this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
            this.location = location;
        }
        
        // Getter
        public Long getId() { return id; }
        public String getTitolo() { return titolo; }
        public LocalDate getDataEvento() { return dataEvento; }
        public String getDescrizione() { return descrizione; }
        public TipoEvento getTipoEvento() { return tipoEvento; }
        public Integer getNumeroMassimoPartecipanti() { return numeroMassimoPartecipanti; }
        public Location getLocation() { return location; }
        public List<Partecipazione> getPartecipazioni() { return partecipazioni; }
        
        public int getPostiDisponibili() {
            return numeroMassimoPartecipanti - partecipazioni.size();
        }
        
        @Override
        public String toString() {
            return "Evento{id=" + id + ", titolo='" + titolo + "', dataEvento=" + dataEvento + 
                   ", tipoEvento=" + tipoEvento + ", numeroMassimoPartecipanti=" + numeroMassimoPartecipanti + 
                   ", location=" + location.getNome() + "}";
        }
    }
    
    static class Partecipazione {
        private Long id;
        private Persona persona;
        private Evento evento;
        private Stato stato;
        
        public enum Stato { CONFERMATA, DA_CONFERMARE }
        
        public Partecipazione(Long id, Persona persona, Evento evento, Stato stato) {
            this.id = id;
            this.persona = persona;
            this.evento = evento;
            this.stato = stato;
        }
        
        // Getter e Setter
        public Long getId() { return id; }
        public Persona getPersona() { return persona; }
        public Evento getEvento() { return evento; }
        public Stato getStato() { return stato; }
        public void setStato(Stato stato) { this.stato = stato; }
        
        @Override
        public String toString() {
            return "Partecipazione{id=" + id + ", persona=" + persona.getNome() + " " + persona.getCognome() + 
                   ", evento=" + evento.getTitolo() + ", stato=" + stato + "}";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DI GESTIONE EVENTI - DEMO ===\n");
        
        try {
            // 1. Creazione di una persona
            System.out.println("1. Creazione di una persona...");
            Persona persona = new Persona(1L, "Mario", "Rossi", "mario.rossi@email.com", 
                                        LocalDate.of(1990, 5, 15), Persona.Sesso.M);
            System.out.println("Persona creata: " + persona);
            System.out.println();
            
            // 2. Creazione di una location
            System.out.println("2. Creazione di una location...");
            Location location = new Location(1L, "Palazzo dei Congressi", "Roma");
            System.out.println("Location creata: " + location);
            System.out.println();
            
            // 3. Creazione di un evento
            System.out.println("3. Creazione di un evento...");
            Evento evento = new Evento(1L, "Conferenza Java 2024", LocalDate.of(2024, 6, 15),
                                     "Una conferenza dedicata alle ultime novità del mondo Java",
                                     Evento.TipoEvento.PUBBLICO, 100, location);
            System.out.println("Evento creato: " + evento);
            System.out.println();
            
            // 4. Creazione di una partecipazione
            System.out.println("4. Creazione di una partecipazione...");
            Partecipazione partecipazione = new Partecipazione(1L, persona, evento, 
                                                              Partecipazione.Stato.CONFERMATA);
            
            // Aggiunta delle relazioni bidirezionali
            persona.getListaPartecipazioni().add(partecipazione);
            evento.getPartecipazioni().add(partecipazione);
            location.getEventi().add(evento);
            
            System.out.println("Partecipazione creata: " + partecipazione);
            System.out.println();
            
            // 5. Test delle funzionalità
            System.out.println("=== TEST DELLE FUNZIONALITÀ ===\n");
            
            System.out.println("5. Informazioni sulla persona:");
            System.out.println("   - Nome completo: " + persona.getNome() + " " + persona.getCognome());
            System.out.println("   - Email: " + persona.getEmail());
            System.out.println("   - Età: " + (LocalDate.now().getYear() - persona.getDataNascita().getYear()) + " anni");
            System.out.println("   - Numero partecipazioni: " + persona.getListaPartecipazioni().size());
            System.out.println();
            
            System.out.println("6. Informazioni sull'evento:");
            System.out.println("   - Titolo: " + evento.getTitolo());
            System.out.println("   - Data: " + evento.getDataEvento());
            System.out.println("   - Location: " + evento.getLocation().getNome() + " (" + evento.getLocation().getCitta() + ")");
            System.out.println("   - Posti totali: " + evento.getNumeroMassimoPartecipanti());
            System.out.println("   - Posti occupati: " + evento.getPartecipazioni().size());
            System.out.println("   - Posti disponibili: " + evento.getPostiDisponibili());
            System.out.println();
            
            // 7. Aggiunta di una seconda persona
            System.out.println("7. Aggiunta di una seconda persona...");
            Persona persona2 = new Persona(2L, "Giulia", "Bianchi", "giulia.bianchi@email.com",
                                         LocalDate.of(1985, 8, 22), Persona.Sesso.F);
            
            Partecipazione partecipazione2 = new Partecipazione(2L, persona2, evento, 
                                                               Partecipazione.Stato.DA_CONFERMARE);
            
            persona2.getListaPartecipazioni().add(partecipazione2);
            evento.getPartecipazioni().add(partecipazione2);
            
            System.out.println("Seconda persona: " + persona2);
            System.out.println("Seconda partecipazione: " + partecipazione2);
            System.out.println();
            
            // 8. Aggiornamento stato partecipazione
            System.out.println("8. Aggiornamento stato partecipazione...");
            System.out.println("Stato prima: " + partecipazione2.getStato());
            partecipazione2.setStato(Partecipazione.Stato.CONFERMATA);
            System.out.println("Stato dopo: " + partecipazione2.getStato());
            System.out.println();
            
            // 9. Riepilogo finale
            System.out.println("=== RIEPILOGO FINALE ===");
            System.out.println("Evento: " + evento.getTitolo());
            System.out.println("Partecipanti iscritti: " + evento.getPartecipazioni().size());
            System.out.println("Posti rimanenti: " + evento.getPostiDisponibili());
            System.out.println();
            
            System.out.println("Lista partecipanti:");
            for (Partecipazione p : evento.getPartecipazioni()) {
                System.out.println("  - " + p.getPersona().getNome() + " " + p.getPersona().getCognome() + 
                                 " (" + p.getStato() + ")");
            }
            
            System.out.println("\n=== DEMO COMPLETATA CON SUCCESSO! ===");
            System.out.println("\nNOTA: Questa è una versione demo che simula il funzionamento del sistema.");
            System.out.println("Per utilizzare la versione completa con database, è necessario:");
            System.out.println("1. Installare Maven");
            System.out.println("2. Configurare PostgreSQL con database 'gestione_eventi'");
            System.out.println("3. Eseguire: mvn clean compile exec:java -Dexec.mainClass=\"it.epicode.Main\"");
            
        } catch (Exception e) {
            System.err.println("Errore durante l'esecuzione della demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}