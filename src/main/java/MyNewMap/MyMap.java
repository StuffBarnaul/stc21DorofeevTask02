package MyNewMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

// TODO VisualVM

public class MyMap<K,V> {
    private Bucket[] buckets;

    public MyMap() {
        this.buckets = (Bucket[]) Array.newInstance(Bucket.class,16);
    }
    public MyMap(int buckets) {
        this.buckets = (Bucket[]) Array.newInstance(Bucket.class,buckets);
    }

    private int getHash(K key) {
        return Objects.hash(key)%buckets.length;
    }

    public int size() {
        int size = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) continue;
            size+=buckets[i].getSize();
        }
        return size;
    }

    public V put(Object key, Object value){
        int hash = getHash((K) key);
        if (buckets[hash] == null){
            if (key == null && value == null) buckets[hash] = new Bucket(Object.class,Object.class);
            if (key == null && value != null) buckets[hash] = new Bucket(Object.class,value.getClass());
            if (key != null && value == null) buckets[hash] = new Bucket(key.getClass(),Object.class);
            if (key != null && value != null) buckets[hash] = new Bucket(key.getClass(),value.getClass());
        }
        return (V) buckets[hash].addElement(key,value);
    }

    public V remove(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) throw new NoSuchElementException();
        return (V) buckets[hash].removeElement(key);
    }

    public V get(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) throw new NoSuchElementException();
        return (V) buckets[hash].findElementByKey(key);
    }

    public boolean containsKey(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) return false;
        return buckets[hash].containsKey(key);
    }

    public boolean containsValue(V value){
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null && buckets[i].containsValue(value)) return true;
        }
        return false;
    }
}
