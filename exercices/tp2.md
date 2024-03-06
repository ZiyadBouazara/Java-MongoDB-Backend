# Exercices - TP2


## GitHub Project
![Local Image](../images/tp2/board.png)
## Milestone
![Local Image](../images/tp2/milestone.png)
## Issues
![Local Image](../images/tp2/issue1.png)
![Local Image](../images/tp2/issue2.png)
![Local Image](../images/tp2/issue3.png)


## Pull Requests
### PR1
![Local Image](../images/tp2/pr1.png)
![Local Image](../images/tp2/pr1.2.png)
![Local Image](../images/tp2/pr1.3.png)

### PR2
![Local Image](../images/tp2/pr2.png)
![Local Image](../images/tp2/pr2.2.png)
### PR3
![Local Image](../images/tp2/pr3.png)

## Arbre de Commits
![Local Image](../images/tp2/commit-tree.png)

## Rétrospective sur le processus

### Mesure des métrique du processus:


#### Temps pour implémenter une issue:

En fonction de la complexité des issues, nos temps d'implémentation variaient. Les petites issues pouvaient nous prendre quelques heures, tandis que les grosses issues, nécessitant plus de temps d'analyse et de discussion, pouvaient prendre jusqu'à plusieurs jours. En moyenne, cela nous prenait un à deux jours.

#### Temps pour intégrer une pull request:

Pour ce qui est de l'intégration des pull-requests, cela ne nous prenait pas énormément de temps, quelques heures tout au plus pour résoudre les conflits dans les cas où il y en avait. En moyenne, cela pouvait nous prendre 30 minutes.
#### Nombres de personnes qui travaillaient sur chaque issue:

Chaque issue était créée et gérée par une seule personne. Nous divisions nos tâches en plusieurs issues, ce qui faisait en sorte qu'une personne suffisait à implémenter la partie assignée. En cas de problème, nous pouvions nous entraider à deux sur une issue, mais cela arrivait rarement.

#### Nombres de personnes qui reviewaient chaques pull-request:

En ce qui concerne les revues des pull-requests, nous attribuions la révision à une seule personne. Cette dernière laissait des commentaires qu'elle jugeait pertinents sur les parties à modifier, supprimer ou ajouter.

#### Nombres d'issues qui étaient en cours d'implémentation en même temps:

Le nombre d'issues en cours d'implémentation simultanée variait considérablement en fonction des périodes de travail. En début d'itération, peu d'issues étaient en cours d'implémentation. Cependant, vers la fin de l'itération, avec plus de membres de l'équipe concentrés sur le livrable, plusieurs issues pouvaient être en cours d'implémentation en même temps, atteignant une moyenne de 5 issues simultanément.

#### Nombres de pull-requests qui étaient en cours de review en même temps:

En moyenne, notre équipe avait environ 3 pull-requests en attente de révision, peu importe la période de l'itération considérée.

### Réflexions à faire en équipe sur le processus:

1. Selon nous, le temps pris pour implémenter les issues était parfait, mais en ce qui concerne les pull-requests, nous aurions dû les implémenter graduellement et éviter d'en avoir plusieurs à réviser et à fusionner vers la fin de l'itération. En faisant cela, nous aurions eu plus de facilité à organiser notre application de manière efficace, et la communication intra-équipe n'en aurait été que facilitée, de plus on aurait évité les merges-conflicts de fin d'itération.


2. De toute évidence, plus une issue/pull-request était grande et complexe, plus le temps nécessaire pour la finaliser augmentait. De plus, la taille d'une pull-request/issue pouvait aussi influencer le nombre de personnes travaillant dessus, comme abordé précédemment.


3. Afin d'améliorer notre processus, le consensus au sein de notre équipe est qu'il serait bénéfique d'organiser des réunions de synchronisation et de discussions plus fréquemment. De plus, il serait impératif de diminuer encore davantage la taille des issues afin de faciliter la fusion une fois l'issue finalisée. Enfin, nous devrions commencer à attribuer des issues et des revues de code à plusieurs personnes plutôt qu'à une seule, car cela améliore la communication entre les membres de l'équipe et augmente la compréhension du code et de la logique générale de l'application.

