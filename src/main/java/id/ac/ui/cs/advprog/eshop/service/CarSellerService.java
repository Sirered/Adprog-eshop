package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.RepositoryHandler;
import id.ac.ui.cs.advprog.eshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarSellerService implements SellerService<Car>{
    @Autowired
    private ShopRepository<Car> carRepository;

    @Autowired
    private RepositoryHandler<Car> repositoryHandler;

    @Override
    public Car create(Car car) {
        repositoryHandler.create(carRepository, car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCars = new ArrayList<>();
        carIterator.forEachRemaining(allCars::add);
        return allCars;
    }


    public Car findById(String id){
        Car car = carRepository.findById(id);
        return car;
    }


    public Car update(Car car) {
        repositoryHandler.update(carRepository, car);
        return car;
    }


    public void delete(String id) {
        repositoryHandler.delete(carRepository, id);
    }
}

