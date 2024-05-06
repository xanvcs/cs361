package fa.dfa;

import java.util.*;

/**
 * This class represents a DFA object that is used to create a automata
 * 
 * @authors Jaden Dawdy, Xian Ma
 */
public class DFA implements DFAInterface {

    private Set<DFAState> DFAStates = new TreeSet<>(); // Set of DFA states
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>(); // Alphabet of the DFA
    private Iterator<DFAState> iterator; // Iterator for the DFA states

    protected int position = 0; // Position of the state

    /**
     * Adds a state to the DFA instance
     * 
     * @param name the name of the state
     * @return true if the state was added, false if not
     */
    @Override
    public boolean addState(String name) {
        position++;

        // check if the state already exists
        if (getState(name) == null) {
            return DFAStates.add(new DFAState(name, position));
        }
        return false;
    }

    /**
     * Sets the state to be a final state
     * 
     * @param name the name of the state
     * @return true if the state was set to final, false if state is null
     */
    @Override
    public boolean setFinal(String name) {
        DFAState state = getState(name);

        if (state == null) {
            return false;
        }

        state.setFinal();
        return state.isFinal;
    }

    /**
     * Sets the state to be the start state. There can only be
     * one start state.
     * 
     * @param name the name of the state
     * @return true if the state was set to start, false if state is null
     */
    @Override
    public boolean setStart(String name) {
        DFAState state = getState(name);

        if (state == null) {
            return false;
        }

        // Ensures only one start state
        DFAState startState = getStartState();
        if (startState != null) {
            startState.isStart = false;

        }
        state.setStart();

        return state.isStart;
    }

    /**
     * Adds a symbol to alphabet
     * 
     * @param symbol the symbol to add
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * Checks if the DFA accepts the string
     * 
     * @param s the string to check
     * @return true if the DFA accepts the string, false if not
     */
    @Override
    public boolean accepts(String s) {

        DFAState current = getStartState();

        // Ensures current is not null before beginning
        assert current != null;

        for (int i = 0; i < s.length(); i++) {

            // Ensures the symbol is in the alphabet
            if (!sigma.contains(s.charAt(i))) {
                return false;
            }

            // Ensures the transition exists
            for (DFAState state : DFAStates) {
                if ((current.transitions.get(s.charAt(i))).equals(state.getName())) {
                    current = state;
                    break;
                }

            }
        }

        return current.isFinal;
    }

    /**
     * Returns the start state of the DFA
     * 
     * @return the start state
     */
    private DFAState getStartState() {
        for (DFAState state : DFAStates) {
            if (state.isStart) {
                return state;
            }
        }
        return null;
    }

    /**
     * Returns the alphabet of the DFA
     * 
     * @return the alphabet in order of insertion
     */
    @Override
    public LinkedHashSet<Character> getSigma() {
        return sigma;
    }

    /**
     * Returns the state of the DFA
     * 
     * @param name the name of the state
     */
    @Override
    public DFAState getState(String name) {
        iterator = DFAStates.iterator();
        DFAState current;

        while (iterator.hasNext()) {
            current = iterator.next();
            if (current.getName().equals(name)) {
                return current;
            }
        }
        return null;
    }

    /**
     * Returns if a state is a final state
     * 
     * @param name the name of the state
     * @return true if the state is final, false if not
     */
    @Override
    public boolean isFinal(String name) {
        return getState(name).isFinal;
    }

    /**
     * Returns if a state is a start state
     * 
     * @param name the name of the state
     * @return true if the state is start, false if not
     */
    @Override
    public boolean isStart(String name) {
        return getState(name).isStart;
    }

    /**
     * Adds a transition to the DFA
     * 
     * @param fromState the state to transition from
     * @param toState   the state to transition to
     * @param onSymb    the symbol to transition on
     * @return true if the transition was added, false if not
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        DFAState from = getState(fromState);
        DFAState to = getState(toState);

        if (from == null || to == null) {
            return false;
        }
        if (!sigma.contains(onSymb)) {
            return false;
        } else {
            from.addTransition(onSymb, to);
        }
        return true;
    }

    /**
     * Swaps the transitions of two symbols
     * 
     * @param symb1 the first symbol to swap
     * @param symb2 the second symbol to swap
     * @return the new DFA with the swapped transitions
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA newDFA = new DFA();
        newDFA.sigma = new LinkedHashSet<>(sigma);

        // Ensures changes are independent of original set
        newDFA.DFAStates = copyStates();

        for (DFAState state : newDFA.DFAStates) {
            String transitionSymb1 = state.transitions.get(symb1);
            String transitionSymb2 = state.transitions.get(symb2);

            state.transitions.put(symb1, transitionSymb2);
            state.transitions.put(symb2, transitionSymb1);
        }

        return newDFA;
    }

    /**
     * Copies the states of the DFA
     * 
     * @return the copied states
     */
    private TreeSet<DFAState> copyStates() {
        TreeSet<DFAState> states = new TreeSet<>();

        for (DFAState state : DFAStates) {
            states.add(new DFAState(state));
        }

        return states;
    }

    /**
     * Returns the string representation of the DFA
     * 
     * @return the string representation of the DFA
     */
    public String toString() {
        String output = "Q = { ";
        for (DFAState state : DFAStates) {
            output += state.getName() + " ";
        }
        output += "}\n";
        output += "Sigma = { ";
        for (char symbol : sigma) {
            output += symbol + " ";
        }
        output += "}\n";
        output += "delta = \n\t\t";
        for (char symbol : sigma) {
            output += symbol + "\t";
        }
        output += "\n";

        // Prints the transition table
        for (DFAState from : DFAStates) {
            output += "\t" + from.getName() + "\t";
            for (char symbol : sigma) {
                String to = from.transitions.get(symbol);
                output += to + "\t";
            }
            output += "\n";
        }
        output += "q0 = " + getStartState().getName() + "\n";
        output += "F = { ";
        for (DFAState state : DFAStates) {
            if (state.isFinal) {
                output += state.getName() + " ";
            }
        }
        output += "}\n";
        return output;
    }

}
