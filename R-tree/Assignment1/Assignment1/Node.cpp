//
//  Node.cpp
//  Assignment1
//
//  Created by RemosyXu on 22/5/17.
//  Copyright © 2017 RemosyXu. All rights reserved.
//

#include "Node.hpp"
#include "Data.hpp"
#include <iostream> 
#include <math.h>


#define B 20

void Node::updateMBR(Node* node){
    //cout << "___________________Update MBR________________________" << endl;
    node->MBR = {0,0,0,0};
    if(node->dataPoints.size()==0){
        vector<Node*> &child = node->childNodes;
        makeMBR(node->MBR.at(0), node->MBR.at(1),node->MBR.at(2), node->MBR.at(3), child);
    }else{
        vector<Data> &child = node->dataPoints;
        makeMBR(node->MBR.at(0), node->MBR.at(1),node->MBR.at(2), node->MBR.at(3), child);
    }
}

void Node::makeMBR(int& min_x, int& min_y,int& max_x, int& max_y,const vector<Data> &dataPts){
    min_x = numeric_limits<int>::max();
    max_x = numeric_limits<int>::min();
    min_y = numeric_limits<int>::max();
    max_y = numeric_limits<int>::min();
    
    //MIN x , MIN y,  MAX x, MAX y
    for(Data data : dataPts){
        //cout << data.X << "/" << data.Y <<endl;
        min_x = std::min(data.X, min_x);
        max_x = std::max(data.X, max_x);
        min_y = std::min(data.Y, min_y);
        max_y = std::max(data.Y, max_y);
    }
    
    if(dataPts.size()==1){
        min_x = 0;
        max_x = dataPts.at(0).X;
        min_y = 0;
        max_y = dataPts.at(0).Y;
    }
    
}

void Node::makeMBR(int& min_x, int& min_y,int& max_x, int& max_y, const vector<Node*> &itrnode){
   
    min_x = numeric_limits<int>::max();
    max_x = numeric_limits<int>::min();
    min_y = numeric_limits<int>::max();
    max_y = numeric_limits<int>::min();
    
    //MIN x , MIN y,  MAX x, MAX y
    for(Node *nd : itrnode){
        /*cout << nd->MBR.at(0) << "/" << nd->MBR.at(1) << "/"
        << nd->MBR.at(2)<< "/" << nd->MBR.at(3)<<endl;*/
        min_x = std::min(nd->MBR.at(0), min_x);
        max_x = std::max(nd->MBR.at(2), max_x);
        min_y = std::min(nd->MBR.at(1), min_y);
        max_y = std::max(nd->MBR.at(3), max_y);
    }

}

void Node::setMBR(const vector<int> &thisMBR){
    MBR.at(0)= thisMBR.at(0);
    MBR.at(1)= thisMBR.at(1);
    MBR.at(2)= thisMBR.at(2);
    MBR.at(3)= thisMBR.at(3);
}

vector<int> Node::getMBR(Node node){
    return node.MBR;
}

int Node::getIncs(int &min_x, int &min_y,int& max_x, int& max_y,const Data &data){
    //Old: min_x, min_y,max_x,max_y
    int oldPerimeter = max_x - min_x+max_y - min_y;
    
    //MIN x , MIN y,  MAX x, MAX y
    /*cout << data.X << "/" << data.Y <<endl;*/
    min_x = std::min(data.X, min_x);
    max_x = std::max(data.X, max_x);
    min_y = std::min(data.Y, min_y);
    max_y = std::max(data.Y, max_y);
    
    int AfterPerimeter = max_x - min_x + max_y - min_y;
    return AfterPerimeter - oldPerimeter;
}

int Node::getPerimeter(const vector<int> &S1_MBR,const vector<int> &S2_MBR){
    int perimeter_sum;
    
    perimeter_sum = S1_MBR.at(2) - S1_MBR.at(0)+ S1_MBR.at(3)-S1_MBR.at(1);
    perimeter_sum += S2_MBR.at(2) - S2_MBR.at(0)+ S2_MBR.at(3)-S2_MBR.at(1);
    
    //cout << "[P_sum]=" << perimeter_sum<< endl;
    return perimeter_sum;
}

