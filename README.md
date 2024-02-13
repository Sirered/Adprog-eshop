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
