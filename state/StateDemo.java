// State interface
interface PlayerState {
    void play(MediaPlayer player);
    void pause(MediaPlayer player);
    void stop(MediaPlayer player);
    void next(MediaPlayer player);
    void previous(MediaPlayer player);
    String getStateName();
}

// Context class
class MediaPlayer {
    private PlayerState currentState;
    private String currentTrack;
    private int trackPosition; // in seconds
    private java.util.List<String> playlist;
    private int currentTrackIndex;
    private int volume;
    
    public MediaPlayer() {
        this.playlist = new java.util.ArrayList<>();
        this.currentTrackIndex = 0;
        this.trackPosition = 0;
        this.volume = 50;
        this.currentState = new StoppedState();
        
        // Add some sample tracks
        playlist.add("Song 1 - Artist A");
        playlist.add("Song 2 - Artist B");
        playlist.add("Song 3 - Artist C");
        playlist.add("Song 4 - Artist D");
        playlist.add("Song 5 - Artist E");
        
        if (!playlist.isEmpty()) {
            this.currentTrack = playlist.get(0);
        }
    }
    
    public void setState(PlayerState state) {
        System.out.println("🔄 State changed to: " + state.getStateName());
        this.currentState = state;
    }
    
    public void play() {
        currentState.play(this);
    }
    
    public void pause() {
        currentState.pause(this);
    }
    
    public void stop() {
        currentState.stop(this);
    }
    
    public void next() {
        currentState.next(this);
    }
    
    public void previous() {
        currentState.previous(this);
    }
    
    // Helper methods
    public void startPlayback() {
        System.out.println("▶️  Now playing: " + currentTrack);
        System.out.println("   Position: " + formatTime(trackPosition));
    }
    
    public void pausePlayback() {
        System.out.println("⏸️  Playback paused at: " + formatTime(trackPosition));
    }
    
    public void stopPlayback() {
        trackPosition = 0;
        System.out.println("⏹️  Playback stopped");
    }
    
    public void nextTrack() {
        if (currentTrackIndex < playlist.size() - 1) {
            currentTrackIndex++;
            currentTrack = playlist.get(currentTrackIndex);
            trackPosition = 0;
            System.out.println("⏭️  Next track: " + currentTrack);
        } else {
            System.out.println("🔚 Already at last track");
        }
    }
    
    public void previousTrack() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--;
            currentTrack = playlist.get(currentTrackIndex);
            trackPosition = 0;
            System.out.println("⏮️  Previous track: " + currentTrack);
        } else {
            System.out.println("🔚 Already at first track");
        }
    }
    
    public void showStatus() {
        System.out.println("\n📱 Media Player Status:");
        System.out.println("   State: " + currentState.getStateName());
        System.out.println("   Track: " + currentTrack + " (" + (currentTrackIndex + 1) + "/" + playlist.size() + ")");
        System.out.println("   Position: " + formatTime(trackPosition));
        System.out.println("   Volume: " + volume + "%");
    }
    
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
    
    // Simulate time progression
    public void simulatePlayback(int seconds) {
        if (currentState instanceof PlayingState) {
            trackPosition += seconds;
            System.out.println("🎵 Playing... (" + formatTime(trackPosition) + ")");
            
            // Simulate track ending (assume 3 minutes per track)
            if (trackPosition >= 180) {
                System.out.println("🎵 Track finished");
                next();
            }
        }
    }
    
    // Getters
    public String getCurrentTrack() { return currentTrack; }
    public int getTrackPosition() { return trackPosition; }
    public PlayerState getCurrentState() { return currentState; }
    public int getCurrentTrackIndex() { return currentTrackIndex; }
    public int getPlaylistSize() { return playlist.size(); }
}

// Concrete States
class StoppedState implements PlayerState {
    @Override
    public void play(MediaPlayer player) {
        player.startPlayback();
        player.setState(new PlayingState());
    }
    
    @Override
    public void pause(MediaPlayer player) {
        System.out.println("❌ Cannot pause when stopped");
    }
    
    @Override
    public void stop(MediaPlayer player) {
        System.out.println("ℹ️  Already stopped");
    }
    
    @Override
    public void next(MediaPlayer player) {
        player.nextTrack();
        // Stay in stopped state
    }
    
    @Override
    public void previous(MediaPlayer player) {
        player.previousTrack();
        // Stay in stopped state
    }
    
    @Override
    public String getStateName() {
        return "Stopped";
    }
}

class PlayingState implements PlayerState {
    @Override
    public void play(MediaPlayer player) {
        System.out.println("ℹ️  Already playing");
    }
    
    @Override
    public void pause(MediaPlayer player) {
        player.pausePlayback();
        player.setState(new PausedState());
    }
    
    @Override
    public void stop(MediaPlayer player) {
        player.stopPlayback();
        player.setState(new StoppedState());
    }
    
    @Override
    public void next(MediaPlayer player) {
        player.nextTrack();
        player.startPlayback();
        // Stay in playing state
    }
    
    @Override
    public void previous(MediaPlayer player) {
        player.previousTrack();
        player.startPlayback();
        // Stay in playing state
    }
    
    @Override
    public String getStateName() {
        return "Playing";
    }
}

class PausedState implements PlayerState {
    @Override
    public void play(MediaPlayer player) {
        player.startPlayback();
        player.setState(new PlayingState());
    }
    
