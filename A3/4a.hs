module Sudoku where

import Data.List
import Control.Monad (msum)
import qualified Data.Char

-- Return the nth row of sudoku grid
rowList grid (n,_) = grid !! n

--Returhs the nth column of sudoku grid
-- For every row of grid pick the nth element
colList grid (_,n) = [ row !! n | row <- grid]

-- return the grid element for row and col
blockList grid (row,col) = do
                        let rowIndex = (row `div` 2)*2
                        let columnIndex = (col `div` 2)*2
                        let ans = take 2(drop rowIndex grid)
                        concatMap (take 2 . drop columnIndex) ans

-- set value at i,j position in grid
--first remove the upper i layers
-- Remove first j colums from ith row
updateGrid :: [[Int]] -> (Int, Int) -> Int -> [[Int]]
updateGrid grid (row, col) newVal = do
    let upperLayer = take row grid
    let currentLayer = (grid !! row)
    let currentUpdatedLayer = ( take col currentLayer ) ++ [newVal] ++ ( drop (col+1) currentLayer)
    let finalGrid = upperLayer ++ [currentUpdatedLayer] ++ (drop (row + 1) grid)
    finalGrid
        
checkForNothing :: [Maybe a] -> Maybe a
checkForNothing = msum

-- returs all possible value for i,j position
possibleValues grid (row,col) = [i | i <- [1..4] , not (i `elem` ( (rowList grid (row,col)) ++ (colList grid (row,col)) ) ) , not (i `elem` blockList grid (row,col) ) ]

-- solve :: [[Int]] -> Maybe [[Int]]
--solve the grid using backtracking
solve grid = do
    let emptyBlocks = [ (i,j) | i <- [0..3] , j <- [0..3] , ((grid !! i) !! j) == 0  ]
    if emptyBlocks == []
        then return grid
        else do
            let validValueList = possibleValues grid ( head emptyBlocks)
            checkForNothing $ map (solve . updateGrid grid ( head emptyBlocks)) validValueList

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
            let rowlist = [Data.Char.digitToInt $ character | character<-takeInput1 , character >= '0' , character <= '4']
            if not((length rowlist)==4)
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
    let x = [ checkCell (i, j) grid | i <- [0..3] , j <- [0..3] ]
    not (False `elem` x ) 

-- Read the input in form of grid
-- solve the input sudoku
sudoku = do
    sudokuGrid <- takeInput 4
    if not (((length sudokuGrid) == 4) && (checkInput sudokuGrid) )
        then putStrLn "Incorrect Input"
        else do
            case solve sudokuGrid of
                Nothing   -> putStrLn "No Answer"
                Just grid -> printFunc grid
    
    
{-

1002
0003
0400
0000

-}