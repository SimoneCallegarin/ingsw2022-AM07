 # Prova Finale Ingegneria del Software 2022
## Gruppo AM07

- ###   10676880    Simone Callegarin([@SimoneCallegarin](https://github.com/SimoneCallegarin))<br>simone.callegarin@mail.polimi.it
- ###   10685017    Giacomo Carugati ([@giacomo-carugati](https://github.com/giacomo-carugati))<br>giacomo.carugati@mail.polimi.it
- ###   10712832    Filippo Buda ([@Filippobuda](https://github.com/Filippobuda))<br>filippo.buda@mail.polimi.it

## Documentazione

### Coverage report
Al seguente link è possibile consultare il report della coverage dei test effettuati con Junit: [Report](https://github.com/SimoneCallegarin/ingsw2022-AM07/tree/master/deliverables/CoverageReport).

Il Controller è stato testato al 100% in tutte le sue parti.

Il Model è stato testato nel 100% delle sue classi, nel 99% dei suoi metodi per un totale di 94% delle linee di codice testate.

| Package     | Class | Method | Line |
|:------------|------:|-------:|-----:|
| Controller  |  100% |   100% | 100% |
| Model       |  100% |    99% |  94% |


### Librerie e Plugins
|Libreria/Plugin|Descrizione|
|:-------------:|-----------|
|     Maven     | Strumento di automazione della compilazione utilizzato principalmente per progetti Java. |
|   JavaSwing   | Libreria grafica per realizzare interfacce utente. |
|     JUnit     | Framework di unit testing. |
|     Jansi     | Libreria che permette di utilizzare le ANSI escape sequences per formattare il proprio output da console |



## Funzionalità

### Implemented Functionalities

| Functionality      | Implementation |
|:-------------------|:--------------:|
| Basic rules        | 🟢 |
| Complete rules     | 🟢 |
| Socket             | 🟢 |
| GUI                | 🟢 |
| CLI                | 🟢 |
| 12 Character cards | 🟢 |
| 4 Players match    | 🟢 |
| Multiple games     | 🟢 |
| Persistance        | 🔴 |
| Resistance to disconnection | 🔴 |


- **12 Character cards:**  sono state implementate tutte e 12 le carte personaggio.

- **4 Players match:**  il progetto è stato realizzato in modo che sia possibile giocare partite da 2, 3 oppure 4 giocatori.

- **Multiple games:**  è stato realizzato il server in modo che possa gestire più partite contemporaneamente, inserendo un giocatore in una nuova partita o in un match                       con già altri giocatori in attesa in base alle preferenze da esso espresse.



## Jars
I Jar del progetto possono essere scaricati al seguente link: [Jars](https://github.com/SimoneCallegarin/ingsw2022-AM07/tree/master/deliverables/Jar).

## Esecuzione
Questo progetto richiede una versione di Java SE 16 o superiore per essere eseguito correttamente.

### Client (App)
Le seguenti istruzioni descrivono come eseguire il client con interfaccia CLI o GUI.

Per lanciare client digitare da terminale il comando:
```
java -jar client.jar 
```

Di seguito vi verrà richiesto di inserire address e port del server a cui connettersi.

Successivamente vi sarà richiesto se desideriate giocare con CLI o GUI, selezionando l'una o l'altra semplicemente inserendo rispettivamente 0 o 1 da tastiera e confermando.

### Server
Per lanciare il server digitare da terminale il comando:
```
java -jar server.jar 
```

Di seguito vi sarà richiesto di inserire la porta su cui mettere in ascolto il server, una volta inserita e confermata la porta il server sarà pronto al funzionamento.

In alternativa:
```
java -jar server.jar portNumber
```


### Note aggiuntive:
Al fine di rendere l'esperienza di gioco il più godibile possibile sarebbe conveniente eseguire le seguenti operazioni prima di giocare:

#### CLI
- Aprire la finestra del terminale a schermo intero;
- Settare da impostazioni di terminale un buffer di almeno 900 caratteri (in modo da evitare spiacevoli tagli della console sulle scritte);
- Utilizzare caratteri di dimensione consona (possibilmente di dimensione ridotta quanto possibile).

#### GUI
- In caso il vostro pc sia dotato di uno schermo 14 pollici o inferiore sarebbe utile settare da impostazioni di windows (Sistema > Schermo > Ridimensionamento personalizzato) un valore di Ridimensionamento personalizzato pari a 100% o inferiore (in modo da evitare piccoli spiacevoli bug grafici).
