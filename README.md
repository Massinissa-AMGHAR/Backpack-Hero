# Backpack Hero

Ma version en Java du jeu **Backpack Hero**, codée avec mon binôme pendant notre Licence Informatique à l'Université Gustave Eiffel.

Le principe : on explore un donjon étage par étage, on affronte des ennemis au tour par tour, et surtout on doit bien organiser son sac à dos, parce que tout ce qu'on ramasse doit y trouver sa place !

## Le jeu

Le donjon compte trois étages. En avançant de salle en salle, on peut tomber sur :

- des **ennemis** à combattre ;
- des **trésors** qui donnent de nouveaux objets ;
- un **marchand** pour vendre ce dont on n'a plus besoin ;
- un **guérisseur** pour récupérer des points de vie.

Au fil de la partie, le héros gagne de l'expérience et peut agrandir son sac. Les meilleurs scores sont enregistrés dans un **Hall of Fame**.

## Comment le jeu est construit

On a codé le jeu entièrement en Java, en découpant le code en plusieurs parties :

- **`map`** : le donjon, ses étages et ses salles.
- **`character`** : le héros, les ennemis, le marchand et le guérisseur. Pour le héros, on a séparé les combats, la gestion de l'inventaire et la progression dans des classes différentes, pour éviter d'avoir une seule énorme classe.
- **`inventory`** : le sac à dos, qui est le cœur du jeu. C'est une grille dans laquelle chaque objet a sa propre forme, peut être tourné et possède une rareté.
- **`interaction`** : certains objets réagissent à ce qui est placé à côté d'eux dans le sac. Par exemple, une armure peut protéger davantage selon ses voisins. Chaque interaction a sa propre classe, ce qui permet d'en ajouter de nouvelles facilement.
- **`effect`** : les effets qui durent plusieurs tours, comme le poison.
- **`graphics`** : tout l'affichage, réalisé avec **Zen**, une bibliothèque graphique fournie par la fac. Chaque écran (carte, combat, marchand, sac…) a sa propre vue, et un contrôleur fait le lien entre les clics du joueur et le jeu.

Le plus gros défi a été de faire communiquer toutes ces parties entre elles sans que le code devienne un gros bazar.

On a aussi mis en place un build avec **Ant**, et on vous a laissé un manuel utilisateur et une doc développeur dans le dossier `docs`, histoire que vous ne soyez pas perdus !
