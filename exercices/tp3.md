# Exercices - TP3

# Planification

## GitHub Project

![Local Image](../images/tp3/board.png)

## Milestone

![Local Image](../images/tp3/milestone.png)

## Issues

![Local Image](../images/tp3/issue1.png)
![Local Image](../images/tp3/issue2.png)
![Local Image](../images/tp3/issue3.png)

## Pull Requests

### PR1

![Local Image](../images/tp3/pr1.png)
![Local Image](../images/tp3/pr1.2.png)

### PR2

![Local Image](../images/tp3/pr2.png)
![Local Image](../images/tp3/pr2.2.png)

### PR3

![Local Image](../images/tp3/pr3.png)
![Local Image](../images/tp3/pr3.2.png)
![Local Image](../images/tp3/pr3.3.png)

## Arbre de Commits

![Local Image](../images/tp3/commit-tree.png)

# Rétrospective - Intégration continue et tests

### Pipeline CI

#### 1. Temps passé à vérifier et tester manuellement le code avant l'implémentation du pipeline de tests automatisés :

Avant l'implémentation du pipeline de tests automatisés, nous estimions le temps passé à vérifier et tester manuellement
le code entre une heure et deux heures. Ce temps était souvent consacré à des activités telles que l'exécution de
scénarios de test, la vérification de la conformité aux spécifications, la saisie de données de test et l'analyse des
résultats. Cette estimation incluait également le temps nécessaire pour répéter ces étapes en cas d'erreurs ou de bugs
détectés lors des tests manuels.

#### 2. Temps passé à faire les vérifications après l'implémentation du CI :

Après l'implémentation du CI, le temps passé à faire les vérifications a été significativement réduit. Avec la
vérification automatisée du formatage, de la compilation et des tests à chaque intégration de code, le processus de
validation est devenu plus rapide et plus efficace. En moyenne, nous constatons que les vérifications nécessitent
maintenant généralement quelques minutes seulement.

#### 3. Points positifs apportés par le CI à notre processus :

1. Gain de temps : Le CI automatise les tâches répétitives et réduit le temps nécessaire pour valider chaque
   modification de code. Avec l'automatisation des étapes telles que la vérification du formatage, la compilation et les
   tests, nous gagnons des heures précieuses auparavant consacrées à des tâches manuelles.


2. Amélioration de la qualité du code : Le CI contribue à une amélioration constante de la qualité du code en détectant
   rapidement les erreurs et les problèmes potentiels. En exécutant des tests à chaque intégration de code, il permet
   d'identifier et de corriger les bugs plus tôt dans le processus de développement, réduisant ainsi les risques
   d'anomalies dans le produit final.


3. Meilleure fiabilité : Le CI garantit une plus grande fiabilité du processus de déploiement en automatisant les étapes
   de vérification et de test. En s'assurant que chaque modification de code est soumise à un ensemble complet de tests,
   il réduit les risques d'introduire des erreurs dans les versions livrées aux utilisateurs.

#### 4. Inconvénients potentiels du pipeline CI et leurs impacts sur le processus, le produit et/ou l'équipe :

1. Fragilité:
    - Impact sur le processus: Le pipeline peut être sensible aux changements dans l'environnement de
      développement, ce qui
      peut le rendre fragile et sujet à des pannes.
    - Impact sur le produit: Des pannes du pipeline peuvent retarder la livraison du produit et affecter sa qualité.
    - Impact sur l'équipe: L'équipe peut être amenée à passer du temps à déboguer et à réparer le pipeline au lieu de se
      concentrer sur le développement du produit.
2. Complexité accrue:

    - Impact sur le processus: Augmentation du temps de configuration et de maintenance du pipeline. Nécessite une
      expertise
      technique pour la gestion et la résolution des problèmes.
    - Impact sur le produit: Risque accru d'erreurs et de bugs dans le pipeline, pouvant affecter la qualité du produit
      final.
    - Impact sur l'équipe: Nécessite une formation et un investissement en temps pour l'équipe afin de comprendre et d'
      utiliser le pipeline.

### Tests

#### 1. Proportion du temps consacré à l'implémentation du code fonctionnel par rapport aux tests et son évolution au fil du temps :

On passe environ 70 % du temps à implémenter le code fonctionnel et 30 % à écrire des tests. Cette proportion évolue au
fil du temps, avec une tendance à augmenter la part des tests. Cela est dû à plusieurs facteurs :

Prise de conscience de l'importance des tests: On a compris que les tests sont essentiels pour garantir la qualité et la
robustesse du code.

On constate donc une transition vers une proportion plus équilibrée entre l'implémentation du code
fonctionnel et l'écriture des tests, voire une augmentation de la part des tests au fil du temps. Cela reflète une
évolution positive vers des pratiques de développement plus rigoureuses et orientées vers la qualité.

#### 2. Impact de l'implémentation de tests sur la charge de travail et son effet sur le processus :

L'implémentation de tests a naturellement augmenté la charge de travail. Cela a eu un impact sur notre processus de
développement de plusieurs manières :

- Augmentation de la taille des issues/PRs: Les issues et les pull requests (PRs) incluent désormais non seulement le
  code
  fonctionnel, mais aussi les tests associés.

- Allongement du temps d'implémentation: Le temps nécessaire à l'implémentation d'une fonctionnalité est désormais plus
  long car il faut également écrire les tests.

- Planification plus rigoureuse: Il est important de planifier les tests en amont afin de garantir qu'ils couvrent tous
  les aspects de la fonctionnalité.

#### 3. Niveau de confiance dans le code suite à l'implémentation de tests :

Nous avons plus confiance en notre code maintenant que on a des tests. Les tests nous permettent de nous assurer que le
code
fonctionne comme prévu et qu'il est robuste face aux erreurs. En plus, nous avons la confirmation que les ajouts de
nouvelles feature ou simplement le code que l'on rajoute n'introduit pas de bris aux features précédentes. Cela nous
donne la tranquillité d'esprit de savoir que le
code est moins susceptible de causer des problèmes.

#### 4. Stratégies pour améliorer l'état des tests :

- Écrire plus de tests: Il est important d'avoir un bon niveau de couverture de test pour garantir la qualité du code.
- Améliorer la qualité des tests: Les tests doivent être bien écrits et faciles à comprendre. Ils doivent également être
  pertinents et tester les aspects importants de la fonctionnalité.
- Ajouter des métriques de tests: ajouter des outils de calcul de coverage et ajouter une étape à notre CI pour vérifier
  qu'un minimum de coverage (par exemple: 80%) est respecté pour qu'il réussise.il est également important de suivre les
  métriques de test et de les analyser
  régulièrement. Cela permet d'identifier les points faibles de la couverture de test et de prendre les mesures
  nécessaires pour les
  améliorer. On pourrait également analyser le temps d'exécution des tests en surveillant la durée nécessaire pour
  exécuter l'ensemble des tests. Une augmentation significative du temps d'exécution peut indiquer des problèmes de
  performance ou une surcharge de tests. En effet, dans notre cas, l'ajout des tests d'intégration de la persistence
  avec des Testcontainer a considérablement augmenté le runtime de nos tests. Une solution serait de créer et utiliser
  le même container mais en rafraichissant la DB en droppant la table.
