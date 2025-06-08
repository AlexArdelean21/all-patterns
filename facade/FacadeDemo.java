// Complex subsystem classes
class Amplifier {
    public void on() {
        System.out.println("Amplifier: Turning on...");
    }
    
    public void off() {
        System.out.println("Amplifier: Turning off...");
    }
    
    public void setVolume(int level) {
        System.out.println("Amplifier: Setting volume to " + level);
    }
    
    public void setSurroundSound() {
        System.out.println("Amplifier: Setting surround sound...");
    }
}

class DvdPlayer {
    public void on() {
        System.out.println("DVD Player: Turning on...");
    }
    
    public void off() {
        System.out.println("DVD Player: Turning off...");
    }
    
    public void play(String movie) {
        System.out.println("DVD Player: Playing \"" + movie + "\"");
    }
    
    public void stop() {
        System.out.println("DVD Player: Stopping playback...");
    }
    
    public void eject() {
        System.out.println("DVD Player: Ejecting disc...");
    }
}

class Projector {
    public void on() {
        System.out.println("Projector: Turning on...");
    }
    
    public void off() {
        System.out.println("Projector: Turning off...");
    }
    
    public void setInput(String input) {
        System.out.println("Projector: Setting input to " + input);
    }
    
    public void wideScreenMode() {
        System.out.println("Projector: Setting wide screen mode...");
    }
}

class TheaterLights {
    public void on() {
        System.out.println("Theater Lights: Turning on...");
    }
    
    public void off() {
        System.out.println("Theater Lights: Turning off...");
    }
    
    public void dim(int level) {
        System.out.println("Theater Lights: Dimming to " + level + "%");
    }
}

class Screen {
    public void up() {
        System.out.println("Screen: Rolling up...");
    }
    
    public void down() {
        System.out.println("Screen: Rolling down...");
    }
}

class PopcornPopper {
    public void on() {
        System.out.println("Popcorn Popper: Turning on...");
    }
    
    public void off() {
        System.out.println("Popcorn Popper: Turning off...");
    }
    
    public void pop() {
        System.out.println("Popcorn Popper: Popping corn...");
    }
}

// Facade class that simplifies the complex subsystem
class HomeTheaterFacade {
    private Amplifier amplifier;
    private DvdPlayer dvdPlayer;
    private Projector projector;
    private TheaterLights lights;
    private Screen screen;
    private PopcornPopper popper;
    
    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd, Projector proj, 
                           TheaterLights lights, Screen screen, PopcornPopper popper) {
        this.amplifier = amp;
        this.dvdPlayer = dvd;
        this.projector = proj;
        this.lights = lights;
        this.screen = screen;
        this.popper = popper;
    }
    
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...\n");
        
        // Turn on all components in the right order
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        projector.setInput("DVD");
        amplifier.on();
        amplifier.setVolume(5);
        amplifier.setSurroundSound();
        dvdPlayer.on();
        dvdPlayer.play(movie);
        
        System.out.println("\nEnjoy your movie!");
    }
    
    public void endMovie() {
        System.out.println("\nShutting down movie theater...\n");
        
        // Turn off all components
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amplifier.off();
        dvdPlayer.stop();
        dvdPlayer.eject();
        dvdPlayer.off();
        
        System.out.println("Movie theater is shut down.");
    }
    
    // Additional convenience methods
    public void listenToMusic(String source) {
        System.out.println("Get ready for some music...\n");
        lights.dim(30);
        amplifier.on();
        amplifier.setVolume(8);
        amplifier.setSurroundSound();
        projector.setInput(source);
        System.out.println("Music system ready!");
    }
    
    public void playRadio(String station) {
        System.out.println("Tuning to radio...\n");
        amplifier.on();
        amplifier.setVolume(6);
        System.out.println("Playing radio station: " + station);
    }
}

// Client code
public class FacadeDemo {
    public static void main(String[] args) {
        // Create all the subsystem components
        Amplifier amp = new Amplifier();
        DvdPlayer dvd = new DvdPlayer();
        Projector projector = new Projector();
        TheaterLights lights = new TheaterLights();
        Screen screen = new Screen();
        PopcornPopper popper = new PopcornPopper();
        
        // Create the facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
            amp, dvd, projector, lights, screen, popper);
        
        System.out.println("=== Facade Pattern Demo ===\n");
        
        // Simple interface to complex operations
        homeTheater.watchMovie("The Matrix");
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        homeTheater.endMovie();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Another example
        homeTheater.listenToMusic("Bluetooth");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 