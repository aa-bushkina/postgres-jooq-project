# Interaction with the DBMS is carried out through the jOOQ library. The database that the project works with describes the entities Invoice, Product, Organization. There may be several items for each invoice. The invoice indicates the Organization, and the item indicates the Product.

Invoices
Invoice № | Invoice date |Sender Organization

Invoice items
Price | Product | Quantity


Organizations
Name | INN | Settlement account


Products
Name | Internal code

A script has been written to create a database for these relationships.
Create managers that provide CRUD operations with entities
Requests for building reports have been written:
- Select the first 10 suppliers according to the quantity of delivered goods
- Select suppliers with the quantity of delivered goods above the specified value (the product and its quantity must allow multiple indication).
- For each day for each product, calculate the quantity and amount of goods received in the specified period, calculate the totals for the period
- Calculate the average price of the received goods for the period
- Display a list of goods delivered by organizations for the period. If the organization did not deliver the goods, then it should still be reflected in the list.

Tests have been written

The application accepts command line arguments:
1) database url
2) name of the database
3) username
4) password

Example:
jdbc:postgresql://localhost/ db_name postgres postgres