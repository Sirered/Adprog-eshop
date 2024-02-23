package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.ShopItem;

public interface SellerService<T extends ShopItem> extends ReadService<T> {
    T create(T shopItem);
    T update(T updatedShopItem);
    void delete(String id);
}
