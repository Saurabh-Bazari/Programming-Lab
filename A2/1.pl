% when null string only one possible way to exist 
% send nothing
decodeRecursive([],1,0).

% when only one in list then its depend only it is positive or zero
% if zero then ans = 0, else 1.
% and subAnswer is from upper base case.
decodeRecursive([Head],Answer,SubAnswer) :- 
                                Head=0,
                                Answer is 0,
                                SubAnswer is 1.

decodeRecursive([Head],Answer,SubAnswer) :- 
                                Head > 0,
                                Answer is 1,
                                SubAnswer is 1.

% when more than one int in list then its depend only it is first 2 integer
% if int from 1 to 26 then and sub answer of subproblem
% if only forst int is positive then also add andwer of sub problem
% else 0.
decodeRecursive([Head1,Head2|Tail],Answer,SubAnswer) :-
                                Head1 = 0,
                                decodeRecursive( [Head2 | Tail] ,AnswerTail,_),
                                Answer is 0,
                                SubAnswer is AnswerTail.

%when first int is 0.
decodeRecursive([Head1,Head2|Tail],Answer,SubAnswer) :- 
                                Head1 = 1,
                                decodeRecursive([Head2 | Tail],AnswerTail,SubAnswerTail),
                                Answer is AnswerTail + SubAnswerTail,
                                SubAnswer is AnswerTail.

%when first int and second int less then 27 and first int is 2.
decodeRecursive([Head1,Head2|Tail],Answer,SubAnswer) :- 
                                Head1 = 2,
                                Head2 <7,
                                decodeRecursive([Head2|Tail],AnswerTail,SubAnswerTail),
                                Answer is AnswerTail + SubAnswerTail,
                                SubAnswer is AnswerTail.

%when first int and second int more then 26 and first int is 2.
decodeRecursive([Head1,Head2|Tail],Answer,SubAnswer) :- 
                                Head1 = 2,
                                Head2 > 6,
                                decodeRecursive([Head2|Tail],AnswerTail,_),
                                Answer is AnswerTail,
                                SubAnswer is AnswerTail.

%when first int is more than 2.
decodeRecursive([Head1,Head2|Tail],Answer,SubAnswer) :- 
                                Head1 > 2,
                                decodeRecursive([Head2|Tail],AnswerTail,_),
                                Answer is AnswerTail,
                                SubAnswer is AnswerTail.

% its take a list of char and return same char converted into integer
% if any other other non Integer char then its fail.
convertCharToIntegerList([],[]).

convertCharToIntegerList([HeadChar | TailChar],[HeadInt | TailInt]) :- 
                            atom_number(HeadChar,TempHead),
                            HeadInt is TempHead,
                            convertCharToIntegerList(TailChar,TempTail),
                            TailInt = TempTail.

%decode take a string and print the number of possible way to decode into int
% if not possible to decode due to some mal char then answer is 0 and print msg.
decode(InputString):- atom_chars(InputString, InputCharList),
                        convertCharToIntegerList(InputCharList,InputIntegerList),
                        decodeRecursive(InputIntegerList,Answer,_),
                        write("Number of possible Strings : "),
                        write(Answer),nl.

decode(InputString):- atom_chars(InputString, InputList1),
                        not(convertCharToIntegerList(InputList1,_)),
                        write("There is some non positive Integer"),nl,
                        write("Number of possible Strings : 0"),nl.