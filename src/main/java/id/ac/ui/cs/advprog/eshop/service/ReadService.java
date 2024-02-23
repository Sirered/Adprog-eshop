package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.ShopItem;

import java.util.List;

public interface ReadService<T extends ShopItem> {
    List<T> findAll();
    T findById(String id);
}
