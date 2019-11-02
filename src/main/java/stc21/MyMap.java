package stc21;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

// TODO VisualVM

public class MyMap<K,V> {
    private MapManager[] buckets;
    //private int size;
    ArrayList l = new ArrayList();

    public MyMap() {
        this.buckets = (MapManager[]) Array.newInstance(MapManager.class,16);
        //this.size = 0;
    }

    public MyMap(int buckets) {
        this.buckets = (MapManager[]) Array.newInstance(MapManager.class,buckets);
        //this.size = 0;
    }

    public static void main(String[] args) {
        String s1 = "s1";
        String s2 = "s2";
        String s3 = "s3";
        String s4 = "s4";
        int i1 = 1;
        int i2 = 2;
        int i3 = 3;
        int i4 = 4;
        HashMap map = new HashMap() {{
            put(s1,i1);
            put(s2,i2);
            put(s3,i3);
            put(s4,i4);
        }};
        Object a1 = map.put(null,null);
        Object a2 = map.put(null,"5");
        Object a3 = map.put(null,null);
        Object a4 = map.put("null",6);
        int f = 0;
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

    public V put(K key, V value){
        int hash = getHash(key);
        if (buckets[hash] == null) buckets[hash] = new MapManager(key.getClass(),value.getClass());
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
            if (buckets[i] == null) continue;
            else {
                if (buckets[i].containsValue(value)) return true;
            }
        }
        return false;
    }
}
