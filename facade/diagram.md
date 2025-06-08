# Facade Pattern Diagram

```mermaid
classDiagram
    class HomeTheaterFacade {
        -Amplifier amplifier
        -DvdPlayer dvdPlayer
        -Projector projector
        -TheaterLights lights
        -Screen screen
        -PopcornPopper popper
        +watchMovie(String movie)
        +endMovie()
        +listenToMusic(String source)
        +playRadio(String station)
    }
    
    class Amplifier {
        +on()
        +off()
        +setVolume(int level)
        +setSurroundSound()
    }
    
    class DvdPlayer {
        +on()
        +off()
        +play(String movie)
        +stop()
        +eject()
    }
    
    class Projector {
        +on()
        +off()
        +setInput(String input)
        +wideScreenMode()
    }
    
    class TheaterLights {
        +on()
        +off()
        +dim(int level)
    }
    
    class Screen {
        +up()
        +down()
    }
    
    class PopcornPopper {
        +on()
        +off()
        +pop()
    }
    
    class Client {
        +main()
    }
    
    HomeTheaterFacade --> Amplifier : "uses"
    HomeTheaterFacade --> DvdPlayer : "uses"
    HomeTheaterFacade --> Projector : "uses"
    HomeTheaterFacade --> TheaterLights : "uses"
    HomeTheaterFacade --> Screen : "uses"
    HomeTheaterFacade --> PopcornPopper : "uses"
    Client --> HomeTheaterFacade : "interacts with"
```

The diagram illustrates:
- **HomeTheaterFacade**: Provides a simplified interface to the complex subsystem
- **Subsystem Classes**: Complex components (Amplifier, DvdPlayer, etc.) that work together
- **Client**: Only needs to interact with the facade, not individual subsystem components
- **Composition**: The facade contains references to all subsystem components 