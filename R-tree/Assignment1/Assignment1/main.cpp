//
//  main.cpp
//  Assignment1
//
//  Created by RemosyXu on 8/5/17.
//  Copyright © 2017 RemosyXu. All rights reserved.
//

#include <iostream>
#include <fstream>
#include <string.h>
#include <vector>
#include <queue>          // std::priority_queue
#include <stdio.h>
#include <unistd.h>
#include "Data.hpp"
#include "Node.hpp"
#include <list>
#include <math.h>


using namespace std;

vector<Data> datalist;
Data NN_best;
Node **tree = nullptr;
Node *node = new Node();

struct sort_H
{
    bool operator()(pair<Node*,int> &a, pair<Node*,int> &b)
    {
        return a.second > b.second || a.second == b.second;
    }
};

/**
 @param u Multiple nodes
 @param mbr A search region of range query
 @_returns Report all points stored at multPt that covered by srchReg
 */
int Range_Query(Node **u, vector<int> mbr){
    int result = 0;
    //cout << "Xmin= " << mbr.at(0) << " Xmax=" << mbr.at(1) << " Ymin=" << mbr.at(2)
    ///<< " Ymax= " << mbr.at(3) << endl;
    if((int) (*u)->childNodes.size()==0){
        for(Data data: (*u)->dataPoints){
            int x = data.X;
            int y = data.Y;
            //cout << "ID_"<< data.ID << "__" << x << "____" << y << endl;
            if(x <= mbr.at(1) && x>= mbr.at(0)
               && y <= mbr.at(3) && y >= mbr.at(2)){
                result++;
            }
        }
    }else{
        for(Node *v: (*u)->childNodes){
            //cout << "Xmin= " << MBR.at(0) << " Xmax=" << MBR.at(2) << " Ymin=" << MBR.at(1)
            ///<< " Ymax= " << MBR.at(3) << endl;
            if(v->MBR.at(2) < mbr.at(0) || v->MBR.at(0) > mbr.at(1) || v->MBR.at(1) > mbr.at(3)|| v->MBR.at(3)< mbr.at(2)){
                continue;
            }
            result += Range_Query(&v, mbr);
        }
    }
    return result;
}

vector<pair<Node*, int>> NN_sort(vector<pair<Node*, int>> H_Set){
    sort(H_Set.begin(), H_Set.end(), [](const pair<Node*,int> &a, const std::pair<Node*,int> &b) {
        return a.second < b.second;
    });
    return H_Set;
}

/*long long getMinst(vector<int> q, vector<int> mbr){
    long long cx;
    long long cy;
    if((int)mbr.size()==4){     //Point -> rectangle
        cx = max(min(q.at(0),mbr.at(2)),mbr.at(0));
        cy = max(min(q.at(1),mbr.at(3)),mbr.at(1));
    }else if((int)mbr.size()==2){ //Point -> query point
        cx = mbr.at(0);
        cy = mbr.at(1);
    }
    //return pow(q.at(0)-cx,2)+pow(q.at(1)-cy,2);
    return abs(q.at(0)-cx + q.at(1)-cy);
}

bool isGreater(long long best_minst, priority_queue<pair<Node*, int>, vector<pair<Node*, int>>,sort_H> H_Set){
    return  best_minst > H_Set.top().second;
}*/

/**
 @param u Multiple node
 @param q A search point of range query
 @_returns Report all points near by covered by srchReg
 */
vector<int> NN_Query(Node **u, Data q){
    long long best_mindist = LONG_MAX;
    vector<int> NN_best; //Out_put
    priority_queue<pair<Node*, int>, vector<pair<Node*, int>>,sort_H> H; //(Node,MINDIST)
    H.push(make_pair(*u, (*u)->getMinst(q)));
    
    while(best_mindist > H.top().second){
        Node *next = H.top().first; //Node next = H pop_front
        H.pop();                    //Remove used node
        
        if((int)next->childNodes.size()>0){ // u is Not Leaf
            for(int i =0; i<(int)next->childNodes.size();i++){
                long long mdist = next->childNodes.at(i)->getMinst(q);
                H.push(make_pair(next->childNodes.at(i),mdist));
            }
        }else{ // u is Leaf
            for(int k =0; k<(int)next->dataPoints.size();k++){
                Data pt = next->dataPoints.at(k);
                long long NN = pt.getMinst(q);
                if(NN<best_mindist){
                    NN_best.clear();
                    best_mindist = NN;
                    NN_best.push_back(pt.ID);
                }else if(NN==best_mindist){
                    NN_best.push_back(pt.ID);
                }
            }
        
        }
    }
    return NN_best;
}

