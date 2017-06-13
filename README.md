# Coding Exercise

Write a program and associated unit tests that can price a basket of goods taking into account some special offers.

The goods that can be purchased, together with their normal prices are:
- Soup – 65p per tin
- Bread – 80p per loaf
- Milk – £1.30 per bottle
- Apples – £1.00 per bag

Current special offers:
- Apples have a 10% discount off their normal price this week
- Buy 2 tins of soup and get a loaf of bread for half price

The program should accept a list of items in the basket and output the subtotal, the special
offer discounts and the final price.

## How to get setup ##
You would need Java 8 and maven 2 to run this project
 
## How to run it ##
First extract the zip file provided and go the folder, then run:

`mvn verify`

then run:
 
`java -jar target/pricing-basket-1.0-SNAPSHOT.jar itemList.json offersListOrder1.json`
`java -jar target/pricing-basket-1.0-SNAPSHOT.jar itemList.json offersListOrder2.json`