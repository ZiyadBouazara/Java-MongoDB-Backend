# Exercices - TP4

# Planification

## GitHub Project

![Local Image](../images/tp4/board.png)

## Milestone

![Local Image](../images/tp4/milestone.png)

## Issues

![Local Image](../images/tp4/issue1.png)
![Local Image](../images/tp4/issue2.png)
![Local Image](../images/tp4/issue3.png)

## Pull Requests

### PR1

![Local Image](../images/tp4/pr1.1.png)
![Local Image](../images/tp4/pr1.2.png)

### PR2

![Local Image](../images/tp4/pr2.1.png)
![Local Image](../images/tp4/pr2.2.png)

### PR3

![Local Image](../images/tp4/pr3.1T.png)
![Local Image](../images/tp4/pr3.2T.png)

## Arbre de Commits

![Local Image](../images/tp3/commit-tree.png)

# 1.Outils d'analyses de code (SCA)

## La qualité du code (clean code, bogues potentiels, optimisations, meilleures pratiques, etc.)
Pour garantir la qualité du code, nous avons utilisé l'outil Qodana, intégré avec IntelliJ IDEA, pour effectuer une analyse approfondie du code source. Qodana nous permet de détecter les problèmes potentiels, les violations des bonnes pratiques de codage, et d'appliquer des normes de qualité élevées à notre codebase. En intégrant Qodana avec IntelliJ IDEA, nous bénéficions d'une expérience de développement fluide et d'une capacité à corriger les problèmes directement dans notre environnement de développement intégré (IDE). Ceci assure que notre code respecte les normes de qualité et de performance attendues, contribuant ainsi à un développement robuste et fiable du projet.

### Analyse Sommaire:
![Local Image](../images/tp4/QodanaSommaire.png)
### Analyse Détaillée:
![Local Image](../images/tp4/QodanaDetail.png)

## La couverture des tests

Nous utilisons Codecov en combinaison avec Jacoco pour assurer une couverture complète des tests dans notre codebase. Jacoco génère des rapports détaillés sur la couverture de code, tandis que Codecov agrège ces données pour fournir une vue globale de la qualité de nos tests. Cette approche nous permet de vérifier que nos tests couvrent efficacement toutes les parties critiques de notre application, garantissant ainsi une meilleure qualité logicielle.

### Analyse Sommaire:
![Local Image](../images/tp4/CodeCovSommaire.png)
### Analyse Détaillée:
![Local Image](../images/tp4/CodeCovDetail.png)

# 2.Architecture
### Rôles des classes principales
![Local Image](../images/tp4/Architecture.png)

- ReservationConfiguration : Permet de configurer une réservation pour un restaurant. Cela permet de définir le nombre
  de places disponibles, le temps de réservation minimum et le temps de réservation maximum.
- ReservationRequest : Permet de faire une requête pour la réservation d'un restaurant.
- RestaurantRequest : Permet de faire une requête pour la création d'un restaurant.
- ...Response : Copie d'un objet complexe qui est retourné à l'utilisateur (DTO)
- RestaurantService/ReservationService : Font le pont entre les Resources et le Domaine en utilisant les Factories,
  Repositories,
  Assemblers et Validators pour Sauvegarder, Rechercher et Supprimer.
- Restaurant : Permet de créer un restaurant et d'avoir accès à ses attributs.
- Reservation : Permet de créer une réservation et d'avoir accès à ses attributs.
- RestaurantMongo : Objet desérializable de Restaurant qui est stocké dans la MongoDB.
- ReservationMongo : Objet desérializable de Reservation qui est stocké dans la MongoDB
- RestaurantResource/ReservationResource : Permet de gérer les requêtes pour les restaurants/reservations.
- InvalidParamExceptionMapper : Permet de gérer les exceptions InvalidParamException.
- MissingParamExceptionMapper : Permet de gérer les exceptions MissingParamException.
- NotFoundExceptionMapper : Permet de gérer les exceptions NotFoundException.
- Main : Permet de démarrer le serveur.
- FuzzySearch : Permet d'avoir l'objet de recherche de Restaurant à travers lequel on peut rechercher un restaurant par
  son nom, par l'heure à laquelle il est possible d'y aller et l'heure de départ.
  Utiliser une classe comme ceci permet une recherche plus flexible.
- Availabilities: Disponibilités d'un restaurant
- VisitTime: Représente le temps auquel un client peut aller au restaurant et en repartir. C'est pour cette raison que
  cette classe contient un from et un to.
- Customer: client qui effectue une Reservation.
- ...Repository: Interface pour Restaurant ou Reservation de la persistance.
- inMemory...Respository: Persistence de Restaurant ou Reservation au runtime.
- Mongo...Repository: Persistence MongoDB de Restaurant ou Reservation qui implémente l'interface ...Repository.

