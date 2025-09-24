# Sistema di Gestione Eventi - S3-L3

## Descrizione
Questo progetto implementa un sistema di gestione eventi utilizzando JPA/Hibernate per la persistenza dei dati. Il sistema gestisce le relazioni tra persone, eventi, location e partecipazioni.

## Struttura del Progetto

### Entità JPA
- **Persona**: Rappresenta gli utenti del sistema con informazioni personali
- **Evento**: Rappresenta gli eventi organizzati
- **Location**: Rappresenta i luoghi dove si svolgono gli eventi
- **Partecipazione**: Gestisce la relazione Many-to-Many tra Persona ed Evento

### Relazioni
- `Persona` ↔ `Partecipazione` (One-to-Many)
- `Evento` ↔ `Partecipazione` (One-to-Many)
- `Location` ↔ `Evento` (One-to-Many)

### DAO (Data Access Object)
- **PersonaDAO**: Operazioni CRUD per le persone
- **EventoDAO**: Operazioni CRUD per gli eventi
- **LocationDAO**: Operazioni CRUD per le location
- **PartecipazioneDAO**: Operazioni CRUD per le partecipazioni

## Configurazione Database

Il progetto è configurato per utilizzare PostgreSQL con le seguenti impostazioni:
- **Host**: localhost:5432
- **Database**: gestione_eventi
- **Username**: postgres
- **Password**: 1234

### Prerequisiti
1. PostgreSQL installato e in esecuzione
2. Database `gestione_eventi` creato
3. Java 17 o superiore
4. Maven

## Esecuzione

1. Assicurarsi che PostgreSQL sia in esecuzione
2. Creare il database:
   ```sql
   CREATE DATABASE gestione_eventi;
   ```
3. Compilare il progetto:
   ```bash
   mvn clean compile
   ```
4. Eseguire la classe Main:
   ```bash
   mvn exec:java -Dexec.mainClass="it.epicode.Main"
   ```

## Funzionalità Implementate

La classe Main dimostra le seguenti funzionalità:
1. Creazione e salvataggio di una persona
2. Creazione e salvataggio di una location
3. Creazione e salvataggio di un evento
4. Creazione e salvataggio di una partecipazione
5. Ricerche avanzate (per email, città, titolo, ecc.)
6. Gestione dello stato delle partecipazioni
7. Verifica dei posti disponibili

## Struttura delle Cartelle
```
src/
├── main/
│   ├── java/
│   │   └── it/epicode/
│   │       ├── entities/     # Entità JPA
│   │       ├── dao/          # Data Access Objects
│   │       └── Main.java     # Classe di test
│   └── resources/
│       └── META-INF/
│           └── persistence.xml  # Configurazione JPA
└── pom.xml                   # Configurazione Maven
```

## Note Tecniche
- Utilizzo di Hibernate 6.2.7.Final
- Configurazione JPA 3.0
- Gestione automatica dello schema database (hibernate.hbm2ddl.auto=update)
- Logging SQL abilitato per debug