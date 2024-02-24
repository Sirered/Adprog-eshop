# 2206046696 - Galih Ibrahim Kurniawan
## Link: https://adprog-eshop-sirered.koyeb.app/product/list
__________________________________________
## Module 1
### Reflection 1
Here are some of the clean coding practices I had employed

* All variables have meaningful names. Rather than just assigning vague variables such as String s or int i, I would name such varables like String id or int index

* Made small tasks that has to be done to implement different features into functions to reduce redundancy. For example both edit and delete requires finding the product that matches the id of the passed product within the ArrayList of the repository so instead of writing the for loop to find where theproduct is in the ArrayList to delete or edit it, I just made a function that finds the index according to the id

* Named my functions appropriately. For example for the function that got the index of the product in the arraylist using the id of the product is called findIndexById. Getting the product from the array list using the id is called findProductById etc.

* Formatting the code with proper spacing, so that code isn't squished together. For example for easier readability I would leave a line between functions, to make it more visible where a function ends and the other begins.

As for secure coding practices, due to the absence of user roles and permissions implementation, as well as the fact that thus far the only input forms made has been for creating and editing a product, there hasn't been much in which I could apply secure coding principles to. I made sure no secrets nor passwords were hard coded in the source code and that's about it. To improve the code I would introduce a user system where people would have to sign up to access the product list, and maybe add roles for people who don't work for the business to only be able to view the list, while those who do work for the business are able to manipulate the data (create, edit, delete), because currently having a single list that anyone can manipulate is bound to cause problems. With the introduction of required sign up, we can also time people out if they do anything malicious (like automating a bot to create 100s of products a second in order to overwhelm the server).

### Reflection 2
#### Part 1
Unit tests are an incredibly helpful tool that allows us to check everytuime we make any changes to the code, that all other functionality that was made before still works as intended, without having to manually test every single feature each time. This is especially useful when you start with a naive approach to a problem/task that you know works then try to edit it to be more optimised later on, since you can use the unit tests to make sure that the function still gives correct output even after making the changes. During PBP and SDA, there were so many instances where I made changes to code aiming for higher optimisation or to make it mesh better with other functions in the code, then spending a solid half an hour just rechecking if everything still works correctly, so unit tests wiil hopefully decrease that time of repetitive testing

As for how many unit tests to use for a class, it really depends. I would aim for at the very least having a unit test for each method inside of a class. However, in some cases that isn't enough, because you may need to have multiple tests for one unit test to check for edge cases. For example, you may have one test to check if the delete function works if there is only one product, then test if the delete works properly if there are multiple products in the repository, to ensure that it doesn't delete products that shouldn't be deleted. Hence, my answer to the question of how many unit tests should be made would be a minimum of one for each function, but ideally one for each edge case you can think of.

Just having a code coverage of 100% does not mean that the code 100% works, it just means that each line of code was 100% tested. For example if the test itself is incorrect or outdated then the code that test covers would have issues, like not giving the correct output or the output is not in the correct format. Furthermore code coverage doesn't consider if all edge cases are covered properly. For example I could have 100% code coverage even if the only test I'm doing on the delete function is just testing if it can remove the chosen product. However that doesn't mean that there aren't any bugs in the delete function, because there is the possibility that the delete function actually removes all products which would be considered a bug, ehile still also passing the test.

#### Part 2
Overall putting the tests for checking number of items in the product list in a new test suite with the same setup and variables will overall decrease code cleanliness. This is because having a different test suite for it would just cause unnecessary duplication where everything except the few tests there are will be exactly the same as the HomePageFunctionalTest suite. This would cause confusion due to the fact that the test for the product list on the Home Page is separate from the rest of the tests about the Home Page. One might say that putting this test in a new class would be good due to separation of concerns, even if they relate to the same page, but I think that should only be applicable for pages that have multiple at most semi-related functions and sections within, not a simple page with only a list of products a few buttons and a header. Furthermore, it would be harder to maintain the different test suites with the exact same setup, because if there are any changes that must occur in the setup and instance variables in one suite, it is likely you will have to make the same exact changes in the other suite. This may just be a minor convenience for now since the setup isn't too long and there are only 2 suites with the same setup, but if we were to add more functionality to the home page that is related to the product list and we were to make new test suites for the new features, it would inevitably result in a real pain to deal with if any of the setup needed to be changed. It would be a lot cleaner if the test for product list is in the test suite with the other tests that check the home page, so all related tests will be together and to reduce the redundancy that would be caused from having an entirely separate class with the same function.

