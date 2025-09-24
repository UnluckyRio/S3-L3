package it.epicode;

import it.epicode.dao.*;
import it.epicode.entities.*;
import java.time.LocalDate;

/**
 * Classe Main per testare il sistema di gestione eventi
 * Dimostra l'utilizzo delle entità e dei DAO creati
 */
public class Main {
    
    public static void main(String[] args) {
        // Inizializzazione dei DAO
        PersonaDAO personaDAO = new PersonaDAO();
        LocationDAO locationDAO = new LocationDAO();
        EventoDAO eventoDAO = new EventoDAO();
        PartecipazioneDAO partecipazioneDAO = new PartecipazioneDAO();
        
        try {
            System.out.println("=== SISTEMA DI GESTIONE EVENTI ===\n");
            
            // 1. Creazione e salvataggio di una persona
            System.out.println("1. Creazione di una persona...");
            Persona persona = new Persona(
                "Mario", 
                "Rossi", 
                "mario.rossi@email.com", 
                LocalDate.of(1990, 5, 15), 
                Persona.Sesso.M
            );
            persona = personaDAO.save(persona);
            System.out.println("Persona creata: " + persona);
            System.out.println();
            
            // 2. Creazione e salvataggio di una location
            System.out.println("2. Creazione di una location...");
            Location location = new Location("Palazzo dei Congressi", "Roma");
            location = locationDAO.save(location);
            System.out.println("Location creata: " + location);
            System.out.println();
            
            // 3. Creazione e salvataggio di un evento
            System.out.println("3. Creazione di un evento...");
            Evento evento = new Evento(
                "Conferenza Java 2024",
                LocalDate.of(2024, 6, 15),
                "Una conferenza dedicata alle ultime novità del mondo Java",
                Evento.TipoEvento.PUBBLICO,
                100,
                location
            );
            evento = eventoDAO.save(evento);
            System.out.println("Evento creato: " + evento);
            System.out.println();
            
            // 4. Creazione e salvataggio di una partecipazione
            System.out.println("4. Creazione di una partecipazione...");
            Partecipazione partecipazione = new Partecipazione(
                persona, 
                evento, 
                Partecipazione.Stato.CONFERMATA
            );
            partecipazione = partecipazioneDAO.save(partecipazione);
            System.out.println("Partecipazione creata: " + partecipazione);
            System.out.println();
            
            // 5. Test delle funzionalità di ricerca
            System.out.println("=== TEST DELLE FUNZIONALITÀ DI RICERCA ===\n");
            
            // Ricerca persona per email
            System.out.println("5. Ricerca persona per email...");
            Persona personaTrovata = personaDAO.findByEmail("mario.rossi@email.com");
            System.out.println("Persona trovata: " + personaTrovata);
            System.out.println();
            
            // Ricerca location per città
            System.out.println("6. Ricerca location per città...");
            var locationsRoma = locationDAO.findByCitta("Roma");
            System.out.println("Location trovate a Roma: " + locationsRoma.size());
            locationsRoma.forEach(System.out::println);
            System.out.println();
            
            // Ricerca eventi per titolo
            System.out.println("7. Ricerca eventi per titolo...");
            var eventiJava = eventoDAO.findByTitolo("Java");
            System.out.println("Eventi Java trovati: " + eventiJava.size());
            eventiJava.forEach(System.out::println);
            System.out.println();
            
            // Ricerca partecipazioni per persona
            System.out.println("8. Ricerca partecipazioni per persona...");
            var partecipazioniPersona = partecipazioneDAO.findByPersona(persona);
            System.out.println("Partecipazioni di " + persona.getNome() + " " + persona.getCognome() + ": " + partecipazioniPersona.size());
            partecipazioniPersona.forEach(System.out::println);
            System.out.println();
            
            // Verifica se la persona è iscritta all'evento
            System.out.println("9. Verifica iscrizione...");
            boolean isIscritta = partecipazioneDAO.isPersonaIscritta(persona, evento);
            System.out.println("La persona è iscritta all'evento: " + isIscritta);
            System.out.println();
            
            // Informazioni sui posti disponibili
            System.out.println("10. Informazioni sui posti disponibili...");
            System.out.println("Posti disponibili per l'evento '" + evento.getTitolo() + "': " + evento.getPostiDisponibili());
            System.out.println();
            
            // Test aggiornamento stato partecipazione
            System.out.println("11. Aggiornamento stato partecipazione...");
            partecipazione.setStato(Partecipazione.Stato.DA_CONFERMARE);
            partecipazione = partecipazioneDAO.update(partecipazione);
            System.out.println("Partecipazione aggiornata: " + partecipazione);
            System.out.println();
            
            // Creazione di una seconda persona e partecipazione
            System.out.println("12. Creazione di una seconda persona e partecipazione...");
            Persona persona2 = new Persona(
                "Giulia", 
                "Bianchi", 
                "giulia.bianchi@email.com", 
                LocalDate.of(1985, 8, 22), 
                Persona.Sesso.F
            );
            persona2 = personaDAO.save(persona2);
            
            Partecipazione partecipazione2 = new Partecipazione(
                persona2, 
                evento, 
                Partecipazione.Stato.CONFERMATA
            );
            partecipazione2 = partecipazioneDAO.save(partecipazione2);
            
            System.out.println("Seconda persona creata: " + persona2);
            System.out.println("Seconda partecipazione creata: " + partecipazione2);
            System.out.println();
            
            // Riepilogo finale
            System.out.println("=== RIEPILOGO FINALE ===");
            System.out.println("Totale persone nel sistema: " + personaDAO.findAll().size());
            System.out.println("Totale location nel sistema: " + locationDAO.findAll().size());
            System.out.println("Totale eventi nel sistema: " + eventoDAO.findAll().size());
            System.out.println("Totale partecipazioni nel sistema: " + partecipazioneDAO.findAll().size());
            
            // Aggiorna l'evento per vedere i posti rimanenti
            evento = eventoDAO.findById(evento.getId());
            System.out.println("Posti rimanenti per '" + evento.getTitolo() + "': " + evento.getPostiDisponibili());
            
            System.out.println("\n=== TEST COMPLETATO CON SUCCESSO! ===");
            
        } catch (Exception e) {
            System.err.println("Errore durante l'esecuzione del test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Chiusura delle connessioni
            PersonaDAO.closeEntityManagerFactory();
            LocationDAO.closeEntityManagerFactory();
            EventoDAO.closeEntityManagerFactory();
            PartecipazioneDAO.closeEntityManagerFactory();
        }
    }
}