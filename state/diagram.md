# State Pattern - State Diagram

```mermaid
stateDiagram-v2
    [*] --> Stopped : MediaPlayer Created
    
    Stopped --> Playing : play()
    Stopped --> Stopped : pause() [Invalid]
    Stopped --> Stopped : stop() [Already stopped]
    Stopped --> Stopped : next() / previous()
    
    Playing --> Paused : pause()
    Playing --> Stopped : stop()
    Playing --> Playing : play() [Already playing]
    Playing --> Playing : next() / previous()
    
    Paused --> Playing : play()
    Paused --> Stopped : stop()
    Paused --> Paused : pause() [Already paused]
    Paused --> Stopped : next() / previous()
    
    Playing --> Buffering : Network Issues
    Buffering --> Playing : Buffer Complete
    Buffering --> Stopped : stop()
    Buffering --> Buffering : play() / pause() / next() / previous() [Buffering...]
    
    note right of Stopped
        Initial state
        Track position = 0
        Can navigate tracks
    end note
    
    note right of Playing
        Track playing
        Position advancing
        Can control playback
    end note
    
    note right of Paused
        Playback paused
        Position preserved
        Can resume or stop
    end note
    
    note right of Buffering
        Loading content
        Limited operations
        Auto-resume when ready
    end note
```

# State Pattern - Class Diagram

```mermaid
classDiagram
    class PlayerState {
        <<interface>>
        +play(MediaPlayer) void
        +pause(MediaPlayer) void
        +stop(MediaPlayer) void
        +next(MediaPlayer) void
        +previous(MediaPlayer) void
        +getStateName() String
    }
    
    class MediaPlayer {
        -currentState: PlayerState
        -currentTrack: String
        -trackPosition: int
        -playlist: List~String~
        -currentTrackIndex: int
        -volume: int
        +MediaPlayer()
        +setState(PlayerState) void
        +play() void
        +pause() void
        +stop() void
        +next() void
        +previous() void
        +startPlayback() void
        +pausePlayback() void
        +stopPlayback() void
        +nextTrack() void
        +previousTrack() void
        +showStatus() void
        +simulatePlayback(int) void
    }
    
    class StoppedState {
        +play(MediaPlayer) void
        +pause(MediaPlayer) void
        +stop(MediaPlayer) void
        +next(MediaPlayer) void
        +previous(MediaPlayer) void
        +getStateName() String
    }
    
    class PlayingState {
        +play(MediaPlayer) void
        +pause(MediaPlayer) void
        +stop(MediaPlayer) void
        +next(MediaPlayer) void
        +previous(MediaPlayer) void
        +getStateName() String
    }
    
    class PausedState {
        +play(MediaPlayer) void
        +pause(MediaPlayer) void
        +stop(MediaPlayer) void
        +next(MediaPlayer) void
        +previous(MediaPlayer) void
        +getStateName() String
    }
    
    class BufferingState {
        -previousState: PlayerState
        +BufferingState(PlayerState)
        +play(MediaPlayer) void
        +pause(MediaPlayer) void
        +stop(MediaPlayer) void
        +next(MediaPlayer) void
        +previous(MediaPlayer) void
        +getStateName() String
    }
    
    class RemoteControl {
        -player: MediaPlayer
        +RemoteControl(MediaPlayer)
        +pressPlay() void
        +pressPause() void
        +pressStop() void
        +pressNext() void
        +pressPrevious() void
        +simulateBuffering() void
    }
    
    PlayerState <|.. StoppedState
    PlayerState <|.. PlayingState
    PlayerState <|.. PausedState
    PlayerState <|.. BufferingState
    
    MediaPlayer --> PlayerState : currentState
    MediaPlayer <-- PlayerState : context
    
    RemoteControl --> MediaPlayer : controls
    
    StoppedState ..> PlayingState : play() transition
    PlayingState ..> PausedState : pause() transition
    PlayingState ..> StoppedState : stop() transition
    PausedState ..> PlayingState : play() transition
    PausedState ..> StoppedState : stop() transition
    PlayingState ..> BufferingState : network issues
    BufferingState ..> PlayingState : buffer complete
```

## Key Features

1. **State Delegation**: MediaPlayer delegates operations to current state
2. **State Transitions**: States control when and how transitions occur
3. **Encapsulation**: Each state encapsulates specific behavior
4. **Invalid Operations**: States handle invalid operations gracefully
5. **Context Independence**: States can be reused across different contexts

## Benefits Demonstrated

- **Behavior Variation**: Same operation produces different behavior based on state
- **Clean Transitions**: State changes are controlled and predictable
- **Extensibility**: New states can be added without modifying existing code
- **Maintainability**: State-specific logic is isolated in separate classes 