vector<Data> Node::sortList(vector<Data> dataPts, int isX){
    vector<Data> list = dataPts;
    if(isX == 1){
        sort(list.begin(), list.end(),[](Data &a, Data &b) {
             return a.X < b.X;
         });
    }else{
        sort(list.begin(), list.end(),[](Data &a, Data &b) {
            return a.Y < b.Y;
        });
    }
    return list;
}

vector<Node*> Node::sortMBR(vector<Node*> itnNode, int isX){
    vector<Node*> list = itnNode;
    if(isX == 1){
        sort(list.begin(), list.end(), CPnode_X());
    }else{
        sort(list.begin(), list.end(), CPnode_Y());
    }
    return list;
}

Node* Node::splitLeaf(){
    int index = ceil(0.4*B);
    int best_perimeter = INT_MAX;
    vector<int> best_S1_MBR;
    vector<int> best_S2_MBR;
    vector<Data> best_s1;
    vector<Data> best_s2;
    
    vector<vector<int>> bestMBRs;
    vector<Node> bestSplit_leaf; // Save split Node
    // Init:S2
    Node *node_s2 = new Node();
    node_s2->isLeaf=1;
    int m = (int)dataPoints.size();
    
    //Sort on X
    vector<Data> list_x = sortList(this->dataPoints, 1);
    /*cout << "--[SORT BY X]--" << endl;
    for(Data d : list_x){
        cout << d.ID << "-->";
    }
    cout << endl;*/
    //Sort on Y
    vector<Data> list_y = sortList(this->dataPoints, 0);
    /*cout << "--[SORT BY Y]--" << endl;
    for(Data d : list_y){
        cout << d.ID << "-->";
    }
    cout << endl;*/
    
    //Calculate MBR for X_sort
    for(int i = index; i <= m - index; i++){
        vector<Data> s1;
        vector<Data> s2;
        //s1 <- the set of the first i points in the "list_x" vector
        for(int j = 0; j < i; j++){
            s1.push_back(list_x.at(j));
        }
        //s2 <- the set of the other i points in the "list_x" vector
        for(int k = i;k<m;k++){
            s2.push_back(list_x.at(k));
        }
        vector<int> S1_MBR = {0,0,0,0};
        vector<int> S2_MBR = {0,0,0,0};
        makeMBR(S1_MBR.at(0),S1_MBR.at(1),S1_MBR.at(2),S1_MBR.at(3),s1); // MIN_X, MIN_Y, MAX_X, MAX_Y
        makeMBR(S2_MBR.at(0),S2_MBR.at(1),S2_MBR.at(2),S2_MBR.at(3),s2); // MIN_X, MIN_Y, MAX_X, MAX_Y
        int perimeter = getPerimeter(S1_MBR,S2_MBR);
        //cout << "[Best_Permeter]="<< best_perimeter << "[permeter]="<< perimeter << endl;
        //Compare MBR
        if(best_perimeter >= perimeter){
            best_perimeter = perimeter;
            best_s1 = s1;
            best_s2 = s2;
            best_S1_MBR = S1_MBR;
            best_S2_MBR = S2_MBR;
        }
    }
    //cout << "[Best_Permeter_X]="<< best_perimeter << endl;
    
   
    //Calculate MBR for Y_sort
    for(int i = index; i <= m - index; i++){
        
        vector<Data> s1;
        vector<Data> s2;
        //s1 <- the set of the first i points in the "list_x" vector
        for(int j = 0; j < i; j++){
            s1.push_back(list_y.at(j));
        }
        //s2 <- the set of the other i points in the "list_x" vector
        for(int k = i;k<m;k++){
            s2.push_back(list_y.at(k));
        }
        vector<int> S1_MBR = {0,0,0,0};
        vector<int> S2_MBR = {0,0,0,0};
        makeMBR(S1_MBR.at(0),S1_MBR.at(1),S1_MBR.at(2),S1_MBR.at(3),s1); // MIN_X, MIN_Y, MAX_X, MAX_Y
        makeMBR(S2_MBR.at(0),S2_MBR.at(1),S2_MBR.at(2),S2_MBR.at(3),s2); // MIN_X, MIN_Y, MAX_X, MAX_Y
        int perimeter = getPerimeter(S1_MBR,S2_MBR);
        //cout << "[Best_Permeter]="<< best_perimeter << "[New_permeter]="<< perimeter << endl;
        //Compare MBR
        if(best_perimeter>perimeter){
            best_perimeter = perimeter;
            best_s1 = s1;
            best_s2 = s2;
            best_S1_MBR = S1_MBR;
            best_S2_MBR = S2_MBR;

        }
    }
    //cout << "[Best_Permeter_Y]="<< best_perimeter << endl;
    
    this->MBR = best_S1_MBR;
    node_s2->MBR = best_S2_MBR;
    this->dataPoints = best_s1;
    node_s2->dataPoints = best_s2;
   
    return node_s2;
}


