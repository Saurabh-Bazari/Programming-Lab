module Anagram where

import Data.List 
import System.IO

anagram listOfCards = do
    let n = length listOfCards
    if not(n == 2)
        -- Wrong input 
        then print ("Enter correct Input")
        else do
    -- store all cards in a single list
    let allCards = (listOfCards !! 0) ++ (listOfCards !! 1)
    let numberOfAllCards  = length allCards
    -- Stores all possible subsets of the cards in list  
    let allSubset = [ take (secondIndex-firstIndex+1) (drop firstIndex allCards) | firstIndex <- [0..numberOfAllCards-1] ,secondIndex <- [0..numberOfAllCards-1] , firstIndex <= secondIndex ]
    -- Sort all the string so that anagram becomes same after sorting
    let seqOfSubseq = [ sort x | x <- allSubset ]
    -- make tuple accroding to sorted position
    let subseqWithIndex = zip seqOfSubseq [1..]
    -- stores all pair which are anagrams
    let anagramLength = [ (firstPermuation,secondPermuation) | (firstPermuation,firstIndex) <- subseqWithIndex , (secondPermuation,secondIndex) <- subseqWithIndex , firstIndex < secondIndex , firstPermuation == secondPermuation ]
    print (length anagramLength)