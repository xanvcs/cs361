package fa.dfa;

import fa.State;
import java.util.*;

/**
 * This class represents the state objects within the DFA
 * 
 * @authors Jaden Dawdy, Xian Ma
 */
public class DFAState extends State implements Comparable<DFAState> {

    protected boolean isFinal = false; // Whether the state is a final state
    protected boolean isStart = false; // Whether the state is the start state
    protected int position; // The position of the state
    protected HashMap<Character, String> transitions = new HashMap<>(); // The transitions of the state

    /**
     * Creates a DFA state object
     * 
     * @param name     the name of the state
     * @param position the position of the state
     */
    public DFAState(String name, int position) {
        super(name);
        this.position = position;
    }

    /**
     * Creates a DFA state object from another DFA state object
     * 
     * @param state the state to copy
     */
    public DFAState(DFAState state) {
        super(state.getName());
        this.isFinal = state.isFinal;
        this.isStart = state.isStart;
        this.position = state.position;
        this.transitions = state.copyTransitions();
    }

    /**
     * Deep copies the transitions from a state and returns it
     * 
     * @return a deep copy of the transitions
     */
    public HashMap<Character, String> copyTransitions() {
        HashMap<Character, String> transitionsCopy = new HashMap<>();

        for (Map.Entry<Character, String> entry : transitions.entrySet()) {
            char key = entry.getKey();
            transitionsCopy.put(key, entry.getValue());
        }

        return transitionsCopy;
    }

    /**
     * Sets the state to be a final state
     */
    public void setFinal() {
        isFinal = true;
    }

    /**
     * Sets the state to be the start state
     */
    public void setStart() {
        isStart = true;
    }

    /**
     * Checks if one state is equal to another
     * 
     * @param state the state to compare to
     * @return true if the states are equal, false if they are not
     */
    public boolean equals(DFAState state) {
        return state.getName().equals(getName());
    }

    /**
     * Adds a transition to the state
     * 
     * @param symbol   the symbol to transition on
     * @param newState the state to transition to
     */
    public void addTransition(char symbol, DFAState newState) {
        transitions.put(symbol, newState.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(DFAState o) {
        return position - o.position;
    }

}
