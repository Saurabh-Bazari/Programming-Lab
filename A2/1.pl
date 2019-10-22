decode_recursive([],1,0).
decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N==0,
                                atom_number(H,Hnumber),
                                Hnumber==0,
                                write("type 1"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is 0,
                                AnsPerv is AnsForT.
decode_recursive([H|T],Ans,AnsPerv) :- 
                                length(T,N),
                                N==0,
                                atom_number(H,Hnumber),
                                Hnumber > 0,
                                write("type 2"),nl,
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
                                write("tyListpe 3"),nl,
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
                                write("type 4"),nl,
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
                                write("type 5"),nl,
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
                                write("type 6"),nl,
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
                                write("type 7"),nl,
                                decode_recursive(T,AnsForT,AnsPervForT),
                                Ans is AnsForT,
                                AnsPerv = AnsForT.

decode(String):- atom_chars(String, List),
                    decode_recursive(List,Ans,AnsPerv),
                    write("ANS : "),
                    write(Ans),nl.