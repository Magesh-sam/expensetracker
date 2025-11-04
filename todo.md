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