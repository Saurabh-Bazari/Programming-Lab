module Sudoku where

import Data.List
import Control.Monad (msum)
import qualified Data.Char

-- Return the nth row of sudoku grid
rowList grid (x_cor,y_cor) = grid !! x_cor

--Returhs the nth column of sudoku grid
-- For every row of grid pick the nth element
colList grid (x_cor,y_cor) = [ rowlist !! y_cor | rowlist <- grid]

-- return the grid element for row and col
blockList grid (x_cor,y_cor) = do
                        let rowIndex = (x_cor `div` 3)*3
                        let colIndex = (y_cor `div` 3)*3
                        let blockIn2D = take 3(drop rowIndex grid)
                        concatMap (take 3 . drop colIndex) blockIn2D

-- set value at i,j position in grid
--first remove the upper i layers
-- Remove first j colums from ith row
updateGrid grid (x_cor, y_cor) newValue = do
    let upperLayer = take x_cor grid
    let currentLayer = (grid !! x_cor)
    let currentUpdatedLayer = ( take y_cor currentLayer ) ++ [newValue] ++ ( drop (y_cor+1) currentLayer)
    let finalGrid = upperLayer ++ [currentUpdatedLayer] ++ (drop (x_cor + 1) grid)
    finalGrid
        
checkForNothing :: [Maybe typeOf] -> Maybe typeOf
checkForNothing = msum

-- returs all possible value for i,j position
possibleValues grid (x_cor,y_cor) = [value | value <- [1..9] , not (value `elem` ( (rowList grid (x_cor,y_cor)) ++ (colList grid (x_cor,y_cor)) ) ) , not (value `elem` blockList grid (x_cor,y_cor) ) ]

-- solve :: [[Int]] -> Maybe [[Int]]
--solve the grid using backtracking
-- solve :: [[Int]] -> Maybe [[Int]]
solve grid = do
    let emptyBlocks = [ (x_cor,y_cor) | x_cor <- [0..8] , y_cor <- [0..8] , ((grid !! x_cor) !! y_cor) == 0  ]
    if emptyBlocks == []
        then return grid
        else do
            let possibleValuesList = possibleValues grid ( head emptyBlocks)
            checkForNothing $ map (solve . updateGrid grid ( head emptyBlocks)) possibleValuesList

-- Print list recursively           
printFunc [] = print "END"
printFunc (head:tail) = do 
    print head
    printFunc tail

--Get input 2 d matrix
-- Take input line by line
takeInput index = do
    if index == 0
        then return []
        else do
            takeInput1 <- getLine
            let rowlist = [Data.Char.digitToInt $ character | character<-takeInput1 , character >= '0' , character <= '9']
            if not((length rowlist)==9)
                then do
                    return []
                else do
                    restList <- takeInput (index-1)
                    return (rowlist:restList)

-- check that given cell is correct or not
checkCell (i, j) grid = do
    let currValue = (grid !! i) !! j
    if currValue == 0
        then True
        else do
            let newGrid = updateGrid grid (i,j) 0
            let possibleNow = possibleValues newGrid (i,j)
            currValue `elem` possibleNow

-- check that given grid is correct or not by checking all cells
checkInput grid = do
    let x = [ checkCell (i, j) grid | i <- [0..8] , j <- [0..8] ]
    not (False `elem` x ) 

-- Read the input in form of grid
-- solve the input sudoku
sudoku = do
    input <- takeInput 9
    if not (((length input) == 9) && (checkInput input) )
        then putStrLn "Incorrect Input"
        else do
            case solve input of
                Nothing   -> putStrLn "No Answer"
                Just grid -> printFunc grid
   
  {-  

100456789
450789103
789123450
230567801
567090234
091034500
040078912
678010305
010045678

[[1,2,3,4,5,6,7,8,9],[4,5,6,7,8,9,1,2,3],[7,8,9,1,2,3,4,5,6],[2,3,4,5,6,7,8,9,1],[5,6,7,8,9,1,2,3,4],[8,9,1,2,3,4,5,6,7],[3,4,5,6,7,8,9,1,2],[6,7,8,9,1,2,3,4,5],[9,1,2,3,4,5,6,7,8]]

-}