/**
 RangeQuery
 @param fname A file of queries
 @_returns a list of query cases
 */
vector<vector<int>> ReadQ1(string fname){
    cout << "[ReadQ1]" << endl;
    ifstream stream(fname);
    string line;
    vector<vector<int>> Q1;
    if(!stream.good()){exit(0);}
    while(getline(stream, line)){
        std::string delimiter = " ";
        size_t pos = 0;
        std::string token;
        vector<int> temp;
        while ((pos = line.find(delimiter)) != string::npos) {
            token = line.substr(0, pos);
            temp.push_back(stoi(token));
            line.erase(0, pos + delimiter.length());
        }
        if((int)temp.size()!=3)exit(0); // Above can only get first str
        temp.push_back(stoi(line));     // Get the last elements of line
        Q1.push_back(temp);
    }
    return Q1;
}

/**
 NN Search
 @param fname A file of queries
 @_returns a list of query cases
 */
vector<Data> ReadQ2(string fname){
    cout << "[ReadQ2]" << endl;
    ifstream stream(fname);
    string line;
    vector<Data> Q2;
    int count=0;
    if(!stream.good()){exit(0);}
    while(getline(stream, line)){
        count++;
        std::string delimiter = " ";
        size_t pos = 0;
        std::string token;
        vector<int> temp;
        while ((pos = line.find(delimiter)) != string::npos) {
            token = line.substr(0, pos);
            temp.push_back(stoi(token));
            line.erase(0, pos + delimiter.length());
        }
        if((int)temp.size()!=1)exit(0); // Above can only get first str
        temp.push_back(stoi(line));     // Get the last elements of line
        Data data;
        data.X = temp.at(0);
        data.Y = temp.at(1);
        data.ID = count;
        Q2.push_back(data);
    }
    return Q2;
}

/**
 The command line usage for assignment
 **/
void showUsage(const char * argv, string comment){
    cerr << comment << endl << "Usage: " << argv << "FILENAME" << endl;
    exit(0);
}

/**
 @param fname A file of data
 */
void ImportData(string fname){
    ifstream stream(fname);
    int totalPt;
    string line;
    if(!stream.good()){exit(0);}
    getline(stream, line);
    totalPt = stoi(line);
    cout << line << endl;
    
    while(getline(stream, line) && totalPt!=0){
        //cout << line << endl;
        totalPt--;
        std::string delimiter = " ";
        size_t pos = 0;
        std::string token;
        vector<int> temp;
        while ((pos = line.find(delimiter)) != string::npos) {
            token = line.substr(0, pos);
            temp.push_back(stoi(token));
            line.erase(0, pos + delimiter.length());
        }
        if((int)temp.size()!=2)exit(0); // Above can only get first two str
        temp.push_back(stoi(line));     // Get the last elements of line
        Data data = Data(temp.at(0),temp.at(1),temp.at(2));
        datalist.push_back(data);
    }

    cout << "Complect__________________Read File"<< endl;
    
    if(totalPt!=0){cout<<"Total PT:" <<totalPt <<endl;exit(0);}
}

/**
 @param data A list of data
 @param mbr A list of quesries
 @_returns Report scan time
 */
int BruteForce_Q1(vector<Data> data, vector<int> mbr){
    int result= 0;
    for(int i=0;i < data.size();i++){
        if(data.at(i).X <= mbr.at(1) && data.at(i).X>= mbr.at(0)
           && data.at(i).Y <= mbr.at(3) && data.at(i).Y >= mbr.at(2)){
            result++;
        }
        /*if(data.at(i).X >= mbr.at(0) && data.at(i).X<= mbr.at(2)
         && data.at(i).Y >= mbr.at(1) && data.at(i).Y <= mbr.at(3)){
         result++;
         }*/
    }
    return result;
}

/**
 @param data A list of data
 @param q A list of quesries
 @_returns Report scan time
 */