Node* Node::splitNode(){
    int index = ceil(0.4*B);
    int best_perimeter = INT_MAX;
    vector<int> best_S1_MBR;
    vector<int> best_S2_MBR;
    vector<Node*> best_s1;
    vector<Node*> best_s2;
    
    vector<vector<int>> bestMBRs;
    vector<Node*> bestSplit_leaf; // Save split Node
    // Init:S2
    Node *node_s2 = new Node(); //New Internal Node
    int m = (int)this->childNodes.size();
    
    //Sort on X
    vector<Node*> list_x = sortMBR(this->childNodes, 1);
    /*cout << "--[SORT BY X]--" << endl;
    for(Node *d: list_x){
        cout<< "min_X=" << d->MBR.at(0) << " min_Y=" <<  d->MBR.at(1)
        << " max_X=" <<  d->MBR.at(2) << " max_Y=" <<  d->MBR.at(3) << endl;
    }
    cout << endl;*/
    //Sort on Y
    vector<Node*> list_y = sortMBR(this->childNodes, 0);
    /*cout << "--[SORT BY Y]--" << endl;
    for(Node *d: list_y){
        cout<< "min_X=" << d->MBR.at(0) << " min_Y=" <<  d->MBR.at(1)
        << " max_X=" <<  d->MBR.at(2) << " max_Y=" <<  d->MBR.at(3) << endl;
    }
    cout << endl;*/
    
    //Calculate MBR for X_sort
    //cout << "index=" << index << endl;
    for(int i = index; i <= m - index; i++){
        //cout << "i=" <<i << endl;
        vector<Node*> s1;
        vector<Node*> s2;
        
        for(int j = 0; j < i; j++){
            s1.push_back(list_x.at(j));
        }
        
        for(int k = i;k<m;k++){
            s2.push_back(list_x.at(k));
        }
        
        vector<int> S1_MBR = {0,0,0,0};
        vector<int> S2_MBR = {0,0,0,0};
        makeMBR(S1_MBR.at(0),S1_MBR.at(1),S1_MBR.at(2),S1_MBR.at(3),s1); // MIN_X, MIN_Y, MAX_X, MAX_Y
        makeMBR(S2_MBR.at(0),S2_MBR.at(1),S2_MBR.at(2),S2_MBR.at(3),s2); // MIN_X, MIN_Y, MAX_X, MAX_Y
        int perimeter = getPerimeter(S1_MBR,S2_MBR);
        //cout << "[Best_Permeter]="<< best_perimeter << "[New_permeter]="<< perimeter << endl;
        //Compare MBR
        if(best_perimeter>=perimeter){
            best_perimeter = perimeter;
            best_s1 = s1;
            best_s2 = s2;
            best_S1_MBR = S1_MBR;
            best_S2_MBR = S2_MBR;
        }
    }
    //cout << "[Best_Permeter_X]="<< best_perimeter << endl;
    
    
    //Calculate MBR for Y_sort
    //cout << "index=" << index << endl;
    for(int i = index; i <= m - index; i++){
        //cout << "i=" <<i << endl;
        vector<Node*> s1;
        vector<Node*> s2;
        //s1 <- the set of the first i points in the "list_y" vector
        for(int j = 0; j < i; j++){
            s1.push_back(list_y.at(j));
        }
        //s2 <- the set of the other i points in the "list_y" vector
        for(int k = i;k<m;k++){
            s2.push_back(list_y.at(k));
        }
        vector<int> S1_MBR = {0,0,0,0};
        vector<int> S2_MBR = {0,0,0,0};
        makeMBR(S1_MBR.at(0),S1_MBR.at(1),S1_MBR.at(2),S1_MBR.at(3),s1);
        makeMBR(S2_MBR.at(0),S2_MBR.at(1),S2_MBR.at(2),S2_MBR.at(3),s2);
        int perimeter = getPerimeter(S1_MBR,S2_MBR);
        S1_MBR.shrink_to_fit();
        S2_MBR.shrink_to_fit();
        //cout << "[Best_Permeter]="<< best_perimeter << "[New_permeter]="<< perimeter << endl;
        //Compare MBR
        if(best_perimeter>=perimeter){
            best_perimeter = perimeter;
            best_s1 = s1;
            best_s2 = s2;
            best_S1_MBR = S1_MBR;
            best_S2_MBR = S2_MBR;
        }
    }
    //cout << "[Best_Permeter_Y]="<< best_perimeter << endl;
    
    this->MBR = best_S1_MBR;
    node_s2->MBR = best_S2_MBR;
    this->childNodes = best_s1;
    node_s2->childNodes = best_s2;
    
    //Update parent for splited nodes
    for(Node *node: node_s2->childNodes){
        node->Parent = node_s2;
    }
    /*cout << "[S1 Data]";
    cout<< "min_X" << this->MBR.at(0) << " min_Y" <<  this->MBR.at(1)
    << " max_X" <<  this->MBR.at(2) << " max_Y" <<  this->MBR.at(3) << endl;
    cout << endl;
    
    cout << "[S2 Data]";
    cout<< "min_X" << node_s2->MBR.at(0) << " min_Y" <<  node_s2->MBR.at(1)
    << " max_X" <<  node_s2->MBR.at(2) << " max_Y" <<  node_s2->MBR.at(3) << endl;
    cout << endl;*/
    return node_s2;
}

