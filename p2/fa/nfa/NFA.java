package fa.nfa;

import fa.State;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * This class represents a NFA object that is used to create an NFA automata
 * 
 * @authors Jaden Dawdy, Xian Ma
 */
public class NFA implements NFAInterface {

    private Set<NFAState> states = new LinkedHashSet<>();
    private Set<Character> sigma = new LinkedHashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addState(String name) {

        NFAState state = (NFAState) getState(name);

        if (state != null) {
            return false;
        } else {
            states.add(new NFAState(name));
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setFinal(String name) {
        NFAState state = (NFAState) getState(name);

        if (state != null) {
            state.isFinal = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setStart(String name) {
        NFAState state = (NFAState) getState(name);

        if (state == null) {
            return false;
        }

        NFAState startState = getStartState();
        if (startState != null) {
            startState.isStart = false;
        }

        state.isStart = true;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accepts(String s) {
        NFAState startState = getStartState();
        Set<NFAState> states = new LinkedHashSet<>();
        states.add(startState);
        return check(s, states);
    }

    /**
     * Recursive function that checks to if the NFA accepts the string
     * @param s
     * @param states
     * @return whether the NFA accepts the string
     */
    public boolean check(String s, Set<NFAState> states) {

        char c = s.charAt(0);
        Set<NFAState> nextStates = new LinkedHashSet<>();
        Set<NFAState> tmp = new LinkedHashSet<>();
        for (NFAState state : states) {
            Set<NFAState> vals = eClosure(state);
            tmp.addAll(vals);
        }  

        for (NFAState state : tmp) {

            if (state.delta.containsKey(c)) {
                Set<NFAState> vals = eClosure(state);
                vals.remove(state);
                nextStates.addAll(vals);
                nextStates.addAll(state.delta.get(c));
            }
        }  

        if (s.length() == 1){
            for (NFAState state : nextStates) {
                if (state.isFinal) {
                    return true;
                }
            }
            return false;
        }
        if (nextStates.isEmpty()) {
            return false;
        }
        return check(s.substring(1), nextStates);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NFAState getState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinal(String name) {
        NFAState state = (NFAState) getState(name);
        return state != null ? state.isFinal : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStart(String name) {
        NFAState state = (NFAState) getState(name);
        return state.isStart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.delta.get(onSymb);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Stack<NFAState> stack = new Stack<>();
        Set<NFAState> eClosure = new LinkedHashSet<>();

        stack.push(s);
        eClosure.add(s);

        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            if (current.delta.containsKey('e') ) {
                for (NFAState state : current.delta.get('e')) {
                    if (!eClosure.contains(state)) {
                        stack.push(state);
                        eClosure.add(state);
                    }
                }
            }
        }

        return eClosure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int maxCopies(String s) {

        NFAState startState = getStartState();
        Set<NFAState> states = new LinkedHashSet<>();
        states.add(startState);
        return check2(s, states);
    }

    /**
     * Recursive function counts the max copies of a string in the NFA
     * @param s
     * @param states
     * @return maxCopies
     */
    public int check2(String s, Set<NFAState> states) {

        char c = s.charAt(0);
        Set<NFAState> nextStates = new LinkedHashSet<>();
        Set<NFAState> tmp = new LinkedHashSet<>();
        Set<NFAState> tmpNext = new LinkedHashSet<>();

        for (NFAState state : states) {
            Set<NFAState> vals = eClosure(state);
            tmp.addAll(vals);
        }  
        if (!sigma.contains(c)){
            return tmp.size();
        }

        for (NFAState state : tmp) {
            if (state.delta.containsKey(c)) {
                Set<NFAState> vals = eClosure(state);
                if (c != 'e') {
                    vals.remove(state);
                }
                nextStates.addAll(vals);
                nextStates.addAll(state.delta.get(c));
            }
        }  
        
        if (s.length() == 1){
            for (NFAState state : nextStates) {
                Set<NFAState> vals = eClosure(state);
                tmpNext.addAll(vals);
            }  
            if (tmpNext.isEmpty()) {
                return 1;
            }
            return tmpNext.size();
        }

        return max(check2(s.substring(1), nextStates), nextStates.size());
    }

    /**
     * Returns the max of two integers
     * @param check2
     * @param size
     * @return max of two integers
     */
    private int max(int check2, int size) {
        return check2 > size ? check2 : size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState from = (NFAState) getState(fromState);

        if (from == null || toStates.isEmpty() || (!sigma.contains(onSymb) && onSymb != 'e')) {
            return false;
        }

        for (String toState : toStates) {
            NFAState to = (NFAState) getState(toState);

            if (to == null) {
                return false;
            }

            from.addTransition(onSymb, to);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDFA() {

        for (NFAState state : states) {
            if (state.delta.containsKey('e')) {
                return false;
            }
            
            // removes duplicates
            Set<Set<NFAState>> values = new HashSet<>();
            values.addAll(state.delta.values());

            if (values.size() != state.delta.size()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the start state of the NFA
     * @return the start state of the NFA
     */
    public NFAState getStartState () {
        for (NFAState state : states) {
            if (state.isStart) {
                return state;
            }
        }
        return null;
    }
}