# Exercices - TP4



































































































# 4. Sécurité Logiciel

# Analyse de sécurité

Dans le monde exigeant du développement logiciel, où chaque ligne de code compte, la sécurité est un impératif absolu. Pour les développeurs comme nous, trouver des solutions efficaces pour protéger nos applications est essentiel.

C'est là qu'intervient Snyk. Un outil précieux dans notre boîte à outils, Snyk nous permet de traquer les vulnérabilités cachées dans nos dépendances externes. En l'intégrant à notre processus de développement, nous renforçons la sécurité de nos projets sans compromettre notre productivité.

Avec Snyk, chaque commit est examiné, chaque branche est protégée. Nous pouvons travailler en toute confiance, sachant que notre code est surveillé en permanence contre les menaces potentielles. Snyk nous fournit des informations précieuses sur les failles de sécurité et nous guide vers les correctifs appropriés.

## Snyk's web application interface. 

<img width="1440" alt="Screenshot 2024-04-20 at 9 34 46 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0326fb82-8e4f-4d54-b676-9dc5aad01c5f">

## Snyk est en mesure de vérifier nos dépendances à travers :
### - Le pom.xml:

<img width="1440" alt="Screenshot 2024-04-20 at 9 37 37 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0a3d498b-b443-46a2-ad94-14f3bc828350">
<img width="1440" alt="Screenshot 2024-04-20 at 9 37 52 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/55091d66-b9bc-42b9-acf7-5b501918211c">

### - Notre image Docker par le biais du DockerFile :

<img width="1440" alt="Screenshot 2024-04-20 at 9 35 00 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/b48bec45-b712-431d-ab55-16eb030b1abb">
<img width="1440" alt="Screenshot 2024-04-20 at 9 36 25 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/644302ca-367f-476e-9ca0-eeebf5ba543c">
<img width="1440" alt="Screenshot 2024-04-20 at 9 36 39 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0a50f3aa-c071-4ca7-b8f8-9308ef463009">

### - Une analyse de code :
<img width="1440" alt="Screenshot 2024-04-20 at 9 39 40 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/9a08bf3f-1007-4240-b700-f83843898446">

## Additionnellement, Snyk génère de manière autonome des Merges Requests suggérant des modifications aux dépendances. 
<img width="1440" alt="Screenshot 2024-04-20 at 9 40 25 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/42340736-07bc-4185-880c-14b558c1667d">

## CLI integration
### Nous avons aussi intégré Snyk dans nos pipelines, avec Snyk CLI, afin qu'il soit en mesure de retourner du feedback concernant les vulnérabilités lors du CI.
[
<img width="1920" alt="Screenshot 2024-04-30 at 1 55 07 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/a4042dff-107f-492d-aedb-2e79608a9a8c">
](url)

### Voici le lien vers lequel le CLI nous redirige afin de voir plus de détails concernant les vulnerabilités avec son usage dans le pipeline.

<img width="1920" alt="Screenshot 2024-04-30 at 1 54 42 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/bb3dedf3-2495-4423-a0ae-9df90961b68a">

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



