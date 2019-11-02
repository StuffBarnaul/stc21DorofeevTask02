package stc21;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

/*
 Спроектировать и реализовать класс, реализующий коллекцию типа "хеш-таблица" (hash-map),
 или двунаправленная хеш-таблица (bi-map), без использования стандартных или библиотечных
 реализаций коллекций (можно использовать массивы). В качестве ключей и значений могут
 использоваться любые объекты. В коллекции должны быть методы добавления, обновления,
 удаления элемента, проверка наличия элемента по ключу (и по значению в случае двунаправленной таблицы),
 получение количества элементов в коллекции.  В случае совершения недопустимых операций выбрасывать
 исключения. Протестировать работоспособность с помощью юнит-тестов, включая положительные
 (при корректных операциях с коллекцией она ведет себя корректно), и негативные тесты
 (исключения при некоректных операциях).
 */

public class MapManager<K,V>
{
    private K[] keys;
    private V[] values;
    private int size;
    boolean containsNullKey = false;

    public K[] getKeys() {
        return keys;
    }
    public V[] getValues() {
        return values;
    }
    public int getSize() {
        return size;
    }

    public MapManager(Class<K> keyClass, Class<V> valueClass) {
        this.keys = (K[]) Array.newInstance(keyClass, 1);
        this.values = (V[]) Array.newInstance(valueClass, 1);
        this.size = 0;
    }

    public V addElement(K key,V value){
        boolean isAdding = true;
        V result = null;
        while (size > keys.length-1) increaseArraySize();
        for (int i = 0; i < keys.length; i++) {
            if (key == null){
                if (keys[i] == null){
                    if (containsNullKey) isAdding = false;
                    result = values[i];
                    values[i] = value;
                    break;
                }
            } else if (key.equals(keys[i])) {
                isAdding = false;
                result = values[i];
                values[i] = value;
                break;
            }
        }
        if (isAdding) {
            if (key == null) containsNullKey = true;
            keys[size] = key;
            values[size] = value;
            size++;
        }
        return result;
    }

    public V removeElement(K key){
        while (size < keys.length*2/3) decreaseArraySize();
        for (int i = 0; i < keys.length; i++) {
            if (key == null){
                if (keys[i] == null){
                    V result = values[i];
                    for (int j = i; j < keys.length-1; j++) {
                        keys[j] = keys[j+1];
                        values[j] = values[j+1];
                    }
                    size--;
                    return result;
                }
            } else if (keys[i] == null) {
            } else {
                if (key.equals(keys[i])){
                    V result = values[i];
                    for (int j = i; j < keys.length-1; j++) {
                        keys[j] = keys[j+1];
                        values[j] = values[j+1];
                    }
                    size--;
                    return result;
                }
            }
        }
        throw new NoSuchElementException();
    }

    public V findElementByKey(K key){
        for (int i = 0; i < size; i++) {
            if (key == null){
                if (keys[i] == null) return values[i];
            } else if (key.equals(keys[i])){
                return values[i];
            }
        }
        return null;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if (value == null){
                if (values[i] == null) return true;
            } else if (values[i] == null) {
            } else if (values[i].equals(value)) return true;
        }
        return false;
    }

    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if (key == null) {
                if (keys[i] == null) return true;
            } else if (keys[i] == null) {
            } else if (keys[i].equals(key)) return true;
        }
        return false;
    }

    private void increaseArraySize() {
        int newSize = size*3/2+1;
        keys = Arrays.copyOf(keys,newSize);
        values = Arrays.copyOf(values,newSize);
    }

    private void decreaseArraySize() {
        int newSize = size*2/3;
        keys = Arrays.copyOf(keys,newSize);
        values = Arrays.copyOf(values,newSize);
    }
}