vector<int> BruteForce_Q2(vector<Data> data, Data q){
    vector<int> result;
    long long bf_best = INT_MAX;
    for(int i=0;i < data.size();i++){
        Data pt = Data(0, data.at(i).X, data.at(i).Y);
        long long mindist = pt.getMinst(q);
        if (mindist < bf_best){
            result.clear();
            bf_best = mindist;
            result.push_back(data.at(i).ID);
        }else if(mindist == bf_best){
            result.push_back(data.at(i).ID);
        }
    }
    return result;
}

/**
 Multiple-times Write in log
 **/
void logger1(string filename,long time, int numQ,vector<int> data){
    string path(getcwd(NULL,0));
    path.append(filename);
    ofstream log;
    ifstream file(path);
    cout << "[Saved at] " << path << endl;
    string strt;
    getline(file,strt);
    log.open(path,fstream::trunc);
    for (auto k = data.begin(); k != data.end(); ++k)log << *k << endl;
    log << endl<< "Total Time: " << time;
    log << endl<< "Average Time: " << time/numQ;
    data.clear();
    log.close();
}

/**
 Multiple-times Write in log
 **/
void logger2(string filename,long time, int numQ,vector<vector<int>> data){
    string path(getcwd(NULL,0));
    path.append(filename);
    ofstream log;
    ifstream file(path);
    cout << "[Saved at] " << path << endl;
    string strt;
    getline(file,strt);
    log.open(path,fstream::trunc);
    for(int ii = 0; ii < (int)data.size();ii++){
        for(int jj = 0; jj < (int)data.at(ii).size();jj++){
            log << data.at(ii).at(jj) << " / ";
        }
        log << endl;
    }
    log << endl<< "Total Time: " << time;
    log << endl<< "Average Time: " << time/numQ;
    data.clear();
    log.close();
}



int main(int argc, const char * argv[]) {
    
    string fn = "/Users/remosy/INFS4205/Assignment1/Assignment1/dataset4.txt";
    string fn1 = "/Users/remosy/INFS4205/Assignment1/Assignment1/range4.txt";
    string fn2 = "/Users/remosy/INFS4205/Assignment1/Assignment1/nnquery4.txt";
    
    ImportData(fn);
    
    for(Data d : datalist){
        node->isLeaf = 1;
        node->insert_DataPoint(d);
        tree = &node;
        while((*tree)->Parent != nullptr) {
            (*tree) = node->Parent;
        }
    }
    (*tree)->updateMBR(*tree); //For NN search
    
    cout << "Complect__________________Build Tree"<< endl;
    
    
    vector<vector<int>> Q1 = ReadQ1(fn1);
    cout << "[ReadQ1 Total Query]=" << (int)Q1.size()<< endl;
    vector<Data> Q2 = ReadQ2(fn2);
    cout << "[ReadQ2 Total Query]=" << (int)Q2.size()<< endl;
    
    vector<int> BF_Q1;
    vector<int> RQ;
    vector<vector<int>> BF_Q2;
    vector<vector<int>> NN;
    
    //Test BruteForce
    
    clock_t start = clock();
    for(vector<int> q : Q1){
        BF_Q1.push_back(BruteForce_Q1(datalist, q));
    }
    clock_t end = clock();
    long time1 = end - start;
    logger1("/out_BFRQ.txt",time1,(int)Q1.size(),BF_Q1);
    cout << "[Brute Force Query Time]=" << time1 << endl;
    

    //Test Range Query
    clock_t start2 = clock();
    for(vector<int> q : Q1){
        RQ.push_back(Range_Query(tree, q));
    }
    clock_t end2 = clock();
    long time2 = end2 - start2;
    logger1("/out_RQ.txt",time2,(int)Q1.size(),RQ);
    cout << "[Range Query Time]="<< time2 << endl;
    
    
    clock_t start3 = clock();
    for(Data q : Q2){
        BF_Q2.push_back(BruteForce_Q2(datalist,q));
    }
    clock_t end3 = clock();
    long time3 =end3 - start3;
    logger2("/out_BFNN.txt",time3,(int)Q2.size(),BF_Q2);
    cout << "[Brute Force Query Time]="<< time3<< endl;
    
    //Test NN_Query
     clock_t start4 = clock();
     for(Data q : Q2){
         NN.push_back(NN_Query(tree, q));
     }
     clock_t end4 = clock();
     long time4 = end4 - start4;
     logger2("/out_NN.txt",time4,(int)Q2.size(),NN);
     cout << "[Nearest Neighborhood Query Time]="<< time4<< endl;
    
    return 0;
}
