FLAGS=-Wall -std=gnu++11

all: one two

one: ./1.1/freq.cpp ./1.2/freq.cpp
	g++ ${FLAGS} ./1.1/freq.cpp -o ./1.1/one
	g++ ${FLAGS} ./1.2/freq.cpp -o ./1.2/one

two: ./2.1/ids.cpp ./2.2/ids.cpp
	g++ ${FLAGS} ./2.1/ids.cpp -o ./2.1/ids
	g++ ${FLAGS} ./2.2/ids.cpp -o ./2.2/ids