__________________________________________
## Module 2
**Link:** https://adprog-eshop-sirered.koyeb.app/product/list

**Code coverage changes**

Repository: 89% -> 100%

ServiceImpl: 5% -> 100%

Controller: 5% -> 100%

Overall: less than 50% -> 97%

![image](https://github.com/Sirered/Adprog-eshop/assets/126568984/43c5602f-ecb6-4fd5-abc2-7c5f71a4a8c2)

### Reflection
#### Part 1: Problems with my code according to PMD and the fixes
**UnusedImport 'org.springframework.web.bind.annotation.*' in the Controller Class**

The reason as to why this was a problem, was because this imported all annotations from that package, due to the use of the '*', despite the fact that not all annotations were used. Thus to fix it I removed that line and wrote the import statement for each of the annotations I was using from that package (which were PostMapping, GetMapping, RequestMapping, ModelAttribute and RequestParam), thus only importing what I needed

**UnnecessaryLocalBeforeReturn in the ServiceImpl Class**

I have a function in my ServiceImpl class called findProductById, which combines my findIndexById and findProductByIndex functions. Initially I had run those 2 functions and stored the result of the findProductByIndex function into a local variable called product, before returning that variable, which was what was causing that segment to be smelly. Thus to combat it, I just instantly returned the result of the findProductByIndex function instead

**UnnecessaryModifier 'public' on the Service Interface**

The methods defined in the interface are public by default, thus adding the 'public' mnodifier is unnecessary. I just had to remove the public modifiers from the methods of the interface

**UseUtilityClass in the Application Class**

This problem occurs, because the class only has one method, and it's static. I did not fix this problem, because that class is from the application being made with Spring Boot and the suggested 'quick' fixes are somewhat problematic. Creating a private Constructor for a class that is never constructed feels like something that goes against code cleanliness. Making it abstract causes problems when testing. Hence I just let it be

#### Part 2: CI/CD

Overall, I believe I have successfully implemented CI/CD. The definition of Continuous Integration is the automation of integrating any changes in code to a shared repository, mainly building and testing the program. With the use of the defined testclasses, the defined gradle test task that runs those test classes and the ci.yml that instantly runs those tests upon every push, any code changes pushed is indeed tested to make sure all functionality still remains consistent even after the changes. Furthermore with the use of external services like Scorecard and PMD along with yml files that instantly run those services upon every push, my code's quality is also automatically checked. Hence, due to these workflows, I hold the opinion that I have successfully implemented Continuous Integration. As for Continuous Deployment, its definition is the deploying code changes to product environments, so any changes can be accessed and tested by users asap, so they can give appropriate feedback to the developers. With the use of Koyeb's services that deploys the app via Koyeb on every push on main, I think I have successfully implemented Continuous Deployment. Since I have implemented both Continuous Integration and Continuous Deployment, I have successfully implemented CI/CD

__________________________________________
## Module 3

##### The Following are classes that are no longer in use, but I have kept, because I have not refactored the tests to use the new implementation of repository and service yet

ProductRepository, CarRepository, ProductService, ProductServiceImpl, CarService, CarServiceImpl

### Reflection

#### Explanation and Implementation of each aspect of SOLID

##### Single Responsibility Principle

**What is it?**

Single Responsibility Principle(SRP) states that each class should have one and only one reason to change(responsibility). This does not mean that each class can only have one method, it means that all methods of a class should all only fulfill a single purpose and no more than that. This separation of classes by purpose makes code a lot more readable and anderstandable, as the classes are now a lot more compact and there is less cluttering that is caused from an abundance of methods that are only loosely related to each other. Furthermore the project is a lot more maintainable, because with this separation by purpose, when we need to make modifications or extend existing functionality, we can easily focus on each aspect of this new/changed feature separately (by going to the class responsible for fulfilling that specific purpose), with little fear of there being any consequences to other aspects of the project. Lastly, testing becomes a lot easier, because this separation of concerns allow us to make simpler tests and if a specific part of a feature is causing the test to fail, it is much easier to traceback to the root cause of the issue.

**How did I implement such?**

The Spring Boot framework already facilitates SRP by separating the Controllers that deal with routing and interaction, from the business logic(service), which is separated from the database logic (repository), which is separated from the definition of the types of data being stored (model). To further implement SRP I have also separated the original Repository classes that we had into 2 classes each, one being a ShopRepository while the other is a RepositoryHandler (these are abstract classes that have been extended according to the type of data that they store/handle with the use of java generics). The ShopRepository fulfills the purpose of representing the database, where the items are stored and has methods related to fetching this stored data. RepositoryHandlers fulfills the purpose of updating the data inside the database by having methods that update, delete and create. 

When the methods for these 2 purposes were in 1 class, trying to find the method that I was trying to modify or refer to would sometimes be a hassle, despite the fact that I this is a solo project, because it would be among 5 methods that are only loosely related to each other by the fact that they deal with the database in some way. I shudder to think how it would be if we were to scale up and require more complex methods. Now since they are in 2 separate and comparably shorter classes where the methods of each class are more closely related to each other, the code is more organised and purpose of the classes are more concrete and understandable. 

Furthermore, the code is a lot more reusable, since if there were ever a case in which a role was introduced that can only read the list, but not make any changes to it, we could just extend the ShopRepository class. I know we could've also reused the previous implementation for repository to implement this read-only scenario, just not use the create, update and delete on the Service layer, but I think it is a lot neater and increases clarity regarding the purpose of each class used to implement a feature if those classes only contain methods and variables that contribute to the implementation of the feature. 

I know that technically if we wanted to fully commit to SRP we could separate RepositoryHandler to 3 classes, 1 for creating, 1 for updating and 1 for deleting, but I think that is exessive, especially since as of right now I don't think there is much of a chance for there to be variations of creating, updating and deleting for this project, so separating read methods from modification methods is sufficient.

##### Open Closed Principle

**What is it?**

Open Closed Principle (OCP) states that modules should be made in such a way where once they have been completed and tested, they shouldn't need to be modified when introducing new features. Instead they should be extendable, so that if new features need to be implemented, you can implement those new features via inheritance or the implementation of interfaces or abstract classes found in the already established and tested source code. This allows for easier extension of already established features.

**How did I implement it?**

First I created an abstract class called shopItem which has the variables id, name and quantity, along with the getter and setter methods for those variables. The already established Car and Product model classes have now become extensions of shopItem. Then for all of the interfaces and abstract classes that are used to implement services and repositories, with the help of java generics, I made sure all the concrete methods in the abstract classes and all methods that need to be implemented will work with any class of item, as long as that class extends ShopItem. This means that if someone wanted to add a new type of store item, let's say Shirts as an example, that will have it's own repository and service, they can just make a Shirt class that extends ShopItem. Afterwards for all of the interfaces and abstract classes needed to make Repositories and Services, they can implement or extend them by doing implements Interface<Shirt> or extends AbstractClass<Shirt>, then defining the required methods and any additional methods specific to the class Shirt. Lastly they use all of the classes that came from implementing or extending the interfaces or abstract classes and make the Controller and appropriate templates. By taking these steps anyone can easily extend my current project to add any type of shopitem they want, as long as it has id, name and quantity, without having to go back and modify the source code, nor having to create the services and repositories from scratch.

##### Liskov Substitution Principle

**What is it?**

Liskov's Substitution Principle (LSP) states that if ClassA is a subclass/extends ClassB, it should be able to do all methods that ClassB can do in a similar manner. For example let's say ClassB has a method funct(float) in which it can work with any float number and will result in the modification of variable Z. If ClassA overrides that method in a way in which it can only process whole numbers or it modifies no variables or it modifies variables other than Z, that would be a violation to LSP, because even though it still has method func(float), in essence it works entirely differently. This is an issue, because keep in mind that if you extend a class, it is subjest to an is-a relationship. It would be weird if we were to give the same command to 2 different entities and they were to respond differently, just because one is an instance of ClassB, but not ClassA, while the other is an instance of ClassA thus would be an instance of ClassB too. If you ever find methods that violate LSP, it would be best to either make it a different method (so don't override unless they work similarly) or reconsider if ClassA does indeed extend ClassB. 

**How did I implement it?**

To prevent violations of LSP, I made sure that all parent classes within my project only had variables and methods that I was certain that all of it's subclasses would have and wouldn't change much in how it works. For example ShopItem, which is an abstract class and is extended by Car and Product, has the variables id, name and quantity, as well as their respective getter and setter methods. I am confident that those variables and methods won't change, because I know that any item added to the shop should have id, name and quantity, otherwise I wouldn't consider them items in the shop and rather some other type of item. Another example is how my abstract class RepositoryHandler<T extends ShopItem> has the concrete methods create and delete defined, because I knew that regardless of what T is, these 2 functions will always just be adding or deleting to the list in the repository. I also have the abstract method update, because I knew that no matter what, any subclass of RepositoryHandler will have an update method which in essence will change every value of an object of type T that isn't 'id' to the values that have been inputted in the parameter section. Making sure that the functions defined in a parent class P must be implemented similarly in the subclass, otherwise I wouldn't consider them a subclass of P, will prevent violations of LSP now and in the future.

Also there was a violation of LSP when CarController extended ProductController. Despite the fact that the functions that the CarController shared with ProductController did work similarly, I still considered that a violation of LSP, because logically it shouldn't be the case. Due to CarController being the subclass of ProductController, the path /car/list would bring you to a list of products, just like product controller. This shouldn't be the case. The path /car/list should logically bring you to a list of cars instead, in which case LSP would be violated. So CarController extending ProductController either doesn't make sense logically or violates LSP, so I removed the 'extends ProductController' part

##### Interface Segregation Principle

**What is it?**

The Interface Segregation Principle states that if you have or plan on adding a decently sized interface and you can think of possible use cases in which you would want a class that implements some of the methods in that interface, but not all of them, it would be best to break up that interface into smaller interfaces, so that if you ever do need only some of the methods from the original interface, but not all, you can implement one of the smaller interfaces that fit what is needed for that class. If at any point there is a class that implements an interface and any of the methods taken from the interface is defined to do nothing or is never used, that would be a violation of ISP and introduces unnecessary clutter. When breaking up a larger interface into smaller it is best to think about what features you might want to implement that would use some of the methods in the interface, if not all of the methods in that interface is necessary for the implementation of any of the features then you can take that collection and make it it's own interface, and remove them from the original interface. If any class needs all of the methods from the original interface, they can just implement multiple interfaces.

**How did I implement it?**

With the method I discussed above I realised that I might eventually want a feature that incorporates customer roles, where they can view shop item lists and buy items from them. Among the methods in the Product/CarService interface, only findAll() and findById() were necessary for implementing a customer role, since customers shouldn't be able to add, delete or update a shop's list of items. Thus I made a new interface called ReadService with the methods findAll() and findById() and removed them from the Product/CarService interface (which I renamed to SellerService<T>). I did also make it so that SellerService extends ReadService, but that was just to make Spring Autowiring simpler. Plus, a seller should be able to read their own products so I think it is fine for now

##### Dependency Inversion Principle

**What is it?**

The Dependency Inversion Principle states that higher level modules should depend on abstractions or interfaces of lower level modules, rather than depending on lower-level concrete classes. This will allow us to have multiple implementations or types of the abstracted module that will all work with the higher level module, as long as the implemented methods work properly.

**How did I implement it?**

I made an abstract class named ShopItem that all models must extend. With the use of Java generics, I was able to make the abstract classes ShopRepository<T> and RepositoryHandler<T> where T must be a class tht extends ShopItem. Since I know that all object of ShopItem have the method getId, I am able to define the implementation of findById concretely in ShopRepository<T> without fear of error due to T not having the method getId. All other concrete methods from the 2 abstract methods work, because it uses properties of the List class, which is not dependent on what exactly type T is, as long as what is put in and read from the list is also o type T. I did have to define concrete classes that extended ShopRepository for each subclass of ShopItem in my project, for Autowiring purposes, however that only required the line NameOfConcreteClass extends ShopRepository<NameofSubclassofShopItem> {}. I also had to create concrete classes for RepositoryHandler for Autowiring purposes and to define the update method (which is an abstract method in RepositoryHandler). 

Then on the service layer, I made an interface defining the methods a SellerService<T> should have. I couldn't use abstract classes in this layer to minimize code repetition, because on the Service layer you have to Autowire what is in your Repository layer, and I can't autowire ShopRepository<T> without specifying a concrete subclass of T, because there would be multiple candidate Bean exceptions. So for every subclass of ShopItem, I made a ServiceImpl that implements SellerService<NameofSubclassofShopItem>. In these ServiceImpls I was able to Autowire the abstract classes from the RepositoryLayer, by specifying which subclass of ShopItem I was using in that Service class in the generics section and work with the knowledge that the repository layer methods needed are available and work properly.

Lastly on the Controller level I made a Controller for every subclass of ShopItem. To facilitate interaction between Controller and Service layer, I just needed to autowire SellerService<NameofSubclassofShopItem> and thn I can implement the controllers with the knowledge that all required methods from the Service layer is available and work properly. 

All in all I think I did implement DIP well, specifically between the Model and Repository layer, but above that, due to autowiring constraints and my unfamiliarity with the Spring framework and Java interfaces and abstract classes in general (we only learnt Java in ddp2, and nowhere else), my implementation of DIP was less useful

#### Advantages of implementing SOLID

**Note: advantages of implementing SOLID and disadvantages of not implementing SOLID is the same thing, so anything in this section, could also be a disadvantage of not implementing SOLID and vice-versa**

Firstly, it makes extending features a lot more easier. With my implementation of OCP that I had detailed previously, any future additions to categories of ShopItems will be a lot faster to implement. Before implementation, I would have to write the code for everything from scratch whenever we needed to add a new type of ShopItem. Now, with the implementation of OCP, I just have to extend a few classes and implement a few interfaces and the task of extension would be finished in half the time compared to before, while letting me still be confident that the interactions between my classes work properly, as long as the methods follow the LSP and they implement the interfaces/abstract classes I have made correctly. 

Secondly, it allows for better separation of concerns. Due to the implementation of SRP, there is now more separation between purposes in my Repository layer, allowing for better readability, understanding and maintainability as everything is in shorter, neater classes, rather than a clutter of loosely related methods in one class. This allows us to better focus on certain aspects of features that we want to implement or improve one at a time, while preventing unintended changes to occur in other aspects of the projhect.

Overall, SOLID allows for easier extension of established code, as well as making code more readable, understandable and scalable.

#### Disadvantages of not implementing SOLID

Firstly, without SOLID, specifically if we didn't think about ISP, it is possible for there to be an interface that right now is serviceable with the current demands on my project, but might be issue in the future. For example if I just kept it so that all of my interfaces required create, update and delete, if there ever came a time where we implement a role that can only read and all of our classes interacts with the already established interface, I might find difficulties in refactoring the interfaces and any corresponding classes that implement those interfaces to accomodate this new demand. The other option would be making classes that can create, update and delete when it shouldn't, which would cause confusion if I were to ever reread my code.

Secondly, without SOLID, there would be less consideration in keeping interaction between methods to be consistent, causing minor inconsistencies in one part of the project. Like originally I would just replace the item in the list with the updated item from the form in Product, while in car it set the values onto the item in the list. At some point this may cause an error when doing comparison for example, where it returns correct values when I'm comparing edited cars, but not for when I'm comparing edited Products, which would cause me to go insane trying to find what went wrong. Having it so that implementation is consistent and follow strict contracts is important to establish early on before the project gets too big. In fact during the conversion to solid, I was dealing with inconsistent naming of methods (like sometimes it would findByCarId and sometimes it would be findWithId), due to the absence of the use of interfaces to facilitate communication between layers, causing me confusion when trying to connect one layer to another, causing possible minor mishaps. Now that I have implemented DIP and LSP that is less of a concern.

