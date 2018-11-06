//
//  Data.hpp
//  Assignment1
//
//  Created by RemosyXu on 22/5/17.
//  Copyright © 2017 RemosyXu. All rights reserved.
//

#ifndef Data_hpp
#define Data_hpp

#include <stdio.h>
#include <vector>
using namespace std;
class Data{
public:
    int ID;
    int X;
    int Y;
    
    Data(int id, int x, int y);   //Constructor
    Data():ID(-1),X(-1),Y(-1){};  //Constructor2
    long long getMinst(Data &query);
    ~Data(){};
    bool isEmpty();
};

#endif /* Data_hpp */
