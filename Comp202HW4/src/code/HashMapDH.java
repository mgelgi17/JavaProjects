/*
 * Student: 
 * Written By Metehan Gelgi 64178
 * 
 */

package code;

import java.lang.reflect.Array;
import java.util.*;
import java.util.HashSet;

import given.AbstractHashMap;
import given.HashEntry;
//import given.iPrintable;

/*
 * The file should contain the implementation of a hashmap with:
 * - Open addressing for collision handling
 * - Double hashing for probing. The double hash function should be of the form: q - (k mod q)
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the keyset method
 */

public class HashMapDH<Key, Value> extends AbstractHashMap<Key, Value> {

	// The underlying array to hold hash entries (see the HashEntry class)
	private HashEntry<Key, Value>[] buckets;

	// Do not forget to call this when you need to increase the size!
	@SuppressWarnings("unchecked")
	protected void resizeBuckets(int newSize) {
		// Update the capacity
		N = nextPrime(newSize);
		buckets = (HashEntry<Key, Value>[]) Array.newInstance(HashEntry.class, N);
	}

	// The threshold of the load factor for resizing
	protected float criticalLoadFactor;

	// The prime number for the secondary hash
	int dhP;
	HashEntry<Key, Value> DEFUNCT = new HashEntry<Key, Value>(null, null);

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */

	/*
	 * ADD A NESTED CLASS IF NEEDED
	 * 
	 */

	// Default constructor
	public HashMapDH() {
		this(101);
	}

	public HashMapDH(int initSize) {
		this(initSize, 0.6f);
	}

	public HashMapDH(int initSize, float criticalAlpha) {
		N = initSize;
		criticalLoadFactor = criticalAlpha;
		resizeBuckets(N);

		// Set up the MAD compression and secondary hash parameters
		updateHashParams();

		/*
		 * ADD MORE CODE IF NEEDED
		 * 
		 */
	}

	/*
	 * ADD MORE METHODS IF NEEDED
	 * 
	 */

	/**
	 * Calculates the hash value by compressing the given hashcode. Note that you
	 * need to use the Multiple-Add-Divide method. The class variables "a" is the
	 * scale, "b" is the shift, "mainP" is the prime which are calculated for you.
	 * Do not include the size of the array here
	 * 
	 * Make sure to include the absolute value since there maybe integer overflow!
	 */
	protected int primaryHash(int hashCode) {
		// TODO: Implement MAD compression given the hash code, should be 1 line
		return Math.abs(Math.floorMod(Math.floorMod(a * hashCode + b, P), N));
	}

	/**
	 * The secondary hash function. Remember you need to use "dhP" here!
	 * 
	 */
	protected int secondaryHash(int hashCode) {
		// TODO: Implement the secondary hash function taught in the class
		return Math.abs(dhP - Math.floorMod(hashCode, dhP));
	}

	@Override
	public int hashValue(Key key, int iter) {
		int k = Math.abs(key.hashCode());
		return Math.abs(primaryHash(k) + iter * secondaryHash(k)) % N;
	}

	/**
	 * checkAndResize checks whether the current load factor is greater than the
	 * specified critical load factor. If it is, the table size should be increased
	 * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
	 * re-calculate the hash parameters and do not forget to re-populate the new
	 * array!
	 */
	protected void checkAndResize() {
		if (loadFactor() > criticalLoadFactor) {
			// TODO: Fill this yourself
			N = 2 * N;
			updateHashParams();
			HashEntry<Key, Value>[] temp = buckets;
			resizeBuckets(N);
			for (HashEntry<Key, Value> hashEntry : temp) {
				if (hashEntry != null && !hashEntry.equals(DEFUNCT)) {
					int iter = 0;
					HashEntry<Key, Value> nextEntry = buckets[hashValue(hashEntry.getKey(), iter)];
					while (nextEntry != null) {
						nextEntry = buckets[hashValue(hashEntry.getKey(), ++iter)];
					}
					buckets[hashValue(hashEntry.getKey(), iter)] = new HashEntry<Key, Value>(hashEntry.getKey(),
							hashEntry.getValue());
				}
			}
		}
	}

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		if (k == null) return null;
		int iter = 0;
		HashEntry<Key, Value> nextEntry = buckets[hashValue(k, iter)];
		while (nextEntry != null) {
			if (!nextEntry.equals(DEFUNCT) && nextEntry.getKey().equals(k)) {
				Value v = nextEntry.getValue();
				return v;
			}
			nextEntry = buckets[hashValue(k, ++iter)];
		}
		return null;
	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		// Do not forget to resize if needed!
		if (k == null)
			return null; // eğer null key gelirse yer dolu olursa önce güncelleyecek miyiz?
		checkAndResize();
		int iter = 0;
		HashEntry<Key, Value> nextEntry = buckets[hashValue(k, iter)];
		while (nextEntry != null && !nextEntry.equals(DEFUNCT)) {
			if (nextEntry.getKey().equals(k)) {
				Value oldV = nextEntry.getValue();
				nextEntry.setValue(v);
				return oldV;
			}

			nextEntry = buckets[hashValue(k, ++iter)];
		}
		buckets[hashValue(k, iter)] = new HashEntry<Key, Value>(k, v);
		n++;
		return null;
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		if (k == null)
			return null;
		int iter = 0;
		HashEntry<Key, Value> nextEntry = buckets[hashValue(k, iter)];
		while (nextEntry != null) {
			if (nextEntry.getKey().equals(k)) {
				Value v = nextEntry.getValue();
				buckets[hashValue(k, iter)] = DEFUNCT;
				n--;
				return v;
			}
			nextEntry = buckets[hashValue(k, ++iter)];
		}
		return null;
	}

	
	

	//
	//
	//
	//
	//FAQ yerinde, ArrayList yazılabilir dendi, TestHashMap.java içinden casting i arrayListe çevirdim.  
	//in FAQ, Answered as arrayList can be used. I cahnged casting to the arrayList in TestHashMap.java as said in FAQ.
	//
	//
	// This is the only function you are allowed to use an existing Java data
	// structure!
	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		Set<Key> keyList = new HashSet<>();
		for (HashEntry<Key, Value> Entry : buckets) {
			if(Entry != null && !Entry.equals(DEFUNCT)) {
				keyList.add(Entry.getKey());
			}
		}
		return keyList;
	}

	@Override
	protected void updateHashParams() {
		super.updateHashParams();
		dhP = nextPrime(N / 2);
	}

}
