starter("Corn Tikki",30).
starter("Tomato Soup",20).
starter("Chilli Paneer",40).
starter("Crispy chicken",40).
starter("Papdi Chaat",20).
starter("Cold Drink",20).

main_dish("Kadhai Paneer with Butter / Plain Naan",50).
main_dish("Veg Korma with Butter / Plain Naan",40).
main_dish("Murgh Lababdar with Butter / Plain Naan",50).
main_dish("Veg Dum Biryani with Dal Tadka",50).
main_dish("Steam Rice with Dal Tadka",40).

desert("Ice-cream",20).
desert("Malai Sandwich",30).
desert("Rasmalai",10).

menu(diet,0,0,1).
menu(diet,0,1,0).
menu(diet,1,0,0).
menu(hungry,1,1,1).
menu(not_so_hungry,0,1,1).
menu(not_so_hungry,1,1,0).

hungrySet() :- starter(Str,Ns),
                main_dish(MD,Nm),
                desert(Dst,Nd),
                write([Str,MD,Dst]).

find_items(hungry,X,Y,Z) :- menu(hungry,X,Y,Z),
							forall(hungrySet(),nl).
                            

dietStraterSet(Limit,X) :- starter(S1,N1),
				((Limit >= N1,X = [S1]) ; (starter(S2,N2),Limit>=(N1+N2),X = [S1,S2] )).

find_items(diet,X,Y,Z) :- menu(diet,1,0,0),
							X is 1,Y is 0,Z is 0,
							forall(dietStraterSet(40,X1),(write(X1),nl)).

dietMainDishSet(Limit,X) :- main_dish(Md1,N1),
					Limit >= N1,
					X = [Md1].

find_items(diet,X,Y,Z) :- menu(diet,0,1,0),
								X is 0,Y is 1,Z is 0,
								forall(dietMainDishSet(40,X1),(write(X1),nl)).

dietDesertSet(Limit,X) :- desert(D1,N1),
				((Limit >= N1,X = [D1]) ; (desert(D2,N2),Limit>=(N1+N2),X = [D1,D2]); ( desert(D2,N2),desert(D3,N3),Limit>=(N1+N2+N3),X = [D1,D2,D3]);
					(desert(D2,N2),desert(D3,N3),desert(D4,N4),Limit>=(N1+N2+N3+N4),X = [D1,D2,D3,D4])).

find_items(diet,X,Y,Z) :- menu(diet,0,0,1),
							X is 0,Y is 0,Z is 1,
							forall(dietDesertSet(40,X1),(write(X1),nl)).

                
not_so_hungryWithStrater() :- main_dish(Md1,N1),
								forall(dietStraterSet(80-N1,X),(write(X),write([Md1]),nl)).

find_items(not_so_hungry,X,Y,Z) :- menu(not_so_hungry,1,1,0),
									X is 1,Y is 1,Z is 0,
									forall(not_so_hungryWithStrater(),nl).

not_so_hungryWithDesert() :- main_dish(Md1,N1),
								forall(dietDesertSet(80-N1,X),(write(X),write([Md1]),nl)).

find_items(not_so_hungry,X,Y,Z) :- menu(not_so_hungry,0,1,1),
									X is 0,Y is 1,Z is 1,
									forall(not_so_hungryWithDesert(),nl).