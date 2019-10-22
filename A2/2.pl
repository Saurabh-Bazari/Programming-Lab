starter("Corn Tikki",30,1).
starter("Tomato Soup",20,2).
starter("Chilli Paneer",40,3).
starter("Crispy chicken",40,4).
starter("Papdi Chaat",20,5).
starter("Cold Drink",20,6).

main_dish("Kadhai Paneer with Butter / Plain Naan",50,1).
main_dish("Veg Korma with Butter / Plain Naan",40,2).
main_dish("Murgh Lababdar with Butter / Plain Naan",50,3).
main_dish("Veg Dum Biryani with Dal Tadka",50,4).
main_dish("Steam Rice with Dal Tadka",40,5).

desert("Ice-cream",20,1).
desert("Malai Sandwich",30,2).
desert("Rasmalai",10,3).

menu(diet,0,0,1).
menu(diet,0,1,0).
menu(diet,1,0,0).
menu(hungry,1,1,1).
menu(not_so_hungry,0,1,1).
menu(not_so_hungry,1,1,0).

hungrySet() :- starter(Str,_,_),
                main_dish(MD,_,_),
				desert(Dst,_,_),
				write("Items : "),
				write(Str),write(" , "),
				write(MD),write(" , "),
				write(Dst).
                            
not_so_hungryWithStrater() :- main_dish(MD,N1,_),
								starter(Str,N2,_),
								80>=N1+N2,
								write("Items : "),
								write(MD),write(" , "),
								write(Str).

not_so_hungryWithDesert() :- main_dish(MD,N1,_),
							desert(Drt,N2,_),
							80>=N1+N2,
							write("Items : "),
							write(MD),write(" , "),
							write(Drt).

printList(L) :- write("Items : "),
				forall(member(X, L) , (write(X),write(", "))),
				nl.

dietWithStarter(X,Limit, Index) :- starter(Str1,N1,Index),
										LimitNew is Limit-N1,
										(( LimitNew >= 0,append(X, [Str1], X1),printList(X1),dietWithStarter(X1,LimitNew,Index));
										(NewIndex is Index + 1,dietWithStarter(X,Limit,NewIndex))).

dietWithMainDish(X,Limit, Index) :- main_dish(Str1,N1,Index),
									LimitNew is Limit-N1,
									(( LimitNew >= 0,append(X, [Str1], X1),printList(X1),dietWithMainDish(X1,LimitNew,Index));
									(NewIndex is Index + 1,dietWithMainDish(X,Limit,NewIndex))).

dietWithDesert(X,Limit, Index) :- desert(Str1,N1,Index),
									LimitNew is Limit-N1,
									(( LimitNew >= 0,append(X, [Str1], X1),printList(X1),dietWithDesert(X1,LimitNew,Index));
									(NewIndex is Index + 1,dietWithDesert(X,Limit,NewIndex))).

find_items(hungry,X,Y,Z) :- menu(hungry,X,Y,Z),
						forall(hungrySet(),nl).

find_items(not_so_hungry,X,Y,Z) :- menu(not_so_hungry,X,Y,Z),
									X == 0,
									forall(not_so_hungryWithDesert(),nl).

find_items(not_so_hungry,X,Y,Z) :- menu(not_so_hungry,X,Y,Z),
									Z == 0,
									forall(not_so_hungryWithStrater(),nl).


find_items(diet,X,Y,Z) :- menu(diet,X,Y,Z),
							X == 1,
							forall(dietWithStarter([],40,1),nl).

find_items(diet,X,Y,Z) :- menu(diet,X,Y,Z),
							Y == 1,
							forall(dietWithMainDish([],40,1),nl).

find_items(diet,X,Y,Z) :- menu(diet,X,Y,Z),
							Z == 1,
							forall(dietWithDesert([],40,1),nl).