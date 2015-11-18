# Installation of MaToSo #

  1. Télécharger le fichier  **matosoyyyymmdd.zip** tagué _Featured_ (yyyymmdd = date de la version) depuis la page _Downloads_ de notre projet
  1. Le décompresser dans un répertoire (par exemple _d:/_)
  1. Lancer le fichier **%install\_directory%/bin/startup.bat**
    1. Une première fenêtre s'ouvre et se ferme
    1. Une seconde fenêtre s'ouvre => l'application serveur _Tomcat_ est lancée (ne pas la fermer sinon vous fermez MaToSo)
  1. Ouvrir un navigateur (par exemple : _Google Chrome_)
  1. Entrer l'URL : **`http://localhost:8080/matoso`**

_MaToSo_ est maintenant installé et prêt à être utilisé !

<a href='Hidden comment: 
=Installation matoso pour les développeurs=

Ce document détaille la procédure d’installation du logiciel de gestion de
tournoi de Mahjong matoso à partir de son fichier d’extension WAR.
<br/>
Cette documentation a été rédigée à destination des utilisateurs Windows mais la procédure à suivre sous un système de type Unix
est similaire à quelques adaptations de chemins et de commandes près.

==Les pré-requis==
===Le serveur d"application===
Le serveur de référence de matoso est Tomcat version 6.0.20. L’utilisation d’une
autre version de Tomcat ou d’un autre serveur n’est pas garantie étant donné que la norme J2EE varie dans le temps et selon les constructeurs.
Le serveur est téléchargeable sur http://tomcat.apache.org/.

===L"application Matoso===
Celui-ci se compose d"un fichier WAR. Le format WAR peut être décompressé avec winzip, winrar, 7zip, etc… Le WAR est téléchargeable sur la homepage du site ou dans la section téléchargement pour les toutes les versions.

==L"installation==
===Tomcat===
Sous Windows, vous avez le choix entre un installateur de type exe ou un zip à décompresser.
Un pré-requis obligatoire fonctionnement de Tomcat est l’initialisation d’une
variable d’environnement CATALINA_HOME.
Pour créer une variable d’environnement sous Windows, aller dans
```
Démarrer > Paramètres > Panneau de configuration > Système > Onglet Avancé > Bouton Variables d’environnement
```
Dans le cadre Variables systèmes créer la variable CATALINA_HOME La valeur
de la variable d’environnement doit pointer vers le chemin de votre
installation de Tomcat, par ex.
```
C:\Program Files\apache-tomcat-6.0.20
```
Pour démarrer Tomcat, vous pouvez passer par les scripts .bat qui se
trouvent dans le répertoire \bin de l’installation ou si vous
avez utilisé l’installateur, passer par les utilitaires du menu
```
Démarrer > Programmes > Tomcat >
```
Une fois Tomcat lancé, ouvrir un navigateur et se connecter à l’url :
```
http://localhost:8080
```
Vous devriez alors visualiser la page d’accueil de Tomcat vous disant que votre installation est fonctionnelle.

===Matoso===
Décompresser le fichier war dans le répertoire %CATALINA_HOME
%\webapps ou %CATALINA_HOME% est le chemin vers votre installation
de Tomcat.
Modifier le fichier %CATALINA_HOME%\webapps\matoso\WEB-
INF\classes\hibernate.cfg.xml pour renseigner le chemin de la base
DERBY :
```
<propertyname="connection.url">jdbc:derby:C:\devs\projects\MATOS
O_DB ;create=true</property>
```
L’option ;create=true pourra être enlevée ultérieurement, une fois la
base effectivement créée. Modifier également le mode de fonctionnement
d’Hibernate par rapport au schéma pour mettre create :
```
<!-- validate, update, create, create-drop -->
<property name="hbm2ddl.auto">create</property>
```

L’option create permet à Hibernate de créer automatiquement toutes les
tables de matoso. L"option validate quand les tables seront créées. L’option
create-drop est une variante qui permet de recréer les tables de matoso
Fédération Française de Mahjong
Installation matoso
5/5
à chaque redémarrage. Cela peut être utile dans le cadre d’une recette
ou l’application doit être vide à chaque redémarrage.

Redémarrer Tomcat. matoso devrait être accessible via l’url :
http://localhost:8080/matoso.

<wiki:comment>