# Project 1: Deterministic Finite Automata

* Author: Jaden Dawdy, Xian Ma
* Class: CS361 Section #002
* Semester: Spring 2024

## Overview

A java program that models a DFA (Deterministic Finite Automata). It accomphlishes
this through implementing the required methods that create transitions, states, and prints out 
the 5 tuple representation of the machine. 

## Reflection

This project helped reinforce many design principles within Java. It was a good refresher
on how to implement a class and its methods alongside extending abstract classes. It also
allowed for us to familiarized ourselves with the Java Collections API. We learned about the many
different iterations of sets and hashmaps that Java has to offer. Moreover, we learned how packages in
Java interact with each other. 

The most challenging part of this project was understanding how to implement the swap method for the DFA.
While the method itself wasn't something that posed a challenge, it was the fact that we had to ensure that
the DFA we were returning was a deep copy of the original DFA and not a shallow one. A shallow one caused
the original copied DFA to be modified when the swap method was called alongside the new one. We tried devising
methods that were more efficient then iteration but ultimately we settled on iterating through and copying it element
by element. The portion of the project that we enjoyed was being able to debug and utilize the provided unit tests find
bugs and issues within our code. It was a good way to ensure that our code was working as intended and a way for us to practice our debugging skills.

## Compiling and Using

This section should tell the user how to compile your code.  It is
also appropriate to instruct the user how to use your code. Does your
program require user input? If so, what does your user need to know
about it to use it as quickly as possible?

To Compile
```
$ javac *.java
```

To Run Tests
```
$ java DFATest.java
```

## Results

```
dfa1 instantiation pass
dfa1 correctness pass
dfa1 accept pass
dfa1 toString pass
dfa1Swap instantiation pass
dfa1Swap accept pass
dfa2 instantiation pass
dfa2 correctness pass
dfa2 accept pass
dfa2 toString pass
dfa2Swap instantiation pass
dfa2Swap accept pass
dfa3 instantiation pass
dfa3 correctness pass
dfa3 accept pass
dfa3 toString pass
dfa3Swap instantiation pass
dfa3Swap accept pass
```

## Sources used

- [LinkedHashSet For Ordering In Sigma](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html)
- [Deep vs Shallow Copying](https://www.baeldung.com/java-copy-hashmap)

----------