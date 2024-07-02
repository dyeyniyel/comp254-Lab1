/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.exercise3.janiel.mark.javier;



/**
 * An implementation of a circularly linked list.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class CircularlyLinkedList<E> implements Cloneable {
  //---------------- nested Node class ----------------
  /**
   * Singly linked node, which stores a reference to its element and
   * to the subsequent node in the list.
   */
  private static class Node<E> {

    /** The element stored at this node */
    private E element;     // an element stored at this node

    /** A reference to the subsequent node in the list */
    private Node<E> next;  // a reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }

    // Accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { return element; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Modifier methods
    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }
  } //----------- end of nested Node class -----------

  // instance variables of the CircularlyLinkedList
  /** The designated cursor of the list */
  private Node<E> tail = null;                  // we store tail (but not head)

  /** Number of nodes in the list */
  private int size = 0;                         // number of nodes in the list

  /** Constructs an initially empty list. */
  public CircularlyLinkedList() { }             // constructs an initially empty list

  // access methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the first element of the list
   * @return element at the front of the list (or null if empty)
   */
  public E first() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return tail.getNext().getElement();         // the head is *after* the tail
  }

  /**
   * Returns (but does not remove) the last element of the list
   * @return element at the back of the list (or null if empty)
   */
  public E last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail.getElement();
  }

  // update methods
  /**
   * Rotate the first element to the back of the list.
   */
  public void rotate() {         // rotate the first element to the back of the list
    if (tail != null)                // if empty, do nothing
      tail = tail.getNext();         // the old head becomes the new tail
  }

  /**
   * Adds an element to the front of the list.
   * @param e  the new element to add
   */
  public void addFirst(E e) {                // adds element e to the front of the list
    if (size == 0) {
      tail = new Node<>(e, null);
      tail.setNext(tail);                     // link to itself circularly
    } else {
      Node<E> newest = new Node<>(e, tail.getNext());
      tail.setNext(newest);
    }
    size++;
  }

  /**
   * Adds an element to the end of the list.
   * @param e  the new element to add
   */
  public void addLast(E e) { // adds element e to the end of the list
    addFirst(e);             // insert new element at front of list
    tail = tail.getNext();   // now new element becomes the tail
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    Node<E> head = tail.getNext();
    if (head == tail) tail = null;           // must be the only node left
    else tail.setNext(head.getNext());       // removes "head" from the list
    size--;
    return head.getElement();
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    if (tail == null) return "()";
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = tail;
    do {
      walk = walk.getNext();
      sb.append(walk.getElement());
      if (walk != tail)
        sb.append(", ");
    } while (walk != tail);
    sb.append(")");
    return sb.toString();
  }
  
  //Clone method for circularly linked list. 
	@SuppressWarnings({ "unchecked" })
	public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {
		// always use inherited Object.clone() to create the initial copy
		CircularlyLinkedList<E> other = (CircularlyLinkedList<E>) super.clone(); // safe cast
		
		if (size > 0) { // we need independent chain of nodes
			other.tail = new Node<>(tail.getElement(), null);
			Node<E> walk = tail.getNext(); // walk through remainder of original list
			Node<E> otherTail = other.tail; // remember most recently created node
		
			// Traverse through the original list until we reach tail
			while (walk != tail) { 
				
				//For each node in the original list, create a new node with the same element.
				Node<E> newest = new Node<>(walk.getElement(), null);
				otherTail.setNext(newest); // link previous node in the new list to this one
				otherTail = newest; //update created node
				walk = walk.getNext(); //move to the next node on original list
			}
			otherTail.setNext(other.tail); //update tail to make it circular by linking the last node in the new list to the first node
			
		}
		return other; //return new list if size == 0
	}
  
  //main method
  //Student Number: 301379377
  //Student Name: Janiel Mark Javier
  public static void main(String[] args) throws CloneNotSupportedException
  {
	  
	  //(LAX, MSP, ATL, BOS)
	  CircularlyLinkedList<String> circularList = new CircularlyLinkedList<String>();
	  circularList.addFirst("LAX");
	  circularList.addLast("MSP");
	  circularList.addLast("ATL");
	  circularList.addLast("BOS");
	 

	  System.out.println("Original List:");  
	  System.out.println(circularList);
	  
	  CircularlyLinkedList<String> circularList2 = (CircularlyLinkedList<String>) circularList.clone();
	  circularList.tail.element="NEW";  //change last element for testing
	  System.out.println("\nUpdated List:");  
	  System.out.println(circularList);
	  
	  System.out.println("\nCloned List (Same as Original List):");  
	  System.out.println(circularList2);

	// Additional code to test the circular nature of the cloned list
	  System.out.println("\nTesting the cloned list if it is circularly linked. Tail's next should point to Head:");
	  System.out.println("Tail: " +circularList2.tail.getElement()); 
	  System.out.println("Head(tail.next): " + circularList2.tail.next.getElement());

  }
}