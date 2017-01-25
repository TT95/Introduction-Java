package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Represents prime number list model.
 * It contains list with prime numbers.
 * Implements next() method which adds new prime number to list.
 * When created, it already contains number 1 in list. 
 * Implements method next method which adds
 * @author Teo Toplak
 *
 */
public class PrimListModel implements ListModel<Integer>{

	/**
	 * Prime numbers list
	 */
	List<Integer> list;
	/**
	 * Listeners list
	 */
	List<ListDataListener> listenerList;

	/**
	 * Construcotor which takes no arguments but add number 1 to list.
	 */
	public PrimListModel() {
		list = new ArrayList<>();
		list.add(1);
		listenerList = new ArrayList<>();
	}
	
	/**
	 * Method generates next prime number and adds it to list.
	 * After adding number it notifies all listeners.
	 */
	public void next() {
		int n = list.get(list.size()-1);
		do {
			n++;
		} while(!isPrim(n));
		list.add(n);
		for(ListDataListener l : listenerList) {
			l.intervalAdded(new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED, 
					0,0));
		}
	}
	/**
	 * Returns true if number given is prime.
	 * @param n number given
	 * @return true if number is prime
	 */
	private boolean isPrim(int n) {
		for(double i=2;i<=Math.sqrt(n)+1;i++){
			if(n % i == 0){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		listenerList.add(l);
	}

	@Override
	public Integer getElementAt(int index) {
		System.out.println(list.get(index));
		return list.get(index);
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		int i=0;
		for(ListDataListener listener : listenerList) {
			if(listener.equals(l)) {
				listenerList.remove(i);
				break;
			}
			i++;
		}
	}
}
