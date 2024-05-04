# Marche Aléatoire


[<video width="320" height="240" controls>
  <source src="HIM_java" type="video/mp4">
  Your browser does not support the video tag.
</video>](https://github.com/MT444M/HIM-java/assets/107960014/105b1d84-ce1b-41ed-85be-885facfea66c
)


Ce projet Java a pour but de construire plusieurs réalisations d'une marche aléatoire de dimension N, avec N pouvant être égal à 1 (marche aléatoire 1D) ou 2 (marche aléatoire 2D). Pour cela, deux tableaux bi-dimensionnels de type entier, `MarcheX` et `MarcheY`, sont utilisés pour stocker les coordonnées x et y des échantillons de la marche aléatoire construite.

## Initialisation

Dans un premier temps, au niveau du constructeur, ces deux tableaux sont initialisés à la bonne taille et remplis avec des 0.

## Remplissage

Dans un second temps, ils sont remplis au moyen d'une méthode `remplit()` selon la règle de construction suivante :

* Pour une marche aléatoire 1D :
Les éléments du tableau `MarcheX` sont remplis avec des nombres entiers allant de 0 à N-1.
Les éléments du tableau `MarcheY` sont remplis selon la règle suivante : `MarcheY[j][i] = MarcheY[j][i-1] + 1` ou `MarcheY[j][i] = MarcheY[j][i-1] - 1`, en utilisant une loi binaire pour construire les éléments de la marche aléatoire.
* Pour une marche aléatoire 2D :
Les éléments des deux tableaux `MarcheX` et `MarcheY` sont remplis selon la règle suivante : `MarcheX[j][i] = MarcheX[j][i-1] + 1` ou `MarcheX[j][i] = MarcheX[j][i-1] - 1`, et `MarcheY[j][i] = MarcheY[j][i-1] + 1` ou `MarcheY[j][i] = MarcheY[j][i-1] - 1`, en utilisant une loi binaire pour construire les éléments de la marche aléatoire.

## IHM

Dans un deuxième temps, une IHM conviviale dotée d'un système de menu a été développée, permettant l'affichage des éléments de nos marches aléatoires selon deux modes de représentation : en mode texte ou en mode graphique. L'affichage des éléments de la marche aléatoire a été animé au moyen de Thread.

## Classe `CMarcheAleatoire`

La classe `CMarcheAleatoire` comporte 5 données membres privées :

* `N` de type entier pour la taille de la marche aléatoire
* `Realisation` de type entier pour le nombre de réalisations de marches aléatoires générées
* `cas` de type entier pour pouvoir distinguer le cas 1D du cas 2D
* `MarcheX` de type tableau bi-dimensionnel de type entier pour stocker les coordonnées x de chacune des réalisations de la marche aléatoire générée
* `MarcheY` de type tableau bi-dimensionnel de type entier pour stocker les coordonnées y de chacune des réalisations de la marche aléatoire générée

Au niveau des méthodes, la classe possède :

* un constructeur par défaut
* une méthode `int RandomBinaire()` permettant de retourner un nombre aléatoire entier valant 1 ou -1
* une méthode de remplissage des éléments constitutifs des marches aléatoires construites (`void Remplit()`)
* des méthodes publiques d'accès aux données membres de la classe (`get...()` et `set...()`)
* une méthode `void Affiche()` permettant d'afficher dans la console d'Eclipse les différentes marches aléatoires ainsi construites

## Main

Dans le main() de la classe `CVotreNomMain`, une variable de la classe `CMarcheAleatoire` a été déclarée, un objet de cette classe a été instancié, la méthode `Remplit()` a été appelée puis la méthode `Affiche()` ou `Affiche(int)`. Le programme a été compilé et exécuté.

## Extensions possibles

Des extensions possibles pour ce projet incluent l'animation réalisée également dans le mode graphique et non plus uniquement dans le mode texte pour pouvoir tracer les différentes réalisations les unes derrières les autres ou pour pouvoir tracer les points au fur et à mesure au sein d'une réalisation donnée.

---
