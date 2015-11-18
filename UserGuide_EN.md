


# Introduction #

The aim of MaToSo (Mahjong Tournament Software) is to manage mahjong's tournaments (Mahjong Competition Rules or Riichi)


Cela comprend le tirage des tables et leur édition, la saisie partielle ou complète des scores des joueurs, l'affichage des résultats et la mise à disposition des statistiques du tournoi et des joueurs ainsi que la génération du fichier des résultats pour envoi à l'EMA

**Recommandation : do not use buttons Previous and Next of the browser. Use links in the application**

## Start the application ##
  1. Launch the file **%install\_directory%/bin/startup.bat**
    1. A first window opens and closes
    1. A second window opens => the application server _Tomcat_ is launched (do not close it otherwise you will close MaToSo)
  1. Open an internet browser (eg : _Google Chrome_)
  1. Enter the URL : **`http://localhost:8080/matoso`**


# The tournament settings #

## The tournament ##
  * Enter the name of the tournament
  * Enable or disable team management
  * Chose rules (MCR or RCR)
Click _Create_

## The players ##
### Mass import ###
Tab _Players mass import_
  * Enter the number of rounds
  * Select the file with all players informations (a sample file is given)
Click _Submit_

### Manual creation ###
Tab _Number of players_
  * Enter the number of players (multiple of 4)
  * Enter the number of rounds
Click _Submit_

### Edition des joueurs ###
  * Cliquer sur le joueur pour l'éditer : Nom, prénom, n° EMA, nationalité, pseudo et équipe
Appuyer sur _Enregistrer les modifications_ pour sauvegarder

## Les sessions ##
### Edition des sessions ###
Navigation à gauche _Les sessions_

Onglet _Paramètres_
  * Cliquer sur la session pour l'éditer : Date, heure de début et heure de fin
Appuyer sur _Sauver_


# Les parcours des joueurs #

## Consultation ##
Navigation à gauche _Les parcours_

Les parcours de chaque joueurs apparaissent avec l'ensemble des informations : n° du joueur, nom du joueur, dates et heures des sessions (si remplies au paravent) et n° des tables de jeu du joueur

## Impression ##
L'impression des parcours est effectué avec le module d'impression du navigateur

## Le tirage ##
Navigation à gauche _Le tirage_
  * Permet de visualiser le tirage des tables


# Saisie des scores #
Navigation à gauche _Les sessions_
  * Sélectionner l'onglet de la session souhaitée
  * Sélectionner la table souhaitée

## Saisie rapide ##
  * Saisir uniquement le score final de chaque joueur de la table dans la ligne _score_ non grisée
Appuyer sur _Sauver_. Le nombre de points de table est calculé automatiquement. Un message de confirmation apparaît.

En cas d'erreur de calcul, un message d'avertissement apparaît.

Navigation à gauche _Les sessions_ pour revenir à la page des sessions.
La table est alors en italique pour indiquer une saisie rapide du score.

## Saisie complète ##
  * Activer la saisie complète en cliquant sur _Calcul automatique des points et scores de table_
  * La ligne _Score_ se grise et n'est plus modifiable

### Cas d'une victoire sur écart ###
  * Saisir la **valeur de la main** dans la colonne _Score_
  * Sélectionner le numéro du joueur **Gagnant** sur la première ligne de la colonne _Win/Lose_
  * Sélectionner le numéro du joueur **Donneur** sur la deuxième ligne de la colonne _Win/Lose_
### Cas d'une victoire tirée ###
  * Saisir la **valeur de la main** dans la colonne _Score_
  * Sélectionner **Tiré** dans la colonne _Tiré_
  * Sélectionner le numéro du joueur **Gagnant** sur la première ligne de la colonne _Win/Lose_
### Cas d'une partie nulle ###
  * Saisir la **valeur 0** dans la colonne _Score_
### Cas d'une pénalité ###
  * Ajouter la pénalité en bas du tableau sur la ligne _Pénalité_
  * Si une autre pénalité doit être ajoutée, cliquer sur _Ajouter une pénalité_
### Validation de la saisie ###
Appuyer sur _Sauver_ pour valider la saisie. Le score et le nombre de points de table sont calculés automatiquement. Un message de confirmation apparaît.

En cas d'erreur de calcul, un message d'avertissement apparaît.

Navigation à gauche _Les sessions_ pour revenir à la page des sessions.
La table est alors marquée d'une étoile pour indiquer une saisie complète du score.


# Les statistiques #
Navigation à gauche _Les statistiques_
## Statistiques du tournoi ##
Onglet _Statistiques du tournoi_
## Classement par joueur ##
Onglet _Classement par joueur_
## Classement par équipe ##
Onglet _Classement par équipe_


# Ajout d'une session finale #
_Fonctionnalité opérationnelle. Rédaction de l'utilisation en cours..._


# Présentation des résultats #
Navigation à gauche _Le classement_
  * Les classements individuel et par équipes défilent automatiquement
  * Cliquer sur le nom du tournoi pour quitter cet affichage


# Export des résultats #
Navigation à gauche _Les exports_
## Les jeux ##
Navigation à gauche _Les jeux_
  * Permet l'export au format CSV de l'ensemble des parties jouées
## Le classement ##
Navigation à gauche _Le classement_
  * Permet l'export au format CSV du classement du tournoi
## Les joueurs ##
Navigation à gauche _Les joueurs_
  * Permet l'export au format HTML des statistiques et des résultats individuels de chaque joueur
## EMA ##
Navigation à gauche _EMA_
  * Permet l'export au format CSV des résultats du tournoi pour envoi à l'EMA