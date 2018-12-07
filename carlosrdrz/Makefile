CC=g++
FLAGS=-Wall -g -pedantic -std=gnu++11
PARTS=1.1 1.2 2.1 2.2 3.1 3.2 4.1 4.2 5.1 5.2

all:  $(patsubst %,./%/main.run,$(PARTS))

%.run: %.cpp
	$(CC) -o $@ $< $(FLAGS)

.PHONY: clean

clean:
	rm -f ./*/main.run
