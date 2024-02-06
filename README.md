# 2206046696 - Galih Ibrahim Kurniawan
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

