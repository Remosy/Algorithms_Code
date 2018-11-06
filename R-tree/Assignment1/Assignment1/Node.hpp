//
//  Node.hpp
//  Assignment1
//
//  Created by RemosyXu on 22/5/17.
//  Copyright © 2017 RemosyXu. All rights reserved.
//

#ifndef Node_hpp
#define Node_hpp

#include <stdio.h>
#include <vector>
#include "Data.hpp"

using namespace std;

class Node{
public:
    int isLeaf;                        // 0|1
    vector<int> MBR;                   // <min_x,min_y,max_x,max_y>
    vector<Node*> childNodes;           // ChildNodes
    vector<Data> dataPoints;           // Leafpoints
    *Node():Parent(nullptr),isLeaf(0){};// Constructor
    ~Node(){};                         // Destructor
    
    void updateMBR(Node* nd);
    void setMBR(const vector<int> &thisMBR);
    int getPerimeter(const vector<int> &S1_MBR,const vector<int> &S2_MBR);
    void makeMBR(int& min_x, int& min_y, int& max_x, int& max_y, const vector<Node*> &itrnode); //for internal node
    void makeMBR(int& min_x, int& min_y, int& max_x, int& max_y, const vector<Data> &dataPts);  //for leaf node
    vector<int> getMBR(Node node);
    vector<Data> sortList(vector<Data> dataPts, int isX);
    vector<Node*> sortMBR(vector<Node*> itrnode, int isX);
    Node* splitLeaf();
    Node* splitNode();
    void handle_overflow();
    Node* choose_subtree(Data &dataPt);
    void insert_DataPoint(Data &dataPt);
    shared_ptr<Node> getptr();
    Node *Parent;                       //Parent Node
    int getIncs(int& min_x, int& min_y,int& max_x, int& max_y,const Data &data);
    long long getMinst(Data &q);
    
    struct CPnode_X
    {
        inline bool operator() (Node *a, Node *b)
        {
            return (int)a->MBR.at(0)< (int)b->MBR.at(0);
        }
    };
    
    struct CPnode_Y
    {
        inline bool operator() (Node *a, Node *b)
        {
            return (int)a->MBR.at(1)< (int)b->MBR.at(1);
        }
    };


};

#endif /* Node_hpp */