    @Override
    public void pause(MediaPlayer player) {
        System.out.println("ℹ️  Already paused");
    }
    
    @Override
    public void stop(MediaPlayer player) {
        player.stopPlayback();
        player.setState(new StoppedState());
    }
    
    @Override
    public void next(MediaPlayer player) {
        player.nextTrack();
        player.setState(new StoppedState());
    }
    
    @Override
    public void previous(MediaPlayer player) {
        player.previousTrack();
        player.setState(new StoppedState());
    }
    
    @Override
    public String getStateName() {
        return "Paused";
    }
}

// Additional state for buffering (common in streaming)
class BufferingState implements PlayerState {
    private PlayerState previousState;
    
    public BufferingState(PlayerState previousState) {
        this.previousState = previousState;
    }
    
    @Override
    public void play(MediaPlayer player) {
        System.out.println("⏳ Buffering... please wait");
        // Simulate buffering complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("✅ Buffering complete");
        player.setState(new PlayingState());
        player.startPlayback();
    }
    
    @Override
    public void pause(MediaPlayer player) {
        System.out.println("⏳ Cannot pause while buffering");
    }
    
    @Override
    public void stop(MediaPlayer player) {
        System.out.println("🛑 Stopping buffering");
        player.setState(new StoppedState());
    }
    
    @Override
    public void next(MediaPlayer player) {
        System.out.println("⏳ Cannot skip while buffering");
    }
    
    @Override
    public void previous(MediaPlayer player) {
        System.out.println("⏳ Cannot go back while buffering");
    }
    
    @Override
    public String getStateName() {
        return "Buffering";
    }
}

// Remote control simulator
class RemoteControl {
    private MediaPlayer player;
    
    public RemoteControl(MediaPlayer player) {
        this.player = player;
    }
    
    public void pressPlay() {
        System.out.println("\n🎮 [PLAY] button pressed");
        player.play();
    }
    
    public void pressPause() {
        System.out.println("\n🎮 [PAUSE] button pressed");
        player.pause();
    }
    
    public void pressStop() {
        System.out.println("\n🎮 [STOP] button pressed");
        player.stop();
    }
    
    public void pressNext() {
        System.out.println("\n🎮 [NEXT] button pressed");
        player.next();
    }
    
    public void pressPrevious() {
        System.out.println("\n🎮 [PREVIOUS] button pressed");
        player.previous();
    }
    
    public void simulateBuffering() {
        System.out.println("\n🌐 Network issue detected - entering buffering state");
        player.setState(new BufferingState(player.getCurrentState()));
    }
}

// Test scenarios
class PlayerTestScenarios {
    public static void testBasicPlayback(MediaPlayer player, RemoteControl remote) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Test: Basic Playback");
        System.out.println("=".repeat(40));
        
        player.showStatus();
        remote.pressPlay();
        player.showStatus();
        
        player.simulatePlayback(30);
        remote.pressPause();
        player.showStatus();
        
        remote.pressPlay();
        remote.pressStop();
        player.showStatus();
    }
    
    public static void testInvalidOperations(RemoteControl remote) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Test: Invalid Operations");
        System.out.println("=".repeat(40));
        
        // Try invalid operations in each state
        remote.pressPause(); // Can't pause when stopped
        remote.pressPlay();
        remote.pressPlay(); // Can't play when already playing
        remote.pressPause();
        remote.pressPause(); // Can't pause when already paused
    }
    
    public static void testTrackNavigation(MediaPlayer player, RemoteControl remote) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Test: Track Navigation");
        System.out.println("=".repeat(40));
        
        remote.pressPlay();
        remote.pressNext();
        player.showStatus();
        
        remote.pressPrevious();
        player.showStatus();
        
        remote.pressStop();
        remote.pressNext(); // Navigate while stopped
        remote.pressNext();
        player.showStatus();
    }
    
    public static void testBufferingScenario(MediaPlayer player, RemoteControl remote) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Test: Buffering Scenario");
        System.out.println("=".repeat(40));
        
        remote.pressPlay();
        remote.simulateBuffering();
        player.showStatus();
        
        remote.pressPause(); // Try to pause while buffering
        remote.pressPlay(); // Resume from buffering
        player.showStatus();
    }
}

public class StateDemo {
    public static void main(String[] args) {
        System.out.println("=== State Pattern Demo - Media Player ===\n");
        
        // Create media player and remote
        MediaPlayer player = new MediaPlayer();
        RemoteControl remote = new RemoteControl(player);
        
        System.out.println("🎵 Media Player Initialized");
        player.showStatus();
        
        // Run test scenarios
        PlayerTestScenarios.testBasicPlayback(player, remote);
        PlayerTestScenarios.testInvalidOperations(remote);
        PlayerTestScenarios.testTrackNavigation(player, remote);
        PlayerTestScenarios.testBufferingScenario(player, remote);
        
        // Demonstrate state transitions
        System.out.println("\n" + "=".repeat(40));
        System.out.println("State Transition Summary");
        System.out.println("=".repeat(40));
        System.out.println("Stopped → Playing: ✅ Play button");
        System.out.println("Playing → Paused: ✅ Pause button");
        System.out.println("Paused → Playing: ✅ Play button");
        System.out.println("Any → Stopped: ✅ Stop button");
        System.out.println("Any → Buffering: ✅ Network issues");
        System.out.println("Buffering → Playing: ✅ Buffer complete");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 