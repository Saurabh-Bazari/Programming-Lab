module Main where

import System.Random
import System.IO
import Data.List (sortBy)
import Control.Monad (replicateM)
import Data.Function (on)

-- list of all teams
teamList :: [[Char]]
teamList = ["BS","CM","CH","CV","CS","DS","EE","HU","MA","ME","PH","ST"]

-- list of all timings 
timings :: [[Char]]
timings = ["1-11 9:30 AM","1-11 7:30 PM","2-11 9:30 AM","2-11 7:30 PM","3-11 9:30 AM","3-11 7:30 PM"]

-- generates 12 random number
randonListNumber = do 
    randomNumbers <- replicateM (12)  (randomRIO (1 :: Int, 100000))
    return randomNumbers
-- shuffle the given list of teams
shuffle teams = do
    randomNumbers <- randonListNumber
    let teamTuple = zip teams randomNumbers
    let sortedTuple = sortBy( compare `on` snd )teamTuple
    let shuffledTeam = [i | (i,j) <- sortedTuple]
    return shuffledTeam
-- write to file
writeToFile shuffledArray = writeFile "team.txt" (unlines shuffledArray)
-- generate a shuffled list of teams and write it to file
main = do
    shuffledArray <- shuffle teamList
    writeToFile shuffledArray
    print ("Shuffle Done !!")
-- read from file
readFile1 = do
    content <- readFile "team.txt"
    let linesOfFiles = lines content
    return linesOfFiles
-- 
printMatch a b = putStrLn ((a!!0) ++ " vs " ++ (a!!1) ++ " " ++  (b !! 0))
-- print the full schedule for matches
printAll teams timings = do
    let n = length timings
    if n==0
        then print "END"
    else do
        printMatch (take 2 teams) (take 1 timings)
        printAll (drop 2 teams) (drop 1 timings)

-- print schedule for a specific team

printTeamWithTeam s teams timings = do
    let n = length timings
    if n==0
        then print "Give correct team"
    else do
        let firstTwoTeams = take 2 teams
        if (s == (firstTwoTeams !! 0)) || (s == (firstTwoTeams !! 1))
            then    printMatch firstTwoTeams (take 1 timings)
            else printTeamWithTeam s (drop 2 teams) (drop 1 timings)

fixtures conditionTeam = do
    teams <- readFile1
    if conditionTeam == "all" 
        then printAll teams timings
        else printTeamWithTeam conditionTeam teams timings

-- Print team with given time
-- basiclly check current time with given time
-- and print match
        
printTeamWithTime timeGiven teamListFromFile timings = do
    if (length timings) ==0
        then print "All Match are finish"
    else do
        let currentTime = timings !! 0
        if (timeGiven == currentTime)
            then    printMatch (take 2 teamListFromFile) (take 1 timings)
            else printTeamWithTime timeGiven (drop 2 teamListFromFile) (drop 1 timings)

-- first read team list from file
-- check correct date and Time
-- check current date and time compare with all
-- current fixed timings.
 
nextMatch dateGiven timeGiven = do
    teamListFromFile <- readFile1
    let encodeDateAndTime = ((dateGiven*100) + (timeGiven))
    let hour = floor(timeGiven)
    if (hour < 0 || hour > 23 || dateGiven <= 0 || dateGiven > 30)
        then print "Enter Correct Date and Time"
    else do
    if encodeDateAndTime <= 109.50
        then printTeamWithTime (timings !! 0) teamListFromFile timings
        else if encodeDateAndTime <= 119.50
            then printTeamWithTime (timings !! 1) teamListFromFile timings
            else if encodeDateAndTime <= 209.50
                then printTeamWithTime (timings !! 2) teamListFromFile timings
                else if encodeDateAndTime <= 219.50
                    then printTeamWithTime (timings !! 3) teamListFromFile timings
                    else if encodeDateAndTime <= 309.50
                        then printTeamWithTime (timings !! 4) teamListFromFile timings
                        else if encodeDateAndTime <= 319.50
                            then printTeamWithTime (timings !! 5) teamListFromFile timings
                            else print "All Match are finished"