### Nos choix

Notre architecture repose sur deux entités principales : Restaurant et Réservation. Ce choix est motivé par les raisons
suivantes :

- Centralité des concepts: Les restaurants et les réservations sont les deux concepts centraux de notre application. La
  plupart des fonctionnalités de l'application tournent autour de ces deux concepts.
- Cohésion: Chaque entité regroupe des fonctionnalités et des données qui lui sont propres. Cela permet d'assurer une
  meilleure cohésion et une meilleure modularité du code.
- Simplicité: Cette architecture est simple et facile à comprendre. Cela facilite la maintenance et l'évolution de l'
  application.

**Structure des packages:**

L'architecture est divisée en quatre packages :

- Controllers: Ce package contient les contrôleurs qui gèrent les interactions avec l'interface utilisateur. Les
  contrôleurs reçoivent les requêtes de l'utilisateur, effectuent des opérations nécessaires en utilisant les services
  appropriés, puis renvoient les réponses à l'interface utilisateur.
- Service: Ce package contient les services qui encapsulent la logique métier de l'application. Les services agissent
  comme une couche intermédiaire entre les contrôleurs et le domaine, fournissant des fonctionnalités spécifiques à
  l'application.
- Domain: Ce package contient les modèles de domaine qui représentent les données de l'application et la logique métier.
  Le domaine encapsule la connaissance et les règles métier de l'application. Il ne devrait pas s'occuper des détails de
  stockage ou d'accès aux données.
- Infrastructure: Ce package représente la persistance de l'application. Elle possède deux types de persistance:
  inMemory et MongoDB.

Avantages de cette architecture:

- Modularité: L'architecture est modulaire et chaque package est indépendant des autres. Cela facilite la maintenance et
  l'évolution de l'application.
- Couplage faible: Le couplage entre les packages est faible. Cela permet de modifier un package sans affecter les
  autres.
- Testabilité: L'architecture est facile à tester car chaque package est indépendant des autres.
- Extensibilité: L'architecture est extensible et il est facile d'ajouter de nouvelles fonctionnalités.

### Relations  suspectes et des solutions potentielles

Dans notre architecture actuelle, chaque service possède les deux repositories (Restaurant et Réservation) au lieu de
posséder seulement celui de son entité. Cela peut sembler suspect et contrevenir aux principes de conception SOLID.

**Justification:**

Cette architecture est due à la forte relation entre les entités Restaurant et Réservation. Elles sont liées par des
références mutuelles:

Un restaurant peut avoir plusieurs réservations.
Une réservation est liée à un seul restaurant.

**Conséquences:**

Certaines opérations sur une entité nécessitent l'accès à l'autre entité:

- Suppression d'un restaurant: Lors de la suppression d'un restaurant, il est nécessaire de supprimer également ses
  réservations associées. Cela implique l'accès au repository Réservation depuis le service Restaurant.

**Solutions potentielles:**

**1. Refactoring du code:**

- Déplacer les repositories dans un module séparé partagé par les services Restaurant et Réservation.
- Adapter les services pour utiliser le module de repositories.

**2. Implémentation d'un pattern Domain Events:**

- Définir des événements de domaine pour les modifications des entités Restaurant et Réservation.
- Enregistrer les événements de domaine dans un journal d'événements.
- Les services écoutent les événements de domaine et effectuent les mises à jour nécessaires.


# 3.Outils de métrique

En ce qui concerne l'outil de mesure de métrique, nous utilisons Sentry pour capturer et signaler les erreurs et les bugs qui se produisent dans notre application, à l'exception de ceux générés par Jersey (comme les erreurs 404 pour des routes non existantes). Sentry nous permet de surveiller efficacement les erreurs en production et de recevoir des notifications en temps réel, nous aidant ainsi à identifier et à résoudre rapidement les problèmes critiques qui affectent la stabilité et la fiabilité de notre application.

### Analyse Sommaire:
![Local Image](../images/tp4/SentrySommaire.png)
### Analyse Détaillée:
![Local Image](../images/tp4/SentryDetail.png)

## **Open Source**
### **Questions**

**1. Nommez 3 avantages à contribuer à des projets open source en tant qu'entreprise et justifiez en quoi cela peut être bénéfique pour tous.**

En tant qu'entreprise, contribuer à des projets open source peut être bénéfique pour plusieurs raisons. Tout d'abord,
cela permet de réduire les coûts de développement en utilisant des solutions déjà existantes et en les adaptant à ses
besoins. De plus, une grande partie de contributeurs sont des gens très bien formés ayant des compétences poussées dans
le domaine, ce qui permet de bénéficier de leur expertise sans avoir à les embaucher. Cela permet donc de gagner du
temps et de l'argent pour l'entreprise qui peut se concentrer sur d'autres aspects de son activité.

