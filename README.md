## Implement a state machine with kotlin using Tinder’s library

### Usage

In this example, we create a StateMachine from the following state diagram.


![alt text](https://github.com/sandeshbodake/tinder-statemachine-example/blob/master/example/sample-example.jpg)


### Gradle dependecy

```implementation 'com.tinder.statemachine:statemachine:0.2.0'```

### Define states, event, and side effects:

```
sealed class WHBState {
    object Work: WHBState()
    object Home: WHBState()
    object Bed: WHBState()
}

sealed class WHBEvent {
    object TakeTrain: WHBEvent()
    object Sleep: WHBEvent()
    object Wake: WHBEvent()
}

sealed class WHBSideEffect {
    object LogTakeTrainTime: WHBSideEffect()
    object LogSleepTime: WHBSideEffect()
    object LogWakeTime: WHBSideEffect()
}
```

### Declare state transitions:

```
val whbStateMachine = StateMachine.create<WHBState, WHBEvent, WHBSideEffect> {
        initialState(WHBState.Bed)

        state<WHBState.Bed> {
            on<WHBEvent.Wake> {
                transitionTo(WHBState.Home, WHBSideEffect.LogWakeTime)
            }
        }

        state<WHBState.Home> {
            on<WHBEvent.TakeTrain> {
                transitionTo(WHBState.Work, WHBSideEffect.LogTakeTrainTime)
            }
        }

        state<WHBState.Work> {
            on<WHBEvent.TakeTrain> {
                transitionTo(WHBState.Home, WHBSideEffect.LogTakeTrainTime)
            }
        }

        state<WHBState.Home> {
            on<WHBEvent.Sleep> {
                transitionTo(WHBState.Bed, WHBSideEffect.LogSleepTime)
            }
        }
}
```


### Perform state transitions:

```
 var stateMachine: WHBStateMachine

 assertThat(stateMachine.whbStateMachine.state).isEqualTo(WHBState.Bed)

 val transaction = stateMachine.whbStateMachine.transition(WHBEvent.Wake)
        assertThat(stateMachine.whbStateMachine.state).isEqualTo(WHBState.Home)
```
