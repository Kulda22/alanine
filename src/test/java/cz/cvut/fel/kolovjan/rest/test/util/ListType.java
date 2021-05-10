package cz.cvut.fel.kolovjan.rest.test.util;

/**
 * Pi-hole has two types of list - regex and exact. Regex contains regex and wildcarded domains.
 */
public enum ListType {
    EXACT(),
    REGEX();
}
