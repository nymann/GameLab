# GamesLab

### Running the game

##### Via make target

```sh
make run
```

##### Via maven

```sh
mvn install -f AsteroidsEntityFramework/pom.xml
mvn package -f AsteroidsEntityFramework/Core/pom.xml
java -jar bin/GameLab.jar
```

### Implemented features

- Random enemy spawn locations
- Player can shoot
- Collision detection using Pythagoras
- Player has 3 lives (loses 1 life when hit)
- Weapon cooldown based on game time
