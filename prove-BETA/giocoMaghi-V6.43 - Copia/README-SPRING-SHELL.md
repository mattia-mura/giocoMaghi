# Gioco dei Maghi - Spring Shell Interactive

## Descrizione
Versione interattiva del Gioco dei Maghi utilizzando Spring Shell con supporto per menu navigabili tramite frecce direzionali e tasti WASD.

## Prerequisiti
- Java 11 o superiore
- Maven 3.6 o superiore

## Installazione e Avvio

### Metodo 1: Script automatico (Windows)
```bash
run-shell.bat
```

### Metodo 2: Manuale
```bash
# Compilazione
mvn clean compile

# Avvio
mvn spring-boot:run
```

## Caratteristiche del Menu Interattivo

### Navigazione
- **Frecce direzionali**: Su/Giù per navigare tra le opzioni
- **WASD**: W (su), S (giù) per navigare
- **INVIO**: Conferma selezione
- **Q**: Scorciatoia per uscire (ultima opzione)
- **ESC**: Alternativa per uscire

### Compatibilità Windows
- Utilizza JLine3 per miglior supporto del terminale Windows
- Gestione automatica del cursore
- Pulizia dello schermo per esperienza fluida

## Comandi Shell Disponibili

### Comandi Principali
- `start` / `gioca` / `play` - Avvia il gioco completo con menu interattivi
- `menu` / `m` - Mostra solo il menu principale
- `log-level` / `log` - Imposta livello di logging

### Gestione Maghi
- `crea-mago [nome]` / `new-mago [nome]` - Crea nuovo mago
- `maghi` / `lista-maghi` - Mostra tutti i maghi
- `elimina-mago <nome>` / `remove-mago <nome>` - Elimina mago

### Comandi Sistema
- `help` - Mostra tutti i comandi disponibili
- `exit` / `quit` - Esci dall'applicazione
- `clear` - Pulisci schermo

## Modalità di Gioco

### 1. Versione OG
Menu interattivo per:
- Creare maghi singolarmente
- Eliminare maghi
- Visualizzare schede magiche
- Assegnare nemici manualmente
- Avviare lotta magica

### 2. Versione Automatica
Menu interattivo per:
- Generare N maghi automaticamente
- Eliminare maghi
- Visualizzare schede magiche
- Apocalisse magica (nemici assegnati automaticamente)

### 3. Gioco a Squadre
Menu interattivo per:
- Generare squadre automaticamente
- Visualizzare schede magiche
- Ragnarok magico (battaglia tra squadre)

### 4. Gestione Log
Selezione del livello di dettaglio:
- Debug (Dettagliato)
- Intermedio
- Minimo

## Vantaggi della Versione Spring Shell

### Esperienza Utente
- **Menu navigabili**: Usa frecce invece di digitare numeri
- **Interfaccia pulita**: Schermo che si aggiorna automaticamente
- **Comandi veloci**: Accesso diretto tramite shell commands
- **Compatibilità Windows**: Gestione ottimizzata per terminali Windows

### Flessibilità
- **Modalità interattiva**: Menu completi per esperienza guidata
- **Modalità command**: Comandi diretti per utenti esperti
- **Configurabile**: Livelli di log e parametri personalizzabili

### Robustezza
- **Gestione errori**: Fallback automatici per incompatibilità terminale
- **Pulizia risorse**: Gestione automatica cursore e terminale
- **Interrupt handling**: Gestione sicura di thread e processi

## Esempi di Utilizzo

### Sessione Completa
```bash
# Avvia l'applicazione
mvn spring-boot:run

# Nel shell Spring
shell:> start
# Naviga con frecce nel menu per impostare livello log
# Seleziona modalità di gioco
# Usa menu interattivi per giocare

# Oppure comandi diretti
shell:> crea-mago Gandalf
shell:> crea-mago Saruman
shell:> maghi
shell:> log-level
```

### Comandi Rapidi
```bash
# Crea più maghi rapidamente
shell:> crea-mago Merlino
shell:> crea-mago Albus
shell:> crea-mago Voldemort

# Visualizza stato
shell:> maghi

# Configura e avvia gioco
shell:> start
```

## Note Tecniche

### Struttura Classi
- `WindowsInteractiveMenu`: Menu ottimizzato per Windows con JLine3
- `MaghiShellCommands`: Comandi Spring Shell
- `MaghiShellApplication`: Applicazione Spring Boot principale

### Configurazione
- `application.properties`: Configurazioni Spring Shell
- `pom.xml`: Dipendenze Spring Boot, Spring Shell, JLine3

### Backward Compatibility
- Mantiene tutte le funzionalità del gioco originale
- `InteractiveMenu` originale ancora disponibile come fallback
- Logica di gioco invariata

## Troubleshooting

### Java non trovato
```bash
# Verifica installazione Java
java -version

# Se non trovato, installa JDK 11+ e aggiungi al PATH
```

### Maven non trovato
```bash
# Verifica installazione Maven
mvn -version

# Se non trovato, installa Maven e aggiungi al PATH
```

### Problemi con i caratteri ANSI
Se i menu non si visualizzano correttamente:
- Usa Windows Terminal invece del Command Prompt
- Oppure usa PowerShell ISE
- In alternativa, i comandi shell funzionano sempre

### Performance
- I menu interattivi richiedono terminale moderno
- Per prestazioni migliori usa comandi diretti
- Il fallback automatico gestisce terminali limitati