public class HashTable<K, V> {

	private static int TABLE_SIZE = 997;
	private static int ITEMS = 0;
	private static int collisionCount = 0;
	@SuppressWarnings("rawtypes")
	private HashEntry[] table;
	private int function;
	private double loadFactor;
	// Constructor
	public HashTable(int function, double loadFactor) {
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
		this.function = function;
		this.loadFactor = loadFactor;
	}

	public int hashFunction(K key) {
		// Finding an int key for our word
		int key2 = 0;
		for (int j = 0; j < ((String) key).length(); j++) {
			int exp = 1;
			for (int j2 = 0; j2 < ((String) key).length() - j - 1; j2++) {
				exp *= 33;
			}
			key2 += (((String) key).charAt(j) - 96) * exp;
		}
		if (key2 < 0)
			key2 = key2 * (-1);
		// Compression Function
		int hash = (int) key2 % TABLE_SIZE;
		return hash;
	}

	public int hashFunction2(K key) {
		int multiplier = 11;

		for (int i = 0; i < ((String) key).length(); i++) {
			multiplier = multiplier * 31 + ((String) key).charAt(i);
		}

		int hash = (multiplier % (TABLE_SIZE * 2 + 1)) % TABLE_SIZE;
		if (hash < 0)
			hash = hash * (-1);
		return hash;
	}

	public int get(K key) {
		boolean isFound = false;
		int index;
		if(function == 1)
			index = hashFunction(key);
		else
			index = hashFunction2(key);
		if (table[index] == null)
			System.out.println("This word does not exist");
		else {
			if (table[index].getWord().equals(key)) {
				isFound = true;
			} else {
				int counter = 0;
				while (counter < TABLE_SIZE) {
					if (table[index] != null && table[index].getWord().equals(key)) {
						isFound = true;						
						break;
					} else {
						if (index != TABLE_SIZE - 1) {
							index++;
							counter++;
						} else {
							index = 0;
							counter++;
						}

					}
				}

			}

		}
		if (isFound) {
			int key2 = 0;
			if(function == 1) {
			
			for (int j = 0; j < ((String) key).length(); j++) {
				int exp = 1;
				for (int j2 = 0; j2 < ((String) key).length() - j - 1; j2++) {
					exp *= 33;
				}
				key2 += (((String) key).charAt(j) - 96) * exp;
			}
			if (key2 < 0)
				key2 = key2 * (-1);
			}else {
				key2 = 11;

				for (int i = 0; i < ((String) key).length(); i++) {
					key2 = key2 * 31 + ((String) key).charAt(i);
				}
				if(key2<0)
					key2 = key2*(-1);
			}
			System.out.println("Search: " + table[index].getWord());
			System.out.println("Key: " + key2);
			System.out.println("Count: " + table[index].getValue());
			System.out.println("Index: " + index);
			System.out.println("");
			return index;
		}else
			return -1;

	}

	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		int index;
		if (function == 1)
			index = hashFunction(key);
		else
			index = hashFunction2(key);
		int dib = 0;
		if (table[index] != null) {
			if (table[index].getWord().equals(key)) {// if the word added before
				table[index].setValue((int) table[index].getValue() + 1);// increasing count
			} else {
				int initial = index;
				int counter = 0;
				boolean isAdded = false;
				while (!isAdded && counter < TABLE_SIZE) {//checking if the word added before to another index
					if (table[index] != null && table[index].getWord().equals(key)) {
						table[index].setValue((int) table[index].getValue() + 1);
						isAdded = true;
					} else {
						if (index != TABLE_SIZE - 1) {
							index++;
							counter++;
						} else {
							index = 0;
							counter++;
						}

					}
				}
				index = initial;
				while (!isAdded) {
					if (table[index] != null && table[index].getDib() < dib) {//controlling dib counts to place
						initial = index;										// the word
						while (table[index] != null) {
							if (index != TABLE_SIZE - 1)
								index++;
							else
								index = 0;
							collisionCount++;
						}
						int toLook;
						if (index == 0)
							toLook = TABLE_SIZE - 1;
						else
							toLook = index - 1;
						while (index != initial) {//sliding the other words that come after the word we want to 
													// add 
							K key1 = (K) table[toLook].getWord();
							V value1 = (V) table[toLook].getValue();
							int dibLast = table[toLook].getDib();
							if (table[index] != null) {
								table[index].setWord(key1);
								table[index].setValue(value1);
								table[index].setDib(dibLast + 1);//increasing dib value of slided word
							} else {
								table[index] = new HashEntry<K, V>(key1, value1);
								table[index].setDib(dibLast + 1);
							}
							if (index != 0) {
								index--;
								toLook--;
							} else {
								index = TABLE_SIZE - 1;
								toLook--;
							}
							if (toLook == -1)
								toLook = TABLE_SIZE - 1;

						}
						//adding the word
						table[initial] = new HashEntry<K, V>(key, value);
						table[initial].setDib(dib);
						ITEMS++;
						isAdded = true;
					} else if (table[index] == null) {
						table[index] = new HashEntry<K, V>(key, value);
						table[index].setDib(dib);
						ITEMS++;
						isAdded = true;
					} else if (table[index] != null && table[index].getDib() >= dib && index != TABLE_SIZE - 1) {
						index++;
						dib++;
						collisionCount++;
					} else if (table[index] != null && table[index].getDib() >= dib && index == TABLE_SIZE - 1) {
						index = 0;
						dib++;
						collisionCount++;
					}
				}
			}
//				
		} else {
			table[index] = new HashEntry<K, V>(key, value);
			ITEMS++;
		}
		if (ITEMS >= (TABLE_SIZE * loadFactor))
			resize();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void resize() {
		int counter = 0;
		TABLE_SIZE = TABLE_SIZE * 2 + 1;
		//finding the biggest prime number that smaller than table size
		for (int i = TABLE_SIZE; i > 0; i--) {
			for (int j = 1; j < i / 2; j++) {
				int remaining = i % j;
				if (remaining == 0)
					counter++;
				if (counter == 2)
					break;
			}
			if(counter == 1) {
				TABLE_SIZE = i;
				break;
			}
			

		}

		HashEntry[] old = table;
		table = new HashEntry[TABLE_SIZE];
		ITEMS = 0;
		for (int i = 0; i < old.length; i++) {
			if (old[i] != null) {
				put((K) old[i].getWord(), (V) old[i].getValue());
				old[i] = null;
			}
		}
	}

	public void print() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null)
				System.out.println(table[i].getWord());
		}
	}

	public int CollisionCount() {

		return collisionCount;
	}

}
