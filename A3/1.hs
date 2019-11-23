import Data.List
import System.IO

-- function which returns product of sum of all list
m :: [[Integer]] -> Integer
m myList 
    |   length myList >0 =product [sum subList | subList <- myList ]
    |   otherwise = 0
-- if list is empty print that and return nothing

-- this function returs the element of seq which maximises the function f
greatest :: (typeOfList -> Int) -> [typeOfList] -> typeOfList
greatest f [] = error "Empty List"
greatest f seq = head [ element | element <- seq ,  (f element) >= maxValue ]
                    where maxValue = maximum [f eLement | eLement <- seq ] 

-- Custom List Data type definition
data List typeOfList = Empty | Cons typeOfList (List typeOfList) deriving (Show)

-- converts Haskell list to custom List
toList :: [typeOfList] -> List typeOfList
toList [] = Empty
toList (headOfList:restOfList) = ( Cons headOfList  ( toList restOfList)  )

-- converts Custom List to Haskell List
toHaskellList :: List typeOfList -> [typeOfList]
toHaskellList Empty = []
toHaskellList (Cons headOfList restOfList) = headOfList : toHaskellList(restOfList)