void Node::handle_overflow(){
    Node *u2  = nullptr;
    if((int)this->childNodes.size()>0){ //If Node
        u2 = splitNode();
        if(this->Parent!=nullptr){
            //cout<<"Node Hand_Over flow with Exist Parent"<< endl;
            u2->Parent = this->Parent;
            this->Parent->childNodes.push_back(u2);
            if((int)u2->Parent->childNodes.size()>B){
                //cout << "[W's ChildNodes Size]=" << u2->Parent->childNodes.size() << endl;
                u2->Parent->handle_overflow();
            }
        }else{
            //cout << "Node Hand_Over flow with New Parent w" << endl;
            Node *w = new Node();
            w->childNodes.push_back(this);
            w->childNodes.push_back(u2);
            this->Parent = w;
            u2->Parent = w;
        }
    }else if((int)this->childNodes.size()==0){ //If Leaf
        u2 = splitLeaf();
        if(this->Parent!=nullptr){
            //cout<<"Hand_Over flow with Exist Parent"<< endl;
            u2->Parent = this->Parent;
            this->Parent->childNodes.push_back(u2);
            if((int)u2->Parent->childNodes.size()>B){
                //cout << "[W's ChildNodes Size]=" << u2->Parent->childNodes.size() << endl;
                u2->Parent->handle_overflow();
            }
        }else{
            //cout << "Hand_Over flow with New Parent w" << endl;
            Node *w = new Node();
            w->childNodes.push_back(this);
            w->childNodes.push_back(u2);
            this->Parent = w;
            u2->Parent = w;
            //updateMBR(this->Parent);
        }
    }
    updateMBR(this->Parent);
}

