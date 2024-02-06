# 2206046696 - Galih Ibrahim Kurniawan
__________________________________________
## Reflection 1
Here are some of the clean coding practices I had employed

* All variables have meaningful names. Rather than just assigning vague variables such as String s or int i, I would name such varables like String id or int index

* Made small tasks that has to be done to implement different features into functions to reduce redundancy. For example both edit and delete requires finding the product that matches the id of the passed product within the ArrayList of the repository so instead of writing the for loop to find where theproduct is in the ArrayList to delete or edit it, I just made a function that finds the index according to the id

* Named my functions appropriately. For example for the function that got the index of the product in the arraylist using the id of the product is called findIndexById. Getting the product from the array list using the id is called findProductById etc.

* Formatting the code with proper spacing, so that code isn't squished together. For example for easier readability I would leave a line between functions, to make it more visible where a function ends and the other begins.

As for secure coding practices, due to the absence of authorisation and authentication implementation, as well as the fact that thus far the only input form made has been for creating and editing a product.