Ensuite, avoir un produit open source permet de laisser libre cours à la communauté et aux utilisateurs du produit de
trouver et rajouter des fonctionnalités et cas d'utilisation précis et qui peuvent être très intéréssants
pour le produit final. Cela permet d'avoir un produit plus complet et plus adapté aux besoins des utilisateurs.

Enfin, cela permet de renforcer la réputation de l'entreprise en tant qu'acteur engagé dans la communauté open source.
Cela peut attirer des talents et des clients qui sont sensibles à ces valeurs et qui veulent travailler avec des
entreprises qui partagent leurs valeurs. Cela peut également permettre de se démarquer de la concurrence et de gagner
en visibilité sur le marché. Par exemple, parmi les compagnies utilisant le plus l'open source on peut facilement en
citer plusieurs qui sont très populaires et qui ont une très bonne réputation, comme Google, Facebook et Microsoft.

**2. Décrivez 3 défis qu'impose la mise en place d'un projet open source et justifiez.**

La mise en place d'un projet open source peut poser plusieurs défis. Tout d'abord, il faut s'assurer que le code est
de qualité et bien documenté pour faciliter la contribution de la communauté. Cela permet également d'avoir des
contributions de meilleure qualité et de diminuer les issues de support, car certaines choses dans le code pourraient
être incomprises du public. Il est impératif que les mainteneurs du projet énoncent clairement leur vision pour le
projet et les standards de qualité à respecter pour que les contributions soient acceptées et intégrées dans le projet.

Ensuite, il faut gérer les issues et les contributions de la communauté pour s'assurer qu'elles sont à jour et conformes
aux standards de l'entreprise. En effet, une liste de issues à jour permet non seulement d'attirer de nouveaux
contributeurs qui peuvent observer le sérieux du projet et le suivi effectué, mais aussi de maintenir un contrôle
intellectuel sur le projet malgré un grand nombre de contributeurs. Avoir de la documentation qui permet de comprendre
quels sont les différents rôles au sein du répértoire et les différents droits et pouvoirs de chacun permet d'avoir une
meilleure clarté. La communication se doit également d'être publique et documentée pour éviter les malentendus et les
conflits.

Enfin, l'instauration d'un code de conduite que ce soit pour les événements associés au projet, les commits des
contributeurs ou au niveau de la communication permet grandement au mainteneurs du projet de maintenir un contrôle et
surtout de les aider à prendre des décisions en cas de conflit ou de problème. Cela permet également de créer un
environnement sain et inclusif pour la communauté open source. En effet, déjà qu'il est très difficile pour un
mainteneur de dire Non à un contributeur, avoir des règles claires et des conséquences pré-établies permet de
faciliter la tâche du mainteneur à prendre des décisions dans certaines situations.

**3. Quelle information vous a-t-elle le plus surprise à propos de l'open source?**

Parmi les choses qui nous ont le plsu impressionné sur l'open source est le fait qu'il paraît que le open source est
tout autant, si ce n'est plus sécuritaire que le code propriétaire. En effet, le fait que le code soit ouvert permet à
la communauté de le regarder et de le tester, ce qui permet de trouver et de corriger les failles de sécurité plus
rapidement. De plus, le fait que le code soit ouvert permet également de vérifier que le code ne contient pas de
backdoors ou de failles intentionnelles, ce qui peut être un risque avec le code propriétaire. Cela permet donc de
renforcer la sécurité du code et de réduire les risques pour les utilisateurs. Cela peut être un argument de poids pour
convaincre les entreprises de passer à l'open source et de contribuer à des projets open source.

