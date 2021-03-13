package edu.miracosta.cs113;
import java.util.*;

public class DoubleLinkedList<E> extends AbstractSequentialList<E>
{  // Data fields
    	private Node<E> head = null;   // points to the head of the list
    	private Node<E> tail = null;   //points to the tail of the list
    	private int size = 0;    // the number of items in the list
  
  
  public void add(int index, E obj)
  { 
	 Node<E> addedNode = new Node(obj);
	
	 if(head == null) //empty list
	 {
		 head = addedNode;
		 tail = addedNode;
	 }

	 else if(index == 0) //add to start 
	 {
		 addFirst(obj);
	 }
	 
	 else if(index == size) //add to end
	 {
		 addedNode.prev = tail;
		 tail.next = addedNode;
		 tail = addedNode;
	 }
	 
	 else {
		 Node<E> nodeRef = head;
		 for(int i = 0; i < index; i++) 
		 {
			 nodeRef = nodeRef.next;
		 }
		 Node<E> newNext = nodeRef;
		 Node<E> newPrevious = nodeRef.prev;
		 nodeRef.prev.next = addedNode;
		 addedNode.prev = newPrevious;
		 nodeRef.prev = addedNode;
		 addedNode.next = newNext;
		 
		 
	 }
	 size++;
	  
	 
  }
  public void addFirst(E obj) {
	  Node<E> addedNode = new Node(obj);
	  addedNode.prev = null;
	  
	  if(head == null) //empty list
	 {
		 head = addedNode;
	 }
	  
	  else 
	  {
		  Node<E> firstNode = head;
		  addedNode.next = firstNode;
		  tail = firstNode;
		  head = addedNode;
	  }
	  size++;
	  
  }
  public void addLast(E obj) { 
	  Node<E> addedNode = new Node(obj);
	  addedNode.next = null;
	  
	  if(head == null) //empty list
	  {
		head = addedNode;
	  }
	  
	  else
	  {
		  Node<E> lastNode = tail;
		  lastNode.next = addedNode;
		  tail = addedNode;
	  }
	  size++;

  }

  public E get(int index) 
  { 	
	  if(size == 0 || index >= size)
		  throw new IndexOutOfBoundsException();
	  ListIterator<E> iter = listIterator(index); 
      return iter.next();
  }  
  public E getFirst() { return head.data;  }
  public E getLast() { return tail.data;  }

  public int size() {  return size;  } 

  public E remove(int index)
  {     E returnValue = null;
        ListIterator<E> iter = listIterator(index);
        if (iter.hasNext())
        {   returnValue = iter.next();
            iter.remove();
        }
        else {   throw new IndexOutOfBoundsException();  }
        return returnValue;
  }

  public Iterator iterator() { return new ListIter(0);  }
  public ListIterator listIterator() { return new ListIter(0);  }
  public ListIterator listIterator(int index){return new ListIter(index);}
  public ListIterator listIterator(ListIterator iter)
  {     return new ListIter( (ListIter) iter);  }

  // Inner Classes
  private static class Node<E>
  {     private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        private Node(E dataItem)  //constructor
        {   data = dataItem;   }
  }  // end class Node

  public class ListIter implements ListIterator<E> 
  {
        private Node<E> nextItem;      // the current node
        private Node<E> lastItemReturned;   // the previous node
        private int index = 0;   // 

    public ListIter(int i)  // constructor for ListIter class
    {   if (i < 0 || i > size)
        {     throw new IndexOutOfBoundsException("Invalid index " + i); }
        lastItemReturned = null;
 
        if (i == size)     // Special case of last item
        {     index = size;     nextItem = null;      }
        else          // start at the beginning
        {   nextItem = head;
            for (index = 0; index < i; index++)  nextItem = nextItem.next;   
        }// end else
    }  // end constructor

    public ListIter(ListIter other)
    {   nextItem = other.nextItem;
        index = other.index;    }

    public boolean hasNext() {   return nextItem != null;    } // Fill Here
    public boolean hasPrevious()
    {   return index > 0;   } // Fill Here
    public int previousIndex() {  return index-1;    } // Fill Here
    public int nextIndex() {  return index;    } // Fill here
    public void set(E o)  
    	{
    		if(lastItemReturned == null)
    			throw new IllegalStateException();
    		if(head == null)
    			head = new Node(o);
    		else if(!hasNext())
    			lastItemReturned.data = o;
    		else if(!hasPrevious())
    			nextItem.data = o;
    		else 
    		lastItemReturned.data =o;
    		
    	}  // not implemented
    public void remove()
    {
    		if(lastItemReturned == null)
    		{
    			throw new IllegalStateException();
    		}
    		if(size == 1) //list only has one item
    		{
    			head = null;
    		}
    		else if(lastItemReturned.prev == null) //remove head
    		{
    			nextItem.prev = null;
    			head = nextItem;
    		}
    		
    		else if(lastItemReturned.next == null) //remove tail
    		{
    			tail.prev.next = null;
    		}
    		
    		else //remove from middle of list
    		{
    			Node<E> next = lastItemReturned.next; //after last accessed node
    			Node<E> previous = lastItemReturned.prev; //before last accessed node
    			next.prev = previous;
    			previous.next = next;
    			if(nextItem == lastItemReturned)
    			{
    				nextItem = next;
    			}
    		}
    		size--;
    }      // not implemented

    /* Cases
     	- empty
     	- one item (no next, not prev)
     	- first item (no prev)
     	- last item (no next)
     */
    public E next()
    {  
    	if(!this.hasNext()) {throw new NoSuchElementException();} //empty or one item (no next)
    	else
    	{
    		
    		lastItemReturned = nextItem;
    		nextItem = nextItem.next;
    		index++;
    		return lastItemReturned.data;
    	}
    
    }

    public E previous() 
    { 
    	if (!hasPrevious()) {throw new NoSuchElementException();}
    	if(nextItem == null) {nextItem = tail;}
    	else {nextItem = nextItem.prev;}
    	lastItemReturned = nextItem;
    	index--;
    	return nextItem.data;
 
    }

    public void add(E obj) {
    	Node<E> addedNode = new Node(obj);
    	
    	if(head==null) //empty list
    		head = addedNode;
    	else if(index == 0) //index 0
    	{
    		Node<E> previousHead = head;
    		head = addedNode;
    		addedNode.next = previousHead;
    		previousHead.prev = addedNode;
    		
    	} 
    	else if(index == size)
    	{
    		Node<E> previousTail = tail;
    		tail = addedNode;
    		addedNode.prev = previousTail;
    		previousTail.next = addedNode;
    	}
    	else
    	{
	    	 Node<E> previous = nextItem.prev;
	    	 previous.next = addedNode;
	    	 addedNode.prev = previous;
	    	 addedNode.next = nextItem;
	    	 nextItem.prev = addedNode;
    	}
    	size++;
    	
    }
  }// end of inner class ListIter
}// end of class DoubleLinkedList
