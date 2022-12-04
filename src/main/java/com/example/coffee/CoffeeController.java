package com.example.coffee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {
    //private List<Coffee> coffees = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    // При старте приложения Spring автоматически инстанциирует CoffeeRepository
    // и добавит ссылку на него в это поле
    @Autowired
    CoffeeRepository coffeeRepository;

    public CoffeeController() {
//        coffees.addAll(
//                List.of(new Coffee("Espresso"),
//                        new Coffee("Cappuccino"),
//                        new Coffee("Latte"),
//                        new Coffee("Ristretto"),
//                        new Coffee("Macchiato")
//                )
//        );

//        coffeeRepository.saveAll(
//                List.of(new Coffee("Espresso"),
//                        new Coffee("Cappuccino"),
//                        new Coffee("Latte"),
//                        new Coffee("Ristretto"),
//                        new Coffee("Macchiato")
//                )
//        );

    }

    //@GetMapping(path = "/coffees")  // http://localhost:8080/coffees
    @GetMapping
    // public List<Coffee> getCoffees()
    public Iterable<Coffee> getCoffees() {

        // return coffees;
        return coffeeRepository.findAll();
    }

    // Напишите метод, который вернет конкретное кофе по его идентификатору
    // http://localhost:8080/coffees/aeb62ee3-3111-41ff-b720-a7225b684b75
    // getCoffeeById

    //@GetMapping("/coffees/{id}")
    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(
            @PathVariable(name = "id") String id
    ) {
//        for (Coffee c : coffees) {
//            if (c.getId().equals(id))
//                return Optional.of(c);
//        }
//        return Optional.empty();

        return coffeeRepository.findById(id);
    }

    // Напишите метод, который удалит кофе по его идентификатору
    // DELETE http://localhost:8080/coffees/aeb62ee3-3111-41ff-b720-a7225b684b75

    //@DeleteMapping(path = "/coffees/{id}")
    @DeleteMapping("/{id}")
    public void deleteCoffee(
            @PathVariable(name = "id") String id
    ) {
        // coffees.removeIf(coffee -> coffee.getId().equals(id));
        coffeeRepository.deleteById(id);
    }

    //@PostMapping("/coffees")  // создает на сервере новый элемент
    @PostMapping
    public Coffee postCoffee(
            @RequestBody Coffee coffee
    ) {
        // coffees.add(coffee);
        coffeeRepository.save(coffee);
        return coffee;
    }

    /*
    //@PutMapping("/coffees/{id}")  // меняет элемент на сервере
    @PutMapping("/{id}")
    public Coffee putCoffee(
            @PathVariable(name = "id") String id,
            @RequestBody Coffee coffee
    ) {
        int coffeeIndex = -1;
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
    }
     */

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> putCoffee(
            @PathVariable(name = "id") String id,
            @RequestBody Coffee coffee
    ) {
        log.info("PUT " + coffee.getId() + "|" + coffee.getName());

        /*
        int coffeeIndex = -1;
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee, HttpStatus.OK);
         */

        if (coffeeRepository.existsById(id)) {
            coffeeRepository.save(coffee);
            return new ResponseEntity<>(coffee, HttpStatus.OK);
        } else {
            coffeeRepository.save(coffee);
            return new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED);
        }
    }

    // напишите метод, который вернет кофе по его названию
    // если такого нет, вернуть NULL
    // PATCH http://localhost:8080/coffees/name/Espresso

    // ДЗ проф ява:
    // *Добавьте поиск по названию кофе в контроллер, раскомментировав и поправив последнюю функцию.
    // Сделайте ее как List<Coffee> getCoffeeByName(@PathVariable String name) -
    // передаваемый шаблон name должен использоваться как подстрока в названии кофе,
    // функция должна возвращать список кофе, в имени которых содержится строка-шаблон.
    // Вам потребуется внести изменение и в Repository -
    // нужно будет добавить внутрь интерфейса функцию с правильным названием,
    // возвращающую список кофе - посмотрите статью https://www.baeldung.com/spring-jpa-like-queries
    // чтобы попытаться "нащупать" название этой функции.

    @GetMapping("/name/{name}")
    public List<Coffee> getCoffeeByName(
            @PathVariable(name = "name") String name) {

//        return coffees.stream()
//                .filter(coffee -> coffee.getName().equals(name))
//                .findFirst();

        return coffeeRepository.findByNameLike("%" + name + "%");
    }
}