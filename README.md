# Projet - Restalo

Le meilleur logiciel de réservation en restauration!

## Requis

- Java 21
- Maven 3.x

## Commandes

### Compilation

```
mvn compile
```

### Exécution

```
mvn exec:java
```

### Run Docker Image

```
docker compose up -d
```

### Stop Docker Image

```
docker compose down -v
```

### Choose persistence
    
```
In SystemProperty, choose the persistence with -Dpersistence=... (mongo or inmemory)
```

### For conecting to the database

```
Create a mongo username and password though the Edit Configuration window, 
and connect with the URL structure provided.
```