[Top 10 Misconceptions about open source software]
(https://www.hotwaxsystems.com/hotwax-blog/ofbiz/the-top-10-misconceptions-about-open-source-software)

### **Notre Code de conduite**

Notre code de conduite est adapté de la [Contributor Covenant]
(https://www.contributor-covenant.org/version/1/4/code-of-conduct.html) version 1.4. Nous avons choisi ce modèle
principalement, car il est très complet et qu'il est utilisé par de nombreux projets open source. Il est important pour
nous de créer un environnement sain et inclusif pour notre communauté open source, et ce code de conduite nous permet
de poser des bases solides pour cela. Nous avons également ajouté quelques éléments spécifiques à notre projet pour
répondre à nos besoins spécifiques et à notre vision du projet.

### **License**

Nous avons choisi la licence MIT pour notre projet open source. Nous avons choisi cette licence principalement, car elle
est très permissive et permet à quiconque d'utiliser, de modifier et de distribuer notre code sans restrictions. De plus, la licence
MIT est très populaire et bien connue, ce qui facilite la compréhension et l'acceptation de notre projet par les
utilisateurs et les contributeurs qui voudraient potentiellement l'utiliser. Enfin, la licence MIT est compatible avec
de nombreuses autres licences open source, ce qui permet de faciliter l'intégration de notre projet dans d'autres
projets open source dans le futur. Dans notre cas, il n'a pas été nécessaire pour nous de choisir une license comme
Apache 2.0, car nous ne sommes pas une entreprise et nous n'avons pas de brevets à protéger. Nous n'avions pas non plus
à utiliser une license GPL, car nous ne voulions pas imposer de restrictions sur l'utilisation du code par les
utilisateurs et les contributeurs.

[The Legal Side Of Open Source](https://opensource.guide/legal/#which-open-source-license-is-appropriate-for-my-project)

# 4. Sécurité Logiciel

# Analyse de sécurité

Dans le monde exigeant du développement logiciel, où chaque ligne de code compte, la sécurité est un impératif absolu. Pour les développeurs comme nous, trouver des solutions efficaces pour protéger nos applications est essentiel.

C'est là qu'intervient Snyk. Un outil précieux dans notre boîte à outils, Snyk nous permet de traquer les vulnérabilités cachées dans nos dépendances externes. En l'intégrant à notre processus de développement, nous renforçons la sécurité de nos projets sans compromettre notre productivité.

Avec Snyk, chaque commit est examiné, chaque branche est protégée. Nous pouvons travailler en toute confiance, sachant que notre code est surveillé en permanence contre les menaces potentielles. Snyk nous fournit des informations précieuses sur les failles de sécurité et nous guide vers les correctifs appropriés.

## Snyk's web application interface. 

![Local Image](../images/tp4/img1.png)

## Snyk est en mesure de vérifier nos dépendances à travers :
### - Le pom.xml:

![Local Image](../images/tp4/img2.png)
![Local Image](../images/tp4/img3.png)

### - Notre image Docker par le biais du DockerFile :

![Local Image](../images/tp4/img4.png)
![Local Image](../images/tp4/img5.png)
![Local Image](../images/tp4/img6.png)

### - Une analyse de code :
![Local Image](../images/tp4/img7.png)

## Additionnellement, Snyk génère de manière autonome des Merges Requests suggérant des modifications aux dépendances. 
![Local Image](../images/tp4/img8.png)

## CLI integration
### Nous avons aussi intégré Snyk dans nos pipelines, avec Snyk CLI, afin qu'il soit en mesure de retourner du feedback concernant les vulnérabilités lors du CI.
![Local Image](../images/tp4/img9.png)

### Voici le lien vers lequel le CLI nous redirige afin de voir plus de détails concernant les vulnerabilités avec son usage dans le pipeline.
![Local Image](../images/tp4/img10.png)

# Vers des pratiques plus sécures


## Voici trois pratiques clés à intégrer dans un processus de développement logiciel pour réduire les risques de vulnérabilités :

### DevSecOps :
Intégrer la sécurité dès les premières étapes du processus de développement (DevOps), plutôt que de la considérer comme une étape distincte à la fin du cycle de vie du logiciel.
Automatiser les tests de sécurité tout au long du pipeline de développement pour détecter et corriger les vulnérabilités dès qu'elles apparaissent.
Encourager une culture de responsabilité partagée entre les développeurs, les opérations et les équipes de sécurité pour garantir que la sécurité est une priorité à chaque étape du développement.
### SSDLC (Secure Software Development Life Cycle) :
Mettre en place un processus de développement logiciel qui intègre des pratiques de sécurité dès le début, en identifiant et en évaluant les risques de sécurité à chaque étape du cycle de vie du logiciel.
Effectuer une analyse de sécurité statique et dynamique du code pour identifier les vulnérabilités potentielles dès les phases de conception et de développement.
Intégrer des mesures de sécurité telles que l'authentification, l'autorisation et le chiffrement des données dès la conception de l'architecture logicielle.
### Software Supply Chain Security :
Identifier et évaluer les dépendances externes utilisées dans le projet pour s'assurer qu'elles proviennent de sources fiables et sécurisées.
Mettre en œuvre des mécanismes de vérification et de validation des composants tiers pour détecter les vulnérabilités connues et les risques de compromission de la chaîne logicielle.
Mettre à jour régulièrement les dépendances et surveiller les avis de sécurité pour réagir rapidement aux nouvelles vulnérabilités identifiées.


Intégrer ces pratiques dans un processus de développement logiciel contribue à réduire les risques de vulnérabilités et à renforcer la sécurité des applications tout au long de leur cycle de vie.





