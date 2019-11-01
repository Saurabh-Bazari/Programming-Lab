% facts of Starter with Item name,Nutrient value,Index
starter("Corn Tikki",30,1).
starter("Tomato Soup",20,2).
starter("Chilli Paneer",40,3).
starter("Crispy chicken",40,4).
starter("Papdi Chaat",20,5).
starter("Cold Drink",20,6).

% facts of Main dish with Item name,Nutrient value,Index
main_dish("Kadhai Paneer with Butter / Plain Naan",50,1).
main_dish("Veg Korma with Butter / Plain Naan",40,2).
main_dish("Murgh Lababdar with Butter / Plain Naan",50,3).
main_dish("Veg Dum Biryani with Dal Tadka",50,4).
main_dish("Steam Rice with Dal Tadka",40,5).

% facts of desert with Item name,Nutrient value,Index
desert("Ice-cream",20,1).
desert("Malai Sandwich",30,2).
desert("Rasmalai",10,3).

% fact of menu in hungry take all category
% in diet only one category,
% and in not_so_hungry main dist and one form starter and desert.
menu(diet,0,0,1).
menu(diet,0,1,0).
menu(diet,1,0,0).
menu(hungry,1,1,1).
menu(not_so_hungry,0,1,1).
menu(not_so_hungry,1,1,0).

% hungrySet take one item from all categories 
% using Backtracking and print them
hungrySet() :- starter(Starter,_,_),
                main_dish(MainDish,_,_),
				desert(Desert,_,_),
				write("Items : "),
				write(Starter),write(" ,"),
				write(MainDish),write(" ,"),
				write(Desert).
                            
% hungrySet take one item from maindish and starter
% using Backtracking and print them							
not_so_hungryWithStarter() :- main_dish(MainDish,NutrientOfMainDish,_),
								starter(Starter,NutrientOfStarter,_),
								80>=NutrientOfMainDish+NutrientOfStarter,
								write("Items : "),
								write(MainDish),write(" ,"),
								write(Starter).

% notsohungry take one item from maindish and desert
% using Backtracking and print them		
not_so_hungryWithDesert() :- main_dish(MainDish,NutrientOfMainDish,_),
							desert(Desert,NutrientOfDesert,_),
							80>=NutrientOfMainDish+NutrientOfDesert,
							write("Items : "),
							write(MainDish),write(" ,"),
							write(Desert).

% print List with comma
printList([Head]) :- write(Head),nl.

printList([Head1,Head2|Tail]) :- write(Head1),
									write(" , "),
									printList([Head2|Tail]).

% print Item before List
printItemList(List) :- write("Items : "),
					printList(List).

% diet take one item from starter
% using Backtracking and print them	
dietWithStarter(List,Limit,Index) :- starter(Item,Nutrient,Index),
										LimitNew is Limit-Nutrient,
										(( LimitNew >= 0,append(List,[Item],X1),printItemList(X1),dietWithStarter(X1,LimitNew,Index));
										(NextIndex is Index + 1,dietWithStarter(List,Limit,NextIndex))).

% diet take one item from main dish
% using Backtracking and print them	
dietWithMainDish(List,Limit,Index) :- main_dish(Item,Nutrient,Index),
									LimitNew is Limit-Nutrient,
									(( LimitNew >= 0,append(List,[Item],X1),printItemList(X1),dietWithMainDish(X1,LimitNew,Index));
									(NextIndex is Index + 1,dietWithMainDish(List,Limit,NextIndex))).

% diet take one item from desert
% using Backtracking and print them	
dietWithDesert(List,Limit,Index) :- desert(Item,Nutrient,Index),
									LimitNew is Limit-Nutrient,
									(( LimitNew >= 0,append(List,[Item],X1),printItemList(X1),dietWithDesert(X1,LimitNew,Index));
									(NextIndex is Index + 1,dietWithDesert(List,Limit,NextIndex))).

% all function takeont category and select category by 1,0
% and succesively satisfy their condition and then print them
find_items(hungry,StarterBit,MainDishBit,DesertBit) :- menu(hungry,StarterBit,MainDishBit,DesertBit),
						forall(hungrySet(),nl).

find_items(not_so_hungry,StarterBit,MainDishBit,DesertBit) :- menu(not_so_hungry,StarterBit,MainDishBit,DesertBit),
									StarterBit == 0,
									forall(not_so_hungryWithDesert(),nl).

find_items(not_so_hungry,StarterBit,MainDishBit,DesertBit) :- menu(not_so_hungry,StarterBit,MainDishBit,DesertBit),
									DesertBit == 0,
									forall(not_so_hungryWithStarter(),nl).


find_items(diet,StarterBit,MainDishBit,DesertBit) :- menu(diet,StarterBit,MainDishBit,DesertBit),
							StarterBit == 1,
							forall(dietWithStarter([],40,1),nl).

find_items(diet,StarterBit,MainDishBit,DesertBit) :- menu(diet,StarterBit,MainDishBit,DesertBit),
							MainDishBit == 1,
							forall(dietWithMainDish([],40,1),nl).

find_items(diet,StarterBit,MainDishBit,DesertBit) :- menu(diet,StarterBit,MainDishBit,DesertBit),
							DesertBit == 1,
							forall(dietWithDesert([],40,1),nl).