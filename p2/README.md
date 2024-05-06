# Project 2: Nondeterministic Finite Automata

* Author: Jaden Dawdy, Xian Ma
* Class: CS361 Section #002
* Semester: Spring 2024

## Overview

A java program that models a NFA (Nondeterministic Finite Automata). It accomphlishes this through implementing the required methods that create transitions to different states on the same symbol, epsilon transitions, and checking if a string is accepted by the NFA.

## Reflection

This project like p1 helped reinforce many design principles within Java. We practiced implementing a class and its methods alongside extending the abstract class `State.java`. We also practiced using the Set data structure for storing data and using it to manipulate data. For example, in the `isDFA` method, we used a hashset's unique feature of not allowing duplicates to check if the NFA was deterministic. We reinforced our understanding of packages in Java due to the additional practice.

The most challenging part of this project was understanding how to implement the maxcopies method. We tried many methods and eventually settled on a modified recursive method similar to the one used in the `accepts` method. We also had to understand how to implement the `isDFA` method. The challenge with eh `isDFA` method was trying to simply the method and use less loops. We had originally used two for loops. One for checking if it contains epsilon transitions and the other for checking if it contains multiple transitions on the same symbol. We eventually utilized a hashset to simplify the method and use only one for loop. Like last time, the part of the project that we enjoyed the most was utilizing the debug tools to figure out issues with our code. It allowed us to quickly understand what was going wrong and fix it.

## Compiling and Using

To compile

```
$ javac *.java
```
To Run Tests
```
$ java NFATest.java
```

## Results

## Sources used

----------
