# projet-genie-logiciel
Ce projet est un projet scolaire dans le but d'apprendre les bonnes pratiques lors de la realisation de logiciel.  
Il consiste à la réalisation d'un logiciel de gestion de parking avec bornes de recharge.

# Installation avec maven

Il est recommandé d'installer le programme en utilisant maven.
Le projet est testé pour Maven 3.8.1 et Java 17.

Pour installer le projet lancer le commande : mvn package

# Mise en place de la base de données MySQL

Ce projet nécessite également une connexion à une base de données. Si la base de données n'est pas connecté le projet se contentera de continuer en local uniquement  
Pour mettre en place la base de donnée il vous faudra une base de donnée mySQL (Xampp pourra vous être utile) avec un schéma "parking"
Il faudra par ailleurs définir un utilisateur dans votre base de donnée qui aura les accès nécessaires puis noter son nom d'utilisateur et son mot de passe dans un fichier .env à la racine du projet. Un fichier .env.template est là pour vous aider à remplir le fichier .env.
Vous devez ensuite lancer le script sql/mySQL_init.sql sur ce schema.

# Execution

Une fois l'installation terminée, il y a désomais deux manière prévue d'utiliser le logiciel :

### Jar

Une archive jar sera disponible dans target/parking-<numVersion>.jar  
Vous pouvez recuperez cette jar et la lancer depuis n'importe où avec la commande java -jar <cheminVersLeJar>

### binaries

Des lauchers en binaries sont également disponible dans /bindist/bin. Il y a parking et parking.bat dépendant du système d'exploitation utilisé. Lancer ces fichiers lancera le logiciel.
