package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.ShopItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ShopRepository<T extends ShopItem> {
    List<T> data = new ArrayList<>();

    public Iterator<T> findAll() {
        return data.iterator();
    }

    public T findById(String id) {
        for (T shopItem : data) {
            if (shopItem.getId().equals(id)) {
                return shopItem;
            }
        }
        return null;
    }
}