## Architecture
### Rôles des classes principales
![Local Image](../images/tp2/architecture.png)
- ReservationConfiguration : Permet de configurer une réservation pour un restaurant. Cela permet de définir le nombre de places disponibles, le temps de réservation minimum et le temps de réservation maximum.
- ReservationRequest : Permet de faire une requête pour la réservation d'un restaurant.
- RestaurantRequest : Permet de faire une requête pour la création d'un restaurant.
- ResourcesHandler : Permet de gérer les ressources telles que les restaurants et les réservations, et de les sauvegarder temporairement (Runtime).
Il effectue également la création des classes principales en appelant les factories respectivent qui les build par la suite.
- Restaurant : Permet de créer un restaurant et d'avoir accès à ses attributs.
- Reservation : Permet de créer une réservation et d'avoir accès à ses attributs.
- RestaurantResource : Permet de gérer les requêtes pour les restaurants.
- InvalidParamExceptionMapper : Permet de gérer les exceptions InvalidParamException.
- MissingParamExceptionMapper : Permet de gérer les exceptions MissingParamException.
- NotFoundExceptionMapper : Permet de gérer les exceptions NotFoundException.
- Main : Permet de démarrer le serveur.
- FuzzySearch : Permet d'avoir l'objet de recherche de Restaurant à travers lequel on peut rechercher un restaurant par son nom, par l'heure à laquelle il est possible d'y aller et l'heure de départ.
Utiliser une classe comme ceci permet une recherche plus flexible.
- VisitTime: Représente le temps auquel un client peut aller au restaurant et en repartir. C'est pour cette raison que cette classe contient un from et un to.
- ...Response : Copie d'un objet complexe qui est retourné à l'utilisateur (DTO) 

### Nos choix
Nous voulions déléguer les tâche telles que la validation à une classe séparé de la Resource, donc nous avons créé ResourceHandler 
qui effectuent plusieurs validations par rapport aux requêtes reçues. Nous avons également établis des DTOS pour la transition entre les différentes couches de l'architecture.
Aussi, nous avons une classe utilitaire qui regroupe différentes classes telles que FuzzySearch pour la recherche de Restaurants. Finalement, nous avons une classe RestaurantFactory,
qui s'occuper de build les Restaurants selon la configuration de réservation fournis par l'utilisateur.

### Relations  suspectes et des solutions potentielles
Dans le but de nous rapprocher du Principe de Responsabilité Unique (SRP), nous avons envisagé l'ajout d'un resourceHandler,
chargé de soulager la resource en lui déléguant la gestion de toutes les actions sur les objets du 
domaine, telles que la sauvegarde et la recherche. Cependant, nous avons rapidement constaté que le 
Handler devenait une 'godclass'.

Pour notre prochaine itération, nous opterons pour un service qui délèguera ses tâches à des factories et des assembleurs afin de promouvoir non seulement le SRP mais aussi le Principe d'Ouverture/Fermeture (OCP).

De plus, nous créons actuellement des objets du domaine dans notre resource, ce qui entraîne une violation du 
Principe d'Inversion de Dépendance (DIP). Dans notre prochaine itération, nous veillerons à tirer parti 
des DTOs et à ne passer que les arguments de ceux-ci aux couches supérieures. 
En résolvant ainsi le problème de DIP, nous espérons nous rapprocher davantage du SRP et de l'OCP. 
Notre resource ne fera que recevoir et envoyer des données au service, sans plus.

Actuellement, nos validations sont effectuées dans les objets de requêtes (DTO), ce qui n'est pas une pratique courante. 
Les requêtes sont censées être des types immuables qui ne font que représenter une requête entrée par un client. 
Cela dit, dans notre prochaine itération, la validation pour la création d'entités se fera dans les
factories, lesquelles seront appelées dans le service. Ainsi, le jour où nous voulons modifier la façon 
dont nous créons un restaurant, nous n'aurons qu'à modifier la factory correspondante.

Un autre point important est que notre ResourceHandler agit actuellement à la fois comme un orchestrateur et comme une base de données via un map qui garde les restaurants en mémoire. Nous souhaitons éliminer ce concept en 
introduisant une interface pour les options de persistance. Ainsi, nos bases de données n'auront qu'à suivre le contrat offert pour les implémenter. Ceci renforce davantage le principe d'Ouverture/Fermeture (OCP).

**Dans le diagramme d'architecture, les flèches rouges représentent les connexions que nous prévoyons supprimer dans la prochaine livraison, car elles enfreignent certains principes discutés précédemment.**