CC=g++
FLAGS=-Wall -g -pedantic -std=gnu++11
PARTS=1.1 1.2 2.1 2.2 3.1 3.2

all:  $(patsubst %,./%/main.o,$(PARTS))

%.o: %.cpp
	$(CC) -c -o $@ $< $(FLAGS)

.PHONY: clean

clean:
	rm -f ./*/main.o
