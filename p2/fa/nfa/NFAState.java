package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fa.State;

/**
 * This class represents a NFAState object that is used to create an NFA automata
 * 
 * @authors Jaden Dawdy, Xian Ma
 */
public class NFAState extends State {

    protected boolean isStart;
    protected boolean isFinal;

    protected Map<Character, Set<NFAState>> delta = new HashMap<>();
    
    /**
     * Creates an NFAState object with the given name
     * @param name - the name of the NFAState
     */
    public NFAState(String name) {
        super(name);
        isFinal = false;
        isStart = false;
    }

    /**
     * Adds a transition to the NFAState's delta / transitions
     * @param onSymb - the symbol to transition on
     * @param toState - the state to transition to
     */
    public void addTransition(char onSymb, NFAState toState) {
        // adds the transition symbol if it doesn't exist
        // creates empty set
        Set<NFAState> set = new HashSet<>();
        delta.putIfAbsent(onSymb, set);
        // adds to the Set of NFAStates
        delta.get(onSymb).add(toState);
    }

    /**
     * Sets the NFAState to be the start state
     */
    public void setStart() {
        isStart = true;
    }

    /**
     * Sets the NFAState to be the final state
     */
    public void setFinal() {
        isFinal = true;
    }
    
    /**
     * Returns the delta / transitions of the NFAState
     */
    public Set<NFAState> toStates(char onSymb) {
        return delta.get(onSymb);
    }
}
