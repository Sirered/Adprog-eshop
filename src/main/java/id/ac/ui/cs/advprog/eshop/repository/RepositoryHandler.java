package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.ShopItem;

import java.util.UUID;

public abstract class RepositoryHandler<T extends ShopItem>  {
    public T create(ShopRepository<T> shopRepository, T shopItem) {
        if(shopItem.getId() == null){
            UUID uuid = UUID.randomUUID();
            shopItem.setId(uuid.toString());
        }

        shopRepository.data.add(shopItem);
        return shopItem;
    }

    public void delete(ShopRepository<T> shopRepository, String id) { shopRepository.data.removeIf(shopItem -> shopItem.getId().equals(id));}

    public abstract ShopItem update(ShopRepository<T> shopRepository, T updatedShopItem);
}
