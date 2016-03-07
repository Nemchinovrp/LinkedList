 
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

public class LinkedList<T> implements List<T> {

    private Item<T> first = null;

    private Item<T> last = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
	// BEGIN (write your solution here)
	if (first == null) 
		return false;
	for (T item : this)
		if (item.equals(o)) return true;
        return false;
	// END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
 	// BEGIN (write your solution here)
         final T[] newM = (T[])new Object[this.size()];
	int i = 0;
	for (T element : this) 
		newM[i++] = element;
        return newM;
	// END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
	// BEGIN (write your solution here)
	if (a.length < size)
            	a = (T1[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);
	int i = 0;

        Object[] result = a;
        for (Item<T> e = first; e != null; e = e.next)
            result[i++] = e.element;
        if (a.length > size)
            a[size] = null;

        return a;
	// END
    }

    @Override
    public boolean add(final T t) {
	// BEGIN (write your solution here)
	if (this.size() == 0) {
		this.first = new Item<T>(t, null, null);
		this.last = last;
	} else if (this.size() == 1) {
		this.last = new Item<T>(t, this.first, null);
		this.first.next = this.last;
	} else {
		final Item<T> newLast = new Item<T>(t, this.last, null);
		this.last.next = newLast;
		this.last = newLast;
	}
	size++;
        return true;
	// END
    }

    @Override
    public boolean remove(final Object o) {
	// BEGIN (write your solution here)
	if (size == 0) return false;
	Item<T> current = this.first;
	while(current.next != null && !current.element.equals(o))
		current = current.next;

	if (current.element.equals(o)) {
		if (this.size() == 1) {
			this.first = null;
			this.last = null;
		} else {
			if (current == first) {
				first = current.next;
				first.prev = null;
			}
			if (current == last) {
				last = current.prev;	
				last.next = null;
			}
			if (current.next != null && current.prev != null) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
			}
		}
		size--;
		return true;
	}
        return false;
	// END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
	// BEGIN (write your solution here)
	this.first = null;
	this.last = null;
        size = 0;
	// END
    }

    @Override
    public T remove(final int index) {
	// BEGIN (write your solution here)
	int i = 0;
	if (size <= index) return null;
	Item<T> current = this.first;
	while(i != index) {
		current = current.next;
		i++;
	}

	if (current != null) {
		if (this.size() == 1) {
			this.first = null;
			this.last = null;
		} else {
			if (current == first) {
				first = current.next;
				first.prev = null;
			}
			if (current == last) {
				last = current.prev;	
				last.next = null;
			}
			if (current.next != null && current.prev != null) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
			}
		}
		size--;
		return current.element;
	}
        return null;
	// END
    }

    // BEGIN (write your solution here)
    private void remove(final Item current) {
	if (current != null) {
		if (this.size() == 1) {
			this.first = null;
			this.last = null;
		} else {
			if (current == first) {
				first = current.next;
				first.prev = null;
			}
			if (current == last) {
				last = current.prev;	
				last.next = null;
			}
			if (current.next != null && current.prev != null) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
			}
		}
		size--;
	}
    }
    // END
    @Override
    public List<T> subList(final int start, final int end) {
	return null;
    }

    @Override
    public ListIterator listIterator() {
	return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
	return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
	throw new UnsupportedOperationException();
    } 

    @Override
    public int indexOf(final Object target) {
	    // BEGIN (write your solution here)
	     int i = 0;
	    Item<T> current = first;
	    while (current != null && !current.element.equals(target)) {
	    	i++;
	    }
	    if (current == null) return -1;
	    return i;
	    // END
    } 

    @Override
    public void add(final int index, final T element) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
	throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
	// BEGIN (write your solution here)
	final Item<T> item = getByIndex(index);
	item.element = element;
	return element;
	// END
    }

    @Override
    public T get(final int index) {
	// BEGIN (write your solution here)
	final Item<T> item = getByIndex(index);
	return item.element;
	// END
    }

    // BEGIN (write your solution here)
    private Item<T> getByIndex(final int index) {
    	int i = 0;
	Item<T> current = first;
	while (i != index) {
		current = current.next;
		i++;
	}
	return current;
    }

    private int indexOf(final Item<T> item) {
    	int i = 0;
	Item<T> current = first;
	while (current != null && current != item) {
		current = current.next;
		i++;
	}	
	if (current != null) return i;
	return -1;
    }
    // END

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> current;

        private Item<T> last;

	public ElementsIterator() {
	    this(0);
	}

	public ElementsIterator(final int index) {
	    // BEGIN (write your solution here)
	    	    this.current = getByIndex(index);

	    // END
	}

        @Override
        public boolean hasNext() {
		return current != null;
        }

        @Override
        public T next() {
	    // BEGIN (write your solution here)
	     if (!hasNext()) 
		throw new NoSuchElementException();
	    last = current;
	    current = current.next;
	    return last.element;
	    // END
        }

	@Override
	public void add(final T element) {
	    LinkedList.this.add(element);
	}

	@Override
	public void set(final T element) {
	    // BEGIN (write your solution here)
	      if (last == null) throw new IllegalStateException();
	    last.element = element;
	    // END
	}
 	
	@Override
	public int previousIndex(){
	    // BEGIN (write your solution here)
	    return LinkedList.this.indexOf(last);
	    // END
	}
	
	@Override
	public int nextIndex() {
	   // BEGIN (write your solution here)
	    	    return indexOf(current.element);

	   // END
	}

	@Override
	public boolean hasPrevious() {
	   // BEGIN (write your solution here)
	   if (current == first) return false;
	   return true;
	   // END
	}	

	@Override
	public T previous() {
	   // BEGIN (write your solution here)
	    if (!hasPrevious()) 
		throw new NoSuchElementException();
	   if (current == null) {
		current = last;
	   	last = null;
	   } else {
	   	last = current.prev;
	   	current = current.prev;
	   }
	   return current.element;
	   // END
	}
	
	@Override
	public void remove() {
	    // BEGIN (write your solution here)
	     if (last == null) 
		throw new IllegalStateException();
	    LinkedList.this.remove(last);
	    last = null;
	    // END
	}

    }

    private static class Item<T> {
    
    	private T element;

	private Item<T> next;

	private Item<T> prev;

	public Item(final T element, final Item<T> prev, final Item<T> next) {
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	public T getElement() {
		return element;
	}

	public Item<T> getNext() {
		return next;
	}

	public Item<T> getPrev() {
		return prev;
	}
    
    }

}
