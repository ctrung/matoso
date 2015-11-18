# Installation de MaToSo #

  1. Télécharger le fichier "matoso\_install.zip"
  1. Désarchiver le fichier dans un dossier (par exemple dans "d:/")
  1. Cliquer 2 fois sur le fichier %Dossier\_d\_installation%/bin/startup.bat
    1. Une fenêtre s'ouvre et se ferme
    1. Une 2ème fenêtre s'ouvre => le serveur d'applications Tomcat est lancé
  1. Ouvrir un navigateur (par exemple : Google Chrome)
  1. Ecrire dans la barre d'adresse : http://localhost:8080/matoso
    * Le logiciel "MaToSo" s'affiche




<a href='Hidden comment: 
avant

=Installation MaToSo=
Ce document détaille la procédure d’installation du logiciel de gestion de tournoi de Mahjong MaToSo à partir de son fichier d’extension WAR.
Cette documentation a été rédigée à destination des utilisateurs Windows mais la procédure à suivre sous un système de type Unix est similaire à quelques adaptations de chemins et de commandes près.

=Les pré-requis=
==Le serveur d"application==
Le serveur de référence de MaToSo est Tomcat version 6.0.xx. L’utilisation d’une autre version de Tomcat ou d’un autre serveur n’est pas garantie étant donné que la norme J2EE varie dans le temps et selon les constructeurs.
Le serveur est téléchargeable sur la page [http://tomcat.apache.org/download-60.cgi download] d"Apache Tomcat

Téléchargez cet installeur si vous êtes sous Windows: _32-bit/64-bit Windows Service Installer_


==L"application MaToSo==
Celui-ci se compose d"un fichier avec une extension WAR. Le format WAR peut être décompressé avec winzip, winrar, 7zip, etc…
Ce fichier est à télécharger sur la [http://code.google.com/p/matoso/ page d"accueil du site] ou dans la [http://code.google.com/p/matoso/downloads/list section téléchargement] pour les toutes les versions.
Téléchargez également le modèle de base de données MATOSO_DB.zip dans la [http://code.google.com/p/matoso/downloads/list section téléchargement].

=L"installation=
==Tomcat==
Lancez l"exécutable précédemment téléchargé.
* Indiquez un chemin d"installation sans espace, par exemple: _C:\Tomcat6.0_ (c"est le chemin que nous utiliserons pour la suite de ce wiki)
* Tomcat Amdinistrator Login
* indiquer un login et un mot de passe, nous aurons besoin la partie administration lors de l"installation de Matoso (par exemple: admin, admin)
* Java Virtual Machine
* laisser le chemin par défaut: _C:\Program Files\Java\jre6_

Lancez Apache Tomcat, une icône avec une flèche verte indique que Apache Tomcat fonctionne.

Pour vérifier le bon fonctionnement de Tomcat, lancer l"adresse _http://localhost:8080_ dans votre navigateur préféré.
Vous devriez voir apparaitre la page d"accueil de Tomcat.

==Variables d"environnement==
Il est nécessaire de créer 2 variables d"environnement qui serviront de liens avec l"application.
Pour créer une variable d’environnement sous Windows, aller dans
```
Démarrer > Paramètres > Panneau de configuration > Système > Onglet Avancé > Bouton Variables d’environnement
```
Dans le cadre Variables systèmes créez la variable *CATALINA_HOME*. La valeur
de la variable d’environnement doit être le chemin de votre
installation de Tomcat, par ex.
```
C:\Tomcat6.0
```
Créer ensuite la variable *JRE_HOME* avec comme valeur le chemin de votre installation Java, par ex.
```
C:\Program Files\Java\jre6
```

==MaToSo==
===Fichier war===
Renommer le fichier avec l"extension WAR téléchargé précédemment en _matoso.war_

* sur la page d"accueil de Tomcat sur votre navigateur, cliquez sur _Tomcat Manager_ situé dans le cadre à gauche.
* Entrez l"identifiant et le mot de passe de l"administrateur
* dans le cadre _Fichier WAR à déployer_ sélectionner le fichier matoso.war
* cliquez sur _Déployer_
* vous pouvez voir apparaitre matoso dans le cadre _Applications_

===Fichier de base de données===
Décompresser le fichier _MATOSO_DB.zip_ et le mettre sous
```
C:\Tomcat6.0
```

===Fichier de configuration===
Avant la première utilisation, ouvrir le fichier
```
C:\Tomcat6.0\webapps\matoso\WEB-INF\classes\hibernate.cfg.xml
```

Modifiez la ligne 12
```
<property name="connection.url">jdbc:derby:C:\Tomcat6.0\MATOSO_DB;create=true</property>
```
Modifiez la ligne 35
```
<property name="hbm2ddl.auto">create</property>
```
Cela va permettre d"initialiser la base de donnée.
Sauvegardez le fichier.

Lancez l"adresse
```
http://localhost:8080/matoso
```
La page d"accueil de MaToSo apparait.

Ouvrir le fichier
```
C:\Tomcat6.0\webapps\matoso\WEB-INF\classes\hibernate.cfg.xml
```

Modifiez la ligne 12
```
<property name="connection.url">jdbc:derby:C:\Tomcat6.0\MATOSO_DB</property>
```
Modifiez la ligne 35
```
<property name="hbm2ddl.auto">validate</property>
```
Cela va permettre de valider la base de données. Vous n"avez plus besoin de modifier ce fichier.

=L"utilisation=
Vous pouvez maintenant utiliser MaToSo à l"adresse
```
http://localhost:8080/matoso
```

'></a>