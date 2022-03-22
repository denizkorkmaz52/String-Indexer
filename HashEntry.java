
public class HashEntry<K,V> {

	private K word;
	private int dib;
	private V value;

	public HashEntry(K word, V value) {
		this.word = word;
		this.value = value;
		this.dib = 0;
	}

	public K getWord() {
		return word;
	}

	public void setWord(K word) {
		this.word = word;
	}

	public int getDib() {
		return dib;
	}

	public void setDib(int dib) {
		this.dib = dib;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	
	

}
