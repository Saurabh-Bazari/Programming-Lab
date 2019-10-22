decode_recursive([],1,0).

decode_recursive([H|T],Ans,AnsPerv) :- 
                                not(atom_number(H,Hnumber)),
                                write("There is not only Integer"),nl,
                                Ans is 0,
                                AnsPerv is 0.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                not(atom_number(SubH,SubHnumber)),
                                write("There is not only Integer"),nl,
                                Ans = 0,
                                AnsPerv = 0.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N==0,
                                atom_number(H,Hnumber),
                                Hnumber==0,
                                write("type2"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is 0,
                                AnsPerv is AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N==0,
                                atom_number(H,Hnumber),
                                Hnumber > 0,
                                write("type3"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is 1,
                                AnsPerv is AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                atom_number(SubH,SubHnumber),
                                Hnumber == 0,
                                write("type4"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans = 0,
                                AnsPerv = AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                atom_number(SubH,SubHnumber),
                                Hnumber == 1,
                                write("type5"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is AnsForT + AnsPervForT,
                                AnsPerv = AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                atom_number(SubH,SubHnumber),
                                Hnumber == 2,
                                SubHnumber <7,
                                write("type6"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is AnsForT + AnsPervForT,
                                AnsPerv = AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                atom_number(SubH,SubHnumber),
                                Hnumber == 2,
                                SubHnumber > 6,
                                write("type7"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is AnsForT,
                                AnsPerv = AnsForT.

decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N > 0,
                                atom_number(H,Hnumber),
                                [SubH|SubT] = T,
                                atom_number(SubH,SubHnumber),
                                Hnumber > 2,
                                write("type8"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is AnsForT,
                                AnsPerv = AnsForT.

decode(String):- atom_chars(String, List),
                    decode_recursive(List,Ans,AnsPerv),
                    write("ANS : "),
                    write(Ans),nl.