- register uer
- login user

- add/delete/edit expense
- add/delete/edit income

expense input -> amount, expense category (optional eg:entertainment, health, groceries), payment method( optional eg: cash, cc, debit card, etc), date ( optional, default: current date and time)
income input -> amount, income category (optional eg:salary, freelance, bonus, etc)

- list all expense -> beautify o/p -> dao done
- list all income
- list expenses by category like eg groceries 20k ,45% -> dao done
- filter expense by category -> dao done
- filter expense by date -> dao done
- filter expense by date (from, to date) -> dao done
- filter expense by year and month -> dao done
- report like top 5 expenses, top 5 category by expense -> dao done

- manage income -> list all income, add/update/delete income, filter
- manage Expense -> list all expense, add/update/delete expense, filter

show income expenses remaining

fix add expense out of bound excep when entered zero

check for outofbound when listing item to let the user select. should be like this no>=1 && no<=size()

allow users to update their profile

shouldn't call db each time. handle everything locally and let the user decide when to sync to database like a save to db or sync db function.

user specific categories
SELECT c.id, c.name
FROM user_category uc
JOIN category c ON uc.category_id = c.id
WHERE uc.app_user_id = ?;

to insert exisiting catgoeies
INSERT INTO user_category (app_user_id, category_id)
SELECT ?, id
FROM category;

new menu -> category CRUD