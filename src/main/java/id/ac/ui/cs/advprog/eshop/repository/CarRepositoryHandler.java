package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepositoryHandler extends RepositoryHandler<Car>{
    @Override
    public Car update(ShopRepository<Car> shopRepository, Car updatedCar) {
        Car car = shopRepository.findById(updatedCar.getId());

        if (car == null) {
            return null;
        }

        car.setName(updatedCar.getName());
        car.setCarColor(updatedCar.getCarColor());
        car.setQuantity(updatedCar.getQuantity());

        return car;
    }
}