Node* Node::choose_subtree(Data &dataPt){
    //cout << "[***Node Size***]" << (int)this->childNodes.size() << endl;
    Node *bestNode = nullptr;
    int bestIncs = INT_MAX;
    vector<int> bestMBR;
    for(int i =0; i < (int)this->childNodes.size(); i++){
            Node *newNode = this->childNodes.at(i);
            vector<int> subMBR = getMBR(*newNode);
            //int newIncs = getIncs(newNode->MBR.at(0),newNode->MBR.at(1),newNode->MBR.at(2),newNode->MBR.at(3), dataPt);
            int newIncs = getIncs(subMBR.at(0),subMBR.at(1),subMBR.at(2),subMBR.at(3), dataPt);
            //cout << "[CurrIncrease]="<< newIncs <<" < [BestIncrease]" << bestIncs << endl;
            if(bestIncs>newIncs){
                bestIncs = newIncs;
                bestNode = newNode;
                bestMBR = subMBR;
            }
        //cout << "[Best Incs]=" << bestIncs << endl;
    }
    /*cout << "[BestNode's Children]=";
    for(Data d : bestNode->dataPoints){
        cout << d.ID << "->";
    }
    cout << endl;*/
    bestNode->MBR = bestMBR;
    return bestNode;
}


/**
 Condition1: Node is Leaf
 Condition2: Node is Node
 @param dataPt A inserted data point
 @_returns Report all points stored at nd that covered by srchReg
 */
void Node::insert_DataPoint(Data &dataPt){
    
    //cout << "[Data]=" <<  dataPt.ID << "_" <<dataPt.X << "_" <<dataPt.Y << endl;
    if((int)childNodes.size()==0){ //IF Leaf
        //cout << "LEAF" << endl;
        dataPoints.push_back(dataPt);
        //cout << "[DataPT Count]=" << (int)this->dataPoints.size() << endl;
        if((int)dataPoints.size()>B){
            //cout << "[OverFlow]=" << dataPt.ID << endl;
            this->handle_overflow();
        }
         //cout << endl;
    }else{                      //If Node
       //cout << "NODE" << endl;
       //cout << "!!!NODE Parent's ChildSize = " << (int)childNodes.size() << endl;
        Node *v = choose_subtree(dataPt);
       /*cout << "[Choose SubTree]=" << "min_X" << v->MBR.at(0) << " min_Y" <<  v->MBR.at(1)
        << " max_X" <<  v->MBR.at(2) << " max_Y" <<  v->MBR.at(3) << endl;*/
        v->insert_DataPoint(dataPt);
    }
}

long long Node::getMinst(Data &q){
    long long cx = 0;
    long long cy = 0;
    
    if(q.X < this->MBR.at(0)){
        cx = this->MBR.at(0) - q.X;
    }else if(q.X > this->MBR.at(2)){
        cx = q.X - this->MBR.at(2);
    }
    
    if(q.Y < this->MBR.at(1)){
        cy = this->MBR.at(1) - q.Y;
    }else if(q.Y > this->MBR.at(3)){
        cy = q.Y - this->MBR.at(3);
    }
    return cx * cx + cy*cy;
    
//    cx = max(min(q.X,this->MBR.at(2)),this->MBR.at(0));
//    cy = max(min(q.Y,this->MBR.at(3)),this->MBR.at(1));
//    return (q.X - cx) * (q.X - cx) + (q.Y- cy) * (q.Y-cy);
//    return pow(q.X-cx,2)+pow(q.Y-cy,2);
}



