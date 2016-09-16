package com.igitras.hikari;

import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author mason
 */
public class TreeMapTest {

    @Test
    public void testTreeMap() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("mason", "mason");
        treeMap.put("mason.hob", "bog");
        treeMap.put("mason.fav", "fav");
        treeMap.put("cath", "cath");
        treeMap.put("zh", "zh");
        SortedMap<String, String> stringStringSortedMap = treeMap.subMap("mason", "z");
        System.out.println(stringStringSortedMap);
    }
}
