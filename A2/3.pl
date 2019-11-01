% Path with cost
neighbour(g1,g5,4).
neighbour(g2,g5,6).
neighbour(g3,g5,8). 
neighbour(g4,g5,9). 
neighbour(g1,g6,10). 
neighbour(g2,g6,9). 
neighbour(g3,g6,3). 
neighbour(g4,g6,5). 
neighbour(g5,g7,3). 
neighbour(g5,g10,4). 
neighbour(g5,g11,6). 
neighbour(g5,g12,7). 
neighbour(g5,g6,7). 
neighbour(g5,g8,9). 
neighbour(g6,g8,2). 
neighbour(g6,g12,3). 
neighbour(g6,g11,5). 
neighbour(g6,g10,9). 
neighbour(g6,g7,10). 
neighbour(g7,g10,2). 
neighbour(g7,g11,5). 
neighbour(g7,g12,7). 
neighbour(g7,g8,10). 
neighbour(g8,g9,3). 
neighbour(g8,g12,3). 
neighbour(g8,g11,4). 
neighbour(g8,g10,8). 
neighbour(g10,g15,5). 
neighbour(g10,g11,2). 
neighbour(g10,g12,5). 
neighbour(g11,g15,4). 
neighbour(g11,g13,5). 
neighbour(g11,g12,4). 
neighbour(g12,g13,7). 
neighbour(g12,g14,8). 
neighbour(g15,g13,3). 
neighbour(g13,g14,4). 
neighbour(g14,g17,5). 
neighbour(g14,g18,4).
neighbour(g17,g18,8).

%write to file
write_to_file(Text) :- open("path.txt",append,Stream),
						write(Stream,Text),
						close(Stream).

% stating points for jail escape
start_node(g1).
start_node(g2).
start_node(g3).
start_node(g4).

% valid Path must end with g17
checkValidPath([g17],0) .

checkValidPath([Head,Head|Tail],NewCost) :- checkValidPath([Head|Tail],NewCost1),
                                                NewCost = NewCost1.    

checkValidPath([Head1,Head2|Tail],NewCost) :- neighbour(Head1,Head2,Cost),
                                checkValidPath([Head2|Tail],NewCost1),
                                NewCost2 is NewCost1+Cost,
                            NewCost = NewCost2. 

% for every pair there must exists a Path 
% check recursively for rest of the list
checkValidPath([Head1,Head2|Tail],NewCost) :- neighbour(Head2,Head1,Cost),
                            checkValidPath([Head2|Tail],NewCost1),
                            NewCost2 is NewCost1+Cost,
                            NewCost = NewCost2. 

% every valid Path must start from g1,g2,g3,g4
valid([Head|Tail]) :- member(Head, [g1,g2,g3,g4]),
                    checkValidPath([Head|Tail],NewCost1),
                    write(NewCost1).

% If list is empty stop printing using cut
printList([]):-write_to_file("\n"),
    		nl,
   			!.

% recursively print the first element of list and repeat for tail untill empty
printList([Head|Tail]):- format('~w ',Head),
					write_to_file(Head),
					write_to_file(" "),
    				printList(Tail).

% when g17 is reached print the Path
compute_path(Head,g17):- printList(Head),
				!.

% Head stores the Path computed till now ending with CurrentNode
% find all neighbours of CurrentNode and add to Path  
compute_path(PathList,CurrentNode) :-

    neighbour(CurrentNode,AdjNode,_),
    append(PathList,[AdjNode],NewPath),
    compute_path(NewPath,AdjNode).

compute_path(PathList,CurrentNode) :-
    
    neighbour(AdjNode,CurrentNode,_),
    append(PathList,[AdjNode],NewPath),
    compute_path(NewPath,AdjNode).


% generate all paths with cyclic_term
% Path can start with any start_node value i.e. g1,g2,g3,g4
all_path_with_cycle() :- start_node(StartingNode),
            compute_path([StartingNode],StartingNode).

% computes path cost and also return the path in list Path
find_valid_path_with_cost(Head,g17,CurCost,NewCost,Path):-
    NewCost is CurCost,
    Path = Head,
    !.

% for every neighboure of CurrentNode if it is not already in list(prevent cycle) add it to path
find_valid_path_with_cost(CurrentList,CurrentNode,CurCost,NewCost,Path):-
    neighbour(CurrentNode,AdjNode,Cost),
    not(member(AdjNode,CurrentList)),
    append(CurrentList,[AdjNode],NewList),
    Temp is Cost + CurCost,
    find_valid_path_with_cost(NewList,AdjNode,Temp,SubNewCost,NewPath),
    NewCost is SubNewCost,
    Path = NewPath.

find_valid_path_with_cost(CurrentList,CurrentNode,CurCost,NewCost,Path):-
    neighbour(AdjNode,CurrentNode,Cost),
    not(member(AdjNode,CurrentList)),
    append(CurrentList,[AdjNode],NewList),
    Temp is Cost + CurCost,
    find_valid_path_with_cost(NewList,AdjNode,Temp,SubNewCost,NewPath),
    NewCost is SubNewCost,
    Path = NewPath.

% find path starting with Node with Cost
dfs(Node,NewCost,Path):-
    CurCost is 0,
    find_valid_path_with_cost([Node],Node,CurCost,SubNewCost,SubPath),
    NewCost is SubNewCost,
    Path = SubPath.
 
% iterate over all valid paths and find the minimum cost among them
optimalPath() :-
    aggregate_all(min(NewCost,Path), (start_node(StartingNode),dfs(StartingNode,NewCost,Path)) , min(MinCost,Result)  ),
    format("OPTIMAL PATH COST: ~w",[MinCost]),
    nl,
    write('Optimal Path: '),
    printList(Result),
    nl.

% recursively calls dfs for g1,g2,g3,g4 and prints the path computed by dfs.
all_path_without_cycle() :- 
	forall((start_node(StartingNode),dfs(StartingNode,_,Path)) , printList(Path) ),
    aggregate_all(count,(start_node(StartingNode),dfs(StartingNode,_,Path)),Length),
    format('Total Number of Paths: ~w',[Length]).

