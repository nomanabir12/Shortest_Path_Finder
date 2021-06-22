# JavaFX---Find-Shortest-Path-Application
JavaFX---Find-Shortest-Path-Application Source Code

* **********************************************************************************************
*		Author:		MonkeySR1
*		Date:		Feb 2015, 24th
* **********************************************************************************************

An image can be found under Issues.

Description:

Once the application is compiled and started, click 'generate new graph' to create a new random graph. 
Enter a start and a destination node in capital letters in the fields below and  click 'submit'. 
If more than 1 letter is entered, only the first letter will be read.
 
On submit, the shortest path will be  calculated and displayed in the textbox.


To the implementation:

Please note that the Dijkstra implementation is adapted from the German Wikipedia Dijkstra pseudo-code: 
http://de.wikipedia.org/wiki/Dijkstra-Algorithmus 
under consideration of http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html 


The DijkstraApp is the Graph main class to start the application.

The class Edge is used to create a EdgeObject for the Graph.

The class Node is used to create a NodeObject for the Graph.

The Graph Controller initializes random vertices and edges including their name and weight representation and displays 
the whole as a graph and controls the user input (number of vertices, edges and their weight can be manipulated here).
If the Input was successful, the shortest path will be calculated via the Dijkstra class and displayed as text.

The class Dijkstra is the actual Dijkstra implementation in which a adjacency-list of predecessors with the smallest 
edge-weight starting with a given start node gets created and the shortest path according to a given destination node 
will be created. 




