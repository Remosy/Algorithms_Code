//
//  Data.cpp
//  Assignment1
//
//  Created by RemosyXu on 22/5/17.
//  Copyright © 2017 RemosyXu. All rights reserved.
//
#include "Data.hpp"
#include <iostream>
#include <vector>
#include <math.h>
using namespace std;

Data::Data(int id, int x, int y){
    ID = id;
    X = x;
    Y = y;
}

bool Data::isEmpty(){
    bool is_empty = false;
    if(ID==-1 && X == -1 && Y == -1){is_empty=true;}
    return is_empty;
}

long long Data::getMinst(Data &query){
    long long temp1 = query.X - this->X;
    long long temp2 = query.Y - this->Y;
    return temp1 * temp1 + temp2 * temp2;

//    return pow(query.X-this->X,2) + pow(query.Y-this->Y,2);
}
